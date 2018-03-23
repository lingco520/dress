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
                            <td class="form-tab-label wid-198 "><label class="form-label"><i class="form-require">*</i>道路名称:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="name" class="s_input s_input_fb w318" validate="{required:true,maxlength:50,minlength:2}" value="${dto.name!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>行政区划:</label></td>
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
                            <td class="form-tab-label wid-198 "><label class="form-label"><i class="form-require">*</i>开始点名称:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="startSection" class="s_input s_input_fb w318" validate="{required:true,maxlength:25,minlength:2}" value="${dto.startSection!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <td class="form-tab-label wid-198 "><label class="form-label">百度经纬度选择(开始):</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-78 ml-5 fl">
                                        <input class="startLongitude" name="startLongitude" type="text" placeholder="经度"
                                               value="${dto.startLongitude!''}" readonly>
                                    </div>
                                    <div class="daq-inp-rule wid-78 ml-5 fl">
                                        <input class="startLatitude" name="startLatitude" type="text" placeholder="纬度"
                                               value="${dto.startLatitude!''}" readonly>
                                    </div>
                                    <button id="mbBox9" class="daq-btn-line blue-line ml-5"
                                            onclick="getLongAndLat('startLongitude','startLatitude')" type="button">标注
                                    </button>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <td class="form-tab-label wid-198 "><label class="form-label"><i class="form-require">*</i>结束点名称:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="endSection" class="s_input s_input_fb w318" validate="{required:true,maxlength:25,minlength:2}" value="${dto.startSection!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <td class="form-tab-label wid-198 "><label class="form-label">百度经纬度选择(结束):</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-78 ml-5 fl">
                                        <input class="endLongitude" name="endLongitude" type="text" placeholder="经度"
                                               value="${dto.endLongitude!''}" readonly>
                                    </div>
                                    <div class="daq-inp-rule wid-78 ml-5 fl">
                                        <input class="endLatitude" name="endLatitude" type="text" placeholder="纬度"
                                               value="${dto.endLatitude!''}" readonly>
                                    </div>
                                    <button id="mbBox10" class="daq-btn-line blue-line ml-5"
                                            onclick="getLongAndLat('endLongitude','endLatitude')" type="button">标注
                                    </button>
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
<#include "../common_coordinate.ftl"><!-- 地图引用 -->


</body>
<script src="http://filealiyun.geeker.com.cn/ued/geekUI/js/cropper/cropper.min.js"></script>


<script type="application/javascript">

    $().ready(function () {
        var $areaSelect = $("#areaSelect");
        // 地区选择菜单
        $areaSelect.lSelect({
            url: "/permitted/ajaxRegion",// AJAX数据获取url
            parameter: "region"
        });

    })

    //一定要注意button上的data-tips和position属性一定要设置并且要按规律设置
    $('.js_tooltip').daqTips();




</script>
</html>