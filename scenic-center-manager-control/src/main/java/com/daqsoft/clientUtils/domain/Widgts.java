package com.daqsoft.clientUtils.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: 模板类
 * @Author: lyl
 * @Date: 2018-02-06 11:38
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@XStreamAlias("widgts")
public class Widgts implements Serializable {
    private static final long serialVersionUID = 1L;
    @XStreamAsAttribute
    @XStreamAlias("name")
    private String name = "";
    @XStreamAsAttribute
    @XStreamAlias("tab")
    private String tab = "";
    @XStreamAsAttribute
    @XStreamAlias("row")
    private String row = "";
    @XStreamAsAttribute
    @XStreamAlias("col")
    private String col = "";
    @XStreamAsAttribute
    @XStreamAlias("slide")
    private String slide = "";
    @XStreamImplicit
    public List<Widgt> widgt = new ArrayList();

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTab() {
        return this.tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public List<Widgt> getWidgt() {
        return this.widgt;
    }

    public void setWidgt(List<Widgt> widgt) {
        this.widgt = widgt;
    }

    public String getRow() {
        return this.row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getCol() {
        return this.col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getSlide() {
        return this.slide;
    }

    public void setSlide(String slide) {
        this.slide = slide;
    }

    public Widgts() {
    }

    public Widgts(String name, String tab, String row, String col, String slide, List<Widgt> widgt) {
        if (name == null) {
            name = "";
        }

        if (tab == null) {
            tab = "";
        }

        if (row == null) {
            row = "";
        }

        if (col == null) {
            col = "";
        }

        if (slide == null) {
            slide = "";
        }

        this.name = name;
        this.tab = tab;
        this.row = row;
        this.col = col;
        this.slide = slide;
        this.widgt = widgt;
    }

    public Widgts(String name, String tab, String row, String col, List<Widgt> widgt) {
        if (name == null) {
            name = "";
        }

        if (tab == null) {
            tab = "";
        }

        if (row == null) {
            row = "";
        }

        if (col == null) {
            col = "";
        }

        this.name = name;
        this.tab = tab;
        this.row = row;
        this.col = col;
        this.widgt = widgt;
    }

    public String toString() {
        return "Widgts [name=" + this.name + ", tab=" + this.tab + ", row=" + this.row + ", col=" + this.col + ", slide=" + this.slide + ", widgt=" + this.widgt + "]";
    }
}