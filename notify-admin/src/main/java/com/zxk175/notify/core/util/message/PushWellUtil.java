package com.zxk175.notify.core.util.message;

import com.google.common.collect.Maps;
import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.core.util.ThreadUtil;
import com.zxk175.notify.core.util.json.FastJsonUtil;
import com.zxk175.notify.core.util.net.OkHttpUtil;
import com.zxk175.notify.core.util.spring.SpringActiveUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @author zxk175
 * @since 2020-03-29 13:47
 */
@Slf4j
public class PushWellUtil {
	
	public static void sendNotify(String title, String msg) {
		sendCommon(title, msg);
	}
	
	private static void sendCommon(String title, String msg) {
		ExecutorService singleThreadPool = ThreadUtil.newExecutor(5, "notify");
		
		singleThreadPool.execute(() -> {
			String chineseStr = SpringActiveUtil.chineseStr();
			try {
				Map<String, String> params = Maps.newHashMap();
				params.put("title", chineseStr + "=" + title);
				params.put("content", msg);
				params.put("sendKey", Const.MSG_KEY);
				
				OkHttpUtil.instance().postJson(Const.WE_CHAT_MSG_URL, FastJsonUtil.jsonStr(params));
			} catch (Exception ex) {
				log.error("异常推送异常", ex);
			}
		});
		
		singleThreadPool.shutdown();
	}
	
}
