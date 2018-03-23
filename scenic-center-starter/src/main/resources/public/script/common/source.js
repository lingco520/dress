
// 计算contents高度
function sourceWH () {
    var contWid = $(window).outerWidth(),
        tabHeight = $(".system-top").outerHeight(),
        winHeight = $(window).outerHeight(), // 获取iframe中子页面的窗体高度
        headHeight = $(".section-head", parent.document).outerHeight(); // 获取iframe中子页面的窗体高度
    $(".section-content").height(winHeight - headHeight - tabHeight - 10);
    // $(".section-son-content").height(winHeight - headHeight - tabHeight - 90);
}
$(function () {
    sourceWH();
})
$(window).resize(function () {
    sourceWH();
})