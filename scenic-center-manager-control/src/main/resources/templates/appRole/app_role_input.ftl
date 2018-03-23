<!DOCTYPE html>
<html>
<head>
<#include "../../../header.ftl">
<#include "../../../edit.ftl">
    <link rel="stylesheet"
          href="http://filealiyun.geeker.com.cn/ued/geekUI/js/zTree/v3.5.29/css/zTreeStyle/zTreeStyle.css "/>
    <script src="http://filealiyun.geeker.com.cn/ued/geekUI/js/zTree/v3.5.29/jquery.ztree.all.min.js"></script>
</head>
<body style="overflow-y: auto">
<p class="demo-title"></p><!-- 这条线为了好看放这里 -->
<div class="form-content mt-32">
    <form id="validateForm" action="">
        <input type="hidden" name="id" value="${dto.id!}"/>
        <div class="form-item-table mt-22">
            <table cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>角色名:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule fl wid-358">
                                <input type="text" name="name" value="${dto.name!}" class="s_input s_input_fb w318"
                                       validate="{required:true,maxlength:30,minlength:2}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>角色代码:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule fl wid-358">
                                <input type="text" name="code" value="${dto.code!}" class="s_input s_input_fb w318"
                                       validate="{required:true,maxlength:20,minlength:2}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>操作权限:</label></td>
                    <td>
                        <div class="checkboxs">
                            <div id="permissionTree" class="ztree"></div>
                        </div>
                    </td>
                    <input type="hidden" id="permissionId" name="permissionId">
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>全选操作:</label></td>
                    <td>
                            <span class="form-table-edit">
                                <input type="checkbox" id="checkAll"/>
                            </span>
                        全选/全不选
                    </td>
                </tr>
            <#include "../../../submit.ftl">
            </table>
        </div>
    </form>
</div>
</body>
<script>
    (function ($, window) {
        var data = ${tree!'[]'};
        var permissions =${rolePermissionList!'[]'};
        var methods = {
            data: {
                simpleData: {
                    pIdKey: "pid",
                    enable: true
                },
                key: {
                    name: "name",
                    children: "subMenus",
                    icon: ''
                }
            },
            view: {
                addDiyDom: function (treeId, treeNode) {
                    var treeObj = $.fn.zTree.getZTreeObj(treeId);
                    for (var x in permissions) {
                        var node = treeObj.getNodeByParam("id", permissions[x]);
                        if (node != null) {
                            treeObj.checkNode(node, true);
                        }
                    }
                }
            },
            check: {
                enable: true,
                chkStyle: "checkbox",
                chkboxType: {"Y": "ps", "N": "ps"}
            },
            callback: {
                onCheck: function (event, treeId, treeNode) {
                    var treeObj = $.fn.zTree.getZTreeObj(treeId);
                    zTreeOnCheck(treeObj);
                }
            }
        };

        var treeName = "permissionTree";

        function zTreeOnCheck(treeObj) {
            var checked = treeObj.getCheckedNodes(true);
            var ids = [];
            for (var i in checked) {
                ids.push(checked[i].id)
            }
            $("#permissionId").val(ids.toString());
        };

        $(function () {
            IDMark_A = "_a";
            var tree = $('#permissionTree');
            $.fn.zTree.init(tree, methods, data);

            var treeObj = $.fn.zTree.getZTreeObj(treeName);
            treeObj.expandAll(true);

            var $checkAll = $("#checkAll");
            $checkAll.click(function () {
                var $check = !!$checkAll.attr("checked");
                treeObj.checkAllNodes($check);
                zTreeOnCheck(treeObj);
            });

            $("#permissionId").val(permissions.toString());

            if ($(".checkbox_false_full").size() == 0) {
                $checkAll.attr("checked", 'true');
            }
        })
    })(jQuery, window);
</script>
</html>