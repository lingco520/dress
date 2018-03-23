<!DOCTYPE html>
<html>
<head>
<#include "../../header.ftl">
<#include "../../admin.ftl">
</head>
<body>
<!--当前位置 面包导航-->
<div class="system-top">
    <p class="daq-crumb clearfix">
    </p>
</div>
<form id="listForm">
    <!--内容 S-->
    <div class="section-content">
        <!--<h3 class="system-title">系统首页</h3>-->
        <div class="section-son-content source-pad">
            <div class="clearfix mt-32">
                <!-- 事件名称 -->
                <div class="form-item mr-5 fl">
                    <div id="searchBy" class="daq-select">
                        <select name="searchBy">
                            <option value="NAME">字典名称</option>
                            <option value="VALUE">字典值</option>
                            <option value="TYPE">字典类型</option>
                        </select>
                    </div>
                </div>
                <div class="daq-inp-rule wid-238 mr-5 daq-inp-icon fl">
                    <input type="text" name="keyword" placeholder="请输入文字"><i class="sysfont search-button inp-icon"></i>
                </div>
                <div class="tool-bar btn-mg-5"></div>
            </div>
            <table id="listTable" cellpadding="0" cellspacing="0" width="100%" class="daq-table daq-tab-pad mt-20">

            </table>
        </div>
    </div>
</form>
</body>
<script>
    $(function () {
        // 表格数据
        daqsoft.$listTable = $("#listTable").daqDataTable({
            url: "/dict",
            columns: [
                {
                    field: 'NAME',
                    title: '字典名称',
                    className: 'channel-td',
                    formatter: function (value, data) {
                        var channelDom = "<div class='channel' data-level = '" + data.LEVEL + "' data-parent='" + data.PID + "'";
                        var indent;
                        if (data.LEVEL > 1) {
                            indent = (data.LEVEL - 1) * 20;
                            channelDom += " style='padding-left:" + indent + "px;'";
                        }
                        channelDom += ">";
                        if (data.CHILDREN) {
                            channelDom += '<a class="sysfont mr-5 chanelShrink" status="0" href="javascript:void(0);" style="font-size:12px;">&#xe6e8;</a>';
                        }
                        return channelDom + value + "</div>";
                    }
                }, {
                    field: 'VALUE',
                    title: '字典值'
                }, {
                    field: 'TYPE',
                    title: '字典类型'
                }, {
                    field: 'LEVEL',
                    title: '字典等级'
                }, {
                    field: 'SORT',
                    title: '排序',
                    width: '80px',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                }, {
                    field: 'ID',
                    title: "操作",
                    width: '190px',
                    formatter: function (value, data) {
                        return "<div class='operation-bar' data=" + value + " data-color='' ></div>";
                    }
                }],
            limits: 3,
            pageText: true,
            checkbox: true,
            mixColor: true,
            callback: function (datas) {
                //注册权限按钮.
                operateRegister(".tool-bar", ".operation-bar");
                //第一个参数为操作栏位置div,第二个为列表操作栏div
                $('.daq-table .channel').parents('td').addClass('channel-td');
            }
        });
        $('.daq-table').on('click', '.channel-td', function (e) {
            e.stopPropagation();
            $(this).find('.channel').click();
        })
        $('.daq-table').on('click', '.channel', function (e) {
            e.stopPropagation();
            matchChannel($(this));
        });
    });
    /**
     * 点击匹配当前栏目下的子栏目
     * obj 要查找子栏目的对象
     **/
    function matchChannel($el) {
        var arr = [],
                id = $el.parents('tr').data('id'),
                shrink = $el.find('.chanelShrink'),
                status = shrink.attr('status');
        if (status == '0') {
            $('.channel').each(function (i, v) {
                var pid = $(this).data('parent');
                if (id == pid) {
                    var newSta = $(this).find('.chanelShrink').attr('status');
                    if (typeof newSta !== 'undefined' && newSta === '0') {
                        matchChannel($(this));
                    }
                    $('.channel').eq(i).parents('tr').stop().hide();
                }
            });

            $el.find('.chanelShrink').attr('status', '1').html('&#xe6ea;');
        } else if (status == '1') {
            $('.channel').each(function (i, v) {
                var pid = $(this).data('parent');
                if (id == pid) {
                    var newLevel = $('.channel').eq(i).data('level');
                    $('.channel').eq(i).parents('tr').stop().show();
                    $('.channel').eq(i).find('.chanelShrink').attr('status', '1').html('&#xe6ea;');
                }
            });
            $el.find('.chanelShrink').attr('status', '0').html('&#xe6e8;');
        }
    }
</script>
</html>