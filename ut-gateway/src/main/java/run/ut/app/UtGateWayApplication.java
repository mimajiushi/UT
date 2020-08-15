package run.ut.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

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
}
