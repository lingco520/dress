/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.controller.featureFood;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.constants.DictConstants;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.featureFood.ResFeatureFood;
import com.daqsoft.form.PageForm;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.CommonService;
import com.daqsoft.service.featureFood.FeatureFoodService;
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
 * @Title: FeatureFoodController
 * @Author: tanggm
 * @Date: 2018/01/09 17:00
 * @Description: TODO 基础资源-特色美食
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Controller
@RequestMapping(value = "/featureFood")
public class FeatureFoodController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeatureFoodController.class);
    private static final String PATH = "featureFood/";
    @Autowired
    private FeatureFoodService featureFoodService;
    @Autowired
    private CommonService commonService;

    /**
     * 列表页面
     *
     * @return 模板页面
     */
    @GetMapping(Constants.LISTACTION)
    public String list() {
        return PATH + "feature_food_list";
    }
    /**
     * 列表数据
     *
     * @param name      美食名称
     * @return 列表页面
     */
    @GetMapping(value = Constants.LISTACTION, headers = HEADER_X_REQUESTED_WITH)
    @ResponseBody
    public BaseResponse list(PageForm pageForm, String name, Integer status, HttpServletRequest request) {
        try {
            pageForm.setVcode(getVcode(request));
            return pageBuilderSuccess(featureFoodService.list(pageForm, name, status));
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
        ResFeatureFood dto = new ResFeatureFood();
        try {
            if (id != null) {
                dto = featureFoodService.get(id);
            }
        } catch (Exception e){
            LOGGER.error("新增/编辑失败:", e);
        }
        List<SysDictVo> languageType = commonService.getDictByType(DictConstants.LANGUAGE_TYPE);
        model.addAttribute("dto", dto);
        model.addAttribute("languageType", languageType);
        return PATH + "feature_food_input";
    }


    /**
     * 保存
     *
     * @param dto 特色美食
     * @return 成功信息
     */
    @RequestMapping(value = Constants.SAVEACTION, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse save(@Valid @ModelAttribute(value = "dto") ResFeatureFood dto, HttpServletRequest request) {
        try {
            dto.setVcode(getVcode(request));
            dto.setAuthor(getAccount(request));
            dto.setUpdateTime(new Date());
            //当前登录用户
            if (dto.getId() == null) {
                featureFoodService.save(dto);
            } else {
                featureFoodService.update(dto);
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
            featureFoodService.delete(ids);
        } catch (Exception e) {
            LOGGER.error("删除失败", e);
            return builderFailed("删除失败!");
        }
        return builderSuccess("删除成功");
    }
}
