<!DOCTYPE html>
<html>
<head>
    <title></title>
    <link rel="shortcut icon" href="/static/favicon.ico"/>
    <script type="text/javascript" src="/static/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/static/jquery/bootstrap.min.js"></script>
    <script type="text/javascript" src="/static/layui-v2.5.6/layui/layui.all.js"></script>
    <link href="/static/layui-v2.5.6/layui/css/layui.css" rel="stylesheet" type="text/css">
    <link href="/static/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <style>
        .menu {
            moz-user-select: -moz-none;
            -moz-user-select: none;
            -o-user-select: none;
            -khtml-user-select: none;
            -webkit-user-select: none;
            -ms-user-select: none;
            user-select: none;
        }

        html, body {
            margin: 0;
            border: 0;
            height: 100%;
            width: 100%;
        }

        .navbar-static-side {
            position: fixed;
            width: 200px;
            z-index: 2001;
            height: 100%;
            background-color: #2f4050;
        }

        #page-wrapper {
            height: 100%;
            position: inherit;
            margin: 0 0 0 200px;
            background-color: #ffffff;
        }

        .content-tabs {
            position: relative;
            height: 39px;
            background: #fafafa;
            line-height: 39px;
            border-bottom: 1px solid #ccc;
        }

        .page-tabs a {
            color: #999;
            display: block;
            float: left;
            border-right: solid 1px #eee;
            padding: 0 15px;
        }

        .page-tabs a.active:hover {
            background: #eaedf1;
            color: #23508e;
        }

        .page-tabs-content .active {
            background: #d0d4d8;
            color: #23508e;
        }

        .menuTab {
            position: relative;
        }

        .menuTab:hover {
            transition: all ease 300ms;
            background: #eaedf1;
            color: #23508e;
        }

        .wan_frame {
            width: 100%;
            height: 100%;
        }

        #content-main {
            height: calc(100% - 41px);
            overflow: hidden;
        }

        .menu {
            cursor: pointer;
            color: #ffffff;
            display: block;
            position: relative;
            font-weight: 600;
            padding: 14px 20px 14px 14px;
            border-bottom: 1px solid #283746;
        }

        .menu:hover {
            transition: all ease 300ms;
            background-color: #111111;
            color: #AAA;
        }

        .page-tabs a i {
            position: absolute;
            top: 1px;
            right: 1px;
        }

        .page-tabs a i:hover {
            color: #c00;
        }

        .nav-ul {
            transition: all ease 300ms;
            border-left: 2px solid #0000FF;
        }

        .nav-ul li a {
            padding-left: 20px;
        }

        .menuDict-open {
            transition: all ease 300ms;
            background-color: #1b3752;
        }
    </style>
</head>
<body id="body">
<#--左侧导航-->
<nav class="navbar-default navbar-static-side">
    <ul>
        <!----------------->
        <#--<li>-->
            <#--<a class="menu menuDict">代码生成</a>-->
            <#--<ul class="nav-ul" style="display: none">-->
                <#--<li>-->
                    <#--<a class="menu menuItem" data-id="/base/coding/index">菜单101</a>-->
                    <#--<a class="menu menuItem">菜单102</a>-->
                    <#--<a class="menu menuItem">菜单103</a>-->
                <#--</li>-->
            <#--</ul>-->
        <#--</li>-->
        <#--<li>-->
            <#--<a class="menu menuItem" data-id="/base/sysOperLog/list">日志列表</a>-->
        <#--</li>-->
        <!----------------->
            <#list menuList as menu>
                <#if menu.type=='页面'>
                 <li>
                     <a class="menu menuItem" data-id="${menu.path}">${menu.name}</a>
                 </li>
                <#elseif menu.type=='目录'>
                 <li>
                     <a class="menu menuDict">${menu.name}</a>
                     <ul class="nav-ul" style="display: none">
                     <li>
                         <#list menu.children as menu1>
                             <#if menu1.type=='页面'>
                             <li>
                                 <a class="menu menuItem" data-id="${menu1.path}">${menu1.name}</a>
                             </li>
                             <#elseif menu1.type=='目录'>
                             <li>
                                 <a class="menu menuDict">${menu1.name}</a>
                                 <ul class="nav-ul" style="display: none">
                                     <li>
                                         <#list menu1.children as menu2>
                                             <#if menu2.type=='页面'>
                                             <li>
                                                 <a class="menu menuItem" data-id="${menu2.path}">${menu2.name}</a>
                                             </li>
                                             <#elseif menu2.type=='目录'>
                                             <li>
                                                 <a class="menu menuDict">${menu2.name}</a>
                                                 <ul class="nav-ul" style="display: none">
                                                     <li>
                                                         <#list menu2.children as menu3>
                                                             <#if menu3.type=='页面'>
                                                             <li>
                                                                 <a class="menu menuItem" data-id="${menu3.path}">${menu3.name}</a>
                                                             </li>
                                                             <#elseif menu3.type=='目录'>
                                                             <li>
                                                                 <a class="menu menuDict">${menu3.name}</a>
                                                                 <ul class="nav-ul" style="display: none">
                                                                     <li>
                                                                         <#list menu3.children as menu4>
                                                                             <#if menu4.type=='页面'>
                                                                             <li>
                                                                                 <a class="menu menuItem" data-id="${menu4.path}">${menu4.name}</a>
                                                                             </li>
                                                                             <#elseif menu4.type=='目录'>
                                                                             <li>
                                                                                 <a class="menu menuDict">${menu4.name}</a>
                                                                                 <ul class="nav-ul" style="display: none">
                                                                                     <li>
                                                                                         <#list menu4.children as menu5>
                                                                                             <a class="menu menuItem" data-id="/base/coding/index">${menu5.name}</a>
                                                                                         </#list>
                                                                                     </li>
                                                                                 </ul>
                                                                             </li>
                                                                             </#if>
                                                                         </#list>
                                                                     </li>
                                                                 </ul>
                                                             </li>
                                                             </#if>
                                                         </#list>
                                                     </li>
                                                 </ul>
                                             </li>
                                             </#if>
                                         </#list>
                                     </li>
                                 </ul>
                             </li>
                             </#if>
                         </#list>
                         </li>
                     </ul>
                 </li>
                </#if>
            </#list>
    </ul>
