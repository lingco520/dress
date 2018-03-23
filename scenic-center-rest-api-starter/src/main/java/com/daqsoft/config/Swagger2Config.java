package com.daqsoft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//参考：http://blog.csdn.net/catoop/article/details/50668896

/**
 * @author zhouq
 * @email zhouq@daqsoft.com
 * @date 2017-06-21 11:53
 * @Version: V1.0.0
 * @Describe: Swagger2 配置
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)

                .apiInfo(apiInfo())

                .select()

                .apis(RequestHandlerSelectors.basePackage("org.spring.springboot.widgetController"))

                .paths(PathSelectors.any())

                .build();

    }


    /**
     * swagger-bootstrap-ui
     * @return
     */
    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()

                .title("swagger-bootstrap-ui RESTful APIs")

                .description("swagger-bootstrap-ui")

                .termsOfServiceUrl("http://api.test.com/")

                .contact("developer@mail.com")

                .version("1.0")

                .build();

    }

}