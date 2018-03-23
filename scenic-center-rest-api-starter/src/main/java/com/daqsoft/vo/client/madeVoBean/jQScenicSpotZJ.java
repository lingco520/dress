package com.daqsoft.vo.client.madeVoBean;

/**
 * @Author: caol.
 * @Date: Created in 2017/6/4.
 * @Version: V3.0.0.
 * @describe:景点舒适度（咂机统计）
 */
public class jQScenicSpotZJ implements java.io.Serializable {
    private String id;
    private String count;
    private String name;
    private String maxcount;
    private String maxquantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaxcount() {
        return maxcount;
    }

    public void setMaxcount(String maxcount) {
        this.maxcount = maxcount;
    }

    public String getMaxquantity() {
        return maxquantity;
    }

    public void setMaxquantity(String maxquantity) {
        this.maxquantity = maxquantity;
    }
}
