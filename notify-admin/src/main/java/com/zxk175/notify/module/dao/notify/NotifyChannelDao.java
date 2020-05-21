package com.zxk175.notify.module.dao.notify;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxk175.notify.module.bean.param.notify.NotifyChannelListParam;
import com.zxk175.notify.module.pojo.notify.NotifyChannel;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 通道表 Mapper 接口
 * </p>
 *
 * @author zxk175
 * @since 2019-11-27 15:35
 */
public interface NotifyChannelDao extends BaseMapper<NotifyChannel> {

    List<Map<String, Object>> listNotifyChannel(NotifyChannelListParam param);

    Long countNotifyChannel(NotifyChannelListParam param);

}
