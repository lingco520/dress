/**
 * 生成UUID
 * @return {string}
 */
function uuid(length) {
    if (!length) {
        length = 32;
    }
    var radix = 16;
    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
    var uuid = [], i;
    radix = radix || chars.length;
    if (length) {
        for (i = 0; i < length; i++)uuid[i] = chars[0 | Math.random() * radix];
    } else {
        var r;
        uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
        uuid[14] = '4';
        for (i = 0; i < 36; i++) {
            if (!uuid[i]) {
                r = 0 | Math.random() * 16;
                uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
            }
        }
    }
    return uuid.join('');
}
/**
 *      ---------------------多张图片选择-------------------
 *
 * @param selecterDivId 资源选择盒子最外层DIV的ID，里面的选择元素会动态初始化。
 * @param valueBoxId    生成的隐藏input 的name
 * @param defaultValue  默认值，逗号分隔
 * @param limitNum      选择限制.
 * @param limitNum      选择限制.
 * @param size 图片上传大小限制，如果不传，默认限制为 4*1024*1024
 */
function initMorePicUploader(selecterDivId, defaultValue, limitNum, valueBoxId, size) {
    // 图片上传大小限制处理
    if(!size){
        size = 4*1024*1024;
    }else{
        size = size*1024*1024;
    }
    // 设置默认的选择限制
    if (!limitNum) {
        limitNum = 1;
    }
    var maxLimitNum = limitNum;
    var inpName = 'url';
    if(valueBoxId){
        inpName = valueBoxId;
    }
    // 检查参数，声明上传盒子的容器
    var selecterBox = $("#" + selecterDivId);

    if (!selecterBox) {
        return;
    }
    // 动态生成上传盒子的基本HTML结构
    var pickerId = uuid();
    var resourceListId = uuid();
    var $list = $('<ul class="clearfix multiple-pic" id="' + resourceListId + '"><input id="pics" type="hidden" name="'+ inpName +'" value="'+ defaultValue +'" /></ul>');
    var basicHtml = '<li class = "pics-add" id="' + pickerId + '">';
    basicHtml += '<i class="sysfont">&#xe629;</i>';
    basicHtml += '<p class="pics-add-tips">最多只能选择' + maxLimitNum + '个资源</p>';
    basicHtml += '</li>';

    $list.prepend(basicHtml);
    selecterBox.html($list);

    // 初始化WebUploader
    var imgsUploader = WebUploader.create({
        // 选完文件后，是否自动上传。
        auto: true,
        // swf文件路径
        swf: 'http://filealiyun.geeker.com.cn/ued/webupload/js/Uploader.swf',
        // 文件接收服务端。
        server: 'http://file.geeker.com.cn/uploadOSS',
        //设置文件上传域的name
        fileVal: 'Filedata',
        //文件上传参数表
        formData: {
            path: 'scrs/img'
        },
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: {
            id: "#" + pickerId,
            multiple: true  //设置是否支持多文件上传
        },
        // 文件总数量
        fileNumLimit: maxLimitNum,
        fileSingleSizeLimit: size,
        // 只允许选择图片文件。
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/jpg,image/jpeg,image/bmp,image/png,image/gif'
        }
    });

    // 如果传入了默认值，则进行初始化显示
    if (defaultValue && defaultValue != "") {
        //TODO 初始化原有选择.
        var datas = defaultValue.split(",");
        var imgHtml = '';
        var fileName = '';
        // dftName = defaultName ? defaultName : '暂无';
        for (i = 0; i < datas.length; i++) {
            var img = datas[i];
            var dftName = datas[i];
            if (!img || img == "") {
                img = "../logo/no-photo.png";
            } else {
                dftName = dftName.substring(img.lastIndexOf('/') + 1, img.length);
                fileName = dftName;
                dftName = dftName.substring(0, dftName.lastIndexOf('.'));//去掉后缀
            }
            imgHtml = '<li class="resourceItem" title="' + dftName + '">' +
                '<img src = "' + img + '"  />' +
                '<div class="box-mask">' +
                '<p class="upload-operate"><i class="sysfont prev">&#xe61a;</i><i class="sysfont preview">&#xe626;</i><i class="sysfont delete">&#xe621;</i><i class="sysfont next">&#xe618;</i></p>' +
                '<p class="pics-item-name hidden">' + dftName + '</p>' +
                '</div>' +
                '</li>';
            $("#" + pickerId).before(imgHtml);
        }
    }
    // 超出限制张数隐藏上传框
    showOrHiddenPictureAdd(maxLimitNum, resourceListId, pickerId);

    imgsUploader.on('error', function (file) {
        if (file == 'Q_TYPE_DENIED') {
            $.daqMessage({
                icon: {
                    text: '&#xe622;'
                },
                text: '不支持改文件类型！',
                skin: 1,
                time: 2000
            });
        } else if (file == 'Q_EXCEED_NUM_LIMIT') {
            $.daqMessage({
                icon: {
                    text: '&#xe622;'
                },
                text: '超出文件上传的数量限制！',
                skin: 1,
                time: 2000
            });
        } else if (file == "F_DUPLICATE") {
            $.daqMessage({
                icon: {
                    text: '&#xe622;'
                },
                text: '该文件已上传！',
                skin: 1,
                time: 5000
            });
        } else if (file == 'Q_EXCEED_SIZE_LIMIT' || file == 'F_EXCEED_SIZE') {
            $.daqMessage({
                icon: {
                    text: '&#xe622;'
                },
                text: '文件大小超出限制！',
                skin: 1,
                time: 2000
            });
        }
    })
    //zf 增加文件上传前过滤逗号
    imgsUploader.on('uploadBeforeSend', function (file, data, header) {
        //这里可以通过data对象添加POST参数
        header['X_Requested_With'] = 'XMLHttpRequest';
        var name=file.file.name;//获取文件名称
        // var ext=file.file.ext;//获取后缀

        var spilt= name.split(",");//拆分特殊字符之逗号
        if (spilt.length>0){
            for(var i=0;i<spilt.length;i++){
                name=name.replace(",", "");//去掉逗号
            }
        }
        // name=name.substring(0,name.lastIndexOf('.'));//去掉后缀
        file.file.name=name;//最后赋值
        // console.log(file.file.name);
    })
    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    imgsUploader.on('uploadSuccess', function (file, response) {
        $.daqMessage({
            icon: {
                text: '&#xe61e;'
            },
            text: '上传成功！',
            skin: 1,
            time: 2000
        });
        var datas = response.datas;
        if (datas) {
            var len = $("#" + resourceListId).find(".resourceItem").length;
            if (len < limitNum) {
                var finalUrl = datas.url,
                    picInput = $('#' + resourceListId).find('#pics');
                // 判断当前是否有值
                var currentValue = picInput.val();
                if (currentValue == "") {
                    picInput.val(finalUrl);
                } else {
                    picInput.val(currentValue + "," + finalUrl);
                }
                var name = file.name.substring(0, file.name.lastIndexOf('.'));//去掉后缀
                var $item = $(
                    '<li class="resourceItem" title="' + name + '">' +
                    '<img src = "' + response.url + '"  />' +
                    '<div class="box-mask">' +
                    '<p class="upload-operate"><i class="sysfont prev">&#xe61a;</i><i class="sysfont preview">&#xe626;</i><i class="sysfont delete">&#xe621;</i><i class="sysfont next">&#xe618;</i></p>' +
                    '<p class="pics-item-name hidden">' + name + '</p>' +
                    '</div>' +
                    '</li>'
                );
                $("#" + pickerId).before($item);
            }
            // 超出限制张数隐藏上传框
            showOrHiddenPictureAdd(maxLimitNum, resourceListId, pickerId);
        }
    });

    // 文件上传失败，显示上传出错。
    imgsUploader.on('uploadError', function (file) {
        $.daqMessage({
            icon: {
                text: '&#xe622;'
            },
            text: '上传失败！',
            skin: 1,
            time: 2000
        });
    });

    /**
     * 前后切换
     * 避免与其他JS冲突，均以seleterBox前置.
     */
    {
        selecterBox.on('click', 'li i.next', function () {
            var $that = $(this).parents("li");
            var len = selecterBox.find("li:not('.pics-add')").length;
            var index = $that.index();
            if (len > 0 && index < (len - 1)) {
                $that.insertAfter(selecterBox.find("li").eq(index + 1));
            } else {
                return;
            }
        });
        selecterBox.on('click', 'li i.prev', function () {
            var $that = $(this).parents("li");
            var index = $that.index();
            if (index > 0) {
                index--;
                $that.insertBefore(selecterBox.find("li").eq(index));
            } else {
                return;
            }
        });
        selecterBox.on('click', 'li i.delete', function () {
            var $that = $(this).parents('li'),
                $ul = $that.parent('ul'),
                name = $that.find('.pics-item-name').text();
            $that.remove();
            showOrHiddenPictureAdd(limitNum, resourceListId, pickerId);

            var files = imgsUploader.getFiles();
            $(files).each(function(i, v) {
                var now = v.name;
                now = now.substring(0, now.lastIndexOf('.'));//去掉后缀
                if (now == name) {
                    imgsUploader.removeFile($(this));
                }
            })
            // imgsUploader.removeFile();
            //点击删除图片后，重新遍历box里的图片url进行组装 zf
            var src="";
            var len=$ul.find(".resourceItem").length;
            $ul.find(".resourceItem").each(function (i, v) {
                if (i == len-1){
                    src += $(this).find('img').attr("src");
                }else{
                    src += $(this).find('img').attr("src") + ",";
                }
            })

            $("#pics").val(src);


        });
        selecterBox.on('click', 'li i.preview', function () {
            var $parents = $(this).parents('li');
            el = $parents.find('img');
            $parents.daqPreviewImg({
                imgEl: el,
                title: '.pics-item-name'
            });
        });
        selecterBox.on('mouseenter mouseleave', '.resourceItem', function (event) {
            if (event.type == "mouseenter") {
                $(this).find('.box-mask').stop().animate({'height': '100%'}, 50);
                $(this).find('.upload-operate').stop().fadeIn();
                $(this).find('.pics-item-name').stop().hide();
            } else if (event.type == "mouseleave") {
                $(this).find('.box-mask').stop().animate({'height': '26px'}, 50);
                $(this).find('.upload-operate').stop().hide();
                $(this).find('.pics-item-name').stop().fadeIn();
            }
        });
    }

    /**
     * 根据限制数据控制显示、隐藏上传框
     * @param limitNum
     * @param pickerId
     */
    function showOrHiddenPictureAdd(limitNum, resourceListId, pickerId) {
        var len = $("#" + resourceListId).find(".resourceItem").length;
        if (len >= limitNum) {
            $("#" + pickerId).addClass("div-display-none");
        } else {
            $("#" + pickerId).removeClass("div-display-none");
        }
    }

}

