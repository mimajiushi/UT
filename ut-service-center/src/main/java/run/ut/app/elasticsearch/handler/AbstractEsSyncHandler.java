package run.ut.app.elasticsearch.handler;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import run.ut.app.service.OptionsService;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wenjie
 */
@Data
@Component
@Slf4j
public class AbstractEsSyncHandler {

    @Autowired
    private OptionsService optionsService;

    protected ConcurrentHashMap<String, Object> binLogPropertiesMap = new ConcurrentHashMap<>();

    protected void syncBinLogProperties() {
        optionsService.save(binLogPropertiesMap);
    }

}
