package com.zxk175.notify.core.http;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.module.bean.param.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author zxk175
 * @since 2020-03-29 13:52
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
	
	
	public static <T> Response<T> success() {
		return setOk(ResponseMsg.SUCCESS.code(), ResponseMsg.SUCCESS.msg());
	}
	
	public static <T> Response<T> success(T data) {
		Response<T> ok = success();
		return ok.setData(data);
	}
	
	public static <T> Response<T> success(ResponseMsg responseMsg) {
		return success(responseMsg.code(), responseMsg.msg());
	}
	
	public static <T> Response<T> success(Integer code, String msg) {
		return setOk(code, msg);
	}
	
	public static <T> Response<T> success(T data, Object extra) {
		return success(data).setExtra(extra);
	}
	
	public static <T> Response<T> success(ResponseMsg responseMsg, T data, Object extra) {
		Response<T> ok = success(responseMsg);
		return ok.setData(data).setExtra(extra);
	}
	
	public static <T> Response<T> failure() {
		return setFail(ResponseMsg.FAILURE.code(), ResponseMsg.FAILURE.msg());
	}
	
	public static <T> Response<T> failure(ResponseMsg responseMsg) {
		return setFail(responseMsg.code(), responseMsg.msg());
	}
	
	public static <T> Response<T> failure(T data) {
		Response<T> fail = failure();
		return fail.setData(data);
	}
	
	public static <T> Response<T> failure(String msg) {
		return setFail(Const.FAILURE_CODE, msg);
	}
	
	public static <T> Response<T> failure(Integer code, String msg) {
		return setFail(code, msg);
	}
	
	public static <T> Response<T> failure(ResponseMsg responseMsg, T data) {
		Response<T> fail = failure(responseMsg);
		return fail.setData(data);
	}
	
	public static <T> Response<T> saveReturn(boolean flag) {
		return flag ? success(ResponseMsg.DB_SAVE_SUCCESS) : failure(ResponseMsg.DB_SAVE_FAILURE);
	}
	
	public static <T> Response<T> removeReturn(boolean flag) {
		return flag ? success(ResponseMsg.DB_DELETE_SUCCESS) : failure(ResponseMsg.DB_DELETE_FAILURE);
	}
	
	public static <T> Response<T> modifyReturn(boolean flag) {
		return flag ? success(ResponseMsg.DB_MODIFY_SUCCESS) : failure(ResponseMsg.DB_MODIFY_FAILURE);
	}
	
	public static <T> Response<T> objectReturn(T data) {
		return ObjectUtil.isNull(data) ? failure(ResponseMsg.NOT_FOUND_DATA) : Response.success(data);
	}
	
	public static Response<Collection<?>> collReturn(Collection<?> data) {
		return CollUtil.isEmpty(data) ? failure(ResponseMsg.NOT_FOUND_DATA) : Response.success(data);
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
	
	public static <T extends Collection<?>> Response<T> putPageExtraFalse(T data, Long count, PageParam param) {
		return putPageExtra(data, count, param);
	}
	
	private static <T extends Collection<?>> Response<T> putPageExtra(T data, Long total, PageParam param) {
		if (CollUtil.isEmpty(data)) {
			return Response.success(ResponseMsg.NOT_FOUND_DATA);
		}
		
		return Response.success(data);
	}
	
}
