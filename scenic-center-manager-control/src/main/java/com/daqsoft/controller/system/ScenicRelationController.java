package com.daqsoft.controller.system;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.appPermission.AppRole;
import com.daqsoft.entity.scenicEntity.SysUserScenic;
import com.daqsoft.form.PageForm;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.CommonService;
import com.daqsoft.service.appPermission.AppRoleService;
import com.daqsoft.service.scenicRelationService.ScenicRelationService;
import com.daqsoft.vo.SysDictVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping(value = "/scenicRelation")
public class ScenicRelationController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScenicRelationController.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static final String PATH = "scenicRelation/";
    private static String k = "user:vcode:";
    @Autowired
    private ScenicRelationService scenicRelationService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private CommonService commonService;
    @Autowired
    private AppRoleService appRoleService;

    /**
     * 返回账号信息页面,页面通过ajax再请求数据
     *
     * @return 模板页面
     */
    @GetMapping(Constants.LISTACTION)
    public String list(HttpServletRequest request) {
        return PATH + "scenic_relation_list";
    }

    /**
     * 用户列表
     *
     * @param account 账号
     * @return 列表页面
     */
    @GetMapping(value = Constants.LISTACTION, headers = HEADER_X_REQUESTED_WITH)
    @ResponseBody
    public BaseResponse list(PageForm pageForm, String account) {
        return pageBuilderSuccess(scenicRelationService.list(pageForm, account));
    }

    /**
     * 编辑
     *
     * @param id    数据id
     * @param model 数据对象
     * @return 新增/编辑页面
     */
    @GetMapping(Constants.EDITACTION)
    public String edit(Long id, ModelMap model) {

        List<SysDictVo> appUserType = commonService.getDictByType("appUserType");
        List<AppRole> appRoles = appRoleService.findAllValidData();
        SysUserScenic dto;
        if (id != null) {
            dto = scenicRelationService.get(id);
        } else {
            dto = new SysUserScenic();
        }

        model.addAttribute("dto", dto);
        model.addAttribute("appRoles", appRoles);
        model.addAttribute("appUserType", appUserType);
        return PATH + "scenic_relation_input";
    }


    /**
     * 保存,并且新增账号+vcode到redis
     *
     * @param dto 景区用户实体
     * @return 成功信息
     */
    @RequestMapping(value = Constants.SAVEACTION, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse save(@Valid @ModelAttribute(value = "dto") SysUserScenic dto, Principal principal) {
        try {
            //当前登录用户
            if (dto.getId() == null) {
                return builderSuccess("用户不存在！");
            } else {
                String vcode = "";
                String account = "";
                //1.判断是否有vcode
                if (dto.getVcode() == null || dto.getVcode() == "") {
                    //如果本身vcode为空
                    //1.根据此用户id查询上级是否有vcode
                    //上级创建者
                    long creater = dto.getCreater();
                    account = dto.getAccount();
                    //查询上级创建者vcode
                    String createrVcode = scenicRelationService.getCreaterVcode(creater);
                    //查询密码和基础信息生成vcode
                    SysUserScenic before = scenicRelationService.get(dto.getId());
                    if (StringUtils.isEmpty(createrVcode)) {
                        //上级没有vcode的情况则生成vcode
                        vcode = DigestUtils.md5Hex(before.getAccount() + before.getPassword());/*生成规则是账号+密码*/
                        dto.setVcode(vcode);
                        //将账号和景区编码放在redis中，目的在获取景区唯一标示
                        //1.先查询是否有
                        Object isTure = stringRedisTemplate.opsForHash().get(k, before.getAccount());
                        //2.如果有就更新，没有就新增
                        if (isTure != null) {
                            stringRedisTemplate.opsForHash().put(k, before.getAccount(), vcode);
                        } else {
                            stringRedisTemplate.opsForHash().put(k, before.getAccount(), vcode);
                        }
                    } else {
                        //2.上级如果有vcode,则返回上级的vcode
                        dto.setVcode(createrVcode);
                        //1.先查询是否有
                        Object isTure = stringRedisTemplate.opsForHash().get(k, before.getAccount());
                        //2.如果有就更新，没有就新增
                        if (isTure != null) {
                            stringRedisTemplate.opsForHash().put(k, before.getAccount(), createrVcode);
                        } else {
                            stringRedisTemplate.opsForHash().put(k, before.getAccount(), createrVcode);
                        }
                    }
                }
                scenicRelationService.update(dto);
            }

        } catch (Exception e) {
            LOGGER.error("保存失败:", e);
            return builderFailed("保存失败");
        }
        return builderSuccess(Constants.SAVESUCCESSOPERATION);
    }
}
