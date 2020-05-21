package com.zxk175.notify.core.oss;

/**
 * @author zxk175
 * @since 2019-10-12 16:39
 */
public class OssFactory {

    public static AbstractOssService build() {
        return new AliYunOssService();
    }
}
