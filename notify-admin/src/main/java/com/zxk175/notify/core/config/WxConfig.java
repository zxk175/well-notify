package com.zxk175.notify.core.config;

/**
 * @author zxk175
 * @since 2019-11-27 15:08
 */
public class WxConfig {
	
	public static final Long ACCESS_TOKEN_TTL = 6800L;
	public static final String MP_APP_ID = "wx1741314f7734b373";
	public static final String MP_APP_SECRET = "7f68567361647694b24e4b472ea524cd";
	
	public static final String OPEN_ID = "ooPE56M2H3HOuAiilxOGqu3CM-hE";
	public static final String DEVICE_ID = "NJSIb4pNKvg8cMXBzFHbcSjg271TV65mZdKKeFLDRTA";
	
	public static String WX_MP_TOKEN = "well666";
	public static String ACCESS_TOKEN_KEY = "access_token";
	public static String GLOBAL_TOKEN_KEY = "mp_global_token";
	
	
	private static final String BASE_URL = "https://api.weixin.qq.com";
	
	/**
	 * 获取全局token
	 */
	public static String GLOBAL_TOKEN_URL = BASE_URL + "/cgi-bin/token?grant_type=client_credential&appid={}&secret={}";
	/**
	 * 发送模板消息
	 */
	public static String SEND_TEMPLATE_MSG = BASE_URL + "/cgi-bin/message/template/send?access_token={}";
	/**
	 * 获取粉丝基本信息
	 */
	public static String USER_INFO = BASE_URL + "/cgi-bin/user/info?access_token={}&openid={}&lang=zh_CN";
	/**
	 * 获取公众号粉丝
	 */
	public static String USER_LIST = BASE_URL + "/cgi-bin/user/get?access_token={}&next_openid={}";
	
}
