package com.zxk175.notify.config.filter;

import com.zxk175.notify.config.filter.request.RepeatedlyReadFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

/**
 * Filter配置
 * 根据order值的大小，从小到大的顺序依次过滤
 *
 * @author zxk175
 * @since 2019-10-12 16:19
 */
@Configuration
public class SystemFilterConfig {
	
	@Bean
	public FilterRegistrationBean<Filter> repeatedlyReadFilterRegistration() {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
		
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		registration.setFilter(new RepeatedlyReadFilter());
		registration.setName("RepeatedlyReadFilter");
		registration.addUrlPatterns("/*");
		registration.setEnabled(true);
		registration.setOrder(1);
		
		return registration;
	}
	
}
