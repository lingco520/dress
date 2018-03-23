/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.controller;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.constants.ScrsConstants;
import com.daqsoft.service.CommonService;
import com.daqsoft.vo.ScenicGisConfigVo;
import com.daqsoft.vo.ScenicSpotsVo;
import com.daqsoft.vo.SysDictVo;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
/**
 * @Title: CommonController
 * @Author: tanggm
 * @Date: 2018/01/12 18:15
 * @Description: TODO 文件上传公共类
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */

/**
 * 请求地址中 /permitted/common 中的 permitted 因为文件上传框架对其做了拦截，需要加上 permitted
 */
@Controller
@RequestMapping("/permitted/common")
public class CommonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);
    @Autowired
    CommonService commonService;

    /**
     * 2.0文件服务富文本上传
     *
     * @param file
     */
    @RequestMapping(value = "/ossUploadKindFile")
    @ResponseBody
    public Object ossUploadKindFile(@RequestParam(value = "Filedata") MultipartFile file) {

        JSONObject obj = commonService.ossUploadKindFile(file);

        return obj;
    }

    /**
     * 查询字典列表(两个参数方法)
     * @param type1
     * @param type2
     * @return
     */
    @RequestMapping(value = "/getDictListByTwo", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse getDictListByTwo(String type1, String type2) {
        try {
            List<SysDictVo> dictList = commonService.getDictListByTwo(type1, type2);
            return ResponseBuilder.custom().success().data(dictList).build();
        } catch (Exception e){
            LOGGER.error("查询错误:", e);
            return ResponseBuilder.custom().failed("查询错误", 1).build();
        }
    }
    /**
     * 查询字典列表(一个参数方法)
     * @param type
     * @return
     */
    @RequestMapping(value = "/getDictListByOne", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse getDictListByOne(String type) {
        try {
            List<SysDictVo> dictList = commonService.getDictListByOne(type);
            return ResponseBuilder.custom().success().data(dictList).build();
        } catch (Exception e){
            LOGGER.error("查询错误:", e);
            return ResponseBuilder.custom().failed("查询错误", 1).build();
        }
    }
}
