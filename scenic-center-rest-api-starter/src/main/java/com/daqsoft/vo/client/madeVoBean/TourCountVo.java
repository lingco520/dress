package com.daqsoft.vo.client.madeVoBean;

/**
 * 团队数量vo
 */
public class TourCountVo implements  java.io.Serializable{

    private  String tours;//团队数量
    private String tourFrom;//团队来源

    public TourCountVo(){

    }

    public String getTours() {
        return tours;
    }

    public void setTours(String tours) {
        this.tours = tours;
    }

    public String getTourFrom() {
        return tourFrom;
    }

    public void setTourFrom(String tourFrom) {
        this.tourFrom = tourFrom;
    }
}
