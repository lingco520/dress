package com.daqsoft.vo.client.madeVoBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author tanggm
 * @version V1.0.0
 * @description 景区实时人数分析
 * @date 2017-06-21 11:49
 */
public class ActualPassengerVo {
    // 景区景点名称
    private String scenicName;
    // 景区负荷度
    private String loadNum;
    // 最大承载量
    private String maxCount;
    // 景点时间点、人数集合
    private List<Map<String, Object>> dateNumList = new ArrayList<>();

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }

    public List<Map<String, Object>> getDateNumList() {
        return dateNumList;
    }

    public void setDateNumList(List<Map<String, Object>> dateNumList) {

        this.dateNumList = dateNumList;
    }

    public String getLoadNum() {
        return loadNum;
    }

    public void setLoadNum(String loadNum) {
        this.loadNum = loadNum;
    }

    public String getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(String maxCount) {
        this.maxCount = maxCount;
    }
}
