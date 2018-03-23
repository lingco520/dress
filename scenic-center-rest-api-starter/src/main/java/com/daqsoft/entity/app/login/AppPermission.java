package com.daqsoft.entity.app.login;

import java.io.Serializable;

/**
 * app权限实体
 */
public class AppPermission implements Serializable {
    //id
    private Long id;
    //名称
    private String name;
    //状态
    private Long status;
    //排序
    private Long sort;
    //父级权限ID
    private Long pid;
    //权限唯一标示
    private String code;
    //层级结构(,pid,id,)
    private String level;
    //层级(1-4级)
    private Integer nlevel;

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

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getNlevel() {
        return nlevel;
    }

    public void setNlevel(Integer nlevel) {
        this.nlevel = nlevel;
    }
}