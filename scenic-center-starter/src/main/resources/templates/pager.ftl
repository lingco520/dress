<!DOCTYPE html>
<html>
<head>
    <script>
        (function ($) {
            $(function () {
                $('#pagination_bar').daqPages({
                    type: 2,
                    total:  ${pager.total!},
                    pages: ${pager.pageCount!},
                    pageSize: ${pager.pageSize!},
                    limits: 2,
                    currPage: ${pager.currPage!},
                    prev: '上一页',
                    next: '下一页',
                    pageText: true,
                    pageSlect: true,
                    pageInp: true,
                    callback: function (data) {
                        $("#pageNo").val(data.currPage);
                        $("#pageSize").val(data.pageSize);
                        $('.system-btn > button').click();
                    }
                });
            })
        })(jQuery);
    </script>
</head>
<body>
<input type="hidden" name="pageSize" id="pageSize" value="${pager.pageSize!}"/>
<input type="hidden" name="pageNo" id="pageNo" value="${pager.pageNo!}"/>
<input type="hidden" name="orderBy" id="orderBy" value="${orderBy!}"/>
<input type="hidden" name="orderType" id="order" value="${orderType!}"/>
<div id="pagination_bar" style="padding-top:10px;"></div>
</body>
</html>