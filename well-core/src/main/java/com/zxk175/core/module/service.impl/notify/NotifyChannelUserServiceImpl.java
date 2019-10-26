package com.zxk175.core.module.service.impl.notify;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxk175.core.common.bean.param.notify.NotifyChannelUserInfoParam;
import com.zxk175.core.common.bean.param.notify.NotifyChannelUserListParam;
import com.zxk175.core.common.bean.param.notify.NotifyChannelUserRemoveParam;
import com.zxk175.core.common.constant.Const;
import com.zxk175.core.common.http.HttpMsg;
import com.zxk175.core.common.http.Response;
import com.zxk175.core.common.util.common.CommonUtil;
import com.zxk175.core.module.dao.notify.NotifyChannelUserDao;
import com.zxk175.core.module.entity.notify.NotifyChannelUser;
import com.zxk175.core.module.service.notify.NotifyChannelUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 通道用户表 服务实现类
 * </p>
 *
 * @author zxk175
 * @since 2019-05-18 15:14:05
 */
@Service
public class NotifyChannelUserServiceImpl extends ServiceImpl<NotifyChannelUserDao, NotifyChannelUser> implements NotifyChannelUserService {

    @Override
    public Response saveNotifyChannelUser(NotifyChannelUser param) {
        this.save(param);

        return Response.saveReturn();
    }

    @Override
    public Response removeNotifyChannelUser(NotifyChannelUserRemoveParam param) {
        for (String id : param.getIdArr()) {
            this.removeById(id);
        }

        return Response.removeReturn();
    }

    @Override
    public Response modifyNotifyChannelUser(NotifyChannelUser param) {
        this.updateById(param);

        return Response.modifyReturn();
    }

    @Override
    public Response listNotifyChannelUserPage(NotifyChannelUserListParam param) {
        CommonUtil.buildPageParam(param);

        List<Map<String, Object>> records = baseMapper.listNotifyChannelUser(param);
        if (CollUtil.isEmpty(records)) {
            return Response.fail(HttpMsg.NOT_FOUND_DATA);
        }

        Long count = baseMapper.countNotifyChannelUser(param);

        return CommonUtil.putPageExtraFalse(records, count, param);
    }

    @Override
    public Response infoNotifyChannelUser(NotifyChannelUserInfoParam param) {
        QueryWrapper<NotifyChannelUser> notifyChannelUserQw = new QueryWrapper<>();
        notifyChannelUserQw.select("user_id, channel_id, full_name, open_id, state");
        notifyChannelUserQw.eq(Const.DB_USER_ID, param.getUserId());
        NotifyChannelUser notifyChannelUserDb = this.getOne(notifyChannelUserQw);

        return Response.objectReturn(notifyChannelUserDb);
    }
}
