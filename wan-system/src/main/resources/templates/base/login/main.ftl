<!DOCTYPE html>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="/static/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/static/jquery/bootstrap.min.js"></script>
    <script type="text/javascript" src="/static/layui-v2.5.6/layui/layui.all.js"></script>
    <link href="/static/layui-v2.5.6/layui/css/layui.css" rel="stylesheet" type="text/css">

</head>
<body>
    <a href="/base/admin/2" target="_blank">
        <h1>查询用户</h1>
    </a>
    <a href="/base/position/1" target="_blank">
        <h1>查询职务</h1>
    </a>
    <a href="/base/organization/1" target="_blank">
        <h1>查询部门</h1>
    </a>
    <a href="/base/organization/1" target="_blank">
        <h1>查询部门</h1>
    </a>
    <a href="/base/sysOperLog/list" target="_blank">
        <h1>日志记录</h1>
    </a>
    <a href="/base/coding/index" target="_blank">
        <h1>代码生成</h1>
    </a>
    <a href="/base/proFile/uploadTest" target="_blank">
        <h1>文件上传测试</h1>
    </a>
    <a href="/swagger-ui.html" target="_blank">
        <h1>swagger</h1>
    </a>
    <button onclick="openAddWin()" type="button">layer弹框</button>

    <button onclick="logout()" type="button">
        <h1>退出登录</h1>
    </button>
</body>
<script type="text/javascript">
    function logout() {
        $.ajax({
            url: "logout",
            type: "get",
            success :function () {
                window.location.href="login";
            }
        })
    }

    function del() {
        $.ajax({
            url: "/base/sysOperLog/1892",
            type: "post",
            data:{
                _method:"DELETE",
            },
            success :function (message) {
                alert(message.type);
                console.log(message.content)
            }
        })
    }

    function openAddWin() {
        layer.open({
            type: 2,
            title: '选择人员',
            shade: true,
            maxmin: true,
            area: ['80%', '80%'],
            content: '/base/coding/index'
        });
    }
</script>

</html>