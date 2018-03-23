package com.daqsoft.vo.client.madeVoBean;

/**
 * Created by superziy on 2017-06-06.
 */
public class RealPeopleComeFromVo {
    private String sourceCity;//游客来源地
    private String sourcecityNew;//省份游客数
    private String  sourcecityPro;//	游客占比

    public RealPeopleComeFromVo(){

    }

    public String getSourceCity() {
        return sourceCity;
    }

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }

    public String getSourcecityNew() {
        return sourcecityNew;
    }

    public void setSourcecityNew(String sourcecityNew) {
        this.sourcecityNew = sourcecityNew;
    }

    public String getSourcecityPro() {
        return sourcecityPro;
    }

    public void setSourcecityPro(String sourcecityPro) {
        this.sourcecityPro = sourcecityPro;
    }
}
