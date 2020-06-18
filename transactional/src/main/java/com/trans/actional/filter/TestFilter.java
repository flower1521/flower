package com.trans.actional.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by lenovo on 2019/11/30.
 */
@WebFilter(filterName = "testFilter", urlPatterns = "/*")
//@Configuration
public class TestFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(TestFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("TestFilter init...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("TestFilter doFilter...");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        logger.info("TestFilter destroy...");
    }
}
