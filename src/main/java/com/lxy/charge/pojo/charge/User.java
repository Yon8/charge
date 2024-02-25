package com.lxy.charge.pojo.charge;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) //属性值为null时不返回
public class User {
    @Excel(name = "用户ID", orderNum = "1")
    private Integer id;

    @Excel(name = "用户名", orderNum = "2")
    private String name;

    @Excel(name = "密码", orderNum = "3")
    private String password;

    @Excel(name = "电话", orderNum = "4")
    private String phone;

    @Excel(name = "车牌号", orderNum = "5")
    private String plate;

    @Excel(name = "余额", orderNum = "6")
    private Double balance;
    public User() {
    }

    public User(Integer id, String name, String password, String phone, String plate, Double balance) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.plate = plate;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", plate='" + plate + '\'' +
                ", balance=" + balance +
                '}';
    }
}
