package com.daqsoft.clientUtils;

import com.alibaba.fastjson.JSONObject;
import com.daqsoft.utils.DesUtils;
import com.daqsoft.utils.HttpUtils;
import com.daqsoft.entity.clientEntity.UrlConfig;
import com.daqsoft.entity.clientEntity.UserConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lyl
 * @version V1.0.0
 * @description 注册用户方法
 * @date 2018-02-06
 */
public class ClientSaveUser {
    private static final String REGISTER_INTERFACE = "http://esb.daqsoft.com/saveUserEncrypt";
    private static final String CHANGE_INTERFACE = "http://esb.daqsoft.com/changePassByCode";
    private static final String GETVCODE_INTERFACE = "http://esb.daqsoft.com/product/getCode";
    private static final String REGISTER_SCENIC_INTERFACE = "http://esb.daqsoft.com/addProduct";

//    /**
//     * 注册用户
//     *
//     * @param map
//     * @return
//     */
//    public Map<String, String> register(Map map) {
//
//        String url = REGISTER_INTERFACE;
//        String s1 = HttpUtils.postForm(url, map);
//        JSONObject parse = (JSONObject) JSONObject.parse(s1);
//
//        Map<String, String> maps = new HashMap<>();
//        String status = parse.get("status").toString();
//        if (!"200".equals(status)) {
//            maps.put("status", "-1");
//        } else {
//            String uCode = parse.get("vcode").toString();
//            //用户标识
//            maps.put("uCode", uCode);
//            maps.put("status", "1");
//        }
//        return maps;
//    }
//
//    /**
//     * 修改密码
//     *
//     * @param map
//     * @return
//     */
//    public Map<String, String> updatePwd(Map map) {
//
//        String url = CHANGE_INTERFACE;
//        String s1 = HttpUtils.postForm(url, map);
//        JSONObject parse = (JSONObject) JSONObject.parse(s1);
//
//        Map<String, String> maps = new HashMap<>();
//        String status = parse.get("status").toString();
//        if (!"200".equals(status)) {
//            maps.put("status", "-1");
//        } else {
//            maps.put("status", "1");
//        }
//        return maps;
//    }

    /**
     * 获取景区VCODE
     *
     * @param pCode
     * @return
     */
    public Map getVcode(String pCode) throws Exception {

        String httpUrl = GETVCODE_INTERFACE;
        Map map = new HashMap<>();
        map.put("code", pCode);

        String str = HttpUtils.postForm(httpUrl, map);

        JSONObject parse = (JSONObject) JSONObject.parse(str);
        String vcode = parse.getJSONObject("data").get("productcode").toString();
        String scenicName = parse.getJSONObject("data").get("name").toString();

        Map maps = new HashMap<>();
        maps.put("vcode", vcode);
        maps.put("scenicName", scenicName);
        return maps;
    }


    /**
     * 登录
     *
     * @param userConfig
     * @param urlConfig
     * @return
     */
    public Map<String, String> login(UserConfig userConfig, UrlConfig urlConfig) {
        Map<String, String> logMap = new HashMap<>();
        String phone = userConfig.getPhone();
        String username = userConfig.getUsername();
        String password = userConfig.getPassword();
        String s = phone + "," + username + "," + password;
        String s2 = "";
        try {
            s2 = DesUtils.encryptTwiceStr(s);

        } catch (Exception e) {
            e.printStackTrace();
        }
        logMap.put("data", s2);

        String s1 = HttpUtils.postForm(urlConfig.getLoginUrl(), logMap);
        JSONObject parse = (JSONObject) JSONObject.parse(s1);

        Map<String, String> map = new HashMap<>();

        String status = parse.get("status").toString();
        if (!"200".equals(status)) {
            map.put("status", "-1");
        } else {
            String productcode = parse.get("productcode").toString();
            //用户标识
            String key = parse.get("vcode").toString();
            map.put("productcode", productcode);
            map.put("key", key);
            map.put("status", "1");
        }

        return map;

    }

//    /**
//     * 注册景区
//     *
//     * @param map
//     * @return
//     */
//    public Map<String, String> registerScenic(Map map) {
//
//        String url = REGISTER_SCENIC_INTERFACE;
//        String s1 = HttpUtils.postForm(url, map);
//        JSONObject parse = (JSONObject) JSONObject.parse(s1);
//
//        Map<String, String> maps = new HashMap<>();
//        String status = parse.get("status").toString();
//        if (!"1".equals(status)) {
//            maps.put("status", "-1");
//        } else {
//            String vcode = parse.get("vcode").toString();
//            maps.put("pCode", vcode);
//            maps.put("status", "1");
//        }
//        return maps;
//    }

}
