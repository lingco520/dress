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
                    <label class="form-label">图片标题：</label>
                    <div class="daq-inp-rule wid-198 fl">
                        <input type="text" value="" name="title" placeholder="图片标题"/>
                    </div>
                </div>
                <div class="form-item fl mr-20">
                    <label class="form-label">景点：</label>
                    <div id="scenicId" class="daq-select wid-90 fl">
                        <select name="scenicId">
                            <option value="">全部</option>
                        <#if scenicList??>
                            <#list scenicList as t>
                                <option value="${t.id!}">${t.name!}</option>
                            </#list>
                        </#if>
                        </select>
                    </div>
                </div>
                <div class="form-item fl mr-20">
                    <label class="form-label">图片类型：</label>
                    <div id="imageType" class="daq-select wid-90 fl">
                        <select name="imageType">
                            <option value="">全部</option>
                        <#if imageType??>
                            <#list imageType as t>
                                <option value="${t.id!}">${t.name!}</option>
                            </#list>
                        </#if>
                        </select>
                    </div>
                </div>
                <div class="form-item fl mr-20">
                    <label class="form-label">语言：</label>
                    <div id="languageType" class="daq-select wid-90 fl">
                        <select name="languageType">
                            <option value="">全部</option>
                        <#if languageType??>
                            <#list languageType as t>
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
    var scenicList,imageTypeList;
    $(function () {
        // 表格数据
        daqsoft.$listTable = $("#listTable").daqDataTable({
            url: "/pic/list",
            columns: [
                {
                    field: 'title',
                    title: '图片标题',
                    formatter: function (value) {
                        return daqsoft.getBlank(value);
                    }
                }, {
                    field: 'imageType',
                    title: '图片类型',
                    formatter: function (value, data) {
                        return getDictName(imageTypeList, data.imageType);
                    }
                }, {
                    field: 'scenicId',
                    title: '景点',
                    formatter: function (value, data) {
                        return getDictName(scenicList, data.scenicId);;
                    }
                }, {
                    field: 'updateTime',
                    title: '时间',
                    formatter: function (value) {
                        return formatDate(value);
                    }
                }, {
                    field: 'imageUrl',
                    title: '图片',
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

        // 加载景点列表
        getScenicList();
        // 加载图片类型列表
        getImageTypeList();

        // 绑定下拉列表框
        bindSelect();

    });
    function bindSelect() {
        $("#scenicId").daqSelect({
            value: '',
            callback: function (data) {
            }
        });
        $("#imageType").daqSelect({
            value: '',
            callback: function (data) {
            }
        });
        $("#languageType").daqSelect({
            value: '',
            callback: function (data) {
            }
        });
    }
    function getScenicList() {
        $.ajax({
            url: "/permitted/common/getScenicList",
            type: "get",
            dataType: "json",
            async: false,
            success: function (data) {
                if (data.code == 0) {
                    scenicList = data.datas;
                }
            }
        })
    }

    function getImageTypeList() {
        $.ajax({
            url: "/pic/getImageTypeList",
            type: "get",
            dataType: "json",
            async: false,
            success: function (data) {
                if (data.code == 0) {
                    imageTypeList = data.datas;
                }
            }
        })
    }
</script>
</html>