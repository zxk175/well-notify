package com.zxk175.notify.core.util.wx;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.zxk175.notify.core.config.WxConfig;
import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.core.tuple.Tuple2;
import com.zxk175.notify.core.tuple.Tuples;
import com.zxk175.notify.core.util.MyStrUtil;
import com.zxk175.notify.core.util.net.OkHttpUtil;
import com.zxk175.notify.module.bean.vo.wx.WxMpUserVo;

/**
 * @author zxk175
 * @since 2020-03-29 13:43
 */
public class WxMpUtil {

    static Tuple2<String, String> judgeWxReturn(JSONObject result) {
        if (CollUtil.isNotEmpty(result)) {
            return Tuples.tuple(result.getString("errcode"), result.getString("errmsg"));
        }

        throw new RuntimeException("");
    }

    public static boolean judgeWxReturnWithTrue(JSONObject result) {
        Tuple2<String, String> tuple = judgeWxReturn(result);

        return MyStrUtil.eq(tuple.first, Const.ZERO_STR);
    }

    public static WxMpUserVo getUserInfo(String accessToken, String openId) {
        String url = MyStrUtil.format(WxConfig.USER_INFO, accessToken, openId);
        JSONObject data = OkHttpUtil.instance().get2Obj(url);
        return data.toJavaObject(WxMpUserVo.class);
    }

}
