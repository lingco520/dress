/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.utils;

/**
 * @Title: ConvertUtil
 * @Author: tanggm
 * @Date: 2018/01/16 15:25
 * @Description: TODO 转换工具类
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */

public class ConvertUtil {

    /**
     * id数组转id字符串
     * @param ids id数组
     * @return id字符串，用 "," 分割，最后一个没有逗号
     */
    public static String idsToString(Long[] ids){
        StringBuffer idsStr = new StringBuffer();
        if(null != ids){
            for (int i = 0; i < ids.length; i++){
                idsStr.append(ids[i]);
                if(i != (ids.length - 1)){
                    idsStr.append(",");
                }
            }
        }
        return idsStr.toString();
    }
}
