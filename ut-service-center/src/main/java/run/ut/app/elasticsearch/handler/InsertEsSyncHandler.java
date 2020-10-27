package run.ut.app.elasticsearch.handler;

import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import run.ut.app.elasticsearch.TableTemplate;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wenjie
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InsertEsSyncHandler extends AbstractEsSyncHandler implements EsSyncHandler {

    private final TableTemplate tableTemplate;
    private final RestHighLevelClient restHighLevelClient;


    @Value("${spring.datasource.database}")
    private String database;

    @Override
    public void sync(EventData eventData) throws IOException {
        if (!this.database.equals(EsSyncHandlers.database)) {
            return;
        }
        if (eventData instanceof WriteRowsEventData) {
            List<Serializable[]> rows = ((WriteRowsEventData) eventData).getRows();
            List<String> columns = tableTemplate.getColumnMap().get(EsSyncHandlers.table);
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
                IndexRequest indexRequest = new IndexRequest(EsSyncHandlers.table);

                indexRequest.id(map.get("id"));
                map.remove("id");
                indexRequest.source(map, XContentType.JSON);
                restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
                syncBinLogProperties();
            }
        }
    }
}
