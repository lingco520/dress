<!DOCTYPE html>
<html>
<head>
<#include "../../header.ftl">
<#include "../../edit.ftl">
    <link rel="stylesheet" href="http://filealiyun.geeker.com.cn/ued/geekUI/js/zTree/v3.5.29/css/zTreeStyle/zTreeStyle.css "/>
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
                            <div id="menuTree" class="ztree"></div>
                        </div>
                    </td>
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
            <#include "../../submit.ftl">
            </table>
        </div>
    </form>
</div>
</body>
<script>
    (function ($, window) {
        var data = ${menus1!'[]'};
        var operates =${entityOperates!};
        var methods = {
            data: {
                simpleData: {
                    pIdKey: "pid",
                    enable: true
                },
                key: {
                    name: "name",
                    children: "subMenus",
                    icon:''
                }
            },
            check: {
                enable: true,
                chkStyle: "checkbox",
                chkboxType: {"Y": "ps", "N": "ps"}
            },
            view: {
                addDiyDom: function (treeId, treeNode) {
                    var checkCount = 0;
                    var aObj = $("#" + treeNode.tId + IDMark_A);
                    var html = [];
                    var treeObj = $.fn.zTree.getZTreeObj(treeId);
                    var menuOperates = treeNode.operates;
                    if (menuOperates) {
                        for (var i = 0; i < menuOperates.length; i++) {
                            var op = treeNode.operates[i];
                            var input = "<input class='operates' type='checkbox' name='sysOperates' value='";
                            var isChecked = $.inArray(op.id, operates) != -1;
                            if (isChecked) {
                                checkCount++;
                            }
                            input += op.id + "'" + (isChecked ? "checked" : "") + ">";
                            html.push("<label style='margin:6px;padding:5px;'>" + input + op.name + "</label>");
                        }
                    }
                    aObj.after("<span id='" + treeNode.tId + "_opspan'>" + html.join("") + "</span>");
                    if (checkCount > 0) {
                        treeNode.checked = true;
                        if (checkCount < treeNode.operates.length)
                            treeNode.halfCheck = true;
                        treeObj.updateNode(treeNode, true);
                    }
                }
            },
            callback: {
                onCheck: function (event, treeId, treeNode) {
                    var treeObj = $.fn.zTree.getZTreeObj(treeId);
                    if (treeNode.children) {
                        var children = treeNode.children;
                        for (var i = 0; i < children.length; i++) {
                            var tId = children[i].tId;
                            var znote = treeObj.getNodeByTId(tId);
                            //如果菜单有三层.单点第一层时.扫描第二层.第二层再每个扫描第三层.然后选中.
                            //最多三层情况.直接判断.不使用递归
                            if (znote.children) {
                                znote.halfCheck = false;
                                treeObj.updateNode(treeNode, true);
                                var tchildren = znote.children;
                                for (var j = 0; j < tchildren.length; j++) {
                                    var tchildId = tchildren[j];
                                    check(tchildId.checked, tchildId);
                                    tchildId.halfCheck = false;  //三级层级关系.需要判断.是否全选和全不选.
                                    treeObj.updateNode(tchildId, true);
                                }
                                //中间这层需要跟据结点判断是否选种.半选.不然子类操作有导致的半选状态.不会因为会选或全不选取消
                            }
                            check(znote.checked, znote);
                            znote.halfCheck = false; //三级层级关系.需要判断.是否全选和全不选.
                            treeObj.updateNode(znote, true);
                        }
                    } else {
                        var znote = treeObj.getNodeByTId(treeNode.tId);
                        check(znote.checked, treeNode);
                    }
                    /**
                     * 在初始化时,如果操作有部分节点是未选中.父节点就会被修改为半选中.
                     * 此处操作父节点时,就需要将半选种状态取消.
                     */
                    znote.halfCheck = false;
                    treeObj.updateNode(treeNode, true);
                }
            }
        };

        function check(isChecked, obj) {
            $("#" + obj.tId + "_opspan").find('input[name=\'sysOperates\']').prop("checked", isChecked);
        }

        /**
         * 操作选中时.控制父节点是否也需要选中.
         */
        function checkParentStatus() {
            var $this = $(this);
            var inputArr = $this.parents("span").find("input[name='sysOperates']");
            var checkedCount = 0;
            var id = $this.parents("li").attr("id");
            var treeObj = $.fn.zTree.getZTreeObj(treeName);
            var treeNode = treeObj.getNodeByTId(id);
            for (var i = 0; i < inputArr.length; i++) {
                var obj = inputArr[i];
                if (obj.checked == true)
                    checkedCount++
            }
            if (checkedCount > 0) {  //判断节点是否需要选中.
                treeNode.checked = true;
                treeNode.halfCheck = checkedCount < inputArr.length;
                treeObj.updateNode(treeNode, true);
            } else if (checkedCount == 0) {
                treeNode.halfCheck = false;
                treeNode.checked = false;
                treeObj.updateNode(treeNode, true);
            }
            checkeChkButton();
        }

        function halfCheck(treeObj, element) {
            var tId = element.tId;
            var znote = treeObj.getNodeByTId(tId);
            znote.halfCheck = false;
            treeObj.updateNode(znote, true);
        }

        //点权限时,检查是否全选也要选中.
        function checkeChkButton() {
            var chkAll = $("#checkAll");
            chkAll.attr("checked", false);
            var correlateAllCheckButton = true;
            $(".operates").each(function () {
                var checked = $(this).attr("checked");
                if (!checked) {
                    correlateAllCheckButton = checked;
                    return false;
                }
            });
            chkAll.attr("checked", correlateAllCheckButton);
        }

        var treeName = "menuTree";
        $(function () {
            IDMark_A = "_a";
            var tree = $('#menuTree');
            $.fn.zTree.init(tree, methods, data);
            var $checkAll = $("#checkAll");
            $checkAll.click(function () {
                var $check = !!$checkAll.attr("checked");
                $(".operates").attr("checked", $check);
                var treeObj = $.fn.zTree.getZTreeObj(treeName);
                var nodes = treeObj.getNodes();
                for (var i = 0; i < nodes.length; i++) {
                    var node = nodes[i];   //最顶层菜单
                    if (node.children) {   //判断是否有第二层
                        var children = node.children;
                        //处理第二层选中状态
                        for (var j = 0; j < children.length; j++) {
                            var son = children[j];
                            halfCheck(treeObj, son);
                            if (son.children) { //判断是否有第三层
                                var grandChildren = son.children;
                                for (var k = 0; k < grandChildren.length; k++) {
                                    var cId = grandChildren[k].tId;
                                    var cznote = treeObj.getNodeByTId(cId);
                                    cznote.halfCheck = false;
                                    treeObj.updateNode(cznote, true);
                                }
                            }
                        }
                    }
                }
                $.fn.zTree.getZTreeObj(treeName).checkAllNodes($check);
            });
            var $inputOperations = $('input[name=\'sysOperates\']');
            //每个权限点击时,检测父类需要选中或者半选
            $inputOperations.click(checkParentStatus);
            if ($inputOperations.length == $('input[name=\'sysOperates\']:checked').length)
                $checkAll.attr("checked", 'true');
        })
    })(jQuery, window);
</script>
</html>