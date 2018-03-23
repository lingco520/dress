package com.daqsoft.vo.client.madeVoBean;

/**
 * Author znb
 * on 2017/6/23.
 */
public class TrafficWarningVo {
    private String vcode;
    private String level;
    private String startsection;
    private String endsection;
    private String starttime;
    private String endtime;
    private double startlongitude;
    private double startlatitude;
    private double endlongitude;
    private double endlatitude;

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public double getStartlatitude() {
        return startlatitude;
    }

    public void setStartlatitude(double startlatitude) {
        this.startlatitude = startlatitude;
    }

    public double getEndlongitude() {
        return endlongitude;
    }

    public void setEndlongitude(double endlongitude) {
        this.endlongitude = endlongitude;
    }

    public double getEndlatitude() {
        return endlatitude;
    }

    public void setEndlatitude(double endlatitude) {
        this.endlatitude = endlatitude;
    }

    public String getStartsection() {
        return startsection;
    }

    public void setStartsection(String startsection) {
        this.startsection = startsection;
    }

    public String getEndsection() {
        return endsection;
    }

    public void setEndsection(String endsection) {
        this.endsection = endsection;
    }

    public double getStartlongitude() {
        return startlongitude;
    }

    public void setStartlongitude(double startlongitude) {
        this.startlongitude = startlongitude;
    }
}
