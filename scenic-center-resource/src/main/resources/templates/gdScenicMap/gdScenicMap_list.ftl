<!DOCTYPE html>
<html>
<head>
<#include "../header.ftl">
<#include "../admin.ftl">
<#include "../edit.ftl">
    <title>高德地图</title>

    <style>
        body,html,#container{
            height: 100%;
            margin: 0px;
        }
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
<!--内容 -->
<div id="container" tabindex="0"></div>
<!--当前位置 面包导航-->
<div class="system-top">
    <p class="daq-crumb clearfix"></p>
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

<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.4.4&key=667bb65e484d2df284d6c4053571df37"></script>
<!-- UI组件库 1.0 -->
<script src="//webapi.amap.com/ui/1.0/main.js?v=1.0.11"></script>

<script type="text/javascript">
    //地区经度
    var lon = ${dto.longitude!};
    //地区纬度
    var lat = ${dto.latitude!};
    var map, marker, infoWindow, pathSimplifierIns, serverUrl = 'http://192.168.2.21:2018/permitted/gisModuleInfo/',
        historyMarker = [], markerLineImg = [],// 记录Marker数据
        mapIconUrl = {
            scenic: '/images/supermap/scenc.png',
            parking: '/images/supermap/stopCar.png',
            entertainment: '/images/supermap/fun.png',
            dining: '/images/supermap/food.png',
            toilet: '/images/supermap/wc.png',
            hotel: '/images/supermap/hotel.png'
        };

    $(function () {
        gdMap.init();
    });

    var gdMap = {
        init: function () {
            this.eventClick();
            this.createMap();
        },
        /* 判断选择的是基础数据类型图标 */
        getMapIcon: function (icon) {
            return mapIconUrl[icon];
        },
        //点击请求地图基础数据
        eventClick: function () {
            var _this = this;
            //时间
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
                        // vector.removeAllFeatures();//清除人员轨迹
                        // vector.refresh();
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
        //请求基础数据
        getMapData: function (options, typeIcon) {
            var _this = this;
            $.ajax({
                url: serverUrl + options.code,
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
        //请求定位人员列表数据
        personnelPosition: function (params) {
            $.ajax({
                url: serverUrl + 'getPersonId',
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
        //请求人员行走轨迹数据
        personTrack: function (data) {
            var _this = this;
            $.ajax({
                url: serverUrl + 'getPersonLocation',
                data: data,
                type: 'GET',
                crossDomain: true,
                traditional: true,//{foo:["bar1", "bar2"]} 转换为 '&foo=bar1&foo=bar2',
                success: function (result) {
                    var data = result.data;
                    if (data.datas && data.datas.length > 0) {
                        _this.personTrackLineMap(data)
                    } else {
                        alert("数据出错或今日暂无数据，请选择其他日期。")
                    }

                }, error: function () {
                    alert("人员定位数据获取失败。")
                }
            });
        },
        //创建地图
        createMap: function () {
            map = new AMap.Map('container',{
                resizeEnable: true,
                zoom: 14,
                center: [lon, lat]
            });
        },
        //添加覆盖物
        addMarker: function (lonlat, markerIcon, code) {
            if (lonlat.lon && lonlat.lat) {
                marker = new AMap.Marker({
                    icon: markerIcon,
                    map: map,
                    position: [lonlat.lon, lonlat.lat]
                });
                map.setZoomAndCenter(15, [lonlat.lon, lonlat.lat]);
                (historyMarker[code]).push(marker);
                //鼠标点击marker弹出自定义的信息窗体
                AMap.event.addListener(marker, 'mouseover', function() {
                    gdMap.openInfoWindow(lonlat);
                });
            }
        },
        //清除图标覆盖物
        removeMapImg: function (data) {
            map.remove(data);
        },
        //打开信息窗口
        openInfoWindow: function (lonlatIW) {
            //构建信息窗体中显示的内容
            var info = '';
            info = "<div>"+ lonlatIW.name +"</div>";
            infoWindow = new AMap.InfoWindow({
                content: info,  //使用默认信息窗体框样式，显示信息内容
                offset: new AMap.Pixel(20,-15)
            });
            infoWindow.open(map, [lonlatIW.lon, lonlatIW.lat]);
        },
        //人员走动轨迹
        personTrackLineMap: function (dataMap) {
            var linePath = [], distance = dataMap.sumDistance,
                data= dataMap.datas ;
            if (pathSimplifierIns) {
                pathSimplifierIns.hide();
            }

            //请求轨迹
            AMapUI.load(['ui/misc/PathSimplifier', 'lib/$'], function(PathSimplifier, $) {

                if (!PathSimplifier.supportCanvas) {
                    alert('当前环境不支持 Canvas！');
                    return;
                }
                //默认路径为空
/*                var emptyLineStyle = {
                    lineWidth: 0,
                    fillStyle: null,
                    strokeStyle: null,
                    borderStyle: null
                };*/
                //绘制轨迹
                pathSimplifierIns = new PathSimplifier({
                    zIndex: 100,
                    autoSetFitView: true,
                    map: map, //所属的地图实例
                    getPath: function(pathData, pathIndex) {
                        return pathData.path;
                    },
                    renderOptions: {
                        renderAllPointsIfNumberBelow: 100
                        //将点、线相关的style全部置emptyLineStyle
/*                        pathLineStyle: emptyLineStyle,
                        pathLineSelectedStyle: emptyLineStyle,
                        pathLineHoverStyle: emptyLineStyle,
                        keyPointStyle: emptyLineStyle,
                        startPointStyle: emptyLineStyle,
                        endPointStyle: emptyLineStyle,
                        keyPointHoverStyle: emptyLineStyle,
                        keyPointOnSelectedPathLineStyle: emptyLineStyle*/
                    }
                });

                window.pathSimplifierIns = pathSimplifierIns;

                for (var i = 0; i < data.length; i++) {
                    linePath.push([data[i].longitude, data[i].latitude]);
/*                    if (i + 1 < data.length - 1) {
                        var lnglat1 = new AMap.LngLat(data[i].longitude, data[i].latitude);
                        var lnglat2 =  new AMap.LngLat(data[i+1].longitude, data[i+1].latitude);
                        distance += Math.floor(lnglat1.distance(lnglat2));
                    }*/
                }
                //设置数据
                pathSimplifierIns.setData([{
                    name: '路线0',
                    path: linePath
                }]);
                //对第一条线路（即索引 0）创建一个巡航器
                var navg = pathSimplifierIns.createPathNavigator(0, {
                    loop: true, //循环播放
                    speed: distance,
                    pathNavigatorStyle: {
                        //经过路径的样式
                        pathLinePassedStyle: {
                            lineWidth: 5,
                            strokeStyle: '#12b7f5',
                            dirArrowStyle: {
                                stepSpace: 12,
                                strokeStyle: '#ff8416'
                            }
                        }
                    }
                });
                navg.start();

            });
        }
    };



</script>

</body>

</html>