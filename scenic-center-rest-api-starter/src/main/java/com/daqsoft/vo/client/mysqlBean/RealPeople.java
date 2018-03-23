package com.daqsoft.vo.client.mysqlBean;

import java.util.Date;

/**
 * @Author: ziy .
 * @Date: Created in 2017/4/13.
 * @Version: V3.0.0.
 * @describe:实时人数
 */


public class RealPeople implements java.io.Serializable {

    // Fields

    private static final long serialVersionUID = 7466204652730965186L;
    //主键
    private Long ID;
    //资源编码
    private String RESOURCEVCODE;
    //名称
    private String NAME;
    //实时人数
    private String TEMPLYQUANTITY;
    //最大承载量
    private Long MAXQUANTITY;
    //时间
    private Date TIME;
    //票务类型
    private String SOURCE;
    //票务id
    private Long TICKET;
    //系统时间
    private Date SYSTEMTIME;
    //vcode
    private String VCODE;
    //总数
    private String TOTAL;
    //时间
    private String PopTime;
    //汽车总数
    private String SumCar;
    //团散人数
    private String SumPeople;

    /**
     * default constructor
     */
    public RealPeople() {
    }

    public RealPeople(Long ID, String RESOURCEVCODE, String NAME, String TEMPLYQUANTITY, Long MAXQUANTITY, Date TIME, String SOURCE, Long TICKET, Date SYSTEMTIME, String VCODE, String TOTAL, String popTime, String sumCar, String sumPeople) {
        this.ID = ID;
        this.RESOURCEVCODE = RESOURCEVCODE;
        this.NAME = NAME;
        this.TEMPLYQUANTITY = TEMPLYQUANTITY;
        this.MAXQUANTITY = MAXQUANTITY;
        this.TIME = TIME;
        this.SOURCE = SOURCE;
        this.TICKET = TICKET;
        this.SYSTEMTIME = SYSTEMTIME;
        this.VCODE = VCODE;
        this.TOTAL = TOTAL;
        PopTime = popTime;
        SumCar = sumCar;
        SumPeople = sumPeople;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getRESOURCEVCODE() {
        return RESOURCEVCODE;
    }

    public void setRESOURCEVCODE(String RESOURCEVCODE) {
        this.RESOURCEVCODE = RESOURCEVCODE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getTEMPLYQUANTITY() {
        return TEMPLYQUANTITY;
    }

    public void setTEMPLYQUANTITY(String TEMPLYQUANTITY) {
        this.TEMPLYQUANTITY = TEMPLYQUANTITY;
    }

    public Long getMAXQUANTITY() {
        return MAXQUANTITY;
    }

    public void setMAXQUANTITY(Long MAXQUANTITY) {
        this.MAXQUANTITY = MAXQUANTITY;
    }

    public Date getTIME() {
        return TIME;
    }

    public void setTIME(Date TIME) {
        this.TIME = TIME;
    }

    public String getSOURCE() {
        return SOURCE;
    }

    public void setSOURCE(String SOURCE) {
        this.SOURCE = SOURCE;
    }

    public Long getTICKET() {
        return TICKET;
    }

    public void setTICKET(Long TICKET) {
        this.TICKET = TICKET;
    }

    public Date getSYSTEMTIME() {
        return SYSTEMTIME;
    }

    public void setSYSTEMTIME(Date SYSTEMTIME) {
        this.SYSTEMTIME = SYSTEMTIME;
    }

    public String getVCODE() {
        return VCODE;
    }

    public void setVCODE(String VCODE) {
        this.VCODE = VCODE;
    }

    public String getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(String TOTAL) {
        this.TOTAL = TOTAL;
    }

    public String getPopTime() {
        return PopTime;
    }

    public void setPopTime(String popTime) {
        PopTime = popTime;
    }

    public String getSumCar() {
        return SumCar;
    }

    public void setSumCar(String sumCar) {
        SumCar = sumCar;
    }

    public String getSumPeople() {
        return SumPeople;
    }

    public void setSumPeople(String sumPeople) {
        SumPeople = sumPeople;
    }
}
