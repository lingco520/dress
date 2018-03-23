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
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>视频标题:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="title" validate="{required:true,maxlength:20}" class="s_input w120 fom_inp" value="${dto.title!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>所属类型名称:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="videoType" class="daq-select wid-198 fl mr-20">
                                        <select name="videoType">
                                            <#if mediaType??>
                                                <#list mediaType as m>
                                                    <option value="${m.id!}" <#if m.id == (dto.type)!>selected</#if>>${m.name!}</option>
                                                </#list>
                                            </#if>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>所属景点:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="scenic" class="daq-select wid-198 fl mr-20">
                                        <select name="scenicId">
                                            <#if scenic??>
                                                <#list scenic as s>
                                                    <option value="${s.id!}" <#if s.id == (dto.scenicId)!>selected</#if>>${s.name!}</option>
                                                </#list>
                                            </#if>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">封面图片:</label></td>
                            <td>
                                <div class="form-table-edit" id="cropNews">
                                    <div class="upload-default-box">
                                        <i class="sysfont upload-dft-icon">&#xe69c;</i>
                                        <p class="upload-tips">支持JPG和PNG格式图片不超过2M</p>
                                    </div>
                                    <input id="image" name="imageUrl" type="hidden">
                                    <button class="daq-btn btn-pad-52 mt-5 media-btn" id="imageUploadBox" type="button">
                                        <i class="sysfont mr-5">&#xe697;</i>点击上传
                                    </button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">视频:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <input id="videoUrl" name="videoUrl" type="hidden" value="${dto.videoUrl!''}">
                                    <div class="upload-default-box upload-media-box">
                                        <i class="sysfont upload-dft-icon">&#xe69b;</i>
                                        <p class="upload-tips">支持FLV和MP4格式视频不超过100M</p>
                                    </div>
                                    <button id="videoUploadBox" class="daq-btn btn-pad-52 mt-5 media-btn" type="button">
                                        <i class="sysfont mr-5">&#xe699;</i>点击上传
                                    </button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">简介内容:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule inp-textarea js-dynamic-box">
                                        <textarea name="content" rows="3" validate="{maxlength:200}">${dto.content!}</textarea>
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
                                            <#if languageType??>
                                                <#list languageType as t>
                                                    <option value="${t.value!}" <#if t.value == (dto.languageType)!''>selected</#if>>${t.name!}</option>
                                                </#list>
                                            </#if>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">排序:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="sort" validate="{required:true,digits: true,range:[0,999],messages:{digits: '请输入非负整数'}}" class="s_input w120 fom_inp" value="${dto.sort!'0'}" />
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
    $("#videoType").daqSelect({
        value: '${dto.type!}',
        callback: function (data) {
        }
    });
    $("#scenic").daqSelect({
        value: '${dto.scenicId!}',
        callback: function (data) {
        }
    });
    $("#languageType").daqSelect({
        value: '${dto.languageType!}',
        callback: function (data) {
        }
    });
    // 调用生成单选按钮方法
    $("#status").daqRadio();

    // DOM加载完成时执行的函数体
    $(function() {
        initPlugins();
    });
    /**
     * 初始化插件
     * @see form-action.js
     */
    function initPlugins() {

        // 单图片上传（imageUploadBox：上传button的ID；image:图片上传隐藏域的id;回显默认值;限制数量,默认是1(此参数方法没有使用，通过样式控制的);true:是否可以裁剪)
        initImageUploader('imageUploadBox', "image", "${dto.imageUrl!}", 1, true);//相关图片
        //视频
        initFileUploader('videoUploadBox', "videoUrl", '', "mp4,flv", "video/*", "${dto.videoUrl!''}", "",'','videoNameBox');

    }
</script>
</html>