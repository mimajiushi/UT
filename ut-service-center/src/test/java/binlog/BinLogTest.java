package binlog;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import run.ut.app.UtServiceCenterApplication;
import run.ut.app.elasticsearch.TableTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UtServiceCenterApplication.class)
@Slf4j
public class BinLogTest {

    @Resource
    private TableTemplate tableTemplate;
    @Resource
    private RestHighLevelClient restHighLevelClient;
    @Value("${spring.datasource.database}")
    private String database;

    @Test
    public void test1() throws IOException {
        // 监听binlog的客户端
        BinaryLogClient client = new BinaryLogClient(
            "192.168.129.129",
            3306,
            "root",
            "root"
        );

        //
        client.registerEventListener(event -> {
            EventType eventType = event.getHeader().getEventType();
            String database = "";
            String table = "";
            if (eventType == EventType.TABLE_MAP){
                TableMapEventData tableMapEventData = event.getData();
                database = tableMapEventData.getDatabase();
                table = tableMapEventData.getTable();
            }
            if (database.equals("") || table.equals("")) {
                log.error("database or table is null!");
            }
            if (eventType != EventType.EXT_UPDATE_ROWS
                && eventType != EventType.EXT_WRITE_ROWS
                && eventType != EventType.EXT_DELETE_ROWS) {
                log.info("eventType not in EXT_UPDATE_ROWS or EXT_WRITE_ROWS or EXT_DELETE_ROWS");
                return;
            }
            try {

                EventData data = event.getData();
                List<Serializable[]> values = getAfterValues(data);
                if (!tableTemplate.getColumnMap().containsKey(table)) {
                    return;
                }
                if (!database.equals(this.database)) {
                    return;
                }
                List<String> columns = tableTemplate.getColumnMap().get(table);
                for (Serializable[] serializables : values) {
                    // sql's column -> value
                    Map<String, String> map = new HashMap<>(1 << 4);
                    for (int i = 0; i < serializables.length; i++) {
                        map.put(columns.get(i), serializables[i].toString());
                    }
                    // 添加文档
                    IndexRequest indexRequest = new IndexRequest(table);

                    indexRequest.id(map.get("id"));
                    indexRequest.source(map, XContentType.JSON);

                    IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        client.connect();
    }




    private List<Serializable[]> getAfterValues(EventData eventData) {

        if (eventData instanceof WriteRowsEventData) {
            return ((WriteRowsEventData) eventData).getRows();
        }

        if (eventData instanceof UpdateRowsEventData) {
            return ((UpdateRowsEventData) eventData).getRows().stream()
                // key是before，value是after
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        }

        if (eventData instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData) eventData).getRows();
        }

        return Collections.emptyList();
    }


}
