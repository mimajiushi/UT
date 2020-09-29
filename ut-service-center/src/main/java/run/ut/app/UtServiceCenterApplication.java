package run.ut.app;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * UT main class
 *
 * @author wenjie
 */

@SpringBootApplication
@EnableAsync
@EnableScheduling
@DubboComponentScan(basePackages = {"run.ut.app.netty.impl", "run.ut.app.mail.impl", "run.ut.app.handler", "run.ut.app.service.impl"})
@MapperScan("run.ut.app.mapper")
public class UtServiceCenterApplication extends SpringBootServletInitializer {

    private static ConfigurableApplicationContext CONTEXT;

    public static void main(String[] args) {
        System.setProperty("spring.config.additional-location", "file:${user.home}/.ut-service-center/");
        CONTEXT = SpringApplication.run(UtServiceCenterApplication.class);
    }

    public static void close() {
        Thread thread = new Thread(() -> {
            CONTEXT.close();
        });

        thread.setDaemon(false);
        thread.start();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // Customize the spring config location
        System.setProperty("spring.config.additional-location", "file:${user.home}/.ut-service-center/");
        return super.configure(builder);
    }
}
