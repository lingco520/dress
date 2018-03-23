package com.daqsoft.vo.client.madeVoBean;

/**
 * @Author: superziy .
 * @Date: Created in 17:10 2017/8/7.
 * @Version:
 * @describe:人力资源vo
 */
public class HumanResourcesVO {
    private Integer leavewomen;//离职人数
    private Integer total;//总人数
    private Integer year;//年份
    private Integer man;//男职员
    private Integer women;//女职员
    private Integer leaveman;//入职人数

    public Integer getLeavewomen() {
        return leavewomen;
    }

    public void setLeavewomen(Integer leavewomen) {
        this.leavewomen = leavewomen;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMan() {
        return man;
    }

    public void setMan(Integer man) {
        this.man = man;
    }

    public Integer getWomen() {
        return women;
    }

    public void setWomen(Integer women) {
        this.women = women;
    }

    public Integer getLeaveman() {
        return leaveman;
    }

    public void setLeaveman(Integer leaveman) {
        this.leaveman = leaveman;
    }
}
