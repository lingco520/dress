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
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>旅行社名称:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="name" validate="{required:true,maxlength:20}"
                                               class="s_input w120 fom_inp" value="${dto.name!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">略缩名称:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="shortName" validate="{maxlength:20}"
                                               class="s_input w120 fom_inp" value="${dto.shortName!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">许可证号:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="licenseno" validate="{maxlength:50}"
                                               class="s_input w120 fom_inp" value="${dto.licenseno!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">电子邮箱:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="email" validate="{maxlength:50}"
                                               class="s_input w120 fom_inp" value="${dto.email!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">腾讯微博:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="qqWeibo" validate="{maxlength:50}"
                                               class="s_input w120 fom_inp" value="${dto.qqWeibo!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">新浪微博:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="sinaWeibo" validate="{maxlength:50}"
                                               class="s_input w120 fom_inp" value="${dto.sinaWeibo!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">微信名称:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="wechat" validate="{maxlength:20}"
                                               class="s_input w120 fom_inp" value="${dto.wechat!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>所属地区:</label></td>
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
                            <td class="form-tab-label"><label class="form-label">Logo图片:</label></td>
                            <td>
                                <div class="form-position" id="cropNews">
                                    <div class="upload-default-box">
                                        <i class="sysfont upload-dft-icon">&#xe69c;</i>
                                        <p class="upload-tips">支持JPG和PNG格式图片不超过2M</p>
                                    </div>
                                    <input id="logo" name="logo" type="hidden">
                                    <button class="daq-btn btn-pad-52 mt-5 media-btn" id="imageUploadBoxLogo"
                                            type="button">
                                        <i class="sysfont mr-5">&#xe697;</i>点击上传
                                    </button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">宣传视频:</label></td>
                            <td>
                                <div class="form-position" id="cropNews">
                                    <div class="upload-default-box">
                                        <i class="sysfont upload-dft-icon">&#xe69c;</i>
                                        <p class="upload-tips">支持wav,mp3,flv,avi格式视频不超过2M</p>
                                    </div>
                                    <input id="video" name="video" type="hidden">
                                    <button class="daq-btn btn-pad-52 mt-5 media-btn" id="imageUploadBoxVideo" type="button">
                                        <i class="sysfont mr-5">&#xe697;</i>点击上传
                                    </button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>详细地址:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="address" validate="{required:true,maxlength:50}"
                                               class="s_input w120 fom_inp" value="${dto.address!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>联系电话:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="phone" validate="{required:true,phone:true}"
                                               class="s_input w120 fom_inp" value="${dto.phone!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">传真:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="fax" validate="{number:true,maxlength:11}"
                                               class="s_input w120 fom_inp" value="${dto.fax!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>旅行社类型:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="choose-box clearfix" id="travelType">
                                        <#if travelType??>
                                    <#list travelType as t>
                                        <input type="checkbox" name="travelType" validate="{required:true,messages:{required:'请选择旅行社类型'}}" value="${(t.value)!''}"
                                               title="${(t.name)!''}"
                                               <#if (dto.travelType)?? && (dto.travelType?split(","))?seq_contains(t.value)>checked</#if>/>
                                    </#list>
                                        </#if>
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
<script src="http://filealiyun.geeker.com.cn/ued/geekUI/js/cropper/cropper.min.js"></script>
<script type="application/javascript">
    // 复选按钮绑定样式
    $("#travelType").daqCheckbox();
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
        initImageUploader('imageUploadBoxLogo', "logo", "${dto.logo!}", 1, true);//略缩图
// 附件上传(视频上传)
        var suffix = 'wav,mp4,flv,avi',
                mimeTypes = '';
        initFileUploader('imageUploadBoxVideo', "video", "${dto.video!}", suffix, mimeTypes, "", "", "file") //第三个参数限制文件后缀类型，没填就不限制

    }
</script>
</html>