package com.daqsoft.utils;


import com.daqsoft.constants.ScrsConstants;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文件判断辅助类
 */
public class KindImgUpload {

    /**
     * 判断文件是否为空
     * @param file
     * @return
     */
    public boolean isEmptyFile(MultipartFile file){

        return file == null;
    }

    /**
     * 判断文件是否过大（具体大小需商榷后放入配置）
     * @param file
     * @return
     */
    public boolean isOverMax(MultipartFile file){
        return file.getSize() <= 10485760;

    }

    /**
     * 判断上传的文件是否为图片类型
     * @param file
     * @return
     */
    public boolean isImg(MultipartFile file){
        //定义一个集合，判断上传的文件类型是否为图片
        List<String> allow = new ArrayList<>();
        allow.add("jpg");
        allow.add("jpeg");
        allow.add("png");
        allow.add("gif");
        String origName = file.getOriginalFilename();
        int index = origName.lastIndexOf(".");
        String ext = origName.substring(index + 1, origName.length());
        return allow.contains(ext);

    }
    /**
     * 根据上传的类型判断允许的文件类型
     *
     * @param file
     * @param dir
     * @return
     */
    public boolean fileTypeAllow(MultipartFile file, String dir) {
        String[] allow = new String[]{};
        if (ScrsConstants.KIND_IMAGE.equalsIgnoreCase(dir)) {
            allow = new String[]{"jpg", "jpeg", "png", "gif"};
        } else if (ScrsConstants.KIND_FLASH.equalsIgnoreCase(dir)) {
            allow = new String[]{"mp4", "mp3", "avi", "rmvb"};
        } else if (ScrsConstants.KIND_FILE.equalsIgnoreCase(dir)) {
            allow = new String[]{"text", "pdf", "xlsx", "xls", "doc", "docx", "log"};
        }
        List allowList = Arrays.asList(allow);
        String origName = file.getOriginalFilename();
        int index = origName.lastIndexOf(".");
        String ext = origName.substring(index + 1, origName.length()).toLowerCase();
        return allowList.contains(ext);
    }
}
