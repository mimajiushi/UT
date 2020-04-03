package run.ut.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * Halo main class.
 *
 * @author ryanwang
 * @date 2017-11-14
 */
@SpringBootApplication
@MapperScan(basePackages = "run.ut.app.mapper")
public class UtApplication extends SpringBootServletInitializer {

    @SuppressWarnings("checkstyle:StaticVariableName")
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(UtApplication.class);
    }
}
