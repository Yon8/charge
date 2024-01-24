package com.lxy.charge.pojo.charge;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class Warden {
    @Excel(name = "管理员编号",orderNum = "1")
    private String id;
    @Excel(name = "管理员名称",orderNum = "2")
    private String name;
    @Excel(name = "密码",orderNum = "3")
    private String password;
    @Excel(name = "所属站点",orderNum = "4")
    private String station;
    @Excel(name = "电话",orderNum = "5")
    private String phone;
    @Excel(name = "权限",orderNum = "6")

    private String authority;

    @Excel(name = "站点名称", orderNum = "7")
    private String stationName;

    public Warden() {

    }

    public Warden(String id, String name, String password, String station, String phone, String authority, String stationName) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.station = station;
        this.phone = phone;
        this.authority = authority;
        this.stationName = stationName;
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

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
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

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Override
    public String toString() {
        return "Warden{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", station='" + station + '\'' +
                ", phone='" + phone + '\'' +
                ", authority='" + authority + '\'' +
                ", stationName='" + stationName + '\'' +
                '}';
    }
}
