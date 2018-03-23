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
                            <td class="form-tab-label wid-198 "><label class="form-label"><i class="form-require">*</i>美食名称:</label></td>
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
                            <td class="form-tab-label"><label class="form-label">浏览次数:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="scans" readonly class="s_input s_input_fb w281" value="${dto.scans!'0'}"/>
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
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>美食价格:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="checkPrice" class="s_input s_input_fb w281" validate="{required:true,digits: true,range:[0,10000],messages:{digits: '请输入0-10000非负整数'}}" value="${dto.checkPrice!'0'}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">美食图片:</label></td>
                            <td>
                                <div class="form-table-edit clearfix" id="cropNews">
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
                            <td class="form-tab-label"><label class="form-label">美食内容:</label></td>
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
<!-- 图片裁剪的DVI -->
<script type="text/template" id="cropperTmpl">
    <div class=" pd-20 clearfix">
        <div class="cropper-img fl">
            <img id="js_jcrop" src="" alt="" />
        </div>
        <div class="cropper-show">
            <div class="img-preview" style="width: 200px; height: 150px;"></div>
            <p class="mt-20 cropper-tips">选择裁剪比例</p>
            <p class="mt-10 cropper-btn">
                <button class="daq-btn-line btn-mini blue-line" data-method="setAspectRatio" data-option="1">1:1</button>
                <button class="daq-btn-line btn-mini blue-line btn-curr" data-method="setAspectRatio" data-option="1.3333333333">4:3</button>
                <button class="daq-btn-line btn-mini blue-line" data-method="setAspectRatio" data-option="1.7777777777">16:9</button>
            </p>
        </div>
    </div>
</script>
</body>
<script src="http://filealiyun.geeker.com.cn/ued/geekUI/js/cropper/cropper.min.js"></script>
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