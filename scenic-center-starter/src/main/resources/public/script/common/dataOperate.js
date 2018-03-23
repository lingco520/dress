//将错误信息的方法放到window里.方便用户重写.
window.customMethod = {
    errorPlacement : function (error, element) {
        error.appendTo(element.parents(".form-table-edit"));
    }
};
$(function () {
    /* ---------- Form validate ---------- */
    var $validateForm = $("#validateForm");
    var formSubmitted = false;
    if ($validateForm.length > 0) {
        $.metadata.setType("attr", "validate");
        console.log("binding validateForm ...");
        $validateForm.validate({
            //当验证成功时,调用此函数.
            submitHandler: function () {
                //如果表单验证成功,屏蔽掉提交按钮防止重复提交
                console.log("validate successfully");
                if (!formSubmitted) {
                    formSubmitted = true;
                    daqsoft.submit($validateForm);
                } else {
                    layer.msg("请勿重复提交!");
                }
            }, errorPlacement: window.customMethod.errorPlacement
        });
    }
    $(".submit").click(function () {
        console.log(" submiting form...");
        $("#validateForm").submit();
    });
    /*-- the button is to close opening panel --*/
    $(".return").click(function () {
        daqsoft.close(false,"cancel");
    });
});