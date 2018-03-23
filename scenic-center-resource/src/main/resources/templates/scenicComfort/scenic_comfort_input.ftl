<!DOCTYPE html>
<html>
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
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>景点名称:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="scenicName" class="daq-select wid-198 fl mr-20">
                                        <select name="name">
                                            <#if scenicName??>
                                                <#list scenicName as s>
                                                    <option value="${s.name!}" <#if s.name == (dto.name)!>selected</#if>>${s.name!}</option>
                                                </#list>
                                            </#if>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>舒适度等级:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <select name="rating">
                                        <option value="非常舒适" <#if (dto.rating)! == '非常舒适'>selected</#if>>非常舒适</option>
                                        <option value="舒适" <#if (dto.rating)! == '舒适'>selected</#if>>舒适</option>
                                        <option value="一般"<#if (dto.rating)! == '一般'>selected</#if>>一般</option>
                                        <option value="拥挤"<#if (dto.rating)! == '拥挤'>selected</#if>>拥挤</option>
                                        <option value="非常拥挤"<#if (dto.rating)! == '非常拥挤'>selected</#if>>非常拥挤</option>
                                        <option value="爆满"<#if (dto.rating)! == '爆满'>selected</#if>>爆满</option>
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>舒适度等级最小人数:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="mincount" validate="{required:true,digits: true,range:[0,999999],messages:{digits: '请输入非负整数'}}" class="s_input w120 fom_inp" value="${dto.mincount!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>舒适度等级最大人数:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="maxcount" validate="{required:true,digits: true,range:[0,999999],messages:{digits: '请输入非负整数'}}" class="s_input w120 fom_inp" value="${dto.maxcount!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>排序:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="rank" validate="{required:true,digits: true,range:[0,999],messages:{digits: '请输入非负整数'}}" class="s_input w120 fom_inp" value="${dto.rank!'0'}"/>
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
    $("#scenicName").daqSelect({
        value: '${dto.name!}',
        callback: function (data) {
        }
    });
    // 调用生成单选按钮方法
    $("#status").daqRadio();







</script>
</html>