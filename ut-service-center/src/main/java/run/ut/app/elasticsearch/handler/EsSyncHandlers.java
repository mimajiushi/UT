package run.ut.app.elasticsearch.handler;

import com.github.shyiko.mysql.binlog.event.EventData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

/**
 * @author wenjie
 */
@Slf4j
@Service
public class EsSyncHandlers {

    private Collection<EsSyncHandler> esSyncHandlers = new LinkedList<>();

    public static String table = "";
    public static String database = "";

    public EsSyncHandlers(ApplicationContext applicationContext) {
        this.esSyncHandlers = applicationContext.getBeansOfType(EsSyncHandler.class).values();
    }

    public void sync(EventData eventData) throws IOException {
        for (EsSyncHandler esSyncHandler : esSyncHandlers) {
            esSyncHandler.sync(eventData);
        }
    };

}
