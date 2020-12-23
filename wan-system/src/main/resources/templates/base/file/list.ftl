<!DOCTYPE html>
<html class="ie9 no-focus">
<head>
    <meta charset="utf-8">
    <title>文件管理列表</title>
    <#include "/common/tmp/commom.ftl">
</head>
<body>
<@adminPageNav navName1='文件管理' navName2='文件管理列表'/>
<form id="listForm" action="list" method="get">
    <div class="page-head">
        <@pageHeadLeft>
             <button type="button" onclick="openImportWin()" class="btn btn-sm btn-success hidden-xs">
                 <i class="fa fa-plus"></i>上传文件</button>

        </@pageHeadLeft>
        <@pageHeadRight>
            <div class="search-item">
                <label>请求方式:</label>
                <select class="input-sm input-search" name="search_eq_method">
                    <option value="" selected>全部</option>
                    <#list methodList as method>
                         <option value="${method}" <#if searchParams['search_eq_method']==method>selected </#if>>${method}</option>
                    </#list>
                </select>
            </div>
            <div class="search-item">
                <label>用户:</label>
                <input class="input-sm input-search" name="search_like_admin.realname" value=""/>
            </div>
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
                <th>源文件名</th>
                <th>文件路径</th>
                <th>文件大小</th>
                <th>上传者</th>
                <th>操作</th>
            </tr>
            <#list customSearch.pagedata.content as data>
            <tr>
                <td>
                    <label class="css-input css-checkbox css-checkbox-primary remove-padding remove-margin">
                        <input type="checkbox" name="ids" value="${data.id}"/><span></span>
                    </label>
                </td>
                <td>${data.original}</td>
                <td>${data.downloadPath}</td>
                <td>${data.fileSizeStr}</td>
                <td>${data.admin.realname}</td>
                <td>
                    <div class="btn-group">
                        <a href="file/${data.id}" target="_blank"
                           class="btn btn-xs btn-info" type="button"
                           data-toggle="tooltip" data-original-title="查看">查看</a>
                        <a href="downLoad?id=${data.id}" target="_blank"
                           class="btn btn-xs btn-primary" type="button"
                           data-toggle="tooltip" data-original-title="编辑">下载</a>
                        <a href="javascript:;"
                           onclick="remove(${data.id})"
                           class="btn btn-xs btn-danger" type="button"
                           data-toggle="tooltip" data-original-title="删除">删除</a>
                    </div>
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
            title: '新增文件管理',
            maxmin: true,
            area: ['500px', '600px'],
            content: 'addWin'
        });
    }

    function editWin() {
        layer.open({
            type: 2,
            title: '修改文件管理',
            maxmin: true,
            area: ['500px', '600px'],
            content: 'editWin?id='+id
        });
    }

    function remove(id) {
        ajaxDelete("del",{'id':id},"您确定删除?",function () {
            window.location.reload();
        })
    }

</script>
<div class="modal fade" id="importMessage" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-dialog-popin">
        <form id="putInExcel" class="form-horizontal push-10-t" method="post" enctype ="multipart/form-data"
              style="max-width: 400px;margin: 0 auto">
            <div class="modal-content">
                <div class="block block-themed block-transparent remove-margin-b">
                    <div class="block-header bg-info">
                        <ul class="block-options">
                            <li>
                                <button data-dismiss="modal" type="button"><i class="si si-close"></i></button>
                            </li>
                        </ul>
                        <h3 class="block-title">上传文件</h3>
                    </div>
                    <div class="block-content">
                        <div class="form-group">
                            <div class="col-xs-12 col-md-12">
                                <label for="filePath">选择文件</label>
                                <input type="file" name="file" class="form-control input-sm">
                            </div>
                        </div>
                    </div>
                </div>
                <input value="false" type="hidden" id="isUpload" name="isUpload">
                <div class="modal-footer">
                    <button class="btn btn-sm btn-default" type="button" data-dismiss="modal">关闭</button>
                    <button class="btn btn-sm btn-primary" name="11"  type="button" onclick="importData()">上传
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    //打开导入框
    function openImportWin() {
        $('#importMessage').modal('show');
    }
    function importData() {
        var form = new FormData(document.getElementById('putInExcel'));
        $.ajax({
            url: "upload",
            type: "post",
            data: form,
            processData: false,
            contentType: false,
            success: function (res) {
                layer.closeAll("loading");
                layer.msg(res.msg, {icon: res.icon, time:1000,});
                if(res.type =="SUCCESS" ){
                    setTimeout(function () {
                        window.location.reload();
                    }, 1000)
                }

            },
            error: function (XMLHttpRequest) {
                layer.closeAll("loading");
                alertErrorMessage(XMLHttpRequest);
            }
        })
    }
</script>
</body>
</html>