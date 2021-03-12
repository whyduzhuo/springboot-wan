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
            <label for="column1">输入1<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="column1" name="column1" maxlength="200" value="${data.column1}"
                   required/>
        </div>
        <div class="col-xs-12 col-md-4">
            <label for="column2">部门<span class="text-danger">*</span></label>
            <select class="form-control" name="column2" id="column2">
                <option value="" disabled selected hidden>请选择</option>
                <#list  statusList as status>
                <option value="${status}"  <#if data.column2 == status>selected</#if>>${status}</option>
                </#list>
            </select>
        </div>

        <div class="col-xs-12 col-md-4 ">
            <label for="column3">选择3<span class="text-danger">*</span></label>
            <div class="exp_column">
                <label class="css-input css-checkbox css-checkbox-primary">
                    <input type="radio" name="column3" value="是" <#if data.column3 == '是'>checked</#if> /><span></span>
                    是
                </label>
            </div>

            <div class="exp_column">
                <label class="css-input css-checkbox css-checkbox-primary">
                    <input type="radio" name="column3" value="否" <#if data.column3 == '否'>checked</#if> /><span></span>
                    否
                </label>
            </div>

        </div>
        <div class="col-xs-12 col-md-4">
            <label for="column4">输入4<span class="text-danger">*</span></label>
            <textarea style="width: 100%;height: 150px;resize: none" class="form-control" id="column4"
                      name="column4">${data.column4}</textarea>
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
            closeLayer();
        });
    }
</script>
</html>