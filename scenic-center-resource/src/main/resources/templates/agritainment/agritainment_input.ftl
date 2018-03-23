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
                            <td class="form-tab-label wid-198 "><label class="form-label"><i class="form-require">*</i>农家乐名称:</label>
                            </td>
                            <td>
                                <div class="form-table-edit clearfix">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="text" name="name" class="s_input s_input_fb w318"
                                               validate="{required:true,maxlength:20,minlength:2}"
                                               value="${dto.name!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>简称:</label></td>
                            <td>
                                <div class="form-table-edit clearfix">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="text" name="shortName" class="s_input s_input_fb w318"
                                               validate="{required:true,maxlength:20}" value="${dto.shortName!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <td class="form-tab-label"><label class="form-label">农家乐等级:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="level" class="daq-select wid-198 fl mr-20">
                                        <select name="level">
                                        <#if agritainmentLevel??>
                                            <#list agritainmentLevel as t>
                                                <option value="${t.value!}"
                                                        <#if t.value == (dto.level)!'agritainmentLevel_1'>selected</#if>>${t.name!}</option>
                                            </#list>
                                        </#if>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">许可证号:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158">
                                        <input type="text" name="license" validate="{maxlength:50}"
                                               class="s_input w120 fom_inp" value="${dto.license!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>详细地址:</label></td>
                            <td>
                                <div class="form-table-edit clearfix">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="address" validate="{required:true,maxlength:100}"
                                               class="s_input w120 fom_inp" value="${dto.address!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">EMAIL:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158">
                                        <input type="text" name="email" validate="{maxlength:20}"
                                               class="s_input w120 fom_inp" value="${dto.email!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <td class="form-tab-label"><label class="form-label">法人代表:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158">
                                        <input type="text" name="legalPerson" validate="{maxlength:20}"
                                               class="s_input w120 fom_inp" value="${dto.legalPerson!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">法人代表电话:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158">
                                        <input type="text" name="legalPhone" validate="{number:true,phone:true}"
                                               class="s_input w120 fom_inp" value="${dto.legalPhone!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">负责人:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158">
                                        <input type="text" name="preside" validate="{maxlength:20}"
                                               class="s_input w120 fom_inp" value="${dto.preside!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">负责人电话:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158">
                                        <input type="text" name="presidePhone" validate="{number:true,phone:true}"
                                               class="s_input w120 fom_inp" value="${dto.presidePhone!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">农家乐价格:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158">
                                        <input type="text" name="checkPrice"
                                               validate="{number: true,messages:{digits: '请输入数字'}}"
                                               class="s_input w120 fom_inp" value="${dto.checkPrice!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">房间数量:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158">
                                        <input type="text" name="roomCount" validate="{range:[0,999]}"
                                               class="s_input w120 fom_inp" value="${dto.roomCount!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">可容纳人数:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158">
                                        <input type="text" name="holdCount" validate="{range:[0,999]}"
                                               class="s_input w120 fom_inp" value="${dto.holdCount!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">雅间总数:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158">
                                        <input type="text" name="diningRoom" validate="{range:[0,999]}"
                                               class="s_input w120 fom_inp" value="${dto.diningRoom!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>行政区划:</label></td>
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
                    <#include "../gis_gd_map.ftl"/> <!-- 地图引用 -->
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>电话:</label></td>
                            <td>
                                <div class="form-table-edit clearfix">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="phone" validate="{required:true,phone:true}"
                                               class="s_input w120 fom_inp" value="${dto.phone!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">传真电话:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158">
                                        <input type="text" name="fax" validate="{number:true,phone:true}"
                                               class="s_input w120 fom_inp" value="${dto.fax!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">简介:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="js-dynamic-box">
                                        <textarea name="introduce" id="introduce"
                                                  style="width: 100%; height: 250px">${(dto.introduce?html)!''}</textarea>
                                    </div>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <td class="form-tab-label"><label class="form-label">相关图片:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="upload-default-box">
                                        <i class="sysfont upload-dft-icon">&#xe69c;</i>
                                        <p class="upload-tips">支持JPG和PNG格式图片不超过2M</p>
                                    </div>
                                    <input id="image" name="image" type="hidden">
                                    <button class="daq-btn btn-pad-52 mt-5 media-btn" id="imageUploadBoxImage"
                                            type="button">
                                        <i class="sysfont mr-5">&#xe697;</i>点击上传
                                    </button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">工商注册时间:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" readonly="readonly" name="registerDate" id="registerDate"
                                               class="s_input s_input_fb w281 js_tooltip" skin="dark" data-tips="请选择时间"
                                               position="bottom-left"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>排序:</label></td>
                            <td>
                                <div class="form-table-edit clearfix">
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
</body>
<script type="application/javascript">

    $("#level").daqSelect({
        value: '${dto.level!'agritainmentLevel_1'}',
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
    $(function () {
        initPlugins();
    });


    /**
     * 初始化插件
     * @see form-action.js
     */
    function initPlugins() {

        // 单图片上传（imageUploadBox：上传button的ID；image:图片上传隐藏域的id;回显默认值;限制数量,默认是1(此参数方法没有使用，通过样式控制的);true:是否可以裁剪)
        //电子地图
        initImageUploader('imageUploadBoxImage', "image", "${dto.image!}", 1, false);//徽标或商标

        //编辑器
        initKindEditor("introduce");
    }

    //时间选择器
    laydate.render({
        elem: '#registerDate'
        , type: 'date',
        value: '${(dto.registerDate?string('yyyy-MM-dd'))!}'
    });
    //一定要注意button上的data-tips和position属性一定要设置并且要按规律设置
    $('.js_tooltip').daqTips();


</script>
</html>