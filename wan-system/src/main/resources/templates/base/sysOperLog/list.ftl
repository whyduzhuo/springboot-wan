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
            <@shiro.hasPermission name="100100">
            <button type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#exportModel">
                数据导出
            </button>
            </@shiro.hasPermission>
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
                    <th>OS</th>
                    <th>浏览器</th>
                    <th>操作</th>
                    <th style="width: 15%">请求地址</th>
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
                    <td>${data.os}</td>
                    <td>${data.browser}</td>
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


<div class="modal fade" id="exportModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form class="modal-content" action="exportData" method="post" target="_blank" style="border-radius: 10px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    导出选项
                </h4>
            </div>
            <div class="modal-body">
                <h4 style="display: block">请选择导出范围</h4>
                <div  style="display: inline-block;width: 30%">
                    <label>请求方式:</label>
                    <select class="input-sm input-search form-control" name="search_eq_method">
                        <option value="" selected>全部</option>
                        <#list methodList as method>
                             <option value="${method}" <#if searchParams['search_eq_method']==method>selected</#if>>${method}</option>
                        </#list>
                    </select>
                </div>
                <div style="display: inline-block;width: 30%">
                    <label>是否成功:</label>
                    <select class="input-sm input-search form-control" name="exp_eq_status">
                        <option value="" selected>全部</option>
                        <#list yesOrNotList as yesOrNo>
                            <option value="${yesOrNo}" <#if searchParams['search_eq_status']==yesOrNo>selected</#if>>${yesOrNo}</option>
                        </#list>
                    </select>
                </div>
                <div style="display: inline-block;width: 30%">
                    <label>是否报错:</label>
                    <select class="input-sm input-search form-control" name="exp_eq_haveException">
                        <option value="" selected>全部</option>
                        <#list yesOrNotList as yesOrNo>
                            <option value="${yesOrNo}" <#if searchParams['search_eq_haveException']==yesOrNo>selected</#if>>${yesOrNo}</option>
                        </#list>
                    </select>
                </div>
                <div  style="display: inline-block;width: 30%">
                    <label>用户:</label>
                    <input class="input-sm input-search form-control" name="exp_like_admin.realname" value="${searchParams['search_like_admin.realname']}"/>
                </div>
                <div style="display: inline-block;width: 30%">
                    <label>请求地址:</label>
                    <input class="input-sm input-search form-control" name="exp_like_operUrl" value="${searchParams['search_like_operUrl']}"/>
                </div>
                <div style="display: inline-block;width: 30%">
                    <label>操作:</label>
                    <input class="input-sm input-search form-control" name="exp_like_title" value="${searchParams['search_like_title']}"/>
                </div>
                <br/>

                <h4 style="display: inline-block;margin-top: 20px">请选择导出字段</h4>
                <label class="css-input css-checkbox css-checkbox-primary">
                    <input type="checkbox" value="1" checked class="quanxuan"/><span></span>全选
                </label>
                <div>
                    <div class="exp_column">
                        <label class="css-input css-checkbox css-checkbox-primary">
                            <input type="checkbox" checked name="fields" value="id"/><span></span>id
                        </label>
                    </div>

                    <div class="exp_column">
                        <label class="css-input css-checkbox css-checkbox-primary">
                            <input type="checkbox" checked name="fields" value="时间"/><span></span>时间
                        </label>
                    </div>

                    <div class="exp_column">
                        <label class="css-input css-checkbox css-checkbox-primary">
                            <input type="checkbox" checked name="fields" value="用户"/><span></span>用户
                        </label>
                    </div>

                    <div class="exp_column">
                        <label class="css-input css-checkbox css-checkbox-primary">
                            <input type="checkbox" checked name="fields" value="IP"/><span></span>IP
                        </label>
                    </div>

                    <div class="exp_column">
                        <label class="css-input css-checkbox css-checkbox-primary">
                            <input type="checkbox" checked name="fields" value="操作"/><span></span>操作
                        </label>
                    </div>
                    <div class="exp_column">
                        <label class="css-input css-checkbox css-checkbox-primary">
                            <input type="checkbox" checked name="fields" value="请求地址"/><span></span>请求地址
                        </label>
                    </div>

                    <div class="exp_column">
                        <label class="css-input css-checkbox css-checkbox-primary">
                            <input type="checkbox" checked name="fields" value="操作类型"/><span></span>操作类型
                        </label>
                    </div>

                    <div class="exp_column">
                        <label class="css-input css-checkbox css-checkbox-primary">
                            <input type="checkbox" checked name="fields" value="请求方式"/><span></span>请求方式
                        </label>
                    </div>

                    <div class="exp_column">
                        <label class="css-input css-checkbox css-checkbox-primary">
                            <input type="checkbox" checked name="fields" value="是否成功"/><span></span>是否成功
                        </label>
                    </div>

                    <div class="exp_column">
                        <label class="css-input css-checkbox css-checkbox-primary">
                            <input type="checkbox" checked name="fields" value="是否报错"/><span></span>是否报错
                        </label>
                    </div>

                    <div class="exp_column">
                        <label class="css-input css-checkbox css-checkbox-primary">
                            <input type="checkbox" checked name="fields" value="请求参数"/><span></span>请求参数
                        </label>
                    </div>

                    <div class="exp_column">
                        <label class="css-input css-checkbox css-checkbox-primary">
                            <input type="checkbox" checked name="fields" value="异常信息"/><span></span>异常信息
                        </label>
                    </div>

                    <div class="exp_column">
                        <label class="css-input css-checkbox css-checkbox-primary">
                            <input type="checkbox" checked name="fields" value="响应结果"/><span></span>响应结果
                        </label>
                    </div>

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="submit" class="btn btn-primary">
                    导出excel
                </button>
            </div>
        </form><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</html>