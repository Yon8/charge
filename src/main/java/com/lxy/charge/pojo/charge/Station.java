package com.lxy.charge.pojo.charge;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class Station {
    @Excel(name = "站点编号", orderNum = "1")
    private String id;

    @Excel(name = "站点名称", orderNum = "2")
    private String name;

    @Excel(name = "站点数量", orderNum = "3")
    private int count;

    @Excel(name = "站点位置", orderNum = "4")
    private String location;

    @Excel(name = "站点状态", orderNum = "5")
    private String status;

    @Excel(name = "管理员编号", orderNum = "6")
    private String warden;

    @Excel(name = "管理员名称", orderNum = "7")
    private String wardenName;


    public Station() {
    }

    public Station(String id, String name, int count, String location, String status, String warden, String wardenName) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.location = location;
        this.status = status;
        this.warden = warden;
        this.wardenName = wardenName;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWarden() {
        return warden;
    }

    public void setWarden(String warden) {
        this.warden = warden;
    }

    public String getWardenName() {
        return wardenName;
    }

    public void setWardenName(String wardenName) {
        this.wardenName = wardenName;
    }

    @Override
    public String toString() {
        return "Station{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", location='" + location + '\'' +
                ", status='" + status + '\'' +
                ", warden='" + warden + '\'' +
                ", wardenName='" + wardenName + '\'' +
                '}';
    }
}
