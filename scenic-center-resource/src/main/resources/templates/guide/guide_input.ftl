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
                            <td class="form-tab-label wid-198 "><label class="form-label"><i class="form-require">*</i>导游名称:</label>
                            </td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="name" class="s_input s_input_fb w318"
                                               validate="{required:true,minlength:2,maxlength:20}"
                                               value="${dto.name!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">出生日期:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-date mt-20">
                                        <input type="text" id="birthday" name="birthday">
                                        <i class="sysfont date-icon"></i>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>导游等级:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="guideLevel" class="daq-select wid-198 fl mr-20">
                                        <select name="guideLevel" validate="{required:true}">
                                        <#if guideLevel??>


                                            <#list guideLevel as t>
                                                <option value="${t.value!}"
                                                        <#if t.value == (dto.guideLevel)!''>selected</#if>>${t.name!}</option>
                                            </#list>
                                        </#if>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>导游性别:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="gender" class="daq-select wid-198 fl mr-20">
                                        <select name="gender" validate="{required:true}">
                                        <#if gender??>
                                            <#list gender as t>
                                                <option value="${t.value!}"
                                                        <#if t.value == (dto.gender)!''>selected</#if>>${t.name!}</option>
                                            </#list>
                                        </#if>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>导游学历:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="education" class="daq-select wid-198 fl mr-20">
                                        <select name="education" validate="{required:true}">
                                        <#if education??>
                                            <#list education as t>
                                                <option value="${t.value!}"
                                                        <#if t.value == (dto.education)!''>selected</#if>>${t.name!}</option>
                                            </#list>
                                        </#if>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>民族:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="nationality" class="daq-select wid-198 fl mr-20">
                                        <select name="nationality" validate="{required:true}">
                                        <#if nationality??>
                                            <#list nationality as t>
                                                <option value="${t.value!}"
                                                        <#if t.value == (dto.nationality)!''>selected</#if>>${t.name!}</option>
                                            </#list>
                                        </#if>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">导游照片:</label></td>
                            <td>
                                <div class="form-table-edit clearfix" id="cropNews">
                                    <div>
                                    <div class="upload-default-box">
                                        <i class="sysfont upload-dft-icon">&#xe69c;</i>
                                        <p class="upload-tips">支持JPG和PNG格式图片不超过2M</p>
                                    </div>
                                    <input id="image" name="image" type="hidden">
                                    <button class="daq-btn btn-pad-52 media-btn mt-5" id="imageUploadBox" type="button">
                                        <i class="sysfont mr-5"></i>点击上传
                                    </button>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">导游编号:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="identification" class="s_input s_input_fb w318"
                                               validate="{maxlength:50}" value="${dto.identification!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">所属公司:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="company" class="s_input s_input_fb w318"
                                               validate="{maxlength:50}" value="${dto.company!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>语种:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="choose-box clearfix" id="languageCheckBox">
                                    <#if language??>
                                        <#list language as t>
                                            <input type="checkbox" name="language" value="${(t.value)!''}"
                                                   title="${(t.name)!''}"
                                                   <#if (dto.language)?? && (dto.language?split(","))?seq_contains(t.value)>checked</#if>
                                            />
                                        </#list>
                                    </#if>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>地区:</label></td>
                            <td>
                                <div class="form-table-edit clearfix">
                                    <div class="fl">
                                        <input type="text" id="areaSelect" name="region" value="${dto.region!}"
                                               validate="{required:true,messages:{required:'请选择所在地区'}}"
                                               defaultSelectedPath="${regions!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">身份证号:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="idcard" class="s_input s_input_fb w318"
                                               validate="{maxlength:30}" value="${dto.idcard!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">导游介绍:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="js-dynamic-box">
                                    <textarea name="content" id="content"
                                              style="width: 100%; height: 250px">${(dto.content?html)!''}</textarea>
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
<!-- 图片裁剪的DVI -->
<script type="text/template" id="cropperTmpl">
    <div class=" pd-20 clearfix">
        <div class="cropper-img fl">
            <img id="js_jcrop" src="" alt=""/>
        </div>
        <div class="cropper-show">
            <div class="img-preview" style="width: 200px; height: 150px;"></div>
            <p class="mt-20 cropper-tips">选择裁剪比例</p>
            <p class="mt-10 cropper-btn">
                <button class="daq-btn-line btn-mini blue-line" data-method="setAspectRatio" data-option="1">1:1
                </button>
                <button class="daq-btn-line btn-mini blue-line btn-curr" data-method="setAspectRatio"
                        data-option="1.3333333333">4:3
                </button>
                <button class="daq-btn-line btn-mini blue-line" data-method="setAspectRatio" data-option="1.7777777777">
                    16:9
                </button>
            </p>
        </div>
    </div>
</script>
</body>
<script src="http://filealiyun.geeker.com.cn/ued/geekUI/js/cropper/cropper.min.js"></script>
<script type="application/javascript">
    $(function () {
        // 单图片上传
        initImageUploader('imageUploadBox', "image", "${dto.image!''}", 1, false);
        //编辑器
        initKindEditor("content");
        // 绑定下拉列表框
        bindSelect();
    });
    // 复选按钮绑定样式
    $("#languageCheckBox").daqCheckbox();
    // 单选按钮绑定样式
    $("#status").daqRadio();
    // 初始化日期选择
    laydate.render({
        elem: '#birthday',
        type: 'date',
        value: '${(dto.birthday?string('yyyy-MM-dd'))!}'
    });
    //地区三级显示
    $().ready(function () {
        var $areaSelect = $("#areaSelect");
        // 地区选择菜单
        $areaSelect.lSelect({
            url: "/permitted/ajaxRegion",// AJAX数据获取url
            parameter: "region"
        });

    })

    function bindSelect() {
        // 初始化性别下拉框
        $("#gender").daqSelect({
            value: '${dto.gender!''}',
            callback: function (data) {
            }
        });
        // 等级下拉框
        $("#guideLevel").daqSelect({
            value: '${dto.guideLevel!''}',
            callback: function (data) {
            }
        });
        // 学历下拉框
        $("#education").daqSelect({
            value: '${dto.education!''}',
            callback: function (data) {
            }
        });
        // 民族下拉框
        $("#nationality").daqSelect({
            value: '${dto.nationality!''}',
            callback: function (data) {
            }
        });
    }
</script>
</html>