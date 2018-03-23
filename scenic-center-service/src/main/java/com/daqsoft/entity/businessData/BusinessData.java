package com.daqsoft.entity.businessData;

import java.io.Serializable;
import java.util.Date;

/**
 * 业务数据模拟字段
 */
public class BusinessData implements Serializable {
    /**
     * 开始时间
     */
    private String startDate;
    /**
     * 结束时间
     */
    private String endDate;
    /**
     * 是否模拟
     */
    private String allSure;
    /**
     * 单团队人数范围最小值
     */
    private Integer teamAmountMin;
    /**
     * 单团队人数范围最大值
     */
    private Integer teamAmountMax;
    /**
     * 是否模拟
     */
    private String teamSure;
    /**
     * 流量统计类型
     */
    private String flowSource;
    /**
     * 每小时访问量最小值
     */
    private String flowHourMin;
    /**
     * 每小时访问量最大值
     */
    private String flowHourMax;
    /**
     * 是否模拟
     */
    private String flowHourSure;
    /**
     * 是否模拟字符串， 各个值之间 “,” 分割
     */
    private String flowHourSureStr;
    /**
     * 男性比例最小
     */
    private Integer manRatioMax;
    /**
     * 男性比例最大
     */
    private Integer manRatioMin;
    /**
     * 每天总人数最小值
     */
    private Integer portrayalAmountMin;
    /**
     * 每天总人数最大值
     */
    private Integer portrayalAmountMax;
    /**
     * 是否模拟
     */
    private String portrayalSure;
    /**
     * 时长类型选择
     */
    private String kindOne;
    /**
     * 每天总人数最小值
     */
    private Integer lingeringAmountMinOne;
    /**
     * 每天总人数最大值
     */
    private Integer lingeringAmountMaxOne;
    /**
     * 时长类型选择
     */
    private String kindTwo;
    /**
     * 每天总人数最小值
     */
    private Integer lingeringAmountMinTwo;
    /**
     * 每天总人数最大值
     */
    private Integer lingeringAmountMaxTwo;
    /**
     * 时长类型选择
     */
    private String kindThree;
    /**
     * 每天总人数最小值
     */
    private Integer lingeringAmountMinThree;
    /**
     * 每天总人数最大值
     */
    private Integer lingeringAmountMaxThree;
    /**
     * 时长类型选择
     */
    private String kindFour;
    /**
     * 每天总人数最小值
     */
    private Integer lingeringAmountMinFour;
    /**
     * 每天总人数最大值
     */
    private Integer lingeringAmountMaxFour;
    /**
     * 时长类型选择
     */
    private String kindFive;
    /**
     * 每天总人数最小值
     */
    private Integer lingeringAmountMinFive;
    /**
     * 每天总人数最大值
     */
    private Integer lingeringAmountMaxFive;
    /**
     * 时长类型选择
     */
    private String kindSix;
    /**
     * 每天总人数最小值
     */
    private Integer lingeringAmountMinSix;
    /**
     * 每天总人数最大值
     */
    private Integer lingeringAmountMaxSix;
    /**
     * 时长类型选择
     */
    private String kindSeven;
    /**
     * 每天总人数最小值
     */
    private Integer lingeringAmountMinSeven;
    /**
     * 每天总人数最大值
     */
    private Integer lingeringAmountMaxSeven;
    /**
     * 是否模拟
     */
    private String lingeringSure;
    /**
     * 每天进入人数最小值
     */
    private Integer enterMin;
    /**
     * 每天进入人数最大值
     */
    private Integer enterMax;
    /**
     * 每天离开人数最小值
     */
    private Integer leaveMin;
    /**
     * 每天离开人数最大值
     */
    private Integer leaveMax;
    /**
     * 是否模拟
     */
    private String passengerDaySure;
    /**
     * 每天预计人数最小值
     */
    private Integer predictMin;
    /**
     * 每天预计人数最大值
     */
    private Integer predictMax;
    /**
     * 是否模拟
     */
    private String predictSure;
    /**
     * 线上最小值
     */
    private Integer onlineMin;
    /**
     * 线上最大值
     */
    private Integer onlineMax;
    /**
     * 线下最小值
     */
    private Integer offlineMin;
    /**
     * 线下最大值
     */
    private Integer offlineMax;
    /**
     * 是否模拟
     */
    private String offlineSure;
    /**
     * 网评数据最小值
     */
    private Integer wpMinNum;
    /**
     * 网评数据最大值
     */
    private Integer wpMaxNum;
    /**
     * 是否模拟
     */
    private String wpSure;
    /**
     * aqi最小值
     */
    private Integer aqiMinNum;
    /**
     * aqi最大值
     */
    private Integer aqiMaxNum;
    /**
     * 是否模拟
     */
    private String aqiSure;
    /**
     * 负氧离子最小值
     */
    private Integer oxyMinNum;
    /**
     * 负氧离子最大值
     */
    private Integer oxyMaxNum;
    /**
     * 是否模拟
     */
    private String oxySure;
    /**
     * 景区天气数据-地区选择
     */
    private String weatherRegion;
    /**
     * 是否模拟
     */
    private String weatherSure;
    /**
     * 湿度最小值
     */
    private Integer airHumMinNum;
    /**
     * 湿度最大值
     */
    private Integer airHumMaxNum;
    /**
     * 是否模拟
     */
    private String airHumSure;
    /**
     * 景区风力风向数据-地区选择
     */
    private String windRegion;
    /**
     * 是否模拟
     */
    private String windSure;
    /**
     * 降水量最小值
     */
    private Integer rainMinNum;
    /**
     * 降水量最大值
     */
    private Integer rainMaxNum;
    /**
     * 是否模拟
     */
    private String rainSure;
    /**
     * 紫外线最小值
     */
    private Integer ultMinNum;
    /**
     * 紫外线最大值
     */
    private Integer ultMaxNum;
    /**
     * 是否模拟
     */
    private String ultSure;
    /**
     * 流量最小值
     */
    private Integer flowMinNum;
    /**
     * 流量最大值
     */
    private Integer flowMaxNum;
    /**
     * 是否模拟
     */
    private String flowSure;
    /**
     * 流速最小值
     */
    private Integer speedMinNum;
    /**
     * 流速最大值
     */
    private Integer speedMaxNum;
    /**
     * 是否模拟
     */
    private String speedSure;
    /**
     * 水深最小值
     */
    private Integer depthMinNum;
    /**
     * 水深最大值
     */
    private Integer depthMaxNum;
    /**
     * 是否模拟
     */
    private String depthSure;
    /**
     * 水宽最小值
     */
    private Integer widthMinNum;
    /**
     * 水宽最大值
     */
    private Integer widthMaxNum;
    /**
     * 是否模拟
     */
    private String widthSure;
    /**
     * 水温最小值
     */
    private Integer temperatureMinNum;
    /**
     * 水温最大值
     */
    private Integer temperatureMaxNum;
    /**
     * 是否模拟
     */
    private String temperatureSure;
    /**
     * 雷电经度
     */
    private String thunderLongitude;
    /**
     * 雷电纬度
     */
    private String thunderLatitude;
    /**
     * 是否模拟
     */
    private String thunderSure;
    /**
     * 第一年模拟数据
     */
    private Integer oneYear;
    /**
     * 第二年模拟数据
     */
    private Integer twoYear;
    /**
     * 第三年模拟数据
     */
    private Integer threeYear;
    /**
     * 第四年模拟数据
     */
    private Integer fourYear;
    /**
     * 第五年模拟数据
     */
    private Integer fiveYear;
    /**
     * 是否模拟
     */
    private String busSure;

