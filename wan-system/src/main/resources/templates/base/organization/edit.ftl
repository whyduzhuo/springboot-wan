<!DOCTYPE html>
<html>
<head>
    <title></title>
    <#include "/common/tmp/commom.ftl">
<body>
    <form id="dataForm">
        <input class="form-control" type="hidden" id="data_id" name="id" value="${data.id}">
        <div class="col-xs-12">
            <label for="parent_name">父级节点<span class="text-danger">*</span></label>
            <input class="form-control" type="hidden" id="parent_id" name="parent.id" value="${data.parent.id}">
            <input type="hidden" name="type" value="${data.type}" /><span></span>
            <input class="form-control" readonly type="text" id="parent_name" name="parent.name" value="${data.parent.name}">
        </div>

        <div class="col-xs-12">
            <label for="name">名称<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="name" name="name" value="${data.name}">
        </div>

        <div class="col-xs-12">
            <label for="order">排序<span class="text-danger">*</span></label>
            <input class="form-control" type="number" id="order" name="order" value="${data.order}">
        </div>

        <div class="col-xs-12 col-md-4" style="height: 60px;"></div>
        <div class="edit-win-foot">
            <button type="button" class="btn btn-success" onclick="save()">保存</button>
            <button type="button" class="btn btn-default" onclick="closeLayer()">关闭</button>
        </div>

    </form>
</body>
</head>
<script type="text/javascript">
    function closeLayer() {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }

    function save(){
        layer.load();
        var url = 'addData';
        var data_id = $("#data_id").val();
        if(data_id!=null && data_id!=""){
            url ='edit';
        }
        var data = $('#dataForm').serialize();
        ajaxPost(url,data,function (res) {
            if(url=='addData'){
                window.parent.addNode(res.data);
            }
            closeLayer();
        });
    }
</script>
</html>