package com.zxk175.notify.config.web;

import com.zxk175.notify.config.interceptor.RequestDelayInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zxk175
 * @since 2019-10-12 16:22
 */
@Configuration
public class MySpringMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 限制请求频率拦截器
        registry.addInterceptor(new RequestDelayInterceptor()).addPathPatterns("/**");
    }
}
