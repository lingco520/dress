package com.daqsoft.clientUtils.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.List;

/**
 * @Title: 模板实体
 * @Author: lyl
 * @Date: 2018-02-06 11:30
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@XStreamAlias("apps")
public class Apps implements Serializable {
    private static final long serialVersionUID = 1L;
    @XStreamImplicit
    private List<AppItem> appItem;

    public List<AppItem> getAppItem() {
        return this.appItem;
    }

    public void setAppItem(List<AppItem> appItem) {
        this.appItem = appItem;
    }

    public Apps() {
    }

    public Apps(List<AppItem> appItem) {
        this.appItem = appItem;
    }

    public String toString() {
        return "Apps [appItem=" + this.appItem + "]";
    }
}
