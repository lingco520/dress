<!doctype html>
<html>
<head>
<#include "../../header.ftl">
<#include "../../admin.ftl">
    <link rel="stylesheet"
          href="http://filealiyun.geeker.com.cn/ued/geekUI/js/zTree/v3.5.29/css/daqTreeStyle/daq-normal-tree.css"/>
    <script src="http://filealiyun.geeker.com.cn/ued/geekUI/js/zTree/v3.5.29/jquery.ztree.all.min.js"></script>
</head>
<body>
<!--当前位置 面包导航-->
<div class="system-top">
    <p class="daq-crumb clearfix">
    </p>
</div>
<!--内容 S-->
<form id="listForm">
    <input type="hidden" id="menuId" name="menuId"/>
    <div class="section-content">
        <!--<h3 class="system-title">系统首页</h3>-->
        <div class="section-son-content">
            <div style=" position: absolute; left: 0; top: 0; width:150px;overflow:auto;height:100%; display: block;">
                <div id="tree" class="daq-normal-tree" style="width: 100%; height: 98%; overflow: auto;"></div>
            </div>
            <div style="margin-left: 150px;">
                <div class="clearfix mt-15">
                    <!-- 事件名称 -->
                    <div class="form-item mr-5 fl">
                        <div id="searchBy" class="daq-select">
                            <select name="searchBy">
                                <option value="s.NAME">菜单名称</option>
                                <option value="NAME">操作名称</option>
                                <option value="OP">操作代码</option>
                                <option value="URL">URL</option>
                            </select>
                        </div>
                    </div>
                    <div class="daq-inp-rule wid-238 mr-5 daq-inp-icon fl">
                        <input type="text" name="keyword" placeholder="请输入文字"><i class="sysfont search-button inp-icon"></i>
                    </div>
                    <div class="tool-bar btn-mg-5"></div>
                </div>
                <table id="listTable" cellpadding="0" cellspacing="0" width="100%" class="daq-table daq-tab-pad mt-15">

                </table>
            </div>
        </div>
    </div>
</form>
</body>
<script>
    $(function () {
        //菜单处理
        $(function () {
            $('#tree').daqTree({
                defaultStyleId: '',
                nodes: ${menus!},
                settings: {
                    callback: {
                        onClick: function(zTree,name,element) {
                            $("#menuId").val(element.id);
                            $(".search-button").click();
                        }
                    },
                    data: {
                        key: {
                            children: 'subMenus',
                            name: 'name',
                            title: '',
                            url:'',
                            icon:'' // the Tree doesn't need icon.
                        }
                    },
                    view: {
                        showIcon: false
                    }
                }
            });
        });
        // 表格数据
        daqsoft.$listTable = $("#listTable").daqDataTable({
            url: "/operation",
            columns: [
                {
                    field: 'BNAME',
                    title: '菜单名称'
                }, {
                    field: 'NAME',
                    title: '操作名称'
                }, {
                    field: 'OP',
                    title: '操作代码'
                }, {
                    field: 'URL',
                    title: 'URL地址'
                }, {
                    field: 'ICON',
                    title: '显示图标',
                    formatter: function (value) {
                        value = daqsoft.getBlank(value);
                        return "<i class='sysfont '>" + value + "</i>";
                    }
                }, {
                    field: 'TOOLBAR',
                    title: '工具栏显示',
                    formatter: function (value) {
                        if (value == 1)
                            return "<i class='sysfont tab-green'>&#xe61e;</i>";
                        return "<i class='sysfont tab-red'>&#xe60b;</i>";
                    }
                }, {
                    field: 'OPERATIONBAR',
                    title: '操作栏显示',
                    formatter: function (value) {
                        if (value == 1)
                            return "<i class='sysfont tab-green'>&#xe61e;</i>";
                        return "<i class='sysfont tab-red'>&#xe60b;</i>";
                    }
                }, {
                    field: 'SORT',
                    title: '排序权重',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                }, {
                    field: 'ID',
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
            callback:function(data) {
                //注册权限按钮.
                operateRegister(".tool-bar", ".operation-bar");
            }
        });
        daqsoft.add = function(target) {
            if (daqsoft.checkDialogOpen()) {
                var menuId = $("#menuId").val();
                var url = daqsoft.getMainFrameURL(target);
                if(menuId != '') {
                    if (/\?/.exec(url)) {
                        url = url + "&";
                    }else {
                        url = url + "?";
                    }
                    url = url + "menuId=" + menuId;
                }
                var option = $.extend({}, daqsoft.config, {
                    content: url,
                    title: '添加操作'
                });
                layer.open(option);
            }
        }
    });
</script>
</html>