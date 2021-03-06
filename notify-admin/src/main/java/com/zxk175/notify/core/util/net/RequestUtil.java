package com.zxk175.notify.core.util.net;

import com.zxk175.notify.core.util.MyStrUtil;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zxk175
 * @since 2020-03-29 13:45
 */
public class RequestUtil {

    public static HttpServletRequest request() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new RuntimeException("Request is null");
        }

        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    public static String requestUrl(HttpServletRequest request) {
        String requestUrl = new UrlPathHelper().getOriginatingRequestUri(request);

        String get = "get";
        if (get.equalsIgnoreCase(request.getMethod())) {
            String queryString = request.getQueryString();
            requestUrl += MyStrUtil.isBlank(queryString) ? MyStrUtil.EMPTY : "?" + queryString;
        }

        return requestUrl;
    }

    public static Map<String, String> headers(HttpServletRequest request) {
        Map<String, String> headerMap = new HashMap<>(16);
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            headerMap.put(key, value);
        }

        return headerMap;
    }

}
