package com.zxk175.core.common.util.net;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.zxk175.core.common.util.MyStrUtil;
import com.zxk175.core.common.util.json.FastJsonUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.annotations.EverythingIsNonNull;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zxk175
 * @since 2019-10-10 14:05
 */
@Slf4j
public class OkHttpUtil {


    private OkHttpClient okHttpClient;
    /**
     * 会被多线程使用，所以使用关键字volatile
     */
    private volatile static OkHttpUtil okHttpUtil;


    /**
     * 私有化构造方法
     */
    private OkHttpUtil() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 单例模式，全局1个OkHttpUtil对象
     */
    public static OkHttpUtil instance() {
        if (okHttpUtil == null) {
            synchronized (OkHttpUtil.class) {
                if (okHttpUtil == null) {
                    okHttpUtil = new OkHttpUtil();
                }
            }
        }

        return okHttpUtil;
    }

    public JSONObject get2Obj(String url) {
        return get2Obj(url, null);
    }

    private JSONObject get2Obj(String url, Map<String, String> param) {
        String result = getCommon(url, param);

        return FastJsonUtil.toObject(result, JSONObject.class);
    }

    private String getCommon(String url, Map<String, String> param) {
        // 请求参数
        HttpUrl.Builder builder = HttpUrl.get(url).newBuilder();
        if (CollUtil.isNotEmpty(param)) {
            for (Map.Entry<String, String> entry : param.entrySet()) {
                builder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }

        // 请求创建
        Request request = new Request.Builder()
                .url(builder.build().toString())
                .build();
        return executeRequest(request);
    }

    /**
     * 提交表单数据
     */
    public String postForm(String url, Map<String, String> param) {
        // 表单对象
        FormBody.Builder form = new FormBody.Builder();
        if (CollUtil.isNotEmpty(param)) {
            // 遍历map集合
            for (Map.Entry<String, String> entry : param.entrySet()) {
                form.add(entry.getKey(), entry.getValue());
            }

            RequestBody body = form.build();

            // 采用post提交数据
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            return executeRequest(request);
        }

        return MyStrUtil.EMPTY;
    }

    /**
     * 提交json数据 异步
     */
    private void postJsonAsync(String url, String param, final ResultCallback callback) {
        Request request = postJsonCommon(url, param);

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call call, Response response) {
                callback.onResponse(response);
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call call, IOException ex) {
                callback.onFailure(request, ex);
            }
        });
    }

    /**
     * 提交json数据
     */
    public String postJson(String url, String param) {
        Request request = postJsonCommon(url, param);

        return executeRequest(request);
    }

    public JSONObject postJson2Obj(String url, String param) {
        return FastJsonUtil.toObject(postJson(url, param), JSONObject.class);
    }

    private Request postJsonCommon(String url, String param) {
        // 请求参数
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse(MediaType.APPLICATION_JSON_UTF8_VALUE);
        RequestBody body = RequestBody.create(mediaType, param);

        // 请求创建
        return new Request.Builder()
                .url(url)
                .post(body)
                .build();
    }

    private String executeRequest(Request request) {
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (ObjectUtil.isNull(responseBody)) {
                    return MyStrUtil.EMPTY;
                }

                return responseBody.string();
            }

            log.info("responseMessage：{}", response.message());
        } catch (Exception ex) {
            throw new RuntimeException("executeRequest异常", ex);
        } finally {
            if (ObjectUtil.isNotNull(response)) {
                response.close();
            }
        }

        return MyStrUtil.EMPTY;
    }

    interface ResultCallback {
        /**
         * 成功回调
         */
        void onResponse(Response response);

        /**
         * 失败回调
         */
        void onFailure(Request request, Exception ex);
    }
}