    /**
     * 旅行社团队最小值
     */
    private Integer travelTeamMinNum;
    /**
     * 旅行社团队最大值
     */
    private Integer travelTeamMaxNum;
    /**
     * 是否模拟
     */
    private String travelTeamSure;
    /**
     * 春节
     */
    private Integer spring;
    /**
     * 清明
     */
    private Integer tombSweeping;

    /**
     * 劳动节
     */
    private Integer labour;
    /**
     * 端午
     */
    private Integer dragon;
    /**
     * 中秋节
     */
    private Integer midAutumn;
    /**
     * 国庆
     */
    private Integer national;
    /**
     * 元旦
     */
    private Integer newYear;
    /**
     * 节假日是否模拟
     */
    private String holidaySure;

    /**
     * 未来客流最小
     */
    private Integer peopleMinNum;
    /**
     * 未来客流最大
     */
    private Integer peopleMaxNum;
    /**
     * 未来客流是否模拟
     */
    private String peopleSure;

    /**
     * 大巴车牌号
     */
    private String busNum;
    /**
     * 到达时间
     */
    private Date arriveDate;
    /**
     * 离开时间
     */
    private Date leaveDate;
    /**
     * 团队人数
     */
    private Integer amount;

    /**
     * 是否模拟团队
     */
    private String teamTure;

