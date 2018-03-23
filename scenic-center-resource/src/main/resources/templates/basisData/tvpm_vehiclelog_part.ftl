<!DOCTYPE html>
<html>
<head>
<#include "../header.ftl">
<#include "../edit.ftl">
    <link rel="stylesheet" href="/styleSheet/daqStyle.css">
    <script type="text/javascript" src="/script/jQuery/jquery.lSelect.js"></script>
    <style>

        .btn-hover:hover {
            border-color: #20a0ff;
        }

        .btn-hover:hover i {
            color: #20a0ff;
        }

        .btn-hover1:hover {
            border-color: #ff4949;
        }

        .btn-hover1:hover i {
            color: #ff4949;
        }

        .form-item-table table td.form-tab-label {
            max-width: 165px;
        }

        .dq_dt_ltd {
            text-align: center;
        }

        .base-data-tit h3 {
            font: bold 18px/50px 'Microsoft YaHei UI';
        }

        .form-date-connect {
            padding: 0 1px;
        }
    </style>
</head>
<body>
<div class="system-top">
    <p class="daq-crumb clearfix">
        <a href="javascript:;" class=""><i class="sysfont"></i> 数据模拟</a>
        <i class="sysfont crumb-sign sysfont"></i>
        <a href="javascript:;" class=""><i class="sysfont"></i> 实时数据</a>
    </p>
