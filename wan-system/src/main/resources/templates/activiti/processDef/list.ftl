<!DOCTYPE html>
<html class="ie9 no-focus">
<head>
    <meta charset="utf-8">
    <title>流程定义列表</title>
    <#include "/common/tmp/commom.ftl">
</head>
<body>
<@adminPageNav navName1='工作流' navName2='流程定义列表'/>
<form id="listForm" action="list" method="get">
    <div class="page-head">
            <@pageHeadLeft>

            </@pageHeadLeft>
            <@pageHeadRight>
                <div class="search-item">
                    <label>流程名称:</label>
                    <input class="input-sm input-search" name="search_like_name" value="${searchParams['search_like_name']}"/>
                </div>
                <div class="search-item">
                    <label>状态:</label>
                    <select class="input-sm input-search" name="search_eq_status">
                        <option value="" selected>全部</option>
                        <option value="1" <#if searchParams['search_eq_status']=="1">selected</#if>>已激活</option>
                        <option value="0" <#if searchParams['search_eq_status']=="0">selected</#if>>已暂停</option>
                    </select>
                </div>
            </@pageHeadRight>
    </div>
    <div class="page-body">
        <table class="table table-bordered" id="listTable">
            <tr>
                <th>id</th>
                <th>流程名称</th>
                <th>部署id</th>
                <th>key</th>
                <th>XML</th>
                <th>图片</th>
                <th>版本</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
                <#list customSearch.pagedata.content as data>
                <tr>
                    <td>${data.id}</td>
                    <td>${data.name}</td>
                    <td>${data.deploymentId}</td>
                    <td>${data.key}</td>
                    <td>
                        <a href="bpmn?procDefId=${data.id}" target="_blank">${data.resourceName}</a>
                    </td>
                    <td>
                        <a href="img?procDefId=${data.id}" target="_blank">${data.diagramResourceName}</a>
                    </td>
                    <td>${data.version}</td>
                    <td>
                        <#if data.suspended>
                            <span  class="label label-warning">已暂停</span>
                        <#else>
                            <span  class="label label-success">已激活</span>
                        </#if>
                    </td>
                    <th>
                        <@shiro.hasPermission name="120000">
                        <#if data.suspended>
                            <button type="button" class="btn btn-xs btn-success" onclick="changeSuspended('${data.id}','1')">激活</button>
                        <#else>
                            <button type="button" class="btn btn-xs btn-warning" onclick="changeSuspended('${data.id}','0')">暂停</button>
                        </#if>
                        </@shiro.hasPermission>
                    </th>
                </tr>
                </#list>
        </table>
        <div class="row"><@pageingTemaplte customSearch.pagedata /></div>
    </div>
</form>

<script type="text/javascript">

    function changeSuspended(id,status) {
        layer.confirm("您确定"+(status=="1"?"激活":"暂停")+"流程？",{icon:3}, function (index) {
            ajaxPost("changeSuspended",{"id":id, "status":status},function (res) {
                window.location.reload();
            });
        });
    }

</script>
</body>
</html>