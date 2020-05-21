package com.zxk175.notify.module.controller.wx;

import com.alibaba.fastjson.JSONObject;
import com.zxk175.notify.core.config.WxConfig;
import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.core.http.Response;
import com.zxk175.notify.core.util.json.FastJsonUtil;
import com.zxk175.notify.core.util.net.OkHttpUtil;
import com.zxk175.notify.core.util.wx.WxAccessUtil;
import com.zxk175.notify.core.util.wx.WxSignUtil;
import com.zxk175.notify.module.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信公众号 前端控制器
 *
 * @author zxk175
 * @since 2020-03-30 09:14
 */
@Controller
@AllArgsConstructor
@RequestMapping(Const.BASE_URL + "/wx-mp")
@Api(tags = "微信公众号")
public class WxMpController extends BaseController {

    private final WxAccessUtil wxAccessUtil;


    @ResponseBody
    @GetMapping(value = "/main/v1")
    @ApiOperation(value = "Token验证", notes = "Token验证")
    public String wxAuthGet() {
        return WxSignUtil.checkSign(request, WxConfig.WX_MP_TOKEN);
    }

    @ResponseBody
    @PostMapping(value = "/main/v1")
    @ApiOperation(value = "解析Post请求", notes = "解析Post请求")
    public String parsePost() throws Exception {
        request.setCharacterEncoding(Const.UTF_8);
        response.setContentType("text/xml;charset=utf-8");

        // 默认返回的文本消息
        return "";
    }

    @ResponseBody
    @PostMapping(value = "/clear-quota/v1")
    @ApiOperation(value = "清除配额(每月10次)", notes = "清除配额(每月10次)")
    public Response<Object> clearQuota() {
        String globalToken = wxAccessUtil.getGlobalToken();
        Map<String, Object> paramMap = new HashMap<>(8);
        paramMap.put("appid", WxConfig.MP_APP_ID);

        JSONObject result = OkHttpUtil.instance().postJson2Obj("https://api.weixin.qq.com/cgi-bin/clear_quota?access_token=" + globalToken, FastJsonUtil.jsonStr(paramMap));
        return Response.success(result);
    }

}
