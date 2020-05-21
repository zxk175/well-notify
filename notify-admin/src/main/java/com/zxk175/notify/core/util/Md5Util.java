package com.zxk175.notify.core.util;

import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author zxk175
 * @since 2020-03-29 13:38
 */
public class Md5Util {

    public static String md5(InputStream in) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            int len;
            byte[] buffer = new byte[8192];
            while ((len = in.read(buffer)) != -1) {
                md5.update(buffer, 0, len);
            }
            BigInteger bi = new BigInteger(1, md5.digest());
            return bi.toString(16);
        } catch (Exception ex) {
            throw new RuntimeException("MD5 Exception", ex);
        }
    }

}