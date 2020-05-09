package com.zxk175.notify.module.dao.notify;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxk175.notify.module.bean.param.notify.NotifyMsgListParam;
import com.zxk175.notify.module.pojo.notify.NotifyMsg;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息表 Mapper 接口
 * </p>
 *
 * @author zxk175
 * @since 2019-11-27 15:35
 */
public interface NotifyMsgDao extends BaseMapper<NotifyMsg> {
	
	List<Map<String, Object>> listNotifyMsg(NotifyMsgListParam param);
	
	Long countNotifyMsg(NotifyMsgListParam param);
	
}
