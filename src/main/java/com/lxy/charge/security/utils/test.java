package com.lxy.charge.security.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class test {
    public static void main(String[] args) {
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String password = bc.encode("123456");
        System.out.println(password);
    }
}
