package com.zxk175.notify.module.service.impl.wx;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zxk175.notify.core.config.WxConfig;
import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.core.constant.enums.StateType;
import com.zxk175.notify.core.http.ResponseExt;
import com.zxk175.notify.core.tuple.Tuple2;
import com.zxk175.notify.core.util.MyStrUtil;
import com.zxk175.notify.core.util.ThreadUtil;
import com.zxk175.notify.core.util.spring.SpringActiveUtil;
import com.zxk175.notify.core.util.wx.WxTemplateMsgUtil;
import com.zxk175.notify.core.util.wx.bean.notify.DeviceNotifyData;
import com.zxk175.notify.core.util.wx.thread.DeviceNotifyCallable;
import com.zxk175.notify.module.bean.param.wx.DeviceNotifyParam;
import com.zxk175.notify.module.pojo.notify.NotifyChannel;
import com.zxk175.notify.module.pojo.notify.NotifyChannelUser;
import com.zxk175.notify.module.pojo.notify.NotifyMsg;
import com.zxk175.notify.module.service.notify.INotifyChannelService;
import com.zxk175.notify.module.service.notify.INotifyChannelUserService;
import com.zxk175.notify.module.service.notify.INotifyMsgService;
import com.zxk175.notify.module.service.wx.IWxTemplateMsgService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

/**
 * @author zxk175
 * @since 2020-03-30 11:39
 */
@Service
@AllArgsConstructor
public class WxTemplateMsgServiceImpl implements IWxTemplateMsgService {

    private INotifyMsgService notifyMsgService;
    private WxTemplateMsgUtil wxTemplateMsgUtil;
    private INotifyChannelService notifyChannelService;
    private INotifyChannelUserService notifyChannelUserService;


    @Override
    public ResponseExt<Object, ?> send(DeviceNotifyParam param) {
        String sendKey = param.getSendKey();
        QueryWrapper<NotifyChannel> notifyChannelQw = new QueryWrapper<>();
        notifyChannelQw.select("id, channel_name, state");
        notifyChannelQw.eq(Const.DB_STATE, StateType.SHOW.value());
        notifyChannelQw.eq("id", sendKey);
        NotifyChannel notifyChannelDb = notifyChannelService.getOne(notifyChannelQw);
        if (ObjectUtil.isNull(notifyChannelDb)) {
            return ResponseExt.failure("sendKey不合法");
        }

        QueryWrapper<NotifyChannelUser> notifyChannelUserQw = new QueryWrapper<>();
        notifyChannelUserQw.select("id, user_name, channel_id, open_id, state");
        notifyChannelUserQw.eq(Const.DB_STATE, StateType.SHOW.value());
        notifyChannelUserQw.eq("channel_id", notifyChannelDb.getId());
        List<NotifyChannelUser> notifyChannelUsers = notifyChannelUserService.list(notifyChannelUserQw);
        if (CollUtil.isEmpty(notifyChannelUsers)) {
            return ResponseExt.failure("无人订阅");
        }

        try {
            return notifyCommon(param, notifyChannelDb, notifyChannelUsers);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseExt.failure("发送服务异常");
        }
    }

    private ResponseExt<Object, ?> notifyCommon(DeviceNotifyParam param, NotifyChannel notifyChannel, List<NotifyChannelUser> notifyChannelUsers) throws Exception {
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

            return ResponseExt.success(tuples);
        }

        return ResponseExt.failure("发送失败");
    }
}
