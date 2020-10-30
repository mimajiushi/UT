package run.ut.app.elasticsearch.handler;

import com.github.shyiko.mysql.binlog.event.EventData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import run.ut.app.service.OptionsService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wenjie
 */
@Data
@Component
@Slf4j
public class AbstractEsSyncHandler implements EsSyncHandler {

    @Resource private OptionsService optionsService;

    private AtomicInteger count = new AtomicInteger(0);
    private int mod = 100;

    public static ConcurrentHashMap<String, Object> binLogPropertiesMap = new ConcurrentHashMap<>();

    /**
     * Every {@link AbstractEsSyncHandler#mod} times invoke, save to Mysql
     */
    protected void syncBinLogProperties() {
        int i = count.incrementAndGet();
        if (i / mod == 0) {
            optionsService.save(binLogPropertiesMap);
        }
    }

    protected void handleMap(Map<String, String> map) {
        handleId(map);
        handleDate(map);
    }

    protected void handleId(Map<String, String> map) {
        map.remove("id");
    }

    protected void handleDate(Map<String, String> map) {
        String createTime = map.get("create_time");
        String updateTime = map.get("update_time");
        map.put("create_time", createTime.substring(0, createTime.length() - 2));
        map.put("update_time", updateTime.substring(0, updateTime.length() - 2));
    }

    @Override
    public void sync(EventData eventData) throws IOException {

    }
}
