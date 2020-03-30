package com.zxk175.notify.module.controller.sys;

import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.core.http.Response;
import com.zxk175.notify.module.controller.BaseController;
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
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author zxk175
 * @since 2019-11-27 15:41
 */
@Controller
@AllArgsConstructor
@RequestMapping(Const.BASE_URL + "/sys-user")
@Api(tags = "系统用户")
public class SysUserController extends BaseController {

    private ISysUserService sysUserService;


    @ResponseBody
    @PostMapping("/save")
    @ApiOperation(value = "新增系统用户", notes = "新增系统用户")
    public Response<Object> saveSysUser(@Validated @RequestBody Object param) {
        return ok();
    }

    @ResponseBody
    @PostMapping("/remove")
    @ApiOperation(value = "删除系统用户", notes = "删除系统用户")
    public Response<Object> removeSysUser(@Validated @RequestBody Object param) {
        return ok();
    }

    @ResponseBody
    @PostMapping("/modify")
    @ApiOperation(value = "修改系统用户", notes = "修改系统用户")
    public Response<Object> modifySysUser(@Validated @RequestBody Object param) {
        return ok();
    }

    @ResponseBody
    @PostMapping("/list")
    @ApiOperation(value = "系统用户分页列表", notes = "系统用户分页列表")
    public Response<Object> listSysUserPage(@Validated @RequestBody Object param) {
        return null;
    }

}
