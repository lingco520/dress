<!--经纬度描绘 start-->
<tr>
    <td class="form-tab-label wid-198 "><label class="form-label">经纬度选择:</label></td>
    <td>
        <div class="form-table-edit clearfix">
            <div class="fl">
                <div class="daq-inp-rule wid-78 ml-5 fl">
                    <input class="longitude" name="longitude" type="text" placeholder="经度"
                           value="${dto.longitude!''}">
                </div>
                <div class="daq-inp-rule wid-78 ml-5 fl">
                    <input class="latitude" name="latitude" type="text" placeholder="纬度"
                           value="${dto.latitude!''}">
                </div>
                <button id="mbBox9" class="daq-btn-line blue-line ml-5"
                        onclick="getLongAndLat('longitude','latitude')" type="button">标注
                </button>
            </div>
    </td>
</tr>
<#include "common_coordinate.ftl"><!-- 百度地图引用 -->