/**
 * 初始化图像上传盒子
 * @param uploaderDivId 图像上传盒子最外层DIV的ID，里面的上传元素会动态初始化。
 * @param urlValueBoxId 图像上传时，用来接收图片地址的输入框ID，这个输入框只保存当前盒子上传的图像。
 * @param defaultValue 默认图片值，即初始化上传组件时，一开始就显示的图片，允许多个（用逗号隔开）
 * @param limitNum 上传限制数量，可以不传，默认值为1,暂时没有使用
 * @param crop 是否添加图片裁剪，如果为true则裁剪，false则不裁剪
 * @param size 图片上传大小限制，如果不传，默认限制为 4*1024*1024
 */
function initImageUploader(pickerObj, urlValueBoxId, defaultValue, limitNum, crop, size, widthId, heightId) {
    // 图片上传大小限制处理
    if(!size){
        size = 4*1024*1024;
    }else{
        size = size*1024*1024;
    }
    // 检查参数，声明上传盒子的容器
    var uploaderBox = $("#" + pickerObj),
        $li = uploaderBox.siblings('.upload-default-box');
    var urlValueBox = $("#" + urlValueBoxId);
    if (!urlValueBox) {
        alert("请指定图片上传后用于保存URL值的输入框");
        return;
    } else {
        urlValueBox.val("");
    }
    // 动态生成上传盒子的基本HTML结构
    var pickerId = uuid();
    // 基础参数设定
    // 如果传入了默认值，则进行初始化显示
    if (defaultValue && defaultValue != "") {
        putImageUrlIntoValueBox(defaultValue);
        var img = defaultValue;
        var realPic = img;
        if (img.indexOf("?") >= 0) {
            realPic = img.substring(0, img.indexOf("?"));
        }
        var dftName = realPic.substring(realPic.lastIndexOf('/') + 1, realPic.length);
        dftName=dftName.substring(0,dftName.lastIndexOf('.'));//去掉后缀
        var showDom = '<div class="media-pic-preview"><img src="' + img + '" />' +
            '<div class="box-mask media-show-pic">' +
            '<p class="media-operate"><i class="sysfont js-img-enlarge">&#xe63f;</i></p>' +
            '<p class="pics-item-name">' + dftName + '</p>' +
            '</div>' +
            '<label class="media-show-close"></label></div>';
        uploaderBox.siblings('.upload-default-box').html(showDom);
        uploaderBox.addClass('btn-green').html('<i class="sysfont mr-5">&#xe697;</i>重新上传');
        if (crop == true) {
            $(".media-operate").append('<i class="sysfont js-img-editor">&#xe668;</i>')
        }
    }

    // 初始化WebUploader
    var imgUploader = WebUploader.create({
        // 选完文件后，是否自动上传。
        auto: true,
        // swf文件路径
        swf: 'http://filealiyun.geeker.com.cn/ued/webupload/js/Uploader.swf',
        // 文件接收服务端。
        server: 'http://file.geeker.com.cn/uploadOSS',
        //设置文件上传域的name
        fileVal: 'Filedata',
        //文件上传参数表
        formData: {
            path: 'scrs/img'
        },
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: {
            id: uploaderBox,
            multiple: false  //设置是否支持多文件上传
        },
        fileSingleSizeLimit: size,
        // 只允许选择图片文件。
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/jpg,image/jpeg,image/bmp,image/png'
        }
    });
    imgUploader.on('fileQueued', function () {
        $.daqMessage({
            icon: {
                text: '&#xe633;'
            },
            text: '上传中！',
            skin: 1,
            time: ''
        });
    });
    imgUploader.on('error', function (file) {
        if (file == 'Q_TYPE_DENIED') {
            $.daqMessage({
                icon: {
                    text: '&#xe622;'
                },
                text: '不支持改文件类型！',
                skin: 1,
                time: 2000
            });
        } else if (file == 'Q_EXCEED_NUM_LIMIT') {
            $.daqMessage({
                icon: {
                    text: '&#xe622;'
                },
                text: '超出文件上传的数量限制！',
                skin: 1,
                time: 2000
            });
        } else if (file == "F_DUPLICATE") {
            $.daqMessage({
                icon: {
                    text: '&#xe622;'
                },
                text: '该文件已上传！',
                skin: 1,
                time: 5000
            });
        } else if (file == 'Q_EXCEED_SIZE_LIMIT' || file == 'F_EXCEED_SIZE') {
            $.daqMessage({
                icon: {
                    text: '&#xe622;'
                },
                text: '文件大小超出限制！',
                skin: 1,
                time: 2000
            });
        }
    });
    //zf 增加文件上传前过滤逗号
    imgUploader.on('uploadBeforeSend', function (file, data, header) {
        //这里可以通过data对象添加POST参数
        header['X_Requested_With'] = 'XMLHttpRequest';
        var name=file.file.name;//获取文件名称
        // var ext=file.file.ext;//获取后缀

        var spilt= name.split(",");//拆分特殊字符之逗号
        if (spilt.length>0){
            for(var i=0;i<spilt.length;i++){
                name=name.replace(",", "");//去掉逗号
            }
        }
        // name=name.substring(0,name.lastIndexOf('.'));//去掉后缀
        file.file.name=name;//最后赋值
        // console.log(file.file.name);
    })
    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    imgUploader.on('uploadSuccess', function (file, response) {
        $('.daq-feedback').remove();
        $.daqMessage({
            icon: {
                text: '&#xe61e;'
            },
            text: '上传成功！',
            skin: 1,
            time: 2000
        });
        // 上传成功，设置图片宽度，高度
        if(widthId){
            $("#"+widthId).val(file._info.width);
        }
        if(heightId){
            $("#"+heightId).val(file._info.height);
        }
        var datas = response.datas;
        if (datas) {
            var finalUrl = datas.url;
            putImageUrlIntoValueBox(finalUrl);
            var name=file.name.substring(0,file.name.lastIndexOf('.'));//去掉后缀
            var showDom = '<div class="media-pic-preview"><img src="' + response.url + '" />' +
                '<div class="box-mask media-show-pic">' +
                '<p class="media-operate"><i class="sysfont js-img-enlarge">&#xe63f;</i></p>' +
                '<p class="pics-item-name">' + name + '</p>' +
                '</div>' +
                '<label class="media-show-close"></label></div>';
            uploaderBox.siblings('.upload-default-box').addClass('upload-show-box').html(showDom);
            uploaderBox.addClass('btn-green').find('.webuploader-pick').html('<i class="sysfont mr-5">&#xe697;</i>重新上传');
            if (crop == true) {
                uploaderBox.siblings('.upload-default-box').find(".media-operate").append('<i class="sysfont js-img-editor">&#xe668;</i>')
            }

            // 新增手动去掉 验证样式，因为本来的样式，当图片上传成功后，其实有值了，但是验证的样式没有自动去掉
            $("#"+urlValueBoxId).parent(".form-table-edit").find("label[class=error]").removeClass("error");
        }
        imgUploader.reset();
    });

    // 文件上传失败，显示上传出错。
    imgUploader.on('uploadError', function (file) {
        $.daqMessage({
            icon: {
                text: '&#xe622;'
            },
            text: '上传失败！',
            skin: 1,
            time: 2000
        });
    });

    // 图片预览
    uploaderBox.siblings('.upload-default-box').on('click', '.js-img-enlarge', function () {
        var el = $(this).parents('.form-table-edit').find('img');
        urlValueBox.parents('.form-table-edit').daqPreviewImg({
            imgEl: el,
            title: '.pics-item-name'
        });
    })
    // 图片hover事件
    uploaderBox.siblings('.upload-default-box').on("mouseenter mouseleave", function (event) {
        if (event.type == "mouseenter") {
            $(this).find('.box-mask').stop().animate({"height": "100%"}, 50
            ).find('.media-operate').stop().show();
            $(this).find('.pics-item-name').stop().hide();
        } else if (event.type == "mouseleave") {
            $(this).find('.box-mask').stop().animate({"height": "26px"}, 50).find('.media-operate').stop().hide();
            $(this).find('.pics-item-name').stop().show();
        }
    })
    // 图片删除
    uploaderBox.siblings('.upload-default-box').on('click', '.media-show-close', function () {
        urlValueBox.val('');
        showDom = '<i class="sysfont upload-dft-icon">&#xe69c;</i><p class="upload-tips">支持JPG和PNG格式图片不超过2M</p>';
        uploaderBox.siblings('.upload-default-box').removeClass('upload-show-box').html(showDom);
        uploaderBox.removeClass('btn-green').find('.webuploader-pick').html('<i class="sysfont mr-5">&#xe697;</i>点击上传');
    })
    // 图片裁剪
    if (crop == true) {
        uploaderBox.siblings('.upload-default-box').on('click', '.js-img-editor', function () {
            var el = $(this).parents('.form-table-edit').find('img'),
                sourceImg = el.attr('src'),
                indexImg = sourceImg.indexOf('?'),
                defaultImg = sourceImg,
                datas;
            if (indexImg > 0) {
                defaultImg = sourceImg.substring(0, indexImg);
            }
            $.daqDialog({
                title: '图片裁剪',
                iconfont: '&#xe69c;',
                content: $("#cropperTmpl").html(),
                mask: true,
                width: 720,
                height: 540,
                model:'fadeInDown',
                buttons: [
                    {
                        text :'确定',
                        callback: function(){
                            var oldImg = $('#cropNews').find('.media-pic-preview > img'),
                                oldImgSource = $('#cropNews').find('.media-pic-preview > img').attr('src');
                            // 获取被裁剪过的图片地址进行截取获取上一次被裁剪的x,y坐标值
                            var oldX = oldImgSource.indexOf(',', oldImgSource.indexOf('x_')),
                                oldY = oldImgSource.indexOf(',', oldImgSource.indexOf('y_'));
                            if (oldX > 0 && oldY > 0) {
                                var newX = oldImgSource.substring(oldImgSource.indexOf('x_') + 2, oldX),
                                    newY = oldImgSource.substring(oldImgSource.indexOf('y_') + 2, oldY);
                                datas.x = parseInt(newX) + parseInt(datas.x);
                                datas.y = parseInt(newY) + parseInt(datas.y);
                            }
                            var newImgSource = defaultImg + '?x-oss-process=image/crop,x_'+ datas.x +',y_'+ datas.y +',w_'+ datas.width +',h_'+ datas.height;
                            oldImg.attr('src', newImgSource);
                            $('#cropNews #cover').val(newImgSource);
                        }
                    },
                    {
                        text :'取消',
                        callback: function(){
                        }
                    }
                ],
                callback: function () {
                    var $image = $('.cropper-img > img'),
                        options = {
                            aspectRatio: 16 / 9,
                            preview: '.img-preview',
                            responsive: false,
                            crop: function (data) {
                                datas = {
                                    x: Math.round(data.x),
                                    y: Math.round(data.y),
                                    height: Math.round(data.height),
                                    width: Math.round(data.width),
                                    rotate: Math.round(data.rotate)
                                }
                            }
                        };

                    $('#js_jcrop').attr('src', sourceImg);
                    $image.on({
                        'build.cropper': function (e) {
                            // console.log(e.type);
                        },
                        'built.cropper': function (e) {
                            // console.log(e.type);
                        },
                        'dragstart.cropper': function (e) {
                            // console.log(e.type, e.dragType);
                        },
                        'dragmove.cropper': function (e) {
                            // console.log(e.type, e.dragType);
                        },
                        'dragend.cropper': function (e) {
                            // console.log(e.type, e.dragType);
                        },
                        'zoomin.cropper': function (e) {
                            // console.log(e.type);
                        },
                        'zoomout.cropper': function (e) {
                            // console.log(e.type);
                        }
                    }).cropper(options);


                    // Methods
                    $(document.body).on('click', '[data-method]', function () {
                        var data = $(this).data(),
                            $target,
                            result;
                        $(this).addClass('btn-curr').siblings().removeClass('btn-curr');
                        if (data.method) {

                            data = $.extend({}, data); // Clone a new one

                            if (typeof data.target !== 'undefined') {
                                $target = $(data.target);

                                if (typeof data.option === 'undefined') {
                                    try {
                                        data.option = JSON.parse($target.val());
                                    } catch (e) {
                                        // console.log(e.message);
                                    }
                                }
                            }
                            result = $image.cropper(data.method, data.option);

                        }
                    }).on('keydown', function (e) {

                        switch (e.which) {
                            case 37:
                                e.preventDefault();
                                $image.cropper('move', -1, 0);
                                break;

                            case 38:
                                e.preventDefault();
                                $image.cropper('move', 0, -1);
                                break;

                            case 39:
                                e.preventDefault();
                                $image.cropper('move', 1, 0);
                                break;

                            case 40:
                                e.preventDefault();
                                $image.cropper('move', 0, 1);
                                break;
                        }
                    });

                }
            })
        })
    }
    /**
     * 将图片URL放到图片地址输入框
     * @param urls 图片地址，可以有多个
     */
    function putImageUrlIntoValueBox(urls) {
        if (urlValueBox) {
            var currentValue = urlValueBox.val();
            urlValueBox.val(urls);
            /*if (currentValue == "") {
             urlValueBox.val(urls);
             } else {
             urlValueBox.val(currentValue + "," + urls);
             }*/
        }
    }
}