    /**
     * 交通预警道路
     */
    private String roadTraffic;

    /**
     * 是否模拟交通预警
     */
    private String trafficSure;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAllSure() {
        return allSure;
    }

    public void setAllSure(String allSure) {
        this.allSure = allSure;
    }

    public Integer getTeamAmountMin() {
        return teamAmountMin;
    }

    public void setTeamAmountMin(Integer teamAmountMin) {
        this.teamAmountMin = teamAmountMin;
    }

    public Integer getTeamAmountMax() {
        return teamAmountMax;
    }

    public void setTeamAmountMax(Integer teamAmountMax) {
        this.teamAmountMax = teamAmountMax;
    }

    public String getTeamSure() {
        return teamSure;
    }

    public void setTeamSure(String teamSure) {
        this.teamSure = teamSure;
    }

    public String getFlowSource() {
        return flowSource;
    }

    public void setFlowSource(String flowSource) {
        this.flowSource = flowSource;
    }

    public String getFlowHourMin() {
        return flowHourMin;
    }

    public void setFlowHourMin(String flowHourMin) {
        this.flowHourMin = flowHourMin;
    }

    public String getFlowHourMax() {
        return flowHourMax;
    }

    public void setFlowHourMax(String flowHourMax) {
        this.flowHourMax = flowHourMax;
    }

    public String getFlowHourSure() {
        return flowHourSure;
    }

    public void setFlowHourSure(String flowHourSure) {
        this.flowHourSure = flowHourSure;
    }

    public Integer getManRatioMax() {
        return manRatioMax;
    }

    public void setManRatioMax(Integer manRatioMax) {
        this.manRatioMax = manRatioMax;
    }

    public Integer getManRatioMin() {
        return manRatioMin;
    }

    public void setManRatioMin(Integer manRatioMin) {
        this.manRatioMin = manRatioMin;
    }

    public Integer getPortrayalAmountMin() {
        return portrayalAmountMin;
    }

    public void setPortrayalAmountMin(Integer portrayalAmountMin) {
        this.portrayalAmountMin = portrayalAmountMin;
    }

    public Integer getPortrayalAmountMax() {
        return portrayalAmountMax;
    }

    public void setPortrayalAmountMax(Integer portrayalAmountMax) {
        this.portrayalAmountMax = portrayalAmountMax;
    }

    public String getPortrayalSure() {
        return portrayalSure;
    }

    public void setPortrayalSure(String portrayalSure) {
        this.portrayalSure = portrayalSure;
    }

    public String getKindOne() {
        return kindOne;
    }

    public void setKindOne(String kindOne) {
        this.kindOne = kindOne;
    }

    public Integer getLingeringAmountMinOne() {
        return lingeringAmountMinOne;
    }

    public void setLingeringAmountMinOne(Integer lingeringAmountMinOne) {
        this.lingeringAmountMinOne = lingeringAmountMinOne;
    }

    public Integer getLingeringAmountMaxOne() {
        return lingeringAmountMaxOne;
    }

    public void setLingeringAmountMaxOne(Integer lingeringAmountMaxOne) {
        this.lingeringAmountMaxOne = lingeringAmountMaxOne;
    }

    public String getKindTwo() {
        return kindTwo;
    }

    public void setKindTwo(String kindTwo) {
        this.kindTwo = kindTwo;
    }

    public Integer getLingeringAmountMinTwo() {
        return lingeringAmountMinTwo;
    }

    public void setLingeringAmountMinTwo(Integer lingeringAmountMinTwo) {
        this.lingeringAmountMinTwo = lingeringAmountMinTwo;
    }

    public Integer getLingeringAmountMaxTwo() {
        return lingeringAmountMaxTwo;
    }

