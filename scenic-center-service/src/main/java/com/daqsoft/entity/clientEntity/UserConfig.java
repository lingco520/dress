package com.daqsoft.entity.clientEntity;

import java.io.Serializable;

/**
 * 推送客户端用户中心
 */
public class UserConfig implements Serializable {
    /**
     * 电话号码(可选)
     */
    private String phone;
    /**
     * 用户名
     */
    private String username;
    /***
     * 密码
     */
    private String password;

    /***
     *
     * 默认构造器
     */
    public UserConfig() {
        phone = "";
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 构造方法
     *
     * @param username 用户名
     * @param password 密码
     */
    public UserConfig(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * 构造器
     *
     * @param phone    电话号码
     * @param username 用户名
     * @param password 密码
     */
    public UserConfig(String phone, String username, String password) {
        this.phone = phone;
        this.username = username;
        this.password = password;
    }
}
