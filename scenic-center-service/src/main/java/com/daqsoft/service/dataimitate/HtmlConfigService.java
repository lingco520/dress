package com.daqsoft.service.dataimitate;

import com.daqsoft.entity.htmlConfig.HtmlConfig;
import com.daqsoft.mapper.resource.HtmlConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: 业务数据模块选择service
 * @Author: lyl
 * @Date: 2018/02/26 17:00
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
public class HtmlConfigService {
    @Autowired
    private HtmlConfigMapper htmlConfigMapper;


    /**
     * 存储模板操作
     *
     * @param vcode
     * @param dto
     * @throws Exception
     */
    public void save(String vcode, HtmlConfig dto) throws Exception {
        htmlConfigMapper.emptyTable(vcode);
        String hkey[] = dto.getHkey().split(",");
        for (int i = 0; i < hkey.length; i++) {
            Map map = new HashMap();
            map.put("hkey", hkey[i]);
            map.put("vcode", dto.getVcode());
            htmlConfigMapper.insert(map);
        }
    }
    /**
     * 所有模块查询
     *
     * @return
     */
    public List<Map> moduleSelect() {
        return htmlConfigMapper.moduleSelect();
    }

    /**
     * 当前景区查询
     *
     * @return
     */
    public List<HtmlConfig> getOne(String vcode) {
        return htmlConfigMapper.getOne(vcode);
    }

}