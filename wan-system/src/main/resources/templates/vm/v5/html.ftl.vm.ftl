<#noparse><#include '/admin_tmpl/include/template.ftl' /></#noparse>
<!DOCTYPE html>
<html>

<#noparse><@pageHead></#noparse>

<#noparse></@pageHead></#noparse>

<body class="fixed-left">

<!-- Begin page -->
<div id="wrapper">

    <div class="container">

        <div class="panel panel-default">
            <div class="panel-body" style="padding-top: 0px">
            <div class="pageConsoleBox" style="background: #fff;padding-top: 10px">
             <#noparse><@listpageHead isShowDeleteBtn=false iframeWidth='60%' iframeHeight="90%"></#noparse>
                <input type="text" class="form-control input-sm" name="search_like_name"
                       placeholder="姓名">
            <#noparse></@listpageHead></#noparse>
            </div>
            <div class="listBox">
                <div class="table-responsive">
                    <table id="listTable" lay-filter="list"></table>
                </div>
            </div>
        </div>
    </div>

</div> <!-- container -->

</div>
</body>
<!-- END wrapper -->

<#noparse><@pageFoot></#noparse>
<#noparse></@pageFoot></#noparse>


<#noparse><@pageList></#noparse>

    <script type="text/html" id="editTpl">
        <span class="btn btn-xs btn-info btn-custom" lay-event="edit"><i class="fa fa-edit"></i> 编辑</span>
        <span class="btn btn-xs btn-danger btn-custom" lay-event="del"><i class="fa fa-trash-o"></i> 删除</span>
    </script>

    <script type="application/javascript">

        var table;
        /** 表头 */
        var cols = [[
            {title: '选择', width: 80, fixed: 'left', type: 'checkbox'}
            , {field: 'name', title: '姓名'}
            , {field: 'gridName', title: '区域'}
            , {field: 'nickIdCard', title: '身份证号码'}
            , {field: 'infectionRoute', title: '感染途径'}
            , {field: 'haveCrime', title: '是否有违法犯罪史'}
            , {field: 'crimeStatus', title: '违法犯罪情况'}
            , {field: 'focusContent', title: '关注内容'}
            , {field: 'helpedStatus', title: '帮扶情况'}
            , {field: 'cureStatus', title: '收治情况'}
            , {title: '操作', templet: '#editTpl'}
        ]];

        /**
         * 监听工具条事件
         * @param obj
         */
        function dealToolEvent(obj) {
            var data = obj.data;
            var layEvent = obj.event;
            var id = data.id;

            if ('del' === layEvent) {
                layer.confirm("您确定删除此条记录?", {icon: 3}, function (index) {
                    layer.load();
                    $.ajax({
                        url: 'delete.do',
                        type: 'post',
                        data: {'id': id},
                        async: false,
                        success: function (res) {
                            layer.closeAll("loading");
                            if (res.type == 'success') {
                                layer.msg(res.content, {icon: 1, time: 1000}, function () {
                                    reloadPageData()
                                });
                            } else {
                                layer.confirm(res.content, {icon: 2}, function (index) {
                                    layer.close(index);
                                });
                            }
                        },
                        error: function (XMLHttpRequest) {
                            layer.closeAll("loading");
                            console.log(XMLHttpRequest);
                        }
                    });
                    layer.close(index);
                })
            } else if ('edit' === layEvent) {
                openPage(this, "编辑", "edit?id=" + id, "60%", "90%");
            }
        }


    </script>

<#noparse></@pageList></#noparse>

</body>
</html>