</div>
<div class="section-content">
    <div class="section-son-content">

        <div class="form-content">
            <div class="base-data-tit">
                <h3>车流量</h3>
            </div>
            <form id="dataConfigForm" action="">
                <input type="hidden" name="id" value="${id!}"/>
                <div class="form-item-table">
                    <div>
                        <table cellpadding="0" cellspacing="0" width="100%">
                            <!--数据模拟时间选择-->
                            <tr>
                                <td class="form-tab-label"><label class="form-label">时间范围:</label></td>
                                <td>
                                    <div class="form-table-edit wid-168 fl">
                                        <div class="daq-date daq-inp-rule daq-inp-date">
                                            <input class="date" id="dtvpStartDate" name="dtvpStartDate" value=""
                                                   placeholder="请选择开始时间"/>
                                            <i class="sysfont date-icon"></i>
                                        </div>
                                    </div>
                                    <i class="sysfont form-date-connect fl" style="margin-top: 20px;"></i>
                                    <div class="form-table-edit wid-168 fl">
                                        <div class="daq-date daq-inp-rule daq-inp-date">
                                            <input class="date" id="dtvpEndDate" name="dtvpEndDate" value=""
                                                   placeholder="请选择结束时间"/>
                                            <i class="sysfont date-icon"></i>
                                        </div>
                                    </div>
                                </td>
                                <td class="form-tab-label"><label class="form-label">是否覆盖原有数据:</label></td>
                                <td colspan="2">
                                    <div class="form-table-edit">
                                        <div id="checkbox" class="mt-20 clearfix choose-box ">
                                            <input type="checkbox" name="isDeleteHistory" value="1" title="是"/>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                    <#if dtoList??>
                        <#list dtoList as dto>
                        <tr class="new">
                            <td class="form-tab-label"><label class="form-label">车辆所在地:</label></td>
                            <td colspan="1">
                                <div class="form-table-edit">
                                    <div>
                                        <input type="hidden" class="dtvpRegionName" name="dtvpRegionName" value="">
                                        <input type="hidden" class="dtvpRegion" name="dtvpRegion" value="">
                                        <div class="js_citySelect" class="clearfix mt-20"></div>
                                    </div>
                                </div>
                            </td>
                            <td class="form-tab-label"><label class="form-label">记录数范围:</label></td>
                            <td>
                                <div class="form-table-edit wid-98 fl">
                                    <div class="daq-inp-rule">
                                        <input type="text" name="dtvpRandomMin" class="s_input s_input_fb"
                                               validate="{digits: true,range:[1,99999]}"
                                               value="${dto.dtvpRandomMin!}"/>
                                    </div>
                                </div>
                                <i class="sysfont form-date-connect fl" style="margin-top: 20px;"></i>
                                <div class="form-table-edit wid-98 fl">
                                    <div class="daq-inp-rule">
                                        <input type="text" name="dtvpRandomMax" class="s_input s_input_fb"
                                               validate="{digits: true,range:[1,99999]}"
                                               value="${dto.dtvpRandomMax!}"/>
                                    </div>
                                </div>
                            </td>
                            <td style="background: #f9fafc; text-align: center;padding: 0 3px">
                                <div class="demo-button">
                                    <div class="daq-btn-line btn-icon btn-hover add"><i class="sysfont"></i></div>
                                    <div class="daq-btn-line btn-icon btn-hover1 remove"><i class="sysfont"></i></div>
                                </div>
                            </td>
                        </tr>
                        </#list>
                    </#if>
                            <!--车流量结束-->
                            <tr>
                                <td class="dq_dt_ltd" colspan="8">
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
        /* ---------- 表单验证 ---------- */
        var $validateForm = $("#dataConfigForm");
        var formSubmitted = false;
        if ($validateForm.length > 0) {
            $.metadata.setType("attr", "validate");
            $validateForm.validate({
                //当验证成功时,调用此函数.
                submitHandler: function () {
                    console.log("validate successfully");
                    saveForm($validateForm);
                    /*//如果表单验证成功,屏蔽掉提交按钮防止重复提交
                    if (!formSubmitted) {
                        formSubmitted = true;
                        saveForm($validateForm);
                    } else {
                        layer.msg("请勿重复提交!");
                    }*/
                }, errorPlacement: function (error, element) {
                    error.appendTo(element.parents(".form-table-edit"));
                }
            });
        }
        $(".nextorlast").click(function () {
            $("#dataConfigForm").submit();
        });
        var load;

        function saveForm(validateForm) {
            var param = validateForm.serialize();
            var _csrf = document.cookie.split("=")[1];
            $.ajax({
                headers: {'X-XSRF-TOKEN': _csrf},
                url: "/permitted/basisData/saveDataTvpmVehiclelogPart",
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
                    load.hide();
                    layer.alert(result.message);
                }
            });
        }

        // 自定义验证表达式
        $.validator.addMethod("checkDtvpRandomMax", function (value, element, params) {
            var dtvpRandomMax = $(element).closest('.new').find("[name=dtvpRandomMax]").val();
            if (dtvpRandomMax !== null && dtvpRandomMax !== "" && dtvpRandomMax !== undefined) {
                return this.optional(element) || (parseInt(dtvpRandomMax) >= parseInt(value));
            }
            return true;
        }, "小于最大值");
        $.validator.addMethod("checkDtvpStartDate", function (value, element, params) {
            var dtvpEndDate = $("#dtvpEndDate").val();
            if (dtvpEndDate !== null && dtvpEndDate !== "" && dtvpEndDate !== undefined) {
                return this.optional(element) || (dtvpEndDate >= value);
            }
            return true;
        }, "不大于结束日期");

        // 绑定验证
        $("#dtvpStartDate").rules("add", {
            required: true,
            checkDtvpStartDate: true,
            messages: {
                required: "必填"
            }
        });
        $("#dtvpEndDate").rules("add", {
            required: true,
            messages: {
                required: "必填"
            }
        });
        $("[name=dtvpRegionName]").each(function () {
            $(this).rules("add", {
                required: true,
                messages: {
                    required: "必填"
                }
            });
        });
        $("[name=dtvpRandomMin]").each(function () {
            $(this).rules("add", {
                required: true,
                checkDtvpRandomMax: true,
                messages: {
                    required: "必填"
                }
            });
        });
        $("[name=dtvpRandomMax]").each(function () {
            $(this).rules("add", {
                required: true,
                messages: {
                    required: "必填"
                }
            });
        });


        var regionListSize = '${regionList?size}';
        var defaultList = [];
        if (regionListSize > 0) {
        <#list regionList as regions>
            var defaults = [];
            <#list regions as region>
                var regionJson = {};
                regionJson.region = '${(region.region!)}';
                regionJson.name = '${(region.name!)}';
                defaults.push(regionJson);
            </#list>
            defaultList.push(defaults);
        </#list>
        }
        var regionNameList = [];
        <#list regionNameValueList as regionName>
            regionNameList.push('${(regionName!)}');
        </#list>
        var regionList = [];
        <#list regionValueList as region>
            regionList.push('${(region!)}');
        </#list>
        //地区三级显示
        $().ready(function () {
            var $dtvpAreaSelect = $(".js_citySelect");
            $dtvpAreaSelect.each(function (i) {
                var _this = $(this);
                // 初始input隐藏域赋值
                _this.closest('.new').find(".dtvpRegionName").val(regionNameList[i]);
                _this.closest('.new').find(".dtvpRegion").val(regionList[i]);
                // 绑定事件
                $(this).daqCitySelect({
                    defaults: defaultList[i],
                    className: 'wid-98',
                    callback: function (data) {
                        var regionNames = "", regions = "";
                        // 这里判断，因为前端插件，当选择 “请选择”时，也是有值返回的，这里需要判断一次，如果是 请选择，则值赋值为 ""
                        if(data.length == 1 && data[0].value == ""){

                        }else{
                            for (var i = 0; i < data.length; i++) {
                                regionNames += data[i].name;
                                regions += data[i].value;
                                if (i != (data.length - 1)) {
                                    regionNames += "@";
                                    regions += "@";
                                }
                            }
                        }
                        _this.closest('.new').find(".dtvpRegionName").val(regionNames);
                        _this.closest('.new').find(".dtvpRegion").val(regions);
                    }
                });
            })
        })

    })
