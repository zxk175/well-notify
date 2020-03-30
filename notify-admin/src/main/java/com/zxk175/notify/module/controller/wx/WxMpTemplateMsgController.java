package com.zxk175.notify.module.controller.wx;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zxk175.notify.core.config.WxConfig;
import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.core.constant.enums.StateType;
import com.zxk175.notify.core.http.Response;
import com.zxk175.notify.core.tuple.Tuple2;
import com.zxk175.notify.core.util.MyStrUtil;
import com.zxk175.notify.core.util.ThreadUtil;
import com.zxk175.notify.core.util.spring.SpringActiveUtil;
import com.zxk175.notify.core.util.wx.WxTemplateMsgUtil;
import com.zxk175.notify.core.util.wx.bean.notify.DeviceNotifyData;
import com.zxk175.notify.core.util.wx.thread.DeviceNotifyCallable;
import com.zxk175.notify.module.bean.param.wx.DeviceNotifyParam;
import com.zxk175.notify.module.controller.BaseController;
import com.zxk175.notify.module.pojo.notify.NotifyChannel;
import com.zxk175.notify.module.pojo.notify.NotifyChannelUser;
import com.zxk175.notify.module.pojo.notify.NotifyMsg;
import com.zxk175.notify.module.service.notify.INotifyChannelService;
import com.zxk175.notify.module.service.notify.INotifyChannelUserService;
import com.zxk175.notify.module.service.notify.INotifyMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

/**
 * 微信模板消息 前端控制器
 *
 * @author zxk175
 * @since 2020-03-29 23:28
 */
@Controller
@AllArgsConstructor
@RequestMapping("/notify")
@Api(tags = "微信公众号模板消息通知")
public class WxMpTemplateMsgController extends BaseController {

    private INotifyMsgService notifyMsgService;
    private WxTemplateMsgUtil wxTemplateMsgUtil;
    private INotifyChannelService notifyChannelService;
    private INotifyChannelUserService notifyChannelUserService;


    @GetMapping("msg")
    public String msg(Model model) {
        model.addAttribute("title", "消息阅读 | Well");
        return "msg";
    }

    @ResponseBody
    @PostMapping(value = "/send/v1", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "发送通知消息", notes = "发送通知消息")
    public Response<Object> sendMsg(@Validated @RequestBody DeviceNotifyParam param) {
        String sendKey = param.getSendKey();
        QueryWrapper<NotifyChannel> notifyChannelQw = new QueryWrapper<>();
        notifyChannelQw.select("id, channel_name, state");
        notifyChannelQw.eq(Const.DB_STATE, StateType.SHOW.value());
        notifyChannelQw.eq("id", sendKey);
        NotifyChannel notifyChannelDb = notifyChannelService.getOne(notifyChannelQw);
        if (ObjectUtil.isNull(notifyChannelDb)) {
            return fail("sendKey不合法");
        }

        QueryWrapper<NotifyChannelUser> notifyChannelUserQw = new QueryWrapper<>();
        notifyChannelUserQw.select("id, user_name, channel_id, open_id, state");
        notifyChannelUserQw.eq(Const.DB_STATE, StateType.SHOW.value());
        notifyChannelUserQw.eq("channel_id", notifyChannelDb.getId());
        List<NotifyChannelUser> notifyChannelUsers = notifyChannelUserService.list(notifyChannelUserQw);
        if (CollUtil.isEmpty(notifyChannelUsers)) {
            return fail("无人订阅");
        }

        try {
            return notifyCommon(param, notifyChannelDb, notifyChannelUsers);
        } catch (Exception ex) {
            ex.printStackTrace();
            return fail("发送服务异常");
        }
    }

    @ResponseBody
    @GetMapping(value = "/msg-info/v1", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获取消息内容", notes = "获取消息内容")
    public Response<Object> getMsgInfo(@RequestParam(name = "msgId") String msgId) {
        QueryWrapper<NotifyMsg> notifyMsgQw = new QueryWrapper<>();
        notifyMsgQw.select("title, content");
        notifyMsgQw.eq(Const.DB_STATE, StateType.SHOW.value());
        notifyMsgQw.eq("id", msgId);
        Map<String, Object> notifyMsg = notifyMsgService.getMap(notifyMsgQw);
        return Response.objectReturn(notifyMsg);
    }

    private Response<Object> notifyCommon(DeviceNotifyParam param, NotifyChannel notifyChannel, List<NotifyChannelUser> notifyChannelUsers) throws Exception {
        NotifyMsg notifyMsg = new NotifyMsg();
        String title = param.getTitle();
        String content = param.getContent();
        notifyMsg.setTitle(title);
        notifyMsg.setContent(content);

        boolean save = notifyMsgService.save(notifyMsg);
        if (save) {
            // 创建一个线程池
            ExecutorService executorService = ThreadUtil.newExecutor(notifyChannelUsers.size(), "WxNotify");
            // 创建多个有返回值的任务
            List<FutureTask<Tuple2<String, String>>> futureTasks = new ArrayList<>();
            for (NotifyChannelUser channelUser : notifyChannelUsers) {
                String openId = channelUser.getOpenId();
                if (MyStrUtil.isBlank(openId)) {
                    continue;
                }

                DeviceNotifyData deviceNotifyData = new DeviceNotifyData();
                deviceNotifyData.setOpenId(openId);
                deviceNotifyData.setTemplateId(WxConfig.DEVICE_ID);
                deviceNotifyData.setFirst(title);
                deviceNotifyData.setChannelName(notifyChannel.getChannelName());
                deviceNotifyData.setUrl(SpringActiveUtil.getUrlStr() + "/msg?msgId=" + notifyMsg.getId());

                DeviceNotifyCallable callable = new DeviceNotifyCallable(deviceNotifyData, wxTemplateMsgUtil);
                FutureTask<Tuple2<String, String>> futureTask = new FutureTask<>(callable);

                // 提交异步任务到线程池，让线程池管理任务
                // 由于是异步并行任务，所以这里并不会阻塞
                executorService.submit(futureTask);

                futureTasks.add(futureTask);
            }

            // 关闭线程池
            executorService.shutdown();

            List<Tuple2<String, String>> tuples = new ArrayList<>();
            // 获取所有并发任务的运行结果
            for (FutureTask<Tuple2<String, String>> future : futureTasks) {
                final Tuple2<String, String> result = future.get();
                tuples.add(result);
            }

            return ok(tuples);
        }

        return fail("发送失败");
    }

}
