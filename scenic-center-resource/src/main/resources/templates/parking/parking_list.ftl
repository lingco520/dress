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
                    <label class="form-label">停车场名称：</label>
                    <div class="daq-inp-rule wid-158 fl">
                        <input type="text" value="" name="name" placeholder="停车场名称"/>
                    </div>
                </div>
                <div class="form-item fl mr-20">
                    <label class="form-label">停车场类型：</label>
                    <div id="parkingType" class="daq-select wid-150 fl">
                        <select name="parkingType">
                            <option value="">全部</option>
                            <#list parkingType as t>
                                <option value="${t.value!}">${t.name!}</option>
                            </#list>
                        </select>
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
    var parkingTypeList;
    $(function () {
        // 表格数据
        daqsoft.$listTable = $("#listTable").daqDataTable({
            url: "/parking/list",
            columns: [
                {
                    field: 'name',
                    title: '停车场名称',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                }, {
                    field: 'parkingLot',
                    title: '车位数',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                }, {
                    field: 'parkingType',
                    title: '停车场类型',
                    formatter: function (value, data) {
                        return getDictName(parkingTypeList, data.parkingType);
                    }
                }, {
                    field: 'opentime',
                    title: '开放时间段',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                }, {
                    field: 'chargeStandard',
                    title: '计费方式',
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
            }
        });

        // 加载停车场类型列表
        getParkingTypes();

        // 绑定下拉列表框
        bindSelect();

    });
    function bindSelect() {
        // 初始化停车场类型查询下拉框
        $("#parkingType").daqSelect({
            value: '',
            callback: function (data) {
            }
        });
    }
    function getParkingTypes() {
        $.ajax({
            url: "/permitted/common/getDictListByOne?type=parkingType",
            type: "get",
            dataType: "json",
            async: false,
            success: function (data) {
                if (data.code == 0) {
                    parkingTypeList = data.datas;
                }
            }
        })
    }
</script>
</html>