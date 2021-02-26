<!DOCTYPE html>
<html>
<head>
    <title></title>
    <#include "/common/tmp/commom.ftl">
    <style>
        .menu-button{
            display: inline-block;
            width: 150px;
            height: 200px;
            padding: 20px;
            text-align: center;
            background-color: #fff;
            margin: 15px;
            transition: all ease 300ms;
        }
        .menu-button:hover{
            box-shadow: 0px 5px 10px #dadada;
            transform: translateY(-5px);
        }
    </style>
</head>
<body>
<@adminPageNav navName1='页面组' navName2='${menu.name}'/>

<div style="padding: 20px;text-align: center">
    <#list menuList as children>
        <@shiro.hasPermission name=("${children.num}")>
            <a class="menu-button" href="javaScript:void(0)" onclick="openTab('${children.path}','${children.name}')" >
                ${children.name}
            </a>
        </@shiro.hasPermission>
    </#list>
</div>
</body>
<script LANGUAGE="JavaScript">
    function openTab(url, title) {
        window.parent.showFrame(url, title);
    }
</script>
</html>
