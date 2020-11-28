<!DOCTYPE html>
<html>
<head>
    <title></title>
    <#noparse><#include "/common/tmp/commom.ftl"></#noparse>
<body>
<form id="inputForm" class="form-horizontal" >
    <div class="form-group">
        <input type="hidden" name="id" id="data_id" value="<#noparse>${data.id}</#noparse>"/>
        <div class="col-xs-12 col-md-4">
            <label for="column1">输入1<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="column1" name="column1" maxlength="200" value="<#noparse>${data.column1}</#noparse>"
                   required/>
        </div>
        <div class="col-xs-12 col-md-4">
            <label for="column2">选择1<span class="text-danger">*</span></label>
            <select class="form-control" name="column2" id="column2">
                    <#noparse>[#list  statusList as status]</#noparse>
                    <option value="<#noparse>${status}</#noparse>"  <#noparse>[#if data.column2 == status]</#noparse>selected<#noparse>[/#if]</#noparse>><#noparse>${status}</#noparse></option>
                    <#noparse>[/#list]</#noparse>
            </select>
        </div>

        <div class="col-xs-12 col-md-4 ">
            <label for="column3">选择3<span class="text-danger">*</span></label>
            <div class="exp_column">
                <label class="css-input css-checkbox css-checkbox-primary">
                <input type="radio" name="column3" value="是" <#noparse>[#if data.column3 == '是']</#noparse>checked<#noparse>[/#if]</#noparse> /><span></span>
                </label>是
            </div>

            <div class="exp_column">
                <label class="css-input css-checkbox css-checkbox-primary">
                <input type="radio" name="column3" value="否" <#noparse>[#if data.column3 == '否']</#noparse>checked<#noparse>[/#if]</#noparse> /><span></span>
                </label>否
            </div>

        </div>
        <div class="col-xs-12 col-md-4">
            <label for="column4">输入4<span class="text-danger">*</span></label>
            <textarea style="width: 100%;height: 150px;resize: none" class="form-control" id="column4"
                      name="column4"><#noparse>${data.column4}</#noparse></textarea>
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