<!DOCTYPE html>
<html>
<head>
<#include "../header.ftl">
<#include "../edit.ftl">
    <link rel="stylesheet" href="/styleSheet/daqStyle.css">
    <script type="text/javascript" src="/script/jQuery/jquery.lSelect.js"></script>
    <style>
        /*时间验证错误提示，没有使用form-item-table 特殊处理*/
        .dateError {
            float: left;
            display: inline-block;
            line-height: 36px;
            margin-left: 10px;
            padding-left: 20px;
            font-size: 12px;
            color: #ff4949;
            background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAOCAYAAAAfSC3RAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyZpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNS1jMDIxIDc5LjE1NTc3MiwgMjAxNC8wMS8xMy0xOTo0NDowMCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTQgKFdpbmRvd3MpIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOjNDOENDMzVERjZBRTExRTdBNTg1QjkxN0NBNDQ1MEM4IiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOjNDOENDMzVFRjZBRTExRTdBNTg1QjkxN0NBNDQ1MEM4Ij4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6M0M4Q0MzNUJGNkFFMTFFN0E1ODVCOTE3Q0E0NDUwQzgiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6M0M4Q0MzNUNGNkFFMTFFN0E1ODVCOTE3Q0E0NDUwQzgiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz6HFVd+AAABQ0lEQVR42mL8//8/Axx4eUkCyWIg9gFiRSBmBOJ7QLwViPsYtm17ClPKCNfo5RUDJGcDMQcDdvADiNOBmhchNEI0LWYgDiQANS9k/O/pKQ3k3IHadBbqrBogZkJSPBeIxYDYF2qzOkiyCMl5V4Cm1QPpOCD+BxXrBIqlAOnDUD5IbSEL1BQYiAc6+ylQYTWQBvG1oOxoILsDSZ0vyKm/gQwWNH+AbKmABlo6kJyG5vS/TDgC4CsSmwkaLSgAZNN9IFZFEisH2tYFtCkT6tRcIPsTkL0IydaHIMYWJE3TkTSBnJcDZHcAxZYC2a1I6jaB/CgDZNyGhtY1ID4ExBloLlsCxGpAbAbEP0FsWAIABf9CIhNAEtAF8yFuhiSjeGjkMuBJcokgTahpFRL0IGcXQuNWESr6AIg3A3E/UNNjmFKAAAMA5SNodcV3q1EAAAAASUVORK5CYII=) no-repeat 0 center
        }
    </style>
</head>
<body>
<div class="system-top">
    <p class="daq-crumb clearfix"><a href="javascript:;" class=""><i class="sysfont"></i> 数据模拟</a><i
            class="sysfont crumb-sign sysfont"></i><a href="javascript:;" class=""><i class="sysfont"></i> 实时数据</a>
    </p>
