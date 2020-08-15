package run.ut.app.route;

import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) throws NacosException {
        return builder.routes()
            .route("ut_chat_service_http", r -> r.path("/ws").uri("lb:ws://ut-chat-service-websocket/ws"))
            .build();
    }
}
