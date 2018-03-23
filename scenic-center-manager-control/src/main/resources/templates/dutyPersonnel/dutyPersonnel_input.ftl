<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
<#include "../header.ftl">
<#include "../edit.ftl">
    <script type="text/javascript" src="/script/jQuery/jquery.lSelect.js"></script>
    <style>
        select {
            width: 150px;
            padding: 5px;
            font-size: 14px;
            border: 1px solid #ccc;
            height: 34px;
        }
    </style>
</head>
<body>
<div class="section-content">
    <div class="section-son-content pad-bot0">
        <p class="demo-title"></p><!-- 这条线为了好看放这里 -->
        <div class="form-content mt-32">
            <form id="validateForm" action="">
                <input type="hidden" name="id" value="${dto.id!}"/>
                <div class="form-item-table mt-22">
                    <table cellpadding="0" cellspacing="0" width="100%">

                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>人员名称:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" name="name" class="s_input s_input_fb w318" validate="{required:true,maxlength:20}" value="${dto.name!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>性别:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="gender" class="daq-select wid-198 fl mr-20">
                                        <select name="sex" validate="{required:true}">
                                        <#if gender??>
                                            <#list gender as t>
                                                <option value="${t.value!}"
                                                        <#if t.value == (dto.sex)!''>selected</#if>>${t.name!}</option>
                                            </#list>
                                        </#if>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">出生日期:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" readonly="readonly" name="birthday" id="birthday" class="s_input s_input_fb w281 js_tooltip"  skin="dark" data-tips="请选择时间" position="bottom-left" value="${dto.birthday!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>电话:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-158 fl">
                                        <input type="text" name="phone" validate="{required:true,phone:true}" class="s_input w120 fom_inp" value="${dto.phone!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>级别:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="level" class="daq-select wid-198 fl mr-20">
                                        <select name="slevel" id="level">
                                            <option value="1" <#if '1' == (dto.slevel)!>selected</#if>>领导
                                            </option>
                                            <option value="2" <#if '2' == (dto.slevel)!>selected</#if>>员工
                                            </option>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>所属部门:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="department" class="daq-select wid-198 fl mr-20">
                                            <select name="departmentId" validate="{required:true}">
                                            <#if department??>
                                                <#list department as d>
                                                    <option value="${d.value!}"
                                                            <#if d.value == (dto.departmentId)!''>selected</#if>>${d.name!}</option>
                                                </#list>
                                            </#if>
                                            </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>所属平台账号:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="manager" class="daq-select wid-198 fl mr-20">
                                        <select name="manager" validate="{required:true}">
                                        <#if manager??>
                                            <#list manager as m>
                                                <option value="${m.value!}"
                                                        <#if m.value == (dto.account)!''>selected</#if>>${m.name!}</option>
                                            </#list>
                                        </#if>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>职位:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="duty" class="daq-select wid-198 fl mr-20">
                                        <select name="duty" validate="{required:true}">
                                        <#if duty??>
                                            <#list duty as d>
                                                <option value="${d.value!}"
                                                        <#if d.value == (dto.duty)!''>selected</#if>>${d.name!}</option>
                                            </#list>
                                        </#if>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label"><i
                                    class="form-require">*</i>学历:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div id="edu" class="daq-select wid-198 fl mr-20">
                                        <select name="edu" validate="{required:true}">
                                        <#if edu??>
                                            <#list edu as e>
                                                <option value="${e.value!}"
                                                        <#if e.value == (dto.edu)!''>selected</#if>>${e.name!}</option>
                                            </#list>
                                        </#if>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">入职时间:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" readonly="readonly" name="entry" id="entry" class="s_input s_input_fb w281 js_tooltip"  skin="dark" data-tips="请选择时间" position="bottom-left" value="${dto.entry!}" />
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">工龄:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="daq-inp-rule wid-358">
                                        <input type="text" readonly name="workingtime" id="workingtime" class="s_input s_input_fb w318"  value="${dto.workingtime!}"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tab-label"><label class="form-label">状态:</label></td>
                            <td>
                                <div class="form-table-edit">
                                    <div class="choose-box clearfix" id="status">
                                        <input type="radio" name="status" value="1" title="正常"
                                               <#if (dto.status!1) == 1>checked</#if>>
                                        <input type="radio" name="status" value="0" title="禁用"
                                               <#if (dto.status!1) == 0>checked</#if>>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    <#include "../submit.ftl">
                    </table>
                </div>
            </form>
        </div>
    </div>
</div>
</body>

<script type="application/javascript">
    $("#gender").daqSelect({
        value: '${dto.gender!}',
        callback: function (data) {
        }
    });
    $("#department").daqSelect({
        value: '${dto.departmentId!}',
        callback: function (data) {
        }
    });
    $("#level").daqSelect({
        value: '${dto.slevel!'1'}',
        callback: function (data) {
        }
    });
    $("#manager").daqSelect({
        value: '${dto.manager!}',
        callback: function (data) {
        }
    });
    $("#duty").daqSelect({
        value: '${dto.duty!}',
        callback: function (data) {
        }
    });
    $("#edu").daqSelect({
        value: '${dto.edu!}',
        callback: function (data) {
        }
    });

    $("#status").daqRadio();
    // 复选按钮绑定样式
    $("#messageType").daqCheckbox();
    //时间选择器
    laydate.render({
        elem: '#birthday'
        ,type: 'date'
    });
    laydate.render({
        elem: '#entry'
        ,type: 'date'
    });

    //计算工龄
    function jsGetAge(){
        var strBirthday = $("#entry").val();
        var returnAge;
        var strBirthdayArr=strBirthday.split("-");
        var birthYear = strBirthdayArr[0];
        var birthMonth = strBirthdayArr[1];
        var birthDay = strBirthdayArr[2];

        d = new Date();
        var nowYear = d.getFullYear();
        var nowMonth = d.getMonth() + 1;
        var nowDay = d.getDate();

        if(nowYear == birthYear){
            returnAge = 0;//同年 则为0
        }
        else{
            var ageDiff = nowYear - birthYear ; //年之差
            if(ageDiff > 0){
                if(nowMonth == birthMonth) {
                    var dayDiff = nowDay - birthDay;//日之差
                    if(dayDiff < 0)
                    {
                        returnAge = ageDiff - 1;
                    }
                    else
                    {
                        returnAge = ageDiff ;
                    }
                }
                else
                {
                    var monthDiff = nowMonth - birthMonth;//月之差
                    if(monthDiff < 0)
                    {
                        returnAge = ageDiff - 1;
                    }
                    else
                    {
                        returnAge = ageDiff ;
                    }
                }
                $("#workingtime").val(returnAge);
            }
            else
            {
                alert("时间格式错误！");//返回-1 表示出生日期输入错误 晚于今天
            }
        }

        $("#workingtime").val(returnAge);//返回周岁年龄

    }
</script>
</html>