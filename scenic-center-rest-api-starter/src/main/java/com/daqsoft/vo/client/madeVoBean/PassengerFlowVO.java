package com.daqsoft.vo.client.madeVoBean;

import java.util.List;

/**
 * @author zhouq
 * @email zhouq@daqsoft.com
 * @date 2017-06-22 16:17
 * @Version:
 * @Describe: 客流预测预警 VO
 */
public class PassengerFlowVO {

    //vcode
    private String vcode;

    private String name;

    //最大承载量
    private Integer maxquantity;

    //人流量列表
    private List<RealPeopleRemainVo> dataList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public Integer getMaxquantity() {
        return maxquantity;
    }

    public void setMaxquantity(Integer maxquantity) {
        this.maxquantity = maxquantity;
    }


    public List<RealPeopleRemainVo> getDataList() {
        return dataList;
    }

    public void setDataList(List<RealPeopleRemainVo> dataList) {
        this.dataList = dataList;
    }

    public PassengerFlowVO(String vcode, String name, Integer maxquantity, List<RealPeopleRemainVo> dataList) {
        this.vcode = vcode;
        this.name = name;
        this.maxquantity = maxquantity;
        this.dataList = dataList;
    }

    public PassengerFlowVO() {
    }
}
