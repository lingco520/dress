<!DOCTYPE html>
<html>
<head>
    <!--优先使用 IE 最新版本和 Chrome-->
    <meta name="keywords" content="${title!''}">
    <meta name="description" content="${title!''}">
    <meta name="author" content="${title!''}">
    <title>${title!''}</title>
    <link rel="stylesheet" href="http://filealiyun.geeker.com.cn/ued/geekUI/css/frame.min.css"/>
<#include "header.ftl">
</head>
<body>
<!--头部head S-->
<div class="section-head section-head-v3 clearfix">
    <p class="fl head-v3-logo"><img src="/images/logo.png" alt="<@spring.message "framework.message.daqsoft_name" />"/></p>
    <ul class="fr head-user">
        <li><a href="/"><i class="sysfont head-user-icon">&#xe673;</i></a></li>
        <li><a href="javascript:" id="logout"><i class="sysfont head-user-icon">&#xe674;</i></a></li>
        <li class="head-user-li">
            <a href="javascript:void(0);"><i class="sysfont user-photo">&#xe66a;</i><span
                    class="head-user-name">${username}</span><!-- <i class="sysfont head-user-arr">&#xe6eb;</i> --></a>
            <#--<ul class="head-user-lst">-->
                <#--<li><a href="javascript:void(0);"><@spring.message "framework.message.profile" /></a></li>-->
            <#--</ul>-->
        </li>
    </ul>
</div>
<!--侧边导航及内容 E-->
<div class="body-container nav-w200 section-v3">
    <div class="fl nav-side-box">
        <p class=" side-close source-nav-close nav-side-close " data-close="0"><i class="sysfont">&#xe676;</i></p>
        <div id="js_nav" class="nav-side"></div>
    </div>
    <div class="clearfix section-main">
        <iframe class="section-iframe" id="main" src="${indexUrlLocation!}" name="main"
                style="width: 100%;height: 100%;"
                frameborder="0"></iframe>
    </div>
    <!--右侧内容栏 E-->
</div>
<!-- 切换站点 -->
<script type="text/template" id="website">
    <div class="pad20">
        <div class="website">
            <table id="table" width="100%"></table>
        </div>
    </div>
</script>
<script type="text/javascript">
    self != top && (top.location = self.location);

    (function ($, window) {
        $("#logout").click(function() {
            layer.confirm('<@spring.message "framework.message.logout-prompt" />', {icon: 3,btn: ['<@spring.message "framework.message.no" />','<@spring.message "framework.message.yes" />'],
                title:'<@spring.message "framework.message.system-reminder" />'},  function(index){
                layer.close(index);
            }, function(){
                window.location.href='/logout';
            });
        });
        var roleEmpty = ${ROLE_EMPTY?c};
        var lastLoginIp = '${USER_LAST_LOGIN_IP!}';
        if(roleEmpty) {
            layer.alert("<@spring.message "framework.user.empty-role" />");
        }else {
            $('#js_nav').daqNav({
                shrink: true,
                shrinkIcon: '&#xe675;',
                url: '/permitted/menuItem',
                callback: function (url, id) {
                    var main = $("#main");
                    main.attr("menuId", id);
                    main.attr("src", url);
                }
            });
        }
    })(jQuery, window)
</script>
</body>
</html>