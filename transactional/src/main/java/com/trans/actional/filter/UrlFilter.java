package com.trans.actional.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * 过滤器
 * filter的三种典型应用
 * 1、可以在filter中根据条件决定是否调用chain.doFilter(request, response)方法，即是否让目标资源执行
 * 2、在让目标资源执行之前，可以对request/response作预处理，再让目标资源执行
 * 3、在目标资源执行之后，可以捕获目标资源的执行结果，从而实现一些特殊的功能
 * Created by lenovo on 2019/11/29.
 */
//@WebFilter(filterName = "urlFilter", urlPatterns = {"/testBoot/**"})
//@WebFilter(filterName = "urlFilter", value = "/testBoot")
@WebFilter(filterName = "urlFilter", urlPatterns = {"/*"})
@Configuration
//@Order(value = 2)
public class UrlFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(UrlFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("UrlFilter is init...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("UrlFilter is doFilter...");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String userName = request.getParameter("userName");
        logger.info("userName : {}", userName);
        String requestURI = request.getRequestURI();
        logger.info("requestURI is {}", requestURI);
        //调用该方法后，表示过滤器经过原来的url请求处理方法
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        logger.info("UrlFilter is destroy...");
    }
}