</div>
<div class="section-content">
    <div class="section-son-content">
        <div class="form-content">
            <div class="base-data-tit ml-10">
                <h3>实时人数</h3>
            </div>
            <form id="dataConfigForm" action="">
                <input type="hidden" name="id" value="${id!}"/>
                <div class="form-item-table">
                    <div class="tab-td-wid60">
                        <table cellpadding="0" cellspacing="0" width="100%">
                            <tr>
                                <td class="tab-label-color"><label class="form-label">开始时间:</label></td>
                                <td>
                                    <div class="ml-10 mr-10">
                                        <p class="daq-inp-rule daq-inp-date wid-148">
                                            <input class="startTime date" name="dstpStartDate" id="dstpStartDate"
                                                   placeholder="请选择开始时间"/>
                                            <i class="sysfont"></i>
                                        </p>
                                    </div>
                                </td>
                                <td class="tab-label-color"><label class="form-label">结束时间:</label></td>
                                <td>
                                    <div class="ml-10 mr-10">
                                        <p class="daq-inp-rule daq-inp-date wid-148">
                                            <input class="startTime date" name="dstpEndDate" id="dstpEndDate"
                                                   placeholder="请选择结束时间"/>
                                            <i class="sysfont"></i>
                                        </p>
                                    </div>
                                </td>
                                <td class="tab-label-color"><label class="form-label">是否覆盖原有数据:</label></td>
                                <td>
                                    <div class="form-table-edit">
                                        <div id="checkbox" class="mt-20 clearfix choose-box ">
                                            <input type="checkbox" name="isDeleteHistory" value="1" title="是"/>
                                        </div>
                                    </div>
                                </td>
                                <td colspan="5"></td>
                            </tr>
                            <!--实时人数开始-->
                        <#if dtoList??>
                            <#list dtoList as dto>
                                <tr class="new">
                                    <td class="tab-label-color"><label class="form-label">景区名称:</label></td>
                                    <td>
                                        <div class="form-table-edit">
                                            <div class="daq-inp-rule wid-139">
                                                <input type="text" name="dstpName" id="dstpName1"
                                                       class="s_input s_input_fb"
                                                       validate="{maxlength:40}" value="${dto.dstpName!}"/>
                                            </div>
                                        </div>
                                    </td>
                                    <td class="tab-label-color"><label class="form-label">单票人数值范围:</label></td>
                                    <td>
                                        <div class="form-table-edit wid-70 fl">
                                            <div class="daq-inp-rule">
                                                <input type="text" name="dstpTotalMin" id="dstpTotalMin1"
                                                       class="s_input s_input_fb"
                                                       validate="{digits: true,range:[1,100]}"
                                                       value="${dto.dstpTotalMin!}"/>
                                            </div>
                                        </div>
                                        <div class="form-table-edit wid-70 fl">
                                            <div class="daq-inp-rule">
                                                <input type="text" name="dstpTotalMax" id="dstpTotalMax1"
                                                       class="s_input s_input_fb"
                                                       validate="{digits: true,range:[1,100]}"
                                                       value="${dto.dstpTotalMax!}"/>
                                            </div>
                                        </div>
                                    </td>
                                    <td class="tab-label-color"><label class="form-label">票务类型:</label></td>
                                    <td>
                                        <div class="form-table-edit">
                                            <div class="daq-inp-rule">
                                                <input type="text" name="dstpSource" id="dstpSource1"
                                                       class="s_input s_input_fb"
                                                       validate="{maxlength:40}" value="${dto.dstpSource!}"/>
                                            </div>
                                        </div>
                                    </td>
                                    <td class="tab-label-color"><label class="form-label">类型:</label></td>
                                    <td>
                                        <div class="form-table-edit">
                                            <div class="daq-select dstpType">
                                                <select name="dstpType" id="dstpType1">
                                                    <option value="1" <#if '1' == (dto.dstpType)!>selected</#if>>进园
                                                    </option>
                                                    <option value="2" <#if '2' == (dto.dstpType)!>selected</#if>>出园
                                                    </option>
                                                </select>
                                            </div>
                                        </div>
                                    </td>
                                    <td class="tab-label-color"><label class="form-label">记录数范围:</label></td>
                                    <td>
                                        <div class="form-table-edit wid-78 fl">
                                            <div class="daq-inp-rule">
                                                <input type="text" name="dstpRandomMin" id="dstpRandomMin1"
                                                       class="s_input s_input_fb"
                                                       validate="{digits: true,range:[1,99999]}"
                                                       value="${dto.dstpRandomMin!}"/>
                                            </div>
                                        </div>
                                        <div class="form-table-edit wid-78 fl">
                                            <div class="daq-inp-rule">
                                                <input type="text" name="dstpRandomMax" id="dstpRandomMax1"
                                                       class="s_input s_input_fb"
                                                       validate="{digits: true,range:[1,99999]}"
                                                       value="${dto.dstpRandomMax!}"/>
                                            </div>
                                        </div>
                                    </td>

                                    <td style="background: #f9fafc; text-align: center; ">
                                        <div class="demo-button wid-98">
                                            <div class="daq-btn-line btn-icon add btn-hover"><i class="sysfont"></i>
                                            </div>
                                            <div class="daq-btn-line btn-icon remove btn-hover1"><i
                                                    class="sysfont"></i></div>
                                        </div>
                                    </td>
                                </tr>
                            </#list>
                        </#if>
                            <!--实时人数结束-->
                            <tr>
                                <td class="dq_dt_ltd" colspan="13">
                                    <div class="form-table-edit">
                                        <input type="button" class="daq-btn btn-pad-30 media-btn mt-5 nextorlast"
                                               value="生成模拟数据"/>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="loading"></div>
