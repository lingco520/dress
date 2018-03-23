package com.daqsoft.vo.client.madeVoBean;

/**
 * @author tanggm
 * @version V1.0.0
 * @description 景区拥挤度
 * @date 2017-06-23 16:41
 */
public class SpotCrowdVo {
    // id
    private Integer id;
    // 名称
    private String vehicleno;
    // 车类型
    private String vehicleType;
    // 车速
    private String speed;
    // 车辆状态
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVehicleno() {
        return vehicleno;
    }

    public void setVehicleno(String vehicleno) {
        this.vehicleno = vehicleno;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
