package com.zxk175.notify.config.mbp;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author zxk175
 * @since 2020-03-29 13:50
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
	
	@Override
	public void insertFill(MetaObject metaObject) {
		LocalDateTime now = LocalDateTime.now();
		setMetaValue(metaObject, "createTime", now);
		setMetaValue(metaObject, "updateTime", now);
	}
	
	@Override
	public void updateFill(MetaObject metaObject) {
		setMetaValue(metaObject, "updateTime", LocalDateTime.now());
	}
	
	private void setMetaValue(MetaObject metaObject, String key, Object value) {
		setFieldValByName(key, value, metaObject);
	}
	
}
