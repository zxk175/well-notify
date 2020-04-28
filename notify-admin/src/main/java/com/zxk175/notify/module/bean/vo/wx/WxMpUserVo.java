package com.zxk175.notify.module.bean.vo.wx;

import lombok.Data;

/**
 * @author zxk175
 * @since 2020-03-29 13:21
 */
@Data
public class WxMpUserVo {
	
	/**
	 * 用户的标识
	 */
	private String openId;
	
	/**
	 * 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
	 */
	private Integer subscribe;
	
	/**
	 * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	 */
	private Long subscribetime;
	
	/**
	 * 昵称
	 */
	private String nickname;
	
	/**
	 * 用户的性别（1是男性，2是女性，0是未知）
	 */
	private Integer sex;
	
	/**
	 * 用户所在国家
	 */
	private String country;
	
	/**
	 * 用户所在省份
	 */
	private String province;
	
	/**
	 * 用户所在城市
	 */
	private String city;
	
	/**
	 * 用户的语言，简体中文为zh_CN
	 */
	private String language;
	
	/**
	 * 用户头像
	 */
	private String headimgurl;
	
	private String remark;
	
	private Integer groupid;
	
}
