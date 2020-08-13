package run.ut.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

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
        List<Parameter> pars = new ArrayList<>();

        ParameterBuilder userTokenHeader = new ParameterBuilder();
        userTokenHeader.name("UT-Token").description("用户的token，有登录拦截、权限拦截的接口需要此参数")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();

        pars.add(userTokenHeader.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("run.ut.app"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
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
