package com.trans.actional.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lenovo on 2019/11/30.
 */
@WebFilter(filterName = "myCharacterEncodingFilter", urlPatterns = {"/*"})
//@Configuration
//@Order(value = 1)
public class MyCharacterEncodingFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(MyCharacterEncodingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("CharacterEncodingFilter init...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("CharacterEncodingFilter doFilter...");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("CharacterEncodingFilter destroy");
    }
}
