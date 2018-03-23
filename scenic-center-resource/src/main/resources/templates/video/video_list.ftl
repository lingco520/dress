<!DOCTYPE html>
<html>
<head>
<#include "../../../header.ftl">
<#include "../../../admin.ftl">
    <script src="http://filealiyun.geeker.com.cn/ued/geekUI/js/swfobject.js"></script>
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
                    <label class="form-label">视频名称：</label>
                    <div class="daq-inp-rule wid-198 fl">
                        <input type="text" value="" name="name" placeholder="视频名称"/>
                    </div>
                </div>
                <div class="form-item fl mr-20">
                    <label class="form-label">状态：</label>
                    <#include "../statusSelect.ftl">
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
<script>
    /* *
        * 实例化下拉选择器
        * @params{int} disable: 设置当前对象是否是禁用选项
        * @params{int} value: 设置默认选中值
        * @function callback: 点击选中后触发，并返回选中值
        * */
    $(function () {
        // 表格数据
        var img ='';
        var title ='';
        daqsoft.$listTable = $("#listTable").daqDataTable({
            url: "/video/list",
            columns: [
                 {
                    field: 'title',
                    title: '视频标题',
                    formatter: function (value) {
                        title = value;
                        return daqsoft.getBlank(value);
                    }
                }, {
                    field: 'scenicId',
                    title: '所属景点',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                }, {
                    field: 'videoType',
                    title: '视频类型',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                },
                {
                    field: 'imageUrl',
                    title: '封面图片',
                    formatter: function (value) {
                        img=value;
                        return "<ul  class='tab-preview' ><li data-url='"+value+"'><i></i>"
                                +"<img src='"+value+"' alt='' /></li></ul>";
                    }
                }, {
                    field: 'videoUrl',
                    title: '视频',
                    formatter: function (value) {
                        if(value == '' ){
                            return "<i class='sysfont tab-red'>暂无视频</i>";
                        }else {
                            return "<button type='button'  class='video daq-btn daq-btn-cic btn-small'  data-url='"+value+"' data-title='"+title+"' data-img='"+img+"'><i class='sysfont' >&#xe61d;<i></button>";
                        }
                    }
                },
                {
                    field: 'status',
                    title: '状态',
                    width: '60px',
                    formatter: function (value) {
                        return getStatus(value);
                    }
                },
                {
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

                    $(".video").on('click',function(event){
                        event.stopPropagation();
                        var title = $(this).attr("data-title");
                        var url = $(this).attr("data-url");
                        var img  = $(this).attr("data-img");
                        // 创建视频组件实例
                        $.daqVideo({
                            mediaTitle: title, // 媒体地址
                            mediaUrl:url,   // 媒体地址
                            mediaImg:img,   // 媒体背景图片
                            mediaHeight: 650, // 视频高度
                            mediaWidth: 1200, // 视频宽度
                            swfUrl: "http://filealiyun.geeker.com.cn/ued/geekUI/media/flvplayer.swf"  //swf文件 （注：如果不设置该参数 请将swf文件存放在同级目录src下的media文件内）
                        });
                    });
            }
        });
    });


</script>
</html>