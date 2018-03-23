<tr>
    <td class="form-tab-label"><label class="form-label">地图类型:</label></td>
    <td>
        <div class="form-table-edit">
            <div class="choose-box clearfix" id="mapType">
                <label>
                    <input type="radio" name="mapType" value="2" title="高德"
                           <#if (dto.mapType!2) == 2>checked</#if>>
                    <input type="radio" name="mapType" value="0" title="GIS"
                           <#if (dto.mapType!1) == 0>checked</#if>>
                </label>
            </div>
        </div>
    </td>
</tr>
<!--经纬度描绘 start-->
<tr id="mapDiv">
<#if (dto.mapType!2) == 2>
    <td class="form-tab-label wid-198 "><label class="form-label">高德经纬度选择:</label></td>
    <td>
        <div class="form-table-edit clearfix">
            <div class="fl">
                <div class="daq-inp-rule wid-78 ml-5 fl">
                    <input class="longitudeGd" type="text" placeholder="经度"
                           value="${dto.longitude!''}" readonly>
                </div>
                <div class="daq-inp-rule wid-78 ml-5 fl">
                    <input class="latitudeGd" type="text" placeholder="纬度"
                           value="${dto.latitude!''}" readonly>
                </div>
                <button id="mbBox9" class="daq-btn-line blue-line ml-5"
                        onclick="getLongAndLatGDGIS('longitude','latitude')" type="button">标注
                </button>
            </div>
            <div class="fl">
                <input type="hidden" name="longitude" value="${dto.longitude!''}"
                       id="longitude"/>
                <input type="hidden" name="latitude" value="${dto.latitude!''}" id="latitude"/>
            </div>
    </td>
<#else>
    <td class="form-tab-label wid-198 "><label class="form-label">GIS经纬度选择:</label></td>
    <td>
        <div class="form-table-edit clearfix">
            <div class="fl">
                <div class="daq-inp-rule wid-78 ml-5 fl">
                    <input class="longitudeGis" type="text" placeholder="经度"
                           value="${dto.longitude!''}" readonly>
                </div>
                <div class="daq-inp-rule wid-78 ml-5 fl">
                    <input class="latitudeGis" type="text" placeholder="纬度"
                           value="${dto.latitude!''}" readonly>
                </div>
                <button id="mbBox9" class="daq-btn-line blue-line ml-5"
                        onclick="getGisMapLongLat('longitude','latitude')" type="button">标注
                </button>
            </div>
            <div class="fl">
                <input type="hidden" name="longitude" value="${dto.longitude!''}" id="longitude"/>
                <input type="hidden" name="latitude" value="${dto.latitude!''}" id="latitude"/>
            </div>
        </div>
    </td>
</#if>
</tr>
<#include "common_coordinate_gaode.ftl"><!-- 地图引用 -->
<#include "common_superMap.ftl"> <!-- GIS超图引用 -->
<script type="application/javascript">
    var gisDiv =    '<td class="form-tab-label wid-198 "><label class="form-label">GIS经纬度选择:</label></td>'+
            '<td>'+
            '<div class="form-table-edit clearfix">'+
            '<div class="fl">'+
            '<div class="daq-inp-rule wid-78 ml-5 fl">'+
            '<input class="longitudeGis" type="text" placeholder="经度" value="" readonly>'+
            '</div>'+
            '<div class="daq-inp-rule wid-78 ml-5 fl">'+
            '<input class="latitudeGis" type="text" placeholder="纬度" value="" readonly>'+
            '</div>'+
            '<button id="mbBox9" class="daq-btn-line blue-line ml-5" onclick="getGisMapLongLat(\'longitude\',\'latitude\')" type="button">标注</button>'+
            '</div>'+
            '<div class="fl">'+
            '<input type="hidden" name="longitude" value="" id="longitude"/>'+
            '<input type="hidden" name="latitude" value="" id="latitude"/>'+
            '</div>'+
            '</div>'+
            '</td>';

    var gaodeDiv =  '<td class="form-tab-label wid-198 "><label class="form-label">高德经纬度选择:</label></td>'+
            '<td>'+
            '<div class="form-table-edit clearfix">'+
            '<div class="fl">'+
            '<div class="daq-inp-rule wid-78 ml-5 fl">'+
            '<input class="longitudeGd" type="text" placeholder="经度" value="" readonly>'+
            '</div>'+
            '<div class="daq-inp-rule wid-78 ml-5 fl">'+
            '<input class="latitudeGd" type="text" placeholder="纬度" value="" readonly>'+
            '</div>'+
            '<button id="mbBox9" class="daq-btn-line blue-line ml-5" onclick="getLongAndLatGDGIS(\'longitude\',\'latitude\')" type="button">标注</button>'+
            '</div>'+
            '<div class="fl">'+
            '<input type="hidden" name="longitude" value="" id="longitude"/>'+
            '<input type="hidden" name="latitude" value="" id="latitude"/>'+
            '</div>'+
            '</td>';

    // 地图选择按钮
    $("#mapType").daqRadio({
        callback: function (data, obj) {
            if (data == 0) {
                $("#mapDiv").html(gisDiv);
            } else {
                $("#mapDiv").html(gaodeDiv);
            }
        }
    });
    $().ready(function () {
        var mapType = "${dto.mapType!2}";
        if(mapType == 2){
            $(".longitudeGd").val("${dto.longitude!}");
            $(".latitudeGd").val("${dto.latitude!}");
            $("#longitude").val("${dto.longitude!}");
            $("#latitude").val("${dto.latitude!}");
        }else{
            $(".longitudeGis").val("${dto.longitude!}");
            $(".latitudeGis").val("${dto.latitude!}");
            $("#longitude").val("${dto.longitude!}");
            $("#latitude").val("${dto.latitude!}");
        }


    })
</script>