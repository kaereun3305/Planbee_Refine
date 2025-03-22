package com.pj.planbee.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
        .addPathPatterns("/**") // 모든 요청 가로채기
        .excludePathPatterns(
            "/auth/**",
            "/css/**", "/js/**", "/images/**", "/favicon.ico" // 정적 리소스들
        );
    }
}
