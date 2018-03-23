package com.daqsoft.controller.gisModuleConfig;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.gisMapTypeManage.ResGisMapTypeManage;
import com.daqsoft.entity.gisModuleConfig.ResGisModuleConfig;
import com.daqsoft.entity.gisModuleManage.ResGisModuleManage;
import com.daqsoft.form.PageForm;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.gisModuleConfig.GisModuleConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: gis资源模块配置
 * @Author: superziy .
 * @Date: Created in 11:00 2018/2/6
 * @Version: 4.0.0
 * @describe: gis资源模块配置
 * @since:JDK 1.8
 */
@Controller
@RequestMapping(value = "/gisModuleConfig")
public class GisModuleConfigController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GisModuleConfigController.class);
    private static final String PATH = "gisModuleConfig/";
    @Autowired
    private GisModuleConfigService gisModuleConfigService;


    /**
     * 列表页面
     *
     * @return 模板页面
     */
    @GetMapping(Constants.LISTACTION)
    public String list() {
        return PATH + "gisModuleConfig_list";
    }

    /**
     * 列表数据
     *
     * @param name   名称
     * @param status 状态
     * @return 列表页面
     */
    @GetMapping(value = Constants.LISTACTION, headers = HEADER_X_REQUESTED_WITH)
    @ResponseBody
    public BaseResponse list(PageForm pageForm, String name, String status, HttpServletRequest request) {
        try {
            return pageBuilderSuccess(gisModuleConfigService.list(pageForm, name, getVcode(request), status));
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
        ResGisModuleConfig dto = new ResGisModuleConfig();
        String endPath = "";
        try {
            if (id != null) {
                dto = gisModuleConfigService.get(id);
                endPath = "gisModuleConfig_edit";
            } else {
                dto = new ResGisModuleConfig();
                endPath = "gisModuleConfig_input";
            }
        } catch (Exception e) {
            LOGGER.error("新增/编辑失败:", e);
        }
        //gis模块
        List<ResGisModuleManage> gisModule = gisModuleConfigService.getGisModule();
        //gis地图类型
        List<ResGisMapTypeManage> gisMapType = gisModuleConfigService.getGisMapType();
        model.addAttribute("dto", dto);
        model.addAttribute("gisModule", gisModule);
        model.addAttribute("gisMapType", gisMapType);
        return PATH + endPath;
    }


    /**
     * 保存
     *
     * @param dto
     * @return 成功信息
     */
    @RequestMapping(value = Constants.SAVEACTION, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse save(@Valid @ModelAttribute(value = "dto") ResGisModuleConfig dto, HttpServletRequest request) {
        try {
            //当前登录用户
            if (dto.getId() == null) {
//                //删除所有关于景区的gis模块
//                gisModuleConfigService.removeGisMoudle(getVcode(request));
                //循环添加gis的模块
                String[] moudle = dto.getMoudleId().split(",");
                Map map = new HashMap();
                for (int i = 0; i < moudle.length; i++) {
                    String moudleId = moudle[i];
                    map.put("moudleId", moudleId);
                    map.put("vcode", getVcode(request));
                    map.put("mapTypeId", dto.getMapTypeId());
                    //如果选择id不等于已有模块id，则新增该模块
                    Integer isTrue = gisModuleConfigService.getIsTrue(map);
                    if (isTrue < 1) {
                        //查询该模块的名称
                        ResGisModuleManage gisName = gisModuleConfigService.getGisName(moudleId);
                        dto.setName(gisName.getName());
                        dto.setMoudleId(String.valueOf(gisName.getId()));
                        dto.setSort(i + 1);
                        dto.setVcode(getVcode(request));
                        gisModuleConfigService.save(dto);
                    } else {
                        continue;
                    }
                }
            } else {
                //编辑排序顺序
                gisModuleConfigService.updateSort(dto);
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
            gisModuleConfigService.delete(ids);
        } catch (Exception e) {
            LOGGER.error("删除失败", e);
            return builderFailed("删除失败!");
        }
        return builderSuccess("删除成功");
    }

    /**
     * 验证重复gis资源模块
     *
     * @param name 查询名称
     * @return 成功信息  存在则为 false,不存在则为true,
     */
    @GetMapping("/checkGisName")
    @ResponseBody
    public String checkGisName(String name) {
        String checkGisName = "";
        try {
            Integer isCheck = gisModuleConfigService.isCheckGisName(name);
            if (isCheck > 0) {
                checkGisName = "false";
            } else {
                checkGisName = "true";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return checkGisName;
    }

}
