/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.controller.guide;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.constants.DictConstants;
import com.daqsoft.constants.ScrsConstants;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.guide.ResGuide;
import com.daqsoft.form.PageForm;
import com.daqsoft.framework.service.SysRegionServiceImpl;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.CommonService;
import com.daqsoft.service.guide.GuideService;
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
 * @Title: GuideController
 * @Author: tanggm
 * @Date: 2018/01/09 17:00
 * @Description: TODO 基础资源-导游信息
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Controller
@RequestMapping(value = "/guide")
public class GuideController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuideController.class);
    private static final String PATH = "guide/";
    @Autowired
    private GuideService guideService;
    @Autowired
    private CommonService commonService;
    private final SysRegionServiceImpl sysRegionService;
    @Autowired
    public GuideController(SysRegionServiceImpl sysRegionService) {
        this.sysRegionService = sysRegionService;
    }
    /**
     * 列表页面
     *
     * @return 模板页面
     */
    @GetMapping(Constants.LISTACTION)
    public String list(ModelMap model) {
        try {
            // 导游等级
            List<SysDictVo> guideLevel = commonService.getDictByType(DictConstants.GUIDE_LEVEL);
            model.addAttribute("guideLevel", guideLevel);
            // 导游性别
            List<SysDictVo> gender = commonService.getDictByType(DictConstants.GENDER);
            model.addAttribute("gender", gender);
            return PATH + "guide_list";
        } catch (Exception e){
            LOGGER.error("异常错误:", e);
            return ScrsConstants.ERROR_PAGE;
        }

    }
    /**
     * 列表数据
     *
     * @param name      导游名称
     * @return 列表页面
     */
    @GetMapping(value = Constants.LISTACTION, headers = HEADER_X_REQUESTED_WITH)
    @ResponseBody
    public BaseResponse list(PageForm pageForm, String name, String guideLevel, String gender, Integer status, HttpServletRequest request) {
        try {
            pageForm.setVcode(getVcode(request));
            return pageBuilderSuccess(guideService.list(pageForm, name, guideLevel, gender, status));
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
    public String edit(Long id, ModelMap model) {
        ResGuide dto = new ResGuide();
        try {
            if (id != null) {
                dto = guideService.get(id);
            }
        } catch (Exception e){
            LOGGER.error("新增/编辑失败:", e);
        }
        // 导游等级
        List<SysDictVo> guideLevel = commonService.getDictByType(DictConstants.GUIDE_LEVEL);
        model.addAttribute("guideLevel", guideLevel);
        // 导游性别
        List<SysDictVo> gender = commonService.getDictByType(DictConstants.GENDER);
        model.addAttribute("gender", gender);
        // 语言
        List<SysDictVo> language = commonService.getDictByType(DictConstants.LANGUAGE);
        model.addAttribute("language", language);
        // 学历
        List<SysDictVo> education = commonService.getDictByType(DictConstants.EDUCATION);
        model.addAttribute("education", education);
        // 民族
        List<SysDictVo> nationality = commonService.getDictByType(DictConstants.ETHNICITY);
        model.addAttribute("nationality", nationality);
        model.addAttribute("regions", dto.getRegion() != null ? sysRegionService.queryDeepPathByRegion(Long.valueOf(dto.getRegion())) : "");
        model.addAttribute("dto", dto);
        return PATH + "guide_input";
    }


    /**
     * 保存
     *
     * @param dto
     * @return 成功信息
     */
    @RequestMapping(value = Constants.SAVEACTION, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse save(@Valid @ModelAttribute(value = "dto") ResGuide dto, HttpServletRequest request) {
        try {
            dto.setVcode(getVcode(request));
            dto.setUpdateTime(new Date());
            //当前登录用户
            if (dto.getId() == null) {
                guideService.save(dto);
            } else {
                guideService.update(dto);
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
            guideService.delete(ids);
        } catch (Exception e) {
            LOGGER.error("删除失败", e);
            return builderFailed("删除失败!");
        }
        return builderSuccess("删除成功");
    }
}
