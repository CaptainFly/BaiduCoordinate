package com.jxtii.oa.common.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Fly
 * swagger配置
 * API文档路径:http://ip:port/swagger-ui.html
 */
@Configuration
@EnableSwagger2
@EnableWebMvc
@ComponentScan(basePackages = {"com.jxtii.oa"})
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("郭灵发", "http://www.jxtii.com", "18970026242@189.cn");
        return new ApiInfoBuilder()
                .title("积分管理系统REST API")
                .description("积分管理系统API文档。所有请求后添加参数：timestamp=1492530664&username=admin&digest=70f061dcc658921e565fd9a8402bef6a8c02f6eee76612969d007767e354f06a <br />" +
                        "返回参数：{\n" +
                        "  \"code\": 0,\n" +
                        "  \"data\": {},\n" +
                        "  \"msg\": \"string\"\n" +
                        "} ，code等于200为正常，其他值均为异常。")
                .contact(contact)
                .version("1.0")
                .build();
    }
}
