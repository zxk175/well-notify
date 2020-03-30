package com.zxk175.notify.core.util.upload;

import cn.hutool.core.util.ObjectUtil;

import java.io.InputStream;
import java.util.concurrent.Callable;

/**
 * @author zxk175
 * @since 2020-03-29 13:46
 */
public class UploadCallable implements Callable<String> {

    private String dir;
    private String imgData;
    private InputStream inputStream;


    UploadCallable(String dir, String imgData, InputStream inputStream) {
        this.dir = dir;
        this.imgData = imgData;
        this.inputStream = inputStream;
    }

    @Override
    public String call() throws Exception {
        if (imgData.startsWith("http")) {
            return imgData;
        }

        if (ObjectUtil.isNull(inputStream)) {
            inputStream = UploadUtil.base64ToStream(imgData);
        }

        return UploadUtil.single(inputStream, dir);
    }

}
