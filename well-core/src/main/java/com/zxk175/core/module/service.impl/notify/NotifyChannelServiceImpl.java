package com.zxk175.core.module.service.impl.notify;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxk175.core.common.bean.param.notify.NotifyChannelInfoParam;
import com.zxk175.core.common.bean.param.notify.NotifyChannelListParam;
import com.zxk175.core.common.bean.param.notify.NotifyChannelRemoveParam;
import com.zxk175.core.common.constant.Const;
import com.zxk175.core.common.constant.enums.StateType;
import com.zxk175.core.common.http.HttpMsg;
import com.zxk175.core.common.http.Response;
import com.zxk175.core.common.util.common.CommonUtil;
import com.zxk175.core.module.dao.notify.NotifyChannelDao;
import com.zxk175.core.module.entity.notify.NotifyChannel;
import com.zxk175.core.module.service.notify.NotifyChannelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 通道表 服务实现类
 * </p>
 *
 * @author zxk175
 * @since 2019-05-18 15:14:05
 */
@Service
public class NotifyChannelServiceImpl extends ServiceImpl<NotifyChannelDao, NotifyChannel> implements NotifyChannelService {

    @Override
    public Response saveNotifyChannel(NotifyChannel param) {
        this.save(param);

        return Response.saveReturn();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Response removeNotifyChannel(NotifyChannelRemoveParam param) {
        for (String id : param.getIdArr()) {
            this.removeById(id);
        }

        return Response.removeReturn();
    }

    @Override
    public Response modifyNotifyChannel(NotifyChannel param) {
        this.updateById(param);

        return Response.modifyReturn();
    }

    @Override
    public Response listNotifyChannelPage(NotifyChannelListParam param) {
        CommonUtil.buildPageParam(param);

        List<Map<String, Object>> records = baseMapper.listNotifyChannel(param);
        if (CollUtil.isEmpty(records)) {
            return Response.fail(HttpMsg.NOT_FOUND_DATA);
        }

        Long count = baseMapper.countNotifyChannel(param);

        return CommonUtil.putPageExtraFalse(records, count, param);
    }

    @Override
    public Response listSelectNotifyChannel() {
        QueryWrapper<NotifyChannel> notifyChannelQw = new QueryWrapper<>();
        notifyChannelQw.select("channel_id AS `value`, channel_name AS `view`");
        notifyChannelQw.eq(Const.DB_STATE, StateType.SHOW.value());
        List<Map<String, Object>> records = this.listMaps(notifyChannelQw);

        return Response.collReturn(records);
    }

    @Override
    public Response infoNotifyChannel(NotifyChannelInfoParam param) {
        QueryWrapper<NotifyChannel> notifyChannelQw = new QueryWrapper<>();
        notifyChannelQw.select("channel_id, channel_name, state");
        notifyChannelQw.eq("channel_id", param.getChannelId());
        NotifyChannel notifyChannelDb = this.getOne(notifyChannelQw);

        return Response.objectReturn(notifyChannelDb);
    }
}
