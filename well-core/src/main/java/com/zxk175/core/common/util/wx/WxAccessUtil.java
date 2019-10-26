package com.zxk175.core.common.util.wx;

import com.alibaba.fastjson.JSONObject;
import com.zxk175.core.common.config.WxConfig;
import com.zxk175.core.common.tuple.Tuple2;
import com.zxk175.core.common.tuple.Tuples;
import com.zxk175.core.common.util.MyStrUtil;
import com.zxk175.core.common.util.net.OkHttpUtil;
import com.zxk175.core.common.util.redis.StringRedisUtil;
import com.zxk175.core.common.util.spring.SpringActiveUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zxk175
 * @since 2019/05/17 21:11
 */
@Slf4j
@Component
@AllArgsConstructor
public class WxAccessUtil {

    private StringRedisUtil stringRedisUtil;


    public String getGlobalToken() {
        boolean isTest = SpringActiveUtil.isDebug();
        if (isTest) {
            JSONObject result = OkHttpUtil.instance().get2Obj("http://well.zxk175.com/well/api/wx_global_token/v1");
            return result.getString("data");
        }

        Tuple2<String, String> tuple = getAppIdAndSecret();
        return getGlobalTokenCommon(WxConfig.GLOBAL_TOKEN_KEY, tuple.first, tuple.second);
    }

    private String getGlobalTokenCommon(String key, String appId, String appSecret) {
        String accessToken = stringRedisUtil.get(key);

        if (MyStrUtil.isBlank(accessToken)) {
            String url = MyStrUtil.format(WxConfig.GLOBAL_TOKEN_URL, appId, appSecret);

            // 调用接口
            JSONObject result = OkHttpUtil.instance().get2Obj(url);

            // 如果请求成功
            final String resultKey = WxConfig.ACCESS_TOKEN_KEY;
            if (result.containsKey(resultKey)) {
                accessToken = result.getString(resultKey);

                stringRedisUtil.set(key, accessToken, WxConfig.ACCESS_TOKEN_TTL);
            }
        }

        return accessToken;
    }

    private Tuple2<String, String> getAppIdAndSecret() {
        return Tuples.tuple(WxConfig.MP_APP_ID, WxConfig.MP_APP_SECRET);
    }
}
