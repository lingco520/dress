package com.daqsoft.clientUtils;

import com.daqsoft.clientUtils.sceenConfig.SceenConfig;
import com.daqsoft.utils.HttpUtils;
import com.daqsoft.entity.clientEntity.DBConfig;
import com.daqsoft.entity.clientEntity.UrlConfig;
import com.daqsoft.entity.clientEntity.UserConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lyl
 * @version V1.0.0
 * @description 推送客户端配置
 * @date 2018-02-06
 */
public class DeskTopConfig {
    /**
     * 推送客户端配置
     *
     * @param userConfig
     * @param dbConfig
     * @param urlConfig
     */
    public void pushConfig(UserConfig userConfig, DBConfig dbConfig, UrlConfig urlConfig, int monitor) {
        //登录用户中心
        Map<String, String> loginMap = new ClientSaveUser().login(userConfig, urlConfig);

        String status = loginMap.get("status").toString();

        //判断用户名是否正确
        if (!"1".equals(status)) {
            throw new RuntimeException("用户名或密码错误");
        }
        //获取客户端参数配置，并生成xml
        SceenConfig sceenConfig = new SceenConfig();
        Map<String, String> productcodeMap = sceenConfig.getSceenconfig(dbConfig, loginMap.get("productcode"), monitor);

        //分类封装数据
        String apps = productcodeMap.get("apps").toString();
        String deqec = productcodeMap.get("deqec").toString();
        String scenics = productcodeMap.get("scenics").toString();
        String slideVideos = productcodeMap.get("slideVideos").toString();

        Map<String, String> map = new HashMap<>();

        map.put("topUrl", apps);
        map.put("widgtsConfDetail", deqec);
        map.put("monitors", scenics);
        map.put("slideVideos", slideVideos);
        map.put("key", loginMap.get("key"));

        //推送数据
        HttpUtils.postForm(urlConfig.getPushUrl(), map);

    }
}
