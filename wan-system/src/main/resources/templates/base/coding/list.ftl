<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript" src="/static/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/static/jquery/bootstrap.min.js"></script>
    <script type="text/javascript" src="/static/layui-v2.5.6/layui/layui.all.js"></script>
    <link href="/static/layui-v2.5.6/layui/css/layui.css" rel="stylesheet" type="text/css">
    <script src="/static/js/clipboard.min.js"></script>

    <link rel="stylesheet" href="/static/bootstrap/bootstrap.min.css">
    <style>
        .layui-layer-title{
            overflow:auto !important;
        }
        xmp{
            background-color: #2b2b2b;
            color: #e7be55;
            font-family: Consolas;
            font-size: 16px;
        }
        body{
            padding-top: 10px;
            display: flex;
        }
        .left{
            padding: 10px;
            padding-top: 0px;
            flex: 6;
            max-height: 800px;
            overflow: hidden;
            overflow-y: scroll
        }
        .left ul li a{
            transition: all ease 0ms;
            text-decoration:none;
            color: #38a038;
            display: inline-block;
            width: 100%;
            height: 40px;
            line-height: 40px;
            text-align: center;
            font-size: 18px;
            background-color: #F0F0F0;
            cursor: pointer;
        }
        .left ul li a.left-on{
            background-color: #1c5db7;
            color: #FFF;
        }
        .left ul li a:hover{
            background-color: #4f8ec5;
            color: #FFF;
        }
        .right{
            flex: 4;
        }
    </style>
</head>
<body>
    <div class="left">
        <ul>
            <#list dataList as data>
            <li><a href="javascript:;" onclick="writeInForm(${data.id?c})">${data.module}${data.entityPackages}</a></li>
            </#list>
        </ul>
    </div>
    <div class="right">
        <form method="post" id="dataForm">
            <div class="form-group">
                <div class="col-xs-12 col-md-12">
                    <label class="">作者<span class="text-danger">*</span></label>
                    <input class="form-control" name="author" id="author" placeholder="麻花疼" value="麻花疼">
                </div>
                <div class="col-xs-12 col-md-12">
                    <label class="">业务名<span class="text-danger">*</span></label>
                    <input class="form-control" name="module" id="module"  placeholder="音乐" value="音乐">
                </div>
                <div class="col-xs-12 col-md-12">
                    <label class="">完整类名<span class="text-danger">*</span></label>
                    <input class="form-control" name="entityPackages" id="entityPackages" value="com.diange.entity.jiangli.Music" placeholder="com.diange.entity.jiangli.Music">
                </div>
                <div class="col-xs-12 col-md-12">
                    <label class="">controller包名<span class="text-danger">*</span></label>
                    <input class="form-control" name="controllerpackage" id="controllerpackage" value="com.diange.controller.admin.jiangli" placeholder="com.diange.controller.admin.jiangli">
                </div>
                <div class="col-xs-12 col-md-12">
                    <label class="">service包名<span class="text-danger">*</span></label>
                    <input class="form-control" name="servicepackage" id="servicepackage" value="com.diange.service.jiangli" placeholder="com.diange.service.jiangli">
                </div>
                <div class="col-xs-12 col-md-12">
                    <label class="">dao包名<span class="text-danger">*</span></label>
                    <input class="form-control" name="daopackage" id="daopackage" value="com.diange.dao.jpa.jiangli" placeholder="com.diange.dao.jpa.jiangli">
                </div>
                <div class="col-xs-12 col-md-12">
                    <button type="button" class="btn btn-primary" onclick="save('v1')">财大学工</button>
                    <button type="button" class="btn btn-primary" onclick="save('v2')">乡镇平台saas</button>
                    <button type="button" class="btn btn-primary" onclick="save('v3')">乡镇平台saas2</button>
                    <button type="button" class="btn btn-primary" onclick="save('v4')">本项目</button>
                    <button type="button" class="btn btn-success" onclick="addData()">保存数据库</button>
                </div>
            </div>
        </form>
    </div>