    public void setLingeringAmountMaxTwo(Integer lingeringAmountMaxTwo) {
        this.lingeringAmountMaxTwo = lingeringAmountMaxTwo;
    }

    public String getKindThree() {
        return kindThree;
    }

    public void setKindThree(String kindThree) {
        this.kindThree = kindThree;
    }

    public Integer getLingeringAmountMinThree() {
        return lingeringAmountMinThree;
    }

    public void setLingeringAmountMinThree(Integer lingeringAmountMinThree) {
        this.lingeringAmountMinThree = lingeringAmountMinThree;
    }

    public Integer getLingeringAmountMaxThree() {
        return lingeringAmountMaxThree;
    }

    public void setLingeringAmountMaxThree(Integer lingeringAmountMaxThree) {
        this.lingeringAmountMaxThree = lingeringAmountMaxThree;
    }

    public String getKindFour() {
        return kindFour;
    }

    public void setKindFour(String kindFour) {
        this.kindFour = kindFour;
    }

    public Integer getLingeringAmountMinFour() {
        return lingeringAmountMinFour;
    }

    public void setLingeringAmountMinFour(Integer lingeringAmountMinFour) {
        this.lingeringAmountMinFour = lingeringAmountMinFour;
    }

    public Integer getLingeringAmountMaxFour() {
        return lingeringAmountMaxFour;
    }

    public void setLingeringAmountMaxFour(Integer lingeringAmountMaxFour) {
        this.lingeringAmountMaxFour = lingeringAmountMaxFour;
    }

    public String getKindFive() {
        return kindFive;
    }

    public void setKindFive(String kindFive) {
        this.kindFive = kindFive;
    }

    public Integer getLingeringAmountMinFive() {
        return lingeringAmountMinFive;
    }

    public void setLingeringAmountMinFive(Integer lingeringAmountMinFive) {
        this.lingeringAmountMinFive = lingeringAmountMinFive;
    }

    public Integer getLingeringAmountMaxFive() {
        return lingeringAmountMaxFive;
    }

    public void setLingeringAmountMaxFive(Integer lingeringAmountMaxFive) {
        this.lingeringAmountMaxFive = lingeringAmountMaxFive;
    }

    public String getKindSix() {
        return kindSix;
    }

    public void setKindSix(String kindSix) {
        this.kindSix = kindSix;
    }

    public Integer getLingeringAmountMinSix() {
        return lingeringAmountMinSix;
    }

    public void setLingeringAmountMinSix(Integer lingeringAmountMinSix) {
        this.lingeringAmountMinSix = lingeringAmountMinSix;
    }

    public Integer getLingeringAmountMaxSix() {
        return lingeringAmountMaxSix;
    }

    public void setLingeringAmountMaxSix(Integer lingeringAmountMaxSix) {
        this.lingeringAmountMaxSix = lingeringAmountMaxSix;
    }

    public String getKindSeven() {
        return kindSeven;
    }

    public void setKindSeven(String kindSeven) {
        this.kindSeven = kindSeven;
    }

    public Integer getLingeringAmountMinSeven() {
        return lingeringAmountMinSeven;
    }

    public void setLingeringAmountMinSeven(Integer lingeringAmountMinSeven) {
        this.lingeringAmountMinSeven = lingeringAmountMinSeven;
    }

    public Integer getLingeringAmountMaxSeven() {
        return lingeringAmountMaxSeven;
    }

    public void setLingeringAmountMaxSeven(Integer lingeringAmountMaxSeven) {
        this.lingeringAmountMaxSeven = lingeringAmountMaxSeven;
    }

    public String getLingeringSure() {
        return lingeringSure;
    }

    public void setLingeringSure(String lingeringSure) {
        this.lingeringSure = lingeringSure;
    }

    public Integer getEnterMin() {
        return enterMin;
    }

    public void setEnterMin(Integer enterMin) {
        this.enterMin = enterMin;
    }

    public Integer getEnterMax() {
        return enterMax;
    }

    public void setEnterMax(Integer enterMax) {
        this.enterMax = enterMax;
    }

    public Integer getLeaveMin() {
        return leaveMin;
    }

    public void setLeaveMin(Integer leaveMin) {
        this.leaveMin = leaveMin;
    }

    public Integer getLeaveMax() {
        return leaveMax;
    }

