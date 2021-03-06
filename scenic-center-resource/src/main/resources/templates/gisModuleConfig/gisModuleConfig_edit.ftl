<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
<#include "../header.ftl">
<#include "../edit.ftl">
<#include "../upload.ftl">
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
<body style="width: 600px;">
<div class="section-content">
    <div class="section-son-content pad-bot0">
        <p class="demo-title"></p><!-- 这条线为了好看放这里 -->
        <div class="form-content mt-32">
            <form id="validateForm" action="">
                <input type="hidden" name="id" value="${dto.id!}"/>
                <div class="form-item-table mt-22">
                    <table cellpadding="0" cellspacing="0" width="100%">
                        <tr>
                            <td class="form-tab-label wid-198 "><label class="form-label"><i class="form-require">*</i>模块名称:</label>
                            </td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="name" class="s_input s_input_fb w318"
                                               readonly="readonly"
                                               value="${dto.name!}"/>
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


    $("#status").daqRadio();
    // 等级下拉框
    $("#gisMapType").daqSelect({
        value: '${dto.mapTypeId!''}',
        callback: function (data) {
        }
    });

    // DOM加载完成时执行的函数体
    $(function () {
        initPlugins();
    });
    // 复选按钮绑定样式
    $("#gisModuleCheckBox").daqCheckbox();


    /**
     * 初始化插件
     * @see form-action.js
     */
    function initPlugins() {

        // 单图片上传（imageUploadBox：上传button的ID；image:图片上传隐藏域的id;回显默认值;限制数量,默认是1(此参数方法没有使用，通过样式控制的);true:是否可以裁剪)
        //电子地图
        initImageUploader('imageUploadBoxImage', "image", "${dto.image!}", 1, false);//徽标或商标


    }
</script>
</html>