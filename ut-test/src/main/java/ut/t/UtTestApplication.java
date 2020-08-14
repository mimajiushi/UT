package ut.t;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * UT main class
 *
 * @author wenjie
 */

@SpringBootApplication
public class UtTestApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(UtTestApplication.class, args);
    }
}