    public void setLeaveMax(Integer leaveMax) {
        this.leaveMax = leaveMax;
    }

    public String getPassengerDaySure() {
        return passengerDaySure;
    }

    public void setPassengerDaySure(String passengerDaySure) {
        this.passengerDaySure = passengerDaySure;
    }

    public Integer getPredictMin() {
        return predictMin;
    }

    public void setPredictMin(Integer predictMin) {
        this.predictMin = predictMin;
    }

    public Integer getPredictMax() {
        return predictMax;
    }

    public void setPredictMax(Integer predictMax) {
        this.predictMax = predictMax;
    }

    public String getPredictSure() {
        return predictSure;
    }

    public void setPredictSure(String predictSure) {
        this.predictSure = predictSure;
    }

    public Integer getOnlineMin() {
        return onlineMin;
    }

    public void setOnlineMin(Integer onlineMin) {
        this.onlineMin = onlineMin;
    }

    public Integer getOnlineMax() {
        return onlineMax;
    }

    public void setOnlineMax(Integer onlineMax) {
        this.onlineMax = onlineMax;
    }

    public Integer getOfflineMin() {
        return offlineMin;
    }

    public void setOfflineMin(Integer offlineMin) {
        this.offlineMin = offlineMin;
    }

    public Integer getOfflineMax() {
        return offlineMax;
    }

    public void setOfflineMax(Integer offlineMax) {
        this.offlineMax = offlineMax;
    }

    public String getOfflineSure() {
        return offlineSure;
    }

    public void setOfflineSure(String offlineSure) {
        this.offlineSure = offlineSure;
    }

    public Integer getWpMinNum() {
        return wpMinNum;
    }

    public void setWpMinNum(Integer wpMinNum) {
        this.wpMinNum = wpMinNum;
    }

    public Integer getWpMaxNum() {
        return wpMaxNum;
    }

    public void setWpMaxNum(Integer wpMaxNum) {
        this.wpMaxNum = wpMaxNum;
    }

    public String getWpSure() {
        return wpSure;
    }

    public void setWpSure(String wpSure) {
        this.wpSure = wpSure;
    }

    public Integer getAqiMinNum() {
        return aqiMinNum;
    }

    public void setAqiMinNum(Integer aqiMinNum) {
        this.aqiMinNum = aqiMinNum;
    }

    public Integer getAqiMaxNum() {
        return aqiMaxNum;
    }

    public void setAqiMaxNum(Integer aqiMaxNum) {
        this.aqiMaxNum = aqiMaxNum;
    }

    public String getAqiSure() {
        return aqiSure;
    }

    public void setAqiSure(String aqiSure) {
        this.aqiSure = aqiSure;
    }

    public Integer getOxyMinNum() {
        return oxyMinNum;
    }

    public void setOxyMinNum(Integer oxyMinNum) {
        this.oxyMinNum = oxyMinNum;
    }

    public Integer getOxyMaxNum() {
        return oxyMaxNum;
    }

    public void setOxyMaxNum(Integer oxyMaxNum) {
        this.oxyMaxNum = oxyMaxNum;
    }

    public String getOxySure() {
        return oxySure;
    }

    public void setOxySure(String oxySure) {
        this.oxySure = oxySure;
    }

    public String getWeatherRegion() {
        return weatherRegion;
    }

    public void setWeatherRegion(String weatherRegion) {
        this.weatherRegion = weatherRegion;
    }

    public String getWeatherSure() {
        return weatherSure;
    }

    public void setWeatherSure(String weatherSure) {
        this.weatherSure = weatherSure;
    }

    public Integer getAirHumMinNum() {
        return airHumMinNum;
    }

    public void setAirHumMinNum(Integer airHumMinNum) {
        this.airHumMinNum = airHumMinNum;
    }

    public Integer getAirHumMaxNum() {
        return airHumMaxNum;
    }

    public void setAirHumMaxNum(Integer airHumMaxNum) {
        this.airHumMaxNum = airHumMaxNum;
    }

    public String getAirHumSure() {
        return airHumSure;
    }

    public void setAirHumSure(String airHumSure) {
        this.airHumSure = airHumSure;
    }

    public String getWindRegion() {
        return windRegion;
    }

    public void setWindRegion(String windRegion) {
        this.windRegion = windRegion;
    }

