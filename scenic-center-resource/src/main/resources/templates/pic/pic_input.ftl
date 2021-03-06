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
                            <td class="form-tab-label wid-198 "><label class="form-label"><i class="form-require">*</i>图片标题:</label>
                            </td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="title" class="s_input s_input_fb w318"
                                               validate="{required:true,minlength:2,maxlength:20}"
                                               value="${dto.title!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>图片:</label></td>
                            <td>
                                <div class="form-table-edit clearfix">
                                    <div>
                                    <div class="upload-default-box">
                                        <i class="sysfont upload-dft-icon">&#xe69c;</i>
                                        <p class="upload-tips">支持JPG和PNG格式图片不超过2M</p>
                                    </div>
                                    <input id="imageUrl" name="imageUrl" type="hidden" validate="{required:true}">
                                    <button class="daq-btn btn-pad-52 media-btn mt-5" id="imageUrlUploadBox"
                                            type="button"><i class="sysfont mr-5"></i>点击上传
                                    </button>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>关联景点:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="scenicId" class="daq-select wid-198 fl mr-20">
                                        <select name="scenicId" validate="{required:true}">
                                        <#if scenicList??>
                                            <#list scenicList as t>
                                                <option value="${t.id!}" <#if t.id == (dto.scenicId)!?string>selected</#if>>${t.name!}</option>
                                            </#list>
                                        </#if>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>图片类型:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="imageType" class="daq-select wid-198 fl mr-20">
                                        <select name="imageType" validate="{required:true}">
                                        <#if imageType??>
                                            <#list imageType as t>
                                                <option value="${t.id!}" <#if t.id == (dto.imageType)!>selected</#if>>${t.name!}</option>
                                            </#list>
                                        </#if>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>语言类型:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="languageType" class="daq-select wid-198 fl mr-20">
                                        <select name="languageType" validate="{required:true}">
                                        <#if languageType??>
                                            <#list languageType as t>
                                                <option value="${t.value!}"
                                                        <#if t.value == (dto.languageType)!''>selected</#if>>${t.name!}</option>
                                            </#list>
                                        </#if>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">简介:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule inp-textarea js-dynamic-box">
                                        <textarea name="introduction" rows="3"
                                                  validate="{maxlength:150}">${dto.introduction!}</textarea>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>排序:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="sort" validate="{required:true,range:[0,999]}"
                                               class="s_input w120 fom_inp" value="${dto.sort!'0'}"/>
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
    $(function () {
        // 单图片上传
        initImageUploader('imageUrlUploadBox', "imageUrl", "${dto.imageUrl!''}", 1, false);
        // 绑定下拉列表框
        bindSelect();
    });
    function bindSelect() {
        $("#scenicId").daqSelect({
            value: '',
            callback: function (data) {
            }
        });
        $("#imageType").daqSelect({
            value: '',
            callback: function (data) {
            }
        });
        $("#languageType").daqSelect({
            value: '',
            callback: function (data) {
            }
        });
    }
    // 单选按钮绑定样式
    $("#status").daqRadio();
</script>
</html>