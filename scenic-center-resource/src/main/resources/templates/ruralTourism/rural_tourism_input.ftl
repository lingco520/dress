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
                            <td class="form-tab-label wid-198 "><label class="form-label"><i class="form-require">*</i>乡村旅游名称:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="name" class="s_input s_input_fb w318" validate="{required:true,maxlength:20,minlength:2}" value="${dto.name!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>简称:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="shortName" class="s_input s_input_fb w318" validate="{required:true,maxlength:20}" value="${dto.shortName!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">电子邮件:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158">
                                        <input type="text" name="email" validate="{maxlength:20}" class="s_input w120 fom_inp" value="${dto.email!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <td class="form-tab-label"><label class="form-label">乡村旅游类型:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="ruralTourismType" class="daq-select wid-198 fl mr-20">
                                        <select name="ruralTourismType">
                                        <#if ruralTourismType??>
                                            <#list ruralTourismType as t>
                                                    <option value="${t.value!}" <#if t.value == (dto.ruralTourismType)!'ruralTourismType_1'>selected</#if>>${t.name!}</option>
                                            </#list>
                                        </#if>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">乡村旅游等级:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="ruralTourismLevel" class="daq-select wid-198 fl mr-20">
                                        <select name="ruralTourismLevel">
                                        <#if ruralTourismLevel??>
                                            <#list ruralTourismLevel as t>
                                                    <option value="${t.value!}" <#if t.value == (dto.ruralTourismLevel)!'ruralTourismLevel_1'>selected</#if>>${t.name!}</option>
                                            </#list>
                                        </#if>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">腾讯微博:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158">
                                        <input type="text" name="tencentWeibo" validate="{maxlength:100}" class="s_input w120 fom_inp" value="${dto.tencentWeibo!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">新浪微博:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158">
                                        <input type="text" name="sinaWeibo" validate="{maxlength:20}" class="s_input w120 fom_inp" value="${dto.sinaWeibo!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">微信:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158">
                                        <input type="text" name="weixin" validate="{maxlength:20}" class="s_input w120 fom_inp" value="${dto.weixin!}"/>
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
                        <#include "../map.ftl"/>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">对应商标:</label></td>
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
                            <td class="form-tab-label"><label class="form-label">LOGO:</label></td>
                            <td>
                                <div class="form-table-edit" id="cropNews">
                                    <div class="upload-default-box">
                                        <i class="sysfont upload-dft-icon">&#xe69c;</i>
                                        <p class="upload-tips">支持JPG和PNG格式图片不超过2M</p>
                                    </div>
                                    <input id="logo" name="logo" type="hidden">
                                    <button class="daq-btn btn-pad-52 mt-5 media-btn" id="imageUploadBoxLogo" type="button">
                                        <i class="sysfont mr-5">&#xe697;</i>点击上传
                                    </button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">视频:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <input id="video" name="video" type="hidden" value="${dto.video!''}">
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
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>联系电话:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158">
                                        <input type="text" name="phone" validate="{required:true,number:true}" class="s_input w120 fom_inp" value="${dto.phone!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">传真电话:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158">
                                        <input type="text" name="fax" validate="{number:true}" class="s_input w120 fom_inp" value="${dto.fax!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">网站地址:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158">
                                        <input type="text" name="webSite" validate="{maxlength:200}" class="s_input w120 fom_inp" value="${dto.webSite!}" />
                                    </div>
                                </div>
                            </td>
                        </tr>


                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>详细地址:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158">
                                        <input type="text" name="address" validate="{required:true,maxlength:100}" class="s_input w120 fom_inp" value="${dto.address!}" />
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>排序:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158">
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
    $("#ruralTourismType").daqSelect({
        value: '${dto.ruralTourismType!'ruralTourismType_1'}',
        callback: function (data) {
        }
    });
    $("#ruralTourismLevel").daqSelect({
        value: '${dto.ruralTourismLevel!'ruralTourismLevel_1'}',
        callback: function (data) {
        }
    });

    $("#status").daqRadio();
    $().ready(function () {
        var $areaSelect = $("#areaSelect");
        console.log($areaSelect);
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
        //电子地图
        initImageUploader('imageUploadBoxLogo', "logo", "${dto.logo!}", 1, false);//对应商标
        initImageUploader('imageUploadBoxCorporate', "corporate", "${dto.corporate!}", 1, false);//徽标或商标
        //视频
        initFileUploader('videoUploadBox', "video", '', "mp4,flv", "video/*", "${dto.video!''}", "",'','videoNameBox');

        //编辑器
        initKindEditor("introduction");
    }
</script>
</html>