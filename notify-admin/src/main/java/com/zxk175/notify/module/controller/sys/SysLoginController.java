package com.zxk175.notify.module.controller.sys;

import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.core.http.Response;
import com.zxk175.notify.module.bean.param.sys.user.SysUserLoginParam;
import com.zxk175.notify.module.controller.BaseController;
import com.zxk175.notify.module.pojo.sys.SysUser;
import com.zxk175.notify.module.service.sys.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zxk175
 * @since 2019-11-27 17:30
 */
@Controller
@AllArgsConstructor
@RequestMapping(Const.BASE_URL + "/sys")
@Api(tags = "系统登录")
public class SysLoginController extends BaseController {
	
	private final ISysUserService sysUserService;
	
	
	@ResponseBody
	@PostMapping("/register/v1")
	@ApiOperation(value = "系统用户注册", notes = "系统用户注册")
	public Response<Object> register(@Validated @RequestBody SysUser param) {
		return sysUserService.register(param);
	}
	
	@ResponseBody
	@PostMapping("/login/v1")
	@ApiOperation(value = "系统用户登录", notes = "系统用户登录")
	public Response<Object> login(@Validated @RequestBody SysUserLoginParam param) {
		return sysUserService.login(param);
	}
	
}
