package com.lxy.charge.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

//响应结果类
public class Wrapper<T> implements Serializable {

    //以下定义一系列的状态码和消息
    // 成功码
    public static final int SUCCESS_CODE = 200;

    // 成功信息.
    public static final String SUCCESS_MESSAGE = "操作成功";

    //错误码.
    public static final int ERROR_CODE = 500;

    //错误信息.
    public static final String ERROR_MESSAGE = "系统异常，请稍后重试！";

    // 错误码：参数非法
    public static final int ILLEGAL_ARGUMENT_CODE_ = 400;

    // 错误信息：参数非法
    public static final String ILLEGAL_ARGUMENT_MESSAGE = "请求参数非法，请核查！";

    //错误码：未登录
    public static final int NOT_LOGIN_CODE = 401;

    // 错误信息：未登录提示
    public static final String NOT_LOGIN_MESSAGE = "您需要先登录才能访问！";

    //错误码：参数非法
    public static final int AUTHORIZATION_CODE = 403;

    // 错误信息：参数非法
    public static final String AUTHORIZATION_MESSAGE = "用户凭证已过期，请重新登录！";

    //以下是三个成员变量

    private int code;//状态码

    private String message;//返回消息

    private T result;//返回结果数据

    // 默认无参构造方法. default code=200，操作成功
    public Wrapper() {
        this(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    /**
     * 构造方法，有两个参数.
     *
     * @param code    the code
     * @param message the message
     */
    public Wrapper(int code, String message) {
        this.code(code).message(message);
    }

    /**
     * 构造方法，有三个参数.
     *
     * @param code    the code
     * @param message the message
     * @param result  the result
     */
    public Wrapper(int code, String message, T result) {
        super();
        this.code(code).message(message).result(result);
    }

    //以下是get和set方法
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    //设置状态码 ，返回自身对象的引用.
    public Wrapper<T> code(int code) {
        this.setCode(code);
        return this;
    }

    //设置消息 ，返回自身对象的引用.
    public Wrapper<T> message(String message) {
        this.setMessage(message);
        return this;
    }

    //设置结果数据 ，返回自身对象的引用.
    public Wrapper<T> result(T result) {
        this.setResult(result);
        return this;
    }

    @Override
    public String toString() {
        return "Wrapper{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }

    //在json序列化时将属性忽略掉，标记在属性或者方法上，返回的json数据即不包含该属性。
    @JsonIgnore
    public boolean isSuccess() {
        return this.getCode() == Wrapper.SUCCESS_CODE;
    }

    @JsonIgnore
    public boolean isFail() {
        return !isSuccess();
    }

}

