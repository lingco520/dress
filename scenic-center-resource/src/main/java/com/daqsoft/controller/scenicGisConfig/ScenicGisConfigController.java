package com.daqsoft.controller.scenicGisConfig;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.scenicGisConfig.ResScenicGisConfig;
import com.daqsoft.form.PageForm;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.CommonService;
import com.daqsoft.service.scenicGisConfig.ScenicGisConfigService;
import com.daqsoft.vo.ScenicGisConfigVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Author: superziy .
 * @Date: Created in 9:47 2018/1/29
 * @Version: 4.0.0
 * @describe: 景区gis地图配置
 */
@Controller
@RequestMapping(value = "/scenicGisConfig")
public class ScenicGisConfigController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScenicGisConfigController.class);
    private static final String PATH = "scenicGisConfig/";
    @Autowired
    private ScenicGisConfigService scenicGisConfigService;
    @Autowired
    private CommonService commonService;

    /**
     * 列表页面
     *
     * @return 模板页面
     */
    @GetMapping(Constants.LISTACTION)
    public String list(ModelMap model) {
        return PATH + "scenic_gis_config_list";
    }

    /**
     * 列表数据
     * @param pageForm
     * @param name 景点名称
     * @param rating 等级
     * @param status 状态
     * @param request
     * @return
     */
    @GetMapping(value = Constants.LISTACTION, headers = HEADER_X_REQUESTED_WITH)
    @ResponseBody
    public BaseResponse list(PageForm pageForm, String name, String rating, String status, HttpServletRequest request) {
        try {
            return pageBuilderSuccess(scenicGisConfigService.list(pageForm, name, getVcode(request), rating, status));
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
        ResScenicGisConfig dto = new ResScenicGisConfig();
        try {
            if (id != null) {
                dto = scenicGisConfigService.get(id);
            }
        } catch (Exception e) {
            LOGGER.error("新增/编辑失败:", e);
        }

        model.addAttribute("dto", dto);
        return PATH + "scenic_gis_config_input";
    }


    /**
     * 保存
     *
     * @param dto
     * @return 成功信息
     */
    @RequestMapping(value = Constants.SAVEACTION, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse save(@Valid @ModelAttribute(value = "dto") ResScenicGisConfig dto, HttpServletRequest request) {
        dto.setVcode(getVcode(request));//设置vcode
        try {
            //当前登录用户
            if (dto.getId() == null) {
                //查询景区是否有gis地图
                Integer isGis = scenicGisConfigService.getIsGis(getVcode(request));
                if(isGis > 0){
                    return builderFailed("该景区已存在超图！");
                }
                scenicGisConfigService.save(dto);
            } else {
                scenicGisConfigService.update(dto);
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
            scenicGisConfigService.delete(ids);
        } catch (Exception e) {
            LOGGER.error("删除失败", e);
            return builderFailed("删除失败!");
        }
        return builderSuccess("删除成功");
    }

    /**
     * 查看页面
     *
     * @return 模板页面
     */
    @GetMapping(Constants.VIEWACTION)
    public String list(ModelMap model, HttpServletRequest request) {
        //查询相关配置
        ScenicGisConfigVo gisConfig = commonService.getGisConfig(getVcode(request));
        model.addAttribute("dto", gisConfig);
        //gis地图页面
        return "scenicGis/scenic_gis_view";
    }
}
