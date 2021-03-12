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
                //pathmapper所有请求拦截的路径，/是拦截所有请求
                .pathMapping("/")
                //
                .select()
                //扫描那个路径接口的包的注解生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.woniuxy.controller"))
                .paths(PathSelectors.any())
                .build()
                //生成接口文档应该配置的东西
                .apiInfo(new ApiInfoBuilder()
                        //标题
                        .title("招聘网站，爱干不干")
                        //描述
                        .description("辉煌招聘，个个都是人才")
                        //版本
                        .version("1.0")
                        //联系人信息
                        .contact(new Contact("有事百度无事退朝","http://www.baidu.com","guyi4396@163.com"))
                        //项目主页
                        .license("项目主页")
                        .licenseUrl("http://www.baidu.com")
                        .build());
    }
}
