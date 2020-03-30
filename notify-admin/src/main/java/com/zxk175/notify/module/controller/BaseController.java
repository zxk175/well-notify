package com.zxk175.notify.module.controller;

import com.zxk175.notify.core.http.Response;
import com.zxk175.notify.core.http.ResponseMsg;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zxk175
 * @since 2019/03/16 17:17
 */
public class BaseController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;


    @ModelAttribute
    public void setRequestAndResponse(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    protected Response<Object> ok() {
        return Response.success();
    }

    protected Response<Object> ok(Object data) {
        return Response.success(data);
    }

    protected Response<Object> ok(Object data, Object extra) {
        return Response.success(data, extra);
    }

    protected Response<Object> fail(ResponseMsg responseMsg) {
        return Response.failure(responseMsg);
    }

    protected Response<Object> fail(String msg) {
        return Response.failure(msg);
    }

}
