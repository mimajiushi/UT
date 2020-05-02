package run.ut.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * Halo main class.
 *
 * @author ryanwang
 * @date 2017-11-14
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
@MapperScan(basePackages = "run.ut.app.mapper")
public class UtApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        System.setProperty("spring.config.additional-location", "file:${user.home}/.ut/");
        SpringApplication.run(UtApplication.class);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // Customize the spring config location
        System.setProperty("spring.config.additional-location", "file:${user.home}/.ut/");
        return super.configure(builder);
    }
}
