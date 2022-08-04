package com.rooke.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.rooke.LoggerInterceptor;

@Configuration
public class WebMvmConfig implements WebMvcConfigurer {
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoggerInterceptor()).excludePathPatterns("/css/**", "/fonts/**",
        "/plugin/**", "/scripts/**");
  }
}
