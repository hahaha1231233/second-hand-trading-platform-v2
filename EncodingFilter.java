package com.second_hand_trading_platform.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class EncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("编码过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // 设置请求编码
        req.setCharacterEncoding("UTF-8");

        // ⚠️ 只设置响应编码，不设置Content-Type
        // 让Servlet自己决定Content-Type
        res.setCharacterEncoding("UTF-8");

        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
        System.out.println("编码过滤器销毁");
    }
}