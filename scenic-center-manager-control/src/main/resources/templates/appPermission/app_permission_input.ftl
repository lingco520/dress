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
        <div class="form-item-table mt-22">
            <table cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>权限名称:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule wid-358">
                                <input type="text" name="name" value="${dto.name!}" class="s_input s_input_fb w318"
                                       validate="{required:true,maxlength:15,minlength:2}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label wid-198 "><label class="form-label"><i
                            class="form-require">*</i>权限代码:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule wid-358">
                                <input type="text" name="code" value="${dto.code!}"
                                       class="s_input s_input_fb w318" <#if dto.code??>readonly</#if>
                                />
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>排序:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule wid-358">
                                <input type="text" name="sort" validate="{required:true,range:[1,999]}"
                                       class="s_input w120 fom_inp" value="${dto.sort!'1'}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>上级权限:</label></td>
                <td>
                    <div class="form-table-edit">
                        <div id="parent" class="daq-select wid-358 fl mr-20">
                            <select name="pid" readonly>
                                <option value="">顶级权限</option>
                            <#if appPermissions??>
                                <#list appPermissions as s>
                                    <option value="${s.id!}"
                                            <#if (dto.pid)?? && s.id == dto.pid>selected</#if>>
                                        <#if s.nlevel ==2>&nbsp;&nbsp;&nbsp;</#if>
                                        <#if s.nlevel ==3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</#if>
                                    ${s.name!''}
                                    </option>
                                </#list>
                            </#if>
                            </select>
                        </div>
                    </div>
                </td>
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
            <#include "../../../submit.ftl">
            </table>
        </div>
    </form>
</div>
</body>
<script>
    $("#parent").daqSelect({
        value: '${dto.pid!}',
        callback: function (data) {
        }
    });

    $("#status").daqRadio();
</script>
</html>