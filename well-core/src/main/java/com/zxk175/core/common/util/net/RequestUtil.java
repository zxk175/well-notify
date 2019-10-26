package com.zxk175.core.common.util.net;

import com.google.common.collect.Maps;
import com.zxk175.core.common.util.MyStrUtil;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author zxk175
 * @since 2017/2/21
 */
public class RequestUtil {

    public static HttpServletRequest request() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }

        throw new RuntimeException("Request is null");
    }

    public static String requestUrl(HttpServletRequest request) {
        String requestUrl = request.getRequestURL().toString();
        String queryString = request.getQueryString();

        return requestUrl + (MyStrUtil.isBlank(queryString) ? MyStrUtil.EMPTY : "?" + queryString);
    }

    public static Map<String, String> headers(HttpServletRequest request) {
        Map<String, String> map = Maps.newHashMap();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}
