<#--百度密匙-->
<script src="http://api.map.baidu.com/api?v=2.0&ak=9884f36684296362e68574f80a775ff5"></script>
<!-- 模板生成地理位置标注 -->
<script type="text/template" id="popUpNine">
    <div class="mess-map-head">
        <p class="mess-map-search">
            <input id="searchMap" type="text" value="请输入地址获取对应坐标位置" onfocus="if(value==defaultValue){value='';}"
                   onblur="if(!value){value=defaultValue; }"/><span class="mess-map-mark"><i
                class="sysfont"></i></span></p> <!-- e626; -->
        <a class="mess-map-btn" href="javascript:;">确认</a>
    </div>
    <div class="mess-map">
        <div id="messMap"></div>
    </div>
</script>
<script>
    /*位置标注*/
    var content9 =  $("#popUpNine");
    /**
     * 获取经纬度坐标
     * @param lngId 储存经度的元素的 class
     * @param latId 储存纬度的元素的 class
     */
    function getLongAndLat(lngId, latId) {
        var lngVal, latVal;
        $.daqDialog({
            title: '位置标注',
            content: content9.html(),
            mask: true,
            width: 595,
            height: 455,
            model:'fadeInDown',
            callback: function(){
                // 百度地图API功能
                var map = new BMap.Map("messMap");
                var defIng = $("." + lngId).val();
                var defIat = $("." + latId).val();
                if (defIng && defIng != "" && defIat && defIat != "") { //选中默认值
                    map.centerAndZoom(new BMap.Point(defIng, defIat), 14);
                    var new_point = new BMap.Point(defIng, defIat);
                    var marker = new BMap.Marker(new_point);  // 创建标注
                    map.addOverlay(marker);              // 将标注添加到地图中
                    map.panTo(new_point);
                    var formVal = "经度为:" + defIng + ",纬度为:" + defIat;
                    $("#searchMap").val(formVal);
                    lngVal = defIng, latVal = defIat;
                } else { //没有默认值时定位当前站点所在地区
                    map.centerAndZoom("${CURRENT_REGION!''}", 14);
                }
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
                    map.panTo(new_point);
                    var formVal = "经度为:" + lng + ",纬度为:" + lat;
                    $("#searchMap").val(formVal);
                    lngVal = lng;
                    latVal = lat;
                });
                //点击搜索查询地址
                $(".mess-map-mark").on("click",function(){
                    var searchValue = $("#searchMap").val();
                    /*
                     * /[@\/'\\"#$%&\^*]/.test(searchValue) : 判断为特殊字符
                     * !isNaN(searchValue) : 判断为数字
                     * */
                    if( searchValue == '请输入地址获取对应坐标位置' || /[@\/'\\"#$%&\^*]/.test(searchValue) || !isNaN(searchValue)){
                        alert("请输入正确地址！");
                    }else{
                        local.search(searchValue);
                    }
                });
                //设置添加标注后的回调函数
                local.setMarkersSetCallback(function(data){
                    for(var i=0;i<data.length;i++){
                        data[i].marker.addEventListener("click", attribute)
                    }
                });
                //获取覆盖物位置
                function attribute(e){
                    //alert("marker的位置是" + e.target.getPosition().lng + "," + e.target.getPosition().lat);
                    var formLongitude = e.target.getPosition().lng,
                            formLatitude = e.target.getPosition().lat;
                    var formVal = "经度为:" + formLongitude + ",纬度为:" + formLatitude;
                    $("#searchMap").val(formVal);
                    lngVal = formLongitude;
                    latVal = formLatitude;
                }
                // 用户点击确认按钮后数据写入页面
                $(".daq-message-box").on('click', '.mess-map-btn', function() {
                    $("."+lngId).val(lngVal);
                    $("."+latId).val(latVal);
                    $(".daq-message-box").remove();
                    $(".mess-mask").remove();
                });
            }
        });
    }

</script>




