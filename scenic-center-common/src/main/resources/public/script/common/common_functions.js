/*公共js函数*/
/**
 * 格式化日期
 * @param time
 */
function formatDate(time, pattern) {
    var currTime;
    var cTime = new Date(time);
    var year = cTime.getFullYear(), month = cTime.getMonth() + 1, day = cTime.getDate(),
        hour = cTime.getHours(), min = cTime.getMinutes(), sec = cTime.getSeconds();
    month = addZero(month);
    day = addZero(day);
    hour = addZero(hour);
    min = addZero(min);
    sec = addZero(sec);
    if(pattern == "date"){
        currTime = year + "-" + month + "-" + day;
    }else{
        currTime = year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec;
    }
    return currTime;
}

/**
 * 判断日期补齐位数
 * @param num
 */
function addZero(num) {
    if (num < 10) {
        num = "0" + num;
    }
    return num;
}

/**
 * 公共方法，列表遍历获取字典数据的name值
 * @param dictList 需要遍历的字典列表
 * @param value 字典的value,数据标识
 * @returns {string} 返回字典的中文name值
 */
function getDictName(dictList, value) {
    var html = "";
    if(value!=null) {
        if (dictList && dictList.length > 0) {
            for (var i = 0; i < dictList.length; i++) {
                var sysDict = dictList[i];
                if (value==sysDict.value) {
                    html = '<p class="over-hidden" title="' + sysDict.name + '">' + sysDict.name + '</p>';
                    break;
                }
            }
        }
    }
    return html;
}
/**
 * 图片显示DIV，对图片进行处理
 * @param imageUrl 图片路径
 * @returns {*}
 */
function getImageDiv(imageUrl) {
    if(imageUrl != null && imageUrl != ""){
        return "<ul  class='tab-preview' ><li data-url='"+imageUrl+"'><i></i>"
            +"<img src='"+imageUrl+"' alt='' /></li></ul>";
    }
    return "";
}
/**
 * 音频显示DIV，对音频进行处理
 * @param audioUrl 音频路径
 * @returns {*}
 */
function getAudioDiv(audioUrl) {
    var str;
    if (audioUrl) {
        str = '<div>' +
            '<div class="audio-preview play">' +
            '<a class="daq-btn daq-btn-cic btn-blue btn-pad-small" href="javascript:;" ><i class="sysfont">&#xe61d;</i></a>' +
            '</div>' +
            '<div data-url = "' + audioUrl + '" class="jp-jplayer" id="jp-jplayer' + i + '"></div>' +
            '</div>';
        i++;
    } else {
        str = '<div>暂无音频</div>'
    }
    return str;
}
/**
 * 视频显示DIV，对视频进行处理
 * @param value
 * @param data
 * @returns {*}
 */
function getVideoDiv(value, data) {
    if(value){
        return '<a class="daq-btn daq-btn-cic btn-blue btn-pad-small video-preview" href="javascript:;"  url='+ value +' name='+ data.videoName +'><i class="sysfont" >&#xe61d;<i></a>';
    }else{
        return '<div>暂无视频</div>';
    }
}
/**
 * js启用禁用公共方法
 * @param value
 * @returns {*}
 */
function getStatus(value) {
    if (value == 1){
        return "<i class='sysfont tab-green'>启用</i>";
    }
    return "<i class='sysfont tab-red'>禁用</i>";
}