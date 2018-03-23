/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.dataimitate;

import com.daqsoft.entity.dataconfig.DataBasisConfig;
import com.daqsoft.mapper.resource.DataBasisConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Title: DataBasisConfigService
 * @Author: tanggm
 * @Date: 2018/02/02 13:34
 * @Description: TODO 数据模拟---基础数据模拟
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Service
public class DataBasisConfigService {
    @Autowired
    private DataBasisConfigMapper dataBasisConfigMapper;

    /**
     * 详情
     * @param id
     * @return
     * @throws Exception
     */
    public DataBasisConfig get(Long id) throws Exception{
        return dataBasisConfigMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询景区停车场列表
     * @param vcode
     * @return
     */
    public List<Map> getParkingList(String vcode) {
        return dataBasisConfigMapper.getParkingList(vcode);
    }

    /**
     * 查询停车场信息
     * @param parkingId 停车场ID
     * @return
     */
    public Map getParkingById(Long parkingId) {
        return dataBasisConfigMapper.getParkingById(parkingId);
    }
    /**
     * 保存
     * @param dto
     * @throws Exception
     */
    public void save(DataBasisConfig dto) throws Exception{
        dataBasisConfigMapper.insert(dto);
    }

    /**
     * 通过vcode查询记录
     * @param vcode
     * @return
     */
    public DataBasisConfig findByVcode(String vcode) {
        return dataBasisConfigMapper.findByVcode(vcode);
    }

    public void update(DataBasisConfig dto) throws Exception {
        dataBasisConfigMapper.updateByPrimaryKey(dto);
    }
}
