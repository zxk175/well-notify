package com.zxk175.notify.core.util.jwt;

import com.zxk175.notify.core.util.json.FastJsonUtil;
import com.zxk175.notify.module.bean.vo.token.SysSubjectVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

/**
 * @author zxk175
 * @since 2020-03-29 13:48
 */
public class JwtAuthUtil {
	
	public static SysSubjectVo sysSubject(String token) {
		Jws<Claims> claimsJws = parserSysJwt(token);
		// 解析subject
		String subject = claimsJws.getBody().getSubject();
		
		return FastJsonUtil.toObject(subject, SysSubjectVo.class);
	}
	
	private static Jws<Claims> parserSysJwt(String token) {
		JwtParser parser = Jwts.parser();
		parser.setSigningKey(JwTokenUtil.getKeyInstance());
		
		return parser.parseClaimsJws(token);
	}
	
}