package com.zxk175.notify.module.service.notify;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxk175.notify.core.http.Response;
import com.zxk175.notify.core.http.ResponseExt;
import com.zxk175.notify.module.bean.param.notify.NotifyChannelInfoParam;
import com.zxk175.notify.module.bean.param.notify.NotifyChannelListParam;
import com.zxk175.notify.module.bean.param.notify.NotifyChannelRemoveParam;
import com.zxk175.notify.module.bean.vo.PageBeanVo;
import com.zxk175.notify.module.pojo.notify.NotifyChannel;

import java.util.Collection;

/**
 * <p>
 * 通道表 服务类
 * </p>
 *
 * @author zxk175
 * @since 2019-11-27 15:35
 */
public interface INotifyChannelService extends IService<NotifyChannel> {
	
	Response<Object> saveNotifyChannel(NotifyChannel param);
	
	Response<Object> removeNotifyChannel(NotifyChannelRemoveParam param);
	
	Response<Object> modifyNotifyChannel(NotifyChannel param);
	
	ResponseExt<Collection<?>, PageBeanVo> listNotifyChannelPage(NotifyChannelListParam param);
	
	Response<Collection<?>> listSelectNotifyChannel();
	
	Response<Object> infoNotifyChannel(NotifyChannelInfoParam param);
	
}
