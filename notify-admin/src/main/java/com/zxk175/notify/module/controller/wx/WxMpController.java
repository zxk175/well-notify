package com.zxk175.notify.module.controller.wx;

import com.zxk175.notify.core.config.WxConfig;
import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.core.util.wx.WxSignUtil;
import com.zxk175.notify.module.controller.BaseController;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 微信公众号 前端控制器
 * </p>
 *
 * @author zxk175
 * @since 17/5/17
 */
@Controller
@RequestMapping(Const.BASE_URL + "/wx-mp")
@Api(tags = "微信公众号")
public class WxMpController extends BaseController {

    @ResponseBody
    @GetMapping(value = "/main/v1")
    public String wxAuthGet() {
        return WxSignUtil.checkSign(request, WxConfig.WX_MP_TOKEN);
    }

    @ResponseBody
    @PostMapping(value = "/main/v1")
    public String parsePost() throws Exception {
        request.setCharacterEncoding(Const.UTF_8);
        response.setContentType("text/xml;charset=utf-8");

        // 默认返回的文本消息
        return "";
    }

}
