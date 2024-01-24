package com.lxy.charge.utils;

import org.springframework.util.StringUtils;

//响应结果包装类
public class WrapMapper {

    //无参构造方法
    private WrapMapper() {
    }

    //静态方法，返回响应结果对象（通过三个参数的构造方法创建响应结果对象，并返回）
    public static <E> Wrapper<E> wrap(int code, String message, E o) {
        return new Wrapper<E>(code, message, o);
    }

    //静态方法，返回响应结果对象（通过两个参数的构造方法创建响应结果对象，并返回）
    public static <E> Wrapper<E> wrap(int code, String message) {
        return new Wrapper<E>(code, message);
    }

    //静态方法，返回响应结果对象（通过一个参数状态码的构造方法创建响应结果对象，并返回）
    public static <E> Wrapper<E> wrap(int code) {
        return wrap(code, null);
    }

    //静态方法，返回响应结果对象（传入异常对象，通过两个参数的构造方法创建响应结果对象，并返回）
    public static <E> Wrapper<E> wrap(Exception e) {
        return new Wrapper<E>(Wrapper.ERROR_CODE, e.getMessage());
    }

    //静态方法，返回响应结果对象（通过三个参数的构造方法创建响应结果对象，并返回）
    public static <E> E unWrap(Wrapper<E> wrapper) {
        return wrapper.getResult();
    }

    public static <E> Wrapper<E> illegalArgument() {
        return wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_, Wrapper.ILLEGAL_ARGUMENT_MESSAGE);
    }

    public static <E> Wrapper<E> error() {
        return wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
    }

    public static <E> Wrapper<E> error(String message) {
        return wrap(Wrapper.ERROR_CODE, StringUtils.isEmpty(message) ? Wrapper.ERROR_MESSAGE : message);
    }

    public static <E> Wrapper<E> error(int code, String message) {
        return wrap(code, StringUtils.isEmpty(message) ? Wrapper.ERROR_MESSAGE : message);
    }

    public static <E> Wrapper<E> ok() {
        return new Wrapper<E>();
    }

    public static <E> Wrapper<E> wrap(E o) {
        return new Wrapper<>(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, o);
    }

    public static <E> Wrapper<E> ok(E o) {
        return new Wrapper<>(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, o);
    }

    public static <E> Wrapper<E> success() {
        return new Wrapper<>(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
    }

}

