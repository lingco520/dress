package com.daqsoft.controller.test;

import com.alibaba.fastjson.JSONObject;
import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.test.BusTest;
import com.daqsoft.form.PageForm;
import com.daqsoft.framework.entity.SysUser;
import com.daqsoft.framework.service.SysUserServiceImpl;
import com.daqsoft.framework.service.exceptions.SubSetNotNullException;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.test.BusTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/busTest")
public class BusTestController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusTestController.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static final String PATH = "busTest/";
    @Autowired
    private BusTestService busTestService;
    @Autowired
    private SysUserServiceImpl sysUserService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 返回景区门票的页面,页面通过ajax再请求数据
     *
     * @return 模板页面
     */
    @GetMapping(Constants.LISTACTION)
    public String list(HttpServletRequest request) {
        //客车等级
        List<Map<String, Object>> carLevels = busTestService.getDictsByType("carLevel");
        //客车类型
        List<Map<String, Object>> carTypes = busTestService.getDictsByType("carType");
        String level = JSONObject.toJSONString(carLevels);
        String type = JSONObject.toJSONString(carTypes);
        request.setAttribute("carLevels", carLevels);
        request.setAttribute("carTypes", carTypes);
        request.setAttribute("level", level);
        request.setAttribute("type", type);
        return PATH + "bus_test_list";
    }
    /**
     * 大巴列表
     *
     * @param name      所属企业
     * @param level     客车级别
     * @param type      客车类型
     * @param peopleNum 准载人数
     * @return 列表页面
     */
    @GetMapping(value = Constants.LISTACTION, headers = HEADER_X_REQUESTED_WITH)
    @ResponseBody
    public BaseResponse list(PageForm pageForm, String name, String level, String type, String peopleNum) {
        return pageBuilderSuccess(busTestService.list(pageForm, name, level, type, peopleNum));
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
        //客车等级
        List<Map<String, Object>> carLevels = busTestService.getDictsByType("carLevel");
        //客车类型
        List<Map<String, Object>> carTypes = busTestService.getDictsByType("carType");
        BusTest dto;
        if (id != null) {
            dto = busTestService.get(id);
        } else {
            dto = new BusTest();
        }
        model.addAttribute("dto", dto);
        model.addAttribute("carLevels", carLevels);
        model.addAttribute("carTypes", carTypes);
        return PATH + "busTest_input";
    }


    /**
     * 保存
     *
     * @param dto 大巴实体
     * @return 成功信息
     */
    @RequestMapping(value = Constants.SAVEACTION, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse save(@Valid @ModelAttribute(value = "dto") BusTest dto, Principal principal) {
        try {
            //当前登录用户
            SysUser user = sysUserService.getByAccount(this.checkPrincipal(principal));
            dto.setBtime(sdf.format(new Date()));
            if (dto.getId() == null) {
                dto.setRegion(String.valueOf(user.getRegion()));
                busTestService.save(dto);
            } else {
                busTestService.update(dto);
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
            busTestService.delete(ids);
        } catch (SubSetNotNullException e) {
            LOGGER.error("删除失败", e);
            return builderFailed(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("删除失败", e);
            return builderFailed("删除失败!");
        }
        return builderSuccess("删除成功");
    }
    @GetMapping("/testRedis")
    public void testRedis(){
        String k = "user:vcode:";
        String hk = "admin";
        String value = "d5034caae86f1081e0e6ae5337e48e9f";
        stringRedisTemplate.opsForHash().put(k,hk,value);
    }
}
