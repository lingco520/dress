<!DOCTYPE html>
<html>
<head>
<#include "../header.ftl">
<#include "../edit.ftl">
</head>
<body>
<div class="section-content">
    <div class="section-son-content pad-bot0">
        <p class="demo-title"></p><!-- 这条线为了好看放这里 -->
        <div class="form-content mt-32">
            <form id="validateForm" action="">
                <input type="hidden" name="id" value="${dto.id!}"/>
                <div class="form-item-table mt-22">
                    <table cellpadding="0" cellspacing="0" width="100%">
                        <tr>
                            <td class="form-tab-label wid-198 "><label class="form-label"><i class="form-require">*</i>车牌号:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="busNum" class="s_input s_input_fb w318" validate="{required:true,maxlength:8,minlength:7<#if !dto.busNum??>,remote:'/bus/checkBusNum'</#if>}" value="${dto.busNum!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>所属企业:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="company" class="s_input s_input_fb w318" validate="{required:true,maxlength:100}" value="${dto.company!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">企业电话:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="phone" class="s_input s_input_fb w281" validate="{maxlength:20}" value="${dto.phone!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">客车级别:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="carLevel" class="daq-select wid-198 fl mr-20">
                                        <select name="carLevel">
                                            <#list carLevels as l>
                                                <option value="${l.VALUE!}" <#if l.VALUE == (dto.carLevel)!'carLevel_1'>selected</#if>>${l.NAME!}</option>
                                            </#list>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">客车类型:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="carType" class="daq-select wid-198 fl mr-20">
                                        <select name="carType">
                                        <#list carTypes as t>
                                            <option value="${t.VALUE!}" <#if t.VALUE == (dto.carType)!'carType_1'>selected</#if>>${t.NAME!}</option>
                                        </#list>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">准载人数:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="people" validate="{digits: true,range:[0,999],messages:{digits: '请输入非负整数'}}" class="s_input w120 fom_inp" value="${dto.people!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">颜色:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="color" validate="{maxlength:4}" class="s_input w120 fom_inp" value="${dto.color!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">状态:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="choose-box clearfix" id="status">
                                        <input type="radio" name="status" value="1" title="正常"
                                               <#if (dto.status!1) == 1>checked</#if>>
                                        <input type="radio" name="status" value="0" title="禁用"
                                               <#if (dto.status!1) == 0>checked</#if>>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    <#include "../submit.ftl">
                    </table>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
<script type="application/javascript">
    // 调用生成单选按钮方法
    $("#status").daqRadio();
    // 实例化下拉选择器
    $("#carLevel").daqSelect({
        value: '${dto.carLevel!'carLevel_1'}',
        callback: function (data) {
        }
    });
    // 实例化下拉选择器
    $("#carType").daqSelect({
        value: '${dto.carType!'carType_1'}',
        callback: function (data) {
        }
    });
</script>
</html>