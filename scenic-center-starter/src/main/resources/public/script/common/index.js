
var winHost = window.location.href,
    mark = winHost.substr(winHost.lastIndexOf("#") + 1);
// 页面加载完循环遍历li定位默认选中项
window.onload = function () {
    $(".nav-left li").each(function (index, value) {
        var dataMark = $(this).attr("data-mark");
        if (dataMark == mark) {
            // 判断如果当前的li还有父级li则，需要出发父级li的点击事件以展开点击事件
            if ($(this).parents("li").length > 0) {
                $(this).parents("li").trigger("click");
            }
            $(this).trigger("click");
        }
    });
};
// 调用导航条方法
$(".nav-left").navMenu({
    callback: function () {
        // 点击一级列表页获取href地址复制给iframe修改内容
        $(".nav-left > li").on("click", function () {
            // 判断没有下级列表页则修改iframe内容，否则则显示下级列表
            if ($(this).children(".nav-lst").length < 1) {
                var iframeUrl = $(this).children("a").attr("href");
                changeIframeUrl(iframeUrl);
                changeHash(winHost, iframeUrl);
            }
        });
        // 点击二级列表页获取href地址复制给iframe修改内容
        $(".nav-lst > li").on("click", function () {
            // 判断没有下级列表页则修改iframe内容，否则则显示下级列表
            if ($(this).children(".nav-son-lst").length < 1) {
                var iframeUrl = $(this).children("a").attr("href");
                changeIframeUrl(iframeUrl);
                changeHash(winHost, iframeUrl);
            }
        });
        // 点击三级列表页获取href地址复制给iframe修改内容
        $(".nav-son-lst > li").on("click", function () {
            var iframeUrl = $(this).children("a").attr("href");
            changeIframeUrl(iframeUrl);
            changeHash(winHost, iframeUrl);
        });
    }
});
function changeIframeUrl (url) {
    $('#indIframe').attr('src', url);
}

// 判断有收起nav导航栏的按钮
if ($(".side-close").length && $(".side-close").length > 0) {
    if ($('.source-nav-close').length && $('.source-nav-close').length > 0) {
        sideClose({
            icon: '&#xe675;'
        });
    } else {
        sideClose();
    }
}
// 框架二中tab事件
if ($(".head-nav-lst li").length && $(".head-nav-lst li").length > 0) {
    headTab({
        tabClose: function () {
            console.log("asd");
        },
        tabChange: function (data) {
            console.log(data);
        }
    });
}
/*
 * 判断当前页面地址上是否有hash值，若有则清楚hash值，并重新添加hash值以此来定位选中的内容
 * @param winUrl:当前页面的url地址
 * @param url:iframe调转的链接地址
 * */
function changeHash (winUrl, iframeUrl) {
    var newUrl = iframeUrl.substr(iframeUrl.lastIndexOf("/") + 1);
    if (winUrl.indexOf("#") < 0) {
        window.location.href = winUrl + '#' + newUrl;
    } else {
        // 清楚url地址上的hash，然后重新添加
        var noHash = winUrl.substr(0, winUrl.lastIndexOf("#"));
        window.location.href = noHash + '#' + newUrl;
    }
}

/*
 * 框架二中头部的tab切换nav
 * @params obj:tab的ul容器
 * @params tabClose:tab关闭按钮回调
 * @params tabChange:tab点击切换显示回调
 * */
