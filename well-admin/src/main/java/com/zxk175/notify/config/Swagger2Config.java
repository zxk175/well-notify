package com.zxk175.notify.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2配置
 *
 * @author zxk175
 * @since 2019-10-12 16:20
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createAppApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                // 禁用默认的响应
                .useDefaultResponseMessages(false)
                // 设置为true，以使文档代码生成友好
                .forCodeGeneration(true)
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(security());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("WellNotify 项目")
                .description("了解更多请点击：https://zxk175.com")
                .termsOfServiceUrl("https://zxk175.com")
                .contact(new Contact("张小康", "https://zxk175.com", "zxk175@qq.com"))
                .version("1.0.0")
                .build();
    }

    private List<ApiKey> security() {
        List<ApiKey> apiKeys = new ArrayList<>();
        apiKeys.add(new ApiKey("token", "token", "header"));
        return apiKeys;
    }
}
