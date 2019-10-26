package com.zxk175.core.module.service.notify;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxk175.core.common.bean.param.notify.NotifyChannelInfoParam;
import com.zxk175.core.common.bean.param.notify.NotifyChannelListParam;
import com.zxk175.core.common.bean.param.notify.NotifyChannelRemoveParam;
import com.zxk175.core.common.http.Response;
import com.zxk175.core.module.entity.notify.NotifyChannel;

/**
 * <p>
 * 通道表 服务类
 * </p>
 *
 * @author zxk175
 * @since 2019-05-18 15:14:05
 */
public interface NotifyChannelService extends IService<NotifyChannel> {

    Response saveNotifyChannel(NotifyChannel param);

    Response removeNotifyChannel(NotifyChannelRemoveParam param);

    Response modifyNotifyChannel(NotifyChannel param);

    Response listNotifyChannelPage(NotifyChannelListParam param);

    Response listSelectNotifyChannel();

    Response infoNotifyChannel(NotifyChannelInfoParam param);
}
