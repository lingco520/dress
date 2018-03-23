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
                            <td class="form-tab-label wid-198 "><label class="form-label"><i class="form-require">*</i>旅游场所全称:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="name" class="s_input s_input_fb w318" validate="{required:true,minlength:2,maxlength:40}" value="${dto.name!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label wid-198 "><label class="form-label">旅游场所简称:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="shortName" class="s_input s_input_fb w318" validate="{maxlength:40}" value="${dto.shortName!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">营业执照号码:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="bizlicense" class="s_input s_input_fb w318" validate="{maxlength:100}" value="${dto.bizlicense!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">车位:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="parkingLot" class="s_input s_input_fb w281" validate="{digits: true,range:[0,100000],messages:{digits: '请输入0-100000非负整数'}}" value="${dto.parkingLot!'0'}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">人均消费:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="consumption" class="s_input s_input_fb w281" validate="{digits: true,range:[0,100000],messages:{digits: '请输入0-100000非负整数'}}" value="${dto.consumption!'0'}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">企业工商注册全称:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="icname" class="s_input s_input_fb w318" validate="{maxlength:50}" value="${dto.icname!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">法人代表:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="legal" class="s_input s_input_fb w318" validate="{maxlength:50}" value="${dto.legal!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">许可证号:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="licenseno" class="s_input s_input_fb w318" validate="{maxlength:50}" value="${dto.licenseno!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">经营负责人:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="manager" class="s_input s_input_fb w318" validate="{maxlength:50}" value="${dto.manager!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">开放时间:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="opentime" class="s_input s_input_fb w318" validate="{maxlength:50}" value="${dto.opentime!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">电子邮箱:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="mailBox" class="s_input s_input_fb w318" validate="{maxlength:50}" value="${dto.mailBox!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">邮编:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="zipCode" class="s_input s_input_fb w318" validate="{maxlength:50}" value="${dto.zipCode!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    <#include "../gis_gd_map.ftl"/> <!-- 地图引用 -->
                        <tr>
                            <td class="form-tab-label"><label class="form-label">在线购票地址:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="zxticket" class="s_input s_input_fb w318" validate="{maxlength:50}" value="${dto.zxticket!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">企业个性标识图:</label></td>
                            <td>
                                <div class="form-table-edit clearfix" id="cropNews">
                                    <div>
                                    <div class="upload-default-box">
                                        <i class="sysfont upload-dft-icon">&#xe69c;</i>
                                        <p class="upload-tips">支持JPG和PNG格式图片不超过2M</p>
                                    </div>
                                    <input id="individualitylogo" name="individualitylogo" type="hidden">
                                    <button class="daq-btn btn-pad-52 media-btn mt-5" id="individualitylogoUploadBox" type="button"><i class="sysfont mr-5"></i>点击上传</button>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">企业标识大图:</label></td>
                            <td>
                                <div class="form-table-edit clearfix" id="cropNews">
                                    <div>
                                    <div class="upload-default-box">
                                        <i class="sysfont upload-dft-icon">&#xe69c;</i>
                                        <p class="upload-tips">支持JPG和PNG格式图片不超过2M</p>
                                    </div>
                                    <input id="logobig" name="logobig" type="hidden">
                                    <button class="daq-btn btn-pad-52 media-btn mt-5" id="logobigUploadBox" type="button"><i class="sysfont mr-5"></i>点击上传</button>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">企业标识小图:</label></td>
                            <td>
                                <div class="form-table-edit clearfix" id="cropNews">
                                    <div>
                                    <div class="upload-default-box">
                                        <i class="sysfont upload-dft-icon">&#xe69c;</i>
                                        <p class="upload-tips">支持JPG和PNG格式图片不超过2M</p>
                                    </div>
                                    <input id="logosmall" name="logosmall" type="hidden">
                                    <button class="daq-btn btn-pad-52 media-btn mt-5" id="logosmallUploadBox" type="button"><i class="sysfont mr-5"></i>点击上传</button>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">视频:</label></td>
                            <td>
                                <div class="form-table-edit clearfix">
                                    <div>
                                    <!-- 文件路径 -->
                                    <input id="videoPathBox" name="videoPath" type="hidden" value="${dto.videoPath!''}">
                                    <input id="videoNameBox" name="videoName" type="hidden" value="${dto.videoName!''}">
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
                            <td class="form-tab-label"><label class="form-label">内容:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="js-dynamic-box" >
                                    <textarea name="content" id="content"
                                              style="width: 100%; height: 250px">${(dto.content?html)!''}</textarea>
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
<!-- 图片裁剪的DVI -->
<script type="text/template" id="cropperTmpl">
    <div class=" pd-20 clearfix">
        <div class="cropper-img fl">
            <img id="js_jcrop" src="" alt="" />
        </div>
        <div class="cropper-show">
            <div class="img-preview" style="width: 200px; height: 150px;"></div>
            <p class="mt-20 cropper-tips">选择裁剪比例</p>
            <p class="mt-10 cropper-btn">
                <button class="daq-btn-line btn-mini blue-line" data-method="setAspectRatio" data-option="1">1:1</button>
                <button class="daq-btn-line btn-mini blue-line btn-curr" data-method="setAspectRatio" data-option="1.3333333333">4:3</button>
                <button class="daq-btn-line btn-mini blue-line" data-method="setAspectRatio" data-option="1.7777777777">16:9</button>
            </p>
        </div>
    </div>
</script>
</body>
<script src="http://filealiyun.geeker.com.cn/ued/geekUI/js/cropper/cropper.min.js"></script>
<script type="application/javascript">
    $(function() {
        // 单图片上传
        initImageUploader('individualitylogoUploadBox', "individualitylogo", "${dto.individualitylogo!''}", 1, true);
        initImageUploader('logobigUploadBox', "logobig", "${dto.logobig!''}", 1, true);
        initImageUploader('logosmallUploadBox', "logosmall", "${dto.logosmall!''}", 1, true);
        //视频
        initFileUploader('videoUploadBox', "videoPathBox", '', "mp4,flv", "video/*", "${dto.videoPath!''}", "${dto.videoName!''}",'','videoNameBox');
        //编辑器
        initKindEditor("content");
    });
    $("#status").daqRadio();
</script>
</html>