package com.zxk175.notify.module.service.impl.sys;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.zxk175.notify.core.constant.enums.StateType;
import com.zxk175.notify.core.http.Response;
import com.zxk175.notify.core.tuple.Tuple2;
import com.zxk175.notify.core.util.jwt.JwTokenUtil;
import com.zxk175.notify.core.util.security.ShaUtil;
import com.zxk175.notify.module.bean.param.sys.user.SysUserLoginParam;
import com.zxk175.notify.module.bean.vo.sys.SysUserLoginVo;
import com.zxk175.notify.module.bean.vo.token.SysSubjectVo;
import com.zxk175.notify.module.bean.vo.token.TokenVo;
import com.zxk175.notify.module.dao.sys.SysUserDao;
import com.zxk175.notify.module.pojo.sys.SysUser;
import com.zxk175.notify.module.service.sys.ISysUserService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author zxk175
 * @since 2019-11-27 15:35
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements ISysUserService {
	
	@Override
	public Response<Object> register(SysUser param) {
		Tuple2<String, String> tuple = ShaUtil.enc(param.getPassword());
		param.setPassword(tuple.first);
		param.setSalt(tuple.second);
		
		this.save(param);
		
		return Response.success("注册成功");
	}
	
	@Override
	public Response<Object> login(SysUserLoginParam param) {
		QueryWrapper<SysUser> userQw = new QueryWrapper<>();
		userQw.select("id, user_name, avatar, mobile, salt, password, state");
		userQw.eq("mobile", param.getMobile());
		SysUser sysUserDb = this.getOne(userQw);
		
		// 账号不存在
		if (ObjectUtil.isNull(sysUserDb)) {
			return Response.failure("账号不存在");
		}
		
		// 账号锁定
		if (StateType.HIDE.value().equals(sysUserDb.getState())) {
			return Response.failure("账号已被锁定，请联系管理员");
		}
		
		// 账号存在
		Tuple2<String, String> tuple = ShaUtil.enc(param.getPassword(), sysUserDb.getSalt());
		if (ObjectUtil.isNotNull(sysUserDb) && sysUserDb.getPassword().equals(tuple.first)) {
			SysSubjectVo sysSubjectDTO = new SysSubjectVo(sysUserDb.getId());
			TokenVo tokenVo = JwTokenUtil.buildToken(sysSubjectDTO);
			
			SysUserLoginVo userLoginVo = new SysUserLoginVo();
			userLoginVo.setUserId(String.valueOf(sysUserDb.getId()));
			userLoginVo.setUserName(sysUserDb.getUserName());
			userLoginVo.setAvatar(sysUserDb.getAvatar());
			userLoginVo.setMobile(sysUserDb.getMobile());
			
			Map<Object, Object> data = Maps.newHashMap();
			data.put("user", userLoginVo);
			data.put("token", tokenVo);
			
			return Response.success(data);
		}
		
		return Response.failure("账号或密码不正确");
	}
}
