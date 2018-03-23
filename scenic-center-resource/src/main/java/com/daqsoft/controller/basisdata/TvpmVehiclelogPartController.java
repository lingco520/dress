/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.controller.basisdata;

import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.dataconfig.DataBasisConfig;
import com.daqsoft.framework.service.SysRegionServiceImpl;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.dataimitate.DataBasisConfigService;
import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: TvpmVehiclelogPartController
 * @Author: tanggm
 * @Date: 2018/02/09 17:09
 * @Description: TODO 车流量 数据模拟
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Controller
@RequestMapping(value = "/tvpmVehiclelogPart")
public class TvpmVehiclelogPartController extends BaseResponseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(TvpmVehiclelogPartController.class);
    private static final String PATH = "basisData/";
    @Autowired
    private DataBasisConfigService dataBasisConfigService;
    private final SysRegionServiceImpl sysRegionService;
    @Autowired
    public TvpmVehiclelogPartController(SysRegionServiceImpl sysRegionService) {
        this.sysRegionService = sysRegionService;
    }
    /**
     * 跳转到车流量配置页面
     */
    @GetMapping(Constants.EDITACTION)
    public String edit(ModelMap model, HttpServletRequest request) {
        String vcode = getVcode(request);
        DataBasisConfig dto = dataBasisConfigService.findByVcode(vcode);
        // 封装返回数据
        List<DataBasisConfig> dtoList = new ArrayList<>();
        List allRegionList = new ArrayList<>();
        // 地区名称，region字符串拼接列表
        List regionNameValueList = new ArrayList<>();
        List regionValueList = new ArrayList<>();
        try {
            if (dto == null) {
                dtoList.add(new DataBasisConfig());
            }else {
                model.addAttribute("id", dto.getId());
                model.addAttribute("dtvpStartDate", dto.getDtvpStartDate());
                model.addAttribute("dtvpEndDate", dto.getDtvpEndDate());
                // 默认地址加载
                if(!StringUtils.isEmpty(dto.getDtvpRegionName())){
                    String[] dtvpRegionNames = dto.getDtvpRegionName().split(",");
                    String[] dtvpRegions = dto.getDtvpRegion().split(",");
                    String[] dtvpRandomMins = dto.getDtvpRandomMin().split(",");
                    String[] dtvpRandomMaxs = dto.getDtvpRandomMax().split(",");
                    for(int i = 0; i < dtvpRegionNames.length; i++){
                        DataBasisConfig dataBasisConfig = new DataBasisConfig();
                        String[] regionNames = dtvpRegionNames[i].split("@");
                        String[] regions = dtvpRegions[i].split("@");
                        List<Map> regionList = new ArrayList<>();
                        if(regionNames != null){
                            for(int j = 0; j < regionNames.length; j++){
                                Map dataMap = new HashMap();
                                dataMap.put("region", regions[j]);
                                dataMap.put("name", regionNames[j]);
                                regionList.add(dataMap);
                            }
                        }
                        allRegionList.add(regionList);
                        dataBasisConfig.setDtvpRandomMin(dtvpRandomMins[i]);
                        dataBasisConfig.setDtvpRandomMax(dtvpRandomMaxs[i]);
                        dtoList.add(dataBasisConfig);

                        // 为初始input赋值，回显
                        regionNameValueList.add(dtvpRegionNames[i]);
                        regionValueList.add(dtvpRegions[i]);
                    }
                }else {
                    dtoList.add(new DataBasisConfig());
                }
            }
        } catch (Exception e){
            LOGGER.error("新增/编辑失败:", e);
        }
        model.addAttribute("regionList", allRegionList);
        model.addAttribute("regionNameValueList", regionNameValueList);
        model.addAttribute("regionValueList", regionValueList);
        model.addAttribute("dtoList", dtoList);
        return PATH + "tvpm_vehiclelog_part";
    }
}