</body>
<script>
    $(function () {
        // 重写validate
        if ($.validator) {
            $.validator.prototype.elements = function () {
                var validator = this,
                        rulesCache = {};
                // select all valid inputs inside the form (no submit or reset buttons)
                return $(this.currentForm)
                        .find("input, select, textarea")
                        .not(":submit, :reset, :image, [disabled]")
                        .not(this.settings.ignore)
                        .filter(function () {
                            if (!this.name && validator.settings.debug && window.console) {
                                console.error("%o has no name assigned", this);
                            }
                            rulesCache[this.name] = true;
                            return true;
                        });
            }
        }

        $(".nextorlast").click(function () {
            $("#dataConfigForm").submit();
        });
        // 声明遮罩层
        var load;

        function saveForm(validateForm) {
            var param = validateForm.serialize();
            var _csrf = document.cookie.split("=")[1];
            $.ajax({
                headers: {'X-XSRF-TOKEN': _csrf},
                url: "/permitted/basisData/saveDataScenicTimelyPopulation",
                type: "post",
                data: param,
                dataType: "json",
                beforeSend: function () {
                    // 考虑到原遮罩层 hide() 后不能再次生效，此处先克隆一个原始对象，每次hide克隆后的对象
                    $(".loading").after($(".loading").clone(true));
                    // 添加一个hide时的类属性，标记此div需要隐藏
                    $(".loading:last").addClass("daq-loading")
                    // 发送请求前添加遮罩层
                    load = $(".daq-loading").daqBlockLoading({
                        position: "absolute"
                    });
                    $(".daq-loading").find(".load-text").html("数据写入中，请稍等");
                },
                success: function (result) {
                    // 成功后，关闭遮罩层
                    load.hide();
                    layer.alert(result.message);
                }
            });
        }

        //下面是表单验证规则 根据自己 项目属性进行更改
        /* ---------- 表单验证 ---------- */
        var $validateForm = $("#dataConfigForm");
        if ($validateForm.length > 0) {
            $.metadata.setType("attr", "validate");
            $validateForm.validate({
                //当验证成功时,调用此函数.
                submitHandler: function () {
                    console.log("validate successfully");
                    saveForm($validateForm);
                }, errorPlacement: function (error, element) {
                    error.appendTo(element.parents(".form-table-edit"));
                    if(element.hasClass("date")){
                        // 时间错误信息追加
                        error.appendTo(element.parents(".mr-10"));
                        error.addClass("dateError")
                    }
                }
            });
        }

        // 自定义验证表达式
        $.validator.addMethod("checkDstpTotalMin", function (value, element, params) {
            var dstpTotalMax = $(element).closest('.new').find("[name=dstpTotalMax]").val();
            if (dstpTotalMax !== null && dstpTotalMax !== "" && dstpTotalMax !== undefined) {
                return this.optional(element) || (parseInt(dstpTotalMax) >= parseInt(value));
            }
            return true;
        }, "小于最大值");
        $.validator.addMethod("checkDstpRandomMin", function (value, element, params) {
            var dstpRandomMax = $(element).closest('.new').find("[name=dstpRandomMax]").val();
            if (dstpRandomMax !== null && dstpRandomMax !== "" && dstpRandomMax !== undefined) {
                return this.optional(element) || (parseInt(dstpRandomMax) >= parseInt(value));
            }
            return true;
        }, "小于最大值");
        $.validator.addMethod("checkDstpStartDate", function (value, element, params) {
            var dstpEndDate = $("#dstpEndDate").val();
            if (dstpEndDate !== null && dstpEndDate !== "" && dstpEndDate !== undefined) {
                return this.optional(element) || (dstpEndDate >= value);
            }
            return true;
        }, "不大于结束日期");

        // 绑定验证
        $("#dstpStartDate").rules("add", {
            required: true,
            checkDstpStartDate: true,
            messages: {
                required: "必填"
            }
        });
        $("#dstpEndDate").rules("add", {
            required: true,
            messages: {
                required: "必填"
            }
        });
        $("[name=dstpName]").each(function () {
            $(this).rules("add", {
                required: true,
                messages: {
                    required: "必填"
                }
            });
        });
        $("[name=dstpTotalMin]").each(function () {
            $(this).rules("add", {
                required: true,
                checkDstpTotalMin: true,
                messages: {
                    required: "必填"
                }
            });
        });

        $("[name=dstpTotalMax]").each(function () {
            $(this).rules("add", {
                required: true,
                messages: {
                    required: "必填"
                }
            });
        });
        $("[name=dstpSource]").each(function () {
            $(this).rules("add", {
                required: true,
                messages: {
                    required: "必填"
                }
            });
        });
        $("[name=dstpType]").each(function () {
            $(this).rules("add", {
                required: true,
                messages: {
                    required: "必填"
                }
            });
        });
        $("[name=dstpRandomMin]").each(function () {
            $(this).rules("add", {
                required: true,
                checkDstpRandomMin: true,
                messages: {
                    required: "必填"
                }
            });
        });
        $("[name=dstpRandomMax]").each(function () {
            $(this).rules("add", {
                required: true,
                messages: {
                    required: "必填"
                }
            });
        });

    });
