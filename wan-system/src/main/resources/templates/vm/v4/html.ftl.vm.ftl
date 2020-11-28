<!DOCTYPE html>
<html class="ie9 no-focus">
<head>
    <meta charset="utf-8">
    <title>${data.module}列表</title>
    <#noparse><#include "/common/tmp/commom.ftl"></#noparse>
</head>
<body>
<#noparse><@adminPageNav navName1='</#noparse>${data.module}<#noparse>' navName2='</#noparse>${data.module}<#noparse>列表'/></#noparse>
<form id="listForm" action="list" method="get">
    <div class="page-head">
        <#noparse><@pageHeadLeft></#noparse>
            <#noparse><@shiro.hasPermission name="1"></#noparse>
                <button type="button" class="btn btn-success btn-sm" onclick="addWin()">新增</button>
            <#noparse><@shiro.hasPermission name="1"></#noparse>
        <#noparse></@pageHeadLeft></#noparse>
        <#noparse><@pageHeadRight></#noparse>
            <div class="search-item">
                <label>请求方式:</label>
                <select class="input-sm input-search" name="search_eq_method">
                    <option value="" selected>全部</option>
                    <#noparse><#list methodList as method></#noparse>
                         <option value="<#noparse>${method}</#noparse>" <#noparse><#if searchParams['search_eq_method']</#noparse>==method>selected <#noparse></#if></#noparse>><#noparse>${method}</#noparse></option>
                    <#noparse></#list></#noparse>
                </select>
            </div>
            <div class="search-item">
                <label>用户:</label>
                <input class="input-sm input-search" name="search_like_admin.realname" value="${searchParams['search_like_admin.realname']}"/>
            </div>
        <#noparse></@pageHeadRight></#noparse>
    </div>
    <div class="page-body">
        <table class="table table-bordered" id="listTable">
            <tr>
                <th class="remove-padding-t remove-padding-b">
                    <label class="css-input css-checkbox css-checkbox-primary">
                        <input type="checkbox" id="selectAll"><span></span>
                    </label>
                </th>
                <th>序号</th>
                <th>字段1</th>
                <th>字段2</th>
                <th>字段3</th>
                <th>操作</th>
            </tr>
            <#noparse><#list customSearch.pagedata.content as data></#noparse>
            <tr>
                <td>
                    <label class="css-input css-checkbox css-checkbox-primary remove-padding remove-margin">
                        <input type="checkbox" name="ids" value="<#noparse>${data.id}</#noparse>"/><span></span>
                    </label>
                </td>
                <td><#noparse>${data_index+1}</#noparse></td>
                <td>数据1</td>
                <td>数据2</td>
                <td>数据3</td>
                <td>
                    <div class="btn-group">
                        <a href="javascript:;"
                           onclick="editWin(<#noparse>${data.id}</#noparse>)"
                           class="btn btn-xs btn-primary" type="button"
                           data-toggle="tooltip" data-original-title="编辑">编辑</a>
                        <a href="javascript:;"
                           onclick="remove(<#noparse>${data.id}</#noparse>)"
                           class="btn btn-xs btn-danger" type="button"
                           data-toggle="tooltip" data-original-title="删除">删除</a>
                    </div>
                </td>
            </tr>
            <#noparse></#list></#noparse>
        </table>
        <div class="row"><#noparse><@pageingTemaplte customSearch.pagedata /></#noparse></div>
    </div>
</form>

<script type="text/javascript">
    function addWin() {
        layer.open({
            type: 2,
            title: '新增${data.module}',
            maxmin: true,
            area: ['500px', '600px'],
            content: 'addWin'
        });
    }

    function editWin() {
        layer.open({
            type: 2,
            title: '修改${data.module}',
            maxmin: true,
            area: ['500px', '600px'],
            content: 'editWin?id='+id
        });
    }
    
    function remove(id) {
        ajaxDelete("del",{'id':id},"您确定删除?",function () {
            window.location.reload();
        })
    }

</script>
</body>
</html>