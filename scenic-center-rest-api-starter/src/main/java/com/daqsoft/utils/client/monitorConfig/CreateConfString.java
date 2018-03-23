package com.daqsoft.utils.client.monitorConfig;

import com.daqsoft.vo.client.monitorConfig.*;
import com.thoughtworks.xstream.XStream;

/**
 * @Title: 客户端数据创建XML方法类
 * @Author: lyl
 * @Date: 2018-03-21 18:00
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
public class CreateConfString {
    public CreateConfString() {
    }

    /**
     * 监控配置
     *
     * @param scenics
     * @return
     */
    public static String createMonitors(Scenics scenics) {
        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);
        xStream.alias("scenics", Scenics.class);
        xStream.alias("city", City.class);
        xStream.alias("scenic", Scenic.class);
        xStream.alias("item", Item.class);
        String info = xStream.toXML(scenics);
        return info;
    }

    /**
     * 轮播配置
     *
     * @param slideVideos
     * @return
     */
    public static String createSlideVideos(SlideVideos slideVideos) {
        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);
        xStream.alias("slideVideos", SlideVideos.class);
        xStream.alias("slide", Slide.class);
        xStream.alias("item", Item.class);
        String info = xStream.toXML(slideVideos);
        return info;
    }
}