</script>
<script type="text/javascript">
    $(function () {
        $().ready(function () {
            // 对下拉列表框时间进行重新绑定，不然不能操作
            var $dstpTypes = $(".dstpType");
            $dstpTypes.each(function () {
                $(this).daqSelect({
                    value: '',
                    callback: function (data) {
                    }
                });
            })
        })

        // 复选框绑定
        $("#checkbox").daqCheckbox({
            callback: function (data, obj) {
            }
        });
        // 数据时间段插件js
        var start = laydate.render({
            elem: '#dstpStartDate',
            type: 'date',
            value: '${(dstpStartDate?string('yyyy-MM-dd'))!}',
            change: function () {

            },
            done: function (value, date) {
                if (value !== '') {
                    end.config.min.year = date.year;
                    end.config.min.month = date.month - 1;
                    end.config.min.date = date.date;
                } else {
                    end.config.min.year = '';
                    end.config.min.month = '';
                    end.config.min.date = '';
                }
            }
        });
        var end = laydate.render({
            elem: '#dstpEndDate',
            type: 'date',
            value: '${(dstpEndDate?string('yyyy-MM-dd'))!}',
            change: function () {

            },
            done: function (value, date) {
                if (value !== '') {
                    start.config.max.year = date.year;
                    start.config.max.month = date.month - 1;
                    start.config.max.date = date.date;
                } else {
                    start.config.max.year = '';
                    start.config.max.month = '';
                    start.config.max.date = '';
                }
            }
        });

        // copy DOM
        var appendDiv = '<tr class="new">' +
                '<td class="tab-label-color"><label class="form-label">景区名称:</label></td>' +
                '<td>' +
                '<div class="form-table-edit">' +
                '<div class="daq-inp-rule wid-139">' +
                '<input type="text" name="dstpName" id="dstpName1" class="s_input s_input_fb" validate="{maxlength:40}" value="">' +
                '</div>' +
                '</div>' +
                '</td>' +
                '<td class="tab-label-color"><label class="form-label">单票人数值范围:</label></td>' +
                '<td>' +
                '<div class="form-table-edit wid-78 fl">' +
                '<div class="daq-inp-rule">' +
                '<input type="text" name="dstpTotalMin" id="dstpTotalMin1" class="s_input s_input_fb" validate="{digits: true,range:[1,100]}" value="">' +
                '</div>' +
                '</div>' +
                '<div class="form-table-edit wid-78 fl">' +
                '<div class="daq-inp-rule">' +
                '<input type="text" name="dstpTotalMax" id="dstpTotalMax1" class="s_input s_input_fb" validate="{digits: true,range:[1,100]}" value="">' +
                '</div>' +
                '</div>' +
                '</td>' +
                '<td class="tab-label-color"><label class="form-label">票务类型:</label></td>' +
                '<td>' +
                '<div class="form-table-edit">' +
                '<div class="daq-inp-rule">' +
                '<input type="text" name="dstpSource" id="dstpSource1" class="s_input s_input_fb" validate="{maxlength:40}" value="">' +
                '</div>' +
                '</div>' +
                '</td>' +
                '<td class="tab-label-color"><label class="form-label">类型:</label></td>' +
                '<td>' +
                '<div class="form-table-edit">' +
                '<div class="daq-select dstpType">' +
                '<select name="dstpType" id="dstpType1">' +
                '<option value="1" selected="selected">进园</option>' +
                '<option value="2">出园</option>' +
                '</select>' +
                '</div>' +
                '</div>' +
                '</td>' +
                '<td class="tab-label-color"><label class="form-label">记录数范围:</label></td>' +
                '<td>' +
                '<div class="form-table-edit wid-78 fl">' +
                '<div class="daq-inp-rule">' +
                '<input type="text" name="dstpRandomMin" id="dstpRandomMin1" class="s_input s_input_fb" validate="{digits: true,range:[1,99999]}" value="">' +
                '</div>' +
                '</div>' +
                '<div class="form-table-edit wid-78 fl">' +
                '<div class="daq-inp-rule">' +
                '<input type="text" name="dstpRandomMax" id="dstpRandomMax1" class="s_input s_input_fb" validate="{digits: true,range:[1,99999]}" value="">' +
                '</div></div></td>' +
                '<td style="background: #f9fafc; text-align: center; ">' +
                '<div class="demo-button wid-98">' +
                '<div class="daq-btn-line btn-icon add btn-hover"><i class="sysfont"></i></div> ' +
                ' <div class="daq-btn-line btn-icon remove btn-hover1"><i class="sysfont"></i></div>' +
                '</div>' +
                '</td>' +
                '</tr>';

        // 添加方法
        $(document).on("click", '.add', function () {
            // 限制个数
            var trs = $("table tr[class=new]").length;
            if (trs >= 10) {
                layer.alert("最多10个节点");
                return;
            }
            $(".new:last").after(appendDiv);
            $(".dstpType:last").daqSelect({
                value: '',
                callback: function (data) {
                }
            });
            var idstr = 1;
            $("[name=dstpName]").each(function () {
                $(this).attr("id", "dstpName" + idstr);
                idstr++;
            });
            $("[name=dstpTotalMin]").each(function () {
                $(this).attr("id", "dstpTotalMin" + idstr);
                idstr++;
            });
            $("[name=dstpTotalMax]").each(function () {
                $(this).attr("id", "dstpTotalMax" + idstr);
                idstr++;
            });
            $("[name=dstpSource]").each(function () {
                $(this).attr("id", "dstpSource" + idstr);
                idstr++;
            });
            $("[name=dstpType]").each(function () {
                $(this).attr("id", "dstpType" + idstr);
                idstr++;
            });
            $("[name=dstpRandomMin]").each(function () {
                $(this).attr("id", "dstpRandomMin" + idstr);
                idstr++;
            });
            $("[name=dstpRandomMax]").each(function () {
                $(this).attr("id", "dstpRandomMax" + idstr);
                idstr++;
            });
        })

        // 移除方法
        $(document).on("click", '.remove', function () {
            // 判断是否是最后一个
            var trs = $("table tr[class=new]").length;
            if (trs <= 1) {
                layer.alert("已经是最后一个节点，不能删除");
                return;
            }
            $(this).closest('.new').remove();
        })
    })
</script>
</html>