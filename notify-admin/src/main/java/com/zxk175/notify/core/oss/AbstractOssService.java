package com.zxk175.notify.core.oss;

import com.zxk175.notify.core.tuple.Tuple2;

import java.io.InputStream;

/**
 * @author zxk175
 * @since 2019-10-12 16:39
 */
public abstract class AbstractOssService {

    String bucketName;
    String baseUrl;
    boolean isTest;


    public abstract Tuple2<Boolean, String> upload(InputStream inputStream, String dir, String ext, String newName, String oldName) throws Exception;

    public abstract void removeFile(String bucketName, String diskName, String key);

    public abstract void removeBatch(String dir);

    public abstract boolean objectExist(String fullPath);


    public String getBaseUrl() {
        return baseUrl;
    }
}
