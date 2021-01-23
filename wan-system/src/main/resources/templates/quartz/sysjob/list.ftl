<!DOCTYPE html>
<html class="ie9 no-focus">
<head>
    <meta charset="utf-8">
    <title>定时任务列表</title>
    <#include "/common/tmp/commom.ftl">
</head>
<body>
<@adminPageNav navName1='定时任务' navName2='定时任务列表'/>
<form id="listForm" action="list" method="get">
    <div class="page-head">
        <@pageHeadLeft>
            <@shiro.hasPermission name="101000">
            <button type="button" class="btn btn-success btn-sm" onclick="addWin()">新增</button>
            </@shiro.hasPermission>
        </@pageHeadLeft>
        <@pageHeadRight>

            <div class="search-item">
                <label>任务名称:</label>
                <input class="input-sm input-search" name="search_like_jobName" value="${searchParams['search_like_jobName']}"/>
            </div>
            <div class="search-item">
                <label>任务组名称:</label>
                <input class="input-sm input-search" name="search_like_jobGroup" value="${searchParams['search_like_jobGroup']}"/>
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
                <th>任务名称</th>
                <th>任务组名</th>
                <th>调用目标字符串</th>
                <th>cron执行表达式</th>
                <th>cron计划策略</th>
                <th>是否并发执行</th>
                <th>任务状态</th>
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
                <td>${data.jobName}</td>
                <td>${data.jobGroup}</td>
                <td>${data.invokeTarget}</td>
                <td>${data.cronExpression}</td>
                <td>${data.misfirePolicy}</td>
                <td>${data.concurrent}</td>
                <td>${data.statusHtml}</td>
                <td>
                    <div class="btn-group">
                        <a href="javascript:;"
                           onclick="editWin(${data.id})"
                           class="btn btn-xs btn-primary" type="button"
                           data-toggle="tooltip" data-original-title="编辑">编辑</a>

                        <#if data.status=="停用">
                        <@shiro.hasPermission name="101002">
                        <a href="javascript:;"
                           onclick="changeStatus(${data.id})"
                           class="btn btn-xs btn-success" type="button"
                           data-toggle="tooltip" data-original-title="启用">启用</a>
                         </@shiro.hasPermission>
                        </#if>
                        <#if data.status=="启用">
                        <@shiro.hasPermission name="101003">
                        <a href="javascript:;"
                           onclick="changeStatus(${data.id})"
                           class="btn btn-xs btn-danger" type="button"
                           data-toggle="tooltip" data-original-title="暂停">暂停</a>
                        </@shiro.hasPermission>
                        </#if>
                        <a href="javascript:;"
                           onclick="run(${data.id})"
                           class="btn btn-xs btn-success" type="button"
                           data-toggle="tooltip" data-original-title="触发">触发</a>
                        <a href="javascript:;"
                           onclick="joblog(${data.id})"
                           class="btn btn-xs btn-info" type="button"
                           data-toggle="tooltip" data-original-title="调度日志">调度日志</a>
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
            title: '新增定时任务',
            maxmin: true,
            area: ['500px', '600px'],
            content: 'addWin'
        });
    }

    function editWin(id) {
        layer.open({
            type: 2,
            title: '修改定时任务',
            maxmin: true,
            area: ['500px', '600px'],
            content: 'editWin?id='+id
        });
    }


    function joblog(id) {
        layer.open({
            type: 2,
            title: '定时任务日志',
            maxmin: true,
            area: ['80%', '90%'],
            content: '/quartz/joblog/list?search_eq_sysJob.id='+id
        });
    }

    function run(id) {
        ajaxPost("run",{"id":id});
    }


    function changeStatus(id) {
        ajaxPost("changeStatus",{'id':id},function () {
            window.location.reload();
        })
    }

</script>
</body>
</html>