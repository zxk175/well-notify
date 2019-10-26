package com.zxk175.notify.module.controller.notify;

import com.zxk175.core.common.bean.param.notify.NotifyChannelRemoveParam;
import com.zxk175.core.common.bean.param.notify.NotifyChannelUserInfoParam;
import com.zxk175.core.common.bean.param.notify.NotifyChannelUserListParam;
import com.zxk175.core.common.bean.param.notify.NotifyChannelUserRemoveParam;
import com.zxk175.core.common.http.Response;
import com.zxk175.core.module.controller.BaseController;
import com.zxk175.core.module.entity.notify.NotifyChannelUser;
import com.zxk175.core.module.service.notify.NotifyChannelUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 通道用户表 前端控制器
 * </p>
 *
 * @author zxk175
 * @since 2019-10-12 16:22
 */
@RestController
@AllArgsConstructor
@RequestMapping("/notify-channel-user")
@Api(tags = "NotifyChannelUser", description = "通道用户")
public class NotifyChannelUserController extends BaseController {

    private NotifyChannelUserService notifyChannelUserService;


    @PostMapping(value = "/save/v1")
    @ApiOperation(value = "添加通道用户", notes = "添加通道用户")
    public Response saveNotifyChannelUser(@Validated @RequestBody NotifyChannelUser param) {
        return notifyChannelUserService.saveNotifyChannelUser(param);
    }

    @PostMapping(value = "/remove/v1")
    @ApiOperation(value = "删除通道用户", notes = "删除通道用户")
    public Response removeNotifyChannelUser(@RequestBody NotifyChannelUserRemoveParam param) {
        return notifyChannelUserService.removeNotifyChannelUser(param);
    }

    @PostMapping(value = "/modify/v1")
    @ApiOperation(value = "修改通道用户", notes = "修改通道用户")
    public Response modifyNotifyChannelUser(@Validated @RequestBody NotifyChannelUser param) {
        return notifyChannelUserService.modifyNotifyChannelUser(param);
    }

    @PostMapping(value = "/list/v1")
    @ApiOperation(value = "通道用户列表", notes = "通道用户列表")
    public Response listNotifyChannelUserPage(@Validated @RequestBody NotifyChannelUserListParam param) {
        return notifyChannelUserService.listNotifyChannelUserPage(param);
    }

    @PostMapping(value = "/info/v1")
    @ApiOperation(value = "通道用户详情", notes = "通道用户详情")
    public Response infoNotifyChannelUser(@Validated @RequestBody NotifyChannelUserInfoParam param) {
        return notifyChannelUserService.infoNotifyChannelUser(param);
    }
}
