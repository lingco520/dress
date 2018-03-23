<!DOCTYPE html>
<html>
<head>
<#include "../header.ftl">
<#include "../edit.ftl">
<#include "../upload.ftl">
<#include "../kindEditor.ftl">
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
                            <td class="form-tab-label wid-198 "><label class="form-label"><i class="form-require">*</i>商品名称:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="name" class="s_input s_input_fb w318" validate="{required:true,minlength:2,maxlength:20}" value="${dto.name!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                       <tr>
                            <td class="form-tab-label"><label class="form-label">来源:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="source" class="s_input s_input_fb w318" validate="{maxlength:100}" value="${dto.source!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    <tr>
                        <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>语言:</label></td>
                        <td>
                            <div class="form-table-edit">
                                <div id="languageType" class="daq-select wid-198 fl mr-20">
                                    <select name="languageType" validate="{required:true}">
                                    <#list languageType as t>
                                        <option value="${t.value!}" <#if t.value == (dto.languageType)!''>selected</#if>>${t.name!}</option>
                                    </#list>
                                    </select>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="form-tab-label"><label class="form-label">商品图片:</label></td>
                        <td>
                            <div class="form-table-edit clearfix" id="">
                                <div>
                                <div class="upload-default-box">
                                    <i class="sysfont upload-dft-icon">&#xe69c;</i>
                                    <p class="upload-tips">支持JPG和PNG格式图片不超过2M</p>
                                </div>
                                <input id="image" name="image" type="hidden">
                                <button class="daq-btn btn-pad-52 media-btn mt-5" id="imageUploadBox" type="button"><i class="sysfont mr-5"></i>点击上传</button>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="form-tab-label"><label class="form-label">内容:</label></td>
                        <td>
                            <div class="form-table-edit">
                                <div class="js-dynamic-box" >
                                <textarea name="content" id="content"
                                          style="width: 100%; height: 250px">${(dto.content?html)!''}</textarea>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="form-tab-label"><label class="form-label">摘要:</label></td>
                        <td>
                            <div class="form-table-edit">
                                <div class="daq-inp-rule inp-textarea js-dynamic-box">
                                    <textarea name="introduction" rows="3" maxlength="500">${dto.introduction!''}</textarea>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>排序:</label></td>
                        <td>
                            <div class="form-table-edit">
                                <div class="daq-inp-rule wid-158 fl">
                                    <input type="text" name="sort" validate="{required:true,range:[0,999]}" class="s_input w120 fom_inp" value="${dto.sort!'0'}"/>
                                </div>
                            </div>
                            <label style="margin-left: 10px;"><i class='sysfont tooltip' skin="dark"
                                                                 data-tips="注:排序规则:由低到高优先排序,例:1与2,1的优先级比2高" position="bottom-left" style="color:#9aa3b2">&#xe637;</i></label>
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
    $(function() {
        // 单图片上传
        initImageUploader('imageUploadBox', "image", "${dto.image!''}", 1, true);
        //编辑器
        initKindEditor("content");
    });
   $("#languageType").daqSelect({
        value: '${dto.languageType!'zh'}',
        callback: function (data) {
        }
    });
    $("#status").daqRadio();
</script>
</html>