package com.daqsoft.vo.client.madeVoBean;

/**
 * @author tanggm
 * @version V3.1
 * @description 景区公告
 * @date 2017-07-03 18:58
 */
public class ScenicNewsVo {
    // id
    private Integer id;
    // 标题
    private String title;
    // 资源
    private String source;
    // 链接
    private String link;
    // 内容
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
