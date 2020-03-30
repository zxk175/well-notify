package com.zxk175.notify.module.controller.notify;

import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.core.http.Response;
import com.zxk175.notify.core.http.ResponseExt;
import com.zxk175.notify.module.bean.param.notify.NotifyChannelUserInfoParam;
import com.zxk175.notify.module.bean.param.notify.NotifyChannelUserListParam;
import com.zxk175.notify.module.bean.param.notify.NotifyChannelUserRemoveParam;
import com.zxk175.notify.module.bean.vo.PageBeanVo;
import com.zxk175.notify.module.controller.BaseController;
import com.zxk175.notify.module.pojo.notify.NotifyChannelUser;
import com.zxk175.notify.module.service.notify.INotifyChannelUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

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
@RequestMapping(Const.BASE_URL + "/notify-channel-user")
@Api(tags = "通道用户")
public class NotifyChannelUserController extends BaseController {

    private INotifyChannelUserService notifyChannelUserService;


    @PostMapping(value = "/save/v1")
    @ApiOperation(value = "添加通道用户", notes = "添加通道用户")
    public Response<Object> saveNotifyChannelUser(@Validated @RequestBody NotifyChannelUser param) {
        return notifyChannelUserService.saveNotifyChannelUser(param);
    }

    @PostMapping(value = "/remove/v1")
    @ApiOperation(value = "删除通道用户", notes = "删除通道用户")
    public Response<Object> removeNotifyChannelUser(@Validated @RequestBody NotifyChannelUserRemoveParam param) {
        return notifyChannelUserService.removeNotifyChannelUser(param);
    }

    @PostMapping(value = "/modify/v1")
    @ApiOperation(value = "修改通道用户", notes = "修改通道用户")
    public Response<Object> modifyNotifyChannelUser(@Validated @RequestBody NotifyChannelUser param) {
        return notifyChannelUserService.modifyNotifyChannelUser(param);
    }

    @PostMapping(value = "/list/v1")
    @ApiOperation(value = "通道用户列表", notes = "通道用户列表")
    public ResponseExt<Collection<?>, PageBeanVo> listNotifyChannelUserPage(@Validated @RequestBody NotifyChannelUserListParam param) {
        return notifyChannelUserService.listNotifyChannelUserPage(param);
    }

    @PostMapping(value = "/info/v1")
    @ApiOperation(value = "通道用户详情", notes = "通道用户详情")
    public Response<Object> infoNotifyChannelUser(@Validated @RequestBody NotifyChannelUserInfoParam param) {
        return notifyChannelUserService.infoNotifyChannelUser(param);
    }

}
