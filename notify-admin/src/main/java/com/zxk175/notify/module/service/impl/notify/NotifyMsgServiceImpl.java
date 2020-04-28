package com.zxk175.notify.module.service.impl.notify;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.core.constant.enums.StateType;
import com.zxk175.notify.core.http.ResponseExt;
import com.zxk175.notify.module.dao.notify.NotifyMsgDao;
import com.zxk175.notify.module.pojo.notify.NotifyMsg;
import com.zxk175.notify.module.service.notify.INotifyMsgService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author zxk175
 * @since 2019-11-27 15:35
 */
@Service
public class NotifyMsgServiceImpl extends ServiceImpl<NotifyMsgDao, NotifyMsg> implements INotifyMsgService {
	
	@Override
	public ResponseExt<Object, ?> infoNotifyMsg(String msgId) {
		QueryWrapper<NotifyMsg> notifyMsgQw = new QueryWrapper<>();
		notifyMsgQw.select("title, content");
		notifyMsgQw.eq(Const.DB_STATE, StateType.SHOW.value());
		notifyMsgQw.eq("id", msgId);
		Map<String, Object> notifyMsg = this.getMap(notifyMsgQw);
		return ResponseExt.objectReturn(notifyMsg);
	}
	
}
