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
                            <td class="form-tab-label"><label class="form-label">渠道名称:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="ditchNameType" class="daq-select wid-198 fl mr-20">
                                        <select name="ditchName">
                                        <#if ditchNameType??>
                                            <#list ditchNameType as t>
                                                <option value="${t.name!}" <#if t.name == (dto.ditchName)!>selected</#if>>${t.name!}</option>
                                            </#list>
                                        </#if>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>标题:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="title" class="s_input s_input_fb w318" validate="{required:true,maxlength:20}" value="${dto.title!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>作者:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="autho" class="s_input s_input_fb w318" validate="{required:true,maxlength:20}" value="${dto.autho!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>来源:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="source" class="s_input s_input_fb w318" validate="{required:true,maxlength:20}" value="${dto.source!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>文章标签:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="label" class="s_input s_input_fb w318" validate="{required:true,maxlength:20}" value="${dto.label!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">发布时间:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" readonly="readonly" name="publishTime" id="publishTime" class="s_input s_input_fb w281 js_tooltip"  skin="dark" data-tips="请选择时间" position="bottom-left" value="${dto.publishTime!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>链接:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="link" class="s_input s_input_fb w318" validate="{required:true,maxlength:200}" value="${dto.link!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">简介:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="js-dynamic-box" >
                                        <textarea name="content" id="content"
                                                  style="width: 100%; height: 250px" >${(dto.content?html)!''}</textarea>
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
        value: '${dto.ditchName!'ditchType_1'}',
        callback: function (data) {
        }
    });
    $("#ditchNameType").daqSelect({
        value: '${dto.ditchName!}',
        callback: function (data) {
        }
    });

    $("#status").daqRadio();

    //时间选择器
    laydate.render({
        elem: '#publishTime'
        ,type: 'datetime'
    });
    //一定要注意button上的data-tips和position属性一定要设置并且要按规律设置
    $('.js_tooltip').daqTips();

    $(function() {
        //编辑器
        initKindEditor("content");
    });
</script>
</html>