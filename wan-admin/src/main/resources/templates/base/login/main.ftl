<!DOCTYPE html>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="/js/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/js/jquery/bootstrap.min.js"></script>
</head>
<body>
    <a href="http://localhost:8082/base/admin/2" target="_blank">
        <h1>查询用户</h1>
    </a>
    <a href="http://localhost:8082/base/position/1" target="_blank">
        <h1>查询职务</h1>
    </a>
    <a href="http://localhost:8082/base/organization/1" target="_blank">
        <h1>查询部门</h1>
    </a>
    <a href="http://localhost:8082/base/organization/1" target="_blank">
        <h1>查询部门</h1>
    </a>
    <a href="http://localhost:8082/base/sysOperLog/list" target="_blank">
        <h1>日志记录</h1>
    </a>
    <a href="http://localhost:8082/base/coding/index" target="_blank">
        <h1>代码生成</h1>
    </a>
    <button type="button" onclick="del()">
        <h1>删除</h1>
    </button>
    <button onclick="logout()" type="button">
        <h1>退出登录</h1>
    </button>
    <script>
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
    </script>
</body>
</html>