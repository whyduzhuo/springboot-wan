<#noparse><#include '/admin_tmpl/include/template.ftl' /></#noparse>
<!DOCTYPE html>
<html>

<#noparse><@pageHead></#noparse>
<#noparse></@pageHead></#noparse>

<body class="fixed-left">

<!-- Begin page -->
<div id="wrapper">

    <div class="container">
        <form id="dataForm" class="form-horizontal">
            <div class="form-group">
                <input type="hidden" name="id" value="<#noparse>${data.id}</#noparse>">
                <div class="col-xs-6">
                    <label for="column1">字段1<span class="text-danger">*</span></label>
                    <select class="form-control" id="column1" name="column1">
                        <option disabled selected hidden value="">请选择</option>
                        <#noparse><#list yesOrNoList as yesOrNo></#noparse>
                        <option value="<#noparse>${yesOrNo}</#noparse>" <#noparse><#if data.yesOrNo==yesOrNo></#noparse>selected<#noparse></#if></#noparse>><#noparse>${yesOrNo}</#noparse></option>
                        <#noparse></#list></#noparse>
                    </select>
                </div>
                <div class="col-xs-6">
                    <label for="column2">输入2<span class="text-danger">*</span></label>
                    <input class="form-control" type="text" id="column2" name="column2" value="<#noparse>${data.column2}</#noparse>">
                </div>
                <div class="col-xs-12">
                    <label for="column4">输入3<span class="text-danger">*</span></label>
                    <textarea style="width: 100%;height: 150px;resize: none" class="form-control" id="column3"
                                                           name="column3"><#noparse>${data.column3}</#noparse></textarea>
                </div>
            </div>
        </form>

    </div> <!-- container -->

    <div style="height: 80px"></div>
    <div class="bottomConsoleBox">
        <span class="btn btn-info" onclick="submitData()">保存</span>
    </div>

</div>
<!-- END wrapper -->

<#noparse><@pageFoot></#noparse>

<#noparse></@pageFoot></#noparse>

<script>

    layui.use('laydate', function(){
        var laydate = layui.laydate;
        laydate.render({
            elem: '#pxStartDate'
        });
    });

    function closeLayer() {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }


    function submitData() {
        layer.load();
        $.ajax({
            url: "save",
            type: "post",
            data: $('#dataForm').serialize(),
            success: function (res) {
                layer.closeAll("loading");
                var i = 7;
                switch (res.type) {
                    case 'success':
                        i = 1;
                        break;
                    case 'warn':
                        i = 0;
                        break;
                    case 'error':
                        i = 2;
                        break;
                }
                layer.confirm(res.content, {icon: i}, function (index) {
                    if (res.type == 'success') {
                        closeLayer()
                        parent.$(".reloadPageDataBtn").click();
                    }
                    layer.close(index);
                });
            },
            error: function (XMLHttpRequest) {
                alert("请求错误！");
                console.log(XMLHttpRequest);
            }
        });
    }
</script>
</body>
</html>
