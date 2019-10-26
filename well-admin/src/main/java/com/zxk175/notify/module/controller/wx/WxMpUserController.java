package com.zxk175.notify.module.controller.wx;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zxk175.core.common.bean.dto.wx.WxMpUserDTO;
import com.zxk175.core.common.bean.dto.wx.WxMpUserListDTO;
import com.zxk175.core.common.bean.param.wx.MpUserListParam;
import com.zxk175.core.common.config.WxConfig;
import com.zxk175.core.common.http.Response;
import com.zxk175.core.common.util.DateUtil;
import com.zxk175.core.common.util.MyStrUtil;
import com.zxk175.core.common.util.common.CommonUtil;
import com.zxk175.core.common.util.net.OkHttpUtil;
import com.zxk175.core.common.util.wx.WxAccessUtil;
import com.zxk175.core.common.util.wx.WxMpUtil;
import com.zxk175.core.module.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 微信公众号粉丝 前端控制器
 * </p>
 *
 * @author zxk175
 * @since 2019-10-12 16:21
 */
@Controller
@AllArgsConstructor
@RequestMapping("/wx/mp/user")
@Api(tags = "WxMpUser", description = "微信公众号粉丝")
public class WxMpUserController extends BaseController {

    private WxAccessUtil wxAccessUtil;


    @ResponseBody
    @PostMapping(value = "/list/v1")
    @ApiOperation(value = "微信公众号粉丝列表", notes = "微信公众号粉丝列表")
    public Response wxAuthGet(@Validated @RequestBody MpUserListParam param) {
        CommonUtil.buildPageParam(param);

        String accessToken = wxAccessUtil.getGlobalToken();
        String tmpUrl = MyStrUtil.format(WxConfig.USER_LIST, accessToken, "");
        JSONObject data = OkHttpUtil.instance().get2Obj(tmpUrl);

        List<Map<String, Object>> dataList = Lists.newArrayList();
        Map<String, Object> map;
        WxMpUserListDTO wxMpUserListDTO = data.toJavaObject(WxMpUserListDTO.class);
        for (String openId : wxMpUserListDTO.getData().getOpenid()) {
            map = Maps.newHashMap();

            WxMpUserDTO userInfo = WxMpUtil.getUserInfo(accessToken, openId);
            map.put("openId", openId);
            map.put("avatar", userInfo.getHeadimgurl());
            map.put("nickName", userInfo.getNickname());
            map.put("subscribe", userInfo.getSubscribe());
            map.put("subscribeTime", DateUtil.formatDate(userInfo.getSubscribetime(), ""));

            dataList.add(map);
        }

        return CommonUtil.putPageExtraFalse(dataList, wxMpUserListDTO.getTotal(), param);
    }
}
