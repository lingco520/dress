package com.daqsoft.mapper.client.jqScenicMsg;

import com.daqsoft.vo.client.madeVoBean.JqScenicAreas;
import com.daqsoft.vo.client.madeVoBean.JqScenicMsgVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author zhouq
 * @email zhouq@daqsoft.com
 * @date 2017-06-26 17:57
 * @Version:
 * @Describe: 景区信息DAO
 */
@Repository
public interface JqScenicMsgDao {

    /**
     * 获取景区相关信息
     * @param vcode
     * @return
     */
    List<JqScenicAreas> getJqScenicList(String vcode);

    /**
     * 关联资源编码并返回景区基础信息
     * @param vcode
     * @return 景区资源编码
     */
    List<JqScenicMsgVO> getJqResourceCode(@Param("vcode") String vcode);

    /**
     * 获取景区region
     * @param vcode
     * @return
     */
    List<JqScenicAreas> getJqScenicRegion(@Param("vcode") String vcode);

    /**
     * 通过景区编码获取对应省份的region
     * @param vcode
     * @return
     */
    String getJqScenicProvinceRegion(String vcode);
}
