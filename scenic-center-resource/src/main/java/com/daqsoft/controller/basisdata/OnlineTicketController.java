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
 * @Title: OnlineTicket
 * @Author: tanggm
 * @Date: 2018/02/09 17:08
 * @Description: TODO 线上票务 数据模拟
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Controller
@RequestMapping(value = "/onlineTicket")
public class OnlineTicketController extends BaseResponseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(OnlineTicketController.class);
    private static final String PATH = "basisData/";
    @Autowired
    private DataBasisConfigService dataBasisConfigService;
    /**
     * 跳转到线上票务配置页面
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
                model.addAttribute("dontStartDate", dto.getDontStartDate());
                model.addAttribute("dontEndDate", dto.getDontEndDate());
                if(!StringUtils.isEmpty(dto.getDontName())){
                    String[] dontNames = dto.getDontName().split(",");
                    String[] dontPrices = dto.getDontPrice().split(",");
                    String[] dontNumbers = dto.getDontNumber().split(",");
                    String[] dontRandomMins = dto.getDontRandomMin().split(",");
                    String[] dontRandomMaxs = dto.getDontRandomMax().split(",");
                    for(int i = 0; i < dontNames.length; i++){
                        DataBasisConfig dataBasisConfig = new DataBasisConfig();
                        dataBasisConfig.setDontName(dontNames[i]);
                        dataBasisConfig.setDontPrice(dontPrices[i]);
                        dataBasisConfig.setDontNumber(dontNumbers[i]);
                        dataBasisConfig.setDontRandomMin(dontRandomMins[i]);
                        dataBasisConfig.setDontRandomMax(dontRandomMaxs[i]);
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
        return PATH + "online_ticket";
    }
}
