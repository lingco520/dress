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
                            <td class="form-tab-label wid-198 "><label class="form-label"><i class="form-require">*</i>景点名称:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="name" class="s_input s_input_fb w318" validate="{required:true,maxlength:20,minlength:2}" value="${dto.name!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>景点别名:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="zhName" class="s_input s_input_fb w318" validate="{required:true,maxlength:50}" value="${dto.zhName!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">英文名称:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="usName" class="s_input s_input_fb w281" validate="{maxlength:50}" value="${dto.usName!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">关键字:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="keyWord" validate="{maxlength:50}" class="s_input w120 fom_inp" value="${dto.keyWord!}"/>
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
                            <td class="form-tab-label"><label class="form-label">景点类型:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="scenicType" class="daq-select wid-198 fl mr-20">
                                        <select name="scenicType">
                                            <#if scenicType??>
                                        <#list scenicType as t>
                                            <option value="${t.value!}" <#if t.value == (dto.scenicType)!'scenicType_1'>selected</#if>>${t.name!}</option>
                                        </#list>
                                            </#if>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">景区等级:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <select name="scenicLevel">
                                        <option value="未评级" <#if (dto.scenicLevel)! == '未评级'>selected</#if>>未评级</option>
                                        <option value="A级" <#if (dto.scenicLevel)! == 'A级'>selected</#if>>A级</option>
                                        <option value="AA级" <#if (dto.scenicLevel)! == 'AA级'>selected</#if>>AA级</option>
                                        <option value="AAA级" <#if (dto.scenicLevel)! == 'AAA级'>selected</#if>>AAA级</option>
                                        <option value="AAAA级" <#if (dto.scenicLevel)! == 'AAAA级'>selected</#if>>AAAA级</option>
                                        <option value="AAAAA级" <#if (dto.scenicLevel)! == 'AAAAA级'>selected</#if>>AAAAA级</option>
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">介绍:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="js-dynamic-box" >
                                        <textarea name="introduction" id="introduction"
                                                  style="width: 100%; height: 250px" >${(dto.introduction?html)!''}</textarea>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>景点最大承载量:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="maxquantity" validate="{required:true,digits: true,range:[0,999999],messages:{digits: '请输入非负整数'}}" class="s_input w120 fom_inp" value="${dto.maxquantity!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">门票价格:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="ticketPrice" validate="{digits: true,range:[0,999],messages:{digits: '请输入非负整数'}}" class="s_input w120 fom_inp" value="${dto.ticketPrice!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">门票打折:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="ticketDescription" validate="{maxlength:30}" class="s_input w120 fom_inp" value="${dto.ticketDescription!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">景点导游词:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="tourGuideWord" validate="{maxlength:200}" class="s_input w120 fom_inp" value="${dto.tourGuideWord!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <#include "../gis_gd_map.ftl"/>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">景点详细地址:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="scenicAddress" validate="{maxlength:100}" class="s_input w120 fom_inp" value="${dto.scenicAddress!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">景点电子地图地址:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="electronicMap" validate="{maxlength:100}" class="s_input w120 fom_inp" value="${dto.electronicMap!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">交通方式:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="trafficDescription" validate="{maxlength:100}" class="s_input w120 fom_inp" value="${dto.trafficDescription!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">标志性建筑:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="surroundingLandmark" validate="{maxlength:100}" class="s_input w120 fom_inp" value="${dto.surroundingLandmark!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">开放时间:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="businessHours" validate="{maxlength:100}" class="s_input w120 fom_inp" value="${dto.businessHours!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">游览时间:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="longTour" validate="{maxlength:100}" class="s_input w120 fom_inp" value="${dto.longTour!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">适合游览的时间:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="recommendedTravelTime" validate="{maxlength:100}" class="s_input w120 fom_inp" value="${dto.recommendedTravelTime!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">景点内特色介绍:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="characteristicLandscape" validate="{maxlength:300}" class="s_input w120 fom_inp" value="${dto.characteristicLandscape!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">景区游览示意图:</label></td>
                            <td>
                                <div class="form-table-edit" id="cropNews">
                                    <div class="upload-default-box">
                                        <i class="sysfont upload-dft-icon">&#xe69c;</i>
                                        <p class="upload-tips">支持JPG和PNG格式图片不超过2M</p>
                                    </div>
                                    <input id="tourMap" name="tourMap" type="hidden">
                                    <button class="daq-btn btn-pad-52 mt-5 media-btn" id="imageUploadBoxTourMap" type="button">
                                        <i class="sysfont mr-5">&#xe697;</i>点击上传
                                    </button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">徽标或商标:</label></td>
                            <td>
                                <div class="form-table-edit" id="cropNews">
                                    <div class="upload-default-box">
                                        <i class="sysfont upload-dft-icon">&#xe69c;</i>
                                        <p class="upload-tips">支持JPG和PNG格式图片不超过2M</p>
                                    </div>
                                    <input id="corporate" name="corporate" type="hidden">
                                    <button class="daq-btn btn-pad-52 mt-5 media-btn" id="imageUploadBoxCorporate" type="button">
                                        <i class="sysfont mr-5">&#xe697;</i>点击上传
                                    </button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">略缩图:</label></td>
                            <td>
                                <div class="form-table-edit" id="cropNews">
                                    <div class="upload-default-box">
                                        <i class="sysfont upload-dft-icon">&#xe69c;</i>
                                        <p class="upload-tips">支持JPG和PNG格式图片不超过2M</p>
                                    </div>
                                    <input id="sketchPicture" name="sketchPicture" type="hidden">
                                    <button class="daq-btn btn-pad-52 mt-5 media-btn" id="imageUploadBoxSketchPicture" type="button">
                                        <i class="sysfont mr-5">&#xe697;</i>点击上传
                                    </button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">相关图片:</label></td>
                            <td>
                                <div class="form-table-edit" id="cropNews">
                                    <div class="upload-default-box">
                                        <i class="sysfont upload-dft-icon">&#xe69c;</i>
                                        <p class="upload-tips">支持JPG和PNG格式图片不超过2M</p>
                                    </div>
                                    <input id="image" name="pictureLibrary" type="hidden">
                                    <button class="daq-btn btn-pad-52 mt-5 media-btn" id="imageUploadBox" type="button">
                                        <i class="sysfont mr-5">&#xe697;</i>点击上传
                                    </button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">相关视频:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <input id="videoLibrary" name="videoLibrary" type="hidden" value="${dto.videoLibrary!''}">
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
                            <td class="form-tab-label"><label class="form-label">三维展示素材:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="virtualMaterial" validate="{maxlength:200}" class="s_input w120 fom_inp" value="${dto.virtualMaterial!}"/>
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
</body>
<script type="application/javascript">
    $("#scenicType").daqSelect({
        value: '${dto.scenicType!'scenicType_1'}',
        callback: function (data) {
        }
    });
    $("#status").daqRadio();
    $().ready(function () {
        var $areaSelect = $("#areaSelect");
        // 地区选择菜单
        $areaSelect.lSelect({
            url: "/permitted/ajaxRegion",// AJAX数据获取url
            parameter: "region"
        });

    })

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
        initImageUploader('imageUploadBox', "image", "${dto.pictureLibrary!}", 1, true);//相关图片
        initImageUploader('imageUploadBoxSketchPicture', "sketchPicture", "${dto.sketchPicture!}", 1, true);//略缩图
        initImageUploader('imageUploadBoxCorporate', "corporate", "${dto.corporate!}", 1, true);//徽标或商标
        initImageUploader('imageUploadBoxTourMap', "tourMap", "${dto.tourMap!}", 1, true);//景区游览示意图

        //视频
        initFileUploader('videoUploadBox', "videoLibrary", '', "mp4,flv", "video/*", "${dto.videoLibrary!''}", "",'','videoNameBox');

        //编辑器
        initKindEditor("introduction");
    }
    // 编辑器内容绑定
    $(".submit").click(function() {
        $("#introduction").val(editor.html());
    });
</script>
</html>