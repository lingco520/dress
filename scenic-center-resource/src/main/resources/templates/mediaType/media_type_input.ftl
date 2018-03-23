<!DOCTYPE html>
<html>
<head>
<#include "../header.ftl">
<#include "../edit.ftl">
<#include "../upload.ftl">
<#include "../kindEditor.ftl">
    <script type="text/javascript" src="/script/jQuery/jquery.lSelect.js"></script>
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
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>所属类型名称:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="mediaType" class="daq-select wid-198 fl mr-20">
                                        <select name="type">
                                            <#if mediaType??>
                                        <#list mediaType as s>
                                            <option value="${s.value!}" <#if s.value == (dto.type)!>selected</#if>>${s.name!}</option>
                                        </#list>
                                            </#if>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>类型名称:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="name" validate="{required:true,maxlength:20}" class="s_input w120 fom_inp" value="${dto.name!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">是否微官网展示:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="choose-box clearfix" id="showWeb">
                                        <input type="radio" name="showWeb" value="1" title="展示"
                                               <#if (dto.showWeb!1) == 1>checked</#if>>
                                        <input type="radio" name="showWeb" value="0" title="不展示"
                                               <#if (dto.showWeb!1) == 0>checked</#if>>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">是否微官网展示:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="choose-box clearfix" id="showTouch">
                                        <input type="radio" name="showTouch" value="1" title="展示"
                                               <#if (dto.showTouch!1) == 1>checked</#if>>
                                        <input type="radio" name="showTouch" value="0" title="不展示"
                                               <#if (dto.showTouch!1) == 0>checked</#if>>
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
    $("#mediaType").daqSelect({
        value: '${dto.type!}',
        callback: function (data) {
        }
    });
    // 调用生成单选按钮方法
    $("#showTouch").daqRadio();
    $("#showWeb").daqRadio();
    $("#status").daqRadio();

</script>
</html>