package com.daqsoft.vo.client.mysqlBean;

import com.daqsoft.vo.client.madeVoBean.Tours_cl_Tend;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenxia
 * @version V1.0.0
 * @date 2017-06-06 18:04.
 */
public class CutPageBean implements Serializable {
    private List<Tours_cl_Tend> list;
    private int count;
    private int totalPage;


    /**
     * 数据集合
     */
    public List<Tours_cl_Tend> getList() {
        return list;
    }

    public void setList(List<Tours_cl_Tend> list) {
        this.list = list;
    }

    /**
     * 条数
     */
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    /**
     * 总页数
     */
    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
