package com.daqsoft.clientUtils.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: 轮播监控
 * @Author: lyl
 * @Date: 2018-02-06 11:37
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@XStreamAlias("slideVideos")
public class SlideVideos implements Serializable {
    private static final long serialVersionUID = 1L;
    @XStreamImplicit
    private List<Slide> slides = new ArrayList();

    public SlideVideos() {
    }

    public SlideVideos(List<Slide> slides) {
        this.slides = slides;
    }

    public List<Slide> getSlides() {
        return this.slides;
    }

    public void setSlides(List<Slide> slides) {
        this.slides = slides;
    }

    public String toString() {
        return "SlideVideos [slides=" + this.slides + "]";
    }
}