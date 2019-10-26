package com.zxk175.notify.module.controller.wx;

import com.zxk175.core.common.config.WxConfig;
import com.zxk175.core.common.constant.Const;
import com.zxk175.core.common.util.wx.WxSignUtil;
import com.zxk175.core.module.controller.BaseController;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 微信公众号 前端控制器
 * </p>
 *
 * @author zxk175
 * @since 2019-10-12 16:20
 */
@RestController
@RequestMapping("/wx/mp")
@Api(tags = "WxMpMain", description = "微信公众号入口")
public class WxMpMainController extends BaseController {

    @GetMapping(value = "/main/v1")
    public String wxAuthGet() {
        return WxSignUtil.checkSign(request, WxConfig.WX_MP_TOKEN);
    }

    @PostMapping(value = "/main/v1")
    public String parsePost() throws Exception {
        request.setCharacterEncoding(Const.UTF_8);
        response.setContentType(MediaType.TEXT_XML_VALUE);

        // 默认返回的文本消息
        return "";
    }
}
