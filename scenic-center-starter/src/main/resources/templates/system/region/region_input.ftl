<!DOCTYPE html>
<html>
<head>
<#include "../../header.ftl">
        <#include "../../edit.ftl">
</head>
<body>
<p class="demo-title"></p><!-- 这条线为了好看放这里 -->
<div class="form-content mt-32">
    <form id="validateForm" action="">
        <input type="hidden" name="id" value="${dto.id!}"/>
        <div class="form-item-table mt-22">
            <table cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="form-tab-label wid-198"><label class="form-label"><i class="form-require">*</i>地区编码:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule wid-358 mr-20">
                                <input type="text" name="region" class="s_input s_input_fb w318" validate="{required:true}" value="${dto.region!}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require">*</i>地区名称:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule wid-358">
                                <input type="text" name="name"  validate="{required:true}" class="s_input s_input_fb w318" value="${dto.name!}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require"></i>上级地区编码:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule wid-358">
                                <input type="text" name="parent" class="s_input s_input_fb w318" value="${dto.parent!}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require"></i>地区名称全称:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule wid-358">
                                <input type="text" name="memo" class="s_input s_input_fb w318" value="${dto.memo!}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require"></i>拼音全写:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule wid-358">
                                <input type="text" name="pinyin" class="s_input s_input_fb w318" value="${dto.pinyin!}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require"></i>拼音首字母:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule wid-358">
                                <input type="text" name="acronym" class="s_input s_input_fb w318" value="${dto.acronym!}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require"></i>经度:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule wid-358" style="float:left;">
                                <input type="text" name="longitude" id="longitude" class="s_input s_input_fb w318" value="${dto.longitude!}"/>
                            </div>
                            <button type="button" class="daq-btn" id="locate" style="margin-left: 10px;" >
                                <i class="sysfont">&#xe678;</i>
                                <span>定位</span>
                            </button>
                        </div>

                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require"></i>纬度:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div class="daq-inp-rule wid-358">
                                <input type="text" name="latitude" id="latitude" class="s_input s_input_fb w318" value="${dto.latitude!}"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="form-tab-label"><label class="form-label"><i class="form-require"></i>所属语言:</label></td>
                    <td>
                        <div class="form-table-edit">
                            <div id="lang" class="daq-select wid-358 fl mr-20">
                                <select name="lang">
                                <#list (languages.i18n)! as lang>
                                    <option value="${lang.VALUE!}"
                                            <#if lang.VALUE! == dto.lang!>selected</#if>
                                    >${lang.NAME!}</option>
                                </#list>
                                </select>
                            </div>
                        </div>
                    </td>
                </tr>
            <#include "../../submit.ftl">
            </table>
        </div>
    </form>
</div>
</body>
<script type="application/javascript">
    // 调用生成单选按钮方法
    $("#status").daqRadio();
    // 实例化禁用的下拉选择器
    $("#lang").daqSelect({
    });
</script>

<!-- 位置标注内容 -->
<script type="text/template" id="popUpNine">
    <div class="mess-map-head">
        <p class="mess-map-search"><input id="searchMap" type="text" value="请输入地址获取对应坐标位置" onfocus="if(value==defaultValue){value='';}" onblur="if(!value){value=defaultValue; }" /><span class="mess-map-mark"><i class="sysfont"></i></span></p>
        <a class="mess-map-btn" href="javascript:">确认</a>
    </div>
    <div class="mess-map"><div id="messMap"></div></div>
</script>
<script src="http://api.map.baidu.com/api?v=2.0&ak=9884f36684296362e68574f80a775ff5"></script>
<script>
    /*
     * iconfont: 弹窗头部图标
     * title: 弹窗标题
     * boxWidth: 弹窗宽度
     * animation: 弹窗出现动画  'fadeIn': 渐显出现，'fadeInDown': 由上而下渐显出现
     * content: 弹窗主要内容
     * btn: 定义按钮[],最多2个
     * callback: 回调方法
     * btnCallback: 按钮点击自定义事件
     * */

    /*位置标注*/
    var content9 =  $("#popUpNine");
    $("#locate").on('click',function(){
        var dialog = $.daqDialog({
            title: '位置标注',
            content: content9.html(),
            mask: true,
            width: 595,
            height: 455,
            model:'fadeInDown',
            callback: function(){
                // 百度地图API功能
                var map = new BMap.Map("messMap");
                map.centerAndZoom(new BMap.Point(104.06, 30.67), 12);
                map.enableScrollWheelZoom();   //启用滚轮放大缩小，默认禁用
                map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用

                var local = new BMap.LocalSearch(map, {
                    renderOptions:{map: map}
                });

                // 添加带有定位的导航控件
                var navigationControl = new BMap.NavigationControl({
                    // 靠左上角位置
                    anchor: BMAP_ANCHOR_TOP_LEFT,
                    // LARGE类型
                    type: BMAP_NAVIGATION_CONTROL_LARGE,
                    // 启用显示定位
                    enableGeolocation: true
                });
                map.addControl(navigationControl);
                map.addEventListener("click", function (e) {
                    var lng = e.point.lng,
                            lat = e.point.lat;
                    //通过坐标获取详细地址
                    map.clearOverlays();
                    var new_point = new BMap.Point(lng,lat);
                    var marker = new BMap.Marker(new_point);  // 创建标注
                    map.addOverlay(marker);              // 将标注添加到地图中
                    var formVal = "经度为:" + lng + ",纬度为:" + lat;
                    lgt = lng;
                    ltd = lat;
                    $("#searchMap").val(formVal);
                });
                //点击搜索查询地址
                $(".mess-map-mark").on("click",function(){
                    var searchValue = $("#searchMap").val();
                    /*
                     * /[@\/'\\"#$%&\^*]/.test(searchValue) : 判断为特殊字符
                     * !isNaN(searchValue) : 判断为数字
                     * */
                    if( searchValue == '请输入地址获取对应坐标位置' || /[@\/'\\"#$%&\^*]/.test(searchValue) || !isNaN(searchValue)){
                        $.daqMessage({
                            icon:{
                                text:'',
                                classname:'red'
                            },
                            text: '非法字符!',
                            time:2000,
                            close: true
                        });
                    }else{
                        local.search(searchValue);
                    }
                });
                var lgt = 0,ltd = 0;
                $(".mess-map-btn").click(function() {
                    if(lgt ==0 || ltd == 0) {
                        $.daqMessage({
                            icon:{
                                text:'',
                                classname:'red'
                            },
                            text: '请先标注!',
                            time:2000,
                            close: true
                        });
                    }else {
                        $("#longitude").val(lgt);
                        $("#latitude").val(ltd);
                        dialog.hide();
                    }
                })
            }
        })
    });
</script>

</html>