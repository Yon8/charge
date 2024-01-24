package com.lxy.charge.pojo.charge;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class Warden {
    @Excel(name = "管理员编号",orderNum = "1")
    private String id;
    @Excel(name = "管理员名称",orderNum = "2")
    private String name;
    @Excel(name = "密码",orderNum = "3")
    private String password;
    @Excel(name = "部门",orderNum = "4")
    private String department;
    @Excel(name = "电话",orderNum = "5")
    private String phone;
    @Excel(name = "权限",orderNum = "6")

    private String authority;

    public Warden() {
    }

    public Warden(String id, String name, String password, String department, String phone, String authority) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.department = department;
        this.phone = phone;
        this.authority = authority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "WardenService{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", department='" + department + '\'' +
                ", phone=" + phone +
                ", authority='" + authority + '\'' +
                '}';
    }
}
