package com.lxy.charge.pojo.system;

import java.time.LocalDateTime;

public class LoginRecord {
    private Integer id;
    private Integer sysUser;
    private LocalDateTime  time;
    private String ip;
    private String name;


    public LoginRecord() {

    }

    public LoginRecord(Integer sysUser, LocalDateTime time, String ip) {
        this.sysUser = sysUser;
        this.time = time;
        this.ip = ip;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSysUser() {
        return sysUser;
    }

    public void setSysUser(Integer sysUser) {
        this.sysUser = sysUser;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LoginRecord{" +
                "id=" + id +
                ", sysUser=" + sysUser +
                ", time=" + time +
                ", ip='" + ip + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
