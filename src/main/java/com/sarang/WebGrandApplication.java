package com.sarang;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class WebGrandApplication extends SpringBootServletInitializer{

	@Value("${spring.servlet.multipart.location}")
	String filePath;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(WebGrandApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(WebGrandApplication.class, args);
	}

}
