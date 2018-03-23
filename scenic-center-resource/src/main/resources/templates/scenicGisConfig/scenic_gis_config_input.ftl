<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
<#include "../header.ftl">
<#include "../edit.ftl">
    <script type="text/javascript" src="/script/jQuery/jquery.lSelect.js"></script>
    <link rel="stylesheet" type="text/css" href="/script/color/css/jquery.bigcolorpicker.css" />
    <script type="text/javascript" src="/script/color/js/jquery.bigcolorpicker.min.js"></script>

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
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>地图名称:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="name" validate="{required:true,maxlength:20}" class="s_input w120 fom_inp" value="${dto.name!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>地图链接:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="url" validate="{required:true}" class="s_input w120 fom_inp" value="${dto.url!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>中心点经度:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="centerLongitude" validate="{required:true,number: true,maxlength:10}" class="s_input w120 fom_inp" value="${dto.centerLongitude!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>中心点纬度:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="centerLatitude" validate="{required:true,number: true,maxlength:10}" class="s_input w120 fom_inp" value="${dto.centerLatitude!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>缩放比例:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="mapScale" validate="{required:true,number:true,range:[0,99]}"  class="s_input w120 fom_inp" value="${dto.mapScale!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>底部距离:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="popupBottom" validate="{required:true,number:true,range:[0,99]}"  class="s_input w120 fom_inp" value="${dto.popupBottom!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">背景色:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158">
                                        <input type="text" class="s_input w120 fom_inp" id="c2" name="backgroundColor" value="${dto.backgroundColor!}">
                                    </div>
                                </div>
                                <input id="btn" class="daq-btn daq-btn-cic btn-mini"  value="选色" />
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


<script type="text/javascript">
    // 调用生成单选按钮方法
    $("#status").daqRadio();
    $(function(){
        $("#btn").bigColorpicker("c2");
    });
</script>
<style type="text/css">
    .demo p{padding-bottom:10px}
</style>
</html>