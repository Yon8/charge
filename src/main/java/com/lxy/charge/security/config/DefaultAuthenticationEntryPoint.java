package com.lxy.charge.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/*
认证入口点，当用户请求处理过程中遇见认证异常时，被异常处理器（ExceptionTranslationFilter）用来开启特定的认证流程。
request是遇到了认证异常的用户请求，response 是将要返回给用户的响应，authException 请求过程中遇见的认证异常。
AuthenticationEntryPoint 实现类，可以修改响应头属性信息或开启新的认证流程。
AuthenticationEntryPoint 用来解决匿名用户访问无权限资源时的异常
 */
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(

            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException e) throws IOException, ServletException {
        //设置响应数据的编码
        response.setCharacterEncoding("utf-8");
        response.setStatus(200);
        //告诉浏览器要响应的内容类型,以及编码
        response.setContentType("application/json;charset=utf-8");
        Map<String,Object> map=new HashMap<>();
        map.put("code","401");
        map.put("message","请先登录");
        PrintWriter out=response.getWriter();
        out.println(new ObjectMapper().writeValueAsString(map));
        out.flush();
        out.close();
    }
}