<!DOCTYPE html>
<html>
<head>
    <title></title>
    <#include "/common/tmp/commom.ftl">
    <style type="text/css">
        .edit-win-foot{
            position: absolute;
            height: 35px;
            bottom: 0px;
            background-color: #87c5ff;
            width: 100%;
            text-align: center;
        }
    </style>
<body>
    <form id="dataForm">
        <input class="form-control" type="hidden" id="data_id" name="id" value="${data.id}">
        <div class="col-xs-12">
            <label for="parent_name">父级节点<span class="text-danger">*</span></label>
            <input class="form-control" type="hidden" id="parent_id" name="parent.id" value="${data.parent.id}">
            <input class="form-control" readonly type="text" id="parent_name" name="parent.name" value="${data.parent.name}">
        </div>
        <div class="col-xs-12">
            <label for="type">类型<span class="text-danger">*</span></label>
            <div class="form-control">
                 <#list typeList as type>
                     <label class="css-input css-checkbox css-checkbox-primary">
                         <input type="radio" name="type" value="${type}" <#if data.type==type>checked</#if> /><span></span>
                     </label>
                     <span>${type}</span>&nbsp;&nbsp;&nbsp;&nbsp;
                 </#list>
            </div>
        </div>

        <div class="col-xs-12">
            <label for="name">菜单名称<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="name" name="name" value="${data.name}">
        </div>

        <div class="col-xs-12">
            <label for="path">url<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="path" name="path" value="${data.path}" placeholder="没有地址输入#">
        </div>

        <div class="col-xs-12">
            <label for="num">权限编号<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="num" name="num" value="${data.num}">
        </div>

        <div class="col-xs-12">
            <label for="order">排序<span class="text-danger">*</span></label>
            <input class="form-control" type="number" id="order" name="order" value="${data.order}">
        </div>

        <div class="col-xs-12">
            <label for="remark">备注</label>
            <textarea style="width: 100%;height: 100px;resize: none" class="form-control" id="remark"
                      name="remark">${data.remark}</textarea>
        </div>
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
                window.parent.addMenu(res.data);
            }
            closeLayer();
        });
    }
</script>
</html>