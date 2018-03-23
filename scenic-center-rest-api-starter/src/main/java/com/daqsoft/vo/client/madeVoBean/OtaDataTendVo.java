package com.daqsoft.vo.client.madeVoBean;

/**
 * @Author: ziy .
 * @Date: Created in 2017/4/13.
 * @Version: V3.0.0.
 * @describe:otatend类型
 */


public class OtaDataTendVo implements java.io.Serializable {
    private  long count;
    private  String name;
    private String percent;
    public OtaDataTendVo(){

    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
