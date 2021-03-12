<!DOCTYPE html>
<html class="ie9 no-focus">
<head>
    <meta charset="utf-8">
    <title>请假申请列表</title>
    <#include "/common/tmp/commom.ftl">
</head>
<body>
<@adminPageNav navName1='请假申请' navName2='请假申请列表'/>
<form id="listForm" action="list" method="get">
<div class="page-head">
        <@pageHeadLeft>
        </@pageHeadLeft>
        <@pageHeadRight>
            <div class="search-item">
                <label>请求方式:</label>
                <select class="input-sm input-search" name="search_eq_method">
                    <option value="" selected>全部</option>
                    <#list methodList as method>
                         <option value="${method}" <#if searchParams['search_eq_method']==method>selected </#if>>${method}</option>
                    </#list>
                </select>
            </div>
            <div class="search-item">
                <label>用户:</label>
                <input class="input-sm input-search" name="search_like_admin.realname" value=""/>
            </div>
        </@pageHeadRight>
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
                <th>人员</th>
                <th>标题</th>
                <th>请假时间</th>
                <th>操作</th>
            </tr>
            <#list customSearch.pagedata.content as data>
            <tr>
                <td>
                    <label class="css-input css-checkbox css-checkbox-primary remove-padding remove-margin">
                        <input type="checkbox" name="ids" value="${data.id}"/><span></span>
                    </label>
                </td>
                <td>${data_index+1}</td>
                <td>${data.admin.realname}</td>
                <td>${data.title}</td>
                <td>${data.startTime}-${data.endTime}</td>
                <td>
                    <div class="btn-group">
                        <a href="javascript:;"
                           onclick="editWin(${data.id})"
                           class="btn btn-xs btn-primary" type="button"
                           data-toggle="tooltip" data-original-title="编辑">编辑</a>
                        <a href="javascript:;"
                           onclick="remove(${data.id})"
                           class="btn btn-xs btn-danger" type="button"
                           data-toggle="tooltip" data-original-title="删除">删除</a>
                    </div>
                </td>
            </tr>
            </#list>
        </table>
        <div class="row"><@pageingTemaplte customSearch.pagedata /></div>
    </div>
</form>

<script type="text/javascript">
    function addWin() {
        layer.open({
            type: 2,
            title: '新增请假申请',
            maxmin: true,
            area: ['500px', '600px'],
            content: 'addWin'
        });
    }

    function editWin() {
        layer.open({
            type: 2,
            title: '修改请假申请',
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