package com.zxk175.core.common.util.message;

import com.google.common.collect.Maps;
import com.zxk175.core.common.constant.Const;
import com.zxk175.core.common.util.ThreadUtil;
import com.zxk175.core.common.util.json.FastJsonUtil;
import com.zxk175.core.common.util.net.OkHttpUtil;
import com.zxk175.core.common.util.spring.SpringActiveUtil;

import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @author zxk175
 * @since 2019/04/13 15:52
 */
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
                ex.printStackTrace();
            }
        });

        singleThreadPool.shutdown();
    }
}
