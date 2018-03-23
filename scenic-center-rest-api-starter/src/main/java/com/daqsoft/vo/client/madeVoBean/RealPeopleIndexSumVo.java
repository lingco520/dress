package com.daqsoft.vo.client.madeVoBean;

/**
 * @Author: ziy .
 * @Date: Created in 2017/4/13.
 * @Version: V3.0.0.
 * @describe:主页今日实时人数vo
 */


public class RealPeopleIndexSumVo implements java.io.Serializable {
    private String SumPeople;//团散人数
    private String source;//来源

    public String getSumPeople() {
        return SumPeople;
    }

    public void setSumPeople(String sumPeople) {
        SumPeople = sumPeople;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
