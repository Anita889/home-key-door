package com.example.homekeydoor.security;

import com.example.homekeydoor.exceptions.RestMappingExceptionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RestExceptionsConfig {
	@Bean
	public RestMappingExceptionResolver restMappingExceptionResolver() {
		RestMappingExceptionResolver resolver = new RestMappingExceptionResolver();
		resolver.setOrder(0);
		return resolver;
	}
}
