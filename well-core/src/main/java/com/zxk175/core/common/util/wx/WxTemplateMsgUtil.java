package com.zxk175.core.common.util.wx;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.zxk175.core.common.config.WxConfig;
import com.zxk175.core.common.tuple.Tuple2;
import com.zxk175.core.common.util.DateUtil;
import com.zxk175.core.common.util.MyStrUtil;
import com.zxk175.core.common.util.json.FastJsonUtil;
import com.zxk175.core.common.util.net.OkHttpUtil;
import com.zxk175.core.common.util.wx.bean.TemplateData;
import com.zxk175.core.common.util.wx.bean.TemplateMessage;
import com.zxk175.core.common.util.wx.bean.notify.DeviceNotifyData;
import com.zxk175.core.common.util.wx.bean.notify.NotifyBaseData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zxk175
 * @since 2019/05/17 21:47
 */
@Component
@AllArgsConstructor
public class WxTemplateMsgUtil {

    private WxAccessUtil wxAccessUtil;


    public TemplateMessage buildDeviceNotifyData(DeviceNotifyData deviceNotifyData) {
        TemplateMessage templateMessage = new TemplateMessage();

        Map<String, TemplateData> tmpData = buildTmpData(templateMessage, deviceNotifyData);
        TemplateData first = new TemplateData("#173177", deviceNotifyData.getChannelName());
        tmpData.put("channel", first);

        templateMessage.setTemplateData(tmpData);

        return templateMessage;
    }

    private Map<String, TemplateData> buildTmpData(TemplateMessage templateMessage, NotifyBaseData notifyBaseData) {
        templateMessage.setTopColor("#00FF00");
        templateMessage.setUrl(notifyBaseData.getUrl());
        templateMessage.setToUser(notifyBaseData.getOpenId());
        templateMessage.setTemplateId(notifyBaseData.getTemplateId());

        Map<String, TemplateData> tmpData = Maps.newHashMap();
        TemplateData first = new TemplateData("#000000", notifyBaseData.getFirst());
        tmpData.put("first", first);

        first = new TemplateData("#000000", DateUtil.now("") + "\n");
        tmpData.put("date", first);

        String remark = notifyBaseData.getRemark();
        first = new TemplateData("#173177", MyStrUtil.isBlank(remark) ? "☞点击查看通知详情" : remark);
        tmpData.put("remark", first);

        return tmpData;
    }

    public Tuple2<String, String> sendWxTemplateMsg(TemplateMessage tmpMessage) {
        String accessToken = wxAccessUtil.getGlobalToken();

        String tmpUrl = MyStrUtil.format(WxConfig.SEND_TEMPLATE_MSG, accessToken);

        JSONObject result = OkHttpUtil.instance().postJson2Obj(tmpUrl, FastJsonUtil.jsonStrByMy(tmpMessage));

        return WxMpUtil.judgeWxReturn(result);
    }
}
