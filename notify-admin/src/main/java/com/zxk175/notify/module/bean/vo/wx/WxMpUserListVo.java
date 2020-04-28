package com.zxk175.notify.module.bean.vo.wx;

import lombok.Data;

/**
 * @author zxk175
 * @since 2020-03-29 14:00
 */
@Data
public class WxMpUserListVo {
	
	private Long total;
	
	private Long count;
	
	private WxMpUserListDataVo data;
	
	private String next_openid;
	
}
