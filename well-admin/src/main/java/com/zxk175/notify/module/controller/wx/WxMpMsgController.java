package com.zxk175.notify.module.controller.wx;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zxk175.core.common.bean.param.wx.DeviceNotifyParam;
import com.zxk175.core.common.config.WxConfig;
import com.zxk175.core.common.constant.Const;
import com.zxk175.core.common.constant.enums.StateType;
import com.zxk175.core.common.http.Response;
import com.zxk175.core.common.tuple.Tuple2;
import com.zxk175.core.common.util.MyStrUtil;
import com.zxk175.core.common.util.ThreadUtil;
import com.zxk175.core.common.util.spring.SpringActiveUtil;
import com.zxk175.core.common.util.wx.WxTemplateMsgUtil;
import com.zxk175.core.common.util.wx.bean.notify.DeviceNotifyData;
import com.zxk175.core.common.util.wx.thread.DeviceNotifyCallable;
import com.zxk175.core.module.controller.BaseController;
import com.zxk175.core.module.entity.notify.NotifyChannel;
import com.zxk175.core.module.entity.notify.NotifyChannelUser;
import com.zxk175.core.module.entity.notify.NotifyMsg;
import com.zxk175.core.module.service.notify.NotifyChannelService;
import com.zxk175.core.module.service.notify.NotifyChannelUserService;
import com.zxk175.core.module.service.notify.NotifyMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

/**
 * <p>
 * 微信模板消息 前端控制器
 * </p>
 *
 * @author zxk175
 * @since 2019-10-12 16:21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wx/mp")
@Api(tags = "WxMpMsg", description = "微信模板消息通知")
public class WxMpMsgController extends BaseController {

    private NotifyMsgService notifyMsgService;
    private WxTemplateMsgUtil wxTemplateMsgUtil;
    private NotifyChannelService notifyChannelService;
    private NotifyChannelUserService notifyChannelUserService;


    @PostMapping(value = "/send-msg/v1")
    @ApiOperation(value = "发送微信模板消息", notes = "发送微信模板消息")
    public Response sendMsg(@Validated @RequestBody DeviceNotifyParam param) {
        String sendKey = param.getSendKey();
        QueryWrapper<NotifyChannel> notifyChannelQw = new QueryWrapper<>();
        notifyChannelQw.select("channel_id, channel_name, state");
        notifyChannelQw.eq("channel_id", sendKey);
        notifyChannelQw.eq(Const.DB_STATE, StateType.SHOW.value());
        NotifyChannel notifyChannelDb = notifyChannelService.getOne(notifyChannelQw);
        if (ObjectUtil.isNull(notifyChannelDb)) {
            return fail("sendKey不合法");
        }

        QueryWrapper<NotifyChannelUser> notifyChannelUserQw = new QueryWrapper<>();
        notifyChannelUserQw.select("user_id, channel_id, full_name, open_id, state");
        notifyChannelUserQw.eq("channel_id", notifyChannelDb.getChannelId());
        notifyChannelUserQw.eq(Const.DB_STATE, StateType.SHOW.value());
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

    @GetMapping(value = "/info-msg/v1")
    @ApiOperation(value = "获取消息内容", notes = "获取消息内容")
    public Response infoNotifyMsg(@RequestParam(name = "msgId") String msgId) {
        return notifyMsgService.infoNotifyMsg(msgId);
    }

    private Response notifyCommon(DeviceNotifyParam param, NotifyChannel notifyChannel, List<NotifyChannelUser> notifyChannelUsers) throws Exception {
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
            List<FutureTask<Tuple2<String, String>>> futureTasks = new ArrayList<>(8);
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
                deviceNotifyData.setUrl(SpringActiveUtil.getUrlStr() + "/msg?msgId=" + notifyMsg.getMsgId());

                DeviceNotifyCallable callable = new DeviceNotifyCallable(deviceNotifyData, wxTemplateMsgUtil);
                FutureTask<Tuple2<String, String>> futureTask = new FutureTask<>(callable);

                // 提交异步任务到线程池，让线程池管理任务
                // 由于是异步并行任务，所以这里并不会阻塞
                executorService.submit(futureTask);

                futureTasks.add(futureTask);
            }

            // 关闭线程池
            executorService.shutdown();

            List<Tuple2<String, String>> tuples = new ArrayList<>(8);
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
