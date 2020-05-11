package com.zxk175.notify.module.service.notify;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxk175.notify.core.http.Response;
import com.zxk175.notify.core.http.ResponseExt;
import com.zxk175.notify.module.bean.param.notify.NotifyChannelUserInfoParam;
import com.zxk175.notify.module.bean.param.notify.NotifyChannelUserListParam;
import com.zxk175.notify.module.bean.param.notify.NotifyChannelUserRemoveParam;
import com.zxk175.notify.module.bean.vo.PageBeanVo;
import com.zxk175.notify.module.pojo.notify.NotifyChannelUser;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 通道用户表 服务类
 * </p>
 *
 * @author zxk175
 * @since 2019-11-27 15:35
 */
public interface INotifyChannelUserService extends IService<NotifyChannelUser> {
	
	Response<Object> saveNotifyChannelUser(NotifyChannelUser param);
	
	Response<Object> removeNotifyChannelUser(NotifyChannelUserRemoveParam param);
	
	Response<Object> modifyNotifyChannelUser(NotifyChannelUser param);
	
	ResponseExt<Collection<?>, PageBeanVo> listNotifyChannelUserPage(NotifyChannelUserListParam param);
	
	Response<Object> infoNotifyChannelUser(NotifyChannelUserInfoParam param);
	
	List<NotifyChannelUser> notifyChannelUsers(Long channelId);
	
}
