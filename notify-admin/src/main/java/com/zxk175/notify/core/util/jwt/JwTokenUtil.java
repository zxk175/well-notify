package com.zxk175.notify.core.util.jwt;

import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.core.util.DateUtil;
import com.zxk175.notify.core.util.json.FastJsonUtil;
import com.zxk175.notify.module.bean.vo.token.SysSubjectVo;
import com.zxk175.notify.module.bean.vo.token.TokenVo;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zxk175
 * @since 2019-03-23 21:21
 * <p>
 * jwt的claim里一般包含以下几种数据:
 * 1. iss -- token的发行者
 * 2. sub -- 该jwt所面向的用户
 * 3. aud -- 接收该jwt的一方
 * 4. exp -- token的失效时间
 * 5. nbf -- 在此时间段之前,不会被处理
 * 6. iat -- jwt发布时间
 * 7. jti -- jwt唯一标识,防止重复使用
 */
public class JwTokenUtil {
	
	public static TokenVo buildToken(SysSubjectVo sysSubjectVo) {
		String subjectStr = FastJsonUtil.jsonStr(sysSubjectVo);
		Date now = new Date();
		
		// 添加构成jwt的参数
		JwtBuilder builder = Jwts.builder()
				// head参数
				.setHeaderParam("typ", "JWT")
				// 创建时间
				.setIssuedAt(now)
				// 所有者
				.setSubject(subjectStr)
				// 压缩
				.compressWith(CompressionCodecs.GZIP)
				// 加密方式
				.signWith(SignatureAlgorithm.HS512, getKeyInstance());
		
		// 过期时间
		int tokenTtlHours = Const.TOKEN_TTL_HOURS;
		Date exp = DateUtil.datePlus(Calendar.HOUR_OF_DAY, now, tokenTtlHours);
		// jwt过期时间
		builder.setExpiration(exp);
		
		// 生成jwt
		return new TokenVo((long) (tokenTtlHours), builder.compact(), exp.getTime());
	}
	
	static Key getKeyInstance() {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(Const.TOKEN_DESC_KEY);
		return new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
	}
	
}
