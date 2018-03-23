package com.daqsoft.entity.scenicComfort;
/**
 * @Title:景点舒适度实体
 * @Author: superziy .
 * @Date: Created in 17:16 2018/1/17.
 * @Version: 4.0.0
 * @describe:景点舒适度实体
 * @since:JDK 1.8
 */
public class ResScenicComfort {
    //主键
    private Long id;
    //景区名称
    private String name;
    //排序
    private Integer rank;
    //等级名称
    private String rating;
    //最大舒适度
    private String maxcount;
    //最小舒适度
    private String mincount;
    //景区编码
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