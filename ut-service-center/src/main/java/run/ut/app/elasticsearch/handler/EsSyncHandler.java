package run.ut.app.elasticsearch.handler;

import com.github.shyiko.mysql.binlog.event.EventData;

import java.io.IOException;

/**
 * @author wenjie
 */
public interface EsSyncHandler {

    /**
     * Processing eventData
     *
     * @param eventData    binlog eventData
     */
    public void sync(EventData eventData) throws IOException;
}
