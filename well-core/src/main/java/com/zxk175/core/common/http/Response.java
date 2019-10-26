package com.zxk175.core.common.http;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.zxk175.core.common.constant.Const;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author zxk175
 * @since 2019-10-25 14:40
 */
@Data
@Accessors(chain = true)
public class Response<T> implements Serializable {

    @ApiModelProperty(value = "消息代码", example = "0")
    private Integer code;

    @ApiModelProperty(value = "是否成功", example = "true")
    private Boolean success;

    @ApiModelProperty(value = "消息提示", example = "请求成功")
    private String msg;

    @ApiModelProperty(value = "额外数据", example = "{}")
    private Object extra;

    @ApiModelProperty(value = "返回数据", example = "{}")
    private T data;


    public static <T> Response<T> ok() {
        return setOk(HttpMsg.OK.code(), HttpMsg.OK.msg());
    }

    public static <T> Response<T> ok(T data) {
        Response<T> ok = ok();
        return ok.setData(data);
    }

    public static <T> Response<T> ok(HttpMsg httpMsg) {
        return ok(httpMsg.code(), httpMsg.msg());
    }

    public static <T> Response<T> ok(Integer code, String msg) {
        return setOk(code, msg);
    }

    public static <T> Response<T> ok(T data, Object extra) {
        return ok(data).setExtra(extra);
    }

    public static <T> Response<T> ok(HttpMsg httpMsg, T data, Object extra) {
        Response<T> ok = ok(httpMsg);
        return ok.setData(data).setExtra(extra);
    }

    public static <T> Response<T> fail() {
        return setFail(HttpMsg.FAIL.code(), HttpMsg.FAIL.msg());
    }

    public static <T> Response<T> fail(HttpMsg httpMsg) {
        return setFail(httpMsg.code(), httpMsg.msg());
    }

    public static <T> Response<T> fail(T data) {
        Response<T> fail = fail();
        return fail.setData(data);
    }

    public static <T> Response<T> fail(String msg) {
        return setFail(Const.FAIL_CODE, msg);
    }

    public static <T> Response<T> fail(Integer code, String msg) {
        return setFail(code, msg);
    }

    public static <T> Response<T> fail(HttpMsg httpMsg, T data) {
        Response<T> fail = fail(httpMsg);
        return fail.setData(data);
    }

    public static <T> Response<T> saveReturn() {
        return ok(HttpMsg.DB_ADD_SUCCESS);
    }

    public static <T> Response<T> removeReturn() {
        return ok(HttpMsg.DB_DELETE_SUCCESS);
    }

    public static <T> Response<T> modifyReturn() {
        return ok(HttpMsg.DB_MODIFY_SUCCESS);
    }

    public static <T> Response<T> objectReturn(T data) {
        return ObjectUtil.isNull(data) ? fail(HttpMsg.NOT_FOUND_DATA) : Response.ok(data);
    }

    public static Response<Collection<?>> collReturn(Collection<?> data) {
        return CollUtil.isEmpty(data) ? fail(HttpMsg.NOT_FOUND_DATA) : Response.ok(data);
    }

    private static <T> Response<T> setOk(Integer code, String msg) {
        return setResponse(code, msg, true);
    }

    private static <T> Response<T> setFail(Integer code, String msg) {
        return setResponse(code, msg, false);
    }

    private static <T> Response<T> setResponse(Integer code, String msg, boolean success) {
        return new Response<T>()
                .setCode(code)
                .setMsg(msg)
                .setSuccess(success);
    }
}
