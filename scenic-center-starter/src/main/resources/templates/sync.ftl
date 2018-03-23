<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <!--优先使用 IE 最新版本和 Chrome-->
    <meta name="renderer" content="webkit"/>
    <meta name="author" content="大旗软件-用户同步"/>
    <title>大旗软件-用户同步</title>
    <link rel="stylesheet" href="http://filealiyun.geeker.com.cn/ued/geekUI/css/v1.1.0/daq-system.min.css"/>
</head>
<body>
    <div id="show"></div>
</body>
<script src="http://filealiyun.geeker.com.cn/ued/js/jquery-1.8.3.min.js"></script>
<script src="http://filealiyun.geeker.com.cn/ued/geekUI/js/v1.1.0/daq-system.min.js"></script>
<!--失败确认内容-->
<script type="text/template" id="context">
    <p class="mb-info">用户:'<span style="color: #ff0000;">${user!}</span>'不存在, <br>
        是否从单点登陆系统同步到本系统?</p>
</script>
<script>
    var text = $("#context");
    $(function() {
        $.daqDialog({
            title: '用户同步',
            content: text.html(),
            mask: true,
            width: 400,
            height: 250,
            model: 'fadeInDown',
            buttons: [
                {
                    text: '确定',
                    callback: function () {
                        sync(true);
                    }
                },
                {
                    text: '取消',
                    callback: function () {
                        sync(false);
                    }
                }
            ]
        });
    });
    function sync(flag) {
        $.get("/reg",{"flag":flag},function(data) {
            alert(data.message);
            if(data.code == 0) {
                window.location.href='/';
            }else {
                window.location.href='/logout'
            }
        });
    }
</script>
</html>