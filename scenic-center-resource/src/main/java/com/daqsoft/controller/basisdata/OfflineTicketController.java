/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.controller.basisdata;

import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.dataconfig.DataBasisConfig;
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

/**
 * @Title: OfflineTicketController
 * @Author: tanggm
 * @Date: 2018/02/09 17:08
 * @Description: TODO 线下票务 数据模拟
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Controller
@RequestMapping(value = "/offlineTicket")
public class OfflineTicketController extends BaseResponseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(OfflineTicketController.class);
    private static final String PATH = "basisData/";
    @Autowired
    private DataBasisConfigService dataBasisConfigService;
    /**
     * 跳转到线下票务配置页面
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
                model.addAttribute("dofftStartDate", dto.getDofftStartDate());
                model.addAttribute("dofftEndDate", dto.getDofftEndDate());
                if(!StringUtils.isEmpty(dto.getDofftName())){
                    String[] dofftNames = dto.getDofftName().split(",");
                    String[] dofftPrices = dto.getDofftPrice().split(",");
                    String[] dofftNumbers = dto.getDofftNumber().split(",");
                    String[] dofftRandomMins = dto.getDofftRandomMin().split(",");
                    String[] dofftRandomMaxs = dto.getDofftRandomMax().split(",");
                    for(int i = 0; i < dofftNames.length; i++){
                        DataBasisConfig dataBasisConfig = new DataBasisConfig();
                        dataBasisConfig.setDofftName(dofftNames[i]);
                        dataBasisConfig.setDofftPrice(dofftPrices[i]);
                        dataBasisConfig.setDofftNumber(dofftNumbers[i]);
                        dataBasisConfig.setDofftRandomMin(dofftRandomMins[i]);
                        dataBasisConfig.setDofftRandomMax(dofftRandomMaxs[i]);
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
        return PATH + "offline_ticket";
    }
}
