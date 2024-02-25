package com.lxy.charge.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lxy.charge.security.pojo.SecurityUser;
import com.lxy.charge.security.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

//登录会被该Filter拦截
//按照 Spring Security 框架的默认配置，UsernamePasswordAuthenticationFilter
// 只拦截 /login 的action，也即表单登录提交action，不会拦截其它请求。
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //得到用户名和密码，然后去验证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println(password);
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password,
                        new ArrayList<>()
                )
        );
    }

    //如果认证过程中没有发生异常，那么即为认证成功。
    //身份验证成功后，在response的header增加token
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {

        SecurityUser user = (SecurityUser) authResult.getPrincipal();

        //获取用户名
        String userName = user.getUsername();
        //获取user中角色
        String role
                = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        //生成token
        String token = JwtUtils.generatorToken(userName, role);
        //在header中增加token
        response.addHeader("token", token);

        //返回登录成功信息
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        response.setStatus(200);
        Map<String,Object> map = new HashMap<>();
        map.put("code",0);
        map.put("msg",user);
        ObjectMapper om = new ObjectMapper();
        out.write(om.writeValueAsString(map));
        out.flush();
        out.close();
    }

    //如果认证过程中发生异常，即代表认证失败。
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        response.setStatus(200);
        Map<String,Object> map = new HashMap<>();
        map.put("code",9999);
        map.put("msg",failed.getMessage());
        ObjectMapper om = new ObjectMapper();
        out.write(om.writeValueAsString(map));
        out.flush();
        out.close();
    }
}