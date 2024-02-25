package com.lxy.charge.pojo.system;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

//角色实体类
public class SysRole {
    private Integer id;//角色id

    private String name;//角色名称

    private Integer status;//状态，是否已经启用

    private Integer type;//角色类型，0，平台角色，1，非平台角色


    private Integer creator;//创建人
    private String creatorName;//创建人名称

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private LocalDateTime updateTime;//更新时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private LocalDateTime createTime;//创建时间

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