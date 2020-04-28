package com.zxk175.notify.module.bean.vo.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zxk175
 * @since 2020-03-29 14:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenVo {
	
	private Long ttl;
	
	private String token;
	
	private Long expireIn;
	
}
