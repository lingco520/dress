<!DOCTYPE html>
<html>
<head>
<#include "../header.ftl">
<#include "../admin.ftl">
</head>
<body>
<!--当前位置 面包导航-->
<div class="system-top">
    <p class="daq-crumb clearfix">
    </p>
</div>
<!--内容 S-->
<form id="listForm">
    <div class="section-content">
        <!--<h3 class="system-title">系统首页</h3>-->
        <div class="section-son-content source-pad">
            <div class="filter-condition clearfix mt-32">
                <!-- 事件名称 -->
                <div class="form-item fl mr-20">
                    <label class="form-label">名称：</label>
                    <div class="daq-inp-rule wid-198 fl">
                        <input type="text" value="" name="name" placeholder="名称"/>
                    </div>
                </div>
                <div class="mr-5 daq-inp-icon fl">
                    <button type ="button" class="search-button daq-btn">查询</button>
                </div>
                <!--操作按钮-->
                <div class="tool-bar btn-mg-5"></div>
            </div>
            <table id="listTable" cellpadding="0" cellspacing="0" width="100%" class="daq-table daq-tab-pad mt-20">

            </table>
        </div>
    </div>
</form>
</body>
<script src="http://filealiyun.geeker.com.cn/ued/geekUI/js/audio/jquery.jplayer.js"></script> <!-- 音频插件 -->
<script>
    var i = 1;
    $(function () {
        // 表格数据
        daqsoft.$listTable = $("#listTable").daqDataTable({
            url: "/sysTest/list",
            columns: [
                {
                    field: 'name',
                    title: '名称',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                }, {
                    field: 'audioPath',
                    title: '音频',
                    formatter: function (value) {
                        return getAudioDiv(value);
                    }
                }, {
                    field: 'videoPath',
                    title: '视频',
                    formatter: function (value, data) {
                        return getVideoDiv(value, data);
                    }
                },{
                    field: 'img',
                    title: '图片',
                    formatter: function (value) {
                        return getImageDiv(value);
                    }
                }, {
                    field: 'status',
                    title: '状态',
                    width: '60px',
                    formatter: function (value) {
                        if (value == 1){
                            return "<i class='sysfont tab-green'>正常</i>";
                        }
                        return "<i class='sysfont tab-red'>禁用</i>";
                    }
                }, {
                    field: 'id',
                    title: "操作",
                    width: '190px',
                    formatter: function (value, data) {
                        return "<div class='operation-bar' data=" + value + "></div>";
                    }
                }],
            limits: 3,
            pageText: true,
            checkbox: true,
            mixColor: true,
            callback: function (datas) {
                //注册权限按钮.
                //第一个参数为操作栏位置div,第二个为列表操作栏div
                operateRegister(".tool-bar", ".operation-bar");

                // 渲染完成，加载音频播放
                audioPlayer();
                $(".audio-preview").on('click', function (e) {
                    e.stopPropagation();
                    if ($(this).hasClass("play")) {
                        $(".audio-preview .sysfont").html("&#xe61d;");
                        $(this).find(".sysfont").html('<img src="../images/audio.gif" />');
                        $(this).siblings("div").jPlayer("play");
                    } else {
                        $(this).find(".sysfont").html("&#xe61d;");
                        $(this).siblings("div").jPlayer("pause");
                    }
                });
            }
        });

        // 视频播放
        $('.daq-table').on('click', '.video-preview', function (e) {
            e.stopPropagation();
            var $that = $(this),
                    url = $that.attr('url'),
                    cover = $that.attr('cover'),
                    name = $that.attr('name');
            $.daqVideo({
                mediaTitle: name, // 媒体名稱
                mediaUrl:url,   // 媒体地址
                mediaImg:cover,   // 媒体背景图片
                mediaHeight: 500, // 视频高度
                mediaWidth: 750, // 视频宽度
                swfUrl: "http://filealiyun.geeker.com.cn/ued/geekUI/media/flvplayer.swf"
            });
        })

        // 音频播放
        function audioPlayer() {
            var len = $(".jp-jplayer").length, url = '';
            for (var j = 1; j <= len; j++) {
                $('#jp-jplayer' + j).jPlayer({
                    ready: function (event) {
                        url = $(this).attr('data-url');
                        $(this).jPlayer("setMedia", {
                            title: "",
                            mp3: url
                        });
                    },
                    // cssSelectorAncestor: "#jp_container_1",
                    play: function () { // To avoid multiple jPlayers playing together.
                        $(this).jPlayer("pauseOthers");
                        $(this).siblings("div").removeClass("play").addClass("pause");
                    },
                    pause: function () {
                        $(this).siblings("div").removeClass("pause").addClass("play");
                        $(this).siblings('.audio-preview').find(".sysfont").html("&#xe61d;");
                    },
                    swfPath: "http://filealiyun.geeker.com.cn/ued/geekUI/js/audio/jquery.jplayer.swf",
                    supplied: "m4a, oga, mp3",
                    wmode: "window",
                    remainingDuration: true,
                    globalVolume: true,
                    useStateClassSkin: true,
                    autoBlur: false,
                    smoothPlayBar: true,
                    keyEnabled: true
                });
            }

        }

    });
</script>
</html>