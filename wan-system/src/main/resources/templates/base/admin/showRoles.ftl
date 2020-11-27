<!DOCTYPE html>
<html class="ie9 no-focus">
<head>
    <meta charset="utf-8">
    <title>列表</title>
    <#include "/common/tmp/commom.ftl">
</head>
<body>
<@adminPageNav navName1='用户管理' navName2='角色授予'/>
<form id="listForm" action="showRoles" method="get">
    <div class="page-head">
            <@pageHeadLeft>
            <@shiro.hasPermission name="100403">
                <button class="btn btn-success" type="button" onclick="grantRoles()">授予</button>
            </@shiro.hasPermission>
            </@pageHeadLeft>
            <@pageHeadRight>
                <div class="search-item">
                    <label>角色名称:</label>
                    <input type="hidden" name="id" value="${admin.id}">
                    <input class="input-sm input-search" name="name" value="${name}"/>
                </div>

            </@pageHeadRight>
    </div>
    <div class="page-body">
        <table class="table table-bordered" id="listTable">
            <tr>
                <th class="remove-padding-t remove-padding-b">

                </th>
                <th>角色</th>
                <th>类型</th>
            </tr>
                <#list roleList as data>
                <tr>
                    <td>
                        <label class="css-input css-checkbox css-checkbox-primary remove-padding remove-margin">
                            <input type="checkbox" name="ids" <#if data.type=="固定角色">disabled</#if> <#if data.checked>checked</#if> value="${data.id}"/><span></span>
                        </label>
                    </td>
                    <td>${data.name}</td>
                    <td>${data.typeHtml}</td>

                </tr>
                </#list>
        </table>
    </div>
</form>

<script type="text/javascript">

    function closeLayer() {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }

    function grantRoles() {
        var url = 'grantRoles';
        var roleIds=[];
        $('input[name="ids"]:checked').each(function(){
            roleIds.push($(this).val());
        });
        var data={
            'id':'${admin.id}',
            'roleIds':roleIds
        };
        ajaxPost(url,data, function (res) {
            console.log(res);
            closeLayer();
        });
    }
</script>
</body>
</html>