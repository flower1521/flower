package com.trans.actional.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by lenovo on 2019/11/29.
 */
public class InterceptorConfig implements HandlerInterceptor {
    private static Logger logger = LoggerFactory.getLogger(InterceptorConfig.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("------------开始进入请求地址拦截------------");
        String requestURI = request.getRequestURI();
        logger.info("InterceptorConfig preHandle requestURI : {}", requestURI);
        String userName = request.getParameter("userName");
        logger.info("userName : {}", userName);
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        if (ObjectUtils.isEmpty(user)) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
