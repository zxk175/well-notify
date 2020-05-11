package com.zxk175.notify.module.service.impl.wx;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.zxk175.notify.core.config.WxConfig;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

/**
 * @author zxk175
 * @since 2020-03-30 11:39
 */
@Slf4j
@Service
@AllArgsConstructor
public class WxTemplateMsgServiceImpl implements IWxTemplateMsgService {
	
	private final INotifyMsgService notifyMsgService;
	private final WxTemplateMsgUtil wxTemplateMsgUtil;
	private final INotifyChannelService notifyChannelService;
	private final INotifyChannelUserService notifyChannelUserService;
	
	
	@Override
	public ResponseExt<Object, ?> send(DeviceNotifyParam param) {
		NotifyMsg notifyMsg = notifySave(param);
		
		NotifyChannel notifyChannelDb = notifyChannelService.infoNotifyChannel(param);
		if (ObjectUtil.isNull(notifyChannelDb)) {
			return ResponseExt.failure("sendKey不合法");
		}
		
		List<NotifyChannelUser> notifyChannelUsers = notifyChannelUserService.notifyChannelUsers(notifyChannelDb.getId());
		if (CollUtil.isEmpty(notifyChannelUsers)) {
			return ResponseExt.failure("无人订阅");
		}
		
		try {
			return notifyCommon(notifyMsg, notifyChannelDb, notifyChannelUsers);
		} catch (Exception ex) {
			log.error("发送服务异常", ex);
			return ResponseExt.failure("发送服务异常");
		}
	}
	
	private NotifyMsg notifySave(DeviceNotifyParam param) {
		NotifyMsg notifyMsg = new NotifyMsg();
		notifyMsg.setTitle(param.getTitle());
		notifyMsg.setContent(param.getContent());
		if (notifyMsgService.save(notifyMsg)) {
			return notifyMsg;
		}
		
		log.error("消息插入失败：{}", param);
		throw new RuntimeException("消息插入失败");
	}
	
	private ResponseExt<Object, ?> notifyCommon(NotifyMsg notifyMsg, NotifyChannel notifyChannel, List<NotifyChannelUser> notifyChannelUsers) throws Exception {
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
			deviceNotifyData.setFirst(notifyMsg.getTitle());
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
	
}
