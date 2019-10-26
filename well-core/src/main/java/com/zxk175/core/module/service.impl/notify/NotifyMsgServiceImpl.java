package com.zxk175.core.module.service.impl.notify;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxk175.core.common.constant.Const;
import com.zxk175.core.common.constant.enums.StateType;
import com.zxk175.core.common.http.Response;
import com.zxk175.core.module.dao.notify.NotifyMsgDao;
import com.zxk175.core.module.entity.notify.NotifyMsg;
import com.zxk175.core.module.service.notify.NotifyMsgService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author zxk175
 * @since 2019-05-18 12:10:04
 */
@Service
public class NotifyMsgServiceImpl extends ServiceImpl<NotifyMsgDao, NotifyMsg> implements NotifyMsgService {

    @Override
    public Response infoNotifyMsg(String msgId) {
        QueryWrapper<NotifyMsg> notifyMsgQw = new QueryWrapper<>();
        notifyMsgQw.select("title, content");
        notifyMsgQw.eq("msg_id", msgId);
        notifyMsgQw.eq(Const.DB_STATE, StateType.SHOW.value());
        Map<String, Object> notifyMsg = this.getMap(notifyMsgQw);

        return Response.objectReturn(notifyMsg);
    }
}
