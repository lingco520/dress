<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
<#include "../header.ftl">
<#include "../edit.ftl">
<#include "../upload.ftl">
<#include "../kindEditor.ftl">
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
<div class="section-content">
    <div class="section-son-content pad-bot0">
        <p class="demo-title"></p><!-- 这条线为了好看放这里 -->
        <div class="form-content mt-32">
            <form id="validateForm" action="">
                <input type="hidden" name="id" value="${dto.id!}"/>
                <div class="form-item-table mt-22">
                    <table cellpadding="0" cellspacing="0" width="100%">
                        <tr>
                            <td class="form-tab-label"><label class="form-label">所属平台:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="platformName" class="daq-select wid-198 fl mr-20">
                                        <select name="platformName">
                                        <#if dictTypeDict??>
                                            <#list dictTypeDict as t>
                                                <option value="${t.value!}" <#if t.value == (dto.platformName)!'ditchType_1'>selected</#if>>${t.name!}</option>
                                            </#list>
                                        </#if>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>渠道名称:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="ditchName" class="s_input s_input_fb w318" validate="{required:true,maxlength:20}" value="${dto.ditchName!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>信息类型:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="informationType" class="s_input s_input_fb w318" validate="{required:true,maxlength:20}" value="${dto.informationType!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>资讯类型:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="choose-box clearfix" id="messageType">
                                    <#if messageType??>
                                        <#list messageType as t>
                                            <input type="checkbox" name="type" validate="{required:true,messages:{required:'请选择资讯类型'}}" value="${(t.value)!''}"
                                                   title="${(t.name)!''}"
                                                   <#if (dto.type)?? && (dto.type?split(","))?seq_contains(t.value)>checked</#if>/>
                                        </#list>
                                    </#if>
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
    $("#platformName").daqSelect({
        value: '${dto.platformName!'ditchType_1'}',
        callback: function (data) {
        }
    });

    $("#status").daqRadio();
    // 复选按钮绑定样式
    $("#messageType").daqCheckbox();
</script>
</html>