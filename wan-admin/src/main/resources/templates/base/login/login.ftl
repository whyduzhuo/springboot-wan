<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript" src="/js/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/js/jquery/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/layer-v2.4/layer.js"></script>
    <script type="text/javascript" src="/js/layer/layui.js"></script>

    <link rel="stylesheet" href="/js/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="/js/layer-v2.4/skin/layer.css">
    <link rel="stylesheet" href="/js/layer/layui.css">

    <style type="text/css">
        #login-form{
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
    <img src="/img/login-background.jpg" style="position: absolute;top: 0px;right: 0px">
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
                <label class="">是否记住<span class="text-danger">*</span></label>
                <input class="form-control" name="rememberMe" value="false">
            </div>
            <div class="col-xs-12 col-md-12" style="text-align: center">
                <button type="button" class="btn btn-success" style="width: 100%;margin-top: 20px;" onclick="login()">登录</button>
            </div>
        </div>
    </form>

    <script>
        function login() {
            $.ajax({
                url: "login",
                type: "post",
                data: $('#login-form').serialize(),
                success :function () {
                    window.location.href="index";
                }
            })
        }
    </script>
</body>
</html>