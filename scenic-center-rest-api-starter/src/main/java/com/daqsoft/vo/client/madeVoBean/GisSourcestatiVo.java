package com.daqsoft.vo.client.madeVoBean;

/**
 * @Author: superziy .
 * @Date: Created in 13:43 2017/6/6.
 * @Version: 3.0.0
 * @describe: gis地图景区旅游资源统计
 */


public class GisSourcestatiVo implements java.io.Serializable {
    private String count;//数量
    private  String types;//类型
    private  String name;//名称

    public GisSourcestatiVo(){

    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {

        this.types = types;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
