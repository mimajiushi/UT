package run.ut.app.config.netty;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wenjie
 */

@Component
@ConfigurationProperties(prefix = "netty.websocket")
@Data
public class WebSocketConfiguration {

    private int port;

    private String contextPath = "/ws";

}
