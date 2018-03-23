<!DOCTYPE html>
<html>
<head>
<#include "../../../header.ftl">
<#include "../../../admin.ftl">
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
                    <label class="form-label">渠道名称：</label>
                    <div class="daq-inp-rule wid-120 fl">
                        <input type="text" value="" name="name" placeholder="渠道名称"/>
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
        daqsoft.$listTable = $("#listTable").daqDataTable({
            url: "/sendInfoChannel/list",
            columns: [
                 {
                    field: 'platformName',
                    title: '平台名称',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                },{
                    field: 'ditchName',
                    title: '渠道名称',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                },{
                    field: 'informationType',
                    title: '信息类型',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
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
            }
        });
    });
</script>
</html>