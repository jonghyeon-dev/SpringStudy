package com.sarang.config;

// import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
// import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer{
    // @Value("${spring.servlet.multipart.location}")
    // String filePath;

    //인터셉터 설정 
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry){
        registry.addInterceptor(new CertificationInterceptor())
        .excludePathPatterns("/css/**","/images/**","/asset/**","/error/**");
	}

    // @Override
    // public void addResourceHandlers(ResourceHandlerRegistry registry){
    //     registry.addResourceHandler("/insertBoard**").addResourceLocations("file:"+filePath);
    // }
}