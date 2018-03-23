package com.daqsoft.vo.client.madeVoBean;

/**
 * @author tanggm
 * @version V1.0.0
 * @description 景区实时人数分析
 * @date 2017-06-21 11:49
 */
public class SpotPassengerVo {
    // 景区景点名称
    private String scenicName;
    // 景区负荷度
    private String loadNum;

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }

    public String getLoadNum() {
        return loadNum;
    }

    public void setLoadNum(String loadNum) {
        this.loadNum = loadNum;
    }
}
