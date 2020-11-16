<!DOCTYPE html>
<html class="ie9 no-focus">
<head>
    <meta charset="utf-8">
    <title>列表</title>
    <#include "/common/tmp/commom.ftl">
</head>
<body>
<@adminPageNav navName1='系统管理' navName2='日志列表'/>
    <form id="listForm" action="list" method="get">
        <div class="page-head">
            <@pageHeadLeft>
                <a href="javascript:openImportWin();" class="btn btn-sm btn-success hidden-xs" data-toggle="tooltip" data-original-title="编辑">
                    <i class="fa fa-plus"></i>从Excel文件导入音乐</a>
            </@pageHeadLeft>
            <@pageHeadRight>
                <div class="search-item">
                    <label>请求方式:</label>
                    <select class="input-sm input-search" name="search_eq_method">
                        <option value="" selected>全部</option>
                        <#list methodList as method>
                             <option value="${method}" <#if searchParams['search_eq_method']==method>selected</#if>>${method}</option>
                        </#list>
                    </select>
                </div>
                <div class="search-item">
                    <label>用户:</label>
                    <input class="input-sm input-search" name="search_like_admin.realname" value="${searchParams['search_like_admin.realname']}"/>
                </div>
                <div class="search-item">
                    <label>请求地址:</label>
                    <input class="input-sm input-search" name="search_like_operUrl" value="${searchParams['search_like_operUrl']}"/>
                </div>
                <div class="search-item">
                    <label>操作:</label>
                    <input class="input-sm input-search" name="search_like_title" value="${searchParams['search_like_title']}"/>
                </div>
                <div class="search-item">
                    <label>是否成功:</label>
                    <select class="input-sm input-search" name="search_eq_status">
                        <option value="" selected>全部</option>
                        <#list yesOrNotList as yesOrNo>
                            <option value="${yesOrNo}" <#if searchParams['search_eq_status']==yesOrNo>selected</#if>>${yesOrNo}</option>
                        </#list>
                    </select>
                </div>
                <div class="search-item">
                    <label>是否报错:</label>
                    <select class="input-sm input-search" name="search_eq_haveException">
                        <option value="" selected>全部</option>
                        <#list yesOrNotList as yesOrNo>
                            <option value="${yesOrNo}" <#if searchParams['search_eq_haveException']==yesOrNo>selected</#if>>${yesOrNo}</option>
                        </#list>
                    </select>
                </div>


            </@pageHeadRight>
        </div>
        <div class="page-body">
            <table class="table table-bordered" id="listTable">
                <tr>
                    <th>id</th>
                    <th>用户</th>
                    <th>IP</th>
                    <th style="width: 15%">请求地址</th>
                    <th>操作</th>
                    <th>操作类型</th>
                    <th>请求方式</th>
                    <th>是否成功</th>
                    <td>是否报错</td>
                    <td>请求参数</td>
                    <td>异常信息</td>
                    <td>响应结果</td>
                </tr>
                <#list customSearch.pagedata.content as data>
                <tr>
                    <td>${data.id}</td>
                    <td>${data.admin.realname}</td>
                    <td>${data.operIp}</td>
                    <td>${data.title}</td>
                    <td>${data.operUrl}</td>
                    <td>${data.operateType}</td>
                    <td>${data.method}</td>
                    <td>${data.statusHtml}</td>
                    <td>${data.haveExceptionHtml}</td>
                    <td>
                    <#if data.operParm?length gt 20>
                        <a href="javascript:;" onclick="showOperParm('${data.id}')">${data.operParm?substring(0,20)}...</a>
                    <#else>
                        ${data.operParm}
                    </#if>
                    </td>
                    <td>
                    <#if data.errorMsg?length gt 20>
                        <a href="javascript:;" onclick="showOperParm('${data.id}')">${data.errorMsg?substring(0,20)}...</a>
                    <#else>
                        ${data.errorMsg}
                    </#if>
                    </td>
                    <td>
                    <#if data.jsonResult?length gt 20>
                        <a href="javascript:;" onclick="showOperParm('${data.id}')">${data.jsonResult?substring(0,20)}...</a>
                    <#else>
                        ${data.jsonResult}
                    </#if>

                        <a href="edit.html?id=${data.id}" class="btn btn-xs btn-default" type="button"
                           data-toggle="tooltip" data-original-title="编辑"><i class="fa fa-pencil"></i></a>
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
        function showOperParm(id) {
            var url = 'find?id='+id;
            var data = "";
            ajaxSend(url,"GET",data,function (res) {
                var operParm = res.data.operParm;
                var errorMsg = res.data.errorMsg;
                var jsonResult=res.data.jsonResult;
                layer.open({
                    type: 1,
                    title: '请求参数',
                    maxmin: true,
                    area: ['70%', '70%'],
                    content: "<div class=\"alert alert-info alert-dismissable\">请求参数："+operParm+"</div><div class=\"alert alert-danger alert-dismissable\">错误信息："+errorMsg+"</div><div class=\"alert alert-success  alert-dismissable\">返回值："+jsonResult+"</div>"
                });
            });
        }
    </script>
</body>
</html>