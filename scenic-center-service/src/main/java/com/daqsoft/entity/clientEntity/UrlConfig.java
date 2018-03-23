package com.daqsoft.entity.clientEntity;

import java.io.Serializable;

/**
 * 推送客户端存放一些URL的地址
 */
public class UrlConfig implements Serializable {
    /**
     * 用户中心登录地址
     */
    private String loginUrl;
    /**
     * 推送客户端配置地址
     */
    private String pushUrl;

    public UrlConfig() {
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public UrlConfig(String loginUrl, String pushUrl) {
        this.loginUrl = loginUrl;
        this.pushUrl = pushUrl;
    }
}
