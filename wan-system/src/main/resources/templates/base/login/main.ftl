<!DOCTYPE html>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="/static/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/static/jquery/bootstrap.min.js"></script>
    <script type="text/javascript" src="/static/layui-v2.5.6/layui/layui.all.js"></script>
    <link href="/static/layui-v2.5.6/layui/css/layui.css" rel="stylesheet" type="text/css">
    <link href="/static/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <style>
        html,body{
            margin: 0;
            border: 0;
            height: 100%;
            width: 100%;
        }
        .navbar-static-side{
            position: fixed;
            width: 200px;
            z-index: 2001;
            height: 100%;
            background-color: #2f4050;
        }
        #page-wrapper{
            height: 100%;
            position: inherit;
            margin: 0 0 0 200px;
            background-color: #ffffff;
        }
        .content-tabs {
            position: relative;
            height: 39px;
            background: #fafafa;
            line-height: 38px;
            border-bottom: 1px solid #ccc;
        }
        .page-tabs a {
            color: #999;
            display: block;
            float: left;
            border-right: solid 1px #eee;
            padding: 0 15px;
        }
        .page-tabs a.active:hover, .page-tabs a.active i:hover {
            background: #eaedf1;
            color: #23508e;
        }
        .page-tabs a i:hover {
            color: #c00;
        }
        .page-tabs-content .active{
            background: #eaedf1;
            color: #23508e;
        }
        .menuTab:hover{
            transition: all ease 300ms;
            background: #eaedf1;
            color: #23508e;
        }
        .wan_frame {
            width: 100%;
            height: 100%;
            transition: all ease 300ms;
        }
        #content-main {
            height: calc(100% - 41px);
            overflow: hidden;
        }
        .menuItem{
            cursor: pointer;
            color: #ffffff;
            display: block;
            position: relative;
            font-weight: 600;
            padding: 14px 20px 14px 14px;
        }
        .menuItem:hover{
            transition: all ease 300ms;
            background-color: #111111;
            color: #AAA;
        }
    </style>
</head>
<body id="body">
    <#--左侧导航-->
    <nav class="navbar-default navbar-static-side">
        <ul>
            <li>
                <a class="menuItem" data-id="/base/coding/index">代码生成</a>
            </li>
            <li>
                <a class="menuItem" data-id="/base/sysOperLog/list">日志列表</a>
            </li>
        </ul>
    </nav>
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row content-tabs">
            <nav class="page-tabs menuTabs">
                <div class="page-tabs-content" style="margin-left: 0px;">
                    <a href="javascript:;" class="menuTab" data-id="/system/main">首页</a>
                    <a href="javascript:;" class="menuTab" data-id="/base/coding/index">代码生成 <i class="fa fa-times-circle"></i></a>
                    <a href="javascript:;" class="menuTab" data-id="/base/sysOperLog/list">日志列表 <i class="fa fa-times-circle"></i></a>
                </div>
            </nav>
        </div>
        <div class="row mainContent" id="content-main">
            <iframe class="wan_frame" name="iframe2" id="wan_frame" width="100%" height="100%" src="/base/sysOperLog/list" frameborder="0" data-id="/base/coding/index" seamless=""></iframe>
        </div>
    </div>
</body>
<script type="text/javascript">

    $(document).on("click",".menuItem",function () {
        var url=$(this).attr("data-id");
        $("#wan_frame").attr("data-id",url);
        $("#wan_frame").attr("src",url);
    });
    
    $(".menuTab").click(function () {
        $(".menuTab").removeClass("active");
        $(this).addClass("active");
        var url = $(this).attr("data-id");
        $("#wan_frame").attr("data-id",url);
        $("#wan_frame").attr("src",url);
    });
</script>

</html>