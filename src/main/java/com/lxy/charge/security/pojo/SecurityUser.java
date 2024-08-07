package com.lxy.charge.security.pojo;


import com.lxy.charge.pojo.system.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class SecurityUser implements UserDetails {
    private Integer id;

    private String username;

    private String name;

    private String password;

    private Integer type;

    private Collection<? extends GrantedAuthority> authorities;

    public SecurityUser() {
    }

    public SecurityUser(SysUser user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.password = user.getPassword();
        this.authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        // 账号是否未过期，默认是false
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 账号是否未锁定，默认是false
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 账号凭证是否未过期，默认是false
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}