/**
 * 初始化文件上传盒子（本地上传）
 * @param pickerObj 文件上传盒子最外层DIV的ID，里面的上传元素会动态初始化。
 * @param urlValueBoxId 文件上传时，用来接收音频地址的输入框ID，这个输入框只保存当前盒子上传的文件。
 * @param sizeBox 上传文件时，用来接收文件大小的输入框ID，这个输入框只保存当前盒子上传的文件。
 * @param suffix 允许的文件后缀，允许多个,为''则表示允许所有
 * @param mimeTypes 类型  音频：audio/*   视频 video/*
 * @param defaultValue 默认文件值，即初始化上传组件时，一开始就显示的文件，允许多个（用逗号隔开）
 * @param title 文件名称
 * @param type 上传文件类型(音频、视频不传，通过mimeTypes判断是音频还是视频，如果是文件，则type需要传 file)
 * @param pathNameBox 文件上传时，用来接收音频名称的输入框ID，这个输入框只保存当前盒子上传的文件。
 * @param defalutSize 文件上传时，限制文件大小，如果不传，默认限制为 100M, 注意单位为 M
 */
function initFileUploader(pickerObj, urlValueBoxId, sizeBox, suffix, mimeTypes, defaultValue, title, type, pathNameBox, defalutSize) {
    // 文件上传大小限制处理
    if(!defalutSize){
        defalutSize = 100*1024*1024;
    }else{
        defalutSize = defalutSize*1024*1024;
    }
    // 检查参数，声明上传盒子的容器
    var uploaderBox = $("#" + pickerObj);
    //url显示地址
    var urlBox = $("#" + urlValueBoxId);
    if (!defaultValue) {
        defaultValue = "";
    }
    if (!urlBox) {
        alert("请指定文件上传后用于保存URL值的输入框");
        return;
    } else {
        urlBox.val(defaultValue);
    }

    // 动态生成上传盒子的基本HTML结构
    var pickerId = uuid();
    //初始化文件列表
    if (defaultValue && defaultValue != "") {
        var files = defaultValue.split(","),
            showDom;
        var dftName = files[0];
        dftName = dftName.substring(dftName.lastIndexOf('/')+1,dftName.length);
        dftName=dftName.substring(0,dftName.lastIndexOf('.'));//去掉后缀
        if (mimeTypes == 'audio/*') {
            showDom = '<label class="media-show-close"></label><p class="audio-title">' + dftName + '</p>' +
                '<div class="audio-box">' +
                '<span class="sysfont audio-play play fl">&#xe6a1;</span>' +
                '<div id="audio_jplayer" class="jp-jplayer fl"></div>' +
                '<div id="jp_container_1" class="jp-audio" role="application" aria-label="media player">' +
                '<div class="jp-type-single">' +
                '<div class="jp-gui jp-interface">' +
                '<div class="jp-progress">' +
                '<div class="jp-seek-bar">' +
                '<div class="jp-play-bar"></div>' +
                '<span class="jp_mk"></span>' +
                '</div>' +
                '</div>' +
                '<div class="jp-time-holder">' +
                '<div class="jp-duration" role="timer" aria-label="duration">&nbsp;</div>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>';
            uploaderBox.siblings('.upload-default-box').html(showDom);
            uploaderBox.addClass('btn-green').html('<i class="sysfont mr-5">&#xe698;</i>重新上传');
            // 音频播放
            audioPlay(title, defaultValue, uploaderBox, urlBox);
        } else if (mimeTypes == 'video/*') {  // 视频
            showDom = '<div class="box-mask default-video-show">' +
                '<p class="media-operate"><i class="sysfont" data-url="'+defaultValue+'">&#xe6cd;</i></p>' +
                '<p class="pics-item-name">'+ dftName +'</p>' +
                '</div>' +
                '<label class="media-show-close"></label>';
            uploaderBox.siblings('.upload-default-box').html(showDom);
            uploaderBox.addClass('btn-green').html('<i class="sysfont mr-5">&#xe697;</i>重新上传');
            // 点击播放
            uploaderBox.siblings('.upload-default-box').find( '.media-operate .sysfont').off('click');
            uploaderBox.siblings('.upload-default-box').on('click', '.media-operate .sysfont', function () {
                $.daqVideo({
                    mediaTitle: dftName, // 媒体名称
                    mediaUrl: files[0],   // 媒体地址
                    mediaImg: '',   // 媒体背景图片
                    mediaHeight: 500, // 视频高度
                    mediaWidth: 750, // 视频宽度
                    swfUrl: "http://filealiyun.geeker.com.cn/ued/geekUI/media/flvplayer.swf"
                });
            })
        } else if( type == 'file') {
            showDom = '<div class="box-mask default-video-show">' +
                '<p class="media-operate"><i class="sysfont">&#xe6ec;</i></p>' +
                '<p class="pics-item-name">' + dftName + '</p>' +
                '</div>' +
                '<label class="media-show-close"></label>';
            uploaderBox.siblings('.upload-default-box').html(showDom);
            uploaderBox.addClass('btn-green').html('<i class="sysfont mr-5">&#xe63b;</i>重新上传');
        } else {
            showDom = '<img src="' + files[0] + '" />' +
                '<p class="box-mask tx-c up-box-icon">' +
                '<i class="sysfont js-img-enlarge">&#xe63f;</i>' +
                '<i class="sysfont js-img-delete">&#xe631;</i>' +
                '</p>';
            uploaderBox.siblings('.upload-default-box').html(showDom);
            uploaderBox.addClass('btn-green').html('<i class="sysfont mr-5">&#xe697;</i>重新上传');
        }
    }
    // 初始化Web Uploader
    var fileUploader = WebUploader.create({
        // 选完文件后，是否自动上传。
        auto: true,
        // swf文件路径
        swf: 'http://filealiyun.geeker.com.cn/ued/webupload/js/Uploader.swf',
        // 文件接收服务端。
        server: 'http://file.geeker.com.cn/upload',
        // 设置文件上传域的name
        fileVal: 'Filedata',
        // 文件上传请求的参数表，每次发送都会发送此对象中的参数。
        formData: {
            path: 'scrs' // 表示文件上传到服务器上的文件夹名
        },
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: {
            id: uploaderBox
        },
        fileSingleSizeLimit: defalutSize,
        // 只允许选择文件类型。
        accept: {
            title: 'files',
            extensions: suffix
            // mimeTypes: mimeTypes
        }
    });
    fileUploader.on('beforeFileQueued', function (file) {  // 文件加入队列之前
        if (file.size <= 0) {
            $.daqMessage({
                icon: {
                    text: '&#xe622;'
                },
                text: '上传文件大小需大于0KB！',
                skin: 1,
                time: 2000
            });
            return false;
        }
    });
    fileUploader.on('fileQueued', function () {
        $.daqMessage({
            icon: {
                text: '&#xe633;'
            },
            text: '上传中！',
            skin: 1,
            time: ''
        });
    });
    // 当validate不通过时，会以派送错误事件的形式通知调用者
    fileUploader.on('error', function (file) {
        if (file == 'Q_TYPE_DENIED') {
            $.daqMessage({
                icon: {
                    text: '&#xe622;'
                },
                text: '不支持该文件类型！',
                skin: 1,
                time: 2000
            });
            return false;
        } else if (file == "F_DUPLICATE") {
            $.daqMessage({
                icon: {
                    text: '&#xe622;'
                },
                text: '该文件已上传！',
                skin: 1,
                time: 5000
            });
            return false;
        } else if (file == 'Q_EXCEED_NUM_LIMIT') {
            $.daqMessage({
                icon: {
                    text: '&#xe622;'
                },
                text: '超出文件上传的数量限制！',
                skin: 1,
                time: 2000
            });
            return false;
        } else if (file == 'Q_EXCEED_SIZE_LIMIT' || file == 'F_EXCEED_SIZE') {
            $.daqMessage({
                icon: {
                    text: '&#xe622;'
                },
                text: '文件大小超出限制！',
                skin: 1,
                time: 2000
            });
            return false;
        }
    })
    //zf 增加文件上传前过滤逗号
    fileUploader.on('uploadBeforeSend', function (file, data, header) {
        //这里可以通过data对象添加POST参数
        header['X_Requested_With'] = 'XMLHttpRequest';
        var name=file.file.name;//获取文件名称
        // var ext=file.file.ext;//获取后缀

        var spilt= name.split(",");//拆分特殊字符之逗号
        if (spilt.length>0){
            for(var i=0;i<spilt.length;i++){
                name=name.replace(",", "");//去掉逗号
            }
        }
        // name=name.substring(0,name.lastIndexOf('.'));//去掉后缀
        file.file.name=name;//最后赋值
        // console.log(file.file.name);
    })
    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    fileUploader.on('uploadSuccess', function (file, response) {
        // 上传成功
        if (response && response.url) {
            // 先移除正在上传中的提示框
            $('.daq-feedback').remove();
            $.daqMessage({
                icon: {
                    text: '&#xe633;'
                },
                text: '上传成功！',
                skin: 1,
                time: 2000
            });
            urlBox.val(response.url);
            //多媒体暂时只有单个上传，先直接赋值
            $("#" + sizeBox).val((file.size / 1024).toFixed(2));
            var dftName=file.name.substring(0,file.name.lastIndexOf('.'));//去掉后缀
            uploaderBox.siblings('.upload-default-box').addClass('upload-show-box').html(showDom);
            $("#" + pathNameBox).val(dftName);
            // 如果是新增页面，第一次进来是没有 文件名 文件路径这些值的， 这个 title defaultValue 是传进来的值，这里上传成功后，需要对这些值进行赋新值，不然没有值
            // 文件名
            title = dftName;
            // 文件路径
            defaultValue = response.url;

            if (mimeTypes == 'audio/*') {
                showDom = '<label class="media-show-close"></label><p class="audio-title">' + dftName + '</p>' +
                    '<div class="audio-box">' +
                    '<span class="sysfont audio-play play fl">&#xe6a1;</span>' +
                    '<div id="audio_jplayer" class="jp-jplayer fl"></div>' +
                    '<div id="jp_container_1" class="jp-audio" role="application" aria-label="media player">' +
                    '<div class="jp-type-single">' +
                    '<div class="jp-gui jp-interface">' +
                    '<div class="jp-progress">' +
                    '<div class="jp-seek-bar">' +
                    '<div class="jp-play-bar"></div>' +
                    '<span class="jp_mk"></span>' +
                    '</div>' +
                    '</div>' +
                    '<div class="jp-time-holder">' +
                    '<div class="jp-duration" role="timer" aria-label="duration">&nbsp;</div>' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</div>';
                uploaderBox.siblings('.upload-default-box').html(showDom);
                uploaderBox.addClass('btn-green').find('.webuploader-pick').html('<i class="sysfont mr-5">&#xe698;</i>重新上传');
                audioPlay(title, defaultValue, uploaderBox, urlBox);
            } else if (mimeTypes == 'video/*') {  // 视频
                showDom = '<div class="box-mask default-video-show">' +
                    '<p class="media-operate"><i class="sysfont" data-url="'+defaultValue+'">&#xe6cd;</i></p>' +
                    '<p class="pics-item-name">'+ dftName +'</p>' +
                    '</div>' +
                    '<label class="media-show-close"></label>';
                uploaderBox.siblings('.upload-default-box').html(showDom);
                uploaderBox.addClass('btn-green').find('.webuploader-pick').html('<i class="sysfont mr-5">&#xe697;</i>重新上传');
                // 点击播放
                uploaderBox.siblings('.upload-default-box').off('click');
                uploaderBox.siblings('.upload-default-box').on('click', '.media-operate .sysfont', function () {
                    $.daqVideo({
                        mediaTitle: file.name, // 媒体名稱
                        mediaUrl: response.url,   // 媒体地址
                        mediaImg: '',   // 媒体背景图片
                        mediaHeight: 500, // 视频高度
                        mediaWidth: 750, // 视频宽度
                        swfUrl: "http://filealiyun.geeker.com.cn/ued/geekUI/media/flvplayer.swf"
                    });
                })
            } else if( type == 'file') {
                showDom = '<div class="box-mask default-video-show">' +
                    '<p class="media-operate"><i class="sysfont">&#xe6ec;</i></p>' +
                    '<p class="pics-item-name">' + dftName + '</p>' +
                    '</div>' +
                    '<label class="media-show-close"></label>';
                uploaderBox.siblings('.upload-default-box').html(showDom);
                uploaderBox.addClass('btn-green').find('.webuploader-pick').html('<i class="sysfont mr-5">&#xe63b;</i>重新上传');
            } else {
                showDom = '<img src="' + response.url + '" />' +
                    '<p class="box-mask tx-c up-box-icon">' +
                    '<i class="sysfont js-img-enlarge">&#xe63f;</i>' +
                    '<i class="sysfont js-img-delete">&#xe631;</i>' +
                    '</p>';
                uploaderBox.siblings('.upload-default-box').html(showDom);
                uploaderBox.siblings('.daq-btn').addClass('btn-green').find('.webuploader-pick').html('<i class="sysfont mr-5">&#xe697;</i>重新上传');
            }

            // 新增手动去掉 验证样式，因为本来的样式，当图片上传成功后，其实有值了，但是验证的样式没有自动去掉
            $("#"+urlValueBoxId).parent(".form-table-edit").find("label[class=error]").removeClass("error");
        }
    });
    // 文件上传出错时触发
    fileUploader.on('uploadError', function (file) {
        $.daqMessage({
            icon: {
                text: '&#xe622;'
            },
            text: '上传失败！',
            skin: 1,
            time: 2000
        });
    });

    // 删除选中
    uploaderBox.siblings('.upload-default-box').on('click', '.media-show-close', function (e) {
        e.stopPropagation();
        urlBox.val(''); //清空input框的值
        $("#" + sizeBox).val("");
        $("#" + pathNameBox).val("");
        var defaultDom = '';
        if (type == 'file') {
            defaultDom = '<i class="sysfont upload-dft-icon">&#xe6ec;</i><p class="upload-tips">支持TXT,ZIP,DOC,PDF等格式文件</p>';
            uploaderBox.removeClass('btn-green').find('.webuploader-pick').html('<i class="sysfont mr-5">&#xe63b;</i>点击上传');
        } else if(mimeTypes == 'video/*'){
            defaultDom = '<i class="sysfont upload-dft-icon">&#xe69b;</i><p class="upload-tips">支持FLV和MP4格式视频不超过20M</p>';
            uploaderBox.removeClass('btn-green').find('.webuploader-pick').html('<i class="sysfont mr-5">&#xe699;</i>点击上传');
        } else if(mimeTypes == 'audio/*'){
            defaultDom = '<i class="sysfont upload-dft-icon">&#xe69a;</i><p class="upload-tips">支持MP3和WAM格式音频不超过5M</p>';
            uploaderBox.removeClass('btn-green').find('.webuploader-pick').html('<i class="sysfont mr-5">&#xe698;</i>点击上传');
        }
        $(this).parent('.upload-default-box').removeClass('upload-show-box').html(defaultDom);
        fileUploader.reset();
    });
}
// 音频播放
function audioPlay(title, url, uploaderBox, urlBox) {
    $("#audio_jplayer").jPlayer({
        ready: function (event) {
            $(this).jPlayer("setMedia", {
                title: title,
                mp3: url
            });
        },
        cssSelectorAncestor: "#jp_container_1",
        play: function () {
            $(this).jPlayer("pauseOthers");
        },
        pause: function () {

        },
        swfPath: "http://filealiyun.geeker.com.cn/ued/geekUI/js/audio/jquery.jplayer.swf",
        supplied: "m4a, oga, mp3",
        wmode: "window",
        remainingDuration: true,
        globalVolume: true,
        useStateClassSkin: true,
        autoBlur: false,
        smoothPlayBar: true,
        keyEnabled: true
    });
    $('.upload-default-box').on('click', '.audio-play', function (e) {
        e.stopPropagation();
        if ($(this).hasClass("play")) {
            $(this).siblings(".jp-jplayer").jPlayer("play");
            $(this).html('&#xe6a0;').removeClass("play").addClass("pause");
        } else {
            $(this).siblings(".jp-jplayer").jPlayer("pause");
            $(this).html('&#xe6a1;').removeClass("pause").addClass("play");
        }
    });
    // 删除
    $('.upload-default-box').on('click', '.media-show-close', function () {
        urlBox.val('');
        var defaultDom = '<i class="sysfont upload-dft-icon">&#xe69a;</i><p class="upload-tips">支持MP3和WAM格式音频不超过5M</p>';
        $(this).parents('.upload-default-box').removeClass('upload-show-box').html(defaultDom);
        uploaderBox.siblings('.daq-btn').removeClass('btn-green').find('.webuploader-pick').html('<i class="sysfont mr-5">&#xe698;</i>点击上传');
    })
}

