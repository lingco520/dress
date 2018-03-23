package com.daqsoft.vo.client.madeVoBean;

import java.util.List;

/**
 * @Author: caol.
 * @Date: Created in 2017/6/4.
 * @Version: V3.0.0.
 * @describe:景点舒适度（咂机统计）List
 */
public class jQScenicSpotZJList implements java.io.Serializable{
    private List<jQScenicSpotZJ> list;
    private String count;
    private String maxquantity;

    public List<jQScenicSpotZJ> getList() {
        return list;
    }

    public void setList(List<jQScenicSpotZJ> list) {
        this.list = list;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMaxquantity() {
        return maxquantity;
    }

    public void setMaxquantity(String maxquantity) {
        this.maxquantity = maxquantity;
    }
}
