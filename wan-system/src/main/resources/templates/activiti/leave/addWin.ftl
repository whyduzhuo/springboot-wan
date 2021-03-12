<!DOCTYPE html>
<html>
<head>
    <title></title>
    <#include "/common/tmp/commom.ftl">
<body>
<form id="dataForm">
    <div class="form-group">
        <input type="hidden" name="id" id="data_id" value="${data.id}"/>
        <div class="col-xs-12 col-md-4">
            <label for="title">标题<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="title" name="title" maxlength="200" value="${data.title}"
                   required/>
        </div>
        <div class="col-xs-12 col-md-4">
            <label for="startTime">开始时间<span class="text-danger">*</span></label>
            <input class="form-control time" type="text" id="startTime" name="startTime" maxlength="200" value="${data.startTime}"
                   required/>
        </div>
        <div class="col-xs-12 col-md-4">
            <label for="endTime">结束时间<span class="text-danger">*</span></label>
            <input class="form-control time" type="text" id="endTime" name="endTime" maxlength="200" value="${data.endTime}"
                   required/>
        </div>

        <div class="col-xs-12 col-md-4">
            <label for="reason">原因<span class="text-danger">*</span></label>
            <textarea style="width: 100%;height: 150px;resize: none" class="form-control" id="reason"
                      name="reason">${data.reason}</textarea>
        </div>
        <div class="col-xs-12 col-md-4" style="height: 60px;"></div>
        <div class="edit-win-foot">
            <button type="button" class="btn btn-success" onclick="save()">保存</button>
            <button type="button" class="btn btn-default" onclick="closeLayer()">关闭</button>
        </div>
    </div>
</form>
</body>
</head>
<script type="text/javascript" src="/static/plugins/bootstrap-datetimepicker/moment.min.js"></script>
<script type="text/javascript" src="/static/plugins/bootstrap-datetimepicker/locale/zh-cn.js"></script>
<script type="text/javascript" src="/static/plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.js"></script>
<script type="text/javascript">
    function closeLayer() {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }

    function save(){
        layer.load();
        var url = 'addData';
        var data = $('#dataForm').serialize();
        ajaxPost(url,data,function (res) {
            window.location.reload();
            closeLayer();
        });
    }

    $('.time').datetimepicker({
        format: 'YYYY-MM-DD HH:00'
    });
</script>
</html>