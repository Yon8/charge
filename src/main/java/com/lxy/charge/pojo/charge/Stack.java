package com.lxy.charge.pojo.charge;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
@JsonInclude(JsonInclude.Include.NON_NULL) //属性值为null时不返回
public class Stack {
    @Excel(name = "充电桩编号", orderNum = "1")
    private Integer id;
    @Excel(name = "充电桩名称", orderNum = "2")
    private String name;
    @Excel(name = "充电桩状态", orderNum = "3")
    private Integer status;
    @Excel(name = "所属站点", orderNum = "4")
    private Integer station;
    @Excel(name = "电压", orderNum = "5")
    private Double voltage;
    @Excel(name = "电流", orderNum = "6")
    private Double current;
    @Excel(name = "用户", orderNum = "7")
    private Integer user;
    @Excel(name = "开始时间", orderNum = "8")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private LocalDateTime startTime;
    @Excel(name = "电量", orderNum = "9")
    private Double quantity;
    @Excel(name = "所属站点名称", orderNum = "10")
    private String stationName;

    public Stack() {
    }

    public Stack(Integer id, String name, Integer status, Integer station, Double voltage, Double current, Integer user, LocalDateTime startTime, Double quantity, String stationName) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.station = station;
        this.voltage = voltage;
        this.current = current;
        this.user = user;
        this.startTime = startTime;
        this.quantity = quantity;
        this.stationName = stationName;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStation() {
        return station;
    }

    public void setStation(Integer station) {
        this.station = station;
    }

    public Double getVoltage() {
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }

    public Double getCurrent() {
        return current;
    }

    public void setCurrent(Double current) {
        this.current = current;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Override
    public String toString() {
        return "Stack{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", station=" + station +
                ", voltage=" + voltage +
                ", current=" + current +
                ", user=" + user +
                ", startTime=" + startTime +
                ", quantity=" + quantity +
                ", stationName='" + stationName + '\'' +
                '}';
    }
}
