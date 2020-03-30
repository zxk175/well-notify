package com.zxk175.notify.module.controller.wx;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.zxk175.notify.core.config.WxConfig;
import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.core.http.ResponseExt;
import com.zxk175.notify.core.util.DateUtil;
import com.zxk175.notify.core.util.MyStrUtil;
import com.zxk175.notify.core.util.common.CommonUtil;
import com.zxk175.notify.core.util.net.OkHttpUtil;
import com.zxk175.notify.core.util.wx.WxAccessUtil;
import com.zxk175.notify.core.util.wx.WxMpUtil;
import com.zxk175.notify.module.bean.param.wx.MpUserListParam;
import com.zxk175.notify.module.bean.vo.PageBeanVo;
import com.zxk175.notify.module.bean.vo.wx.WxMpUserListVo;
import com.zxk175.notify.module.bean.vo.wx.WxMpUserVo;
import com.zxk175.notify.module.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信公众号粉丝 前端控制器
 *
 * @author zxk175
 * @since 2020-03-30 09:14
 */
@Controller
@AllArgsConstructor
@RequestMapping(Const.BASE_URL + "/wx-mp/user")
@Api(tags = "微信公众号粉丝")
public class WxMpUserController extends BaseController {

    private WxAccessUtil wxAccessUtil;


    @ResponseBody
    @PostMapping(value = "/list/v1", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "微信公众号粉丝列表", notes = "微信公众号粉丝列表")
    public ResponseExt<Collection<?>, PageBeanVo> wxAuthGet(@Validated @RequestBody MpUserListParam param) throws Exception {
        CommonUtil.buildPageParam(param);

        String accessToken = wxAccessUtil.getGlobalToken();
        String tmpUrl = MyStrUtil.format(WxConfig.USER_LIST, accessToken, "");
        JSONObject data = OkHttpUtil.instance().get2Obj(tmpUrl);

        List<Map<String, Object>> dataList = Lists.newArrayList();
        Map<String, Object> map;
        WxMpUserListVo wxMpUserListVo = data.toJavaObject(WxMpUserListVo.class);
        for (String openId : wxMpUserListVo.getData().getOpenid()) {
            map = new HashMap<>(8);

            WxMpUserVo userInfo = WxMpUtil.getUserInfo(accessToken, openId);
            map.put("openId", openId);
            map.put("avatar", userInfo.getHeadimgurl());
            map.put("nickName", userInfo.getNickname());
            map.put("subscribe", userInfo.getSubscribe());
            map.put("subscribeTime", DateUtil.formatDate(userInfo.getSubscribetime(), ""));

            dataList.add(map);
        }

        return ResponseExt.putPageExtraFalse(dataList, wxMpUserListVo.getTotal(), param);
    }

}
