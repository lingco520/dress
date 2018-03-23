package com.daqsoft.utils.client;

/**
 * @author tanggm
 * @version V3.1
 * @description 根据身份名称转换成对应前端的身份拼音简称
 * @date 2017-07-17 17:28
 */
public class ProvinceUtil {
    /**
     * 传入省份简称，转换成返回前端地图json名称
     * @param provinceName 省份简称
     * @return
     */
    public static String provinceNameToShort(String provinceName){
        String shortName = "";
        switch (provinceName){
            case "安徽省":
                shortName = "anhui";
            break;
            case "澳门":
                shortName = "aomen";
            break;
            case "北京市":
                shortName = "beijing";
            break;
            case "重庆市":
                shortName = "chongqing";
            break;
            case "福建省":
                shortName = "fujian";
            break;
            case "甘肃省":
                shortName = "gansu";
            break;
            case "广东省":
                shortName = "guangdong";
            break;
            case "广西":
                shortName = "guangxi";
            break;
            case "贵州省":
                shortName = "guizhou";
            break;
            case "海南省":
                shortName = "hainan";
            break;
            case "河北省":
                shortName = "hebei";
            break;
            case "黑龙江省":
                shortName = "heilongjiang";
            break;
            case "河南省":
                shortName = "henan";
            break;
            case "湖北省":
                shortName = "hubei";
            break;
            case "湖南省":
                shortName = "hunan";
            break;
            case "江苏省":
                shortName = "jiangsu";
            break;
            case "江西省":
                shortName = "jiangxi";
            break;
            case "吉林省":
                shortName = "jilin";
                break;
            case "辽宁省":
                shortName = "liaoning";
                break;
            case "内蒙古":
                shortName = "neimenggu";
                break;
            case "宁夏":
                shortName = "ningxia";
                break;
            case "青海省":
                shortName = "qinghai";
                break;
            case "山东省":
                shortName = "shandong";
                break;
            case "上海市":
                shortName = "shanghai";
                break;
            case "山西省":
                shortName = "shanxi";
                break;
            case "四川省":
                shortName = "sichuan";
            break;
            case "台州市":
                shortName = "taizhoushi";
            break;
            case "天津市":
                shortName = "tianjin";
            break;
            case "香港":
                shortName = "xianggang";
            break;
            case "新疆":
                shortName = "xinjiang";
            break;
            case "西藏":
                shortName = "xizang";
            break;
            case "雅安市":
                shortName = "yaanshi";
            break;
            case "云南省":
                shortName = "yunnan";
            break;
            case "浙江省":
                shortName = "zhejiang";
            break;
            default:
                shortName = "";
                break;
        }
        return shortName;
    }
}
