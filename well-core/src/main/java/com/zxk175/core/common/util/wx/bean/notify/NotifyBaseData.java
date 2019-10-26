package com.zxk175.core.common.util.wx.bean.notify;

import lombok.Data;

/**
 * @author zxk175
 * @since 2018/4/11 10:11
 */
@Data
public class NotifyBaseData {

    private String url;
    
    private String first;

    private String remark;

    private String openId;

    private String templateId;
}
