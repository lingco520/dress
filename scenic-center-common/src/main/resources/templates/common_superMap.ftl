<!DOCTYPE html>
<html>
<head>
</head>
<body>
<input type="hidden" id="longitudeIdDiv" value=""/>
<input type="hidden" id="latitudeIdDiv" value=""/>
<script type="text/template" id="gisMapLayer">
    <!-- 值的div放到layer里面，不要放到地图渲染里面去，不然会导致定位有一丢丢不准确 -->
    <div style="padding:5px">
        <span>经度：</span>
        <input type="text" id="longitudeVal" class="dq_Long_input" readonly/>&nbsp;&nbsp;
        <span>纬度：</span><input type="text" id="latitudeVal" class="dq_Long_input" readonly/>
        <input type="button" class="daq-btn daq-btn-cic"  value="确定" onclick="getLongLat()"/>
    </div>
    <div id="gisMapDiv">
        <iframe frameborder="0" src="/permitted/common/getGisMap" style="position: absolute; width: 100%; height: 100%;"></iframe>
    </div>
</script>
<script>
            /**
             * 超图经纬度获取公共方法，调用方法的页面引入此 ftl
             * 注意，因为现在涉及多个地图，数据库设计字段都是  longitude   latitude，通过type类型来区分，
             * 所以存在隐藏域的情况， 下面的 传参 lngId  latId  是页面上的经纬度  id class 两个属性值
             * @param lngId 储存经度的元素ID
             * @param latId 储存纬度的元素ID
             */
            function getGisMapLongLat(lngId, latId) {
                $("#longitudeIdDiv").val(lngId)
                $("#latitudeIdDiv").val(latId)
                $.daqDialog({
                    iconfont: '&#xe6e2;',
                    title: 'GIS',
                    content: $('#gisMapLayer').html(),
                    mask: true,
                    width: 900,
                    height: 700,
                    model:'fadeInDown',
                    callback: function(){
                        // 回调让超图 div图层显示
                        $('#gisMapDiv').show();
                    }
                });
            }
            /**
             * 点击确认后，将经纬度值传到页面上，用于数据保存
             */
            function getLongLat(){
                var x= $("#longitudeVal").val();
                var y= $("#latitudeVal").val();
                var lngId = $("#longitudeIdDiv").val();
                var latId = $("#latitudeIdDiv").val();
                //赋值经纬度
                $("#"+lngId).val(x);
                $("#"+latId).val(y);
                $("."+lngId+"Gis").val(x);
                $("."+latId+"Gis").val(y);
                $(".mess-close").click();
            }
</script>
</body>
</html>