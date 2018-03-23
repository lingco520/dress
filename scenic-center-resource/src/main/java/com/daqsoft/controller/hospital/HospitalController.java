package com.daqsoft.controller.hospital;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.hospital.Hospital;
import com.daqsoft.form.PageForm;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.CommonService;
import com.daqsoft.service.hospital.HospitalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

/**
 * @Title: 景区基础资源-医疗站
 * @Author: lrd
 * @Date: Created in 17:53 2018/1/23.
 * @Version: 4.0.0
 * @describe:景区基础资源-医疗站
 * @since:JDK 1.8
 */
@Controller
@RequestMapping(value = "/hospital")
public class HospitalController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HospitalController.class);
    private static final String PATH = "hospital/";
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private CommonService commonService;
    /**
     * 列表页面
     *
     * @return 模板页面
     */
    @GetMapping(Constants.LISTACTION)
    public String list() {
        return PATH + "hospital_list";
    }
    /**
     * 列表数据
     *
     * @param name      加油站名称
     * @return 列表页面
     */
    @GetMapping(value = Constants.LISTACTION, headers = HEADER_X_REQUESTED_WITH)
    @ResponseBody
    public BaseResponse list(PageForm pageForm, String name, String address, HttpServletRequest request) {
        try {
            pageForm.setVcode(getVcode(request));
            return pageBuilderSuccess(hospitalService.list(pageForm, name, address));
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
        Hospital dto = new Hospital();
        try {
            if (id != null) {
                dto = hospitalService.get(id);
            }
        } catch (Exception e){
            LOGGER.error("新增/编辑失败:", e);
        }
        model.addAttribute("dto", dto);
        String memo = commonService.getSysRegionByRegion(getSysUserRegion(request)).getMemo();
        model.addAttribute("CURRENT_REGION", memo);
        return PATH + "hospital_input";
    }


    /**
     * 保存
     *
     * @param dto
     * @return 成功信息
     */
    @RequestMapping(value = Constants.SAVEACTION, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse save(@Valid @ModelAttribute(value = "dto") Hospital dto, HttpServletRequest request) {
        try {
            dto.setVcode(getVcode(request));
            dto.setUpdateTime(new Date());
            //当前登录用户
            if (dto.getId() == null) {
                hospitalService.save(dto);
            } else {
                hospitalService.update(dto);
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
            hospitalService.delete(ids);
        } catch (Exception e) {
            LOGGER.error("删除失败", e);
            return builderFailed("删除失败!");
        }
        return builderSuccess("删除成功");
    }
}
