package com.daqsoft.utils.client;


import com.daqsoft.vo.client.madeVoBean.RealPeopleComeFromVo;

/**
 * @Author: superziy .
 * @Date: Created in 11:30 2017/6/22.
 * @Version: 1.0
 * @describe: 过滤可变参数满足平台灵活配置
 */
public class FiltrationUtil {



    /**
     * 过滤到传入vcode是哪个景区，适应每个景区所需要的要求返回参数
     *
     * @return
     */
    public static RealPeopleComeFromVo getRealPeopleList(String vcode, String sourceCity, String sourcecityNew, String sourcecityPro) {
        RealPeopleComeFromVo comeFromVo = new RealPeopleComeFromVo();

        if (!"".equals(vcode) && vcode.equals("d5034caae86f1081e0e6ae5337e48e9f")) {//如果是云台山vcode,进来过滤本省份
            if (!"".equals(sourceCity) && !sourceCity.equals("河南省")) {//过滤掉本省数据
                if(!"".equals(sourceCity) && !sourceCity.equals("未知省份")) {
                    comeFromVo.setSourceCity(sourceCity);
                    comeFromVo.setSourcecityNew(sourcecityNew);
                    comeFromVo.setSourcecityPro(sourcecityPro);
                }
            }
        }
        return comeFromVo;
    }
}
