package com.zxk175.core.common.util.wx.bean.notify;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zxk175
 * @since 2019/05/18 10:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceNotifyData extends NotifyBaseData {

    private String channelName;
}
