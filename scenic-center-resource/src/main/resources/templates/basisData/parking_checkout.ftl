<!DOCTYPE html>
<html>
<head>
<#include "../header.ftl">
<#include "../edit.ftl">
    <script type="text/javascript" src="/script/jQuery/jquery.lSelect.js"></script>
    <style>

        .wid-50 {
            width: 50px;
        }

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

        .base-data-tit {
            width: 100%;
        }

        .form-item-table table .form-label {
            height: auto;
            line-height: inherit;
        }

        .base-data-tit h3 {
            font: bold 18px/50px 'Microsoft YaHei UI';
        }

        .daq-date > input {
            width: 142px;
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
                <h3>停车场结算</h3>
            </div>
            <form id="dataConfigForm" action="">
                <input type="hidden" name="id" value="${id!}"/>
                <div class="form-item-table">
                    <div>
                        <table cellpadding="0" cellspacing="0" width="100%">

                            <!--数据模拟时间选择-->
                            <tr>
                                <td class="form-tab-label"><label class="form-label">开始时间</label></td>
                                <td>
                                    <div class="form-table-edit wid-188">
                                        <div class="daq-date">
                                            <input class="date" id="dpcStartDate" name="dpcStartDate" value=""
                                                   placeholder="请选择开始时间"/>
                                            <i class="sysfont date-icon"></i>
                                        </div>
                                    </div>
                                </td>
                                <td class="form-tab-label"><label class="form-label">结束时间</label></td>
                                <td>
                                    <div class="form-table-edit wid-188">
                                        <div class="daq-date">
                                            <input class="date" id="dpcEndDate" name="dpcEndDate" value=""
                                                   placeholder="请选择结束时间"/>
                                            <i class="sysfont date-icon"></i>
                                        </div>
                                    </div>
                                </td>
                                <td class="form-tab-label"><label class="form-label">是否覆盖原有数据</label></td>
                                <td colspan="3">
                                    <div class="form-table-edit">
                                        <div id="checkbox" class="mt-20 clearfix choose-box ">
                                            <input type="checkbox" name="isDeleteHistory" value="1" title="是"/>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <!--停车场结算开始-->
                    <#if dtoList??>
                        <#list dtoList as dto>
                        <tr class="new">
                            <td class="form-tab-label"><label class="form-label">停车场</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-select dpcParkingId wid-158">
                                        <select name="dpcParkingId">
                                        <#if parkingList??>
                                            <#list parkingList as t>
                                                <option value="${t.id!}"
                                                        <#if t.id!?string == (dto.dpcParkingId)!?string>selected</#if>>${t.name!}</option>
                                            </#list>
                                        </#if>
                                        </select>
                                    </div>
                                </div>
                            </td>
                            <td class="form-tab-label"><label class="form-label">车辆所在地</label></td>
                            <td colspan="1">
                                <div class="form-table-edit">
                                    <div>
                                        <input type="hidden" class="dpcRegionName" name="dpcRegionName" value="">
                                        <input type="hidden" class="dpcRegion" name="dpcRegion" value="">
                                        <div class="js_citySelect" class="clearfix mt-20"></div>
                                    </div>
                                </div>
                            </td>
                            <td class="form-tab-label"><label class="form-label">记录数范围</label></td>
                            <td>
                                <div class="form-table-edit wid-78">
                                    <div class="daq-inp-rule">
                                        <input type="text" name="dpcRandomMin" class="s_input s_input_fb"
                                               validate="{digits: true,range:[1,99999]}"
                                               value="${dto.dpcRandomMin!}"/>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <div class="form-table-edit wid-78">
                                    <div class="daq-inp-rule">
                                        <input type="text" name="dpcRandomMax" class="s_input s_input_fb"
                                               validate="{digits: true,range:[1,99999]}"
                                               value="${dto.dpcRandomMax!}"/>
                                    </div>
                                </div>
                            </td>
                            <td style="background: #f9fafc; text-align: center;padding: 0 5px">
                                <div class="demo-button">
                                    <div class="daq-btn-line btn-icon btn-hover add"><i class="sysfont"></i></div>
                                    <div class="daq-btn-line btn-icon btn-hover1 remove"><i class="sysfont"></i></div>
                                </div>
                            </td>
                        </tr>
                        </#list>
                    </#if>
                            <!--停车场结算结束-->
                            <tr>
                                <td class="dq_dt_ltd" colspan="10">
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
        // 声明遮罩层
        var load;

        function saveForm(validateForm) {
            var param = validateForm.serialize();
            var _csrf = document.cookie.split("=")[1];
            $.ajax({
                headers: {'X-XSRF-TOKEN': _csrf},
                url: "/permitted/basisData/saveDataParkingCheckout",
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

        // 自定义验证表达式
        $.validator.addMethod("checkDpcRandomMin", function (value, element, params) {
            var dpcRandomMax = $(element).closest('.new').find("[name=dpcRandomMax]").val();
            if (dpcRandomMax !== null && dpcRandomMax !== "" && dpcRandomMax !== undefined) {
                return this.optional(element) || (parseInt(dpcRandomMax) >= parseInt(value));
            }
            return true;
        }, "小于最大值");
        $.validator.addMethod("checkDpcStartDate", function (value, element, params) {
            var dpcEndDate = $("#dpcEndDate").val();
            if (dpcEndDate !== null && dpcEndDate !== "" && dpcEndDate !== undefined) {
                return this.optional(element) || (dpcEndDate >= value);
            }
            return true;
        }, "不大于结束日期");

        // 绑定验证
        $("#dpcStartDate").rules("add", {
            required: true,
            checkDpcStartDate: true,
            messages: {
                required: "必填"
            }
        });
        $("#dpcEndDate").rules("add", {
            required: true,
            messages: {
                required: "必填"
            }
        });
        $("[name=dpcParkingId]").each(function () {
            $(this).rules("add", {
                required: true,
                messages: {
                    required: "必填"
                }
            });
        });
        $("[name=dpcRegionName]").each(function () {
            $(this).rules("add", {
                required: true,
                messages: {
                    required: "必填"
                }
            });
        });
        $("[name=dpcRandomMin]").each(function () {
            $(this).rules("add", {
                required: true,
                checkDpcRandomMin: true,
                messages: {
                    required: "必填"
                }
            });
        });
        $("[name=dpcRandomMax]").each(function () {
            $(this).rules("add", {
                required: true,
                messages: {
                    required: "必填"
                }
            });
        });

        //地区三级显示
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
        $().ready(function () {
            var $dpcAreaSelect = $(".js_citySelect");
            $dpcAreaSelect.each(function (i) {
                var _this = $(this);
                // 初始input隐藏域赋值
                _this.closest('.new').find(".dpcRegionName").val(regionNameList[i]);
                _this.closest('.new').find(".dpcRegion").val(regionList[i]);
                // 绑定事件
                $(this).daqCitySelect({
                    defaults: defaultList[i],
                    className: 'wid-158',
                    callback: function (data) {
                        var regionNames = "", regions = "";
                        if (data.length == 1 && data[0].value == "") {

                        } else {
                            for (var i = 0; i < data.length; i++) {
                                regionNames += data[i].name;
                                regions += data[i].value;
                                if (i != (data.length - 1)) {
                                    regionNames += "@";
                                    regions += "@";
                                }
                            }
                        }
                        _this.closest('.new').find(".dpcRegionName").val(regionNames);
                        _this.closest('.new').find(".dpcRegion").val(regions);
                    }
                });
            })
        })

    })
</script>
<script type="text/javascript">
    $(function () {
        // 下拉列表框渲染数据
        var $dpcParkingIds = $(".dpcParkingId");
        $dpcParkingIds.each(function () {
            var _this = $(this);
            _this.daqSelect({
                value: '',
                callback: function (data) {
                }
            });
        })
        // 数据时间段插件js
        var start = laydate.render({
            elem: '#dpcStartDate',
            type: 'date',
            value: '${(dpcStartDate?string('yyyy-MM-dd'))!}',
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
            elem: '#dpcEndDate',
            type: 'date',
            value: '${(dpcEndDate?string('yyyy-MM-dd'))!}',
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

        var html = "";
        var parkingListSize = '${parkingList?size}';
        if (parkingListSize > 0) {
        <#list parkingList as parking>
            html += "<option value='${(parking.id!)}'>${(parking.name!)}</option>";
        </#list>
        }
        var newTr = '<tr class="new">' +
                '<td class="form-tab-label"><label class="form-label">停车场</label></td>' +
                '<td>' +
                '<div class="form-table-edit">' +
                '<div class="daq-select dpcParkingId wid-158">' +
                '<select name="dpcParkingId">' +
                html +
                '</select>' +
                '</div>' +
                '</td>' +
                '<td class="form-tab-label"><label class="form-label">车辆所在地</label></td>' +
                '<td colspan="1">' +
                '<div class="form-table-edit">' +
                '<div>' +
                '<input type="hidden" class="dpcRegionName" name="dpcRegionName" value="">' +
                '<input type="hidden" class="dpcRegion" name="dpcRegion" value="">' +
                '<div class="js_citySelect" class="clearfix mt-20">' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</td>' +
                '<td class="form-tab-label"><label class="form-label">记录数范围</label></td>' +
                '<td>' +
                '<div class="form-table-edit wid-78">' +
                '<div class="daq-inp-rule">' +
                '<input type="text" name="dpcRandomMin" class="s_input s_input_fb" validate="{digits: true,range:[1,99999]}" value="">' +
                '</div>' +
                '</div>' +
                '</td>' +
                '<td>' +
                '<div class="form-table-edit wid-78">' +
                '<div class="daq-inp-rule">' +
                '<input type="text" name="dpcRandomMax" class="s_input s_input_fb" validate="{digits: true,range:[1,99999]}" value="">' +
                '</div>' +
                '</div>' +
                '</td>' +
                '<td  style="background: #f9fafc; text-align: center;">' +
                '<div class="demo-button">' +
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
                className: 'wid-158',
                callback: function (data) {
                    var regionNames = "", regions = "";
                    // 这里判断，因为前端插件，当选择 “请选择”时，也是有值返回的，这里需要判断一次，如果是 请选择，则值赋值为 ""
                    if (data.length == 1 && data[0].value == "") {

                    } else {
                        for (var i = 0; i < data.length; i++) {
                            regionNames += data[i].name;
                            regions += data[i].value;
                            if (i != (data.length - 1)) {
                                regionNames += "@";
                                regions += "@";
                            }
                        }
                    }
                    _this.closest('.new').find(".dpcRegionName").val(regionNames);
                    _this.closest('.new').find(".dpcRegion").val(regions);
                }
            });


            var _this = $(".dpcParkingId:last");
            _this.daqSelect({
                value: '',
                callback: function (data) {
                }
            });

            var idstr = 1;
            $("[name=dpcParkingId]").each(function () {
                $(this).attr("id", "dpcParkingId" + idstr);
                idstr++;
            });
            $("[name=dpcRegionName]").each(function () {
                $(this).attr("id", "dpcRegion" + idstr);
                idstr++;
            });
            $("[name=dpcRandomMin]").each(function () {
                $(this).attr("id", "dpcRandomMin" + idstr);
                idstr++;
            });
            $("[name=dpcRandomMax]").each(function () {
                $(this).attr("id", "dpcRandomMax" + idstr);
                idstr++;
            });
        });

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