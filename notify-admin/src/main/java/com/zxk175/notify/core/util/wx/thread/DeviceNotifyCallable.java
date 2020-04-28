package com.zxk175.notify.core.util.wx.thread;

import com.zxk175.notify.core.tuple.Tuple2;
import com.zxk175.notify.core.util.wx.WxTemplateMsgUtil;
import com.zxk175.notify.core.util.wx.bean.TemplateMessage;
import com.zxk175.notify.core.util.wx.bean.notify.DeviceNotifyData;

import java.util.concurrent.Callable;

/**
 * @author zxk175
 * @since 2020-03-29 13:43
 */
public class DeviceNotifyCallable implements Callable<Tuple2<String, String>> {
	
	private final DeviceNotifyData deviceNotifyData;
	private final WxTemplateMsgUtil wxTemplateMsgUtil;
	
	
	public DeviceNotifyCallable(DeviceNotifyData deviceNotifyData, WxTemplateMsgUtil wxTemplateMsgUtil) {
		this.deviceNotifyData = deviceNotifyData;
		this.wxTemplateMsgUtil = wxTemplateMsgUtil;
	}
	
	@Override
	public Tuple2<String, String> call() {
		TemplateMessage templateMessage = wxTemplateMsgUtil.buildDeviceNotifyData(deviceNotifyData);
		
		return wxTemplateMsgUtil.sendWxTemplateMsg(templateMessage);
	}
	
}
