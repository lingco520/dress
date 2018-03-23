package com.daqsoft.vo.client.mysqlBean;

/**
 * Created by Administrator on 2017-4-17.
 */
public class ArticleActivity {
    private int id;
    private String title;//新闻标题或者活动名称
    private String keyWord;//关键词或词组
    private String publishTime;//发布时间
    private String ABSTRACT;//摘要或者活动说明
    private String content;//正文
    private String source;//来源
    private String author;//作者、供稿人或者供稿单位
    private String sketchPicture;//带有对象明显特征的缩略规格的图片
    private String image;//标题图片
    private String activityType;//活动类型
    private String startTime;//活动开始时间
    private String endTime;//活动结束时间
    private String activityTimeDescription;//活动举办时间补充说明
    private String hostCity;//活动举办城市所在行政区划
    private String activityLocation;//活动举办地点
    private String activityIntroduction;//活动介绍说明
    private Long scenicId;//活动相关景区
    private String contactPhone;//活动组织机构联系电话
    private String websiteUrl;//活动网站网址
    private String wapUrl;//活动wap网址
    private int status;
    private String vcode;
    private int scans;//浏览次数
    private String pid;//关联w_navigation_manage 字段 id
    private int sort;
    private int TYPECLASS;
    private int languageType;

    public ArticleActivity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getABSTRACT() {
        return ABSTRACT;
    }

    public void setABSTRACT(String ABSTRACT) {
        this.ABSTRACT = ABSTRACT;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSketchPicture() {
        return sketchPicture;
    }

    public void setSketchPicture(String sketchPicture) {
        this.sketchPicture = sketchPicture;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getActivityTimeDescription() {
        return activityTimeDescription;
    }

    public void setActivityTimeDescription(String activityTimeDescription) {
        this.activityTimeDescription = activityTimeDescription;
    }

    public String getHostCity() {
        return hostCity;
    }

    public void setHostCity(String hostCity) {
        this.hostCity = hostCity;
    }

    public String getActivityLocation() {
        return activityLocation;
    }

    public void setActivityLocation(String activityLocation) {
        this.activityLocation = activityLocation;
    }

    public String getActivityIntroduction() {
        return activityIntroduction;
    }

    public void setActivityIntroduction(String activityIntroduction) {
        this.activityIntroduction = activityIntroduction;
    }

    public Long getScenicId() {
        return scenicId;
    }

    public void setScenicId(Long scenicId) {
        this.scenicId = scenicId;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getWapUrl() {
        return wapUrl;
    }

    public void setWapUrl(String wapUrl) {
        this.wapUrl = wapUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public int getScans() {
        return scans;
    }

    public void setScans(int scans) {
        this.scans = scans;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getTYPECLASS() {
        return TYPECLASS;
    }

    public void setTYPECLASS(int TYPECLASS) {
        this.TYPECLASS = TYPECLASS;
    }

    public int getLanguageType() {
        return languageType;
    }

    public void setLanguageType(int languageType) {
        this.languageType = languageType;
    }

    @Override
    public String toString() {
        return "ArticleActivity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", keyWord='" + keyWord + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", ABSTRACT='" + ABSTRACT + '\'' +
                ", content='" + content + '\'' +
                ", source='" + source + '\'' +
                ", author='" + author + '\'' +
                ", sketchPicture='" + sketchPicture + '\'' +
                ", image='" + image + '\'' +
                ", activityType='" + activityType + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", activityTimeDescription='" + activityTimeDescription + '\'' +
                ", hostCity='" + hostCity + '\'' +
                ", activityLocation='" + activityLocation + '\'' +
                ", activityIntroduction='" + activityIntroduction + '\'' +
                ", scenicId=" + scenicId +
                ", contactPhone='" + contactPhone + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                ", wapUrl='" + wapUrl + '\'' +
                ", status=" + status +
                ", vcode='" + vcode + '\'' +
                ", scans=" + scans +
                ", pid='" + pid + '\'' +
                ", sort=" + sort +
                ", TYPECLASS=" + TYPECLASS +
                ", languageType=" + languageType +
                '}';
    }
}
