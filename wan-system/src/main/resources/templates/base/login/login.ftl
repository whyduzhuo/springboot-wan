<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="shortcut icon" href="/static/favicon.ico"/>
    <script type="text/javascript" src="/static/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/static/jquery/bootstrap.min.js"></script>
    <script type="text/javascript" src="/static/layui-v2.5.6/layui/layui.all.js"></script>
    <link href="/static/layui-v2.5.6/layui/css/layui.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="/static/bootstrap/bootstrap.min.css">

    <style type="text/css">
        #login-form {
            width: 300px;
            height: 600px;
            display: block;
            position: absolute;
            right: 30%;
            top: 20%;
        }
    </style>
</head>
<body>
    <img src="/static/img/login-background.jpg" style="position: absolute;top: 0px;right: 0px">
    <form method="post" id="login-form">
        <div class="form-group">
            <div class="col-xs-12 col-md-12">
                <label class="">用户名<span class="text-danger">*</span></label>
                <input class="form-control" name="username" value="admin">
            </div>
            <div class="col-xs-12 col-md-12">
                <label class="">密码<span class="text-danger">*</span></label>
                <input class="form-control" name="password" value="123456">
            </div>
            <div class="col-xs-12 col-md-12">
                <label>验证码</label>
                <input type="hidden" id="key" name="kaptchaKey">
                <div>
                    <div style="width: 50%;display: inline-block">
                        <input class="form-control" placeholder="请输入验证码" name="kaptchaValInput">
                    </div>
                    <img onclick="reloadKaptcha()" title="换一张" id="kaptcha" src="">
                </div>
            </div>
            <div class="col-xs-12 col-md-12">
                <label class="">是否记住<span class="text-danger">*</span></label>
                <input class="form-control" name="rememberMe" value="true">
            </div>
            <div class="col-xs-12 col-md-12" style="text-align: center">
                <button type="button" class="btn btn-success" style="width: 100%;margin-top: 20px;" onclick="login()">登录</button>
            </div>
        </div>
    </form>

    <script>
        function login() {
            layer.load();
            $.ajax({
                url: "login",
                type: "post",
                data: $('#login-form').serialize(),
                success :function (res) {
                    layer.closeAll("loading");
                    if (res.type=='SUCCESS'){
                        window.top.location.href="index";
                    }else {
                        layer.msg(res.msg, {icon: res.icon, time: 1000,skin:'.demo-class'});
                        setTimeout(function () {
                            reloadKaptcha();
                        },1500)
                    }

                }
            })
        }

        reloadKaptcha();
        function reloadKaptcha() {
            layer.load();
            $.ajax({
                url: "/kaptcha/kaptcha",
                type: "get",
                success :function (res) {
                    layer.closeAll("loading");
                    if (res.type=='SUCCESS'){
                        console.log(res);
                        $("#key").val(res.data.key);
                        $("#kaptcha").attr("src",res.data.img);
                    }else {
                        layer.msg(res.msg, {icon: res.icon, time: 1000,skin:'.demo-class'})
                    }
                },
                error:function () {
                    layer.closeAll("loading");
                }
            })
        }

        $(document).keyup(function(event){
            if(event.keyCode ==13){
                $("#submit").trigger("click");
            }
        });
    </script>
</body>
</html>