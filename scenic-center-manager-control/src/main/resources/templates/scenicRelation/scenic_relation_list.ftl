<!DOCTYPE html>
<html>
<head>
<#include "../header.ftl">
<#include "../admin.ftl">
</head>
<body>
<script>
    var level = ${level!};
    var type = ${type!};
</script>
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
                    <label class="form-label">所属账号：</label>
                    <div class="daq-inp-rule wid-198 fl">
                        <input type="text" value="" name="account" placeholder="所属账号"/>
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
<script>

    $(function () {
        // 表格数据
        daqsoft.$listTable = $("#listTable").daqDataTable({
            url: "/scenicRelation/list",
            columns: [
                {
                    field: 'id',
                    title: '编码',
                    hide:true,
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                },{
                    field: 'email',
                    title: '电子邮件',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                },{
                    field: 'account',
                    title: '账号名称',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                }, {
                    field: 'name',
                    title: '名字',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                }, {
                    field: 'region',
                    title: '地区编码',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                },  {
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
                operateRegister(".tool-bar", ".operation-bar");
            }
        });
    });
</script>
</html>