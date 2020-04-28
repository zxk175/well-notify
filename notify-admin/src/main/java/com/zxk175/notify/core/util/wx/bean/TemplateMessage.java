package com.zxk175.notify.core.util.wx.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Map;

/**
 * @author zxk175
 * @since 2020-03-29 13:21
 */
@Data
public class TemplateMessage {
	
	/**
	 * 用户openId
	 */
	@JSONField(name = "touser")
	private String toUser;
	
	/**
	 * 模板消息Id
	 */
	@JSONField(name = "template_id")
	private String templateId;
	
	/**
	 * URL置空，在发送后，点模板消息进入一个空白页面（ios），或无法点击（android）
	 */
	@JSONField(name = "url")
	private String url;
	
	/**
	 * 标题颜色
	 */
	@JSONField(name = "topcolor")
	private String topColor;
	
	/**
	 * 模板详细信息
	 */
	@JSONField(name = "data")
	private Map<String, TemplateData> templateData;
	
}
