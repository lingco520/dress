<!DOCTYPE html>
<html>
<head>
<#include "header.ftl">
<#include "edit.ftl">
</head>
<body>
<p class="demo-title"></p><!-- 这条线为了好看放这里 -->
<div class="form-content mt-32">
    <form id="forms" action="">
        <div class="form-item-table mt-22">
            <table cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab-label-color wid-248"><label class="form-label"><i
                            class="form-require">*</i>旧密码:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule fl wid-358">
                                <input type="password" name="opassword" class="s_input s_input_fb w318"
                                       validate="{required:true,maxlength:15,minlength:2}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="tab-label-color"><label class="form-label"><i class="form-require">*</i>新密码:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule fl wid-358">
                                <input type="password" name="password" id="password" class="s_input s_input_fb w318"
                                       validate="{required:true,maxlength:15,minlength:2}"/>
                                <em></em>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="tab-label-color"><label class="form-label"><i class="form-require">*</i>确认新密码:</label>
                    </td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule fl wid-358">
                                <input type="password" name="rePassword" class="s_input s_input_fb w318"
                                       validate="{required:true,equalTo:'#password'}"/>
                                <em></em>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="tab-label-color"></td>
                    <td>
                        <div class="form-table-edit">
                            <button type="submit" class="daq-btn">确定</button>
                            <button type="button" id="cannel" class="daq-btn">取消</button>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </form>
</div>
</body>
<script type="text/javascript">
    (function ($) {
        $(function () {
            var $validateForm = $("#forms");
            $.metadata.setType("attr", "validate");
            $validateForm.validate({
                //当验证成功时,调用此函数.
                submitHandler: function () {
                    var param = $validateForm.serialize();
                    var _csrf = document.cookie.split("=")[1];
                    $.ajax({
                        headers: {'X-XSRF-TOKEN': _csrf},
                        url: "/permitted/savepw",
                        type: "post",
                        data: param,
                        dataType: "json",
                        success: function (result) {
                            if (result.code == 0) {
                                layer.alert(result.message + ",点击确定重新登陆!", {
                                    closeBtn: 0
                                }, function () {
                                   top.location.href = "/logout";
                                });
                            } else {
                                layer.alert(result.message);
                            }
                        }
                    });
                }, errorPlacement: function (error, element) {
                    error.appendTo(element.parents(".form-table-edit"));
                }
            });
            $("#cannel").click(function () {
                var index = window.parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                window.parent.layer.close(index); //再执行关闭
            });
        });
    })(jQuery);
</script>
</html>