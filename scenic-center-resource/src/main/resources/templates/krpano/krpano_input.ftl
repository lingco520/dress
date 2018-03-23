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
                            <td class="form-tab-label wid-198 "><label class="form-label"><i class="form-require">*</i>全景标题:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="text" id="title" name="title" class="s_input s_input_fb w318" validate="{required:true,minlength:2,maxlength:40}" value="${dto.title!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>相关景区:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="text" class="s_input s_input_fb w318" id="scenicName"  name="scenicName" validate="{required:true,minlength:2,maxlength:50}" value="${dto.scenicName!}">
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>URL链接:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="text" class="s_input s_input_fb w318" id="url"  name="url" validate="{required:true,minlength:2,maxlength:200}" value="${dto.url!}">
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>终端:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="terminalType" class="daq-select wid-198 fl mr-20">
                                        <select name="terminal" validate="{required:true}">
                                        <#list terminalType as t>
                                            <option value="${t.value!}" <#if t.value == (dto.terminal)!''>selected</#if>>${t.name!}</option>
                                        </#list>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">图片:</label></td>
                            <td>
                                <div class="form-table-edit" id="cropNews">
                                    <div class="upload-default-box">
                                        <i class="sysfont upload-dft-icon">&#xe69c;</i>
                                        <p class="upload-tips">支持JPG和PNG格式图片不超过2M</p>
                                    </div>
                                    <input id="img" name="img" type="hidden">
                                    <button class="daq-btn btn-pad-52 media-btn mt-5" id="imageUploadBox" type="button"><i class="sysfont mr-5"></i>点击上传</button>
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
        initImageUploader('imageUploadBox', "img", "${dto.img!''}", 1, true);
        //编辑器
        initKindEditor("content");
        bindSelect();
    });
    // 复选按钮绑定样式
    $("#languageCheckBox").daqCheckbox();
    // 单选按钮绑定样式
    $("#status").daqRadio();
    // 初始化日期选择
</script>
<script>
    function bindSelect() {
        // 终端类型下拉
        $("#terminalType").daqSelect({
            value: '${dto.terminalType!''}',
            callback: function (data) {
            }
        });
    }
</script>
</html>