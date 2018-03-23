<!DOCTYPE html>
<html>
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
                            <td class="form-tab-label wid-198 "><label class="form-label"><i class="form-require">*</i>景区名称:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="name" class="s_input s_input_fb w318" validate="{required:true,maxlength:20,minlength:2}" value="${dto.name!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">景区别名:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="alias" class="s_input s_input_fb w318" validate="{maxlength:50}" value="${dto.alias!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">英文名称:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="englishname" class="s_input s_input_fb w281" validate="{maxlength:50}" value="${dto.englishname!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">景区详细地址:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="address" validate="{maxlength:200}" class="s_input w120 fom_inp" value="${dto.address!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">景区介绍:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="js-dynamic-box" >
                                        <textarea name="introduce" id="content"
                                                  style="width: 100%; height: 250px" >${(dto.introduce?html)!''}</textarea>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <#include "../gis_gd_map.ftl"/>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">景区等级:</label></td>
                            <td>
                                <div class="form-table-edit">
                                        <select name="level">
                                            <option value="未评级" <#if (dto.level)! == '未评级'>selected</#if>>未评级</option>
                                            <option value="A级" <#if (dto.level)! == 'A级'>selected</#if>>A级</option>
                                            <option value="AA级" <#if (dto.level)! == 'AA级'>selected</#if>>AA级</option>
                                            <option value="AAA级" <#if (dto.level)! == 'AAA级'>selected</#if>>AAA级</option>
                                            <option value="AAAA级" <#if (dto.level)! == 'AAAA级'>selected</#if>>AAAA级</option>
                                            <option value="AAAAA级" <#if (dto.level)! == 'AAAAA级'>selected</#if>>AAAAA级</option>
                                        </select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">开放时间:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" readonly="readonly" name="openTime" id="openTime" class="s_input s_input_fb w281 js_tooltip" validate="{maxlength:8}" skin="dark" data-tips="请选择时间" position="bottom-left" value="${dto.openTime!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>景区咨询电话:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="phone" validate="{required:true,phone:true}" class="s_input w120 fom_inp" value="${dto.phone!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">景区传真电话:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="faxphone" validate="{number:true}"  class="s_input w120 fom_inp" value="${dto.faxphone!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>行政区划:</label></td>
                            <td>
                                <div class="form-table-edit clearfix">
                                    <div class="fl">
                                    <input type="text" id="areaSelect" name="region" value="${dto.region!}"  validate="{required:true,messages:{required:'请选择所在地区'}}"  defaultSelectedPath="${regions!}"/>
                                </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">建议游玩时间:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="playTime" validate="{number:true,range:[0,99]}"  class="s_input w120 fom_inp" value="${dto.playTime!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>景区最大承载量:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="maxquantity" validate="{required:true,digits: true,range:[0,999999],messages:{digits: '请输入非负整数'}}" class="s_input w120 fom_inp" value="${dto.maxquantity!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">景区类型:</label></td>
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
                            <td class="form-tab-label"><label class="form-label">略缩图:</label></td>
                            <td>
                                <div class="form-position" id="cropNews">
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
                            <td class="form-tab-label"><label class="form-label">景区门票价格:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="ticketprice" validate="{digits: true,range:[0,999],messages:{digits: '请输入非负整数'}}" class="s_input w120 fom_inp" value="${dto.ticketprice!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">景区相关荣誉:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="relatedtitles" validate="{maxlength:100}" class="s_input w120 fom_inp" value="${dto.relatedtitles!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">景区邮政编码:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="postcode" validate="{number:true}" class="s_input w120 fom_inp" value="${dto.postcode!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">景区电子地图:</label></td>
                            <td>
                                <div class="form-position" id="cropNews">
                                    <div class="upload-default-box">
                                        <i class="sysfont upload-dft-icon">&#xe69c;</i>
                                        <p class="upload-tips">支持JPG和PNG格式图片不超过2M</p>
                                    </div>
                                    <input id="electronimg" name="electronimg" type="hidden">
                                    <button class="daq-btn btn-pad-52 mt-5 media-btn" id="imageUploadBoxElectronimg" type="button">
                                        <i class="sysfont mr-5">&#xe697;</i>点击上传
                                    </button>
                                </div>
                            </td>
                        </tr>                        <tr>
                            <td class="form-tab-label"><label class="form-label">景区网站地址:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="scenicWeb" validate="{maxlength:300}" class="s_input w120 fom_inp" value="${dto.scenicWeb!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">景区微博地址:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="scenicWeibo" validate="{maxlength:300}" class="s_input w120 fom_inp" value="${dto.scenicWeibo!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">景区EMAIL:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="scenicEmail" validate="{maxlength:50}" class="s_input w120 fom_inp" value="${dto.scenicEmail!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">景区游乐设施:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="recreationFacility" validate="{maxlength:50}" class="s_input w120 fom_inp" value="${dto.recreationFacility!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">景区服务设施:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="serviceFacility" validate="{maxlength:50}" class="s_input w120 fom_inp" value="${dto.serviceFacility!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">景区特色活动:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="specialevents" validate="{maxlength:50}" class="s_input w120 fom_inp" value="${dto.specialevents!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">景区服务内容:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="js-dynamic-box" >
                                        <textarea name="serviceContent" id="serviceContent"
                                                  style="width: 100%; height: 250px" >${(dto.serviceContent?html)!''}</textarea>
                                    </div>
                                </div>
                            </td>
                        </tr>                        <tr>
                            <td class="form-tab-label"><label class="form-label">导游设备租金:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="guidedevicerental" validate="{digits: true,range:[0,999],messages:{digits: '请输入非负整数'}}" class="s_input w120 fom_inp" value="${dto.guidedevicerental!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">旅游路线:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="tourRoute" validate="{maxlength:200}"  class="s_input w120 fom_inp" value="${dto.tourRoute!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">注意事项:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="announcements" validate="{maxlength:200}"  class="s_input w120 fom_inp" value="${dto.announcements!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">景区游览示意图:</label></td>
                            <td>
                                <div class="form-position" id="cropNews">
                                    <div class="upload-default-box">
                                        <i class="sysfont upload-dft-icon">&#xe69c;</i>
                                        <p class="upload-tips">支持JPG和PNG格式图片不超过2M</p>
                                    </div>
                                    <input id="sketchMap" name="sketchMap" type="hidden">
                                    <button class="daq-btn btn-pad-52 mt-5 media-btn" id="imageUploadBoxSketchMap" type="button">
                                        <i class="sysfont mr-5">&#xe697;</i>点击上传
                                    </button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">景区标志图:</label></td>
                            <td>
                                <div class="form-position" id="cropNews">
                                    <div class="upload-default-box">
                                        <i class="sysfont upload-dft-icon">&#xe69c;</i>
                                        <p class="upload-tips">支持JPG和PNG格式图片不超过2M</p>
                                    </div>
                                    <input id="brandImg" name="brandImg" type="hidden">
                                    <button class="daq-btn btn-pad-52 mt-5 media-btn" id="imageUploadBoxBrandImg" type="button">
                                        <i class="sysfont mr-5">&#xe697;</i>点击上传
                                    </button>
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
    // 调用生成单选按钮方法
    $("#status").daqRadio();

    //地区
    $().ready(function () {
        var $areaSelect = $("#areaSelect");
        // 地区选择菜单
        $areaSelect.lSelect({
            url: "/permitted/ajaxRegion",// AJAX数据获取url
            parameter: "region"
        });
    })

    //时间选择器
    laydate.render({
        elem: '#openTime'
        ,type: 'time'
    });
    //一定要注意button上的data-tips和position属性一定要设置并且要按规律设置
    $('.js_tooltip').daqTips();


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
        initImageUploader('imageUploadBoxSketchPicture', "sketchPicture", "${dto.sketchPicture!}", 1, true);//略缩图
        initImageUploader('imageUploadBoxElectronimg', "electronimg", "${dto.electronimg!}", 1, true);//景区电子地图
        initImageUploader('imageUploadBoxSketchMap', "sketchMap", "${dto.sketchMap!}", 1, true);//景区游览示意图
        initImageUploader('imageUploadBoxBrandImg', "brandImg", "${dto.brandImg!}", 1, true);//景区标志图

        //编辑器
        initKindEditor("content");
        initKindEditor("serviceContent");
    }
</script>
</html>