package com.zxk175.notify.core.annotation.request;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zxk175
 * @since 2019-11-27 15:15
 */
// 最高优先级
@Order(Ordered.HIGHEST_PRECEDENCE)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RequestLimit {
	
	// 允许访问的次数，默认值MAX_VALUE
	int count() default Integer.MAX_VALUE;
	
	// 时间段，单位为秒，默认值1分钟
	long time() default 60;
	
}

