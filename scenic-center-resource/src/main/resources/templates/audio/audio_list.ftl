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
                    <label class="form-label">语音标题：</label>
                    <div class="daq-inp-rule wid-158 fl">
                        <input type="text" value="" name="title" placeholder="语音标题"/>
                    </div>
                </div>
                <div class="form-item fl mr-20">
                    <label class="form-label">景点：</label>
                    <div id="scenicId" class="daq-select wid-150 fl">
                        <select name="scenicId">
                            <option value="">全部</option>
                        <#if scenicList??>
                            <#list scenicList as t>
                                <option value="${t.id!}">${t.name!}</option>
                            </#list>
                        </#if>
                        </select>
                    </div>
                </div>
                <div class="form-item fl mr-20">
                    <label class="form-label">语音类型：</label>
                    <div id="audioType" class="daq-select wid-150 fl">
                        <select name="audioType">
                            <option value="">全部</option>
                        <#if audioType??>
                            <#list audioType as t>
                                <option value="${t.id!}">${t.name!}</option>
                            </#list>
                        </#if>
                        </select>
                    </div>
                </div>
                <div class="form-item fl mr-20">
                    <label class="form-label">语言：</label>
                    <div id="languageType" class="daq-select wid-90 fl">
                        <select name="languageType">
                            <option value="">全部</option>
                        <#if languageType??>
                            <#list languageType as t>
                                <option value="${t.value!}">${t.name!}</option>
                            </#list>
                        </#if>
                        </select>
                    </div>
                </div>
                <div class="form-item fl mr-20">
                    <label class="form-label">状态：</label>
                <#include "../statusSelect.ftl">
                </div>
                <div class="mr-5 daq-inp-icon fl">
                    <button type="button" class="search-button daq-btn">查询</button>
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
    var scenicList,audioTypeList, i=1;
    $(function () {
        // 表格数据
        daqsoft.$listTable = $("#listTable").daqDataTable({
            url: "/audio/list",
            columns: [
                {
                    field: 'title',
                    title: '语音标题',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                }, {
                    field: 'audioType',
                    title: '语音类型',
                    formatter: function (value, data) {
                        return getDictName(audioTypeList, data.audioType);
                    }
                }, {
                    field: 'scenicId',
                    title: '景点',
                    formatter: function (value, data) {
                        return getDictName(scenicList, data.scenicId);;
                    }
                }, {
                    field: 'audioUrl',
                    title: '语音',
                    formatter: function (value) {
                        return getAudioDiv(value);
                    }
                }, {
                    field: 'audioSize',
                    title: '语音大小',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                }, {
                    field: 'updateTime',
                    title: '时间',
                    formatter: function (value) {
                        return formatDate(value);
                    }
                }, {
                    field: 'status',
                    title: '状态',
                    width: '60px',
                    formatter: function (value) {
                        return getStatus(value);
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

        // 加载景点列表
        getScenicList();
        // 加载语音类型列表
        getAudioTypeList();

        // 绑定下拉列表框
        bindSelect();

    });
    function bindSelect() {
        $("#scenicId").daqSelect({
            value: '',
            callback: function (data) {
            }
        });
        $("#audioType").daqSelect({
            value: '',
            callback: function (data) {
            }
        });
        $("#languageType").daqSelect({
            value: '',
            callback: function (data) {
            }
        });
    }
    // 查询景区景点列表
    function getScenicList() {
        $.ajax({
            url: "/permitted/common/getScenicList",
            type: "get",
            dataType: "json",
            async: false,
            success: function (data) {
                if (data.code == 0) {
                    scenicList = data.datas;
                }
            }
        })
    }
    // 查询音频类型列表
    function getAudioTypeList() {
        $.ajax({
            url: "/audio/getAudioTypeList",
            type: "get",
            dataType: "json",
            async: false,
            success: function (data) {
                if (data.code == 0) {
                    audioTypeList = data.datas;
                }
            }
        })
    }

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
</script>
</html>