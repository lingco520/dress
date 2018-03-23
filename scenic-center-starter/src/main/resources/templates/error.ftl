<!doctype html>
<html>
<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="renderer" content="webkit" />
    <link rel="shortcut icon"  href="images/region/favicon"  type="image/x-icon"  />
    <title>提示页面</title>
    <style>
        body {background-color: #f6f1ee;}
        .error_wrap {position: absolute; left:50%; top:50%; margin-top:-250px; margin-left:-342px; text-align: center;}
        .error_text {text-align: center; font:normal 24px/30px 'Microsoft Yahei'; color: #606060;}
        .mat20 {margin-top:20px;}
        .mat50 {margin-top:50px;}
        .error_btn {float: left; display: block; width:198px; height:81px;}
        .btn_wrap {display: inline-block; *display: inline; *zoom:1; height: 81px; text-align: center; }
        /*.error_btn1 {background:url(./images/error/back_index.png); margin-right: 20px; }*/
        /*.error_btn2 {background:url(./images/error/back_hi.png); }*/
        .error_btn1:hover {background-position: 0 bottom;}
        .error_btn2:hover {background-position: 0 bottom;}
        .error_btn{text-align: center; font:normal 20px/60px 'Microsoft yahei'; color: #fff; text-decoration: none;}
    </style>
</head>
<body>
<div  style="margin-top:250px">
    <#--<img src="./images/error/404.png" alt="" />-->
    <p class="error_text mat20">抱歉，您查找的页面不存在，可能已被删除或转移</p>
    <#--<p class="error_text">请点击以下链接继续浏览网站其他信息！</p>-->
    <div class="btn_wrap mat50">
        <#--<a class="error_btn error_btn1" href="javascript:;" id="backhome">返回首页</a>-->
        <#--<a class="error_btn error_btn2" href="javascript:;" onclick="history.go(-1);history.go(-1).location.reload();">返回上一页</a>-->
    </div>
</div>
</body>
<script src="http://filealiyun.geeker.com.cn/ued/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
    var srcs = parent.$("#main_iframe").attr("src");
    if (typeof srcs != 'undefined') {
        $("#backhome").attr("href", "javascript:window.parent.location.reload();");
    } else {
        $("#backhome").attr("href", "index");
    }
</script>
</html>