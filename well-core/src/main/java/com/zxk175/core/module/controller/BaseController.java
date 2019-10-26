package com.zxk175.core.module.controller;

import com.zxk175.core.common.http.HttpMsg;
import com.zxk175.core.common.http.Response;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

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

    protected Response ok(Object data) {
        return Response.ok(data);
    }

    protected Response ok(Object data, Object extra) {
        return Response.ok(data, extra);
    }

    protected Response fail(HttpMsg httpMsg) {
        return Response.fail(httpMsg);
    }

    protected Response fail(String msg) {
        return Response.fail(msg);
    }

    protected Response objectReturn(Object data) {
        return Response.objectReturn(data);
    }
}
