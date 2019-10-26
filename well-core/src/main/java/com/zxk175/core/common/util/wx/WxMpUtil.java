package com.zxk175.core.common.util.wx;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.zxk175.core.common.bean.dto.wx.WxMpUserDTO;
import com.zxk175.core.common.config.WxConfig;
import com.zxk175.core.common.constant.Const;
import com.zxk175.core.common.tuple.Tuple2;
import com.zxk175.core.common.tuple.Tuples;
import com.zxk175.core.common.util.MyStrUtil;
import com.zxk175.core.common.util.net.OkHttpUtil;

/**
 * @author zxk175
 * @since 2019/05/17 21:47
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

    public static WxMpUserDTO getUserInfo(String accessToken, String openId) {
        String url = MyStrUtil.format(WxConfig.USER_INFO, accessToken, openId);
        JSONObject data = OkHttpUtil.instance().get2Obj(url);
        return data.toJavaObject(WxMpUserDTO.class);
    }
}
