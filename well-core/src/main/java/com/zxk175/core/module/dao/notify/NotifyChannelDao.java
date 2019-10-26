package com.zxk175.core.module.dao.notify;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxk175.core.common.bean.param.notify.NotifyChannelListParam;
import com.zxk175.core.module.entity.notify.NotifyChannel;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 通道表 Mapper 接口
 * </p>
 *
 * @author zxk175
 * @since 2019-05-18 15:14:05
 */
public interface NotifyChannelDao extends BaseMapper<NotifyChannel> {

    List<Map<String, Object>> listNotifyChannel(NotifyChannelListParam param);

    Long countNotifyChannel(NotifyChannelListParam param);
}
