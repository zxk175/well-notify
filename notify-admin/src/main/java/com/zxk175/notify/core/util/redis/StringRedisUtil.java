package com.zxk175.notify.core.util.redis;

import cn.hutool.core.convert.Convert;
import com.zxk175.notify.core.constant.Const;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author zxk175
 * @since 2020-03-29 13:44
 */
@Component
@AllArgsConstructor
public class StringRedisUtil {

    private StringRedisTemplate stringRedisTemplate;


    public boolean flushRedis() {
        final Object execute = stringRedisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.flushAll();
            return Const.SUCCESS;
        });

        return Const.SUCCESS.equals(Convert.toStr(execute));
    }

    public void set(final String key, final String value, final Long expire) {
        stringRedisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
    }

    public String get(final String key) {
        return stringRedisTemplate.boundValueOps(key).get();
    }

    public Long increment(final String key, final Long val) {
        return stringRedisTemplate.boundValueOps(key).increment(val);
    }

    public void expire(final String key, final Long time, final TimeUnit timeUnit) {
        stringRedisTemplate.expire(key, time, timeUnit);
    }

}
