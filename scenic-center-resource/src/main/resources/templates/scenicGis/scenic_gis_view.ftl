<!DOCTYPE html>
<html>
<head>
<#include "../header.ftl">
<#include "../edit.ftl">
    <title>经纬度坐标系图层</title>
    <style>
        .map-btn {
            position: absolute;
            left: 20px;
            bottom: 60px;
        }

        .map-btn li {
            width: 170px;
            height: 100px;
            float: left;
            border-radius: 10px;
            background: #183f59;
            margin-right: 13px;
            margin-bottom: 13px;
            cursor: pointer;
        }

        .map-btn li:hover, .map-btn li.curr {
            background: #1a4b6c;
        }

        .map-btn li .btn-bigbox {
            width: 150px;
            height: 70px;
            margin: 15px auto 0;
        }

        .map-btn li .btn-bigbox .btn-img {
            width: 70px;
            height: 68px;
            border-radius: 70px;
            border: 1px solid #267fb9;
            text-align: center;
            line-height: 68px;
        }

        .map-btn li .btn-bigbox .btn-img img {
            vertical-align: middle;
        }

        .map-btn li .btn-bigbox .btn-data {
            width: auto;
            height: 68px;
            margin-left: 8px;
            font: normal 14px "Microsoft YaHei";
            color: #9bcaec;
        }

        .map-btn li .btn-bigbox .btn-data p:nth-of-type(1) {
            margin-top: 10px;
        }

        .map-btn li .btn-bigbox .btn-data p:nth-of-type(2) {
            margin-top: 8px;
        }

        .map-btn li .btn-bigbox .Scenic {
            font: normal 32px "Arial";
            color: #1bd1ff;
        }

        .person-list {
            position: absolute;
            right: -300px;
            top: 29px;
            width: 300px;
            height: 100%;
            background: url("/images/supermap/mask-5.png") repeat;
            color: white;
        }

        .person-list h1 {
            padding: 10px 0;
            font-size: 16px;
            text-align: center;
            background-color: #3473b7;
        }

        .person-list .daq-table tbody tr {
            cursor: pointer;
        }

        .person-list .daq-table tbody tr:hover {
            background-color: #dddfe4;
        }

        .person-list .timeChange {
            display: inline-block;
            vertical-align: middle;
            padding: 5px 0;
        }

        .person-list span {
            font-size: 14px;
            margin-left: 8px;
        }

        .person-list .daq-date input {
            cursor: pointer;
        }
    </style>

</head>

<body>
<div style="padding:5px">
    <span>经度：</span>
    <input type="text" id="coordinatex" class="s_input w120 fom_inp" readonly/>&nbsp;&nbsp;
    <span>纬度：</span><input type="text" id="coordinatey" class="dq_Long_input" readonly/>
</div>


<!--地图显示的div-->
<div id="map" style="position: absolute; left: 0px; right: 0px; width: auto; height: 100%;">
<#--        <#if gisModule?? >
            <#list gisModule as gis>
                <span>${gis.name!}</span>
                <span>${gis.code!}</span>
            </#list>
        </#if>-->
</div>

<ul class="map-btn">
   <#if gisModule?? >
    <#list gisModule as gis>
            <li data-type="${gis.clickType}" data-code="${gis.code}" data-vcode="${gis.vcode}">
                <div class="btn-bigbox">
                    <div class="btn-img fl"><img src="${gis.image}"></div>
                    <div class="btn-data fl"><p>${gis.name!}</p>
                        <p><span class="scenc-num  Scenic">${gis.num}</span>个</p>
                    </div>
                </div>
            </li>
    </#list>
   </#if>
</ul>

<div class="person-list overflowHide">
    <h1>人员定位</h1>
    <span>请选择日期：</span>
    <div class="timeChange">
        <p class="daq-date daq-inp-rule daq-inp-date">
            <input class="date" id="person-list-start" placeholder="请选择日期" readonly/>
            <i class="sysfont date-icon"></i>
        </p>
    </div>

    <table id="person-list-table" class="daq-table daq-tab-pad">
        <thead>
        <tr>
            <th>ID</th>
            <th>name</th>
        </tr>
        </thead>
        <tbody></tbody>
    </table
</div>