</script>
<script type="text/javascript">
    $(function () {
        // 数据时间段插件js
        var start = laydate.render({
            elem: '#dtvpStartDate',
            type: 'date',
            value: '${(dtvpStartDate?string('yyyy-MM-dd'))!}',
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
            elem: '#dtvpEndDate',
            type: 'date',
            value: '${(dtvpEndDate?string('yyyy-MM-dd'))!}',
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

        // 复选框绑定
        $("#checkbox").daqCheckbox({
            callback: function (data, obj) {
            }
        });

        var newTr = '<tr class="new">' +
                '<td class="form-tab-label"><label class="form-label">车辆所在地:</label></td>' +
                '<td colspan="1">'+
                '<div class="form-table-edit">' +
                '<div>' +
                '<input type="hidden" class="dtvpRegionName" name="dtvpRegionName" value="">' +
                '<input type="hidden" class="dtvpRegion" name="dtvpRegion" value="">' +
                '<div class="js_citySelect" class="clearfix mt-20"></div>' +
                '</div>' +
                '</div>' +
                '</td>' +
                '<td class="form-tab-label"><label class="form-label">记录数范围:</label></td>' +
                '<td>' +
                '<div class="form-table-edit wid-98 fl">' +
                '<div class="daq-inp-rule">' +
                '<input type="text" name="dtvpRandomMin" class="s_input s_input_fb" validate="{digits: true,range:[1,99999]}" value="">' +
                '</div>' +
                '</div>' +
                '<i class="sysfont form-date-connect fl" style="margin-top: 20px;"></i>' +
                '<div class="form-table-edit wid-98 fl">' +
                '<div class="daq-inp-rule">' +
                '<input type="text" name="dtvpRandomMax" class="s_input s_input_fb" validate="{digits: true,range:[1,99999]}" value="">' +
                '</div>' +
                '</div>' +
                '</td>' +
                '<td style="background: #f9fafc; text-align: center;padding: 0 3px">' +
                '<div class="demo-button">\n' +
                '<div class="daq-btn-line btn-icon btn-hover add"><i class="sysfont"></i></div> ' +
                ' <div class="daq-btn-line btn-icon btn-hover1 remove"><i class="sysfont"></i></div>' +
                '</div>' +
                '</td>' +
                '</tr>';

        // copy DOM
        $(document).on("click", '.add', function () {
            // 限制个数
            var trs = $("table tr[class=new]").length;
            if (trs >= 10) {
                layer.alert("最多10个节点");
                return;
            }
            $(".new:last").after(newTr);
            // 绑定加载样式
            var defaults = [];
            var _this = $(".js_citySelect:last");
            _this.daqCitySelect({
                defaults: defaults,
                className: 'wid-98',
                callback: function (data) {
                    var regionNames = "", regions = "";
                    // 这里判断，因为前端插件，当选择 “请选择”时，也是有值返回的，这里需要判断一次，如果是 请选择，则值赋值为 ""
                    if(data.length == 1 && data[0].value == ""){

                    }else{
                        for (var i = 0; i < data.length; i++) {
                            regionNames += data[i].name;
                            regions += data[i].value;
                            if (i != (data.length - 1)) {
                                regionNames += "@";
                                regions += "@";
                            }
                        }
                    }
                    _this.closest('.new').find(".dtvpRegionName").val(regionNames);
                    _this.closest('.new').find(".dtvpRegion").val(regions);
                }
            });

            var idstr = 1;
            $("[name=dtvpRegionName]").each(function () {
                $(this).attr("id", "dtvpRegionName" + idstr);
                idstr++;
            });
            $("[name=dtvpRandomMin]").each(function () {
                $(this).attr("id", "dtvpRandomMin" + idstr);
                idstr++;
            });
            $("[name=dtvpRandomMax]").each(function () {
                $(this).attr("id", "dtvpRandomMax" + idstr);
                idstr++;
            });
        })

        $(document).on("click", '.remove', function () {
            // 判断是否是最后一个
            var trs = $("table tr[class=new]").length;
            if (trs <= 1) {
                layer.alert("已经是最后一个节点，不能删除");
                return;
            }
            $(this).closest('.new').remove();
        });
    })


</script>
</html>