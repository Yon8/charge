package com.lxy.charge.pojo.charge;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.time.LocalDateTime;

public class Fault {
    @Excel(name = "ID", orderNum = "1")
    private Integer id;

    @Excel(name = "充电桩编号", orderNum = "2")
    private Integer stack;

    @Excel(name = "故障时间", orderNum = "3")
    private LocalDateTime time;

    @Excel(name = "故障代码", orderNum = "4")
    private Integer code;

    @Excel(name = "故障描述", orderNum = "5")
    private String description;

    @Excel(name = "故障等级", orderNum = "6")
    private Integer level;

    @Excel(name = "故障状态", orderNum = "7")
    private Integer status;

    @Excel(name = "充电桩名称", orderNum = "8")
    private String name; //stackName

    public Fault() {
    }

    public Fault(Integer id, Integer stack, LocalDateTime time, Integer code, String description, Integer level, Integer status, String name) {
        this.id = id;
        this.stack = stack;
        this.time = time;
        this.code = code;
        this.description = description;
        this.level = level;
        this.status = status;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStack() {
        return stack;
    }

    public void setStack(Integer stack) {
        this.stack = stack;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Fault{" +
                "id=" + id +
                ", stack=" + stack +
                ", time=" + time +
                ", code=" + code +
                ", description='" + description + '\'' +
                ", level=" + level +
                ", status=" + status +
                ", name='" + name + '\'' +
                '}';
    }
}
