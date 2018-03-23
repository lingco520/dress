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
<!--内容 S-->
<form id="listForm">
    <div class="section-content">
        <!--<h3 class="system-title">系统首页</h3>-->
        <div class="section-son-content source-pad">
            <div class="clearfix mt-32">
                <!-- 事件名称 -->
                <div class="form-item mr-5 fl">
                    <div id="searchBy" class="daq-select">
                        <select name="searchBy">
                            <option value="REGION">地区编码</option>
                            <option value="NAME">地区名称</option>
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
            url: "/region",
            columns: [
                {
                    field: 'REGION',
                    title: '地区代码'
                }, {
                    field: 'NAME',
                    title: '地区名称'
                }, {
                    field: 'MEMO',
                    title: '地区全称',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                }, {
                    field: 'LONGITUDE',
                    title: '经度',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                }, {
                    field: 'LATITUDE',
                    title: '纬度',
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
    });
</script>
</html>