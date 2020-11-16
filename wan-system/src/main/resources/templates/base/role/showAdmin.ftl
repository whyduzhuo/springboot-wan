<!DOCTYPE html>
<html class="ie9 no-focus">
<head>
    <meta charset="utf-8">
    <title>列表</title>
    <meta name="robots" content="noindex, nofollow">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0">
    <#include "/common/tmp/commom.ftl">
</head>
<body>
<form id="listForm" action="showAdmin" method="get">
    <div class="page-head">
            <@pageHeadLeft>
                <a href="javascript:refulsh();" class="btn btn-sm btn-success hidden-xs">刷新</a>
            </@pageHeadLeft>
            <@pageHeadRight>
                <div class="search-item">
                    <label>姓名:</label>
                    <input class="input-sm input-search" name="search_like_realname" value="${searchParams['search_like_realname']}"/>
                    <input type="hidden" name="roleId" value="${roleId}">
                </div>
            </@pageHeadRight>
    </div>
    <div class="page-body">
        <table class="table table-bordered" id="listTable">
            <tr>
                <th>账号</th>
                <th>用户名</th>
                <th>角色</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
                <#list customSearch.pagedata.content as data>
                    <tr>
                        <td>${data.username}</td>
                        <td>${data.realname}</td>
                        <td>${data.roleListStr}</td>
                        <td>${data.isDeleteHtml}</td>
                        <td>
                            <div class="btn-group">
                                <button type="button" onclick="detail(${data.id})" class="btn btn-xs btn-primary">
                                    详情
                                </button>
                                <button type="button" onclick="menuList(${data.id})" class="btn btn-xs btn-info">
                                    菜单列表
                                </button>
                            </div>
                        </td>
                    </tr>
                </#list>
        </table>
        <div class="row"><@pageingTemaplte customSearch.pagedata /></div>
    </div>
</form>
</body>
<script>
    function refulsh() {
        window.location.reload();
    }



    function detail(id) {
        layer.open({
            type: 2,
            title: '用户详情',
            maxmin: true,
            area: ['500px', '600px'],
            content: 'detail?id='+id
        });
    }

    function menuList(id) {
        layer.open({
            type: 2,
            title: '用户菜单',
            maxmin: true,
            area: ['500px', '90%'],
            content: 'menuList?id='+id
        });
    }
</script>
</html>
