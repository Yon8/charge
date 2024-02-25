package com.lxy.charge.security.filter;


import com.lxy.charge.security.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  * 所有请求都会被拦截
 *  1、从header中获取token信息
 *  2、如果没有token，则只能访问无需登录就可访问的接口
 *  3、如果有token，从token中获取角色信息
 *  4、检查token是否临期，临期重新刷新
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //读取request头部的token
        String header = request.getHeader("token");

        if (header == null) {
            //如果没有token，无认证放行，相当于没有从token中得到角色信息，只能访问无需登录就可以访问的服务
            chain.doFilter(request, response);
            return;
        }

        //解析token
        Claims token = JwtUtils.getClaimsFromToken(header);
        if (token == null) {
            chain.doFilter(request, response);
            return;
        }

        //从token中获取角色信息
        UsernamePasswordAuthenticationToken authenticationToken = JwtUtils.buildAuthentication(token);

        //将安全令牌设置到上下文中
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //token临近过期，发放新的token
        if (JwtUtils.isRefress(token)) {
            //快过期了，刷新token
            header = JwtUtils.generatorToken(token.getSubject(), token.get("role", String.class));
            response.addHeader("token", header);
        }

        //认证放行
        chain.doFilter(request, response);
        return;
    }
}