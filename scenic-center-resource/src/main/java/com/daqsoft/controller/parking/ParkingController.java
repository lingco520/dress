/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.controller.parking;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.constants.DictConstants;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.parking.ResParking;
import com.daqsoft.form.PageForm;
import com.daqsoft.framework.service.SysRegionServiceImpl;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.CommonService;
import com.daqsoft.service.parking.ParkingService;
import com.daqsoft.vo.SysDictVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @Title: ParkingController
 * @Author: tanggm
 * @Date: 2018/01/23 11:00
 * @Description: TODO 基础资源-导游信息
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Controller
@RequestMapping(value = "/parking")
public class ParkingController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingController.class);
    private static final String PATH = "parking/";
    @Autowired
    private ParkingService parkingService;
    @Autowired
    private CommonService commonService;
    private final SysRegionServiceImpl sysRegionService;
    @Autowired
    public ParkingController(SysRegionServiceImpl sysRegionService) {
        this.sysRegionService = sysRegionService;
    }
    /**
     * 列表页面
     *
     * @return 模板页面
     */
    @GetMapping(Constants.LISTACTION)
    public String list(ModelMap model) {
        // 停车场类别
        List<SysDictVo> parkingType = commonService.getDictByType(DictConstants.PARKING_TYPE);
        model.addAttribute("parkingType", parkingType);
        return PATH + "parking_list";
    }
    /**
     * 列表数据
     *
     * @param name      停车场名称名称
     * @param parkingType      停车场类型
     * @return 列表页面
     */
    @GetMapping(value = Constants.LISTACTION, headers = HEADER_X_REQUESTED_WITH)
    @ResponseBody
    public BaseResponse list(PageForm pageForm, String name, String parkingType, Integer status, HttpServletRequest request) {
        try {
            pageForm.setVcode(getVcode(request));
            return pageBuilderSuccess(parkingService.list(pageForm, name, parkingType, status));
        } catch (Exception e){
            LOGGER.error("查询错误:", e);
            return builderFailed("查询错误");
        }
    }
    /**
     * 新增/编辑
     *
     * @param id    数据id
     * @param model 数据对象
     * @return 新增/编辑页面
     */
    @GetMapping(Constants.EDITACTION)
    public String edit(Long id, ModelMap model, HttpServletRequest request) {
        ResParking dto = new ResParking();
        try {
            if (id != null) {
                dto = parkingService.get(id);
            }
        } catch (Exception e){
            LOGGER.error("新增/编辑失败:", e);
        }
        // 停车场类别
        List<SysDictVo> parkingType = commonService.getDictByType(DictConstants.PARKING_TYPE);
        model.addAttribute("parkingType", parkingType);
        // 地区所有region查询
        model.addAttribute("regions", dto.getRegion() != null ? sysRegionService.queryDeepPathByRegion(Long.valueOf(dto.getRegion())) : "");
        model.addAttribute("dto", dto);
        // 返回region，用于地图初始化地理位置, key 必须为 CURRENT_REGION 这个值,js代码默认是取这个值
        String memo = commonService.getSysRegionByRegion(getSysUserRegion(request)).getMemo();
        model.addAttribute("CURRENT_REGION", memo);
        return PATH + "parking_input";
    }


    /**
     * 保存
     *
     * @param dto
     * @return 成功信息
     */
    @RequestMapping(value = Constants.SAVEACTION, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse save(@Valid @ModelAttribute(value = "dto") ResParking dto, HttpServletRequest request) {
        try {
            dto.setVcode(getVcode(request));
            dto.setUpdateTime(new Date());
            if (dto.getId() == null) {
                parkingService.save(dto);
            } else {
                parkingService.update(dto);
            }
        } catch (Exception e) {
            LOGGER.error("保存失败:", e);
            return builderFailed("保存失败");
        }
        return builderSuccess(Constants.SAVESUCCESSOPERATION);
    }

    /**
     * 删除
     *
     * @param ids 数据集
     * @return 成功信息
     */
    @PostMapping(Constants.DELETEACTION)
    @ResponseBody
    public BaseResponse delete(Long[] ids) {
        try {
            parkingService.delete(ids);
        } catch (Exception e) {
            LOGGER.error("删除失败", e);
            return builderFailed("删除失败!");
        }
        return builderSuccess("删除成功");
    }
}