</nav>
<div id="page-wrapper" class="gray-bg dashbard-1">
    <div class="row content-tabs">
        <nav class="page-tabs menuTabs">
            <div class="page-tabs-content" id="wan-menuTab-list" style="margin-left: 0;">
                <a href="javascript:" class="menuTab" data-id="/system/main">首页</a>

                <a href="javascript:" class="menuTab" style="float: right" onclick="loginOut()">退出</a>
                <a href="/base/refresh" class="menuTab" style="float: right">刷新权限</a>

            </div>
        </nav>
    </div>
    <div class="row mainContent" id="content-main">
        <iframe class="wan_frame" width="100%" height="100%" src="/system/main" frameborder="0"
                data-id="/system/main"></iframe>
    </div>
</div>
</body>
<script type="text/javascript">

    //点击菜单列表
    $(document).on("click", ".menuItem", function () {
        var url = $(this).attr("data-id");
        var title = $(this).text();
        showFrame(url, title);
    });

    //点击menuTab
    $(document).on("click", ".menuTab", function () {
        $(this).addClass("active");
        var url = $(this).attr("data-id");
        showFrame(url);
    });

    //点击关闭事件
    $(document).on("click", ".fa-times-circle", function (e) {
        var url = $(this).parent().attr("data-id");
        $(this).parent().remove();
        $(".wan_frame[src='" + url + "']").remove();
        $(".wan_frame:last").show(300);
        e.stopPropagation();
    });

    //展示某个frame,如果该frame没有，则建立一个fream和tab
    function showFrame(url, title) {
        $(".wan_frame").hide();
        $(".menuTab").removeClass("active");
        $(".menuTab[data-id='" + url + "']").addClass("active");
        var freme = $(".wan_frame[src='" + url + "']");
        if (freme.length > 0) {
            freme.show(300);
        } else {
            var tabHtml = '<a href="javascript:;" class="menuTab active" data-id="' + url + '">' + title + ' <i class="fa fa-times-circle"></i></a>';
            var frameHtml = '<iframe class="wan_frame"  width="100%" height="100%" src="' + url + '" frameborder="0" data-id="' + url + '"></iframe>';
            $("#wan-menuTab-list").append(tabHtml);
            $("#content-main").append(frameHtml);
            return true;
        }
    }

    $(".menuDict").click(function () {
        var ul = $(this).siblings("ul");
        var parent = $(this).parent();
        if (ul.is(':hidden')) {
            ul.show();
            parent.addClass("menuDict-open")
        } else {
            ul.hide();
            parent.removeClass("menuDict-open")
        }
    })

    function loginOut() {
        $.ajax({
            url:'logout',
            type:'get',
            success:function (res) {
                layer.msg(res.message);
                window.location.reload();
            }
        })
    }


</script>

</html>