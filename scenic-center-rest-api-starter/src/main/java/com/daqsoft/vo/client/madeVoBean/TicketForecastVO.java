package com.daqsoft.vo.client.madeVoBean;


import java.util.List;

/**
 * @author zhouq
 * @email zhouq@daqsoft.com
 * @date 2017-06-22 17:49
 * @Version:
 * @Describe:
 */
public class TicketForecastVO {
    //景区代码
    private String vcode;
    //景区名称
    private String name;

    //最大承载量
    private Integer maxquantity;

    //预测总数列表
    private List<TicketForecastIndexOnAndOffVO> dataList;

    public Integer getMaxquantity() {
        return maxquantity;
    }

    public void setMaxquantity(Integer maxquantity) {
        this.maxquantity = maxquantity;
    }


    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TicketForecastIndexOnAndOffVO> getDataList() {
        return dataList;
    }

    public void setDataList(List<TicketForecastIndexOnAndOffVO> dataList) {
        this.dataList = dataList;
    }

    public TicketForecastVO(String vcode, String name, Integer maxquantity, List<TicketForecastIndexOnAndOffVO> dataList) {
        this.vcode = vcode;
        this.name = name;
        this.maxquantity = maxquantity;
        this.dataList = dataList;
    }

    public TicketForecastVO() {
    }
}
