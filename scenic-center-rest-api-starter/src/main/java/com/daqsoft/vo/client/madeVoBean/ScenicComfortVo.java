package com.daqsoft.vo.client.madeVoBean;

/**
 * @author tanggm
 * @version V3.1.0
 * @description 景区景点
 * @date 2017-06-26 19:03
 */
public class ScenicComfortVo {
    // 最小值
    private int minCount;
    // 最大值
    private int maxCount;
    // 景区景点舒适度
    private String comfortStr;

    public int getMinCount() {
        return minCount;
    }

    public void setMinCount(int minCount) {
        this.minCount = minCount;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public String getComfortStr() {
        return comfortStr;
    }

    public void setComfortStr(String comfortStr) {
        this.comfortStr = comfortStr;
    }
}
