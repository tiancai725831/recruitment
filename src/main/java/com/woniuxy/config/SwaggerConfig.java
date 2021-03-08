package com.woniuxy.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2//开启swagger
public class SwaggerConfig {
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                //扫描接口的包
                .apis(RequestHandlerSelectors.basePackage("com.woniuxy.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfoBuilder()
                        //标题
                        .title("user控制类")
                        //描述
                        .description("user控制类的描述")
                        //版本
                        .version("1.0")
                        //联系人信息
                        .contact(new Contact("付翔","http://www.baidu.com","93502690@qq.com"))
                        //项目主页
                        .license("项目主页")
                        .licenseUrl("http://www.baidu.com")
                        .build());
    }
}
