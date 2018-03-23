<!DOCTYPE html>
<html>
<head>
<#include "../../header.ftl">
<#include "../../edit.ftl">
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
        <div class="form-item-table mt-22">
            <table cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="form-tab-label wid-198 "><label class="form-label"><i
                            class="form-require">*</i>登陆帐号:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule fl wid-358">
                                <input type="text" name="account" value="${dto.account!}"
                                       class="s_input s_input_fb w318" <#if dto.account??>readonly</#if>
                                       validate="{required:true,maxlength:20,minlength:4<#if !dto.account??>,remote:'/user/checkAccount'</#if>}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>姓名:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule fl wid-358">
                                <input type="text" name="name" value="${dto.name!}" class="s_input s_input_fb w318"
                                       validate="{required:true,maxlength:10,minlength:2}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label">邮箱:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule fl wid-358">
                                <input type="text" name="email" value="${dto.email!}" class="s_input s_input_fb w318"
                                       validate="{email:true}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label">手机号:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule fl wid-358">
                                <input type="text" name="phone" value="${dto.phone!}" class="s_input s_input_fb w318"
                                       validate="{phone:true}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>所在地:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <label id="regionErrorPopups" style="float: left;">
                                <input type="text" id="areaSelect" name="region" value="${dto.region!}"
                                       validate="{required:true,messages:{required:'请选择所在地区'},errorPlacement:errorPlacement}"
                                       defaultSelectedPath="${regions!}"/>
                            </label>
                        </div>
                    </td>
                </tr>

                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require"></i>用户角色:</label></td>
                    <td>
                        <div class="form-table-edit " style="width:50%;">
                            <div id="roleid" class="mt-20 clearfix choose-box">
                            <#list roles as role>
                                <input type="checkbox" name="roleid"
                                       value="${role.id!}"
                                       <#if dto.roles?seq_contains(role)>checked</#if>
                                       title="${role.name!}"/>
                            </#list>
                            </div>
                        </div>
                    </td>
                </tr>
            <#include "../../submit.ftl">
            </table>
        </div>
    </form>
</div>
</body>
<script type="application/javascript">
    // 调用生成单选按钮方法
    $("#status").daqRadio();
    $("#roleid").daqCheckbox({
        type: 1
    });
    function errorPlacement(error, element) {
        $(error).css({float: "inherit"});
        $(error).appendTo($(element).parent("#regionErrorPopups"));
    }
    $().ready(function () {
        var $areaSelect = $("#areaSelect");
        // 地区选择菜单
        $areaSelect.lSelect({
            url: "/permitted/ajaxRegion",// AJAX数据获取url
            parameter: "region"
        });
    })
</script>
</html>