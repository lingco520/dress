package com.daqsoft.vo.client.mysqlBean;

/**
 * Created by Administrator on 2017-4-16.
 */
public class ApiScenicSpots {


    private Long id;
    private String name;//景区景点的中文名称
    private String zhName;//景区景点的中文别名  景区景点的中文别名
    private String usName;//景区景点的英文或拼音全称
    private String keyWord;//可概括性定义对象的一个或多个词或短语
    private String administrativeDivision;//景区所属的行政区划
    private String scenicType;//所属的景区类型；根据 GB/T 18972确定
    private String scenicArea;//AAAAA级旅游区(点)、AAAA级旅游区(点)、AAA级旅游区(点)、AA级旅游区(点)、A级旅游区(点)；非A级旅游区(点)；根据GB/T 17775-2003确定
    private String relatedTitles;//景区获得的相关荣誉资质称号，如“国家级风景名胜区”、“世界文化遗产”等
    private String scenicSpotPid;//本景区（点）所属的上级景区（点）
    private String introduction;//景区基本情况介绍
    private String ticketPrice;//景区景点门票价格
    private String ticketDescription;//景区门票针对价格说明和优惠打折办法
    private String tourGuideWord;//景区景点导游词
    private String scenicAddress;//景区的详细地址
    private String postcode;//景区的邮政编码
    private String electronicMap;//景区电子地图
    private String trafficDescription;//到达景区的交通方式说明
    private String peripheralInformation;//附近的环境和设施说明
    private String surroundingLandmark;//附近的标志性建筑、地点或区域
    private String businessHours;//景区景点对游客开放的时间
    private String longTour;//游客在景区景点游览一般所需的时间
    private String recommendedTravelTime;//景区适合游客游览的时间，一般以月份和季度表示
    private String scenicOrganization;//景区管理机构名称
    private String administrativeAgencyAddress;//景区管理机构办公地点
    private String advisoryTelephone;//景区游客咨询电话
    private String CustomerserviceFax;//景区客服传真号码
    private String websiteUrl;//景区网站网址
    private String wapUrl;//景区WAP网站网址
    private String officialPublicSpace;//景区官方微博、公共服务空间或帐号
    private String email;//景区对外电子邮件
    private String characteristicLandscape;//景区内特色景观介绍
    private String scenicTourismProject;//景区内设置的索道、缆车、游船、游乐设施等
    private String supportingServiceFacilities;//景区内为游客提供服务的旅游配套服务设施
    private String permanentTourismActivities;//如文艺表演、民俗演出等
    private String touristServiceCenter;//景区内游客服务中心的分布和服务内容介绍
    private String electronicGuide;//景区电子导游设备的租用、使用说明
    private String recommendedTourRoute;//景区内常规游览线路
    private String ScenicTourTips;//景区内游览需要提示游客注意的相关事项
    private String tourMap;//景区游览示意图
    private String corporate;//对应的徽标或商标图片
    private String sketchPicture;//带有对象明显特征的缩略规格的图片
    private String pictureLibrary;//景点相关图片
    private String videoLibrary;//相关视频
    private String audioLibrary;//相关音频组合
    private String virtualMaterial;//基于三维或虚拟实景方式的展示素材
    private int status;
    private String vcode;
    private String longitude;//经度
    private String latitude;//纬度
    private String polygon;//描点
    private String images;//图片
    private String wantgo;//有意愿旅游的人数
    private int zan;
    private String showWeb;//是否展示在资讯网，1：不展示；2：展示在资讯网上；
    private int wngo;//咨询网页面统计，想去景区的人数
    private int goed;//咨询网统计，去过景区的人数
    private String wfp720;//管理720全景管理表，w_fp_720的url相关联
    private String personCount;//AP设备的MAC地址，唯一
    private String voiceUrl;//语音路径，与 w_voice_list 字段url关联
    private int sort;
    private String pixel_x;
    private String Pixel_y;
    private int application;//该景点应用到场景

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public String getAdministrativeDivision() {
        return administrativeDivision;
    }