    public String getWindSure() {
        return windSure;
    }

    public void setWindSure(String windSure) {
        this.windSure = windSure;
    }

    public Integer getRainMinNum() {
        return rainMinNum;
    }

    public void setRainMinNum(Integer rainMinNum) {
        this.rainMinNum = rainMinNum;
    }

    public Integer getRainMaxNum() {
        return rainMaxNum;
    }

    public void setRainMaxNum(Integer rainMaxNum) {
        this.rainMaxNum = rainMaxNum;
    }

    public String getRainSure() {
        return rainSure;
    }

    public void setRainSure(String rainSure) {
        this.rainSure = rainSure;
    }

    public Integer getUltMinNum() {
        return ultMinNum;
    }

    public void setUltMinNum(Integer ultMinNum) {
        this.ultMinNum = ultMinNum;
    }

    public Integer getUltMaxNum() {
        return ultMaxNum;
    }

    public void setUltMaxNum(Integer ultMaxNum) {
        this.ultMaxNum = ultMaxNum;
    }

    public String getUltSure() {
        return ultSure;
    }

    public void setUltSure(String ultSure) {
        this.ultSure = ultSure;
    }

    public Integer getFlowMinNum() {
        return flowMinNum;
    }

    public void setFlowMinNum(Integer flowMinNum) {
        this.flowMinNum = flowMinNum;
    }

    public Integer getFlowMaxNum() {
        return flowMaxNum;
    }

    public void setFlowMaxNum(Integer flowMaxNum) {
        this.flowMaxNum = flowMaxNum;
    }

    public String getFlowSure() {
        return flowSure;
    }

    public void setFlowSure(String flowSure) {
        this.flowSure = flowSure;
    }

    public Integer getSpeedMinNum() {
        return speedMinNum;
    }

    public void setSpeedMinNum(Integer speedMinNum) {
        this.speedMinNum = speedMinNum;
    }

    public Integer getSpeedMaxNum() {
        return speedMaxNum;
    }

    public void setSpeedMaxNum(Integer speedMaxNum) {
        this.speedMaxNum = speedMaxNum;
    }

    public String getSpeedSure() {
        return speedSure;
    }

    public void setSpeedSure(String speedSure) {
        this.speedSure = speedSure;
    }

    public Integer getDepthMinNum() {
        return depthMinNum;
    }

    public void setDepthMinNum(Integer depthMinNum) {
        this.depthMinNum = depthMinNum;
    }

    public Integer getDepthMaxNum() {
        return depthMaxNum;
    }

    public void setDepthMaxNum(Integer depthMaxNum) {
        this.depthMaxNum = depthMaxNum;
    }

    public String getDepthSure() {
        return depthSure;
    }

    public void setDepthSure(String depthSure) {
        this.depthSure = depthSure;
    }

    public Integer getWidthMinNum() {
        return widthMinNum;
    }

    public void setWidthMinNum(Integer widthMinNum) {
        this.widthMinNum = widthMinNum;
    }

    public Integer getWidthMaxNum() {
        return widthMaxNum;
    }

    public void setWidthMaxNum(Integer widthMaxNum) {
        this.widthMaxNum = widthMaxNum;
    }

    public String getWidthSure() {
        return widthSure;
    }

    public void setWidthSure(String widthSure) {
        this.widthSure = widthSure;
    }

    public Integer getTemperatureMinNum() {
        return temperatureMinNum;
    }

    public void setTemperatureMinNum(Integer temperatureMinNum) {
        this.temperatureMinNum = temperatureMinNum;
    }

    public Integer getTemperatureMaxNum() {
        return temperatureMaxNum;
    }

    public void setTemperatureMaxNum(Integer temperatureMaxNum) {
        this.temperatureMaxNum = temperatureMaxNum;
    }

    public String getTemperatureSure() {
        return temperatureSure;
    }

    public void setTemperatureSure(String temperatureSure) {
        this.temperatureSure = temperatureSure;
    }

    public String getThunderLongitude() {
        return thunderLongitude;
    }

    public void setThunderLongitude(String thunderLongitude) {
        this.thunderLongitude = thunderLongitude;
    }

    public String getThunderLatitude() {
        return thunderLatitude;
    }

