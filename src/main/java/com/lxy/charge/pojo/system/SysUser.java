package com.lxy.charge.pojo.system;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

//用户实体类
@JsonInclude(JsonInclude.Include.NON_NULL) //属性值为null时不返回
public class SysUser{

    @Excel(name = "用户ID", orderNum = "1")
    private Integer id; // 用户id

    @Excel(name = "用户名", orderNum = "2")
    private String username; // 用户名

    @Excel(name = "密码", orderNum = "3")
    private String password; // 密码

    @Excel(name = "昵称", orderNum = "4")
    private String name; // 昵称

    @Excel(name = "角色", orderNum = "5")
    private String role; // 角色名称

    @Excel(name = "角色ID", orderNum = "6")
    private Integer roleId; // 角色ID

    @Excel(name = "性别", orderNum = "7")
    private Integer gender; // 性别：0-男，1-女

    @Excel(name = "出生日期", orderNum = "8")
    private LocalDate birthday; // 出生日期

    @Excel(name = "地址", orderNum = "9")
    private String address; // 地址

    @Excel(name = "手机号", orderNum = "10")
    private String phone; // 手机号

    @Excel(name = "邮件", orderNum = "11")
    private String email; // 邮件

    @Excel(name = "删除状态", orderNum = "12")
    private Integer delFlag; // 删除状态：0-正常，1-已删除

    @Excel(name = "更新时间", orderNum = "13")
    private LocalDateTime updateTime; // 更新时间

    @Excel(name = "创建时间", orderNum = "14")
    private LocalDateTime createTime; // 创建时间

    @Excel(name = "备注", orderNum = "15")
    private String remarks; // 备注
    public SysUser() {
    }

    public SysUser(Integer id, String username, String password, String name, String role, Integer roleId, Integer gender, LocalDate birthday, String address, String phone, String email, Integer delFlag, LocalDateTime updateTime, LocalDateTime createTime, String remarks) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
        this.roleId = roleId;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.delFlag = delFlag;
        this.updateTime = updateTime;
        this.createTime = createTime;
        this.remarks = remarks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", roleId=" + roleId +
                ", gender=" + gender +
                ", birthday=" + birthday +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", delFlag=" + delFlag +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}