package com.zxk175.notify.core.http;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.module.bean.param.PageParam;
import com.zxk175.notify.module.bean.vo.PageBeanVo;
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
public class ResponseExt<T, EX> implements Serializable {
	
	@ApiModelProperty(value = "消息代码", example = Const.SUCCESS_CODE + "")
	private Integer code;
	
	@ApiModelProperty(value = "是否成功", example = "true")
	private Boolean success;
	
	@ApiModelProperty(value = "消息提示", example = Const.SUCCESS_TXT)
	private String msg;
	
	@ApiModelProperty(value = "额外数据")
	private EX extra;
	
	@ApiModelProperty(value = "返回数据")
	private T data;
	
	
	public static <T, EX> ResponseExt<T, EX> success() {
		return setSuccess(ResponseMsg.SUCCESS.code(), ResponseMsg.SUCCESS.msg());
	}
	
	public static <T, EX> ResponseExt<T, EX> success(T data) {
		ResponseExt<T, EX> ok = success();
		return ok.setData(data);
	}
	
	public static <T, EX> ResponseExt<T, EX> success(ResponseMsg responseMsg) {
		return success(responseMsg.code(), responseMsg.msg());
	}
	
	public static <T, EX> ResponseExt<T, EX> success(Integer code, String msg) {
		return setSuccess(code, msg);
	}
	
	public static <T, EX> ResponseExt<T, EX> success(T data, EX extra) {
		ResponseExt<T, EX> success = success(data);
		return success.setExtra(extra);
	}
	
	public static <T, EX> ResponseExt<T, EX> success(ResponseMsg responseMsg, T data, EX extra) {
		ResponseExt<T, EX> ok = success(responseMsg);
		return ok.setData(data).setExtra(extra);
	}
	
	public static <T, EX> ResponseExt<T, EX> failure() {
		return setFailure(ResponseMsg.FAILURE.code(), ResponseMsg.FAILURE.msg());
	}
	
	public static <T, EX> ResponseExt<T, EX> failure(ResponseMsg responseMsg) {
		return setFailure(responseMsg.code(), responseMsg.msg());
	}
	
	public static <T, EX> ResponseExt<T, EX> failure(T data) {
		ResponseExt<T, EX> fail = failure();
		return fail.setData(data);
	}
	
	public static <T, EX> ResponseExt<T, EX> failure(String msg) {
		return setFailure(Const.FAILURE_CODE, msg);
	}
	
	public static <T, EX> ResponseExt<T, EX> failure(Integer code, String msg) {
		return setFailure(code, msg);
	}
	
	public static <T, EX> ResponseExt<T, EX> failure(ResponseMsg responseMsg, T data) {
		ResponseExt<T, EX> fail = failure(responseMsg);
		return fail.setData(data);
	}
	
	public static <T, EX> ResponseExt<T, EX> saveReturn(boolean flag) {
		return flag ? success(ResponseMsg.DB_SAVE_SUCCESS) : failure(ResponseMsg.DB_SAVE_FAILURE);
	}
	
	public static <T, EX> ResponseExt<T, EX> removeReturn(boolean flag) {
		return flag ? success(ResponseMsg.DB_DELETE_SUCCESS) : failure(ResponseMsg.DB_SAVE_FAILURE);
	}
	
	public static <T, EX> ResponseExt<T, EX> modifyReturn(boolean flag) {
		return success(ResponseMsg.DB_MODIFY_SUCCESS);
	}
	
	public static <T, EX> ResponseExt<T, EX> objectReturn(T data) {
		return ObjectUtil.isNull(data) ? failure(ResponseMsg.NOT_FOUND_DATA) : ResponseExt.success(data);
	}
	
	public static <T, EX> ResponseExt<Collection<?>, EX> collReturn(Collection<?> data) {
		return CollUtil.isEmpty(data) ? failure(ResponseMsg.NOT_FOUND_DATA) : ResponseExt.success(data);
	}
	
	private static <T, EX> ResponseExt<T, EX> setSuccess(Integer code, String msg) {
		return setResponse(code, msg, true);
	}
	
	private static <T, EX> ResponseExt<T, EX> setFailure(Integer code, String msg) {
		return setResponse(code, msg, false);
	}
	
	private static <T, EX> ResponseExt<T, EX> setResponse(Integer code, String msg, boolean success) {
		return new ResponseExt<T, EX>()
				.setCode(code)
				.setMsg(msg)
				.setSuccess(success);
	}
	
	public static ResponseExt<Collection<?>, PageBeanVo> putPageExtraFalse(Collection<?> data, Long count, PageParam param) {
		return putPageExtra(data, count, param, new PageBeanVo());
	}
	
	public static <T extends Collection<?>, EX extends PageBeanVo> ResponseExt<T, EX> putPageExtraFalse(T data, Long count, PageParam param, EX extra) {
		return putPageExtra(data, count, param, extra);
	}
	
	private static <T extends Collection<?>, EX extends PageBeanVo> ResponseExt<T, EX> putPageExtra(T data, Long total, PageParam param, EX extra) {
		if (CollUtil.isEmpty(data)) {
			return ResponseExt.success(ResponseMsg.NOT_FOUND_DATA);
		}
		
		if (extra == null) {
			return ResponseExt.success(data);
		}
		
		extra.setPage(param.getPageRaw());
		extra.setSize(param.getSize());
		extra.setTotal(total);
		extra.setTotalPage(extra.calcTotalPage());
		
		return ResponseExt.success(data, extra);
	}
	
}
