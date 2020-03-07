package run.ut.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger Configuration
 *
 * @author wenjie
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("run.ut.app"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("UT")
                .description("UT接口文档")
                //.termsOfServiceUrl("/")
                .version("1.0")
                .build();
    }
}
