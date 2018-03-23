(function ($, window) {
    $(function () {

        function methodRegister() {
            //添加方法注册按钮
            $(".list-operate-add").click(function () {
                var $this = $(this);
                daqsoft.add($this);
            });
            //编辑方法注册按钮
            $(".list-operate-edit").click(function () {
                var $this = $(this);
                daqsoft.edit($this);
            });
            //删除方法注册按钮
            $(".list-operate-delete").click(function () {
                var $this = $(this);
                daqsoft.deletes($this);
            });
            //查看方法注册按钮
            $(".list-operate-view").click(function () {
                var $this = $(this);
                daqsoft.view($this);
            });
            //删除方法注册按钮
            $(".list-operate-remove").click(function () {
                var $this = $(this);
                daqsoft.deletes($this);
            });
        }

        var authenticatedOperationCache;

        //对操作进行缓存,
        function getAuthenticatedOperation() {
            if (authenticatedOperationCache) {
                return authenticatedOperationCache;
            } else {
                $.ajax({
                    type: "get",
                    data: {menuId: daqsoft.getMainFrameMenuId()},
                    url: "/permitted/getAuthenticatedOperation",
                    cache: false,
                    async: false,
                    dataType: "json",
                    success: function (result) {
                        authenticatedOperationCache = result;
                    }
                });
                return authenticatedOperationCache;
            }
        }

        // js代码
        $(".daq-crumb").daqBreadcrumb({
            url: "/permitted/getBreadCrumbNavigation?menuId=" + daqsoft.getMainFrameMenuId()
        });
        window.operateRegister = function (_tool, _op) {
            var result = getAuthenticatedOperation();
            var operates = result.datas;
            var $tool = $(_tool);
            var $op = $(_op);
            //翻页清空
            $tool.empty();
            operates.forEach(function (operate) {
                var url = operate.url;
                var op = operate.op;
                var icon = operate.icon;
                var name = operate.name;
                if (operate.toolbar) {
                    $tool.append("<a class='daq-btn list-operate-" + op + "' url='" + url + "' ><i class='sysfont'>" + icon + "</i><span>" + name + "</span></a>");
                }
                if (operate.operationBar) {
                    var color = operate.color || "blue-line";
                    $op.append("<a class='daq-tab-btn " + color + " list-operate-" + op + "' href='javascript:void(0);' url='" + url + "'>" + name + "</a>");
                }
                if (!operate.toolbar && !operate.operationBar) {
                    $tool.append("<input type='hidden' id='" + op + "' url='" + url + "' />");
                }
            });
            methodRegister();
        };
        /* ---------- List ---------- */
        var $listForm = $("#listForm");// 列表表单
        if ($listForm.length > 0) {
            $listForm.attr("method", "get");
            var $searchButton = $(".search-button");// 查找按钮
            var $input = $(".daq-inp-rule input");//关键字input
            var searchBy = $("#searchBy");
            //多选框注册
            var checkboxDiv = $(".checkboxDiv");
            checkboxDiv.daqCheckbox({isText: false});
            // 查找按钮点击
            $searchButton.click(function () {
                daqsoft.flush($listForm);
            });

            //包装list页面的搜索类型select
            $input.keyup(function (e) {
                var theEvent = e || window.event;
                var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
                if (code == 13) {
                    $searchButton.click();
                    return false;
                }
                return true;
            });
            //主页面查询类型下拉框注册
            if (searchBy) {
                searchBy.daqSelect({});
            }
            //关键字输入框效果
            $input.clearText(".daq-inp-rule");
        }
    })
})(jQuery, window);