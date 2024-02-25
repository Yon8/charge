package com.lxy.charge.pojo.charge;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Bill {
    @Excel(name = "ID", orderNum = "1")
    private Integer id;

    @Excel(name = "用户ID", orderNum = "2")
    private Integer user;

    @Excel(name = "用户名称", orderNum = "3")
    private String name;

    @Excel(name = "账单状态", orderNum = "4")
    private Integer status;

    @Excel(name = "开始时间", orderNum = "5")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private LocalDateTime startTime;

    @Excel(name = "结束时间", orderNum = "6")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private LocalDateTime endTime;

    @Excel(name = "充电类型", orderNum = "7")
    private Integer type;

    @Excel(name = "所属站点", orderNum = "8")
    private Integer station;

    @Excel(name = "充电桩编号", orderNum = "9")
    private Integer stack;

    @Excel(name = "充电量", orderNum = "10")
    private Double quantity;

    @Excel(name = "金额", orderNum = "11")
    private Double amount;

    @Excel(name = "站点名称", orderNum = "12")
    private String stationName;

    @Excel(name = "充电桩名称", orderNum = "13")
    private String stackName;


    public Bill() {
    }

    public Bill(Integer id, Integer user, String name, Integer status, LocalDateTime startTime, LocalDateTime endTime, Integer type, Integer station, Integer stack, Double quantity, Double amount, String stationName, String stackName) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.station = station;
        this.stack = stack;
        this.quantity = quantity;
        this.amount = amount;
        this.stationName = stationName;
        this.stackName = stackName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStackName() {
        return stackName;
    }

    public void setStackName(String stackName) {
        this.stackName = stackName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStation() {
        return station;
    }

    public void setStation(Integer station) {
        this.station = station;
    }

    public Integer getStack() {
        return stack;
    }

    public void setStack(Integer stack) {
        this.stack = stack;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", user=" + user +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", type=" + type +
                ", station=" + station +
                ", stack=" + stack +
                ", quantity=" + quantity +
                ", amount=" + amount +
                ", stationName='" + stationName + '\'' +
                ", stackName='" + stackName + '\'' +
                '}';
    }
}
