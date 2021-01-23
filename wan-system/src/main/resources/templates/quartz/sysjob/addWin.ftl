<!DOCTYPE html>
<html>
<head>
    <title></title>
    <#include "/common/tmp/commom.ftl">
<body>
<form id="dataForm">
    <div class="form-group">
        <input type="hidden" name="id" id="data_id" value="${data.id}"/>
        <div class="col-xs-12 col-md-4">
            <label for="jobName">任务名称<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="jobName" name="jobName" maxlength="200" value="${data.jobName}"
                   required/>
        </div>
        <div class="col-xs-12 col-md-4">
            <label for="jobGroup">任务组名<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="jobGroup" name="jobGroup" maxlength="200" value="${data.jobGroup}"
                   required/>
        </div>
        <div class="col-xs-12 col-md-4">
            <label for="invokeTarget">调用目标字符串<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="invokeTarget" name="invokeTarget" maxlength="200" value="${data.invokeTarget}"
                   required/>
        </div>
        <div class="col-xs-12 col-md-4">
            <label for="cronExpression">cron执行表达式<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="cronExpression" name="cronExpression" maxlength="200" value="${data.cronExpression}"
                   required/>
        </div>

        <div class="col-xs-12 col-md-4 ">
            <label for="misfirePolicy">cron计划策略<span class="text-danger">*</span></label>
            <#list LogicalEnumList as LogicalEnum>
            <div class="exp_column">
                <label class="css-input css-checkbox css-checkbox-primary">
                    <input type="radio" name="misfirePolicy" value="${LogicalEnum}" <#if data.misfirePolicy == LogicalEnum>checked</#if> /><span></span>
                </label>${LogicalEnum}
            </div>
            </#list>
        </div>

        <div class="col-xs-12 col-md-4 ">
            <label for="concurrent">是否允许并发执行<span class="text-danger">*</span></label>
            <#list ConcurrentEnumList as ConcurrentEnum>
            <div class="exp_column">
                <label class="css-input css-checkbox css-checkbox-primary">
                    <input type="radio" name="concurrent" value="${ConcurrentEnum}" <#if data.concurrent == ConcurrentEnum>checked</#if> /><span></span>
                </label>${ConcurrentEnum}
            </div>
            </#list>
        </div>

        <div class="col-xs-12 col-md-4 ">
            <label for="concurrent">任务状态<span class="text-danger">*</span></label>
            <#list StatusEnumList as StatusEnum>
            <div class="exp_column">
                <label class="css-input css-checkbox css-checkbox-primary">
                    <input type="radio" name="status" value="${StatusEnum}" <#if data.status == StatusEnum>checked</#if> /><span></span>
                </label>${StatusEnum}
            </div>
            </#list>
        </div>

        <div class="edit-win-foot">
            <button type="button" class="btn btn-success" onclick="save()">保存</button>
            <button type="button" class="btn btn-default" onclick="closeLayer()">关闭</button>
        </div>
    </div>
</form>
</body>
</head>
<script type="text/javascript">
    function closeLayer() {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }

    function save(){
        layer.load();
        var url = 'addData';
        var data = $('#dataForm').serialize();
        ajaxPost(url,data,function (res) {
            window.parent.location.reload();
            closeLayer();
        });
    }
</script>
</html>