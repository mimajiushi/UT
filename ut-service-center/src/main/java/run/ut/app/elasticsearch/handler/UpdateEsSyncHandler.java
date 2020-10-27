package run.ut.app.elasticsearch.handler;

import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import run.ut.app.elasticsearch.TableTemplate;
import run.ut.app.utils.JsonUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wenjie
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UpdateEsSyncHandler extends AbstractEsSyncHandler implements EsSyncHandler {

    private final TableTemplate tableTemplate;
    private final RestHighLevelClient restHighLevelClient;


    @Value("${spring.datasource.database}")
    private String database;

    @Override
    public void sync(EventData eventData) throws IOException {
        if (!this.database.equals(EsSyncHandlers.database)) {
            return;
        }
        if (eventData instanceof UpdateRowsEventData) {
            List<Serializable[]> rows = ((UpdateRowsEventData) eventData).getRows().stream()
                // key is before，value is after
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
            List<String> columns = tableTemplate.getColumnMap().get(EsSyncHandlers.table);
            for (Serializable[] serializables : rows) {
                // sql's column -> value
                Map<String, String> map = new HashMap<>(1 << 4);
                for (int i = 0; i < serializables.length; i++) {
                    // Text type corresponding to MySQL
                    if (serializables[i] instanceof byte[]) {
                        map.put(columns.get(i), new String((byte[]) serializables[i]));
                    } else {
                        map.put(columns.get(i), serializables[i].toString());
                    }
                }
                UpdateRequest updateRequest = new UpdateRequest(EsSyncHandlers.table, map.get("id"));
                map.remove("id");
                updateRequest.doc(JsonUtils.objectToJson(map), XContentType.JSON);

                restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
                syncBinLogProperties();
            }
        }
    }
}
