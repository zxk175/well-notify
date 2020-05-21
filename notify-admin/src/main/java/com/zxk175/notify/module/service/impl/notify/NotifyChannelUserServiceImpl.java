package com.zxk175.notify.module.service.impl.notify;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.core.constant.enums.StateType;
import com.zxk175.notify.core.http.Response;
import com.zxk175.notify.core.http.ResponseExt;
import com.zxk175.notify.core.util.common.CommonUtil;
import com.zxk175.notify.module.bean.param.notify.NotifyChannelUserInfoParam;
import com.zxk175.notify.module.bean.param.notify.NotifyChannelUserListParam;
import com.zxk175.notify.module.bean.param.notify.NotifyChannelUserRemoveParam;
import com.zxk175.notify.module.bean.vo.PageBeanVo;
import com.zxk175.notify.module.dao.notify.NotifyChannelUserDao;
import com.zxk175.notify.module.pojo.notify.NotifyChannelUser;
import com.zxk175.notify.module.service.notify.INotifyChannelUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 通道用户表 服务实现类
 * </p>
 *
 * @author zxk175
 * @since 2019-11-27 15:35
 */
@Service
public class NotifyChannelUserServiceImpl extends ServiceImpl<NotifyChannelUserDao, NotifyChannelUser> implements INotifyChannelUserService {

    @Override
    public Response<Object> saveNotifyChannelUser(NotifyChannelUser param) {
        boolean flag = this.save(param);
        return Response.saveReturn(flag);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Response<Object> removeNotifyChannelUser(NotifyChannelUserRemoveParam param) {
        NotifyChannelUser notifyChannelUser;
        for (String idStr : param.getIdArr()) {
            notifyChannelUser = new NotifyChannelUser();
            notifyChannelUser.setId(Convert.toLong(idStr));
            notifyChannelUser.setState(StateType.HIDE.value());
            this.updateById(notifyChannelUser);
        }

        return Response.removeReturn(true);
    }

    @Override
    public Response<Object> modifyNotifyChannelUser(NotifyChannelUser param) {
        boolean flag = this.updateById(param);
        return Response.modifyReturn(flag);
    }

    @Override
    public ResponseExt<Collection<?>, PageBeanVo> listNotifyChannelUserPage(NotifyChannelUserListParam param) {
        CommonUtil.buildPageParam(param);

        List<Map<String, Object>> records = baseMapper.listNotifyChannelUser(param);

        Long count = baseMapper.countNotifyChannelUser(param);

        return ResponseExt.putPageExtraFalse(records, count, param);
    }

    @Override
    public Response<Object> infoNotifyChannelUser(NotifyChannelUserInfoParam param) {
        QueryWrapper<NotifyChannelUser> notifyChannelUserQw = new QueryWrapper<>();
        notifyChannelUserQw.select("id AS userId, user_name AS userName, channel_id AS channelId, channel_name AS channelName, open_id AS openId, state");
        notifyChannelUserQw.eq("id", param.getUserId());
        Map<String, Object> record = this.getMap(notifyChannelUserQw);
        return Response.objectReturn(record);
    }

    @Override
    public List<NotifyChannelUser> notifyChannelUsers(Long channelId) {
        QueryWrapper<NotifyChannelUser> notifyChannelUserQw = new QueryWrapper<>();
        notifyChannelUserQw.select("id, user_name, channel_id, open_id, state");
        notifyChannelUserQw.eq(Const.DB_STATE, StateType.SHOW.value());
        notifyChannelUserQw.eq("channel_id", channelId);
        return this.list(notifyChannelUserQw);
    }

}
