<!DOCTYPE html>
<html class="ie9 no-focus">
<head>
    <meta charset="utf-8">
    <title>定时任务列表</title>
    <#include "/common/tmp/commom.ftl">
</head>
<body>
<@adminPageNav navName1='定时任务' navName2='定时任务日志'/>
<form id="listForm" action="list" method="get">
    <div class="page-head">
        <@pageHeadRight>

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
                <th>日志信息</th>
                <th>执行状态</th>
                <th>异常信息</th>
                <th>开始时间</th>
                <th>结束时间</th>
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
                <td>${data.jobMessage}</td>
                <td>${data.statusHtml}</td>
                <td>${data.exceptionInfo}</td>
                <td>${data.startTime?string('yyyy/MM/dd HH:mm:ss:SSS')}</td>
                <td>${data.endTime?string('yyyy/MM/dd HH:mm:ss:SSS')}</div>
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

    function changeStatus(id) {
        ajaxPost("changeStatus",{'id':id},function () {
            window.location.reload();
        })
    }

</script>
</body>
</html>