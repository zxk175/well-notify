package com.zxk175.notify.core.util.upload;

import cn.hutool.core.codec.Base64;
import com.google.common.collect.Lists;
import com.zxk175.notify.core.oss.AbstractOssService;
import com.zxk175.notify.core.oss.OssFactory;
import com.zxk175.notify.core.tuple.Tuple2;
import com.zxk175.notify.core.tuple.Tuple4;
import com.zxk175.notify.core.tuple.Tuples;
import com.zxk175.notify.core.util.InCacheUtil;
import com.zxk175.notify.core.util.Md5Util;
import com.zxk175.notify.core.util.MyStrUtil;
import com.zxk175.notify.core.util.ThreadUtil;
import com.zxk175.notify.core.util.common.CommonUtil;
import com.zxk175.notify.core.util.json.FastJsonUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

/**
 * @author zxk175
 * @since 2020-03-29 13:43
 */
public class UploadUtil {

    public static String single(InputStream inputStream, String dir) {
        Tuple4<Boolean, String, String, String> tuple = single(inputStream, dir, "jpg", "");
        return tuple.third;
    }

    public static String single(InputStream inputStream, String dir, String ext) {
        Tuple4<Boolean, String, String, String> tuple = single(inputStream, dir, ext, "");
        return tuple.third;
    }

    public static Tuple4<Boolean, String, String, String> single(InputStream inputStream, String dir, String ext, String oldImgUrl) {
        try {
            long fileSize = inputStream.available();
            if (fileSize < 1) {
                throw new RuntimeException("上传文件大小不能为0");
            }

            InCacheUtil cacheUtil = new InCacheUtil(inputStream);
            InputStream cacheStream = cacheUtil.getInputStream();

            // 判断图片md5
            String newName = Md5Util.md5(cacheStream);
            String[] newNameSplit = dir.split(MyStrUtil.COLON);
            if (newNameSplit.length > 1) {
                dir = newNameSplit[0];
                newName = newNameSplit[1] + MyStrUtil.UNDERLINE + newName;
            }

            String oldName = CommonUtil.getFileName(oldImgUrl);
            String shortPath = dir + newName + MyStrUtil.DOT + ext;

            // 判断文件是否存在
            AbstractOssService instance = OssFactory.build();
            boolean objectExist = instance.objectExist(shortPath);

            // 文件存在 返回原始图片地址
            if (objectExist) {
                String fullPath = instance.getBaseUrl() + shortPath;
                return Tuples.tuple(true, shortPath, fullPath, newName);
            }

            cacheStream = cacheUtil.getInputStream();
            Tuple2<Boolean, String> tuple = instance.upload(cacheStream, dir, ext, newName, oldName);
            if (tuple.first) {
                String fullPath = tuple.second;

                return Tuples.tuple(true, shortPath, fullPath, newName);
            }

            return Tuples.tuple(false, shortPath, "", newName);
        } catch (Exception ex) {
            throw new RuntimeException("上传失败");
        }
    }

    public static Tuple2<String, List<String>> multiple(String dir, List<String> imgList) throws Exception {
        final int size = imgList.size();

        if (size < 1) {
            return Tuple2.with(MyStrUtil.EMPTY, Lists.newArrayList());
        }

        // 创建一个线程池
        ExecutorService executorService = ThreadUtil.newExecutor(size, "uploadUtil");
        // 创建多个有返回值的任务
        List<FutureTask<String>> futureTasks = Lists.newArrayList();
        for (final String img : imgList) {
            if (MyStrUtil.isBlank(img)) {
                continue;
            }

            final UploadCallable callable = new UploadCallable(dir, img, null);

            FutureTask<String> futureTask = new FutureTask<>(callable);

            // 提交异步任务到线程池，让线程池管理任务
            // 由于是异步并行任务，所以这里并不会阻塞
            executorService.submit(futureTask);

            futureTasks.add(futureTask);
        }

        // 关闭线程池
        executorService.shutdown();

        List<String> imgPaths = Lists.newArrayList();
        // 获取所有并发任务的运行结果
        for (FutureTask<String> future : futureTasks) {
            final String result = future.get();
            if (MyStrUtil.isNotBlank(result)) {
                imgPaths.add(result);
            }
        }

        return Tuples.tuple(FastJsonUtil.jsonStrByMy(imgPaths), imgPaths);
    }

    static InputStream base64ToStream(String base64) {
        if (MyStrUtil.isBlank(base64)) {
            throw new RuntimeException("image参数不能为空");
        }

        byte[] decode = Base64.decode(base64);

        return new ByteArrayInputStream(decode);
    }

}
