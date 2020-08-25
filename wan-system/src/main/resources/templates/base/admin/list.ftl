<!DOCTYPE html>
<!--[if IE 9]>         <html class="ie9 no-focus"> <![endif]-->
<!--[if gt IE 9]><!-->
<html class="no-focus"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <title>列表</title>
    <script type="text/javascript" src="/static/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/static/jquery/bootstrap.min.js"></script>
    <script type="text/javascript" src="/static/layui-v2.5.6/layui/layui.all.js"></script>
    <link href="/static/layui-v2.5.6/layui/css/layui.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="/static/bootstrap/bootstrap.min.css">

    <meta name="author" content="bianmaren">
    <meta name="robots" content="noindex, nofollow">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0">
    <style>
        html{
            background-color: #f5f5f5;
        }
        body{
            margin: 10px;
        }
        tr th,tr td{
            text-align: center;
        }
        .page-head{
            display: flex;
            padding: 5px 0px;
            margin-bottom: 10px;
        }
        .page-head,.page-body{
            border-radius: 5px;
            padding: 5px;
            background-color: #FFF;
            box-shadow: 0px 0px 5px #999;
        }
        .page-head-left{
            flex: 3;
        }
        .page-head-right{
            flex: 7;
        }
        .search-item{
            float: right;
        }
        .input-search{
            padding: 2px 5px;
            border: 1px solid #ccc;
            line-height: 26px;
            font-size: 14px;
        }
        .search-item label{
            font-family: serif;
            font-size: 16px;
        }
    </style>
</head>
<body>
    <form id="listForm" action="list" method="get">
        <div class="page-head">
            <div class="page-head-left">
                <a href="javascript:refulsh();" class="btn btn-sm btn-success hidden-xs">
                    <i class="fa fa-plus"></i>刷新</a>
            </div>
            <div class="page-head-right">
                <div class="search-item">
                    <label>请求:</label>
                    <input class="input-sm input-search" name=""/>
                </div>
            </div>
        </div>
        <div class="page-body">
            <table class="table table-bordered" id="listTable">
                <tr>
                    <th>账号</th>
                    <th>用户名</th>
                    <th>角色</th>
                    <th>操作</th>
                </tr>
                    <#list customSearch.pagedata.content as data>
                    <tr>
                        <td>${data.username}</td>
                        <td>${data.realname}</td>
                        <td>${data.roleListStr}</td>
                        <td>
                            <div class="btn-group">
                                <button type="button" onclick="detail(${data.id})" class="btn btn-xs btn-primary">
                                    详情
                                </button>
                                <button type="button" onclick="menuList(${data.id})" class="btn btn-xs btn-info">
                                    菜单列表
                                </button>
                                <button type="button" onclick="remove(${data.id})" class="btn btn-xs btn-danger">
                                    删除
                                </button>
                            </div>
                        </td>
                    </tr>
                    </#list>
            </table>
        </div>
    </form>
</body>
<script>
    function refulsh() {
        window.location.reload();
    }

    /**
     * 删除
     * @param id
     */
    function remove(id) {
        layer.confirm("您确定删除?",{icon:3}, function (index) {
            layer.load();
            $.ajax({
                url: 'del',
                type: 'post',
                data: {'id':id},
                async:false,
                success: function (res) {
                    layer.closeAll("loading");
                    layer.confirm(res.msg,{icon:res.icon}, function (index) {
                        if(res.type=='SUCCESS'){
                            layer.load();
                            window.location.reload();
                        }
                        layer.close(index);
                    });
                },
                error: function (XMLHttpRequest) {
                    layer.closeAll("loading");
                    alertErrorMessage(XMLHttpRequest);
                }
            });
            layer.close(index);
        })
    }

    function detail(id) {
        layer.open({
            type: 2,
            title: '用户详情',
            maxmin: true,
            area: ['500px', '600px'],
            content: 'detail?id='+id
        });
    }
    
    function menuList(id) {
        layer.open({
            type: 2,
            title: '用户菜单',
            maxmin: true,
            area: ['500px', '90%'],
            content: 'menuList?id='+id
        });
    }
</script>
</html>
