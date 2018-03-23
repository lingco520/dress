/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.vo;

/**
 * @Title: ScenicSpotsVo
 * @Author: superziy
 * @Date: 2018/01/15 17:42
 * @Description: 景点名称
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */

public class ScenicSpotsVo {
    /**
     * id
     */
    private String id;
    /**
     * 景点名称
     */
    private String name;
    /**
     * 景区唯一标示
     */
    private String vcode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
}
