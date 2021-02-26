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
                                <@shiro.hasPermission name="100402">
                                <button type="button" onclick="detail(${data.id})" class="btn btn-xs btn-primary">
                                    编辑
                                </button>
                                </@shiro.hasPermission>
                                <button type="button" onclick="menuList(${data.id})" class="btn btn-xs btn-info">
                                    菜单列表
                                </button>
                                <button type="button" onclick="roleList(${data.id})" class="btn btn-xs btn-success">
                                    角色列表
                                </button>
                                <@shiro.hasPermission name="100400">
                                <#if data.delTime==0>
                                    <button type="button" onclick="remove(${data.id},'禁用')" class="btn btn-xs btn-danger">
                                        禁用
                                    </button>
                                <#else >
                                    <button type="button" onclick="remove(${data.id},'启用')" class="btn btn-xs btn-success">
                                        启用
                                    </button>
                                </#if>
                                </@shiro.hasPermission>
                                <@shiro.hasPermission name="100404">
                                <button type="button" onclick="resetPasswordWin(${data.id})" class="btn btn-xs btn-primary">
                                    修改密码
                                </button>
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
<!-- 编辑/新增结点对话框 -->
<div class="modal fade" id="modal-add" tabindex="-1" data-backdrop="static" role="dialog" aria-hidden="true" style="display: none;position: absolute">
    <div class="modal-dialog  modal-dialog-popin" style="min-width: 400px;max-width: 600px;">
        <div class="modal-content">
            <div class="block block-themed block-transparent remove-margin-b">
                <div class="block-header bg-info" style="background-color: #1c8eb9">
                    <ul class="block-options">
                        <li>
                            <button data-dismiss="modal" type="button"><i class="si si-close"></i></button>
                        </li>
                    </ul>
                    <h3 class="block-title">重置密码</h3>
                </div>
                <div class="block-content">
                    <form class="form-horizontal push-10-t" method="post" onsubmit="return false;" id="dataForm"
                          style="margin: 0 auto;position: relative;">
                        <div class="form-group">
                            <div class="col-xs-12">
                                <label for="password">新密码<span class="text-danger">*</span></label>
                                <input class="form-control" type="password"  name="password">
                                <input id="adminId" type="hidden" name="adminId">
                            </div>
                            <div class="col-xs-12">
                                <label for="password">确认密码<span class="text-danger">*</span></label>
                                <input class="form-control" type="password"  name="repassword">
                            </div>
                        </div>
                        <div class="alert alert-danger alert-dismissable">
                            <button type="button" class="close" data-dismiss="alert"
                                    aria-hidden="true">
                                &times;
                            </button>提示：密码至少输入6位,修改密码之后，用户登录失效
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-sm btn-default" type="button" data-dismiss="modal">关闭</button>
                <button class="btn btn-sm btn-primary" id="addNodeButton" type="button" onclick="resetPassword()"><i class="fa fa-check"></i> 提交
                </button>
            </div>
        </div>
    </div>
</div>
<script>
    function resetPasswordWin(id) {
        $('#adminId').val(id);
        $('#modal-add').modal('show')
    }
    function resetPassword() {
        ajaxPost("resetPassword",$('#dataForm').serialize(),function () {
            $('#modal-add').modal('hide');
        });
    }
</script>
</html>