    public void setThunderLatitude(String thunderLatitude) {
        this.thunderLatitude = thunderLatitude;
    }

    public String getThunderSure() {
        return thunderSure;
    }

    public void setThunderSure(String thunderSure) {
        this.thunderSure = thunderSure;
    }

    public Integer getOneYear() {
        return oneYear;
    }

    public void setOneYear(Integer oneYear) {
        this.oneYear = oneYear;
    }

    public Integer getTwoYear() {
        return twoYear;
    }

    public void setTwoYear(Integer twoYear) {
        this.twoYear = twoYear;
    }

    public Integer getThreeYear() {
        return threeYear;
    }

    public void setThreeYear(Integer threeYear) {
        this.threeYear = threeYear;
    }

    public Integer getFourYear() {
        return fourYear;
    }

    public void setFourYear(Integer fourYear) {
        this.fourYear = fourYear;
    }

    public Integer getFiveYear() {
        return fiveYear;
    }

    public void setFiveYear(Integer fiveYear) {
        this.fiveYear = fiveYear;
    }

    public String getBusSure() {
        return busSure;
    }

    public void setBusSure(String busSure) {
        this.busSure = busSure;
    }

    public Integer getTravelTeamMinNum() {
        return travelTeamMinNum;
    }

    public void setTravelTeamMinNum(Integer travelTeamMinNum) {
        this.travelTeamMinNum = travelTeamMinNum;
    }

    public Integer getTravelTeamMaxNum() {
        return travelTeamMaxNum;
    }

    public void setTravelTeamMaxNum(Integer travelTeamMaxNum) {
        this.travelTeamMaxNum = travelTeamMaxNum;
    }

    public String getTravelTeamSure() {
        return travelTeamSure;
    }

    public void setTravelTeamSure(String travelTeamSure) {
        this.travelTeamSure = travelTeamSure;
    }

    public Integer getSpring() {
        return spring;
    }

    public void setSpring(Integer spring) {
        this.spring = spring;
    }

    public Integer getTombSweeping() {
        return tombSweeping;
    }

    public void setTombSweeping(Integer tombSweeping) {
        this.tombSweeping = tombSweeping;
    }

    public Integer getLabour() {
        return labour;
    }

    public void setLabour(Integer labour) {
        this.labour = labour;
    }

    public Integer getDragon() {
        return dragon;
    }

    public void setDragon(Integer dragon) {
        this.dragon = dragon;
    }

    public Integer getMidAutumn() {
        return midAutumn;
    }

    public void setMidAutumn(Integer midAutumn) {
        this.midAutumn = midAutumn;
    }

    public Integer getNational() {
        return national;
    }

    public void setNational(Integer national) {
        this.national = national;
    }

    public Integer getNewYear() {
        return newYear;
    }

    public void setNewYear(Integer newYear) {
        this.newYear = newYear;
    }

    public String getHolidaySure() {
        return holidaySure;
    }

    public void setHolidaySure(String holidaySure) {
        this.holidaySure = holidaySure;
    }

    public Integer getPeopleMinNum() {
        return peopleMinNum;
    }

    public void setPeopleMinNum(Integer peopleMinNum) {
        this.peopleMinNum = peopleMinNum;
    }

    public Integer getPeopleMaxNum() {
        return peopleMaxNum;
    }

    public void setPeopleMaxNum(Integer peopleMaxNum) {
        this.peopleMaxNum = peopleMaxNum;
    }

    public String getPeopleSure() {
        return peopleSure;
    }

    public void setPeopleSure(String peopleSure) {
        this.peopleSure = peopleSure;
    }

    public String getFlowHourSureStr() {
        return flowHourSureStr;
    }

    public void setFlowHourSureStr(String flowHourSureStr) {
        this.flowHourSureStr = flowHourSureStr;
    }

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum;
    }

    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getTeamTure() {
        return teamTure;
    }

    public void setTeamTure(String teamTure) {
        this.teamTure = teamTure;
    }

    public String getRoadTraffic() {
        return roadTraffic;
    }

    public void setRoadTraffic(String roadTraffic) {
        this.roadTraffic = roadTraffic;
    }

    public String getTrafficSure() {
        return trafficSure;
    }

    public void setTrafficSure(String trafficSure) {
        this.trafficSure = trafficSure;
    }
}