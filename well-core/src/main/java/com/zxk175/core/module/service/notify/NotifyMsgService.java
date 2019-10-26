package com.zxk175.core.module.service.notify;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxk175.core.common.http.Response;
import com.zxk175.core.module.entity.notify.NotifyMsg;

/**
 * <p>
 * 消息表 服务类
 * </p>
 *
 * @author zxk175
 * @since 2019-05-18 12:10:04
 */
public interface NotifyMsgService extends IService<NotifyMsg> {

    Response infoNotifyMsg(String msgId);
}
