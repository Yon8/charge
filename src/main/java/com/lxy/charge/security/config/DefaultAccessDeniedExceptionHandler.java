package com.lxy.charge.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/*
AccessDeineHandler 用来解决认证过的用户访问无权限资源时的异常
 */
public class DefaultAccessDeniedExceptionHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {
        //设置响应数据的编码
        response.setCharacterEncoding("utf-8");
        //告诉浏览器要响应的内容类型,以及编码
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(200);
        Map<String,Object> map=new HashMap<>();
        map.put("code",403);
        map.put("message","没有此资源的操作权限");
        PrintWriter out=response.getWriter();
        out.println(new ObjectMapper().writeValueAsString(map));
        out.flush();
        out.close();
    }
}