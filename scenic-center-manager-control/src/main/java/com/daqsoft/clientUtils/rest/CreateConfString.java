package com.daqsoft.clientUtils.rest;

import com.daqsoft.clientUtils.domain.Daqec;
import com.daqsoft.clientUtils.domain.*;
import com.thoughtworks.xstream.XStream;

/**
 * @Title: 客户端数据创建XML方法类
 * @Author: lyl
 * @Date: 2018-02-06 11:39
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

    public static String createScreenConf(Daqec daqec) {
        String info = null;
        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);
        xStream.alias("screenConf", Daqec.class);
        xStream.alias("widgts", Widgts.class);
        xStream.alias("widgt", Widgt.class);
        info = xStream.toXML(daqec);
        return info;
    }

    public static String createMttMonitors(MttScenics mttScenics) {
        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);
        xStream.alias("mttScenics", MttScenics.class);
        xStream.alias("city", City.class);
        xStream.alias("scenic", Scenic.class);
        xStream.alias("item", Item.class);
        String info = xStream.toXML(mttScenics);
        return info;
    }

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

    public static String createTopUrl(Apps apps) {
        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);
        xStream.alias("apps", Apps.class);
        xStream.alias("app", AppItem.class);
        String info = xStream.toXML(apps);
        return info;
    }

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
