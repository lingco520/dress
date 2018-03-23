package com.daqsoft.vo.client.mysqlBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 景区人数domain 类
 */
public class JqScenicTimelyPopulation implements Serializable {
    private Long ID;
    private String RESOURCEVCODE;
    private String NAME;
    private String TEMPLYQUANTITY;
    private Long MAXQUANTITY;
    private String TIME;
    private String SOURCE;
    private Long TICKET;
    private Date SYSTEMTIME;
    private String VCODE;
    private String TOTAL;
    private String PopTime;//时间
    private String SumCar;//汽车总数
    private String SumPeople;//团散人数


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


    public String getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(String TOTAL) {
        this.TOTAL = TOTAL;
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

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
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
}
