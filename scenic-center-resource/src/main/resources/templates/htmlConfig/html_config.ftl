<!DOCTYPE html>
<html>
<head>
<#include "../header.ftl">
<#include "../edit.ftl">
</head>
<style>
    .form-item-table table .dq_dt_ltd{
        text-align: center;
    }
    .base-data-tit {
        width: 100%;
    }

    .base-data-tit h3 {
        font: bold 18px/50px 'Microsoft YaHei UI';
    }
</style>
<body>
<div class="system-top">
    <p class="daq-crumb clearfix">
        <a href="javascript:;" class=""><i class="sysfont"></i> 数据模拟</a>
        <i class="sysfont crumb-sign sysfont"></i>
        <a href="javascript:;" class=""><i class="sysfont"></i> 实时数据</a>
    </p>
</div>
<div class="section-content">
    <div class="section-son-content">
        <div class="form-content">
            <div class="base-data-tit">
                <h3>景区微件模拟配置</h3>
            </div>
            <form id="dataConfigForm" action="">
                <div class="form-item-table">
                    <div>
                        <table cellpadding="0" cellspacing="0" width="100%" id="table">
                            <!--实时人数开始-->

                            <tr>
                                <td class="form-tab-label"><label class="form-label"><i
                                        class="form-require">*</i>模块类型:</label></td>
                                <td>
                                    <div class="form-table-edit">
                                        <div class="choose-box clearfix" id="moduleSelectCheckBox">
                                    <#if moduleSelect??>
                                        <#list moduleSelect as t>
                                            <input type="checkbox" name="moduleSelect" checked class="moduleSelect" value="${(t.HKEY)!''}"
                                                   title="${(t.NAME)!''}"
                                                   <#if (dto.hkey)?? && (dto.hkey?split(","))?seq_contains(t.HKEY)>checked</#if>
                                            />
                                        </#list>
                                    </#if>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <!--实时人数结束-->
                            <tr>
                                <td class="dq_dt_ltd" colspan="15">
                                    <div class="form-table-edit">
                                        <input type="button" class="daq-btn btn-pad-30 media-btn mt-5 nextorlast" onclick="saveConfig()" value="保存"/>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</div>
</body>
<script src="http://filealiyun.geeker.com.cn/ued/geekUI/js/cropper/cropper.min.js"></script>
<script>
    $("#moduleSelectCheckBox").daqCheckbox();

    function saveConfig() {
        var params="";
        //获取选中数据并拼接
        $("input[name=moduleSelect]:checked").each(function () {
            params+= $(this).val()+",";
        })
        var _csrf = document.cookie.split("=")[1];
        $.ajax({
            headers: {'X-XSRF-TOKEN': _csrf},
            type: "post",
            url: "/htmlConfig/saveHtmlConfig",
            data: {'HKEY': params},
            traditional: true,
            dataType: "json",
            success: function (respMsg) {
                layer.alert(respMsg.message);
            }
        });
    }
</script>
</html>