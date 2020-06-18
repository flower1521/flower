package com.trans.actional.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by lenovo on 2019/11/29.
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new InterceptorConfig())
//                .addPathPatterns("/hello/**");
//                .addPathPatterns("/testBoot/**");
    }
}
