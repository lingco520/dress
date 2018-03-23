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
                <div class="form-item-table mt-22">
                    <table cellpadding="0" cellspacing="0" width="100%">
                        <tr>
                            <td class="form-tab-label"><label class="form-label">UED组件地址:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-600">
                                        <input type="text" class="s_input s_input_fb w318" readonly value="http://s.ued.daqsoft.com/geekUI/system/demo/index.html"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">封面图(单张):</label></td>
                            <td>
                                <div class="form-table-edit" id="cropNews">
                                    <div class="upload-default-box">
                                        <i class="sysfont upload-dft-icon">&#xe69c;</i>
                                        <p class="upload-tips">支持JPG和PNG格式图片不超过2M</p>
                                    </div>
                                    <input id="image" name="image" type="hidden">
                                    <button class="daq-btn btn-pad-52 mt-5 media-btn" id="imageUploadBox" type="button">
                                        <i class="sysfont mr-5">&#xe697;</i>点击上传
                                    </button>
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
                            <td class="form-tab-label"><label class="form-label">附件:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <input id="attachmentUploadPath" name="attachment" type="hidden"
                                           value="">
                                    <div class="upload-default-box upload-media-box">
                                        <i class="sysfont upload-dft-icon">&#xe6ec;</i>
                                        <p class="upload-tips">支持TXT,ZIP,DOC,PDF等格式文件</p>
                                    </div>
                                    <button class="daq-btn btn-pad-52 mt-5 media-btn" id="attachmentUploadBox" type="button"><i class="sysfont mr-5">&#xe63b;</i>点击上传 </button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">内容:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="js-dynamic-box" >
                                        <textarea name="content" id="content"
                                                  style="width: 100%; height: 250px">内容</textarea>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">摘要:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule inp-textarea js-dynamic-box">
                                        <textarea name="introduction" rows="3">摘要</textarea>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>行政区划:</label></td>
                            <td>
                                <div class="form-table-edit clearfix">
                                    <div class="fl">
                                        <input type="text" id="areaSelect" name="administrativeDivision" value="${dto.administrativeDivision!}"
                                               validate="{required:true,messages:{required:'请选择所在地区'}}"
                                               defaultSelectedPath="${regions!}"/>
                                    </div>
                                </div>
                            </td>
                            <td>PS:编辑页面回显数据绑定要检查</td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">开放时间:<a href="http://www.layui.com/laydate/">日期插件 laydate-> http://www.layui.com/laydate/</a></label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" readonly="readonly" name="openTime" id="openTime" class="s_input s_input_fb w281 js_tooltip" validate="{maxlength:8}" skin="dark" data-tips="请选择时间" position="bottom-left" value="${dto.openTime!}"/>
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
        initImageUploader('imageUploadBox', "image", "", 1, true);
        //多图上传(imagesUploadBox 资源选择盒子最外层DIV的ID，里面的选择元素会动态初始化;imageUrl：生成的隐藏input的name；defaultValue  默认值，逗号分隔；5：选择限制)
        initMorePicUploader('imagesUploadBox', "", 5, "imageUrl");

        //编辑器
        initKindEditor("content");

        // 附件上传
        var suffix = 'txt,rar,zip,doc,xls,docx,xlsx,pdf,wav,mp3,mid,flv,pptx',
                mimeTypes = '';
        initFileUploader('attachmentUploadBox',"attachmentUploadPath","", suffix, mimeTypes,"", "", "file") //第三个参数限制文件后缀类型，没填就不限制
    }

//地区三级显示
   $().ready(function () {
       var $areaSelect = $("#areaSelect");
       console.log($areaSelect);
       // 地区选择菜单
       $areaSelect.lSelect({
           url: "/permitted/ajaxRegion",// AJAX数据获取url
           parameter: "region"
       });

   })

   //时间选择器
   laydate.render({
       elem: '#openTime'
       ,type: 'datetime'
   });
   //一定要注意button上的data-tips和position属性一定要设置并且要按规律设置
   $('.js_tooltip').daqTips();

</script>
</html>