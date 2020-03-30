package com.zxk175.notify.core.aspect.request;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.zxk175.notify.core.annotation.request.RequestLimit;
import com.zxk175.notify.core.exception.RequestLimitException;
import com.zxk175.notify.core.util.common.CommonUtil;
import com.zxk175.notify.core.util.net.IpUtil;
import com.zxk175.notify.core.util.net.RequestUtil;
import com.zxk175.notify.core.util.redis.StringRedisUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author zxk175
 * @since 2020-03-29 13:52
 */
@Log4j2
@Aspect
@Order(3)
@Component
@AllArgsConstructor
public class RequestLimitAop {

    private StringRedisUtil stringRedisUtil;


    @Before("within(@org.springframework.stereotype.Controller *) && @annotation(limit)")
    public void doBefore(RequestLimit limit) {
        HttpServletRequest request = RequestUtil.request();
        if (ObjectUtil.isNull(request)) {
            throw new NullPointerException("未获取到HttpServletRequest对象");
        }

        String ip = IpUtil.getClientIp(request);
        String url = Convert.toStr(request.getRequestURL());
        // 获取请求限制Key
        String key = CommonUtil.requestLimitKey(url, ip);
        long count = stringRedisUtil.increment(key, 1L);
        // 第一次调用
        if (count == 1) {
            stringRedisUtil.expire(key, limit.time(), TimeUnit.SECONDS);
        }

        // 超过限制次数
        long limitCount = limit.count();
        if (count > limitCount) {
            log.error("用户IP[{}]访问地址[{}]超过了限定的次数[{}]", ip, url, limitCount);
            throw new RequestLimitException();
        }
    }
    
}
