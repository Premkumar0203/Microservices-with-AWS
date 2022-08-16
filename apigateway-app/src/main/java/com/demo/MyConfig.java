 package com.demo;

 import java.util.Arrays;

 import java.util.Collections;

 import org.springframework.context.annotation.Bean;

 import org.springframework.context.annotation.Configuration;

 import org.springframework.web.cors.CorsConfiguration;

 import org.springframework.web.cors.reactive.CorsWebFilter;

 import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

 @Configuration

 public class MyConfig {
	 
 @Bean

 public CorsWebFilter corsWebFilter() {

		 final CorsConfiguration corsConfig = new CorsConfiguration();
		 corsConfig.setAllowedOrigins(Collections.singletonList("*"));
		 corsConfig.setMaxAge(3600L);
		 corsConfig.setAllowedMethods(Arrays.asList("GET", "POST"));
		 corsConfig.addAllowedHeader("*");
		 final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		 source.registerCorsConfiguration("/**", corsConfig);
		 return new CorsWebFilter(source);

 }

 }