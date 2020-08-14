<!DOCTYPE html>
<!--[if IE 9]>         <html class="ie9 no-focus"> <![endif]-->
<!--[if gt IE 9]><!-->
<html class="no-focus"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <title>列表</title>
    <script type="text/javascript" src="/static/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/static/jquery/bootstrap.min.js"></script>
    <script type="text/javascript" src="/static/layui-v2.5.6/layui/layui.all.js"></script>
    <link href="/static/layui-v2.5.6/layui/css/layui.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="/static/bootstrap/bootstrap.min.css">

    <meta name="author" content="bianmaren">
    <meta name="robots" content="noindex, nofollow">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0">
    <style>
        tr th,tr td{
            text-align: center;
        }
    </style>
</head>
<body>
<div>
    <a href="javascript:openImportWin();" class="btn btn-sm btn-success hidden-xs">
        <i class="fa fa-plus"></i>从Excel文件导入音乐</a>
</div>
<table class="table table-bordered" id="listTable">
    <tr>
        <th>id</th>
        <th>用户名</th>
        <th>姓名</th>
    </tr>
        <#list customSearch.pagedata.content as data>
        <tr>
            <td>${data.id}</td>
            <td>${data.username}</td>
            <td>${data.realname}</td>
        </tr>
        </#list>
</table>
</body>
</html>