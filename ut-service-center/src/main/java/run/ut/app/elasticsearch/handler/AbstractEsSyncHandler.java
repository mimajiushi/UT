package run.ut.app.elasticsearch.handler;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import run.ut.app.service.OptionsService;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wenjie
 */
@Data
@Component
@Slf4j
public class AbstractEsSyncHandler {

    @Autowired
    private OptionsService optionsService;

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

}
