package com.daqsoft.vo.client.mysqlBean;

/**
 * 云台山监控实体
 * Created by lyl on 2017/4/18 0018.
 */
public class Monitor implements java.io.Serializable{
    private int id;
    private String brand;//品牌
    private int clarity;//
    private String COORDINATE_X;//经度
    private String COORDINATE_Y;//纬度
    private String image;//图片链接
    private String outipaddress;//外网ip
    private String isshow;//是否展示，1展示，0不展示
    private String main;//
    private String name;//名称
    private int nowStatus;//目前状态
    private int numcode;//
    private String play;//
    private String outport;//外网端口
    private String pwd;//监控密码
    private int rank;//排序
    private String remark;//备注
    private int status;//状态
    private String time;//添加时间
    private String type;//
    private String uname;//监控用户名
    private int now_status;//目前状态
    private String longitude;//经度
    private String code;
    private String latitude;//纬度
    private int numVcode;
    private String vcode;
    private String inipaddress;//内网ip地址
    private String inport;//内网端口
    private String port;//端口
    private String url;//播放链接

    public Monitor(){}

    public Monitor(int id, String brand, int clarity, String COORDINATE_X, String COORDINATE_Y, String image, String outipaddress, String isshow, String main, String name, int nowStatus, int numcode, String paly, String outport, String pwd, int rank, String remark, int status, String time, String type, String uname, int now_status, String longitude, String code, String latitude, int numVcode, String vcode, String inipaddress, String inport, String port, String url) {
        this.id = id;
        this.brand = brand;
        this.clarity = clarity;
        this.COORDINATE_X = COORDINATE_X;
        this.COORDINATE_Y = COORDINATE_Y;
        this.image = image;
        this.outipaddress = outipaddress;
        this.isshow = isshow;
        this.main = main;
        this.name = name;
        this.nowStatus = nowStatus;
        this.numcode = numcode;
        this.play = play;
        this.outport = outport;
        this.pwd = pwd;
        this.rank = rank;
        this.remark = remark;
        this.status = status;
        this.time = time;
        this.type = type;
        this.uname = uname;
        this.now_status = now_status;
        this.longitude = longitude;
        this.code = code;
        this.latitude = latitude;
        this.numVcode = numVcode;
        this.vcode = vcode;
        this.inipaddress = inipaddress;
        this.inport = inport;
        this.port = port;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getClarity() {
        return clarity;
    }

    public void setClarity(int clarity) {
        this.clarity = clarity;
    }

    public String getCOORDINATE_X() {
        return COORDINATE_X;
    }

    public void setCOORDINATE_X(String COORDINATE_X) {
        this.COORDINATE_X = COORDINATE_X;
    }

    public String getCOORDINATE_Y() {
        return COORDINATE_Y;
    }

    public void setCOORDINATE_Y(String COORDINATE_Y) {
        this.COORDINATE_Y = COORDINATE_Y;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOutipaddress() {
        return outipaddress;
    }

    public void setOutipaddress(String outipaddress) {
        this.outipaddress = outipaddress;
    }

    public String getIsshow() {
        return isshow;
    }

    public void setIsshow(String isshow) {
        this.isshow = isshow;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNowStatus() {
        return nowStatus;
    }

    public void setNowStatus(int nowStatus) {
        this.nowStatus = nowStatus;
    }

    public int getNumcode() {
        return numcode;
    }

    public void setNumcode(int numcode) {
        this.numcode = numcode;
    }

    public String getPaly() {
        return play;
    }

    public void setPaly(String paly) {
        this.play = paly;
    }

    public String getOutport() {
        return outport;
    }

    public void setOutport(String outport) {
        this.outport = outport;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public int getNow_status() {
        return now_status;
    }

    public void setNow_status(int now_status) {
        this.now_status = now_status;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getNumVcode() {
        return numVcode;
    }

    public void setNumVcode(int numVcode) {
        this.numVcode = numVcode;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getInipaddress() {
        return inipaddress;
    }

    public void setInipaddress(String inipaddress) {
        this.inipaddress = inipaddress;
    }

    public String getInport() {
        return inport;
    }

    public void setInport(String inport) {
        this.inport = inport;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
