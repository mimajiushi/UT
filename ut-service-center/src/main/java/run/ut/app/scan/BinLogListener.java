package run.ut.app.scan;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import run.ut.app.repository.EsPostRepository;
import run.ut.app.sync.TableTemplate;
import run.ut.app.utils.JsonUtils;
import run.ut.app.utils.SpringUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author wenjie
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class BinLogListener implements BinaryLogClient.EventListener, ApplicationListener<ContextRefreshedEvent> {

    private final TableTemplate tableTemplate;
    private final RestHighLevelClient restHighLevelClient;
    private final DataSourceProperties dataSourceProperties;
    private final EsPostRepository esPostRepository;
    private BinaryLogClient client;

    @Value("${spring.datasource.database}")
    private String database;
    @Value("${spring.datasource.host}")
    private String host;
    @Value("${spring.datasource.port}")
    private Integer port;

    private String table = "";
    private String databaseName = "";

    @SneakyThrows
    @Override
    public void onEvent(Event event) {
        if (event != null && event.getData() != null) {
            log.info(event.getData().toString());
        }
        EventType eventType = event.getHeader().getEventType();
        if (eventType == EventType.TABLE_MAP){
            TableMapEventData tableMapEventData = event.getData();
            this.databaseName = tableMapEventData.getDatabase();
            this.table = tableMapEventData.getTable();
            return;
        }
        if (table.equals("")) {
            log.warn("table is null!");
            return;
        }
        if (eventType != EventType.EXT_UPDATE_ROWS
            && eventType != EventType.EXT_WRITE_ROWS
            && eventType != EventType.EXT_DELETE_ROWS) {
            log.warn("eventType not in EXT_UPDATE_ROWS or EXT_WRITE_ROWS or EXT_DELETE_ROWS");
            return;
        }
        if (!tableTemplate.getColumnMap().containsKey(table)) {
            return;
        }
        if (!this.database.equals(databaseName)) {
            return;
        }

        EventData data = event.getData();
        sync(data);

    }


    private void sync(EventData eventData) throws IOException {

        // insert
        if (eventData instanceof WriteRowsEventData) {
            List<Serializable[]> rows = ((WriteRowsEventData) eventData).getRows();
            List<String> columns = tableTemplate.getColumnMap().get(table);
            for (Serializable[] serializables : rows) {
                // sql's column -> value
                Map<String, String> map = new HashMap<>(1 << 4);
                for (int i = 0; i < serializables.length; i++) {
                    if (serializables[i] instanceof byte[]) {
                        map.put(columns.get(i), new String((byte[]) serializables[i]));
                    } else {
                        map.put(columns.get(i), serializables[i].toString());
                    }
                }
                // sync document
                IndexRequest indexRequest = new IndexRequest(table);

                indexRequest.id(map.get("id"));
                map.remove("id");
                indexRequest.source(map, XContentType.JSON);

                restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            }
        }

        // update
        if (eventData instanceof UpdateRowsEventData) {
            List<Serializable[]> rows = ((UpdateRowsEventData) eventData).getRows().stream()
                // key is before，value is after
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
            List<String> columns = tableTemplate.getColumnMap().get(table);
            for (Serializable[] serializables : rows) {
                // sql's column -> value
                Map<String, String> map = new HashMap<>(1 << 4);
                for (int i = 0; i < serializables.length; i++) {
                    if (serializables[i] instanceof byte[]) {
                        map.put(columns.get(i), new String((byte[]) serializables[i]));
                    } else {
                        map.put(columns.get(i), serializables[i].toString());
                    }
                }
                UpdateRequest updateRequest = new UpdateRequest(table, map.get("id"));
                map.remove("id");
                updateRequest.doc(JsonUtils.objectToJson(map), XContentType.JSON);

                restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            }
        }

        // delete
        if (eventData instanceof DeleteRowsEventData) {
            List<Serializable[]> rows = ((DeleteRowsEventData) eventData).getRows();
            List<String> columns = tableTemplate.getColumnMap().get(table);
            for (Serializable[] serializables : rows) {
                // sql's column -> value
                Map<String, String> map = new HashMap<>(1 << 4);
                for (int i = 0; i < serializables.length; i++) {
                    if (serializables[i] instanceof byte[]) {
                        map.put(columns.get(i), new String((byte[]) serializables[i]));
                    } else {
                        map.put(columns.get(i), serializables[i].toString());
                    }
                }
                // sync document
                DeleteRequest deleteRequest = new DeleteRequest(table, map.get("id"));
                restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            }
        }
    }

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Thread thread = new Thread(() -> {
            try {
                binlogClientBoot();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        });
        thread.setDaemon(false);
        thread.start();
    }

    public void binlogClientBoot() throws IOException {
        BinLogListener binLogListener = SpringUtils.getBean(BinLogListener.class);
        client = new BinaryLogClient(
            host,
            port,
            dataSourceProperties.getUsername(),
            dataSourceProperties.getPassword()
        );
        client.registerEventListener(binLogListener);
        client.connect();
    }

    public void closeBinlogClient() throws IOException {
        client.disconnect();
    }
}
