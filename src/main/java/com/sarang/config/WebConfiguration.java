package com.sarang.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer{

    //인터셉터 설정 
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new CertificationInterceptor())
        .excludePathPatterns("/css/**","/images/**","/asset/**","/error/**");
	}
}