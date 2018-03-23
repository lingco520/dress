package com.daqsoft.vo.client.madeVoBean;

/**
 * @Author: ziy .
 * @Date: Created in 2017/4/13.
 * @Version: V3.0.0.
 * @describe:gis 地图显示信息越界报警
 */


public class GisBoundsPoliceVo implements java.io.Serializable {
    private String rownum;//序号
    private String id;//id
    private String name;//名称
    private String addTime;//添加时间
    private String image;//图片地址
    private String seed;//是否查看

    public GisBoundsPoliceVo(){

    }

    public String getRownum() {
        return rownum;
    }

    public void setRownum(String rownum) {
        this.rownum = rownum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }
}
