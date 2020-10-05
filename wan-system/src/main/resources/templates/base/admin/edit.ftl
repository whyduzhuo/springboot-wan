<!DOCTYPE html>
<html>
<head>
    <title></title>
    <link rel="shortcut icon" href="/static/favicon.ico"/>
    <script type="text/javascript" src="/static/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/static/jquery/bootstrap.min.js"></script>
    <script type="text/javascript" src="/static/layui-v2.5.6/layui/layui.all.js"></script>
    <script type="text/javascript" src="/static/zTree_v3-master/js/jquery.ztree.all.js"></script>

    <link href="/static/layui-v2.5.6/layui/css/layui.css" rel="stylesheet" type="text/css">
    <link href="/static/zTree_v3-master/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="/static/bootstrap/bootstrap.min.css">
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
            <label for="username">账号<span class="text-danger">*</span></label>
            <input class="form-control" readonly type="text" id="username" name="username" value="${data.username}">
        </div>

        <div class="col-xs-12">
            <label for="realname">昵称<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="realname" name="realname" value="${data.realname}">
        </div>

        <div class="edit-win-foot">
            <button type="button" class="btn btn-success" onclick="ajaxSubmit()">保存</button>
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

    function ajaxSubmit(){
        layer.load();
        var url = '/base/admin/addData';
        var data_id = $("#data_id").val();
        if(data_id!=null && data_id!=""){
            url ='edit';
        }
        console.log($('#dataForm').serialize());
        $.ajax({
            url: url,
            type: "POST",
            data: $('#dataForm').serialize(),
            success: function (res) {
                layer.closeAll("loading");
                layer.confirm(res.msg,{icon:res.icon}, function (index) {
                    if(res.type =='SUCCESS'){
                        layer.load();
                        if(url=='addData'){
                            window.parent.addMenu(res.data);
                        }
                        closeLayer();
                    }
                    layer.close(index);
                });
            },
            error: function (XMLHttpRequest) {
                layer.closeAll("loading");
                alertErrorMessage(XMLHttpRequest);
            }
        });
    }
</script>
</html>