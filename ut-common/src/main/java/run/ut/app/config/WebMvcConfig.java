package run.ut.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author wenjie
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Configure swagger's resources and static resources
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/", "classpath:/static/");
        super.addResourceHandlers(registry);
    }
}
