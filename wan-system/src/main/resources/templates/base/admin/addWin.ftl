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
        <input class="form-control" type="hidden" id="data_id" name="id">
        <div class="col-xs-12">
            <label for="username">账号<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="username" name="username">
        </div>

        <div class="col-xs-12">
            <label for="realname">昵称<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="realname" name="realname">
        </div>

        <div class="col-xs-12">
            <label for="password">密码<span class="text-danger">*</span></label>
            <input class="form-control" type="password" id="password" name="password" >
        </div>

        <div class="col-xs-12">
            <label for="re_password">再次确认密码<span class="text-danger">*</span></label>
            <input class="form-control" type="password" id="re_password" name="re_password">
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
        var url = 'addData';
        var data = $('#dataForm').serialize();
        if($("#password").val()!=$("#re_password").val()){
            alert("两次密码不一致！");
            return false;
        }
        ajaxPost(url,data,function () {
            window.parent.location.reload();
        });
    }
</script>
</html>