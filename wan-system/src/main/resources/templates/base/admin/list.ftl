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
<@adminPageNav navName1='系统管理' navName2='用户列表'/>
    <form id="listForm" action="list" method="get">
        <div class="page-head">
            <@pageHeadLeft>
            <@shiro.hasPermission name="100401">
                <button type="button" onclick="addWin()" class="btn btn-sm btn-success hidden-xs">新增</button>
            </@shiro.hasPermission>
            </@pageHeadLeft>
            <@pageHeadRight>
                <div class="search-item">
                    <label>姓名:</label>
                    <input class="input-sm input-search" name="search_like_realname" value="${searchParams['search_like_realname']}"/>
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
                                <button type="button" onclick="roleList(${data.id})" class="btn btn-xs btn-success">
                                    角色列表
                                </button>
                                <@shiro.hasPermission name="100400">
                                <#if data.isDelete=='否'>
                                    <button type="button" onclick="remove(${data.id},'禁用')" class="btn btn-xs btn-danger">
                                        禁用
                                    </button>
                                <#else >
                                    <button type="button" onclick="remove(${data.id},'启用')" class="btn btn-xs btn-success">
                                        启用
                                    </button>
                                </#if>
                                </@shiro.hasPermission>
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

    /**
     * 删除
     * @param id
     */
    function remove(id,msg) {
        ajaxDelete("del",{'id':id},"您确定的"+msg+"该用户？",function () {
            window.location.reload();
        });
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
    
    function addWin() {
        layer.open({
            type: 2,
            title: '用户新增',
            maxmin: true,
            area: ['500px', '600px'],
            content: 'addWin'
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
    
    function roleList(id) {
        layer.open({
            type: 2,
            title: '用户菜单',
            maxmin: true,
            area: ['600px', '90%'],
            content: 'showRoles?id='+id
        });
    }
</script>
</html>
