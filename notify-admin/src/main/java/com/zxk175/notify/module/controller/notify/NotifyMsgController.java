package com.zxk175.notify.module.controller.notify;

import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.core.http.Response;
import com.zxk175.notify.core.http.ResponseExt;
import com.zxk175.notify.module.bean.param.notify.NotifyMsgListParam;
import com.zxk175.notify.module.bean.param.notify.NotifyMsgRemoveParam;
import com.zxk175.notify.module.bean.vo.PageBeanVo;
import com.zxk175.notify.module.controller.BaseController;
import com.zxk175.notify.module.service.notify.INotifyMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

/**
 * 消息表 前端控制器
 *
 * @author zxk175
 * @since 2020-03-30 09:15
 */
@Controller
@AllArgsConstructor
@RequestMapping(Const.BASE_URL + "/notify-msg")
@Api(tags = "通知消息")
public class NotifyMsgController extends BaseController {
	
	private final INotifyMsgService notifyMsgService;
	
	
	@ResponseBody
	@PostMapping("/remove/v1")
	@ApiOperation(value = "删除消息", notes = "删除消息")
	public Response<Object> removeNotifyMsg(@Validated @RequestBody NotifyMsgRemoveParam param) {
		return notifyMsgService.removeNotifyMsg(param);
	}
	
	@ResponseBody
	@PostMapping("/list/v1")
	@ApiOperation(value = "消息分页列表", notes = "消息分页列表")
	public ResponseExt<Collection<?>, PageBeanVo> listNotifyMsgPage(@Validated @RequestBody NotifyMsgListParam param) {
		return notifyMsgService.listNotifyMsgPage(param);
	}
	
	@ResponseBody
	@GetMapping(value = "/info/v1")
	@ApiOperation(value = "获取消息内容", notes = "获取消息内容")
	public Response<Object> getMsgInfo(@RequestParam(name = "msgId") String msgId) {
		return notifyMsgService.infoNotifyMsg(msgId);
	}
	
}
