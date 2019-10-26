package com.zxk175.notify.config.exception;

import cn.hutool.core.util.ObjectUtil;
import com.zxk175.core.common.bean.dto.ErrorDto;
import com.zxk175.core.common.constant.Const;
import com.zxk175.core.common.http.HttpMsg;
import com.zxk175.core.common.http.Response;
import com.zxk175.core.common.util.ExceptionUtil;
import com.zxk175.core.common.util.common.CommonUtil;
import com.zxk175.core.common.util.net.RequestUtil;
import com.zxk175.core.common.util.upload.UploadUtil;
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
 * 全局异常处理
 *
 * @author zxk175
 * @since 2019-10-12 16:18
 */
@Order(-1000)
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String FORMAT1 = Const.FORMAT1;
    private static final String FORMAT2 = Const.FORMAT2;


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        buildExceptionInfo(ex, "请求方式处理不支持");
        final HttpServletRequest request = RequestUtil.request();
        return String.format("Cannot %s %s", request.getMethod(), request.getRequestURI());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NoHandlerFoundException.class)
    public Response handleNoHandlerFoundException(NoHandlerFoundException ex) {
        buildExceptionInfo(ex, "请求地址不存在");
        final HttpServletRequest request = RequestUtil.request();
        String msg = "请求地址不存在：" + request.getRequestURI();
        return Response.fail(Const.FAIL_CODE, msg);
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        ErrorDto errorDto;
        List<ErrorDto> errorDtoList = new ArrayList<>(fieldErrors.size());
        for (FieldError fieldError : fieldErrors) {
            errorDto = new ErrorDto()
                    .setField(fieldError.getField())
                    .setMessage(fieldError.getDefaultMessage())
                    .setRejectedValue(fieldError.getRejectedValue());

            errorDtoList.add(errorDto);
        }

        Map<String, List<ErrorDto>> data = new HashMap<>(8);
        data.put("errors", errorDtoList);

        buildExceptionInfo(ex, "bean参数校验异常");
        return Response.fail(HttpMsg.PARAM_NOT_COMPLETE, data);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ConstraintViolationException.class)
    public Object handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        Iterator<ConstraintViolation<?>> it = violations.iterator();

        ErrorDto errorDTO;
        List<ErrorDto> errorDtoList = new LinkedList<>();
        while (it.hasNext()) {
            ConstraintViolation<?> violation = it.next();
            PathImpl propertyPath = (PathImpl) violation.getPropertyPath();
            NodeImpl leafNode = propertyPath.getLeafNode();
            int parameterIndex = leafNode.getParameterIndex();

            errorDTO = new ErrorDto()
                    .setField(leafNode.getName())
                    .setMessage(violation.getMessage())
                    .setRejectedValue(violation.getInvalidValue())
                    .setIndex(parameterIndex);

            errorDtoList.add(errorDTO);
        }

        // 排序从小到大
        errorDtoList.sort(Comparator.comparingInt(ErrorDto::getIndex));

        Map<String, List<ErrorDto>> data = new HashMap<>(8);
        data.put("errors", errorDtoList);

        buildExceptionInfo(ex, "单个参数校验异常");
        return Response.fail(HttpMsg.PARAM_NOT_COMPLETE, data);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public Response handleException(Exception ex) {
        return buildExceptionInfo(ex, "未知异常");
    }

    private Response buildExceptionInfo(Exception ex, String title) {
        ex.printStackTrace();

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

        Response response = Response.fail(Const.FAIL_CODE, title);
        Map<String, String> extra = new HashMap<>(8);
        extra.put("msg", ex.getMessage());
        response.setExtra(extra);

        return response;
    }
}
