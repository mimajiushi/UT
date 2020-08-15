package run.ut.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * UT main class
 *
 * @author wenjie
 */

@SpringBootApplication
@EnableDiscoveryClient
public class UtGateWayApplication {

    private static ConfigurableApplicationContext CONTEXT;

    public static void main(String[] args) {
        System.setProperty("spring.config.additional-location", "file:${user.home}/.ut-gateway/");
        CONTEXT = SpringApplication.run(UtGateWayApplication.class, args);
    }


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            // 获取服务实际的ip和端口
            .route("ut_chat_service_http", r -> r.path("/ws").uri("ws://localhost:8088/ws"))
            .build();
    }
}
