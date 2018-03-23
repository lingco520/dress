/**
 * 初始化富文本编辑器
 * @param textAreaId 容器
 * @param maxLength  最大输入值
 */
function initKindEditor(textAreaId, maxLength) {
    //编辑器
    KindEditor.ready(function (K) {
        window.editor = K.create("#" + textAreaId, {
            height: "300px",
            width: "100%",
            items: [
                'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
                'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
                'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
                'flash', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
                'anchor', 'link', 'unlink', '|', 'about'
            ],
            uploadJson: '/permitted/common/ossUploadKindFile',
            allowImageUpload: true,
            allowImageRemote: false,
            filePostName: 'Filedata',
            afterCreate: function () {
                this.loadPlugin('autoheight');
                this.sync();
            },
            afterBlur: function () {
                this.sync();
            },
            afterChange: function () {
                $('.word_count1').html(this.count());
                $('.word_count2').html(this.count('text'));
                var limitNum = 100;  //设定限制字数,默认500
                if (maxLength && maxLength != '') {
                    limitNum = maxLength;
                }
                if (this.count('text') > limitNum) {
                    //超过字数限制自动截取
                    var strValue = this.text();
                    strValue = strValue.substring(0, limitNum);
                    this.text(strValue);
                }
            },
            extraFileUploadParams : {
                // 权限，需要加这个，404
                _csrf: daqsoft.getCookie("XSRF-TOKEN")
            }
        });
    });
}