package com.zxk175.notify.core.util.wx.bean.notify;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zxk175
 * @since 2020-03-29 13:43
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceNotifyData extends NotifyBaseData {

    private String channelName;

}
