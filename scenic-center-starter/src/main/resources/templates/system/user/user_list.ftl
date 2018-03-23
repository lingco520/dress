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
                            <option value="ACCOUNT">用户名</option>
                            <option value="NAME">真实姓名</option>
                            <option value="PHONE">手机号</option>
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
            url: "/user",
            columns: [
                {
                    field: 'ACCOUNT',
                    title: '用户名'
                }, {
                    field: 'NAME',
                    title: '真实姓名'
                }, {
                    field: 'PHONE',
                    title: '手机号',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                }, {
                    field: 'EMAIL',
                    title: '邮箱',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                }, {
                    field: 'LOGINTIME',
                    title: '最后登陆时间',
                    formatter: function (value, data) {
                        return daqsoft.getBlank(value);
                    }
                }, {
                    field: 'LOGINIP',
                    title: '最后登陆IP',
                    formatter: function (value, data) {
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
            callback: function (data) {
                //注册权限按钮.
                operateRegister(".tool-bar", ".operation-bar");
            }
        });
    });
</script>
</html>