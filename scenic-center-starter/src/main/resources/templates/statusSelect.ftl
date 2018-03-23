<div id="status" class="daq-select wid-90 fl">
    <select name="status">
        <option value="">全部</option>
        <option value="1" >启用</option>
        <option value="0">禁用</option>
    </select>
</div>
<script>
    $("#status").daqSelect({
        value: '',
        callback: function (data) {
        }
    });
</script>
