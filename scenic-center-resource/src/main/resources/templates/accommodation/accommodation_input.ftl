<!DOCTYPE html>
<html>
<head>
<#include "../header.ftl">
<#include "../edit.ftl">
<#include "../kindEditor.ftl">
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
<body style="overflow-y: auto;">
<#--<div class="section-content">-->
    <#--<div class="section-son-content pad-bot0">-->
        <#--<p class="demo-title"></p><!-- 这条线为了好看放这里 &ndash;&gt;-->
        <#---->
    <#--</div>-->
<#--</div>-->
<div class="pd-15">
    <div class="form-content">
        <form id="validateForm" action="">
            <input type="hidden" name="id" value="${dto.id!}"/>
            <div class="form-item-table">
                <div id="firstTable">
                    <table cellpadding="0" cellspacing="0" width="100%">
                        <tr>
                            <td class="form-tab-label wid-198"><label class="form-label"><i class="form-require">*</i>住宿点名称:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="text" name="name" class="s_input s_input_fb w318" validate="{required:true,maxlength:25,minlength:2}" value="${dto.name!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>住宿点别名:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="text" name="zhName" class="s_input s_input_fb w318" validate="{required:true,maxlength:50}" value="${dto.zhName!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">住宿点英文名称:</label></td>
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
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>住宿点所在地:</label></td>
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
                            <td class="form-tab-label"><label class="form-label">酒店星级:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="touristHotelLevel" class="daq-select wid-198 fl mr-20">
                                        <select name="touristHotelLevel">
                                        <#list touristHotelLevel as t>
                                            <option value="${t.value!}" <#if t.value == (dto.touristHotelLevel)!'hotelLevel0'>selected</#if>>${t.name!}</option>
                                        </#list>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>酒店饭店类型:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="choose-box clearfix" id="acommodationTypeCheckBox">
                                    <#if acommodationType??>
                                        <#list acommodationType as t>
                                            <input type="checkbox" name="acommodationType" value="${(t.value)!''}"
                                                   title="${(t.name)!''}"
                                                   <#if (dto.acommodationType)?? && (dto.acommodationType?split(","))?seq_contains(t.value)>checked</#if>
                                            />
                                        </#list>
                                    </#if>
                                    </div>
                                </div>
                            </td>
                        </tr>


                        <tr>
                            <td class="form-tab-label"><label class="form-label">住宿点介绍:</label></td>
                            <td>
                                <div>
                                    <div class="js-dynamic-box" >
                                        <textarea name="content" id="content"
                                                  style="width: 100%; height: 250px" value="${dto.introduction!}"></textarea>
                                    </div>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <td class="form-tab-label"><label class="form-label">咨询电话:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="number" name="phone" validate="{maxlength:11}" class="s_input w120 fom_inp" value="${dto.phone!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">传真号:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="number" name="fax" validate="{maxlength:13}" class="s_input w120 fom_inp" value="${dto.fax!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">详细地址:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="text" name="address" validate="{maxlength:100}" class="s_input w120 fom_inp" value="${dto.address!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">网址:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="websiteUrl" validate="{maxlength:100}" class="s_input w120 fom_inp" value="${dto.websiteUrl!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">wap网址:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="wapUrl" validate="{maxlength:100}" class="s_input w120 fom_inp" value="${dto.wapUrl!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">微博账号:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="offocoalPublicSpace" validate="{maxlength:100}" class="s_input w120 fom_inp" value="${dto.offocoalPublicSpace!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">交通方式:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="text" name="trafficDescription" validate="{maxlength:200}" class="s_input w120 fom_inp" value="${dto.trafficDescription!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">客房总数:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="number" name="guestRoomNumber" validate="{maxlength:5}" class="s_input w120 fom_inp" value="${dto.guestRoomNumber!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">总床位数:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="number" name="totalBedNumber" validate="{maxlength:5}" class="s_input w120 fom_inp" value="${dto.totalBedNumber!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">大床位总数:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="number" name="bigbed" validate="{maxlength:5}" class="s_input w120 fom_inp" value="${dto.bigbed!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">标间总数:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="number" name="doubleRoom" validate="{maxlength:5}" class="s_input w120 fom_inp" value="${dto.doubleRoom!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">三人间总数:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="number" name="threePersonRoom" validate="{maxlength:5}" class="s_input w120 fom_inp" value="${dto.totalBedNumber!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">四人或以上房间总数:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="number" name="manyPersonRoom" validate="{maxlength:5}" class="s_input w120 fom_inp" value="${dto.manyPersonRoom!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">残疾人房间总数:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="number" name="disabledPersonRoom" validate="{maxlength:5}" class="s_input w120 fom_inp" value="${dto.disabledPersonRoom!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">营业时间:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="businessHours" validate="{maxlength:100}" class="s_input w120 fom_inp" value="${dto.businessHours!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">最低价格:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="number" name="checkPrice" validate="{maxlength:5}" class="s_input w120 fom_inp" value="${dto.checkPrice!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">最低价格时间:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="checkPriceTime" validate="{maxlength:100}" class="s_input w120 fom_inp" value="${dto.checkPriceTime!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">支付方式:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="payMethod" validate="{maxlength:100}" class="s_input w120 fom_inp" value="${dto.payMethod!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">最后一次装修时间:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="decorateTime" validate="{maxlength:100}" class="s_input w120 fom_inp" value="${dto.decorateTime!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">相关荣誉:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="text" name="honorQualification" validate="{maxlength:100}" class="s_input w120 fom_inp" value="${dto.honorQualification!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>


                        <tr >
                            <td class="form-tab-label"></td>
                            <td class="dq_dt_ltd" colspan="1">
                                <div class="form-table-edit">
                                    <input type="button" class="daq-btn btn-pad-30 media-btn mt-5" onclick="nextStep();" value="下一步"/>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="secondTable" class="none">
                    <table cellpadding="0" cellspacing="0" width="100%">
                        <tr>
                            <td class="form-tab-label"><label class="form-label">邮编:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="number" name="postcode" validate="{maxlength:6}" class="s_input w120 fom_inp" value="${dto.postcode!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">行政区:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="text" name="administrativeDivision" validate="{maxlength:100}" class="s_input w120 fom_inp" value="${dto.administrativeDivision!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <td class="form-tab-label"><label class="form-label">许可证号:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="text" name="license" validate="{maxlength:50}" class="s_input w120 fom_inp" value="${dto.license!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">工商注册时间:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="text" name="registerTime" validate="{maxlength:50}" class="s_input w120 fom_inp" value="${dto.registerTime!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <td class="form-tab-label"><label class="form-label">登记注册类型:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="registerType" class="daq-select wid-198 fl mr-20">
                                        <select name="registerType">
                                        <#list registerType as b>
                                            <option value="${b.value!}" <#if b.value == (dto.registerType)!'registerType1'>selected</#if>>${b.name!}</option>
                                        </#list>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <td class="form-tab-label"><label class="form-label">法人代表:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="text" name="legalPerson" validate="{maxlength:10}" class="s_input w120 fom_inp" value="${dto.legalPerson!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">法人代表电话:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="number" name="legalPhone" validate="{maxlength:11}" class="s_input w120 fom_inp" value="${dto.legalPhone!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">负责人:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="text" name="preside" validate="{maxlength:10}" class="s_input w120 fom_inp" value="${dto.preside!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">负责人电话:</label></td>
                            <td>
                            <div class="form-table-edit">
                                <div class="daq-inp-rule wid-358 fl">
                                    <input type="number" name="presidePhone" validate="{maxlength:11}" class="s_input w120 fom_inp" value="${dto.presidePhone!}"/>
                                </div>
                            </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">餐厅总数:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="number" name="resturantCount" validate="{maxlength:4}" class="s_input w120 fom_inp" value="${dto.resturantCount!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">中餐厅个数:</label></td>
                            <td>
                                <div class="form-table-edit">
                                <div class="daq-inp-rule wid-358 fl">
                                    <input type="number" name="chineseRestaurant" validate="{maxlength:3}" class="s_input w120 fom_inp" value="${dto.chineseRestaurant!}"/>
                                </div>
                            </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">西餐厅个数:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="number" name="wersternRestaurant" validate="{maxlength:3}" class="s_input w120 fom_inp" value="${dto.wersternRestaurant!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">多功能餐厅总数:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="number" name="datefrom" validate="{maxlength:3}" class="s_input w120 fom_inp" value="${dto.datefrom!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">会议相关服务介绍:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="text" name="faccilitiesServices" validate="{maxlength:200}" class="s_input w120 fom_inp" value="${dto.faccilitiesServices!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">餐厅相关服务:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="text" name="diningServices" validate="{maxlength:200}" class="s_input w120 fom_inp" value="${dto.diningServices!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">娱乐相关微服务:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="text" name="entertainmentServices" validate="{maxlength:200}" class="s_input w120 fom_inp" value="${dto.entertainmentServices!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">商务相关服务:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="text" name="businessServices" validate="{maxlength:200}" class="s_input w120 fom_inp" value="${dto.businessServices!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">客房相关设施及服务:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="text" name="roomServices" validate="{maxlength:200}" class="s_input w120 fom_inp" value="${dto.roomServices!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">停车场及周边情况:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="text" name="parkingInfomation" validate="{maxlength:200}" class="s_input w120 fom_inp" value="${dto.parkingInfomation!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <td class="form-tab-label"><label class="form-label">电子地图:</label></td>
                            <td>
                                <style>
                                    .pics-item-name{
                                        font-size:12px;
                                    }
                                    .form-fileload {
                                        position:relative;
                                    }
                                    .form-fileload  .error{
                                        position: absolute;
                                        bottom:11px;
                                        left:193px;
                                    }
                                </style>
                                <div class="form-table-edit form-fileload">
                                    <div class="upload-default-box mt-20">
                                        <i class="sysfont upload-dft-icon"></i>
                                        <p class="upload-tips">支持JPG和PNG格式图片不超过2M</p>
                                    </div>
                                    <input hidden="hidden" name="electronicMap" id="electronicMap" value="${dto.electronicMap!}"/>
                                    <button class="daq-btn btn-pad-52 media-btn mt-5" id="imageUploadelectronicMap" type="button"><i class="sysfont mr-5"></i>点击上传</button>

                                </div>
                            </td>
                        </tr>


                        <tr>
                            <td class="form-tab-label"><label class="form-label">徽标或商标图片:</label></td>
                            <td>
                                <style>
                                    .pics-item-name{
                                        font-size:12px;
                                    }
                                    .form-fileload {
                                        position:relative;
                                    }
                                    .form-fileload  .error{
                                        position: absolute;
                                        bottom:11px;
                                        left:193px;
                                    }
                                </style>
                                <div class="form-table-edit form-fileload">
                                    <div class="upload-default-box mt-20">
                                        <i class="sysfont upload-dft-icon"></i>
                                        <p class="upload-tips">支持JPG和PNG格式图片不超过2M</p>
                                    </div>
                                    <input hidden="hidden" name="corporate" id="corporate" value="${dto.corporate!}"/>
                                    <button class="daq-btn btn-pad-52 media-btn mt-5" id="imageUploadcorporate" type="button"><i class="sysfont mr-5"></i>点击上传</button>

                                </div>
                            </td>
                        </tr>

                        <tr>
                            <td class="form-tab-label"><label class="form-label">标志性相关（图片）:</label></td>
                            <td>
                                <style>
                                    .pics-item-name{
                                        font-size:12px;
                                    }
                                    .form-fileload {
                                        position:relative;
                                    }
                                    .form-fileload  .error{
                                        position: absolute;
                                        bottom:11px;
                                        left:193px;
                                    }
                                </style>
                                <div class="form-table-edit form-fileload">
                                    <div class="upload-default-box mt-20">
                                        <i class="sysfont upload-dft-icon"></i>
                                        <p class="upload-tips">支持JPG和PNG格式图片不超过2M</p>
                                    </div>
                                    <input hidden="hidden" name="sketchPicture" id="sketchPicture" value="${dto.sketchPicture!}"/>
                                    <button class="daq-btn btn-pad-52 media-btn mt-5" id="imageUploadsketchPicture" type="button"><i class="sysfont mr-5"></i>点击上传</button>
                                </div>
                            </td>
                        </tr>
                        <#--<tr>-->
                            <#--<td class="form-tab-label"><label class="form-label">标志性相关(多张):</label></td>-->
                            <#--<td>-->
                                <#--<div class="form-position">-->
                                    <#--<div class="upload-default-box mt-20">-->
                                        <#--<i class="sysfont upload-dft-icon"></i>-->
                                        <#--<p class="upload-tips">支持JPG和PNG格式单张图片不超过2M</p>-->
                                    <#--</div>-->
                                    <#--<div class=" source-type-box js-dynamic-box" id="imagesUploadBox">-->
                                    <#--</div>-->
                                <#--</div>-->
                            <#--</td>-->
                        <#--</tr>-->

                        <tr>
                            <td class="form-tab-label"><label class="form-label">相关图片（多图）:</label></td>
                            <td>
                                <style>
                                    .pics-item-name{
                                        font-size:12px;
                                    }
                                    .form-fileload {
                                        position:relative;
                                    }
                                    .form-fileload  .error{
                                        position: absolute;
                                        bottom:11px;
                                        left:193px;
                                    }
                                </style>
                                <div class="form-table-edit form-fileload">
                                    <div class="upload-default-box mt-20">
                                        <i class="sysfont upload-dft-icon"></i>
                                        <p class="upload-tips">支持JPG和PNG格式图片不超过2M</p>
                                    </div>
                                    <input hidden="hidden" name="pictureLibrary" id="pictureLibrary" value="${dto.pictureLibrary!}"/>
                                    <div class=" source-type-box js-dynamic-box" id="imageUploadpictureLibrary">
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <td class="form-tab-label"><label class="form-label">720全景图片:</label></td>
                            <td>
                                <style>
                                    .pics-item-name{
                                        font-size:12px;
                                    }
                                    .form-fileload {
                                        position:relative;
                                    }
                                    .form-fileload  .error{
                                        position: absolute;
                                        bottom:11px;
                                        left:193px;
                                    }
                                </style>
                                <div class="form-table-edit form-fileload">
                                    <div class="upload-default-box mt-20">
                                        <i class="sysfont upload-dft-icon"></i>
                                        <p class="upload-tips">支持JPG和PNG格式</p>
                                    </div>
                                    <a class="daq-btn btn-pad-52 media-btn mt-5" id="corporate" type="button">点击上传</a>
                                    <input hidden="hidden" name="sign720" id="sign720" value="${dto.sign720!}"/>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <td class="form-tab-label"><label class="form-label">相关视频:</label></td>
                            <td>
                                <div class="form-table-edit clearfix">
                                    <div>
                                        <!-- 文件名称、路径 -->
                                        <input id="videoPathBox" name="videoLibrary" type="hidden" value="${dto.videoLibrary!''}">
                                        <!-- 文件大小 -->
                                        <div class="upload-default-box upload-media-box">
                                            <i class="sysfont upload-dft-icon">&#xe69b;</i>
                                            <p class="upload-tips">支持FLV和MP4格式视频不超过100M</p>
                                        </div>
                                        <button id="videoUploadBox" class="daq-btn btn-pad-52 mt-5 media-btn" type="button">
                                            <i class="sysfont mr-5">&#xe699;</i>点击上传
                                        </button>
                                    </div>
                                </div>
                            </td>
                        </tr>




                        <tr>
                            <td class="form-tab-label"><label class="form-label">三维展示素材:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="text" name="virtualMaterial" validate="{maxlength:200}" class="s_input w120 fom_inp" value="${dto.virtualMaterial!}"/>
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

                        <tr>
                            <td class="form-tab-label"></td>
                            <td>
                                <div class="form-table-edit">
                                    <input type="button" id="btnSumit" class="daq-btn mr-10" onclick="lastStep()" value="上一步"/>
                                    <button type="button" class="daq-btn submit" >保存</button>
                                    <button type="button" class="daq-btn return">返回</button>
                                </div>
                            </td>
                        </tr>

                    </table>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
<script src="http://filealiyun.geeker.com.cn/ued/geekUI/js/cropper/cropper.min.js"></script>
<script type="application/javascript">

    $(function() {
        initPlugins();
    });

    function initPlugins() {
        // 单图片上传（imageUploadBox：上传button的ID；image:图片上传隐藏域的id;回显默认值;限制数量,默认是1(此参数方法没有使用，通过样式控制的);true:是否可以裁剪)

        initImageUploader('imageUploadelectronicMap', "electronicMap", "${dto.electronicMap!''}", 1, true);
        initImageUploader('imageUploadcorporate', "corporate", "${dto.corporate!''}", 1, true);
        initImageUploader('imageUploadsketchPicture', "sketchPicture", "${dto.sketchPicture!''}", 1, true);
        <#--initImageUploader('imageUploadpictureLibrary', "pictureLibrary", "${dto.pictureLibrary!''}", 1, true);-->
        //多图上传(imagesUploadBox 资源选择盒子最外层DIV的ID，里面的选择元素会动态初始化;imageUrl：生成的隐藏input的name；defaultValue  默认值，逗号分隔；5：选择限制)
//        initMorePicUploader('imagesUploadBox', "", 5, "imageUrl");
        initMorePicUploader('imageUploadpictureLibrary', "${dto.pictureLibrary!''}", 5, "pictureLibrary");
        //编辑器
        initKindEditor("content");

        initFileUploader('videoUploadBox', "videoPathBox", '', "mp4,flv", "video/*", "${dto.videoLibrary!''}", "",'','', 100);


    }

    //拿到对象
    function nextStep(){
        if($("form").valid()){
            $("#firstTable").addClass("none");
            $("#secondTable").removeClass('none').find("input").eq(0).focus();
        }
        $(".requireField").hide();
    }
    function lastStep(){
        $("#secondTable").addClass("none");
        $("#firstTable").removeClass("none").find("input").eq(0).focus();
    }

    // 调用生成单选按钮方法
    <#--$("#status").daqRadio();-->
    <#--// 实例化下拉选择器-->
    <#--$("#scenicType").daqSelect({-->
        <#--value: '${dto.scenicType!'scenicType_1'}',-->
        <#--callback: function (data) {-->
        <#--}-->
    <#--});-->
    <#--$("#scenicLevel").daqSelect({-->
        <#--value: '${dto.scenicLevel!'scenicLevel_1'}',-->
        <#--callback: function (data) {-->
        <#--}-->
    <#--});-->
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


    $("#touristHotelLevel").daqSelect({
        value: '${dto.touristHotelLevel!'hotelLevel0'}',
        callback: function (data) {
        }
    });
    $("#registerType").daqSelect({
        value: '${dto.registerType!'registerType1'}',
        callback: function (data) {
        }
    });    // 复选按钮绑定样式
    $("#acommodationTypeCheckBox").daqCheckbox();

</script>
</html>