<!--引用需要的脚本 注意：SuperMap.Include.js内部已经动态引用相关JS，CSS文件-->
<script type="text/javascript" src="/script/supermap/libs/SuperMap.Include.js"></script>
<script>

    var map,marker,infowin,animatorVector;
    $(function () {
        var vector;

        //声明变量 map,markerlayer,marker, layer, url,
        var markerlayer, layer, url = "${dto.url!}", backGroundColor = "${dto.backgroundColor!}",
                count = 0,
                //Markers图片路径
                mapIconUrl = {
                    scenic: '/images/supermap/scenc.png',
                    parking: '/images/supermap/stopCar.png',
                    entertainment: '/images/supermap/fun.png',
                    dining: '/images/supermap/food.png',
                    toilet: '/images/supermap/wc.png',
                    hotel: '/images/supermap/hotel.png'
                };
        var historyMarker = [], markerLineImg = [];// 记录Marker数据
        var mapInit = {
            init: function () {
                this.createMap();
                this.event();
            },
            event: function () {
                var _this = this;

                laydate.render({
                    elem: '#person-list-start',
                    value: _this.dateToString(),
                    max: _this.dateToString()
                });

                /* 点击基础数据 切换*/
                $('.map-btn').on('click', 'li', function () {
                    var _code = $(this).attr('data-code'),
                            _vcode = $(this).attr('data-vcode'),
                            _dataIcon = $(this).attr('data-type');

                    var params = {code: _code, vcode: _vcode};

                    // 如果是人员定位单独处理
                    if (_code === "personLocation") {
                        var display = $(".person-list").hasClass("overflowHide");
                        if (display == true) {
                            _this.personnelPosition(params);
                            _this.removeMapImg(markerLineImg)//清除线路起始图片
                        } else {
                            $(".person-list").animate({"right":"-300px"}).addClass("overflowHide");
                            vector.removeAllFeatures();//清除人员轨迹
                            vector.refresh();
                            _this.removeMapImg(markerLineImg)//清除线路起始图片
                        }
                    } else {
                        if (historyMarker[_code] === undefined || historyMarker[_code].length === 0) {
                            historyMarker[_code] = [];// 初始化_code为数组
                            _this.getMapData(params, _dataIcon);
                        } else {
                            _this.removeMapImg(historyMarker[_code]);
                            historyMarker[_code] = [];//清空数组数据
                        }
                    }

                });

                //点击人员定位，请求轨迹
                $(".person-list table tbody").on("click", "tr", function () {
                    var dataID = $(this).attr("data-id");
                    var dataVcode = $(this).attr("data-vcode");
                    _this.personTrack({"id": dataID, "vcode": dataVcode,"date":$("#person-list-start").val()});
                    _this.removeMapImg(markerLineImg)//清除线路起始图片
                });
            },
            //获取现在时间（系统时间）
            dateToString: function () {
                var now = new Date();
                var year = now.getFullYear();
                var month = (now.getMonth() + 1).toString();
                var day = (now.getDate()).toString();
                var hour = (now.getHours()).toString();
                var minute = (now.getMinutes()).toString();
                var second = (now.getSeconds()).toString();
                if (month.length == 1) {
                    month = "0" + month;
                }
                if (day.length == 1) {
                    day = "0" + day;
                }
                if (hour.length == 1) {
                    hour = "0" + hour;
                }
                if (minute.length == 1) {
                    minute = "0" + minute;
                }
                if (second.length == 1) {
                    second = "0" + second;
                }
                var dateTime = year + "-" + month + "-" + day;
                return dateTime;
            },
            //请求人员行走轨迹数据
            personTrack: function (data) {
                var _this = this;
                $.ajax({
                    url: 'http://192.168.2.21:2018/permitted/gisModuleInfo/getPersonLocation',
                    data: data,
                    type: 'GET',
                    crossDomain: true,
                    traditional: true,//{foo:["bar1", "bar2"]} 转换为 '&foo=bar1&foo=bar2',
                    success: function (data) {
                        var data = data.datas;
                        if (data && data.length > 0) {
                            _this.addMapLineData(data)
                        } else {
                            alert("数据出错或今日暂无数据，请选择其他日期。")
                        }

                    }, error: function () {
                        alert("人员定位数据获取失败。")
                    }
                });
            },
            addMapLineStartEndImg: function (x, y, markerIcon) {
                var size = new SuperMap.Size(40, 45);
                var offset = new SuperMap.Pixel(-(size.w / 2), -size.h);
                var icon = new SuperMap.Icon(markerIcon, size, offset);
                marker = new SuperMap.Marker(new SuperMap.LonLat(x, y), icon);
                markerlayer.addMarker(marker);//地图绘制
                markerLineImg.push(marker);// 保存marker 数据
            },
            addMapLineData: function (data) {
                var _this = this;
                vector.removeAllFeatures();//清除路线绘制
                vector.refresh();
                var points2 = [];
                for (var i = 0; i < data.length; i++) {
                    if (i == 0) {
                        _this.addMapLineStartEndImg(data[i].longitude, data[i].latitude, "/images/supermap/start.png")
                    } else if (i == data.length - 1) {
                        _this.addMapLineStartEndImg(data[i].longitude, data[i].latitude, "/images/supermap/end.png")
                    }
                    points2.push(new SuperMap.Geometry.Point(data[i].longitude, data[i].latitude))
                }
                var line1 = new SuperMap.Geometry.LineString(points2);
                var linecVector = new SuperMap.Feature.Vector(line1);
                linecVector.style = {
                    strokeColor: "#183f59",
                    strokeWidth: 2
                };
                vector.addFeatures([linecVector]);
                map.addLayers([vector]);

                animatorVector = new SuperMap.Layer.AnimatorVector("Vector Layer", {needRecordDrawedFeature: true}, {
                    speed: 0.1,
                    startTime: 0,
                    endTime: 100
                });
                map.addLayers([animatorVector]);

                _this.addPoint(data);
            },
            addPoint: function (data) {
                var features = [];
                var style =
                        {
                            fillColor: "red",
                            strokeOpacity: 0,
                            strokeWidth: 5,
                            pointRadius: 5
                        };

                console.log(data);
                for (var i = 0; i < data.length; i++) {
                    var point = new SuperMap.Geometry.Point(data[i].longitude, data[i].latitude);
                    var pointFeature = new SuperMap.Feature.Vector(point, {
                        FEATUREID: 1,
                        TIME: i*30
                    }, style);
                    features.push(pointFeature);
                }
                animatorVector.addFeatures(features);
                animatorVector.animator.start();
            },
            //请求定位人员列表数据
            personnelPosition: function (params) {
                $.ajax({
                    url: 'http://192.168.2.21:2018/permitted/gisModuleInfo/getPersonId',
                    data: {
                        vcode: params.vcode
                    },
                    type: 'GET',
                    crossDomain: true,
                    traditional: true,//{foo:["bar1", "bar2"]} 转换为 '&foo=bar1&foo=bar2',
                    success: function (data) {
                        $(".person-list tbody").empty();
                        var data = data.datas;
                        if (data.length > 0) {
                            var tr = "";
                            for (var i = 0; i < data.length; i++) {
                                tr += "<tr data-id=" + data[i].personId + "  data-vcode=" + data[i].vcode + ">" +
                                        "<td>" + (i + 1) + "</td>" +
                                        "<td>" + data[i].name + "</td>" +
                                        "<tr>";
                            }
                            $(".person-list tbody").append(tr);
                        } else {
                            $(".person-list tbody").append("<tr><td colspan='2' style='text-align: center'>暂无数据</td><tr>");
                        }

                        $(".person-list").animate({"right":0}).removeClass("overflowHide")

                    }, error: function () {
                        alert("人员定位数据获取失败。")
                    }
                });
            },
            //清除图标覆盖物
            removeMapImg: function (data) {
                for (var i = 0; i < data.length; i++) {
                    markerlayer.removeMarker(data[i]);
                }
            },
            /* 请求地图基础数据 */
            getMapData: function (options, typeIcon) {
                var _this = this;
                $.ajax({
                    url: 'http://192.168.2.21:2018/permitted/gisModuleInfo/' + options.code,
                    data: {
                        vcode: options.vcode
                    },
                    type: 'GET',
                    crossDomain: true,
                    traditional: true,//{foo:["bar1", "bar2"]} 转换为 '&foo=bar1&foo=bar2',
                    success: function (result) {
                        var dataList = result.datas,
                                lonlat = {};
                        if (dataList && dataList.length > 0) {
                            var icon = _this.getMapIcon(typeIcon);

                            for (var i = 0; i < dataList.length; i++) {
                                lonlat = {
                                    lon: dataList[i].longitude,
                                    lat: dataList[i].latitude,
                                    name: dataList[i].name
                                };
                                _this.addMarker(lonlat, icon, options.code);
                            }
                        }
                    }
                });
            },
            /* 判断选择的是基础数据类型图标 */
            getMapIcon: function (icon) {
                return mapIconUrl[icon];
            },
            //添加图标覆盖物
            addMarker: function (lonlat, markerIcon, code) {
                var _this = this;
                var size = new SuperMap.Size(43, 62);
                var offset = new SuperMap.Pixel(-(size.w / 2), -size.h);
                var icon = new SuperMap.Icon(markerIcon, size, offset);
                marker = new SuperMap.Marker(new SuperMap.LonLat(lonlat.lon, lonlat.lat), icon);
                marker.data = lonlat.name;
                markerlayer.addMarker(marker);
                marker.events.on({
                    "mouseover": openInfoWin,
                    // "mouseout": closeInfoWin,
                    // "scope": marker
                });

                (historyMarker[code]).push(marker);
            },
            //初始化超图
            createMap: function () {
                var _this = this;
                //1.在指定DOM元素上初始化地图对象。
                map = new SuperMap.Map("map");
                //2.添加控件。
                map.addControl(new SuperMap.Control.ScaleLine());
                map.addControl(new SuperMap.Control.MousePosition());

                map.events.on({"click": _this.callbackFunction});
                markerlayer = new SuperMap.Layer.Markers("markerLayer");
                vector = new SuperMap.Layer.Vector("vector");//创建路线绘制map
                //2.初始化图层。
                //创建分块动态REST图层，该图层显示iserver 8C 服务发布的地图,
                //其中"想要"为图层名称，url图层的服务地址，{transparent: true}设置到url的可选参数
                layer = new SuperMap.Layer.TiledDynamicRESTLayer("map", url, null, {maxResolution: "auto"});
                //监听图层信息加载完成事件，异步加载图层。
                layer.events.on({"layerInitialized": _this.addLayer});
                map.maxScale = 0.001;
                $("#map").css("background-color", backGroundColor);


            },
            //添加layer到涂层模块上，初始化地图参数
            addLayer: function () {
                //中心点位经度
                var centerLongitude = ${dto.centerLongitude!};
                //中心点位纬度
                var centerLatitude = ${dto.centerLatitude!};
                //缩放比例
                var mapScale = ${dto.mapScale!};
                map.addLayers([layer, markerlayer]);
                //将Layer图层加载到Map对象上。
                map.addLayers([layer]);
                //出图，设置中心点，指定放缩级别。
                map.setCenter(new SuperMap.LonLat(centerLongitude, centerLatitude), mapScale);

            },
            //地图描点(获取经纬度)
            callbackFunction: function (e) {
                var lonlat = map.getLonLatFromPixel(new SuperMap.Pixel(e.clientX, e.clientY));
                $("#coordinatex").val(lonlat.lon.toFixed(5));
                $("#coordinatey").val(lonlat.lat.toFixed(5));
                // addData(lonlat.lat.toFixed(5), lonlat.lon.toFixed(5));
            }

        };

        mapInit.init()

        //打开信息框
        function openInfoWin () {
            closeInfoWin();
            var marker=this;
            var lonlat = marker.getLonLat();
            var size = new SuperMap.Size(0, 33);
            var offset = new SuperMap.Pixel(11, -50);
            var icon = new SuperMap.Icon("", size, offset);
            var contentHTML = "<div style='width:80px; font-size:12px;font-weight:bold ; opacity: 0.8'>";
            contentHTML += marker.data ;
            contentHTML += "</div>";
            //初始化FramedCloud类
            popup = new SuperMap.Popup.FramedCloud(
                    "chicken",
                    marker.getLonLat(),
                    null,
                    contentHTML,
                    icon,
                    true,
                    null,
                    false,
            );

            infowin = popup;
            map.addPopup(popup);
        }
        //关闭信息框
        function closeInfoWin () {
            if (infowin) {
                try {
                    infowin.hide();
                    infowin.destroy();
                }
                catch (e) {
                }
            }
        }
    });
</script>

</body>
</html>