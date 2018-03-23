/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.controller.audio;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.constants.DictConstants;
import com.daqsoft.constants.ScrsConstants;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.audio.MediaAudio;
import com.daqsoft.form.PageForm;
import com.daqsoft.framework.util.Constants;
import com.daqsoft.service.CommonService;
import com.daqsoft.service.audio.AudioService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Title: AudioController
 * @Author: tanggm
 * @Date: 2018/01/25 14:36
 * @Description: TODO 多媒体-音频
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Controller
@RequestMapping(value = "/audio")
public class AudioController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AudioController.class);
    private static final String PATH = "audio/";
    @Autowired
    private AudioService audioService;
    @Autowired
    private CommonService commonService;
    /**
     * 列表页面
     *
     * @return 模板页面
     */
    @GetMapping(Constants.LISTACTION)
    public String list(ModelMap model, HttpServletRequest request) {
        try {
            //查询景区下的景点
            List<ScenicSpotsVo> scenicList = commonService.getScenicByVcode(getVcode(request));
            //查询音频的类型
            List<MediaTypeVo> audioType = commonService.getMediaType(getVcode(request), DictConstants.AUDIO);
            //查询语言类型
            List<SysDictVo> languageType = commonService.getDictByType(DictConstants.LANGUAGE_TYPE);
            model.addAttribute("scenicList", scenicList);
            model.addAttribute("audioType", audioType);
            model.addAttribute("languageType", languageType);
            return PATH + "audio_list";
        } catch (Exception e){
            LOGGER.error("查询错误:", e);
            return ScrsConstants.ERROR_PAGE;
        }
    }

    /**
     * 列表数据
     * @param pageForm
     * @param title
     * @param scenicId
     * @param audioType
     * @param languageType
     * @param request
     * @return
     */
    @GetMapping(value = Constants.LISTACTION, headers = HEADER_X_REQUESTED_WITH)
    @ResponseBody
    public BaseResponse list(PageForm pageForm, String title, String scenicId, String audioType, String languageType, Integer status, HttpServletRequest request) {
        try {
            pageForm.setVcode(getVcode(request));
            return pageBuilderSuccess(audioService.list(pageForm, title, scenicId, audioType, languageType, status));
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
     * @param request 请求对象
     * @return 新增/编辑页面
     */
    @GetMapping(Constants.EDITACTION)
    public String edit(Long id, ModelMap model, HttpServletRequest request) {
        MediaAudio dto = new MediaAudio();
        try {
            if (id != null) {
                dto = audioService.get(id);
            }
        } catch (Exception e){
            LOGGER.error("新增/编辑失败:", e);
        }
        //查询景区下的景点
        List<ScenicSpotsVo> scenicList = commonService.getScenicByVcode(getVcode(request));
        //查询图片的类型
        List<MediaTypeVo> audioType = commonService.getMediaType(getVcode(request), DictConstants.AUDIO);
        //查询语言类型
        List<SysDictVo> languageType = commonService.getDictByType(DictConstants.LANGUAGE_TYPE);
        model.addAttribute("scenicList", scenicList);
        model.addAttribute("audioType", audioType);
        model.addAttribute("languageType", languageType);
        model.addAttribute("dto", dto);
        return PATH + "audio_input";
    }


    /**
     * 保存
     *
     * @param dto
     * @return 成功信息
     */
    @RequestMapping(value = Constants.SAVEACTION, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse save(@Valid @ModelAttribute(value = "dto") MediaAudio dto, HttpServletRequest request) {
        try {
            dto.setVcode(getVcode(request));
            dto.setUpdateTime(new Date());
            //当前登录用户
            if (dto.getId() == null) {
                audioService.save(dto);
            } else {
                audioService.update(dto);
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
            audioService.delete(ids);
        } catch (Exception e) {
            LOGGER.error("删除失败", e);
            return builderFailed("删除失败!");
        }
        return builderSuccess("删除成功");
    }
    /**
     * 查询图片的类型
     * @param request
     * @return
     */
    @RequestMapping(value = "/getAudioTypeList", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse getAudioTypeList(HttpServletRequest request) {
        try {
            //查询音频的类型
            List<MediaTypeVo> audioType = commonService.getMediaType(getVcode(request), DictConstants.AUDIO);
            List<SysDictVo> dictVoList = new ArrayList<>();
            if(audioType != null && audioType.size() > 0){
                for(MediaTypeVo mediaTypeVo : audioType){
                    SysDictVo sysDictVo = new SysDictVo();
                    sysDictVo.setName(mediaTypeVo.getName());
                    sysDictVo.setValue(mediaTypeVo.getId());
                    dictVoList.add(sysDictVo);
                }
            }
            return ResponseBuilder.custom().success().data(dictVoList).build();
        } catch (Exception e){
            LOGGER.error("查询错误:", e);
            return ResponseBuilder.custom().failed("查询错误", 1).build();
        }
    }
}
