package com.blog.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Component
public class TestFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest) servletRequest ;
        HttpServletResponse httpServletResponse=(HttpServletResponse) servletResponse;

        LOGGER.info("********************************* Filter Result Are  **************************************************");
        LOGGER.info("Local Port : "+servletRequest.getLocalPort());
        LOGGER.info("Server : "+servletRequest.getServerName());

        LOGGER.info("Method: "+httpServletRequest.getMethod());
        LOGGER.info("Path: "+httpServletRequest.getServletPath());
        LOGGER.info("URI : "+httpServletRequest.getRequestURL());
        LOGGER.info("URL: "+httpServletRequest.getRequestURI());

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }


}
