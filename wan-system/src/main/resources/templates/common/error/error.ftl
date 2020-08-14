<!DOCTYPE html>
<!--[if IE 9]>         <html class="ie9 no-focus"> <![endif]-->
<!--[if gt IE 9]><!-->
<html class="no-focus"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <title>出错了</title>
    <meta name="author" content="bianmaren">
    <meta name="robots" content="noindex, nofollow">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0">
    <style>
        .pulldown {
            top: 150px;
        }
        .col-sm-offset-3 {
            margin-left: 25%;
        }
        .col-sm-6 {
            width: 50%;
        }
        .text-amethyst {
            color: #a48ad4;
        }
        .font-w300 {
            font-weight: 300 !important;
        }
        body, h1, h2, h3, h4, h5, h6, .h1, .h2, .h3, .h4,.h5, .h6 {
            font-family: "Helvetica Neue", "Luxi Sans", "DejaVu Sans", Tahoma, "Hiragino Sans GB", STHeiti, "Microsoft YaHei";
        }
        h1,h2,h3,h4{
            margin: 0;
        }
        .fadeInUp {
            -webkit-animation-name: fadeInUp;
            animation-name: fadeInUp;
        }
        .bg-white {
            background-color: #fff;
        }
        .text-center {
            text-align: center;
        }
        .pulldown {
            position: relative;
        }
        .animated {
            -webkit-animation-duration: 1s;
            animation-duration: 1s;
            -webkit-animation-fill-mode: both;
            animation-fill-mode: both;
        }
    </style>
</head>
<body>
<div class="content bg-white text-center pulldown overflow-hidden">
    <div class="row">
        <div class="col-sm-6 col-sm-offset-3">
            <!-- Error Titles -->
            <h1 class="font-s36 font-w300 text-amethyst animated fadeInDown">提示</h1>
            <br/>
            <h2 class="h3 font-w300 push-20 animated fadeInUp">
            <#if content??>${content}</#if>
            </h2>
            <div class="push-50"></div>
        </div>
    </div>
</div>

</body>
</html>


