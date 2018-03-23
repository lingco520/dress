<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <!--优先使用 IE 最新版本和 Chrome-->
    <meta name="renderer" content="webkit"/>
    <meta name="author" content="大旗软件-登录页面"/>
    <title>大旗软件-登录页面</title>
    <link rel="stylesheet" href="http://filealiyun.geeker.com.cn/ued/geekUI/css/v1.1.0/daq-system.min.css"/>
    <link rel="stylesheet" href="/stylesheets/login.css"/>
</head>
<body>
<div class="canvas-box">
    <canvas id="logBg" class="log-bg">你的浏览器不支持canvas</canvas>
    <div class="login-box">
        <img class="login-logo" src="/images/login/login-log.png" alt="旅游公共信息服务平台"/>
        <div class="log-message">
            <h3 class="log-tit">用户登录</h3>
            <input type="hidden"
                   id="csrf"
                   value="${_csrf.token}"/>
            <div class="log-pad">
                <div>
                    <span class="errorSpan">
                    </span>
                </div>
                <div class="daq-inp-rule log-inp wid-278 mt-10 clearfix">
                    <i class="sysfont">&#xe67e;</i>
                    <span class="log-inp-item">
                        <input type="text" id="username" placeholder="用户名"/>
                    </span>
                </div>
                <div class="daq-inp-rule log-inp wid-278 mt-10 clearfix">
                    <i class="sysfont">&#xe691;</i>
                    <span class="log-inp-item">
                        <input type="password" id="password" placeholder="密码"/>
                    </span>
                </div>
                <div class="login-button-wrap">
                    <button id="submit" class="log-btn">登录</button>
                </div>
                <div class="login-button-wrap">
                    <button class="log-btn" id="login">单点登陆</button>
                </div>
            </div>
        </div>
    </div>
    <!--<img class="log-ball ain_3d" src="images/ball.png" alt="球体" />-->
    <!--球体-->
    <div class="globe" id="ball"></div>
</div>

<script src="http://filealiyun.geeker.com.cn/ued/js/jquery-1.8.3.min.js"></script>
<script src="http://filealiyun.geeker.com.cn/ued/geekUI/js/v1.1.0/daq-system.min.js"></script>
<script src="/script/login/login.js"></script> <!--背景-->
<script src="/script/login/draw.js"></script> <!--旋转球体-->
<script>
    (function ($) {
        "use strict";
        //登录页面若在框架内，则跳出框架
        self != top && (top.location = self.location);
        $(function () {
            var $username = $("#username"), $password = $("#password"), $submit = $("#submit");
            // 提交表单验证,记住登录用户名
            $submit.click(function () {
                if ($username.val() == "") {
                    showErrorMessage("请输入您的用户名!");
                    $username.focus();
                    return false;
                }
                if ($password.val() == "") {
                    showErrorMessage("请输入您的密码!");
                    $password.focus();
                    return false;
                }
                var param = {
                    "username": $username.val(),
                    "password": $password.val(),
                    "${_csrf.parameterName}": $("#csrf").val()
                };
                $.ajax({
                    type: 'POST',
                    url: '/login',
                    data: param,
                    dataType: 'JSON',
                    success: function (data) {
                        if (data.message == "success") {
                            window.location.href = "/";
                        } else {
                            showErrorMessage(data.message);
                        }
                    },
                    error: function (XMLHttpRequest) {
                        var result = XMLHttpRequest.responseText;
                        var jsonResult = JSON.parse(result);
                        showErrorMessage(jsonResult.message);
                    }
                });
            });

            document.onkeydown = function (e) {
                emptyErrorMessage();
                var theEvent = e || window.event;
                var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
                if (code == 13) {
                    $("#submit").click();
                    return false;
                }
                return true;
            };
            function showErrorMessage(message) {
                $(".errorSpan").text(message);
            }

            function emptyErrorMessage() {
                $(".errorSpan").text("");
            }

            $('#rememberPwd').daqCheckbox();

            $(".daq-inp-rule input").clearText(".daq-inp-rule");
            // 球体
            function draw() {
                var ww = $(window).width();
                var rotate = new RotateBall(document.getElementById('ball'), 15, 15, ww / 2 - (ww * 0.01));
                rotate.start();
            }

            draw();
            $("#login").click(function () {
                window.location.href = "/login"
            });
        });

    })(jQuery)
</script>
</body>
</html>