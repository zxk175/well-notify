package com.zxk175.notify.core.util.serializer;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * @author zxk175
 * @since 2020-03-29 13:44
 */
public class FstRedisSerializer implements RedisSerializer<Object> {
	
	@Override
	public byte[] serialize(Object t) throws SerializationException {
		if (ObjectUtil.isNull(t)) {
			return new byte[0];
		}
		return SerializationUtil.serialize(t);
	}
	
	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null || bytes.length <= 0) {
			return null;
		}
		
		return SerializationUtil.deserialize(bytes);
	}
	
}
