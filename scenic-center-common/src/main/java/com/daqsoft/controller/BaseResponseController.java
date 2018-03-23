/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.controller;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.constants.ScrsConstants;
import com.daqsoft.form.PageForm;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @Title: BaseResponseController
 * @Author: tanggm
 * @Date: 2017/12/27 15:15
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */

public class BaseResponseController {
    protected static final String PAGER = "pager";
    protected static final String SAVE_SUCCESS_OPERATION = "保存成功!";
    protected static final String SAVE_FAILED_OPERATION = "保存失败!";
    protected static final String HEADER_X_REQUESTED_WITH = "X-Requested-With=XMLHttpRequest";

    protected String checkPrincipal(Principal principal) {
        if (ObjectUtils.isEmpty(principal)) {
            throw new UsernameNotFoundException("用户未找到!");
        } else {
            return principal.getName();
        }
    }

    /**
     * 分页返回
     *
     * @param pageForm
     * @return
     */
    public BaseResponse pageBuilderSuccess(PageForm pageForm) {
        return ResponseBuilder.custom().success().currPage(pageForm.getCurrPage()).pageSize(pageForm.getPageSize())
                .totalCount((int) pageForm.getTotalCount()).totalPage(pageForm.getTotalPage())
                .data(pageForm.getRows()).build();
    }

    /**
     * 返回实体对象信息
     *
     * @param obj
     * @return
     */
    public BaseResponse dataBuilderSuccess(Object obj) {
        return ResponseBuilder.custom().success().data(obj).build();
    }

    /**
     * 操作成功
     * 无信息返回，默认code为 1
     *
     * @return
     */
    public BaseResponse builderSuccess() {
        return ResponseBuilder.custom().success().build();
    }

    /**
     * 操作成功
     * 自定义信息，默认code为 0
     *
     * @param msg 返回信息
     * @return
     */
    public BaseResponse builderSuccess(String msg) {
        return ResponseBuilder.custom().success(msg).build();
    }

    /**
     * 操作成功
     *
     * @param msg  返回信息
     * @param code 自定义code
     * @return
     */
    public BaseResponse builderSuccess(String msg, int code) {
        return ResponseBuilder.custom().success(msg, code).build();
    }


    /**
     * 操作错误或异常
     * 错误信息， 默认code 为 1
     *
     * @param msg 信息
     * @return
     */
    public BaseResponse builderFailed(String msg) {
        return ResponseBuilder.custom().failed(msg, 1).build();
    }

    /**
     * 操作错误
     * 自定义信息与code
     * 错误自定义code
     *
     * @return
     */
    public BaseResponse builderFailed(String msg, int code) {
        return ResponseBuilder.custom().failed(msg, code).build();
    }

    /**
     * 获取用户vcode
     */
    public String getVcode(HttpServletRequest request) {
        return request.getSession().getAttribute(ScrsConstants.VCODE)+"";
    }
    /**
     * 获取登录用户账号
     */
    public String getAccount(HttpServletRequest request) {
        return request.getSession().getAttribute(ScrsConstants.ACCOUNT)+"";
    }
    /**
     * 获取登录用户地区region
     */
    public String getSysUserRegion(HttpServletRequest request) {
        return request.getSession().getAttribute(ScrsConstants.USER_REGION)+"";
    }
}
