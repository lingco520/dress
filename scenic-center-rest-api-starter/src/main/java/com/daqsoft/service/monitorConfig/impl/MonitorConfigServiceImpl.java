package com.daqsoft.service.monitorConfig.impl;

import com.daqsoft.mapper.monitorConfig.MonitorConfigMapper;
import com.daqsoft.pinyin.HanYuPinYin;
import com.daqsoft.service.monitorConfig.MonitorConfigService;
import com.daqsoft.utils.client.monitorConfig.CreateConfString;
import com.daqsoft.vo.client.monitorConfig.City;
import com.daqsoft.vo.client.monitorConfig.Item;
import com.daqsoft.vo.client.monitorConfig.Scenic;
import com.daqsoft.vo.client.monitorConfig.Scenics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Title: 客户端监控配置供外部使用接口
 * @Author: lyl
 * @Date: 2018/03/21 17:37
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
public class MonitorConfigServiceImpl implements MonitorConfigService{

    @Autowired
    private MonitorConfigMapper monitorConfigMapper;

    @Override
    public String getMonitorConfigXML(String vcode, int div) {

        List<Scenic> scenicList = new LinkedList<>();
        //分组数量
        int groupNum = monitorConfigMapper.getMonitorGroupNum(vcode);
        Map param = new HashMap();
        param.put("vcode",vcode);
        //分组名称，区分多组与单组
        if(groupNum > 0) {
            List<Map> monitorGroupName = monitorConfigMapper.getMonitorGroupName(vcode);
            for (Map groupName : monitorGroupName) {
                String gn = groupName.get("groupName") + "";
                List<Item> itemList = new LinkedList<>();
                List<Map> infoList = new LinkedList<>();
                param.put("groupName", gn);
                //区分外网地址还是内网地址
                if (div == 1) {
                    infoList = monitorConfigMapper.getMonitorOutInfos(param);
                } else {
                    infoList = monitorConfigMapper.getMonitorInfos(param);
                }
                //监控信息
                itemList = itemlistData(infoList);

                //声明Scenic对象，监控信息放入实体
                Scenic scenic = new Scenic();
                scenic = sceniclistData(itemList,vcode,param,1);

                scenicList.add(scenic);
            }
        }else{
            List<Item> itemList = new LinkedList<>();
            List<Map> infoList = new LinkedList<>();
            param.put("groupName", "");
            //区分外网地址还是内网地址
            if (div == 1) {
                infoList = monitorConfigMapper.getMonitorOutInfos(param);
            } else {
                infoList = monitorConfigMapper.getMonitorInfos(param);
            }
            //监控信息
            itemList = itemlistData(infoList);

            //声明Scenic对象，监控信息放入实体
            Scenic scenic = new Scenic();
            scenic = sceniclistData(itemList,vcode,param,2);

            scenicList.add(scenic);
        }


        //获取地区及名称并生成区域实体
        List<City> cityList = new LinkedList<>();
        List<Map> regionList = monitorConfigMapper.getRegion(vcode);
        for(Map regions : regionList) {
            String region = regions.get("region") + "";
            String name = regions.get("name") + "";

            City city = new City();
            city.setName(name);
            city.setRegion(region);
            city.setScenicCount(1 + "");
            city.setScenic(scenicList);
            cityList.add(city);
        }

        //最终实体
        Scenics scenics = new Scenics();
        scenics.setCity(cityList);

        return CreateConfString.createMonitors(scenics);
    }


    /**
     * 获取监控实体信息方法
     *
     * @param list
     * @return
     */
    private List<Item> itemlistData(List<Map> list) {
        List<Item> itemList = new LinkedList<>();
        for (Map infos : list) {
            String id = infos.get("id") + "";
            String name = infos.get("name") + "";
            String pinyin = new HanYuPinYin().getHanyuPinyinFirstChar(name);
            String spinyin = pinyin;
            String outipaddress = infos.get("outipaddress") + "";
            String outport = infos.get("outport") + "";
            String simplename = infos.get("simplename") + "";
            String uname = infos.get("uname") + "";
            String pwd = infos.get("pwd") + "";
            String numcode = infos.get("numcode") + "";
            String rank = infos.get("rank") + "";
            String nowStatus = infos.get("nowStatus") + "";
            String status = infos.get("status") + "";
            String image = infos.get("image") + "";

            Item item = new Item(id, name, image, "1", numcode, outipaddress, simplename, outport, pwd, uname, rank, spinyin);
            itemList.add(item);
        }
        return itemList;
    }


    /**
     * 封装Scenic对象
     *
     * @param list
     * @param vcode
     * @param map
     * @return
     */
    private Scenic sceniclistData(List<Item>  list, String vcode, Map map, int judge) {

        //声明Scenic对象并封装数据
        Scenic scenic = new Scenic();
        scenic.setNormal("1");
        scenic.setCode(vcode);
        scenic.setItem(list);

        //监控数量放入实体
        int monitorNum = monitorConfigMapper.getCountMonitor(map);
        scenic.setMonitorCount(monitorNum + "");
        String scenicName = "";
        //分组名称放入实体
        if(judge == 1){
            scenicName = map.get("groupName") + "";
        } else {
            scenicName = monitorConfigMapper.getScenicName(vcode);
        }
        scenic.setName(scenicName);
        String pinyin = new HanYuPinYin().getHanyuPinyinFirstChar(scenicName);
        scenic.setSpinyin(pinyin);

        return scenic;
    }

}