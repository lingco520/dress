package com.daqsoft.vo.client.madeVoBean;

/**
 * @version V1.0.0
 * @description 应急资源
 * @date 2017-07-03
 */
public class EmergencyHQGVo {
    private String emeName;
    private Long count;
    private Long newadd = 0l;

    public String getEmeName() {
        return emeName;
    }

    public void setEmeName(String emeName) {
        this.emeName = emeName;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getNewadd() {
        return newadd;
    }

    public void setNewadd(Long newadd) {
        this.newadd = newadd;
    }
}