    public void setAdministrativeDivision(String administrativeDivision) {
        this.administrativeDivision = administrativeDivision;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZhName() {
        return zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    public String getUsName() {
        return usName;
    }

    public void setUsName(String usName) {
        this.usName = usName;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }


    public String getScenicType() {
        return scenicType;
    }

    public void setScenicType(String scenicType) {
        this.scenicType = scenicType;
    }

    public String getScenicArea() {
        return scenicArea;
    }

    public void setScenicArea(String scenicArea) {
        this.scenicArea = scenicArea;
    }

    public String getRelatedTitles() {
        return relatedTitles;
    }

    public void setRelatedTitles(String relatedTitles) {
        this.relatedTitles = relatedTitles;
    }

    public String getScenicSpotPid() {
        return scenicSpotPid;
    }

    public void setScenicSpotPid(String scenicSpotPid) {
        this.scenicSpotPid = scenicSpotPid;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getTicketDescription() {
        return ticketDescription;
    }

    public void setTicketDescription(String ticketDescription) {
        this.ticketDescription = ticketDescription;
    }

    public String getTourGuideWord() {
        return tourGuideWord;
    }

    public void setTourGuideWord(String tourGuideWord) {
        this.tourGuideWord = tourGuideWord;
    }

    public String getScenicAddress() {
        return scenicAddress;
    }

    public void setScenicAddress(String scenicAddress) {
        this.scenicAddress = scenicAddress;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getElectronicMap() {
        return electronicMap;
    }

    public void setElectronicMap(String electronicMap) {
        this.electronicMap = electronicMap;
    }

    public String getTrafficDescription() {
        return trafficDescription;
    }

    public void setTrafficDescription(String trafficDescription) {
        this.trafficDescription = trafficDescription;
    }

    public String getPeripheralInformation() {
        return peripheralInformation;
    }

    public void setPeripheralInformation(String peripheralInformation) {
        this.peripheralInformation = peripheralInformation;
    }

    public String getSurroundingLandmark() {
        return surroundingLandmark;
    }

    public void setSurroundingLandmark(String surroundingLandmark) {
        this.surroundingLandmark = surroundingLandmark;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }

    public String getLongTour() {
        return longTour;
    }

    public void setLongTour(String longTour) {
        this.longTour = longTour;
    }

    public String getRecommendedTravelTime() {
        return recommendedTravelTime;
    }

    public void setRecommendedTravelTime(String recommendedTravelTime) {
        this.recommendedTravelTime = recommendedTravelTime;
    }

    public String getScenicOrganization() {
        return scenicOrganization;
    }

    public void setScenicOrganization(String scenicOrganization) {
        this.scenicOrganization = scenicOrganization;
    }

    public String getAdministrativeAgencyAddress() {
        return administrativeAgencyAddress;
    }

    public void setAdministrativeAgencyAddress(String administrativeAgencyAddress) {
        this.administrativeAgencyAddress = administrativeAgencyAddress;
    }

    public String getAdvisoryTelephone() {
        return advisoryTelephone;
    }

    public void setAdvisoryTelephone(String advisoryTelephone) {
        this.advisoryTelephone = advisoryTelephone;
    }

    public String getCustomerserviceFax() {
        return CustomerserviceFax;
    }

    public void setCustomerserviceFax(String customerserviceFax) {
        CustomerserviceFax = customerserviceFax;
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

    public String getOfficialPublicSpace() {
        return officialPublicSpace;
    }

    public void setOfficialPublicSpace(String officialPublicSpace) {
        this.officialPublicSpace = officialPublicSpace;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCharacteristicLandscape() {
        return characteristicLandscape;
    }

    public void setCharacteristicLandscape(String characteristicLandscape) {
        this.characteristicLandscape = characteristicLandscape;
    }

    public String getScenicTourismProject() {
        return scenicTourismProject;
    }

    public void setScenicTourismProject(String scenicTourismProject) {
        this.scenicTourismProject = scenicTourismProject;
    }

    public String getSupportingServiceFacilities() {
        return supportingServiceFacilities;
    }

    public void setSupportingServiceFacilities(String supportingServiceFacilities) {
        this.supportingServiceFacilities = supportingServiceFacilities;
    }

    public String getPermanentTourismActivities() {
        return permanentTourismActivities;
    }

    public void setPermanentTourismActivities(String permanentTourismActivities) {
        this.permanentTourismActivities = permanentTourismActivities;
    }

    public String getTouristServiceCenter() {
        return touristServiceCenter;
    }

    public void setTouristServiceCenter(String touristServiceCenter) {
        this.touristServiceCenter = touristServiceCenter;
    }

    public String getElectronicGuide() {
        return electronicGuide;
    }

    public void setElectronicGuide(String electronicGuide) {
        this.electronicGuide = electronicGuide;
    }

    public String getRecommendedTourRoute() {
        return recommendedTourRoute;
    }

    public void setRecommendedTourRoute(String recommendedTourRoute) {
        this.recommendedTourRoute = recommendedTourRoute;
    }

    public String getScenicTourTips() {
        return ScenicTourTips;
    }

    public void setScenicTourTips(String scenicTourTips) {
        ScenicTourTips = scenicTourTips;
    }

    public String getTourMap() {
        return tourMap;
    }

    public void setTourMap(String tourMap) {
        this.tourMap = tourMap;
    }

    public String getCorporate() {
        return corporate;
    }

    public void setCorporate(String corporate) {
        this.corporate = corporate;
    }

    public String getSketchPicture() {
        return sketchPicture;
    }

    public void setSketchPicture(String sketchPicture) {
        this.sketchPicture = sketchPicture;
    }

    public String getPictureLibrary() {
        return pictureLibrary;
    }

    public void setPictureLibrary(String pictureLibrary) {
        this.pictureLibrary = pictureLibrary;
    }

    public String getVideoLibrary() {
        return videoLibrary;
    }

    public void setVideoLibrary(String videoLibrary) {
        this.videoLibrary = videoLibrary;
    }

    public String getAudioLibrary() {
        return audioLibrary;
    }

    public void setAudioLibrary(String audioLibrary) {
        this.audioLibrary = audioLibrary;
    }

    public String getVirtualMaterial() {
        return virtualMaterial;
    }

    public void setVirtualMaterial(String virtualMaterial) {
        this.virtualMaterial = virtualMaterial;
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getPolygon() {
        return polygon;
    }

    public void setPolygon(String polygon) {
        this.polygon = polygon;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getWantgo() {
        return wantgo;
    }

    public void setWantgo(String wantgo) {
        this.wantgo = wantgo;
    }

    public String getShowWeb() {
        return showWeb;
    }

    public void setShowWeb(String showWeb) {
        this.showWeb = showWeb;
    }

    public int getWngo() {
        return wngo;
    }

    public void setWngo(int wngo) {
        this.wngo = wngo;
    }

    public int getGoed() {
        return goed;
    }

    public void setGoed(int goed) {
        this.goed = goed;
    }

    public String getWfp720() {
        return wfp720;
    }

    public void setWfp720(String wfp720) {
        this.wfp720 = wfp720;
    }

    public String getPersonCount() {
        return personCount;
    }

    public void setPersonCount(String personCount) {
        this.personCount = personCount;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getPixel_x() {
        return pixel_x;
    }

    public void setPixel_x(String pixel_x) {
        this.pixel_x = pixel_x;
    }

    public String getPixel_y() {
        return Pixel_y;
    }

    public void setPixel_y(String pixel_y) {
        Pixel_y = pixel_y;
    }

    public int getApplication() {
        return application;
    }

    public void setApplication(int application) {
        this.application = application;
    }

    public ApiScenicSpots() {
    }
}
