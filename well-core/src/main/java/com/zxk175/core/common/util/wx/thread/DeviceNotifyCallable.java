package com.zxk175.core.common.util.wx.thread;

import com.zxk175.core.common.tuple.Tuple2;
import com.zxk175.core.common.util.wx.WxTemplateMsgUtil;
import com.zxk175.core.common.util.wx.bean.TemplateMessage;
import com.zxk175.core.common.util.wx.bean.notify.DeviceNotifyData;

import java.util.concurrent.Callable;

/**
 * @author zxk175
 * @since 2019/05/18 15:48
 */
public class DeviceNotifyCallable implements Callable<Tuple2<String, String>> {

    private DeviceNotifyData deviceNotifyData;
    private WxTemplateMsgUtil wxTemplateMsgUtil;


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
