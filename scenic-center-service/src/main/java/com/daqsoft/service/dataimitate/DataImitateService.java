/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.dataimitate;

import com.daqsoft.entity.businessData.BusinessData;
import com.daqsoft.mapper.resource.BusinessDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

/**
 * @Title: DataImitateService
 * @Author: tanggm
 * @Date: 2018/03/06 14:34
 * @Description: TODO 保存景区业务数据模拟，数据项与数据值
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Service
public class DataImitateService {
    @Autowired
    private BusinessDataMapper businessDataMapper;

    /**
     * 保存业务数据模拟数据项与数据值
     * @param vcode
     * @param businessData
     * @throws Exception
     */
    void saveDataImitate(String vcode, BusinessData businessData) throws Exception{
        businessDataMapper.deleteBusinessData(vcode);
        StringBuffer insertSql = new StringBuffer("INSERT INTO data_imitate (SKEY, SVALUE, VCODE) VALUES ");
        Field[] fields = businessData.getClass().getDeclaredFields();
        for (Field field : fields){
            field.setAccessible(true);
            // 判断如果值为空则不存记录值
            if(null != field.get(businessData)){
                insertSql.append("('");
                insertSql.append(field.getName());
                insertSql.append("', '");
                insertSql.append(field.get(businessData));
                insertSql.append("', '");
                insertSql.append(vcode);
                insertSql.append("')");
                insertSql.append(",");
            }
        }
        businessDataMapper.saveBusinessData(insertSql.substring(0, insertSql.length() - 1));
    }
}
