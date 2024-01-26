package com.lxy.charge.pojo.charge;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) //属性值为null时不返回
public class Station {
    @Excel(name = "站点编号", orderNum = "1")
    private Integer id;

    @Excel(name = "站点名称", orderNum = "2")
    private String name;

    @Excel(name = "站点数量", orderNum = "3")
    private Integer count;

    @Excel(name = "站点位置", orderNum = "4")
    private String location;

    @Excel(name = "站点状态", orderNum = "5")
    private String status;

    @Excel(name = "管理员编号", orderNum = "6")
    private Integer warden;

    @Excel(name = "管理员名称", orderNum = "7")
    private String wardenName;


    public Station() {
    }

    public Station(Integer id, String name, Integer count, String location, String status, Integer warden, String wardenName) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.location = location;
        this.status = status;
        this.warden = warden;
        this.wardenName = wardenName;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
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

    public Integer getWarden() {
        return warden;
    }

    public void setWarden(Integer warden) {
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", location='" + location + '\'' +
                ", status='" + status + '\'' +
                ", warden=" + warden +
                ", wardenName='" + wardenName + '\'' +
                '}';
    }
}
