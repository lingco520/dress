package com.daqsoft.controller.video;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.constants.DictConstants;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.mediaType.ResMediaType;
import com.daqsoft.entity.video.MediaVideo;
import com.daqsoft.form.PageForm;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.CommonService;
import com.daqsoft.service.video.VideoService;
import com.daqsoft.vo.MediaTypeVo;
import com.daqsoft.vo.ScenicSpotsVo;
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
 * @Date: Created in 9:47 2018/1/18.
 * @Version: 4.0.0
 * @describe: 视频（增删改查）
 */
@Controller
@RequestMapping(value = "/video")
public class VideoController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoController.class);
    private static final String PATH = "video/";
    @Autowired
    private CommonService commonService;
    @Autowired
    private VideoService videoService;


    /**
     * 列表页面
     *
     * @return 模板页面
     */
    @GetMapping(Constants.LISTACTION)
    public String list() {
        return PATH + "video_list";
    }

    /**
     * 列表数据
     *
     * @param pageForm
     * @param name     名称
     * @param status   状态
     * @param request
     * @return
     */
    @GetMapping(value = Constants.LISTACTION, headers = HEADER_X_REQUESTED_WITH)
    @ResponseBody
    public BaseResponse list(PageForm pageForm, String name, String status, HttpServletRequest request) {
        try {
            return pageBuilderSuccess(videoService.list(pageForm, name, getVcode(request), status));
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
        MediaVideo dto = new MediaVideo();

        try {
            if (id != null) {
                dto = videoService.get(id);
            }
        } catch (Exception e) {
            LOGGER.error("新增/编辑失败:", e);
        }
        //查询景区下的景点
        List<ScenicSpotsVo> scenic = commonService.getScenicByVcode(getVcode(request));
        //查询视频的类型
        List<MediaTypeVo> mediaType = commonService.getMediaType(getVcode(request), DictConstants.VIDEO);
        //查询语言类型
        List<SysDictVo> languageType = commonService.getDictByType(DictConstants.LANGUAGE_TYPE);
        model.addAttribute("dto", dto);
        model.addAttribute("scenic", scenic);
        model.addAttribute("mediaType", mediaType);
        model.addAttribute("languageType", languageType);
        return PATH + "video_input";
    }


    /**
     * 保存
     *
     * @param dto
     * @return 成功信息
     */
    @RequestMapping(value = Constants.SAVEACTION, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse save(@Valid @ModelAttribute(value = "dto") MediaVideo dto, HttpServletRequest request) {
        dto.setVcode(getVcode(request));//设置vcode
        try {
            //当前登录用户
            if (dto.getId() == null) {
                videoService.save(dto);
            } else {
                videoService.update(dto);
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
            videoService.delete(ids);
        } catch (Exception e) {
            LOGGER.error("删除失败", e);
            return builderFailed("删除失败!");
        }
        return builderSuccess("删除成功");
    }
}
