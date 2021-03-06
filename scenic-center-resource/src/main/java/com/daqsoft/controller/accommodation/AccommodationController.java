package com.daqsoft.controller.accommodation;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.constants.DictConstants;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.acommodation.ResAccommodation;
import com.daqsoft.form.PageForm;
import com.daqsoft.framework.service.SysRegionServiceImpl;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.CommonService;
import com.daqsoft.service.accommodation.AccommodationService;
import com.daqsoft.vo.SysDictVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @Title: 宾馆酒店
 * @Author: lyl
 * @Date: 2018/01/12 17:47
 * @Description: TODO
 * @Comment：
 * @Version: 4.0.0
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Controller
@RequestMapping(value = "/accommodation")
public class AccommodationController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccommodationController.class);
    private static final String PATH = "accommodation/";
    @Autowired
    private AccommodationService accommodationService;
    @Autowired
    private CommonService commonService;

    private final SysRegionServiceImpl sysRegionService;

    public AccommodationController(SysRegionServiceImpl sysRegionService) {
        this.sysRegionService = sysRegionService;
    }


    /**
     * 列表页面
     *
     * @return 模板页面
     */
    @GetMapping(Constants.LISTACTION)
    public String list() {
        return PATH + "accommodation_list";
    }

    /**
     * 列表数据
     *
     * @param name 酒店名称
     * @return 列表页面
     */
    @GetMapping(value = Constants.LISTACTION, headers = HEADER_X_REQUESTED_WITH)
    @ResponseBody
    public BaseResponse list(PageForm pageForm, String name, HttpServletRequest request) {
        try {
            return pageBuilderSuccess(accommodationService.list(pageForm, name, getVcode(request)));
        } catch (Exception e) {
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
        ResAccommodation dto = new ResAccommodation();
        //获取酒店等级
        List<SysDictVo> hotelLevelDict = commonService.getDictByType(DictConstants.HOTEL_RATING);
        //获取登记注册类型
        List<SysDictVo> registerLevelDict = commonService.getDictByType(DictConstants.REGISTER_LEVEL);
        //获取酒店类型
        List<SysDictVo> accomodationTypeDict = commonService.getDictByType(DictConstants.ACCOMODATION_TYPE);
        try {
            if (id != null) {
                dto = accommodationService.get(id);
            }
        } catch (Exception e) {
            LOGGER.error("新增/编辑失败:", e);
        }
        model.addAttribute("regions", dto.getRegion() != null ? sysRegionService.queryDeepPathByRegion(Long.valueOf(dto.getRegion())) : "");
        model.addAttribute("dto", dto);
        String memo = commonService.getSysRegionByRegion(getSysUserRegion(request)).getMemo();
        model.addAttribute("CURRENT_REGION", memo);
        model.addAttribute("touristHotelLevel", hotelLevelDict);
        model.addAttribute("registerType", registerLevelDict);
        model.addAttribute("acommodationType", accomodationTypeDict);
        return PATH + "accommodation_input";
    }


    /**
     * 保存
     *
     * @param dto
     * @return 成功信息
     */
    @RequestMapping(value = Constants.SAVEACTION, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse save(@Valid @ModelAttribute(value = "dto") ResAccommodation dto, HttpServletRequest request) {
        dto.setVcode(getVcode(request));//设置vcode
        try {
            //当前登录用户
            if (dto.getId() == null) {
                accommodationService.save(dto);
            } else {
                accommodationService.update(dto);
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
            accommodationService.delete(ids);
        } catch (Exception e) {
            LOGGER.error("删除失败", e);
            return builderFailed("删除失败!");
        }
        return builderSuccess("删除成功");
    }


}
