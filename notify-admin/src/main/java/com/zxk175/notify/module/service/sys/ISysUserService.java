package com.zxk175.notify.module.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxk175.notify.core.http.Response;
import com.zxk175.notify.module.bean.param.sys.user.SysUserLoginParam;
import com.zxk175.notify.module.pojo.sys.SysUser;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author zxk175
 * @since 2019-11-27 15:35
 */
public interface ISysUserService extends IService<SysUser> {
	
	Response<Object> register(SysUser param);
	
	Response<Object> login(SysUserLoginParam param);
	
}
