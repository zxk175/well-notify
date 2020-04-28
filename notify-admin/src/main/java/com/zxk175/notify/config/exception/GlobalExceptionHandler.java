package com.zxk175.notify.config.exception;

import cn.hutool.core.util.ObjectUtil;
import com.zxk175.notify.core.constant.Const;
import com.zxk175.notify.core.http.Response;
import com.zxk175.notify.core.http.ResponseMsg;
import com.zxk175.notify.core.util.ExceptionUtil;
import com.zxk175.notify.core.util.common.CommonUtil;
import com.zxk175.notify.core.util.upload.UploadUtil;
import com.zxk175.notify.module.bean.vo.ErrorVo;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zxk175
 * @since 2020-03-29 13:56
 */
@Slf4j
@Order(-1000)
@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final String FORMAT1 = Const.FORMAT1;
	private static final String FORMAT2 = Const.FORMAT2;
	
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public String handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
		buildExceptionInfo(ex, "请求方式处理不支持");
		return String.format("Cannot %s %s", request.getMethod(), request.getRequestURI());
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(NoHandlerFoundException.class)
	public Response<?> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpServletRequest request) {
		buildExceptionInfo(ex, "请求地址不存在");
		String msg = "请求地址不存在：" + request.getRequestURI();
		return Response.failure(Const.FAILURE_CODE, msg);
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		
		ErrorVo errorVo;
		List<ErrorVo> errorVoList = new ArrayList<>(fieldErrors.size());
		for (FieldError fieldError : fieldErrors) {
			errorVo = new ErrorVo()
					.setField(fieldError.getField())
					.setMessage(fieldError.getDefaultMessage())
					.setRejectedValue(fieldError.getRejectedValue());
			
			errorVoList.add(errorVo);
		}
		
		Map<String, List<ErrorVo>> data = new HashMap<>(8);
		data.put("errors", errorVoList);
		
		buildExceptionInfo(ex, "bean参数校验异常");
		return Response.failure(ResponseMsg.PARAM_NOT_COMPLETE, data);
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(ConstraintViolationException.class)
	public Object handleConstraintViolationException(ConstraintViolationException ex) {
		Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
		Iterator<ConstraintViolation<?>> it = violations.iterator();
		
		ErrorVo errorVo;
		List<ErrorVo> errorVoList = new LinkedList<>();
		while (it.hasNext()) {
			ConstraintViolation<?> violation = it.next();
			PathImpl propertyPath = (PathImpl) violation.getPropertyPath();
			NodeImpl leafNode = propertyPath.getLeafNode();
			int parameterIndex = leafNode.getParameterIndex();
			
			errorVo = new ErrorVo()
					.setField(leafNode.getName())
					.setMessage(violation.getMessage())
					.setRejectedValue(violation.getInvalidValue())
					.setIndex(parameterIndex);
			
			errorVoList.add(errorVo);
		}
		
		// 排序从小到大
		errorVoList.sort(Comparator.comparingInt(ErrorVo::getIndex));
		
		Map<String, List<ErrorVo>> data = new HashMap<>(8);
		data.put("errors", errorVoList);
		
		buildExceptionInfo(ex, "单个参数校验异常");
		return Response.failure(ResponseMsg.PARAM_NOT_COMPLETE, data);
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(Exception.class)
	public Response<?> handleException(Exception ex) {
		return buildExceptionInfo(ex, "未知异常");
	}
	
	private Response<?> buildExceptionInfo(Exception ex, String title) {
		log.error("未知异常", ex);
		
		StringBuilder msg = new StringBuilder();
		msg.append(FORMAT1);
		msg.append("异常信息");
		msg.append(FORMAT2);
		msg.append(ObjectUtil.isNull(ex) ? "没有错误信息" : ex.getMessage());
		
		try {
			InputStream inputStream = CommonUtil.createExceptionHtml(title, ex);
			
			String dir = CommonUtil.buildErrorPath("error");
			String ossUrl = UploadUtil.single(inputStream, dir, "html");
			msg.append(FORMAT1);
			msg.append("异常详细信息地址");
			msg.append(FORMAT2);
			msg.append(ossUrl);
			
			ExceptionUtil.sendRequestInfo(title, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Response<?> response = Response.failure(Const.FAILURE_CODE, title);
		Map<String, String> extra = new HashMap<>(8);
		extra.put("msg", ex.getMessage());
		response.setExtra(extra);
		
		return response;
	}
	
}