</body>
<script>
    function save(v) {
        var detail ="";
        var html="";
        var controller="";
        var service="";
        var dao="";
        var mapper="";

        $.ajax({
            url:'getDetail/'+v,
            type: "get",
            async:false,
            data: $('#dataForm').serialize(),
            success:function (data) {
                detail = data;
            },
            error:function (XMLHttpRequest) {
                alert("系统错误");
                console.log(XMLHttpRequest);
            }
        });

        $.ajax({
            url:'getHtml/'+v,
            type: "get",
            async:false,
            data: $('#dataForm').serialize(),
            success:function (data) {
                html = data;
            },
            error:function (XMLHttpRequest) {
                alert("系统错误");
                console.log(XMLHttpRequest);
            }
        });
        $.ajax({
            url:'getController/'+v,
            type: "get",
            async:false,
            data: $('#dataForm').serialize(),
            success:function (data) {
                controller = data;
            },
            error:function (XMLHttpRequest) {
                alert("系统错误");
                console.log(XMLHttpRequest);
            }
        });
        $.ajax({
            url:'getService/'+v,
            type: "get",
            async:false,
            data: $('#dataForm').serialize(),
            success:function (data) {
                service = data;
            },
            error:function (XMLHttpRequest) {
                alert("系统错误");
                console.log(XMLHttpRequest);
            }
        });
        $.ajax({
            url:'getDao/'+v,
            type: "get",
            async:false,
            data: $('#dataForm').serialize(),
            success:function (data) {
                dao = data;
            },
            error:function (XMLHttpRequest) {
                alert("系统错误");
                console.log(XMLHttpRequest);
            }
        });
        $.ajax({
            url:'getMapper/'+v,
            type: "get",
            async:false,
            data: $('#dataForm').serialize(),
            success:function (data) {
                mapper = data;
            },
            error:function (XMLHttpRequest) {
                alert("系统错误");
                console.log(XMLHttpRequest);
            }
        });
        layer.tab({
            area:['70%', '90%'],
            btn: ['复制','关闭'],
            tab: [{
                title: 'detail',
                content: '<xmp style="padding:20px;">'+detail+'</xmp>'
            }, {
                title: 'Html',
                content: '<xmp style="padding:20px;">'+html+'</xmp>'
            }, {
                title: 'controller',
                content: '<xmp style="padding:20px;">'+controller+'</xmp>'
            }, {
                title: 'service',
                content: '<xmp style="padding:20px;">'+service+'</xmp>'
            }, {
                title: 'dao',
                content: '<xmp style="padding:20px;">'+dao+'</xmp>'
            }, {
                    title: 'mapper',
                    content: '<xmp style="padding:20px;">'+mapper+'</xmp>'
                }
            ],
            btn1:function (index, layero) {
                var code = $(".layui-layer-tabli:visible xmp").html();
                var promise = navigator.clipboard.writeText(code);
                layer.msg('复制成功！', {
                    icon: 6,
                    time:1000,
                });
            }

        });
    }

    $(".left").on('click',"ul li a",function () {
        $(".left ul li a").removeClass("left-on");
        $(this).addClass("left-on");
    });
    
    function writeInForm(id) {
        layer.load();
        console.log(id);
        $.ajax({
            url:'findById',
            type: "get",
            data:{
                "id":id
            },
            success:function (data) {
                layer.closeAll("loading");
                $("#author").val(data.author);
                $("#module").val(data.module);
                $("#entityPackages").val(data.entityPackages);
                $("#controllerpackage").val(data.controllerpackage);
                $("#servicepackage").val(data.servicepackage);
                $("#daopackage").val(data.daopackage);
            },
            error:function (XMLHttpRequest) {
                layer.closeAll("loading");
                console.log(XMLHttpRequest);
            }
        })
    }

    function addData() {
        layer.load();
        $.ajax({
            url:'addData',
            type: "post",
            data: $('#dataForm').serialize(),
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
