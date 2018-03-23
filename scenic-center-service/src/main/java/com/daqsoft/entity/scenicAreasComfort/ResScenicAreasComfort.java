package com.daqsoft.entity.scenicAreasComfort;

public class ResScenicAreasComfort {
    //主键id
    private Long id;
    //名称
    private String name;
    //排序
    private Integer rank;
    //舒适度等级
    private String rating;
    //舒适度最小人数
    private String maxcount;
    //舒适度最大人数
    private String mincount;
    //景区唯一标示
    private String vcode;
    //状态
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating == null ? null : rating.trim();
    }

    public String getMaxcount() {
        return maxcount;
    }

    public void setMaxcount(String maxcount) {
        this.maxcount = maxcount == null ? null : maxcount.trim();
    }

    public String getMincount() {
        return mincount;
    }

    public void setMincount(String mincount) {
        this.mincount = mincount == null ? null : mincount.trim();
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode == null ? null : vcode.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}