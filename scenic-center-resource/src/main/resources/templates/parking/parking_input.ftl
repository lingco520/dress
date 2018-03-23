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
                            <td class="form-tab-label wid-198 "><label class="form-label"><i class="form-require">*</i>停车场名称:</label>
                            </td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="name" class="s_input s_input_fb w318"
                                               validate="{required:true,minlength:2,maxlength:30}"
                                               value="${dto.name!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>停车场类型:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="parkingType" class="daq-select wid-198 fl mr-20">
                                        <select name="parkingType" validate="{required:true}">
                                        <#if parkingType??>
                                            <#list parkingType as t>
                                                <option value="${t.value!}"
                                                        <#if t.value == (dto.parkingType)!''>selected</#if>>${t.name!}</option>
                                            </#list>
                                        </#if>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    <#include "../gis_gd_map.ftl"/> <!-- 地图引用 -->
                        <tr>
                            <td class="form-tab-label wid-198 "><label class="form-label">停车场详细地址:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-300 fl">
                                        <input id="address" type="text" name="address" value="${dto.address!''}"
                                               placeholder="请输入" maxlength="200">
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">车位:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="parkingLot" class="s_input s_input_fb w281"
                                               validate="{digits: true,range:[0,100000],messages:{digits: '请输入0-100000非负整数'}}"
                                               value="${dto.parkingLot!'0'}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">开放时间:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="opentime" class="s_input s_input_fb w281"
                                               validate="{maxlength:30}" value="${dto.opentime!''}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">停车计费方式和费用:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="chargeStandard" class="s_input s_input_fb w281"
                                               validate="{maxlength:50}" value="${dto.chargeStandard!}"/>
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
                            <td class="form-tab-label"><label class="form-label">内容:</label></td>
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
        //编辑器
        initKindEditor("content");
        // 绑定下拉列表框
        bindSelect();
    });
    // 单选按钮绑定样式
    $("#status").daqRadio();
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
        $("#parkingType").daqSelect({
            value: '${dto.parkingType!''}',
            callback: function (data) {
            }
        });
    }
</script>
</html>