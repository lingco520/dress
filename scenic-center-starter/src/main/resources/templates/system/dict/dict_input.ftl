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
                    <td class="tab-label-color"><label class="form-label"><i class="form-require">*</i>字典类型:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule fl wid-358">
                                <input type="text" name="name" class="s_input s_input_fb w318"
                                       validate="{required:true,maxlength:15,minlength:2}" value="${dto.name!}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="tab-label-color"><label class="form-label">字典值:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule wid-358">
                                <input type="text" name="value" class="s_input s_input_fb w318" value="${dto.value!}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="tab-label-color"><label class="form-label"><i class="form-require"></i>上级字典:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div id="parentDict" class="drop-down-select wid-378 mr-20">
                                <select name="parentDict" >
                                    <option value="">--顶级字典--</option>
                                <#list trees! as d>
                                    <option value="${d.ID!}" <#if d.ID == (dto.parentDict.id)!0>selected</#if>
                                            data-level="${d.LEVEL}">${d.NAME!}</option>
                                </#list>
                                </select>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="tab-label-color"><label class="form-label"><i class="form-require">*</i>字典类别:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule fl wid-358">
                                <input type="text" name="type"
                                       <#if dto.id??>readonly</#if>  validate="{required:true,maxlength:20}"
                                       value="${dto.type!}"/>
                            </div>
                        </div>
                        <label style="margin-left: 10px;"><i class='sysfont tooltip' skin="dark"
                                                             data-tips="'字典类型'是对数据字典进行分类,如:性别,民族,政治面貌等,填写规则尽量使用该类型的英文以方便识别,通过类型查询时,会将该类型所有子典查询出来.请确认添加类型,保存后修改无效." position="bottom-left" style="color:#9aa3b2">&#xe637;</i></label>
                    </td>
                </tr>
                <tr>
                    <td class="tab-label-color"><label class="form-label"><i class="form-require">*</i>排序:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule  fl  wid-158">
                                <input type="text" name="sort" class="s_input s_input_fb w281"
                                       validate="{required:true,digits:true,range:[0,999]}" value="${dto.sort!}"/>
                            </div>

                        </div>
                        <label style="margin-left: 10px;"><i class='sysfont tooltip' skin="dark"
                                                             data-tips="注:请避免同级字典使用相同的排序值&nbsp;. 排序规则:高位优先排序,例:107与95,高位1比9小.107的优先级比95高.如果高位相同,则向右依次比较." position="bottom-left" style="color:#9aa3b2">&#xe637;</i></label>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label">字典级别:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div id="nlevel" class="daq-select wid-158 fl">
                                <select name="nlevel">
                                    <option value="program" <#if dto.nlevel! == 'program'>selected</#if>>program</option>
                                    <option value="system" <#if dto.nlevel! == 'system'>selected</#if>>system</option>
                                </select>
                            </div>
                        </div>
                        <label style="margin-left: 10px;"><i class='sysfont tooltip' skin="dark"
                                                             data-tips="请确认添加类型,保存后修改无效." position="bottom-left" style="color:#9aa3b2">&#xe637;</i></label>
                    </td>
                </tr>
            <#include "../../submit.ftl"/>
            </table>
        </div>
    </form>
</div>
</body>
<script type="application/javascript">
    // 调用生成单选按钮方法
    $("#status").daqRadio();
    $(".tooltip").daqTips();
    // 实例化禁用的下拉选择器
    $("#parentDict").dropDownSelect({
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
    $("#nlevel").daqSelect({
        <#if dto.id??>
            disable: true
        </#if>
    });
</script>
</html>