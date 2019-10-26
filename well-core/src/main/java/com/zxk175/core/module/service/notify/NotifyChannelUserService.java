package com.zxk175.core.module.service.notify;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxk175.core.common.bean.param.notify.NotifyChannelUserInfoParam;
import com.zxk175.core.common.bean.param.notify.NotifyChannelUserListParam;
import com.zxk175.core.common.bean.param.notify.NotifyChannelUserRemoveParam;
import com.zxk175.core.common.http.Response;
import com.zxk175.core.module.entity.notify.NotifyChannelUser;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 通道用户表 服务类
 * </p>
 *
 * @author zxk175
 * @since 2019-05-18 15:14:05
 */
public interface NotifyChannelUserService extends IService<NotifyChannelUser> {

    Response saveNotifyChannelUser(NotifyChannelUser param);

    Response removeNotifyChannelUser(NotifyChannelUserRemoveParam param);

    Response modifyNotifyChannelUser(NotifyChannelUser param);

    Response listNotifyChannelUserPage(NotifyChannelUserListParam param);

    Response infoNotifyChannelUser(NotifyChannelUserInfoParam param);
}
