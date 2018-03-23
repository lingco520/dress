<#--高德密匙-->
<script src="http://webapi.amap.com/maps?v=1.4.4&key=667bb65e484d2df284d6c4053571df37"></script>
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
     * @param lngId
     * @param latId
     */
    function getLongAndLatGDGIS(lngId, latId) {
        var lngVal, latVal;
        $.daqDialog({
            title: '位置标注',
            content: content9.html(),
            mask: true,
            width: 595,
            height: 455,
            model:'fadeInDown',
            callback: function () {
                //初始化地图对象，加载地图
                var marker, placeSearch;
                var map = new AMap.Map("messMap", {
                    resizeEnable: true
                });
                // 回显的话直接调用addMarker();
                //addMarker(104.053375, 30.904091);
                map.on('click', function(e) {
                    var lng = e.lnglat.getLng(),
                            lat = e.lnglat.getLat();
                    $('#searchMap').val("经度为:" + lng + ",纬度为:" + lat);
                    lngVal = lng;
                    latVal = lat;
                    addMarker(lng, lat)
                });
                AMap.service(["AMap.PlaceSearch"], function() {
                    placeSearch = new AMap.PlaceSearch({ //构造地点查询类
                        pageSize: 5,
                        pageIndex: 1,
                        city: "010", //城市
                        map: map,
                        panel: "panel"
                    });
                });
                // 关键字搜索
                $(".daq-message-box").on('click', '.mess-map-mark', function () {
                    var searchVal = $('#searchMap').val();
                    placeSearch.search(searchVal);
                });
                // 搜索结果标记添加监听事件
                AMap.event.addListener(placeSearch, "markerClick", function(e){
                    var lng = e.data.location.lng,
                            lat = e.data.location.lat;
                    $('#searchMap').val("经度为:" + lng + ",纬度为:" + lat);
                    lngVal = lng;
                    latVal = lat;
                })
                // 用户点击确认按钮后数据写入页面
                $(".daq-message-box").on('click', '.mess-map-btn', function () {
                    // 百度地图回显input框的 class属性，文本框数据回显
                    $("."+lngId + "Gd").val(lngVal);
                    $("."+latId + "Gd").val(latVal);
                    console.log(lngVal)
                    console.log(lngId)
                     $("#"+lngId).val(lngVal);
                    $("#"+latId).val(latVal);
                    $(".daq-message-box").remove();
                    $(".mess-mask").remove();
                });
                // 实例化点标记
                function addMarker(lng, lat) {
                    // 判断如果有标记则先清除
                    if (marker) {
                        marker.setMap(null);
                        marker = null;
                    }
                    // 清除搜索结果的标记
                    placeSearch.clear();
                    marker = new AMap.Marker({
                        icon: "http://webapi.amap.com/theme/v1.3/markers/n/mark_r.png",
                        position: [lng, lat]
                    });
                    marker.setMap(map);
                }
            }
        });
    }

</script>




