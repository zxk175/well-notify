package com.zxk175.notify.module.controller.wx;

import com.zxk175.notify.core.config.WxConfig;
import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.core.util.wx.WxSignUtil;
import com.zxk175.notify.module.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 微信公众号 前端控制器
 *
 * @author zxk175
 * @since 2020-03-30 09:14
 */
@Controller
@RequestMapping(Const.BASE_URL + "/wx-mp")
@Api(tags = "微信公众号")
public class WxMpController extends BaseController {
	
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
	
}
