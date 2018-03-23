package com.daqsoft.controller.gisMapTypeManage;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.constants.DictConstants;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.gisClickManage.ResGisClickManage;
import com.daqsoft.entity.gisMapTypeManage.ResGisMapTypeManage;
import com.daqsoft.form.PageForm;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.CommonService;
import com.daqsoft.service.gisClickManage.GisClickManageService;
import com.daqsoft.service.gisMapTypeManage.GisMapTypeManageService;
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
 * @Title: gis地图类型管理
 * @Author: superziy .
 * @Date: Created in 11:00 2018/2/6.
 * @Version: 4.0.0
 * @describe: gis地图类型管理
 * @since:JDK 1.8
 */
@Controller
@RequestMapping(value = "/gisMapTypeManage")
public class GisMapTypeManageController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GisMapTypeManageController.class);
    private static final String PATH = "gisMapTypeManage/";
    @Autowired
    private GisMapTypeManageService gisMapTypeManageService;
    @Autowired
    private CommonService commonService;

    /**
     * 列表页面
     *
     * @return 模板页面
     */
    @GetMapping(Constants.LISTACTION)
    public String list() {
        return PATH + "gisMapTypeManage_list";
    }

    /**
     * 列表数据
     *
     * @param name 名称
     * @return 列表页面
     */
    @GetMapping(value = Constants.LISTACTION, headers = HEADER_X_REQUESTED_WITH)
    @ResponseBody
    public BaseResponse list(PageForm pageForm, String name, String status, HttpServletRequest request) {
        try {
            return pageBuilderSuccess(gisMapTypeManageService.list(pageForm, name, status));
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
        ResGisMapTypeManage dto = new ResGisMapTypeManage();
        try {
            if (id != null) {
                dto = gisMapTypeManageService.get(id);
            } else {
                dto = new ResGisMapTypeManage();
            }
        } catch (Exception e) {
            LOGGER.error("新增/编辑失败:", e);
        }
        model.addAttribute("dto", dto);
        //获取地图展示端
        List<SysDictVo> gisShowTypeDict = commonService.getDictByType(DictConstants.GISSHOW_TYPE);
        model.addAttribute("gisShowTypeDict", gisShowTypeDict);
        return PATH + "gisMapTypeManage_input";
    }


    /**
     * 保存
     *
     * @param dto
     * @return 成功信息
     */
    @RequestMapping(value = Constants.SAVEACTION, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse save(@Valid @ModelAttribute(value = "dto") ResGisMapTypeManage dto, HttpServletRequest request) {
        try {
            //当前登录用户
            if (dto.getId() == null) {
                gisMapTypeManageService.save(dto);
            } else {
                gisMapTypeManageService.update(dto);
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
            gisMapTypeManageService.delete(ids);
        } catch (Exception e) {
            LOGGER.error("删除失败", e);
            return builderFailed("删除失败!");
        }
        return builderSuccess("删除成功");
    }

    /**
     * 验证重复gis资源类型
     *
     * @param name 查询名称
     * @return 成功信息  存在则为 false,不存在则为true,
     *
     */
    @GetMapping("/checkGisMapType")
    @ResponseBody
    public String checkGisMapType(String name) {
       String checkGisName = "";
        try {
            Integer isCheck = gisMapTypeManageService.checkGisMapType(name);
            if(isCheck > 0){
                checkGisName = "false";
            }else {
                checkGisName = "true";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return checkGisName;
    }

}
