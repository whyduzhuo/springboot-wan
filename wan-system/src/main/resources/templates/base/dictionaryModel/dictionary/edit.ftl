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
            <label for="modelName">模块名称<span class="text-danger">*</span></label>
            <input value="${data.dictModel.id}" type="hidden" name="dictModel.id">
            <input class="form-control" type="text" id="modelName" readonly value="${data.dictModel.modelName}">
        </div>

        <div class="col-xs-12">
            <label for="value">字典值<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="value" name="value" value="${data.value}" >
        </div>
        <div class="col-xs-12">
            <label for="code">字典code<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="code" name="code" value="${data.code}" >
        </div>

        <div class="col-xs-12">
            <label for="order">排序<span class="text-danger">*</span></label>
            <input class="form-control" type="number" id="order" name="order" value="${data.order}">
        </div>

        <div class="col-xs-12">
            <label for="status">启用状态<span class="text-danger">*</span></label>
            <#list statusList as status>
            <div class="exp_column">
                <label class="css-input css-checkbox css-checkbox-primary">
                    <input type="radio" <#if data.status == status>checked</#if> name="status"
                    value="${status}"/><span></span>
                </label>${status}
            </div>
            </#list>
        </div>
        <div class="col-xs-12">
            <label for="remark">备注</label>
            <textarea style="height: 100px" class="form-control" type="text" id="remark" name="remark" >${data.remark}</textarea>
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
            window.parent.location.reload();
        });
    }
</script>
</html>