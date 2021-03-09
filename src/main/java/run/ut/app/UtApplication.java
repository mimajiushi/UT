package run.ut.app;

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
@MapperScan(basePackages = "run.ut.app.mapper")
public class UtApplication {

    private static ConfigurableApplicationContext CONTEXT;

    public static void main(String[] args) {
        System.setProperty("spring.config.additional-location",
                "optional:file:${user.home}/.ut/,optional:file:${user.home}/ut-dev/");
        CONTEXT = SpringApplication.run(UtApplication.class);
    }

    public static void close() {
        Thread thread = new Thread(() -> {
            CONTEXT.close();
        });

        thread.setDaemon(false);
        thread.start();
    }

}
