package com.lxy.charge.pojo.system;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

//角色实体类
@JsonInclude(JsonInclude.Include.NON_NULL) //属性值为null时不返回
public class SysRole {

    @Excel(name = "角色ID", orderNum = "1")
    private Integer id; // 角色id

    @Excel(name = "角色名称", orderNum = "2")
    private String name; // 角色名称

    @Excel(name = "状态", orderNum = "3")
    private Integer status; // 状态，是否已经启用

    @Excel(name = "角色类型", orderNum = "4")
    private Integer type; // 角色类型，0-平台角色，1-非平台角色

    @Excel(name = "创建人", orderNum = "5")
    private Integer creator; // 创建人

    @Excel(name = "创建人名称", orderNum = "6")
    private String creatorName; // 创建人名称

    @Excel(name = "更新时间", orderNum = "7")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private LocalDateTime updateTime; // 更新时间

    @Excel(name = "创建时间", orderNum = "8")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private LocalDateTime createTime; // 创建时间

    public SysRole() {
    }

    public SysRole(Integer id, String name, Integer status, Integer type, Integer creator, String creatorName, LocalDateTime updateTime, LocalDateTime createTime) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.type = type;
        this.creator = creator;
        this.creatorName = creatorName;
        this.updateTime = updateTime;
        this.createTime = createTime;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
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

    @Override
    public String toString() {
        return "SysRole{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", creator=" + creator +
                ", creatorName='" + creatorName + '\'' +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                '}';
    }
}