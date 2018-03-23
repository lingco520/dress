<!DOCTYPE html>
<html>
<head>
<#include "../../header.ftl">
<#include "../../edit.ftl">
</head>
<body>
<p class="demo-title"></p><!-- 这条线为了好看放这里 -->
<div class="form-content mt-32">
    <form id="validateForm" action="">
        <input type="hidden" name="id" value="${dto.id!}"/>
        <div class="form-item-table mt-22">
            <table cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="form-tab-label wid-198 "><label class="form-label"><i class="form-require">*</i>菜单名称:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule wid-358 fl">
                                <input type="text" name="name" class="s_input s_input_fb w318" validate="{required:true,maxlength:15,minlength:2}" value="${dto.name!}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require"></i>上级菜单:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div id="parentMenu" class="drop-down-select wid-378 mr-20">
                                <select name="parentMenu">
                                    <option value="">-顶级菜单-</option>
                                <#list menus as d>
                                    <option value="${d.id!}" <#if d.id == (dto.parentMenu.id)!0>selected</#if> data-level="${d.level!}">${d.name!}</option>
                                </#list>
                                </select>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label">菜单图标:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule wid-358">
                                <input type="text" name="icon" class="s_input s_input_fb w281"
                                       value="${dto.icon!?html}"/>
                            </div>

                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label">唯一编码:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule wid-358">
                                <input type="text" name="code" class="s_input s_input_fb w281 "
                                       value="${dto.code!}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>排序:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule wid-158 fl">
                                <input type="text" name="sort" validate="{required:true,range:[0,999]}" class="s_input w120 fom_inp" value="${dto.sort!}"/>
                            </div>
                        </div>

                        <label style="margin-left: 10px;"><i class='sysfont tooltip' skin="dark"
                                                             data-tips="注:请避免同级菜单使用相同的排序值&nbsp;. 排序规则:高位优先排序,例:107与95,高位1比9小.107的优先级比95高.如果高位相同,则向右依次比较." position="bottom-left" style="color:#9aa3b2">&#xe637;</i></label>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>状态:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="choose-box clearfix" id="status">
                                <input type="radio" name="status" value="1" title="启用"
                                       <#if (dto.status!1) == 1>checked</#if>>
                                <input type="radio" name="status" value="0" title="停用"
                                       <#if (dto.status!1) == 0>checked</#if>>
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
    // 实例化禁用的下拉选择器
    $(".tooltip").daqTips();
    $("#parentMenu").dropDownSelect({
        level: {
            enable: true, // 使用层级
            separator: '',
            simple: true, // 使用简单层级
            key: 'level' // 表示层级的字段为level，匹配option的data-level
        },
        defaultSelected: {
            enable: true
        },
        separator: '',
        filter: true,
        multi: false
    });
</script>
</html>