package com.zxk175.notify.core.util.net;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.zxk175.notify.core.util.MyStrUtil;
import com.zxk175.notify.core.util.json.FastJsonUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zxk175
 * @since 2020-03-29 13:48
 */
@Slf4j
public class OkHttpUtil {
	
	private final OkHttpClient okHttpClient;
	/**
	 * 会被多线程使用，所以使用关键字volatile
	 */
	private volatile static OkHttpUtil okHttpUtil;
	
	
	/**
	 * 私有化构造方法
	 */
	private OkHttpUtil() {
		okHttpClient = new OkHttpClient.Builder()
				.connectTimeout(15, TimeUnit.SECONDS)
				.writeTimeout(20, TimeUnit.SECONDS)
				.readTimeout(20, TimeUnit.SECONDS)
				.build();
	}
	
	/**
	 * 单例模式，全局1个OkHttpUtil对象
	 */
	public static OkHttpUtil instance() {
		if (okHttpUtil == null) {
			synchronized (OkHttpUtil.class) {
				if (okHttpUtil == null) {
					okHttpUtil = new OkHttpUtil();
				}
			}
		}
		
		return okHttpUtil;
	}
	
	public JSONObject get2Obj(String url) {
		String result = getCommon(url, new HashMap<>(8));
		
		return FastJsonUtil.toObject(result, JSONObject.class);
	}
	
	private String getCommon(String url, Map<String, String> param) {
		// 请求参数
		HttpUrl.Builder builder = HttpUrl.get(url).newBuilder();
		for (Map.Entry<String, String> entry : param.entrySet()) {
			builder.addQueryParameter(entry.getKey(), entry.getValue());
		}
		
		// 请求创建
		Request request = new Request.Builder()
				.url(builder.build().toString())
				.build();
		
		return executeRequest(request);
	}
	
	public String postJson(String url, String param) {
		// 请求参数
		MediaType mediaType = MediaType.Companion.parse("application/json");
		RequestBody body = RequestBody.Companion.create(param, mediaType);
		
		// 请求创建
		Request request = new Request.Builder()
				.url(url)
				.post(body)
				.build();
		
		return executeRequest(request);
	}
	
	public JSONObject postJson2Obj(String url, String param) {
		return FastJsonUtil.toObject(postJson(url, param), JSONObject.class);
	}
	
	private String executeRequest(Request request) {
		Response response = null;
		try {
			response = okHttpClient.newCall(request).execute();
			log.info("responseMessage：{}", response.message());
			
			ResponseBody responseBody = response.body();
			if (ObjectUtil.isNull(responseBody)) {
				return MyStrUtil.EMPTY;
			}
			
			return responseBody.string();
		} catch (Exception ex) {
			log.error("executeRequest异常", ex);
			return MyStrUtil.EMPTY;
		} finally {
			if (ObjectUtil.isNotNull(response)) {
				response.close();
			}
		}
	}
	
}