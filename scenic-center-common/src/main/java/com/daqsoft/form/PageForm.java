/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.form;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: PageForm
 * @Author: tanggm
 * @Date: 2017/12/27 15:11
 * @Description: TODO 通用分页表单，封装分页数据表单搜索的通用参数，如页码、关键词等信息。
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */

public class PageForm {
    /**
     * 第几页
     */
    private int currPage = 1;

    /**
     * 每页查询多少条
     */
    private int pageSize = 10;

    /**
     * 总条数
     */
    private long totalCount = 0;

    /**
     * 总页数
     */
    private int totalPage = 0;

    /**
     * 关键词
     */
    private String keyword = "";
    /**
     * 景区vcode
     */
    private String vcode = "";

    /**
     * 列表数据集合
     */
    private List rows = new ArrayList();

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }
}
