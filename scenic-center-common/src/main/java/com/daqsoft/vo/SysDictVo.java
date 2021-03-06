/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.vo;

/**
 * @Title: SysDictVo
 * @Author: tanggm
 * @Date: 2018/01/15 17:42
 * @Description: TODO 系统字典VO
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */

public class SysDictVo {
    /**
     * 字典名称
     */
    private String name;
    /**
     * 字典值（key）
     */
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
