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
<@adminPageNav navName1='字典管理' navName2='${dictModel.modelName}字典'/>
    <form id="listForm" action="list" method="get">
        <div class="page-head">
            <@pageHeadLeft>
                <@shiro.hasPermission name="100502">
                 <button type="button" class="btn btn-success" onclick="detail('')">新增</button>
                </@shiro.hasPermission>
            </@pageHeadLeft>
            <@pageHeadRight>

                <div class="search-item">
                    <label>字典值:</label>
                    <input type="hidden" name="modelId" value="${dictModel.id}">
                    <input class="input-sm input-search" name="search_like_value" value="${searchParams['search_like_value']}"/>
                </div>

                <div class="search-item">
                    <label>字典code:</label>
                    <input class="input-sm input-search" name="search_like_code" value="${searchParams['search_like_code']}"/>
                </div>

            </@pageHeadRight>
        </div>
        <div class="page-body">
            <table class="table table-bordered" id="listTable">
                <tr>
                    <th>字典模块</th>
                    <th>字典值</th>
                    <th>字典code</th>
                    <th>排序</th>
                    <th>启用状态</th>
                    <th>操作</th>
                </tr>
                <#list customSearch.pagedata.content as data>
                <tr>
                    <td>${data.dictModel.modelName}</td>
                    <td>${data.value}</td>
                    <td>${data.code}</td>
                    <td>${data.order}
                        <lable class="orders-lable">
                            <a href="javascript:void(0)" onclick="upOrDown('${data.id}','1')" class="glyphicon glyphicon-chevron-up"></a>
                            <a href="javascript:void(0)" onclick="upOrDown('${data.id}','-1')" class="glyphicon glyphicon-chevron-down"></a>
                        </lable>
                    </td>
                    <td>${data.statusHtml}</td>
                    <td>
                    <@shiro.hasPermission name="100503">
                        <button type="button" onclick="detail('${data.id}')" class="btn btn-primary btn-sm">修改</button>
                    </@shiro.hasPermission>
                    </td>
                </tr>
                </#list>
            </table>
            <div class="row"><@pageingTemaplte customSearch.pagedata /></div>
        </div>
    </form>

    <script type="text/javascript">
        function refulsh() {
            layer.load();
            window.location.reload();
        }


        function detail(id) {
            layer.open({
                type: 2,
                title: '编辑字典',
                maxmin: true,
                area: ['500px', '600px'],
                content: 'detail?modelId=${dictModel.id}&id='+id
            });
        }
        
        function upOrDown(id,change) {
            ajaxSend("upOrDown","POST",{"id":id, "change":change},function (res) {
                window.location.reload();
            },function (res) {
                layer.msg(res.msg,{icon:0})
            })
        }
    </script>
</body>
</html>