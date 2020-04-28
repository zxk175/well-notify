package com.zxk175.notify.module.service.wx;

import com.zxk175.notify.core.http.ResponseExt;
import com.zxk175.notify.module.bean.param.wx.DeviceNotifyParam;

/**
 * <p>
 * 微信用户表 服务类
 * </p>
 *
 * @author zxk175
 * @since 2019-11-27 15:35
 */
public interface IWxTemplateMsgService {
	
	ResponseExt<Object, ?> send(DeviceNotifyParam param);
	
}
