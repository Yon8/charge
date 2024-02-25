package com.lxy.charge.security.config;


import com.lxy.charge.security.filter.JwtAuthenticationFilter;
import com.lxy.charge.security.filter.JwtLoginFilter;
import com.lxy.charge.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    SecurityService customUserDetailsService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .authorizeRequests()
                .antMatchers("/chargeManage/**").hasAnyRole("admin","superAdmin") //admin和superAdmin 都有权访问
                .antMatchers("/systemManage/**").hasRole("superAdmin") //只有superAdmin有权访问
                .anyRequest().authenticated()//其它服务 不登录后即可以访问
                //配置登录接口
                .and().formLogin().loginPage("/login_page")
                //security提供的登录接口
                .loginProcessingUrl("/login") //参数 /login?username=xx&password=xx
                .permitAll()
                //开启跨域 cors()
                .and().cors().configurationSource(corsConfigurationSource())
                .and().csrf().disable()
                //登录拦截器 成功登录 发放token  /login
                .addFilter(new JwtLoginFilter(authenticationManager()))
                //其它访问拦截器 验证token是否有效 有效才放行
                .addFilter(new JwtAuthenticationFilter(authenticationManager()));
        http.exceptionHandling()
                .authenticationEntryPoint(new DefaultAuthenticationEntryPoint())
                .accessDeniedHandler(new DefaultAccessDeniedExceptionHandler());
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 设置用户User,根据用户名,找到用户User，比对密码 获取role
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }
    //配置加密方式
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //设置无需权限就可以访问的资源
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/login_page");
        web.ignoring().antMatchers("/upload/**");
        web.ignoring().antMatchers("/register");
        web.ignoring().antMatchers("/index.html");
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/js/**");
    }

    //spring security 配置跨域访问资源
    private CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern("*"); //同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；
        corsConfiguration.addAllowedHeader("*");  //header，允许哪些header
        corsConfiguration.addAllowedMethod("*");  //允许的请求方法，PSOT、GET、PUT等
        corsConfiguration.addExposedHeader("token"); //拓展header 浏览器放过redponse的token 不然跨域登录收不到token
        corsConfiguration.setAllowCredentials(true); //允许浏览器携带cookie
        source.registerCorsConfiguration("/**", corsConfiguration); //配置允许跨域访问的url
        return source;
    }
}