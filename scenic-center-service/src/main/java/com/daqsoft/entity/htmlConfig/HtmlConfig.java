package com.daqsoft.entity.htmlConfig;

/**
 * @Title: 模板选择配置实体
 * @Author: lyl
 * @Date: 2018/02/26 17:40
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
public class HtmlConfig {
    private Long id;
    private String hkey;
    private String vcode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getHkey() {
        return hkey;
    }

    public void setHkey(String hkey) {
        this.hkey = hkey;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }
}