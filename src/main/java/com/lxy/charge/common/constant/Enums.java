package com.lxy.charge.common.constant;




import java.util.Objects;


public enum Enums {

    /**
     * 系统字典类型
     */
    SYS_DIC_TYPE(0, "dic_type", "系统类"),
    SERVE_DIC_TYPE(1, "dic_type", "业务类"),

    /**
     * 认证相关
     */
    AUTH_NO_TOKEN(401, "auth_type", "用户凭证已过期，请重新登录！"),
    AUTH_NO_ACCESS(403, "auth_type", "无访问权限，请核实!"),
    AUTH_NONEXISTENT(404, "auth_type", "请求路径不存在"),

    /**
     * 公共
     */
    NOT_DEL_FALG(0, "del_falg", "正常"),
    IS_DEL_FALG(1, "del_falg", "已删除"),

    /**
     * 角色类型
     */
    ROLE_SYSTEM(0, "role_type", "平台角色"),
    ROLE_NOT_SYSTEM(1, "role_type", "非平台角色"),

    /**
     * 用户信息相关
     */
    USER_GENDER_MALE(0, "user_gender", "男"),
    USER_GENDER_GIRL(1, "user_gender", "女"),

    /**
     * 消息状态
     */
    MESSAGE_UNTREATED(0, "message_status", "未处理"),
    MESSAGE_PROCESSED(1, "message_status", "已处理"),

    /**
     * 菜单类型
     */
    MENU_TYPE_0(0, "menu_type", "按钮"),
    MENU_TYPE_1(1, "menu_type", "菜单"),
    MENU_TYPE_2(2, "menu_type", "嵌套链接"),
    MENU_TYPE_3(3, "menu_type", "跳转链接"),

    /**
     * 状态
     */
    STATUS_0(0, "status", "未启用"),
    STATUS_1(1, "status", "启用") ;

    private Integer key;
    private String group;
    private String value;

    public Integer getKey() {
        return key;
    }

    public String getGroup() {
        return group;
    }

    public String getValue() {
        return value;
    }

    public static String getValue(Integer key) {
        for (Enums item : Enums.values()) {
            if (Objects.equals(key, item.key)) {
                return item.getValue();
            }
        }
        return null;
    }


    public static String getVlaueByGroup(Integer key, String group) {
        for (Enums item : Enums.values()) {
            if (Objects.equals(key, item.key) && Objects.equals(group, item.group)) {
                return item.getValue();
            }
        }
        return null;
    }

    Enums() {
    }

    Enums(Integer key, String group, String value) {
        this.key = key;
        this.group = group;
        this.value = value;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setValue(String value) {
        this.value = value;
    }
}