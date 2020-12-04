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
            <label for="name">角色名称<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="name" name="name" maxlength="200" value="${data.name}"
                   required/>
        </div>
        <div class="col-xs-12 col-md-4">
            <label for="organization_id">部门<span class="text-danger">*</span></label>
            <select class="form-control" name="organization.id" id="organization_id">
                <#list  orgList as org>
                    <option value="${org.id}"  <#if data.organization.id == org.id>selected</#if>>${org.name}</option>
                </#list>
            </select>
        </div>

        <div class="col-xs-12 col-md-4 ">
            <label for="column3">角色类别<span class="text-danger">*</span></label>
            <input type="hidden" name="type" value="${data.type}">
            <div class="exp_column">
                ${data.typeHtml}
            </div>
        </div>

        <div class="col-xs-12">
            <label for="order">排序<span class="text-danger">*</span></label>
            <input class="form-control" type="number" id="order" name="order" value="${data.order}">
        </div>

        <div class="col-xs-12 col-md-4">
            <label for="remark">备注</label>
            <textarea style="width: 100%;height: 150px;resize: none" class="form-control" id="remark"
                      name="remark">${data.remark}</textarea>
        </div>

        <div class="edit-win-foot">
            <button type="button" class="btn btn-success" onclick="save()">保存</button>
            <button type="button" class="btn btn-default" onclick="closeLayer()">关闭</button>
        </div>
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