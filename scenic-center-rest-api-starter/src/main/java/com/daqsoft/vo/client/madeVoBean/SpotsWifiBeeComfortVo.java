package com.daqsoft.vo.client.madeVoBean;


import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Title: 小蜜蜂wifi景点舒适度
 * @Author: yangmei
 * @Date: 2017/08/08 11:21
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
public class SpotsWifiBeeComfortVo {
    @JsonIgnore
    private String id;
    private String bdcode; //建筑ID
    @JsonIgnore
    private String totalnum; //总人数
    @JsonIgnore
    private String maxCount; //景点承载量
    private String crtime;
    private String degree; //负荷度

    public SpotsWifiBeeComfortVo() {
    }

    public SpotsWifiBeeComfortVo(String id, String bdcode, String totalnum, String maxCount, String crtime, String degree) {
        this.id = id;
        this.bdcode = bdcode;
        this.totalnum = totalnum;
        this.maxCount = maxCount;
        this.crtime = crtime;
        this.degree = degree;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBdcode() {
        return bdcode;
    }

    public void setBdcode(String bdcode) {
        this.bdcode = bdcode;
    }

    public String getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(String totalnum) {
        this.totalnum = totalnum;
    }

    public String getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(String maxCount) {
        this.maxCount = maxCount;
    }

    public String getCrtime() {
        return crtime;
    }

    public void setCrtime(String crtime) {
        this.crtime = crtime;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
