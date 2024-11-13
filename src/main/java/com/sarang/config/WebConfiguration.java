package com.sarang.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;

@Configuration
public class WebConfiguration implements WebMvcConfigurer{

    //인터셉터 설정 
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry){
        registry.addInterceptor(new CertificationInterceptor())
        .excludePathPatterns("/css/**","/images/**","/asset/**","/error/**");
	}

    //lucy
    // @Bean
    // public FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean() {
    //     FilterRegistrationBean<XssEscapeServletFilter> filterRegistration = new FilterRegistrationBean<>();
    //     filterRegistration.setFilter(new XssEscapeServletFilter());
    //     filterRegistration.setOrder(1);
    //     filterRegistration.addUrlPatterns("/*");

    //     return filterRegistration;
    // }
    //Json XSS
    @Bean
    public MappingJackson2HttpMessageConverter jsonEscapeConverter() {
        // MappingJackson2HttpMessageConverter Default ObjectMapper 설정 및 ObjectMapper Config 설정
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        objectMapper.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }
}