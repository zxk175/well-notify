package com.zxk175.core.common.aspect.redis;

import com.zxk175.core.common.constant.Const;
import com.zxk175.core.common.http.Response;
import com.zxk175.core.common.util.redis.StringRedisUtil;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author zxk175
 * @since 2019/05/18 09:49
 */
@Aspect
@Component
@Order(9996)
@AllArgsConstructor
public class RedisCleanAop {

    private StringRedisUtil stringRedisUtil;


    @Around(value = "@annotation(com.zxk175.core.common.annotation.redis.RedisClean)")
    public Object after(ProceedingJoinPoint joinPoint) throws Throwable {
        Response response = (Response) joinPoint.proceed();
        if (response.getSuccess()) {
            boolean ok = stringRedisUtil.flushRedis();

            if (ok) {
                return Response.ok(Const.OK_CODE, "Redis缓存清除成功");
            }

            return Response.ok(Const.FAIL_CODE, "Redis缓存清除失败");
        }

        return response;
    }
}