<!DOCTYPE html>
<html>
<head>
<#include "../../../header.ftl">
<#include "../../../edit.ftl">
    <script type="text/javascript" src="/script/jQuery/jquery.lSelect.js"></script>
    <style>
        select {
            width: 150px;
            padding: 5px;
            font-size: 14px;
            border: 1px solid #ccc;
            height: 34px;
        }
    </style>
</head>
<body>
<p class="demo-title"></p><!-- 这条线为了好看放这里 -->
<div class="form-content mt-32">
    <form id="validateForm" action="">
        <input type="hidden" name="id" value="${dto.id!}"/>
        <input type="hidden" name="creater" value="${dto.creater!}"/>
        <input type="hidden" name="vcode" value="${dto.vcode!}"/>
        <div class="form-item-table mt-22">
            <table cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="form-tab-label wid-198 "><label class="form-label"><i
                            class="form-require">*</i>登陆帐号:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule wid-358">
                                <input type="text" name="account" value="${dto.account!}"
                                       class="s_input s_input_fb w318" <#if dto.account??>readonly</#if>
                                       />
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>姓名:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule wid-358">
                                <input type="text" name="name" value="${dto.name!}" class="s_input s_input_fb w318"
                                       validate="{required:true,maxlength:10,minlength:2}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>邮箱:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule wid-358">
                                <input type="text" name="email" value="${dto.email!}" class="s_input s_input_fb w318"
                                       validate="{required:true,email:true}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>手机号:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule wid-358">
                                <input type="text" name="phone" value="${dto.phone!}" class="s_input s_input_fb w318"
                                       validate="{required:true,phone:true}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label">app用户类型:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div id="appUserType" class="daq-select wid-198 fl mr-20">
                                <select name="appUserType">

                                    <option value="">暂未设置</option>
                                <#if appUserType??>
                                    <#list appUserType as t>
                                        <option value="${t.value!}"
                                                <#if t.value == (dto.appUserType)!'appUserType_1'>selected</#if>>${t.name!}</option>
                                    </#list>
                                </#if>
                                </select>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label">app用户角色:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div id="appUserRole" class="daq-select wid-198 fl mr-20">
                                <select name="appUserRole">
                                    <option value="">暂无角色</option>
                                <#if appRoles?? && (appRoles?size > 0) >
                                    <#list appRoles as t>
                                        <option value="${t.id!}"
                                                <#if t.id == (dto.appUserRole)!-1>selected</#if>>${t.name!}</option>
                                    </#list>
                                </#if>
                                </select>
                            </div>
                        </div>
                    </td>
                </tr>
            <#include "../../../submit.ftl">
            </table>
        </div>
    </form>
</div>
</body>
<script>
    $("#appUserType").daqSelect({
        value: '${dto.appUserType!''}'
    });
    $("#appUserRole").daqSelect({
        value: '${dto.appUserRole!''}'
    });
</script>
</html>