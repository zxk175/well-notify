package com.zxk175.notify.module.service.notify;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxk175.notify.core.http.ResponseExt;
import com.zxk175.notify.module.pojo.notify.NotifyMsg;

/**
 * <p>
 * 消息表 服务类
 * </p>
 *
 * @author zxk175
 * @since 2019-11-27 15:35
 */
public interface INotifyMsgService extends IService<NotifyMsg> {
	
	ResponseExt<Object, ?> infoNotifyMsg(String msgId);
	
}
