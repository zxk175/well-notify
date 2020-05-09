package com.zxk175.notify.module.service.notify;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxk175.notify.core.http.Response;
import com.zxk175.notify.core.http.ResponseExt;
import com.zxk175.notify.module.bean.param.notify.NotifyMsgListParam;
import com.zxk175.notify.module.bean.param.notify.NotifyMsgRemoveParam;
import com.zxk175.notify.module.bean.vo.PageBeanVo;
import com.zxk175.notify.module.pojo.notify.NotifyMsg;

import java.util.Collection;

/**
 * <p>
 * 消息表 服务类
 * </p>
 *
 * @author zxk175
 * @since 2019-11-27 15:35
 */
public interface INotifyMsgService extends IService<NotifyMsg> {
	
	Response<Object> removeNotifyMsg(NotifyMsgRemoveParam param);
	
	ResponseExt<Collection<?>, PageBeanVo> listNotifyMsgPage(NotifyMsgListParam param);
	
	Response<Object> infoNotifyMsg(String msgId);
	
}
