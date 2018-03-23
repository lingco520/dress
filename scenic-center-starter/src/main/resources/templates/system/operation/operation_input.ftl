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
                    <td class="form-tab-label wid-198"><label class="form-label"><i
                            class="form-require">*</i>所属菜单:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div id="menu" class="drop-down-select fl wid-378 mr-20">
                                <select>
                                    <option value="">--请选择--</option>
                                <#list menus as d>
                                    <option value="${d.id!}" <#if d.id == (dto.sysMenu.id)!0>selected</#if>
                                            data-level="${d.level}">${d.name!}</option>
                                </#list>
                                </select>
                                <input type="text" style="display: none" id="sysMenu" name="sysMenu" value="${(dto.sysMenu.id)!}"
                                       validate="{required:true,messages:{required:'请选择菜单!'}}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>操作名称:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule fl wid-358">
                                <input type="text" name="name" value="${dto.name!}" class="s_input s_input_fb w318"
                                       validate="{required:true,maxlength:15,minlength:2}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>操作代码:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div id="op" class="daq-select wid-358 fl mr-20">
                                <select id="opSelect" name="op"
                                        validate="{required:true,messages:{required:'请选择操作'}}">
                                <#list dictOperations.operation as ops>
                                    <option value="${ops.VALUE!}" <#if dto.op! == ops.VALUE!>selected</#if>
                                    >${ops.NAME}</option>
                                </#list>
                                </select>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>URL地址:</label>
                    </td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule fl wid-358">
                                <input type="text" name="url" value="${dto.url!}" class="s_input s_input_fb w318"
                                       validate="{required:true,maxlength:60}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label">操作图标:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule wid-358">
                                <input type="text" name="icon" class="s_input s_input_fb w281 "
                                       value="${dto.icon!?html}"/>
                                <em></em>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label">操作栏按钮颜色:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule wid-358">
                                <input type="text" name="color" class="s_input s_input_fb w281 "
                                       value="${dto.color!}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label">排序:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule fl wid-158">
                                <input type="text" name="sort" validate="{digits:true,range:[0,999]}"
                                       class="s_input w120 fom_inp" value="${dto.sort!}"/>
                                <em></em>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>操作栏显示:</label>
                    </td>
                    <td>
                        <div class="form-table-edit">
                            <div class="choose-box clearfix" id="operationbar">

                                <input type="radio" name="operationbar" <#if (dto.operationbar!1) == 1>checked</#if>
                                       value="1" title="显示"/>
                                <input type="radio" name="operationbar" <#if (dto.operationbar!1) == 0>checked</#if>
                                       value="0" title="不显示"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>工具类显示:</label>
                    </td>
                    <td>
                        <div class="form-table-edit">
                            <div class="choose-box clearfix" id="toolbar">
                                <input type="radio" name="toolbar" <#if (dto.toolbar!1) == 1>checked</#if> value="1"
                                       title="显示"/>
                                <input type="radio" name="toolbar" <#if (dto.toolbar!1) == 0>checked</#if> value="0"
                                       title="不显示"/>
                            </div>
                        </div>
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
    $("#toolbar").daqRadio();
    $("#operationbar").daqRadio();
    $("#op").daqSelect();
    $("#menu").dropDownSelect({
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
        multi: false,
        change:function(event, option, options) {
            $("#sysMenu").val(option.value);
            $("#sysMenu").keyup();
        }
    });
</script>
</html>