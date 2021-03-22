<!DOCTYPE html>
<html>
<head>
    <title></title>
    <#include "/common/tmp/commom.ftl">
</head>
<body>
<@adminPageNav navName1='' navName2='拥有此菜单的角色'/>
    <form id="listForm" action="list" method="get">
        <div class="page-body">
            <table class="table table-bordered" id="listTable">
                <tr>
                    <th>id</th>
                    <th>角色名称</th>
                    <th>角色编号</th>
                    <th>角色类型</th>
                    <th>排序</th>
                    <th>备注</th>
                    <th>类别</th>
                    <th>查看</th>
                </tr>
                    <#list dataList as data>
                    <tr>
                        <td>${data.id}</td>
                        <td>${data.name}</td>
                        <td>${data.roleNum}</td>
                        <td>${data.type}</td>
                        <td>${data.remark}</td>
                        <td>${data.typeHtml}</td>
                        <td>
                            <@shiro.hasPermission name="100304">
                                <a class="a-line-bottom" href="javascript:void(0);"  onclick="showAdmin(${data.id})">人员列表</a>
                            </@shiro.hasPermission>
                        </td>
                        <td>
                            <div class="btn-group">
                            <@shiro.hasPermission name="100305">
                                <button type="button" class="btn btn-xs btn-info" onclick="showMenu(${data.id})">菜单授权</button>
                            </@shiro.hasPermission>
                            </div>
                        </td>
                    </tr>
                    </#list>
            </table>
        </div>
    </form>
<script>


    function showAdmin(id) {
        layer.open({
            type: 2,
            title: '人员列表',
            maxmin: true,
            area: ['80%', '90%'],
            content: '/base/role/showAdmin?roleId='+id
        });
    }
    
    function showMenu(id) {
        layer.open({
            type: 2,
            title: '菜单列表',
            maxmin: true,
            area: ['60%', '90%'],
            content: '/base/role/showMenu?id='+id
        });
    }

</script>
</body>
</html>