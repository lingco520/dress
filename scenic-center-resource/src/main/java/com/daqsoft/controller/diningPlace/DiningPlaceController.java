package com.daqsoft.controller.diningPlace;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.constants.DictConstants;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.diningPlace.ResDiningPlace;
import com.daqsoft.form.PageForm;
import com.daqsoft.framework.service.SysRegionServiceImpl;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.CommonService;
import com.daqsoft.service.diningPlace.DiningPlaceService;
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
 * @Author: superziy .
 * @Date: Created in 9:47 2018/1/22.
 * @Version: 4.0.0
 * @describe: 餐饮场所（增删改查）
 */
@Controller
@RequestMapping(value = "/diningPlace")
public class DiningPlaceController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiningPlaceController.class);
    private static final String PATH = "diningPlace/";
    @Autowired
    private DiningPlaceService diningPlaceService;
    @Autowired
    private CommonService commonService;

    private final SysRegionServiceImpl sysRegionService;

    @Autowired
    public DiningPlaceController(SysRegionServiceImpl sysRegionService) {
        this.sysRegionService = sysRegionService;
    }

    /**
     * 列表页面
     *
     * @return 模板页面
     */
    @GetMapping(Constants.LISTACTION)
    public String list(ModelMap model) {
        //获取餐饮等级
        List<SysDictVo> restaurantLevelDict = commonService.getDictByType(DictConstants.RESTAURANT_LEVEL);
        //获取餐饮的类型
        List<SysDictVo> restaurantTypeDict = commonService.getDictByType(DictConstants.RESTAURANT_TYPE);
        model.addAttribute("restaurantLevel", restaurantLevelDict);
        model.addAttribute("restaurantType", restaurantTypeDict);
        return PATH + "dining_place_list";
    }

    /**
     * 列表数据
     *
     * @param name 特产名称
     * @return 列表页面
     */
    @GetMapping(value = Constants.LISTACTION, headers = HEADER_X_REQUESTED_WITH)
    @ResponseBody
    public BaseResponse list(PageForm pageForm, String name,String level,String type,String status, HttpServletRequest request) {
        try {
            return pageBuilderSuccess(diningPlaceService.list(pageForm, name, getVcode(request),level,type,status));
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
    public String edit(Long id, ModelMap model, HttpServletRequest request) throws Exception {
        ResDiningPlace dto = new ResDiningPlace();
        //获取餐饮等级
        List<SysDictVo> restaurantLevelDict = commonService.getDictByType(DictConstants.RESTAURANT_LEVEL);
        //获取餐饮的类型
        List<SysDictVo> restaurantTypeDict = commonService.getDictByType(DictConstants.RESTAURANT_TYPE);
        try {
            if (id != null) {
                dto = diningPlaceService.get(id);
            } else {
                dto = new ResDiningPlace();
            }
        } catch (Exception e) {
            LOGGER.error("新增/编辑失败:", e);
        }
        model.addAttribute("regions", dto.getRegion() != null ? sysRegionService.queryDeepPathByRegion(Long.valueOf(dto.getRegion())) : "");
        model.addAttribute("dto", dto);
        String memo = commonService.getSysRegionByRegion(getSysUserRegion(request)).getMemo();
        model.addAttribute("CURRENT_REGION", memo);
        model.addAttribute("restaurantLevel", restaurantLevelDict);
        model.addAttribute("restaurantType", restaurantTypeDict);
        return PATH + "dining_place_input";
    }


    /**
     * 保存
     *
     * @param dto
     * @return 成功信息
     */
    @RequestMapping(value = Constants.SAVEACTION, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse save(@Valid @ModelAttribute(value = "dto") ResDiningPlace dto, HttpServletRequest request) {
        dto.setVcode(getVcode(request));//设置vcode
        try {
            //当前登录用户
            if (dto.getId() == null) {
                diningPlaceService.save(dto);
            } else {
                diningPlaceService.update(dto);
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
            diningPlaceService.delete(ids);
        } catch (Exception e) {
            LOGGER.error("删除失败", e);
            return builderFailed("删除失败!");
        }
        return builderSuccess("删除成功");
    }
}