/**
 * 根据限制数据控制显示、隐藏上传框
 * @param limitNum
 * @param pickerId
 */
function showOrHiddenUpload(limitNum, pickerId) {
    if (limitNum < 1) {
        $("#" + pickerId).addClass("div-display-none");
    }
    if (limitNum > 0) {
        $("#" + pickerId).removeClass("div-display-none");
    }
}

/**
 * 媒体资源表格中的本地上传(多文件批量)
 * @param uploaderDivId 文件上传盒子最外层DIV的ID，里面的上传元素会动态初始化。
 * @param urlValueBoxId 文件上传时，用来接收文件地址的输入框ID，这个输入框只保存当前盒子上传的文件。
 * @param type 类型 2 视频 3 音频
 */
function initMediaFileUploader(uploaderDivId, urlValueBoxId, type) {
    var suffix, mimeTypes;
    if (!type) {
        alert("请指定文件上传类型");
        return;
    }
    if (type == '2') {//视频
        suffix = 'mp4,flv,wmv', mimeTypes = 'video/*';
    } else {//音频
        suffix = 'mp3,wam', mimeTypes = 'audio/*';
    }
    // 检查参数，声明上传盒子的容器
    var uploaderBox = $("#" + uploaderDivId);
    //url显示地址
    var urlBox = $("#" + urlValueBoxId);
    if (!uploaderBox) {
        alert("请指定文件上传的DIV");
        return;
    }
    if (!uploaderBox.hasClass("daq-upload")) {
        //  alert("要求文件上传的DIV必须有 daq-upload 样式");
        return;
    }
    if (!urlBox) {
        alert("请指定文件上传后用于保存URL值的输入框");
        return;
    } else {
        urlBox.val();
    }

    // 动态生成上传盒子的基本HTML结构
    var pickerId = uuid();
    var fileList = uuid();
    var html = '';
    html += '<div id="' + pickerId + '">本地上传</div>';
    html += '<p id="info" class="upload-text"></p>';
    html += '<div id="' + fileList + '" class="uploader-list"></div>';
    uploaderBox.html(html);
    //存放文件列表
    var $list = $("#" + fileList);

    // 初始化Web Uploader
    var uploader = WebUploader.create({
        // 选完文件后，是否自动上传。
        auto: true,
        // swf文件路径
        swf: 'http://filealiyun.geeker.com.cn/ued/webupload/js/Uploader.swf',
        // 文件接收服务端。
        server: 'http://file.geeker.com.cn/upload',
        // 设置文件上传域的name
        fileVal: 'Filedata',
        // 文件上传请求的参数表，每次发送都会发送此对象中的参数。
        formData: {
            path: 'scrs' // 表示文件上传到服务器上的文件夹名
        },
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: {
            id: '#' + pickerId
        },
        fileSingleSizeLimit: 10 * 1024 * 1024, //最大10M
        // 只允许选择图片文件。
        accept: {
            title: 'files',
            extensions: suffix,
            mimeTypes: mimeTypes
        }
    });

    // 文件被加入队列时触发
    uploader.on('fileQueued', function (file) {
        // webuploader事件.当选择文件后，文件被加载到文件队列中，触发该事件。等效于 uploader.onFileueued = function(file){...} ，类似js的事件定义。
        var $li = $(
            '<div id="' + file.id + '" class="file-item"><i class="sysfont">&#xe680;</i>' +
            '<span>' + file.name + '</span>' +
            '<i class="sysfont upload-success"></i>' + //完成后的图标
            '</div>'
        );
        // $list为容器jQuery实例
        $list.append($li);
        // 判断文件类型显示不同的图标
        if (file.type.indexOf("image") < 0) {
            $('#' + file.id).find(".upload-folder").html("");
        } else {
            $('#' + file.id).find(".upload-folder").html("");
        }
    });
    // 文件上传过程中创建进度条实时显示。
    uploader.on('uploadProgress', function (file, percentage) {
        var $li = $('#' + file.id),
            $percent = $list.find('.progress span');
        // 避免重复创建
        if (!$percent.length) {
            $percent = $('<div class="progress"><p class="progress-p">' +
                '<span></span>' + //表示进度
                '</p><label>' + Math.ceil(percentage * 100) + '%</label></div>')
                .appendTo($li)
                .find('span');
        }
        //显示当前进度
        $percent.css('width', percentage * 100 + '%');
        //进度条模块总宽度
        var progressWid = $li.find(".progress").outerWidth(),
            //显示当前进度百分比数值的宽度
            labelWid = $li.find(".progress label").outerWidth();
        //进度条的宽度
        $li.find(".progress-p").width(progressWid - labelWid);
    });
    //zf 增加文件上传前过滤逗号
    uploader.on('uploadBeforeSend', function (file, data, header) {
        //这里可以通过data对象添加POST参数
        header['X_Requested_With'] = 'XMLHttpRequest';
        var name=file.file.name;//获取文件名称
        // var ext=file.file.ext;//获取后缀

        var spilt= name.split(",");//拆分特殊字符之逗号
        if (spilt.length>0){
            for(var i=0;i<spilt.length;i++){
                name=name.replace(",", "");//去掉逗号
            }
        }
        // name=name.substring(0,name.lastIndexOf('.'));//去掉后缀
        file.file.name=name;//最后赋值
        // console.log(file.file.name);
    })

    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on('uploadSuccess', function (file, response) {
        var $li = $('#' + file.id);
        $li.addClass('upload-state-done');
        $li.find(".upload-success").addClass("success");
        //移除进度百分比数值,并重新计算滚动条宽度
        $li.find(".progress label").remove();
        $li.find(".progress-p").css("width", "100%");
        if (response && response.url) {
            //公用属性
            var lang = $("#langBox").val(); //语言
            var title = file.name, path = response.url;
            var url = "", dataList = [];
            var resourceId = $("#resourceId").val();
            var resourceType = $("#resourceType").val();
            if (type == '3') { //音频
                url = "/siteAudio/batchAdd?";
                var data = {
                    title: title,
                    path: path,
                    sortno: 1,
                    status: 1,
                    lang: lang,
                    sourceType: resourceType,
                    sourceId: resourceId,
                    size: (file.size / 1024).toFixed(2)
                };
                dataList.push(data);
            }
            //保存资源到媒体库
            $.ajax({
                url: url,
                type: "post",
                data: {"audioJson": JSON.stringify(dataList)},
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        alert("保存成功");
                        refreshMediaTable(resourceType, resourceId, type);
                    }
                }
            });
        }
    });
    // 文件上传失败，显示上传出错。
    uploader.on('uploadError', function (file) {
        var $li = $('#' + file.id),
            $error = $li.find('div.error');
        // 避免重复创建
        if (!$error.length) {
            $error = $('<div class="error"></div>').appendTo($li);
        }
        $error.text('上传失败');
    });

    // 完成上传完了，成功或者失败，先删除进度条。
    // uploader.on('uploadComplete', function (file) {
    //     var $li = $('#' + file.id);
    //     $('#' + file.id).find('.progress').remove();
    //     //点击删除上传的文件
    //     $li.on("click", ".upload-success", function () {
    //         $(this).parents(".file-item").remove();
    //         uploader.removeFile(file, true);
    //     });
    // });
    //给上传文件列表添加hover事件
    // $(".uploader-list").on('mouseover', '.file-item', function () {
    //     $(this).find(".upload-success").addClass("delete");
    //     $(this).find(".upload-success").html("");
    // });
    //
    // $(".uploader-list").on('mouseout', '.file-item', function () {
    //     $(this).find(".upload-success").removeClass("delete");
    //     $(this).find(".upload-success").html("");
    // });

}

