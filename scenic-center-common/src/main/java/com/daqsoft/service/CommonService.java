/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service;

import com.daqsoft.file.entity.ResultEntity;
import com.daqsoft.framework.entity.SysRegion;
import com.daqsoft.mapper.common.DictByTypeMapper;
import com.daqsoft.mapper.common.GisConfigMapper;
import com.daqsoft.utils.FileUploadUtil;
import com.daqsoft.utils.KindImgUpload;
import com.daqsoft.vo.MediaTypeVo;
import com.daqsoft.vo.ScenicGisConfigVo;
import com.daqsoft.vo.ScenicSpotsVo;
import com.daqsoft.vo.SysDictVo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Title: CommonService
 * @Author: tanggm
 * @Date: 2018/01/11 19:31
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
public class CommonService {
    @Autowired
    private DictByTypeMapper dictByTypeMapper;
    @Autowired
    private GisConfigMapper gisConfigMapper;

    /**
     * 富文本框图片上传
     *
     * @param file
     * @return
     */
    public JSONObject ossUploadKindFile(MultipartFile file, String dir) {
        String message;  //返回信息
        String fileUrl = ""; //文件地址
        int error = 0; //状态
        KindImgUpload upload = new KindImgUpload();
        if (upload.isEmptyFile(file)) {
            error = 1;
            message = "请选择文件";
        } else if (!upload.fileTypeAllow(file, dir)) {
            error = 1;
            message = "不允许的文件类型,请重新选择";
        } else if (!upload.isOverMax(file)) {
            error = 1;
            message = "文件最多不能超过10MB,请重新选择";
        } else {
            String origName = file.getOriginalFilename();
            int index = origName.lastIndexOf(".");
            String ext = origName.substring(index + 1, origName.length());
            String uuid = String.valueOf(UUID.randomUUID());
            String fileName = uuid + "." + ext;
            File destFile = null;
            String path = "scrs/imgs";
            try {
                InputStream in = file.getInputStream();
                ResultEntity resultEntity = FileUploadUtil.uploadForInput(path, fileName, in);
                if (resultEntity.getState() == 0) {
                    Map datas = (Map) resultEntity.getDatas();
                    fileUrl = datas.get("url").toString();
                    message = resultEntity.getMessage();
                } else {
                    error = 1;
                    message = "上传失败";
                }
            } catch (Exception e) {
                e.printStackTrace();
                error = 1;
                message = "上传失败";
                if (destFile != null && destFile.exists()) {
                    destFile.delete();
                }
            }
        }

        JSONObject obj = new JSONObject();
        obj.put("error", error);
        obj.put("url", fileUrl);
        obj.put("message", message);

        return obj;
    }

    /**
     * 富文本框图片上传
     *
     * @param file 文件
     * @return
     */
    public JSONObject ossUploadKindFile(MultipartFile file) {
        String message;  //返回信息
        String fileUrl = ""; //文件地址
        int error = 0; //状态
        KindImgUpload upload = new KindImgUpload();
        if (upload.isEmptyFile(file)) {
            error = 1;
            message = "请选择文件";
        } else if (!upload.isImg(file)) {
            error = 1;
            message = "不允许的文件类型";
        } else if (!upload.isOverMax(file)) {
            error = 1;
            message = "文件过大";
        } else {
            String origName = file.getOriginalFilename();
            int index = origName.lastIndexOf(".");
            String ext = origName.substring(index + 1, origName.length());
            String uuid = String.valueOf(UUID.randomUUID());
            String fileName = uuid + "." + ext;

            //暂定此位置，需放入统一配置文件
            String path = "viscenter/imgs";
            try {
                InputStream in = file.getInputStream();
                ResultEntity resultEntity = FileUploadUtil.uploadForInput(path, fileName, in);
                if (resultEntity.getState() == 0) {
                    Map datas = (Map) resultEntity.getDatas();
                    fileUrl = datas.get("url").toString();
                    message = resultEntity.getMessage();
                } else {
                    error = 1;
                    message = "上传失败";
                }
            } catch (Exception e) {
                e.printStackTrace();
                error = 1;
                message = "上传失败";
            }
        }

        JSONObject obj = new JSONObject();
        obj.put("error", error);
        obj.put("url", fileUrl);
        obj.put("message", message);

        return obj;
    }

    /**
     * 查询字典
     *
     * @return
     */
    public List<SysDictVo> getDictByType(String type) {
        List<SysDictVo> list = dictByTypeMapper.getDictByType(type);
        return list;
    }

    /**
     * 查询所属账号
     *
     * @return
     */
    public List<SysDictVo> getAccount(String type) {
        List<SysDictVo> list = dictByTypeMapper.getAccount(type);
        return list;
    }
    /**
     * 查询景区部门
     *
     * @return
     */
    public List<SysDictVo> getDepartment(String vcode) {
        List<SysDictVo> list = dictByTypeMapper.getDepartment(vcode);
        return list;
    }

    /**
     * 查询账号下所属景点
     *
     * @return
     */
    public List<ScenicSpotsVo> getScenicByVcode(String vcode) {
        List<ScenicSpotsVo> list = dictByTypeMapper.getScenicByVcode(vcode);
        return list;
    }

    /**
     * 查询视频类型
     *
     * @return
     */
    public List<MediaTypeVo> getMediaType(String vcode, String type) {
        HashMap map = new HashMap();
        map.put("vcode", vcode);
        map.put("type", type);
        List<MediaTypeVo> list = dictByTypeMapper.getMediaType(map);
        return list;
    }

    /**
     * 查询两个参数类型的集合
     * @param type1
     * @param type2
     * @return
     */
    public List<SysDictVo> getDictListByTwo(String type1, String type2) {
        List<SysDictVo> list = dictByTypeMapper.getDictListByTwo(type1, type2);
        return list;
    }

    /**
     * 通过region查询地区region对象数据
     * @param region 地区region
     * @return region的SysRegion数据
     */
    public SysRegion getSysRegionByRegion(String region){
        return dictByTypeMapper.getSysRegionByRegion(region);
    }

    /**
     * 查询一个参数类型的集合
     * @param type
     * @return
     */
    public List<SysDictVo> getDictListByOne(String type) {
        return dictByTypeMapper.getDictListByOne(type);
    }

    /**
     * 查询gis地图配置
     * @param vcode
     * @return
     */
    public ScenicGisConfigVo getGisConfig(String vcode) {
        return gisConfigMapper.getGisConfig(vcode);
    }
}
