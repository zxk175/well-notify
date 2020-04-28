package com.zxk175.notify.core.util.security;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.core.tuple.Tuple2;
import com.zxk175.notify.core.tuple.Tuples;

/**
 * @author zxk175
 * @since 2020-03-29 13:44
 */
public class ShaUtil {
	
	public static Tuple2<String, String> enc(String data) {
		String baseString = "1234567890abcdefghijkmnpqrstuvwxyzABCDEFGHIJKMNPQRSTUVWXYZ";
		String salt = RandomUtil.randomString(baseString, 20);
		return enc(data, salt);
	}
	
	public static Tuple2<String, String> enc(String data, String salt) {
		Digester sha256 = new Digester(DigestAlgorithm.SHA256);
		sha256.setSalt(salt.getBytes(Const.UTF_8_OBJ));
		
		return Tuples.tuple(sha256.digestHex(data), salt);
	}
	
}
