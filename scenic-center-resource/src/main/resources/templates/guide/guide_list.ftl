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
                    <label class="form-label">导游名称：</label>
                    <div class="daq-inp-rule wid-198 fl">
                        <input type="text" value="" name="name" placeholder="导游名称"/>
                    </div>
                </div>
                <div class="form-item fl mr-20">
                    <label class="form-label">导游性别：</label>
                    <div id="gender" class="daq-select wid-90 fl">
                        <select name="gender">
                            <option value="">全部</option>
                        <#if gender??>
                            <#list gender as t>
                                <option value="${t.value!}">${t.name!}</option>
                            </#list>
                        </#if>
                        </select>
                    </div>
                </div>
                <div class="form-item fl mr-20">
                    <label class="form-label">导游等级：</label>
                    <div id="guideLevel" class="daq-select wid-90 fl">
                        <select name="guideLevel">
                            <option value="">全部</option>
                        <#if guideLevel??>
                            <#list guideLevel as t>
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
<script>
    var dictList;
    $(function () {
        // 表格数据
        daqsoft.$listTable = $("#listTable").daqDataTable({
            url: "/guide/list",
            columns: [
                {
                    field: 'name',
                    title: '导游名称',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                }, {
                    field: 'guideLevel',
                    title: '等级',
                    formatter: function (value, data) {
                        return getDictName(dictList, data.guideLevel);
                    }
                }, {
                    field: 'identification',
                    title: '导游编号',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                }, {
                    field: 'company',
                    title: '所属公司',
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
                    field: 'gender',
                    title: '性别',
                    formatter: function (value, data) {
                        return getDictName(dictList, data.gender);
                    }
                }, {
                    field: 'image',
                    title: '证件照',
                    formatter: function (value) {
                        return getImageDiv(value);
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

        // 加载字典列表
        getDictListByTypes();

        // 绑定下拉列表框
        bindSelect();

    });
    function bindSelect() {
        // 初始化导游性别查询下拉框
        $("#gender").daqSelect({
            value: '',
            callback: function (data) {
            }
        });
        // 初始化导游性别查询下拉框
        $("#guideLevel").daqSelect({
            value: '',
            callback: function (data) {
            }
        });
    }
    function getDictListByTypes() {
        $.ajax({
            url: "/permitted/common/getDictListByTwo?type1=gender&type2=guideLevel",
            type: "get",
            dataType: "json",
            async: false,
            success: function (data) {
                if (data.code == 0) {
                    dictList = data.datas;
                }
            }
        })
    }
</script>
</html>