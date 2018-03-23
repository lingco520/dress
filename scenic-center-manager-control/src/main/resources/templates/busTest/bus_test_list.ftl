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
                    <label class="form-label">所属企业：</label>
                    <div class="daq-inp-rule wid-198 fl">
                        <input type="text" value="" name="name" placeholder="所属企业"/>
                    </div>
                </div>
                <div class="form-item fl mr-20">
                    <label class="form-label">客车级别：</label>
                    <div id="level" class="daq-select wid-90 fl">
                        <select name="level">
                            <option value="">全部</option>
                            <#list carLevels as l>
                                <option value="${l.VALUE!}">${l.NAME!}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="form-item fl mr-20">
                    <label class="form-label">客车类型：</label>
                    <div id="type" class="daq-select wid-90 fl">
                        <select name="type">
                            <option value="">全部</option>
                            <#list carTypes as t>
                                <option value="${t.VALUE!}">${t.NAME!}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="form-item fl mr-20">
                    <label class="form-label">准载人数：</label>
                    <div id="peopleNum" class="daq-select wid-90 fl">
                        <select name="peopleNum">
                            <option value="">全部</option>
                            <option value="10-20">10-20</option>
                            <option value="21-30">21-30</option>
                            <option value="31-40">31-40</option>
                            <option value="41-999">41以上</option>
                        </select>
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
    /* *
        * 实例化下拉选择器
        * @params{int} disable: 设置当前对象是否是禁用选项
        * @params{int} value: 设置默认选中值
        * @function callback: 点击选中后触发，并返回选中值
        * */
    $("#level").daqSelect({
        value: '',
        callback: function (data) {
        }
    });
    $("#type").daqSelect({
        value: '',
        callback: function (data) {
        }
    });
    $("#peopleNum").daqSelect({
        value: '',
        callback: function (data) {
        }
    });
    $(function () {
        // 表格数据
        daqsoft.$listTable = $("#listTable").daqDataTable({
            url: "/busTest/list",
            columns: [
                {
                    field: 'busnum',
                    title: '车牌号',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                }, {
                    field: 'company',
                    title: '所属企业',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                }, {
                    field: 'phone',
                    title: '企业电话',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                }, {
                    field: 'carLevel',
                    title: '客车级别',
                    formatter: function (value) {
                        for (var i = 0; i < level.length; i++) {
                            if(level[i].VALUE == value){
                                return level[i].NAME;
                            }
                        }
                        return "";
                    }
                }, {
                    field: 'carType',
                    title: '客车类型',
                    formatter: function (value) {
                        for (var i = 0; i < type.length; i++) {
                            if(type[i].VALUE == value){
                                return type[i].NAME;
                            }
                        }
                        return "";
                    }
                }, {
                    field: 'people',
                    title: "准载人数",
                    width: '190px',
                    formatter: function (value) {
                        return value ? value+"人" : "";
                    }
                }, {
                    field: 'color',
                    title: '颜色',
                    width: '60px',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
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
            callback: function (datas) {
                //注册权限按钮.
                //第一个参数为操作栏位置div,第二个为列表操作栏div
                operateRegister(".tool-bar", ".operation-bar");
            }
        });
    });
</script>
</html>