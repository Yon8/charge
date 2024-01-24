package com.lxy.charge.common;

//通用状态码枚举类型
//通用的响应结果类

//通用状态码枚举类型
public enum StatusCode {
    //状态码类
    Success(0,"成功"),
    Fail(-1,"失败"),
    InvalidParams(201,"非法参数"),
    InvalidGrantType(202,"非法授权类型");

    //状态码
    private Integer code;
    //描述信息
    private String msg;

    //构造方法
    StatusCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
