package com.daqsoft.vo.client.madeVoBean;

/**
 * @author zhouq
 * @email zhouq@daqsoft.com
 * @date 2017-06-26 17:55
 * @Version:
 * @Describe:景区信息VO
 */
public class JqScenicMsgVO {
    //景区名称
    private String name;
    //景区编码
    private String vcode;
    //账号
    private String account;
    //账号名称
    private String accountName;
    //资源编码
    private String resourceCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public JqScenicMsgVO() {
    }

    public JqScenicMsgVO(String name, String vcode, String account, String accountName, String resourceCode) {
        this.name = name;
        this.vcode = vcode;
        this.account = account;
        this.accountName = accountName;
        this.resourceCode = resourceCode;
    }
}
