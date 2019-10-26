package com.zxk175.notify.module.controller.notify;

import com.zxk175.core.common.bean.param.notify.NotifyChannelInfoParam;
import com.zxk175.core.common.bean.param.notify.NotifyChannelListParam;
import com.zxk175.core.common.bean.param.notify.NotifyChannelRemoveParam;
import com.zxk175.core.common.http.Response;
import com.zxk175.core.module.controller.BaseController;
import com.zxk175.core.module.entity.notify.NotifyChannel;
import com.zxk175.core.module.service.notify.NotifyChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 通道表 前端控制器
 * </p>
 *
 * @author zxk175
 * @since 2019-10-12 16:22
 */
@RestController
@AllArgsConstructor
@RequestMapping("/notify-channel")
@Api(tags = "NotifyChannel", description = "通道")
public class NotifyChannelController extends BaseController {

    private NotifyChannelService notifyChannelService;


    @PostMapping(value = "/save/v1")
    @ApiOperation(value = "添加通道", notes = "添加通道")
    public Response saveNotifyChannel(@Validated @RequestBody NotifyChannel param) {
        return notifyChannelService.saveNotifyChannel(param);
    }

    @PostMapping(value = "/remove/v1")
    @ApiOperation(value = "删除通道", notes = "删除通道")
    public Response removeNotifyChannel(@RequestBody NotifyChannelRemoveParam param) {
        return notifyChannelService.removeNotifyChannel(param);
    }

    @PostMapping(value = "/modify/v1")
    @ApiOperation(value = "修改通道", notes = "修改通道")
    public Response modifyNotifyChannel(@Validated @RequestBody NotifyChannel param) {
        return notifyChannelService.modifyNotifyChannel(param);
    }

    @PostMapping(value = "/list/v1")
    @ApiOperation(value = "通道列表", notes = "通道列表")
    public Response listNotifyChannelPage(@Validated @RequestBody NotifyChannelListParam param) {
        return notifyChannelService.listNotifyChannelPage(param);
    }

    @PostMapping(value = "/list-select/v1")
    @ApiOperation(value = "通道下拉框", notes = "通道下拉框")
    public Response listSelectNotifyChannel() {
        return notifyChannelService.listSelectNotifyChannel();
    }

    @PostMapping(value = "/info/v1")
    @ApiOperation(value = "通道详情", notes = "通道详情")
    public Response infoNotifyChannel(@Validated @RequestBody NotifyChannelInfoParam param) {
        return notifyChannelService.infoNotifyChannel(param);
    }
}
