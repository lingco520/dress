package com.daqsoft.constants;

/**
 * @Author: superziy .
 * @Date: Created in 9:50 2017/6/22.
 * @Version: 1.0
 * @describe: 常量
 */
public abstract class Constants {


    /**
     * 大数据实验室提供运营商数据接口
     */
    public static String Bigdata = "http://data.daqsoft.com/dataService/";

    /**
     * 微信访问量接口
     */
    public static String Wechat = "http://yun.m.geeker.com.cn/queryFlux.do";

    /**
     * 网站集群平台接口
     */
    //public static String WebStat ="http://192.168.0.35/hzcloud/web/site/stat";
    public static String WebStat ="http://cms.hzcloud.daqsoft.com/hzcloud/web/site/stat";

    /**
     * 触摸屏PC端平台接口
     */
    //分页参数默认设置
    public static final int PAGE_NO = 1;
    public static final int PAGE_SIZE = 10;
    public static final String ASC = " asc ";
    public static final String ORACLEDATABAS = "ORACLE";
    //UTF-8编码
    public static final String UTF8 = "UTF-8";
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String MESSAGE = "errors";
    public static final String TITLE = "title";
     //认证码
    public static final String AUTHCODE = "authcode";
    public static final String AUTHCODE_SEPERATOR = "-";
     //HTTP method
    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String SYS_CONFIG_KEY = "cfg";
    public static final String OP_IN_REQUEST = "OP";
    //    public static final String USER_IN_SESSION = "USER_IN_SESSION";
    public static final String USER_IN_SESSION = "USER_IN_SESSION";
    public static final String USER_IN_REQUEST = "USER";
    //配置的景区编号
    public final static String TYPE = "1";
    public final static String GETTOKEN_URL = "http://www.scgis.net.cn/imap/imapServer/token/getToken?username=datacenter&password=daqsoft&IpAddress=&TimeSpan=30D";
     //信息发布 - 公告
    //信息发布链接
    public final static String SXH_INFOSEND_URL = "sxhInfosendUrl";
    //信息发布、OTA数据      --用户标识
    public final static String USER_MARK = "userMark";
    //接口返回数据格式
    public final static String FORMAT_JSON = "json";
    //电子巡更接口Url地址
    public static String ELECTRONIC ="http://show.keepers.com.cn:8089/WebService/MobileService.asmx";
    //海螺沟短信发送接口
    public static String sendMessageURL ="http://hprpt2.eucp.b2m.cn:8080/sdkproxy/sendsms.action";
    //海螺沟短信发送接口cdKey
    public static String HLG_CD_KEY ="cdkey=8SDK-EMY-6699-REUSQ";
    //海螺沟短信发送接口密码
    public static String HLG_CD_PASSWORD ="password=729909";
}
