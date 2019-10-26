package com.zxk175.core.module.dao.notify;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxk175.core.common.bean.param.notify.NotifyChannelUserListParam;
import com.zxk175.core.module.entity.notify.NotifyChannelUser;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 通道用户表 Mapper 接口
 * </p>
 *
 * @author zxk175
 * @since 2019-05-18 15:14:05
 */
public interface NotifyChannelUserDao extends BaseMapper<NotifyChannelUser> {

    List<Map<String, Object>> listNotifyChannelUser(NotifyChannelUserListParam param);

    Long countNotifyChannelUser(NotifyChannelUserListParam param);
}
