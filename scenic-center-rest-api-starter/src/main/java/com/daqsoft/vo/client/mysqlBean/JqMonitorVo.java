package com.daqsoft.vo.client.mysqlBean;

/**
 * @Author: ziy .
 * @Date: Created in 2017/4/18.
 * @Version: V3.0.0.
 * @describe:监控数据
 */


public class JqMonitorVo {


    private Long id;
    private String url;
    private String name;

    public JqMonitorVo(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
