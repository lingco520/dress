/**
 * 将一些常用公共方法提取出来
 * @author ANONYM
 * @since 1.0
 */

var daqsoft = window.daqsoft || {};

(function ($, window) {

    "use strict";
    var messages = {
        tipsWord: "对不起,请选择一个选项!",
        closeDialog: "请先关闭当前面板!",
        deleteMethod: '你确定要删除吗？'
    };
    /**
     * 提示文字.可以进行动态修改.
     */
    daqsoft = {
        config: {
            type: 2,
            shadeClose: true,
            shade: false,
            max: true, //开启最大化最小化按钮
            //offset: ['30px', '30px'],
            id: "layerOperator",
            area: ['80%', '80%']
        },
        innerConfig: {
            tipsWord: messages.tipsWord,
            listTable: $("#listTable")
        },
        checkDialogOpen: function () {
            if ($("#layerOperator").size() != 0) {
                layer.msg(messages.closeDialog);
                return false;
            }
            return true;
        },
        add: function (targat) {
            if (daqsoft.checkDialogOpen()) {
                var title = targat.attr("title");
                var option = $.extend({}, daqsoft.config, {
                    content: daqsoft.getMainFrameURL(targat),
                    title: title
                });
                layer.open(option);
            }
        },
        getSelectedId: function (target) {
            var ids = [];
            if (target.hasClass("daq-tab-btn")) {
                ids[0] = target.parent(".operation-bar").attr("data");
            } else {
                daqsoft.getIdBylistTable().each(function (index, value) {
                    ids.push($(value).val());
                });
            }
            return ids;
        },
        openPanelById: function (target) {
            if (daqsoft.checkDialogOpen()) {
                var id = daqsoft.getSelectedId(target);
                var title = target.attr("title");
                if (id.length == 1) {
                    var option = $.extend({}, daqsoft.config, {
                        content: daqsoft.getMainFrameURL(target, id[0]),
                        title: title
                    });
                    layer.open(option);
                    return true;
                }
                layer.alert(daqsoft.innerConfig.tipsWord);
                return false;
            }
        },
        edit: function (target) {
            daqsoft.openPanelById(target)
        },
        audit: function (target) {
            daqsoft.openPanelById(target)
        },
        /**
         *  发送post请求
         *  请求头包装xsrf-token,发送前遮挡.发送完成后取消遮挡.
         *  送数据统一json格式
         * @param url 请求地址
         * @param param 参数
         * @param dialogType 点击关闭的类型.如果值为'cancel',则不刷新页面
         */
        post: function (url, param, dialogType) {
            var loading = $(".daq-loading").daqBlockLoading({
                position: "absolute"
            });
            $.ajax({
                url: url,
                headers: {'X-XSRF-TOKEN': daqsoft.getCookie("XSRF-TOKEN")},
                dataType: "json",
                data: param,
                type: "POST",
                success: function (result) {
                    loading.hide();
                    daqsoft.result(result, dialogType);
                },
                error: function (XMLHttpRequest) {
                    loading.hide();
                    console.log(XMLHttpRequest);
                    daqsoft.result({
                        code: 1,
                        message: "操作失败,请查看控制台日志或联系管理员!"
                    }, dialogType);
                }
            });
        },
        /*delete 是IE8下是关键字.无法使用.加个S处理兼容.*/
        deletes: function (target) {
            var id = daqsoft.getSelectedId(target);
            if (id.length == 0) {
                layer.alert(daqsoft.innerConfig.tipsWord);
                return false;
            }
            layer.confirm(messages.deleteMethod, {icon: 3}, function (index) {
                    layer.close(index);
                    daqsoft.post(target.attr("url"), {
                        "ids": id.join(",")
                    });
                }, function (index) {
                    daqsoft.close(index, "cancel");
                }
            );
        },
        getBlank: function (value) {
            return value ? value : "";
        },
        view: function (target) {
            daqsoft.openPanelById(target);
        },
        getIdBylistTable: function () {
            return $("#listTable input[name='table-row']:checked");
        },
        /**
         * 关闭弹出层.
         * @param index 面板ID
         * @param type 点击关闭的类型.如果值为'cancel',则不刷新页面
         */
        close: function (index, type) {
            if (type != 'cancel') {
                var $listForm = $(daqsoft.innerConfig.listTable, parent.document);
                if (!$listForm) {
                    $listForm = $(daqsoft.innerConfig.listTable, document);
                }
                daqsoft.flush($listForm);
            }
            if (!index) {
                index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            } else {
                layer.close(index);
            }
        },
        flush: function ($listForm) {
            var data = {};
            //查询数据
            $listForm.serializeArray().forEach(function (field) {
                data[field.name] = field.value;
            });
            //这里是弹出层调用.
            var listTable = parent.daqsoft.$listTable;
            if (!listTable) {
                listTable = daqsoft.$listTable;
            }
            listTable.search({
                data: data
            });
        },
        closeByEnterKey: function () {
            document.onkeydown = function (e) {
                //如果回车.直接关闭窗口.
                var ev = document.all ? window.event : e;
                if (ev.keyCode == '13') {
                    daqsoft.close(1);
                }
            };
        },
        /**
         * validation验证通过后.调用该方法提交表单.
         * 通过表单序列化后.ajax提交到后台.
         * @param validateForm
         */
        submit: function (validateForm) {

            daqsoft.popupsOperate(validateForm,"#save","save","保存失败,没有权限!");

        },
        /**
         * 在列表页面,对某条数据进行弹窗内操作,本方法定义操作完成后的机制.
         * 事件触发后,会先判断用户是否有权限去操作.如果没有.则弹出没有权限的提示.
         * 如果有权限.则将数据推送到远程.然后关闭弹出层.
         * @param validateForm form html元素
         * @param element 权限html元素
         * @param dialogType 点击关闭的类型.如果值为'cancel',则不刷新页面
         */
        popupsOperate: function(validateForm,element,dialogType,errorMessage) {
            var $element = $(window.parent.document).find(element);
            if ($element.length == 0) {
                layer.alert(errorMessage, {
                    icon: 2,
                    closeBtn: 0
                });
            } else {
                $("<div class='daq-loading'></div>").appendTo("body");
                var param = validateForm.serialize();
                daqsoft.post($element.attr("url"), param, dialogType);
            }
        },
        result: function (result, dialogType) {
            layer.alert(result.message, {
                icon: result.code == "0" ? 1 : 2,
                closeBtn: 0
            }, function (index) {
                layer.close(index);
                daqsoft.close(null, dialogType);
            });
        },
        getCookie: function (name) {
            var cookievalue = "";
            var search = name + "=";
            if (document.cookie.length > 0) {
                var offset = document.cookie.indexOf(search);
                if (offset != -1) {
                    offset += search.length;
                    var end = document.cookie.indexOf(";", offset);
                    if (end == -1) {
                        end = document.cookie.length;
                    }
                    cookievalue = unescape(document.cookie.substring(offset, end))
                }
            }
            return cookievalue;
        },
        getMainFrameMenuId: function () {
            return $($(window.parent.document).find("#main")).attr("menuid");
        },
        /**
         * 操作权限添加时,会将URL放到元素上.这里直接从元素里取URL
         * @param target 元素
         * @param id 数据ID
         * @return string
         */
        getMainFrameURL: function (target, id) {
            var url = $(target).attr("url");
            if (id) {
                var idParam = "id=" + id;
                if (/\?/.exec(url)) {
                    return url + "&" + idParam;
                }
                return url + "?" + idParam;
            }
            return url;
        }
    };
    window.daqsoft = daqsoft;
})(jQuery, window);