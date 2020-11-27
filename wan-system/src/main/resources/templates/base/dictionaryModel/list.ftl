<!DOCTYPE html>
<html class="ie9 no-focus">
<head>
    <meta charset="utf-8">
    <title>列表</title>
    <#include "/common/tmp/commom.ftl">
    <style>
        .orders-lable{
            display: inline-block;vertical-align: middle
        }
        .orders-lable a{
            top: 0px;
            display: block;
            font-size: 12px;
        }
    </style>
</head>
<body>
<@adminPageNav navName1='系统管理' navName2='字典管理'/>
    <div style="display: flex;height: 90%;">
        <form id="listForm" action="list" method="get" style="flex: 2;">
            <div class="page-head">
                <@pageHeadLeft>
                    <button type="button" class="btn btn-success" onclick="detail('')">新增</button>
                </@pageHeadLeft>
                <@pageHeadRight>
                    <div class="search-item">
                        <label>模块名称:</label>
                        <input class="input-sm input-search" name="search_like_modelName" value="${searchParams['search_like_modelName']}"/>
                    </div>
                    <div class="search-item">
                        <label>模块编码:</label>
                        <input class="input-sm input-search" name="search_like_modelCode" value="${searchParams['search_like_modelCode']}"/>
                    </div>
                </@pageHeadRight>
            </div>
            <div class="page-body">
                <table class="table table-bordered" id="listTable">
                    <tr>
                        <th>模块名称</th>
                        <th>模块编码</th>
                        <th>排序</th>
                        <th>操作</th>
                    </tr>
                    <#list customSearch.pagedata.content as data>
                    <tr>
                        <td>
                            <a href="javascript:void(0);" onclick="refulsh('${data.id}')">${data.modelName}</a>
                        </td>
                        <td>${data.modelCode}</td>
                        <td>${data.order}
                            <lable class="orders-lable">
                                <a href="javascript:void(0)" class="glyphicon glyphicon-chevron-up"></a>
                                <a href="javascript:void(0)" class="glyphicon glyphicon-chevron-down"></a>
                            </lable>
                        </td>
                        <td>
                            <button type="button" class="btn btn-primary btn-sm" onclick="detail('${data.id}')">编辑</button>
                        </td>
                    </tr>
                    </#list>
                </table>
                <div class="row"><@pageingTemaplte customSearch.pagedata /></div>
            </div>
        </form>

        <iframe id="dictFrame" style="flex: 3;" src="/base/dictionary/list">

        </iframe>
    </div>

    <script type="text/javascript">
        function refulsh(id) {
            $("#dictFrame").attr("src","/base/dictionary/list?modelId="+id)
        }
        
        function detail(id) {
            layer.open({
                type: 2,
                title: '新增字典模块',
                maxmin: true,
                area: ['500px', '600px'],
                content: 'detail?id='+id
            });
        }
    </script>
</body>
</html>