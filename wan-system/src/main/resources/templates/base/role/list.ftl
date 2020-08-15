<!DOCTYPE html>
<html>
<head>
    <title></title>
    <link rel="shortcut icon" href="/static/favicon.ico"/>
    <script type="text/javascript" src="/static/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/static/jquery/bootstrap.min.js"></script>
    <script type="text/javascript" src="/static/layui-v2.5.6/layui/layui.all.js"></script>

    <link href="/static/layui-v2.5.6/layui/css/layui.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="/static/bootstrap/bootstrap.min.css">
</head>
<body>
    <table class="table table-bordered" id="listTable">
        <tr>
            <th>id</th>
            <th>角色名称</th>
            <th>角色类型</th>
            <th>归属部门</th>
            <th>备注</th>
            <th>操作</th>
        </tr>
            <#list customSearch.pagedata.content as data>
            <tr>
                <td>${data.id}</td>
                <td>${data.name}</td>
                <td>${data.type}</td>
                <td>${data.organization.name}</td>
                <td>${data.remark}</td>
                <td>
                <@shiro.hasPermission name="100301">
                    <button type="button" class="btn btn-xs btn-primary" onclick="openEditWin(${data.id})">修改</button>
                </@shiro.hasPermission>
                <@shiro.hasPermission name="100304">
                    <button type="button" class="btn btn-xs btn-info" onclick="showAdmin(${data.id})">人员列表</button>
                </@shiro.hasPermission>
                <@shiro.hasPermission name="100305">
                    <button type="button" class="btn btn-xs btn-info" onclick="showMenu(${data.id})">菜单列表</button>
                </@shiro.hasPermission>
                <@shiro.hasPermission name="100302">
                    <button type="button" class="btn btn-xs btn-danger" onclick="del(${data.id})">删除</button>
                </@shiro.hasPermission>
                </td>
            </tr>
            </#list>
    </table>
<script>
    
    function openAddWin() {
        layer.open({
            type: 2,
            title: '新增菜单',
            maxmin: true,
            area: ['500px', '600px'],
            content: 'addWin'
        });
    }

    function openEditWin(id) {
        layer.open({
            type: 2,
            title: '编辑菜单',
            maxmin: true,
            area: ['500px', '600px'],
            content: 'editWin?id='+id
        });
    }
    
    function showAdmin(id) {
        layer.open({
            type: 2,
            title: '人员列表',
            maxmin: true,
            area: ['80%', '90%'],
            content: 'showAdmin?id='+id
        });
    }
    
    function showMenu(id) {
        layer.open({
            type: 2,
            title: '菜单列表',
            maxmin: true,
            area: ['60%', '90%'],
            content: 'showMenu?id='+id
        });
    }
    
    function del(id) {
        
    }
</script>
</body>
</html>