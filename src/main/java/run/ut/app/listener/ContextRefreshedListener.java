package run.ut.app.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import run.ut.app.config.netty.WebSocketConfiguration;
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
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    private final WebSocketConfiguration webSocketConfiguration;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            webSocketServerBoot();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void webSocketServerBoot() throws InterruptedException {
        WebSocketServer.getInstance().start(webSocketConfiguration.getPort());
    }
}
