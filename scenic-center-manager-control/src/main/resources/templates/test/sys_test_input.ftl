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
                            <td class="form-tab-label wid-198 "><label class="form-label"><i class="form-require">*</i>名称:</label></td>
                            <td>
                                <div class="form-table-edit clearfix">
                                    <div class="daq-inp-rule wid-358 fl">
                                        <input type="text" name="name" class="s_input s_input_fb w318" validate="{required:true,minlength:2,maxlength:20}" value="${dto.name!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>地区:</label></td>
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
                            <td class="form-tab-label"><label class="form-label">图片(带裁剪):</label></td>
                            <td>
                                <div class="form-table-edit clearfix" id="cropNews">
                                    <div>
                                        <div class="upload-default-box">
                                            <i class="sysfont upload-dft-icon">&#xe69c;</i>
                                            <p class="upload-tips">支持JPG和PNG格式图片</p>
                                        </div>
                                        <input id="imgCropper" name="imgCropper" type="hidden" validate="{required:true}">
                                        <button class="daq-btn btn-pad-52 media-btn mt-5" id="imageCropperUploadBox" type="button"><i class="sysfont mr-5"></i>点击上传</button>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">图片:</label></td>
                            <td>
                                <div class="form-table-edit clearfix">
                                    <div>
                                        <div class="upload-default-box">
                                            <i class="sysfont upload-dft-icon">&#xe69c;</i>
                                            <p class="upload-tips">支持JPG和PNG格式图片不超过2M</p>
                                        </div>
                                        <input id="img" name="img" type="hidden" validate="{required:true}">
                                        <input id="widthId" name="imgWidth" type="hidden" validate="">
                                        <input id="heightId" name="imgHeight" type="hidden" validate="">
                                        <button class="daq-btn btn-pad-52 media-btn mt-5" id="imageUploadBox" type="button"><i class="sysfont mr-5"></i>点击上传</button>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">图片集(多张):</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class=" source-type-box js-dynamic-box" id="imagesUploadBox">
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">音频:</label></td>
                            <td>
                                <div class="form-table-edit clearfix">
                                    <div>
                                    <#--文件路径-->
                                        <input id="audioNameBox" name="audioName" type="hidden" value="${dto.audioName!''}">
                                        <input id="audioPathBox" name="audioPath" type="hidden" value="${dto.audioPath!''}" validate="{required:true}">
                                    <#--文件大小-->
                                        <input id="audioSizeBox" name="audioSize" type="hidden" value="${dto.audioSize!''}">
                                        <div class="upload-default-box upload-media-box">
                                            <i class="sysfont upload-dft-icon">&#xe69a;</i>
                                            <p class="upload-tips">支持MP3和WAM格式音频不超过5M</p>
                                        </div>
                                        <button id="audioUploadBox" class="daq-btn btn-pad-52 mt-5 media-btn" type="button">
                                            <i class="sysfont mr-5">&#xe698;</i>点击上传
                                        </button>
                                    </div>
                                </div>
                            </td>
                        </tr>
                       <tr>
                            <td class="form-tab-label"><label class="form-label">视频:</label></td>
                            <td>
                                <div class="form-table-edit clearfix">
                                    <div>
                                    <!-- 文件名称、路径 -->
                                    <input id="videoNameBox" name="videoName" type="hidden" value="${dto.videoName!''}">
                                    <input id="videoPathBox" name="videoPath" type="hidden" value="${dto.videoPath!''}" validate="{required:true}">
                                    <!-- 文件大小 -->
                                    <input id="videoSizeBox" name="videoSize" type="hidden" value="${dto.videoSize!''}">
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
                            <td class="form-tab-label"><label class="form-label">附件:</label></td>
                            <td>
                                <div class="form-table-edit clearfix">
                                    <div>
                                    <input id="attachmentUploadPath" name="attachment" type="hidden"
                                           value="" validate="{required:true}">
                                    <div class="upload-default-box upload-media-box">
                                        <i class="sysfont upload-dft-icon">&#xe6ec;</i>
                                        <p class="upload-tips">支持TXT,ZIP,DOC,PDF等格式文件</p>
                                    </div>
                                    <button class="daq-btn btn-pad-52 mt-5 media-btn" id="attachmentUploadBox" type="button"><i class="sysfont mr-5">&#xe63b;</i>点击上传 </button>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">简介(测试多个富文本):</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="js-dynamic-box" >
                                    <textarea name="introduction" id="introduction"
                                              style="width: 100%; height: 250px">${(dto.introduction?html)!''}</textarea>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">详情:</label></td>
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
<script src="http://filealiyun.geeker.com.cn/ued/geekUI/js/cropper/cropper.min.js"></script><!-- 裁剪js -->
<script src="http://filealiyun.geeker.com.cn/ued/geekUI/js/swfobject.js"></script><!-- 引用视频播放插件 -->
<script src="http://filealiyun.geeker.com.cn/ued/geekUI/js/audio/jquery.jplayer.js"></script> <!-- 音频插件 -->
<script type="application/javascript">
    $(function() {
        // 单图片上传
        /**
         * imageUploadBox: 点击上传 id
         * img :隐藏域的id
         * <#--${dto.img!''}  回显值，图片路径-->
         * 1 ：上传数量，此方法支持单图上传，默认1
         * true ：启用裁剪功能， false ： 禁用裁剪功能
         * 2 :表示图片上传大小限制，如果不传，则默认是4*1024*1024 即4M，（注意：单位为 M）
         */
        initImageUploader('imageCropperUploadBox', "imgCropper", "", 1, true, 2);
        initImageUploader('imageUploadBox', "img", "${dto.img!''}", 1, false, 2, "widthId", "heightId");
        //多图上传(imagesUploadBox 资源选择盒子最外层DIV的ID，里面的选择元素会动态初始化;<#--${dto.images!}-->  默认值，逗号分隔；5：选择限制上传数量；images：生成的隐藏input的name；2 :表示每张图片上传大小限制，如果不传，则默认是4*1024*1024 即4M，（注意：单位为 M）)
        initMorePicUploader('imagesUploadBox', "${dto.images!''}", 5, "images", 2);
        //编辑器
        initKindEditor("content");
        initKindEditor("introduction");
        /**
         * 初始化文件上传盒子（本地上传）
         * @param pickerObj 文件上传盒子最外层DIV的ID，里面的上传元素会动态初始化。
         * @param urlValueBoxId 文件上传时，用来接收音频地址的输入框ID，这个输入框只保存当前盒子上传的文件。
         * @param sizeBox 上传文件时，用来接收文件大小的输入框ID，这个输入框只保存当前盒子上传的文件。
         * @param suffix 允许的文件后缀，允许多个,为''则表示允许所有
         * @param mimeTypes 类型  音频：audio/*   视频 video/*
         * @param defaultValue 默认文件值，即初始化上传组件时，一开始就显示的文件，允许多个（用逗号隔开）
         * @param title 文件名称
         * @param type 上传文件类型(音频、视频不传，通过mimeTypes判断是音频还是视频，如果是文件，则type需要传 file)
         * @param pathNameBox 文件上传时，用来接收音频名称的输入框ID，这个输入框只保存当前盒子上传的文件。
         * @param 100 文件上传时，控制文件上传大小的限制，如果不传，默认100M ，（注意单位为： M）
         */
        //音频
        initFileUploader('audioUploadBox', "audioPathBox", 'audioSizeBox', "mp3", "audio/*", "${dto.audioPath!}", "${dto.audioName!}", '', 'audioNameBox', 100);
        //视频
        initFileUploader('videoUploadBox', "videoPathBox", 'videoSizeBox', "mp4,flv", "video/*", "${dto.videoPath!''}", "${dto.videoName!''}",'','videoNameBox', 100);
        // 附件上传
        var suffix = 'txt,rar,zip,doc,xls,docx,xlsx,pdf,wav,mp3,mid,flv,pptx',
                mimeTypes = '';
        initFileUploader('attachmentUploadBox','attachmentUploadPath','', suffix, mimeTypes,'', '', 'file', '', 100) //第三个参数限制文件后缀类型，没填就不限制
        //地区三级显示
        $().ready(function () {
            var $areaSelect = $("#areaSelect");
            // 地区选择菜单
            $areaSelect.lSelect({
                url: "/permitted/ajaxRegion",// AJAX数据获取url
                parameter: "region"
            });

        });

        // 单选按钮绑定样式
        $("#status").daqRadio();
    });

</script>
</html>