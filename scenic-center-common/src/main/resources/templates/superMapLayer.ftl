<!DOCTYPE html>
<html>
<head>
<#include "header.ftl">
<#include "edit.ftl">
    <title>经纬度坐标系图层</title>
    <!--引用需要的脚本 注意：SuperMap.Include.js内部已经动态引用相关JS，CSS文件-->
    <script type="text/javascript" src="/script/supermap/libs/SuperMap.Include.js"></script>
    <script>
        //声明全局变量 map,markerlayer,marker, layer, url
        var map,markerlayer,marker, layer, url = "${dto.url!}",backGroundColor = "${dto.backgroundColor!}";
        function init() {
            //1.在指定DOM元素上初始化地图对象。
            map = new SuperMap.Map("map");
            //2.添加控件。
            map.addControl(new SuperMap.Control.ScaleLine());
            map.addControl(new SuperMap.Control.LayerSwitcher());
            map.addControl(new SuperMap.Control.MousePosition());

            map.events.on({"click":callbackFunction});
            markerlayer = new  SuperMap.Layer.Markers("markerLayer");
            //2.初始化图层。
            //创建分块动态REST图层，该图层显示iserver 8C 服务发布的地图,
            //其中"想要"为图层名称，url图层的服务地址，{transparent: true}设置到url的可选参数
            layer = new SuperMap.Layer.TiledDynamicRESTLayer("map", url, null, { maxResolution: "auto"});
            //监听图层信息加载完成事件，异步加载图层。
            layer.events.on({ "layerInitialized": addLayer });
            map.maxScale = 0.001;
            $("#map").css("background-color",backGroundColor);

        }

        //添加layer到涂层模块上，初始化地图参数
        function addLayer() {
            //中心点位经度
            var centerLongitude = ${dto.centerLongitude!};
            //中心点位纬度
            var centerLatitude = ${dto.centerLatitude!};
            //缩放比例
            var mapScale = ${dto.mapScale!};
            map.addLayers([layer,markerlayer]);
            //将Layer图层加载到Map对象上。
            map.addLayers([layer]);
            //出图，设置中心点，指定放缩级别。
            map.setCenter(new SuperMap.LonLat(centerLongitude,centerLatitude), mapScale);
        }

        //地图描点
        function callbackFunction(e)
        {
            var lonlat = map.getLonLatFromPixel(new SuperMap.Pixel(e.clientX,e.clientY));
            parent.$("#longitudeVal").val(lonlat.lon.toFixed(5));
            parent.$("#latitudeVal").val(lonlat.lat.toFixed(5));
            addData(lonlat.lat.toFixed(5),lonlat.lon.toFixed(5));
        }
        //添加数据
        function addData(x,y)
        {
            markerlayer.removeMarker(marker);
            var size = new SuperMap.Size(44,33);
            var offset = new SuperMap.Pixel(-(size.w/2-10), -(size.h));
            var icon = new SuperMap.Icon('/images/supermap/location.png', size, offset);
            marker =new SuperMap.Marker(new SuperMap.LonLat(y,x),icon) ;
            markerlayer.addMarker(marker);
        }



    </script>
</head>

<body onload="init()">
<!--地图显示的div-->
    <div id="map" style="position: absolute; left: 0px; right: 0px; width: auto; height: 100%;">
    </div>
</body>
</html>