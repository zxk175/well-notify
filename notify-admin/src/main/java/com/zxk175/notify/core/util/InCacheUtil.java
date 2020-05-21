package com.zxk175.notify.core.util;

import cn.hutool.core.util.ObjectUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author zxk175
 * @since 2020-03-29 13:37
 */
public class InCacheUtil {

    /**
     * 将InputStream中的字节保存到ByteArrayOutputStream中
     */
    private ByteArrayOutputStream byteArrayOutputStream = null;

    public InCacheUtil(InputStream inputStream) {
        try {
            if (ObjectUtil.isNull(inputStream)) {
                return;
            }

            int length;
            byte[] buffer = new byte[1024];
            byteArrayOutputStream = new ByteArrayOutputStream();

            while ((length = inputStream.read(buffer)) > -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }

            byteArrayOutputStream.flush();
        } catch (Exception ex) {
            throw new RuntimeException("InCacheUtil Init Exception", ex);
        }
    }

    public InputStream getInputStream() {
        if (ObjectUtil.isNull(byteArrayOutputStream)) {
            return null;
        }

        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

}
