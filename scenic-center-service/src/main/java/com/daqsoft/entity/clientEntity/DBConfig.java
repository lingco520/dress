package com.daqsoft.entity.clientEntity;

import java.io.Serializable;

/**
 * 客户端推送数据库配置信息
 */
public class DBConfig implements Serializable {
    /**
     * 数据库url
     */
    private String db_url;
    /**
     * 数据库账号
     */
    private String db_username;
    /**
     * 数据库密码
     */
    private String db_password;

    public String getDb_url() {
        return db_url;
    }

    public void setDb_url(String db_url) {
        this.db_url = db_url;
    }

    public String getDb_username() {
        return db_username;
    }

    public void setDb_username(String db_username) {
        this.db_username = db_username;
    }

    public String getDb_password() {
        return db_password;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }

    /**
     * 默认构造器
     */
    public DBConfig() {
    }

    /**
     * 构造器
     *
     * @param db_url      数据库url
     * @param db_username 数据库账号
     * @param db_password 数据库密码
     */
    public DBConfig(String db_url, String db_username, String db_password) {
        this.db_url = db_url;
        this.db_username = db_username;
        this.db_password = db_password;
    }
}
