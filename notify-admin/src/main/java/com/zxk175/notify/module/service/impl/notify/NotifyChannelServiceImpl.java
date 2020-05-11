package com.zxk175.notify.module.service.impl.notify;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.core.constant.enums.StateType;
import com.zxk175.notify.core.http.Response;
import com.zxk175.notify.core.http.ResponseExt;
import com.zxk175.notify.core.util.common.CommonUtil;
import com.zxk175.notify.module.bean.param.notify.NotifyChannelInfoParam;
import com.zxk175.notify.module.bean.param.notify.NotifyChannelListParam;
import com.zxk175.notify.module.bean.param.notify.NotifyChannelRemoveParam;
import com.zxk175.notify.module.bean.param.wx.DeviceNotifyParam;
import com.zxk175.notify.module.bean.vo.PageBeanVo;
import com.zxk175.notify.module.dao.notify.NotifyChannelDao;
import com.zxk175.notify.module.pojo.notify.NotifyChannel;
import com.zxk175.notify.module.service.notify.INotifyChannelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 通道表 服务实现类
 * </p>
 *
 * @author zxk175
 * @since 2019-11-27 15:35
 */
@Service
public class NotifyChannelServiceImpl extends ServiceImpl<NotifyChannelDao, NotifyChannel> implements INotifyChannelService {
	
	@Override
	public Response<Object> saveNotifyChannel(NotifyChannel param) {
		return Response.saveReturn(this.save(param));
	}
	
	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<Object> removeNotifyChannel(NotifyChannelRemoveParam param) {
		NotifyChannel notifyChannel;
		for (String idStr : param.getIdArr()) {
			notifyChannel = new NotifyChannel();
			notifyChannel.setId(Convert.toLong(idStr));
			notifyChannel.setState(StateType.HIDE.value());
			this.updateById(notifyChannel);
		}
		
		return Response.removeReturn(true);
	}
	
	@Override
	public Response<Object> modifyNotifyChannel(NotifyChannel param) {
		return Response.modifyReturn(this.updateById(param));
	}
	
	@Override
	public ResponseExt<Collection<?>, PageBeanVo> listNotifyChannelPage(NotifyChannelListParam param) {
		CommonUtil.buildPageParam(param);
		
		List<Map<String, Object>> dataList = baseMapper.listNotifyChannel(param);
		
		Long count = baseMapper.countNotifyChannel(param);
		
		return ResponseExt.putPageExtraFalse(dataList, count, param);
	}
	
	@Override
	public Response<Collection<?>> listSelectNotifyChannel() {
		QueryWrapper<NotifyChannel> notifyChannelQw = new QueryWrapper<>();
		notifyChannelQw.select("id AS `value`, channel_name AS `view`");
		notifyChannelQw.eq(Const.DB_STATE, StateType.SHOW.value());
		notifyChannelQw.orderByDesc("create_time");
		return Response.collReturn(this.listMaps(notifyChannelQw));
	}
	
	@Override
	public Response<Object> infoNotifyChannel(NotifyChannelInfoParam param) {
		QueryWrapper<NotifyChannel> notifyChannelQw = new QueryWrapper<>();
		notifyChannelQw.select("id AS channelId, channel_name AS channelName, state");
		notifyChannelQw.eq("id", param.getChannelId());
		return Response.objectReturn(this.getMap(notifyChannelQw));
	}
	
	@Override
	public NotifyChannel infoNotifyChannel(DeviceNotifyParam param) {
		QueryWrapper<NotifyChannel> notifyChannelQw = new QueryWrapper<>();
		notifyChannelQw.select("id, channel_name, state");
		notifyChannelQw.eq(Const.DB_STATE, StateType.SHOW.value());
		notifyChannelQw.eq("id", param.getSendKey());
		NotifyChannel notifyChannelDb = this.getOne(notifyChannelQw);
		// 通道为空 返回公共通道
		if (ObjectUtil.isNull(notifyChannelDb)) {
			param.setSendKey("999");
			notifyChannelDb = infoNotifyChannel(param);
		}
		
		return notifyChannelDb;
	}
	
}
