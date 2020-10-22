package binlog;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import org.junit.Test;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BinLogTest {

    private String database;
    private String table;

    @Test
    public void test1() {
        // 监听binlog的客户端
        BinaryLogClient client = new BinaryLogClient(
            "192.168.129.129",
            3306,
            "binlog_test", // 这个其实没什么卵用
            "binlog_test",
            "binlog_test"
        );

        //
        client.registerEventListener(event -> {

            TableMapEventData tableMapEventData = event.getData();
            this.database = tableMapEventData.getDatabase();
            this.table = tableMapEventData.getTable();

            EventData data = event.getData();
            List<Serializable[]> values = getAfterValues(data);
            for (Serializable[] serializables : values) {

            }


        });
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
