package com.zxk175.notify.module.controller.notify;

import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.core.http.Response;
import com.zxk175.notify.core.http.ResponseExt;
import com.zxk175.notify.module.bean.param.notify.NotifyChannelInfoParam;
import com.zxk175.notify.module.bean.param.notify.NotifyChannelListParam;
import com.zxk175.notify.module.bean.param.notify.NotifyChannelRemoveParam;
import com.zxk175.notify.module.bean.vo.PageBeanVo;
import com.zxk175.notify.module.controller.BaseController;
import com.zxk175.notify.module.pojo.notify.NotifyChannel;
import com.zxk175.notify.module.service.notify.INotifyChannelService;
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
 * 通道表 前端控制器
 * </p>
 *
 * @author zxk175
 * @since 2019-10-12 16:22
 */
@RestController
@AllArgsConstructor
@RequestMapping(Const.BASE_URL + "/notify-channel")
@Api(tags = "通知通道")
public class NotifyChannelController extends BaseController {

    private final INotifyChannelService notifyChannelService;


    @PostMapping(value = "/save/v1")
    @ApiOperation(value = "添加通道", notes = "添加通道")
    public Response<Object> saveNotifyChannel(@Validated @RequestBody NotifyChannel param) {
        return notifyChannelService.saveNotifyChannel(param);
    }

    @PostMapping(value = "/remove/v1")
    @ApiOperation(value = "删除通道", notes = "删除通道")
    public Response<Object> removeNotifyChannel(@Validated @RequestBody NotifyChannelRemoveParam param) {
        return notifyChannelService.removeNotifyChannel(param);
    }

    @PostMapping(value = "/modify/v1")
    @ApiOperation(value = "修改通道", notes = "修改通道")
    public Response<Object> modifyNotifyChannel(@Validated @RequestBody NotifyChannel param) {
        return notifyChannelService.modifyNotifyChannel(param);
    }

    @PostMapping(value = "/list/v1")
    @ApiOperation(value = "通道列表", notes = "通道列表")
    public ResponseExt<Collection<?>, PageBeanVo> listNotifyChannelPage(@Validated @RequestBody NotifyChannelListParam param) {
        return notifyChannelService.listNotifyChannelPage(param);
    }

    @PostMapping(value = "/list-select/v1")
    @ApiOperation(value = "通道下拉框", notes = "通道下拉框")
    public Response<Collection<?>> listSelectNotifyChannel() {
        return notifyChannelService.listSelectNotifyChannel();
    }

    @PostMapping(value = "/info/v1")
    @ApiOperation(value = "通道详情", notes = "通道详情")
    public Response<Object> infoNotifyChannel(@Validated @RequestBody NotifyChannelInfoParam param) {
        return notifyChannelService.infoNotifyChannel(param);
    }

}
