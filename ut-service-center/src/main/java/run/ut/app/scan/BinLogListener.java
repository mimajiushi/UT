package run.ut.app.scan;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
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
import run.ut.app.utils.SpringUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
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
        log.info(event.getData().toString());
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
        List<Serializable[]> values = getValues(data);
        List<String> columns = tableTemplate.getColumnMap().get(table);
        for (Serializable[] serializables : values) {
            // sql's column -> value
            Map<String, String> map = new HashMap<>(1 << 4);
            for (int i = 0; i < serializables.length; i++) {
                map.put(columns.get(i), serializables[i].toString());
            }
            // TODO 不同sql操作也要对应不同es操作
            // sync document
            IndexRequest indexRequest = new IndexRequest(table);

            indexRequest.id(map.get("id"));
            indexRequest.source(map, XContentType.JSON);

            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        }

    }


    private List<Serializable[]> getValues(EventData eventData) {

        if (eventData instanceof WriteRowsEventData) {
            return ((WriteRowsEventData) eventData).getRows();
        }

        if (eventData instanceof UpdateRowsEventData) {
            return ((UpdateRowsEventData) eventData).getRows().stream()
                // key is before，value is after
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        }

        if (eventData instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData) eventData).getRows();
        }

        return Collections.emptyList();
    }

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        binlogClientBoot();
    }

    private void binlogClientBoot() throws IOException {
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
