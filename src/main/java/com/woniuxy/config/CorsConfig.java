package com.woniuxy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
            	.allowedOrigins("*")
                .allowCredentials(true)
        		.maxAge(3600);
        //跨域发送复杂请求时，会先发送一次options请求进行预检，这里3600表示预检通过后3600s内不再进行预检。
    }
}
