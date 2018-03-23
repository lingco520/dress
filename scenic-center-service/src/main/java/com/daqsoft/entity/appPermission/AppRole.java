package com.daqsoft.entity.appPermission;

import java.io.Serializable;

/**
 * app角色实体
 */
public class AppRole implements Serializable {
    //id
    private Long id;
    //名称
    private String name;
    //权限唯一标示
    private String code;
    //创建者
    private String user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}