package run.ut.app.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import run.ut.app.UtApplication;
import run.ut.app.netty.WebSocketServer;

/**
 * The method executed after the application context is refreshed.
 *
 * @author wenjie
 */

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    private final WebSocketServer webSocketServer;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            webSocketServerBoot();
        } catch (Exception e) {
            e.printStackTrace();
            UtApplication.close();
        }
    }

    private void webSocketServerBoot() throws Exception {
        webSocketServer.init();
        webSocketServer.start();
    }
}
