package run.ut.app.elasticsearch.handler;

import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
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
public class DeleteEsSyncHandler extends AbstractEsSyncHandler implements EsSyncHandler {

    private final TableTemplate tableTemplate;
    private final RestHighLevelClient restHighLevelClient;

    @Value("${spring.datasource.database}")
    private String database;

    @Override
    public void sync(EventData eventData) throws IOException {
        if (!this.database.equals(EsSyncHandlers.database)) {
            return;
        }
        if (eventData instanceof DeleteRowsEventData) {
            List<Serializable[]> rows = ((DeleteRowsEventData) eventData).getRows();
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
                DeleteRequest deleteRequest = new DeleteRequest(EsSyncHandlers.table, map.get("id"));
                restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
                syncBinLogProperties();
            }
        }
    }
}
