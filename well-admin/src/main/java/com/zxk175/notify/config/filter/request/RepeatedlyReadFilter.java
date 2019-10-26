package com.zxk175.notify.config.filter.request;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * https://blog.csdn.net/bjo2008cn/article/details/53888923
 *
 * @author zxk175
 * @since 2019-10-12 16:18
 */
public class RepeatedlyReadFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        request = new RepeatedlyReadWrapper(httpRequest);
        filterChain.doFilter(request, response);
    }
}
