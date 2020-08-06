<#noparse><#include '/admin_tmpl/include/template.ftl' /></#noparse>
<!DOCTYPE html>
<html>

<#noparse><@pageHead></#noparse>
    <style type="text/css">
        .container {
            padding: 20px;
        }

        .checkbox {
            width: 180px;
        }
    </style>
<#noparse></@pageHead></#noparse>

<body class="fixed-left">

<!-- Begin page -->
<div id="wrapper">

    <div class="container">
        <form id="dataForm">
            <div class="form-group">
                <input type="hidden" name="id" value="<#noparse>${data.id}</#noparse>">
                <div class="col-xs-6">
                    <label for="column1">字段1<span class="text-danger">*</span></label>
                    <select class="form-control" id="column1" name="column1">
                        <option disabled selected hidden value="">请选择</option>
                        <option value="">aaa</option>
                        <option value="">bbb</option>
                    </select>
                </div>
                <div class="col-xs-6">
                    <label for="column2">输入2<span class="text-danger">*</span></label>
                    <input class="form-control" type="text" id="column2" name="column2">
                </div>
                <div class="col-xs-12">
                    <label for="column4">输入3<span class="text-danger">*</span></label>
                    <textarea style="width: 100%;height: 150px;resize: none" class="form-control" id="column3"
                              name="column3"></textarea>
                </div>
            </div>
        </form>

    </div> <!-- container -->

    <div class="bottomConsoleBox">
        <span class="btn btn-info" onclick="submitData()">保存</span>
    </div>

</div>
<!-- END wrapper -->

<#noparse><@pageFoot></#noparse>

<#noparse></@pageFoot></#noparse>

<script>
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
                        layer.load();
                        closeLayer();
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
