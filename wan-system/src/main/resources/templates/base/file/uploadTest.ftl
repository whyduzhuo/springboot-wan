<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript" src="/static/js/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/jquery/bootstrap.min.js"></script>
    <script type="text/javascript" src="/static/js/layer-v2.4/layer.js"></script>
    <script type="text/javascript" src="/static/js/layer/layui.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/clipboard.js/2.0.4/clipboard.min.js"></script>

    <link rel="stylesheet" href="/static/js/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="/static/js/layer-v2.4/skin/layer.css">
    <link rel="stylesheet" href="/static/js/layer/layui.css">

</head>
<body>
    <form method="post" id="dataForm"  enctype ="multipart/form-data">
        <div class="form-group">
            <div class="col-xs-4 col-md-4">
                <label class="">文件<span class="text-danger">*最大1MB</span></label>
                <input class="form-control" name="file" type="file" id="file" placeholder="请选择文件">
            </div>
            <div class="col-xs-12 col-md-12">
                <button type="button" class="btn btn-success" onclick="addData()">上传</button>
            </div>
            <a href="downloadTest">下载模板</a>
        </div>
    </form>
</body>
<script>
    function addData() {
        layer.load();
        var form = new FormData(document.getElementById('dataForm'));
        $.ajax({
            url:'upload',
            type: "post",
            processData : false,
            contentType : false,
            data: form,
            success:function (res) {
                layer.closeAll("loading");
                var i = 7;
                switch (res.type){
                    case 'SUCCESS':i=1;
                        break;
                    case 'WARN':i = 0;
                        break;
                    case 'ERROR':i =2;
                        break;
                }
                layer.confirm(res.message,{icon:i}, function (index) {
                    if(res.type=='SUCCESS'){
                        layer.load();
                        window.location.reload();
                    }
                    layer.close(index);
                });
            },
            error:function (XMLHttpRequest) {
                layer.closeAll("loading");
                alert("系统错误");
                console.log(XMLHttpRequest);
            }
        });
    }
</script>
</html>
