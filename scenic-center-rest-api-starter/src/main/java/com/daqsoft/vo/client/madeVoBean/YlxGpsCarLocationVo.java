/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.vo.client.madeVoBean;

/**
 * @Title: YlxGpsCarLocationVo
 * @Author: tanggm
 * @Date: 2018/01/05 16:20
 * @Description: TODO 尉犁县车载系统Vo
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */

public class YlxGpsCarLocationVo {
    /**
     * id
     */
    private Integer id;
    /**
     * 汽车id
     */
    private Integer cid;
    /**
     * 汽车牌照
     */
    private String name;
    /**
     * 车速
     */
    private double speed;
    /**
     * 车辆类型
     */
    private String carType;
    /**
     * 汽车vKey
     */
    private String vKey;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getvKey() {
        return vKey;
    }

    public void setvKey(String vKey) {
        this.vKey = vKey;
    }
}