/**
 * 初始化多媒体资源
 * @param uploaderDivId  封装有默认值的div框
 * @param urlValueBoxId 封装文件地址的input框
 * @param defaultFile 默认值（根据默认值显示）
 * @param initUploaderBox 没有默认值的上传文件框
 */
function initMediaFile(initDivId, urlValueBoxId, defaultFile) {
    // 检查参数，声明上传盒子的容器
    var initDivBox = $("#" + initDivId);
    var urlValueBox = $("#" + urlValueBoxId);

    /**
     *创建上传文件框
     */
    function createUploadDiv() {
        var initUploadId = uuid();
        var uploadHtml = '<div class="daq-inp-rule wid-358" id="' + initUploadId + '"></div>';
        initDivBox.append(uploadHtml);
        $('#' + initUploadId).on('click', function () {
            getAudioMsg(initDivId, urlValueBoxId, '3');
        });
    }

    // 动态生成上传盒子的基本HTML结构
    var fileList = uuid();
    var html = '';
    html += '<div id="' + fileList + '" class="uploader-list"></div>';
    initDivBox.html(html);
    //存放文件列表
    var $list = $("#" + fileList);

    if (defaultFile) { //有默认值时隐藏上传文件框
        if (defaultFile.id) {
            urlValueBox.val(defaultFile.id);
        } else {
            urlValueBox.val(defaultFile);
        }
    } else {
        createUploadDiv();
    }

    //初始化文件列表
    if (defaultFile && defaultFile != "") {
        var fileHtml = '';
        if (defaultFile.id) { //文件框选择
            fileHtml += '<div class="file-item upload-state-done"><i class="sysfont">&#xe680;</i>';
            fileHtml += '<input type="hidden" value="' + defaultFile.id + '"/>';
            fileHtml += '<span>' + defaultFile.name + '</span><i class="sysfont upload-success success"></i></div>';
            $list.append(fileHtml);
        } else {
            //获取媒体信息
            $.ajax({
                url: "/siteAudio/findById?id=" + defaultFile,
                type: "get",
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code == 0 && result.data) {
                        fileHtml += '<div class="file-item upload-state-done"><i class="sysfont">&#xe680;</i>';
                        fileHtml += '<input type="hidden" value="' + result.data.id + '"/>';
                        fileHtml += '<span>' + result.data.title + '</span><i class="sysfont upload-success success"></i></div>';
                        $list.append(fileHtml);
                    } else {
                        createUploadDiv();
                    }
                }
            });
        }
    }

    /**
     * 默认路径的删除
     */
    $('.uploader-list .upload-success').on('click', function () {
        urlValueBox.val("");
        $list.remove();
        createUploadDiv();
    });

    //给上传文件列表添加hover事件
    $(".uploader-list").on('mouseover', '.file-item', function () {
        $(this).find(".upload-success").addClass("delete");
        $(this).find(".upload-success").html("");
    });
    $(".uploader-list").on('mouseout', '.file-item', function () {
        $(this).find(".upload-success").removeClass("delete");
        $(this).find(".upload-success").html("");
    });
}

