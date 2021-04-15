<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="shortcut icon" href="/static/favicon.ico"/>
    <#include "/common/tmp/commom.ftl">
    <script src="/static/plugins/verify/js/crypto-js.js"></script>
    <script src="/static/plugins/verify/js/ase.js"></script>
    <script src="/static/plugins/verify/js/verify.js" ></script>
    <link rel="stylesheet" type="text/css" href="/static/plugins/verify/css/verify.css">
    <style type="text/css">
        #login-form {
            width: 300px;
            height: 600px;
            display: block;
            position: absolute;
            right: 30%;
            top: 20%;
        }
        body{
            background-color: #ffffff;
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
            <label class="css-input css-checkbox css-checkbox-primary remove-padding remove-margin">
                <input type="checkbox" name="rememberMe" /><span></span>
                记住我
            </label>
        </div>
        <div class="col-xs-12 col-md-12" style="text-align: center">
            <input type="hidden" name="captchaVerification" id="captchaVerification">
            <button id='btn' type="button" class="btn btn-success" style="width: 100%;margin-top: 10px">登录</button>
            <div id="mpanel2" style="">
            </div>
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
                }

            }
        })
    }

    $(document).keyup(function(event){
        if(event.keyCode ==13){
            $("#submit").trigger("click");
        }
    });
</script>
<script>
    $('#mpanel2').slideVerify({
        baseUrl:'',  //服务器请求地址, 默认地址为安吉服务器;
        mode:'pop',     //展示模式
        containerId:'btn',//pop模式 必填 被点击之后出现行为验证码的元素id
        imgSize : {       //图片的大小对象,有默认值{ width: '310px',height: '155px'},可省略
            width: '400px',
            height: '200px',
        },
        barSize:{          //下方滑块的大小对象,有默认值{ width: '310px',height: '50px'},可省略
            width: '400px',
            height: '40px',
        },
        beforeCheck:function(){  //检验参数合法性的函数  mode ="pop"有效
            var flag = true;
            //实现: 参数合法性的判断逻辑, 返回一个boolean值
            return flag
        },
        ready : function() {},  //加载完毕的回调
        success : function(params) { //成功的回调
            // params为返回的二次验证参数 需要在接下来的实现逻辑回传服务器
            // 例如: login($.extend({}, params))
            $("#captchaVerification").val(params.captchaVerification);
            setTimeout(function () {
                login();
            },1500);
        },
        error : function() {}        //失败的回调
    });
</script>
</body>
</html>