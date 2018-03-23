package com.daqsoft.vo.client.madeVoBean;

/**
 * @Author: superziy .
 * @Date: Created in 13:43 2017/6/6.
 * @Version: 3.0.0
 * @describe: gis地图景区详情
 */


public class GisSourceDetailVo implements java.io.Serializable {
    private String id;//id
    private  String types;//类型
    private  String name;//名称
    private  String address;//地址
    private  String zhName;//别名
    private  String introduction;//介绍
    private  String typename;//类型
    private  String parkingTotal;//停车场总车位
    private  String vcode;

    public GisSourceDetailVo(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZhName() {
        return zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getParkingTotal() {
        return parkingTotal;
    }

    public void setParkingTotal(String parkingTotal) {
        this.parkingTotal = parkingTotal;
    }
}
