package run.ut.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author swj
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Configure swagger's resources
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/");
        super.addResourceHandlers(registry);
    }
}
