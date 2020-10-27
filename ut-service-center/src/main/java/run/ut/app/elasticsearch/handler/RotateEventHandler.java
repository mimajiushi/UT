package run.ut.app.elasticsearch.handler;

import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.RotateEventData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import run.ut.app.model.properties.BinLogProperties;

/**
 * @author wenjie
 */
@Component
@Slf4j
public class RotateEventHandler extends AbstractEsSyncHandler implements EsSyncHandler {


    @Override
    public void sync(EventData eventData) {
        if (eventData instanceof RotateEventData) {
            RotateEventData rotateEventData = (RotateEventData) eventData;
            String binlogFilename = rotateEventData.getBinlogFilename();
            long binlogPosition = rotateEventData.getBinlogPosition();

            AbstractEsSyncHandler.binLogPropertiesMap.put(BinLogProperties.BINLOG_FILENAME.getValue(), binlogFilename);
            AbstractEsSyncHandler.binLogPropertiesMap.put(BinLogProperties.BINLOG_POSITION.getValue(), binlogPosition);
        }
    }
}