function headTab (options) {
    var defaults = {
        obj: '.head-nav-lst',
        tabClose: function () {},
        tabChange: function () {}
    };
    var setting = $.extend(defaults, options);
    var tabNum = $(setting.obj).find("li").length;
    // 给关闭按钮添加hover事件
    $(".head-nav-close").on('mouseenter mouseleave', function (event) {
        if (event.type == 'mouseenter') {
            $(this).html("&#xe622;");
        } else if (event.type == 'mouseleave') {
            $(this).html("&#xe6e5;");
        }
    });
    // 给关闭按钮添加点击事件
    $(".head-nav-close").on('click', function () {
        var $that = $(this),
            currIndex = $that.index(),
            tabTotal = $(setting.obj).find("li").length;
        // 移除当前tab
        $that.parents("li").remove();
        $.isFunction(setting.tabClose) && setting.tabClose();
        if ($that.parents('li').hasClass('head-nav-curr')) {
            if (currIndex == (tabTotal - 1)) { // 判断当前关闭的是最后一个tab，则选种样式为前一个
                currIndex--;
            }
            $(setting.obj).find('li').eq(currIndex).addClass('head-nav-curr').siblings('li').removeClass('head-nav-curr');
        }
    });
    // 绑定点击切换样式事件
    $(setting.obj).on("click", 'li', function () {
        var index = $(this).index();
        $(this).addClass('head-nav-curr').siblings('li').removeClass('head-nav-curr');
        $.isFunction(setting.tabChange) && setting.tabChange(index);
    });
}
/*
 * 框架中缩放按钮事件
 * @params obj:缩放按钮
 * @params logo:展开状态的logo
 * @params smallLogo:收起来状态的logo
 * @params icon:收起来状态的按钮字体图标编码
 * */
function sideClose (options) {
    var defaults = {
        obj: '.side-close',
        logo: 'images/logo.png',
        smallLogo: 'images/logo-small.png',
        icon: '&#xe670;'
    };
    var setting = $.extend(defaults, options);
    var oldIcon = $(setting.obj).find('.sysfont').html();
    $(setting.obj).on("click", function () {
        var $that = $(this),
            closeNum = $that.attr("data-close");
        if (closeNum == 0) {
            // 移除滚动条超出自动样式
            $(".nav-left").removeClass('nav-overflow').addClass('nav-close');
            // 关闭所有子栏
            $(".nav-lst").stop().hide();
            // 改变自身图标
            $(this).attr("data-close", "1").find('.sysfont').html(setting.icon);
            // 改变logo图标
            $(".logo-side img").attr("src", setting.smallLogo);
            // 改变导航宽度
            $(".body-container").addClass("nav-w56");
            // 隐藏部分元素
            // 遍历一级li并对其进行操作
            $(".nav-left > li").each(function () {
                var $that = $(this);
                $(".nav-prompt").stop().hide();
                $that.addClass("nav-contract").find(".nav-text").addClass("nav-text-none");
                $that.children("a").find(".nav-arrow").stop().fadeOut(200);
            });
        } else {
            // 添加滚动条超出自动
            $(".nav-left").addClass('nav-overflow').removeClass('nav-close');
            // 改变自身图标
            $(this).attr("data-close", "0").find('.sysfont').html(oldIcon);
            // 改变logo图标
            $(".logo-side img").attr("src", setting.logo);
            // 改变导航宽度
            $(".body-container").removeClass("nav-w56");
            // 遍历一级li并对其进行操作,隐藏部分元素
            $(".nav-left > li").each(function () {
                var $that = $(this);
                $(".nav-prompt").stop().show();
                $that.removeClass("nav-contract").find(".nav-text").removeClass("nav-text-none");
                $that.children("a").find(".nav-arrow").stop().fadeIn(200);
            });
        }
    });
}

// 点击切换站点出现弹框
$('.head-website').on('click', function () {
    /* 位置标注 */
    var content =  $("#website");
    $.daqDialog({
        title: '切换站点',
        iconfont: '&#xe687;',
        content: content.html(),
        mask: true,
        width: 660,
        height: 460,
        model:'fadeInDown',
        callback: function () {
            // 实例表格
            var table = $('#table').daqDataTable({
                url:"./js/mock/website.json",
                columns: [
                    {
                        field: 'websiteName',
                        title: '站点名称',
                        className: ''
                    },
                    {
                        field: 'url',
                        title: '站点URL',
                        className: ''
                    },
                    {
                        field: 'eventName',
                        title: '操作',
                        className: '',
                        formatter: function (value) {
                            return '<p class="tab-operate">' +
                                '<a class="daq-tab-btn blue-line js_editor" href="javascript:;">切换到该站点</a>'+
                                '</p>';
                        }
                    }],
                pagination: false
            });
        }
    })
})