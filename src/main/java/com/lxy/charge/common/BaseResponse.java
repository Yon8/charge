package com.lxy.charge.common;

//通用的响应结果类
public class BaseResponse<T> {
    private Integer code;//状态码
    private String msg;//描述信息
    private T data;//响应数据，由于返回数据类型不确定，因此使用泛型

    public BaseResponse() {
    }

    public BaseResponse(StatusCode code) {
        this.code = code.getCode();
    }

    public BaseResponse(StatusCode code, String msg) {
        this.code = code.getCode();
        this.msg = msg;
    }

    public BaseResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
