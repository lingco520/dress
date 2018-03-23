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
import java.util.List;
import java.util.Map;

/**
 * @Title: TimelyParkingController
 * @Author: tanggm
 * @Date: 2018/02/09 17:09
 * @Description: TODO 实时停车位 数据模拟
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Controller
@RequestMapping(value = "/timelyParking")
public class TimelyParkingController extends BaseResponseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(TimelyParkingController.class);
    private static final String PATH = "basisData/";
    @Autowired
    private DataBasisConfigService dataBasisConfigService;
    private final SysRegionServiceImpl sysRegionService;
    @Autowired
    public TimelyParkingController(SysRegionServiceImpl sysRegionService) {
        this.sysRegionService = sysRegionService;
    }
    /**
     * 跳转到实时停车位配置页面
     */
    @GetMapping(Constants.EDITACTION)
    public String edit(ModelMap model, HttpServletRequest request) {
        String vcode = getVcode(request);
        DataBasisConfig dto = dataBasisConfigService.findByVcode(vcode);
        // 封装返回数据
        List<DataBasisConfig> dtoList = new ArrayList<>();
        try {
            if (dto == null) {
                dtoList.add(new DataBasisConfig());
            }else {
                model.addAttribute("id", dto.getId());
                model.addAttribute("dtpStartDate", dto.getDtpStartDate());
                model.addAttribute("dtpEndDate", dto.getDtpEndDate());
                if(!StringUtils.isEmpty(dto.getDtpParkingId())){
                    String[] dtpParkingIds = dto.getDtpParkingId().split(",");
                    String[] dtpTotals = dto.getDtpTotal().split(",");
                    String[] dtpUsedMins = dto.getDtpUsedMin().split(",");
                    String[] dtpUsedMaxs = dto.getDtpUsedMax().split(",");
                    String[] dtpRandomMins = dto.getDtpRandomMin().split(",");
                    String[] dtpRandomMaxs = dto.getDtpRandomMax().split(",");
                    for(int i = 0; i < dtpParkingIds.length; i++){
                        DataBasisConfig dataBasisConfig = new DataBasisConfig();
                        dataBasisConfig.setDtpParkingId(dtpParkingIds[i]);
                        dataBasisConfig.setDtpTotal(dtpTotals[i]);
                        dataBasisConfig.setDtpUsedMin(dtpUsedMins[i]);
                        dataBasisConfig.setDtpUsedMax(dtpUsedMaxs[i]);
                        dataBasisConfig.setDtpRandomMin(dtpRandomMins[i]);
                        dataBasisConfig.setDtpRandomMax(dtpRandomMaxs[i]);
                        dtoList.add(dataBasisConfig);
                    }
                }else {
                    dtoList.add(new DataBasisConfig());
                }
            }
        } catch (Exception e){
            LOGGER.error("新增/编辑失败:", e);
        }
        model.addAttribute("dtoList", dtoList);
        // 查询景区停车场列表
        List<Map> parkingList = dataBasisConfigService.getParkingList(vcode);
        model.addAttribute("parkingList", parkingList);
        model.addAttribute("parkingLot", parkingList != null && parkingList.size() > 0 ? parkingList.get(0).get("parkingLot"):0);
        model.addAttribute("dtoList", dtoList);
        return PATH + "timely_parking";
    }
}
