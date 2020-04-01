package com.zxk175.notify.module.controller.wx;

import com.zxk175.notify.core.http.ResponseExt;
import com.zxk175.notify.module.bean.param.wx.DeviceNotifyParam;
import com.zxk175.notify.module.controller.BaseController;
import com.zxk175.notify.module.service.notify.INotifyMsgService;
import com.zxk175.notify.module.service.wx.IWxTemplateMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 微信模板消息 前端控制器
 *
 * @author zxk175
 * @since 2020-03-29 23:28
 */
@Controller
@AllArgsConstructor
@RequestMapping("/notify")
@Api(tags = "微信公众号模板消息通知V1")
public class WxMpTemplateMsgV1Controller extends BaseController {

    private INotifyMsgService notifyMsgService;
    private IWxTemplateMsgService templateMsgService;


    @GetMapping("msg")
    public String msg(Model model) {
        model.addAttribute("title", "消息阅读 | Well");
        return "msg";
    }

    @ResponseBody
    @PostMapping(value = "/send/v1", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "发送通知消息", notes = "发送通知消息")
    public ResponseExt<Object, ?> sendMsg(@Validated @RequestBody DeviceNotifyParam param) {
        return templateMsgService.send(param);
    }

    @ResponseBody
    @GetMapping(value = "/msg-info/v1", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获取消息内容", notes = "获取消息内容")
    public ResponseExt<Object, ?> getMsgInfo(@RequestParam(name = "msgId") String msgId) {
        return notifyMsgService.infoNotifyMsg(msgId);
    }

}