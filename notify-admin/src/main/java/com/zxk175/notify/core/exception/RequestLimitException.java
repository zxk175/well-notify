package com.zxk175.notify.core.exception;

/**
 * @author zxk175
 * @since 2020-03-29 13:51
 */
public class RequestLimitException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RequestLimitException() {
        super("HTTP请求超出设定的限制");
    }

}
