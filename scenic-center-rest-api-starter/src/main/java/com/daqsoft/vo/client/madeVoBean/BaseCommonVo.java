/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.vo.client.madeVoBean;

/**
 * @Title: BaseCommonVo
 * @Author: tanggm
 * @Date: 2018/03/17 11:18
 * @Description: TODO 公共返回VO，适用 name value
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */

public class BaseCommonVo {
    /**
     * 封装对象的 key
     */
    private Object name;
    /**
     * 封装对象的 value
     */
    private Object value;

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
