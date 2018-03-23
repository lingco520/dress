package com.daqsoft.clientUtils.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: ${file_name}
 * @Author: Administrator
 * @Date: 2017/12/20 11:37
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@XStreamAlias("slide")
public class Slide implements Serializable {
    private static final long serialVersionUID = 1L;
    @XStreamAsAttribute
    private String tab = "";
    @XStreamImplicit
    private List<Item> items = new ArrayList();

    public Slide() {
    }

    public Slide(String tab, List<Item> items) {
        if (tab == null) {
            tab = "";
        }

        this.tab = tab;
        this.items = items;
    }

    public String getTab() {
        return this.tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public List<Item> getItems() {
        return this.items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String toString() {
        return "Slide [tab=" + this.tab + ", items=" + this.items + "]";
    }
}