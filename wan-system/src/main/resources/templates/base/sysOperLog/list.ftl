<!DOCTYPE html>
<html class="ie9 no-focus">
<head>
    <meta charset="utf-8">
    <title>列表</title>
    <#include "/common/tmp/commom.ftl">
</head>
<body>
    <form id="listForm" action="list" method="get">
        <div class="page-head">
            <@pageHeadLeft>
                <a href="javascript:openImportWin();" class="btn btn-sm btn-success hidden-xs">
                    <i class="fa fa-plus"></i>从Excel文件导入音乐</a>
                <a href="javascript:refulsh();" class="btn btn-sm btn-success hidden-xs">
                    <i class="fa fa-plus"></i>刷新</a>
            </@pageHeadLeft>
            <@pageHeadRight>
                <div class="search-item">
                    <label>请求方式:</label>
                    <select class="input-sm input-search" name="search_eq_method">
                        <option value="" selected>全部</option>
                        <option value="GET" <#if searchParams['search_eq_method']=='GET'>selected</#if>>GET</option>
                        <option value="POST" <#if searchParams['search_eq_method']=='POST'>selected</#if>>POST</option>
                        <option value="DELETE" <#if searchParams['search_eq_method']=='DELETE'>selected</#if>>DELETE</option>
                    </select>
                </div>
                <div class="search-item">
                    <label>用户:</label>
                    <input class="input-sm input-search" name="search_like_admin.realname" value="${searchParams['search_like_admin.realname']}"/>
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


            </@pageHeadRight>
        </div>
        <div class="page-body">
            <table class="table table-bordered" id="listTable">
                <tr>
                    <th>id</th>
                    <th>用户</th>
                    <th>IP</th>
                    <th>请求地址</th>
                    <th>操作类型</th>
                    <th>请求方式</th>
                    <th>是否成功</th>
                </tr>
                <#list customSearch.pagedata.content as data>
                <tr>
                    <td>${data.id}</td>
                    <td>${data.admin.realname}</td>
                    <td>${data.operIp}</td>
                    <td>${data.operUrl}</td>
                    <td>${data.operateType}</td>
                    <td>${data.method}</td>
                    <td>${data.statusHtml}</td>
                </tr>
                </#list>
            </table>
            <div class="row"><@pageingTemaplte customSearch.pagedata /></div>
        </div>
    </form>

    <script type="text/javascript">
        function refulsh() {
            window.location.reload();
        }
        

    </script>

    <#--文件导入模态框-->
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
                            <h3 class="block-title">Excel数据导入</h3>
                        </div>
                        <div class="block-content">
                            <div class="form-group">
                                <div class="col-xs-12 col-md-12">
                                    <label for="filePath">选择文件</label>
                                    <input id="file" type="file" name="file" class="form-control input-sm">
                                </div>
                                <div class="col-xs-12 col-md-12 " id="message_detil" style="display: none;">
                                    <div style="border: 1px solid #faccc6;color: #e4393c;background-color: #ffebeb;padding: 5px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <input value="false" type="hidden" id="isUpload" name="isUpload">
                    <div class="modal-footer">
                        <a style="margin-right: 80px;" href='downLoadExcel.html' target="_blank" title="点击下载模板"><i class="glyphicon glyphicon-save" data-toggle="tooltip" data-original-title="下载Excel模板"></i>下载Excel模板</a>
                        <button class="btn btn-sm btn-default" type="button" data-dismiss="modal">关闭</button>
                        <button class="btn btn-sm btn-success" type="button" onclick="importData(false)">检查</button>
                        <button class="btn btn-sm btn-primary" name="11"  type="button" onclick="importData(true)">上传
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

        $('#importMessage').on('hide.bs.modal', function () {
            if (isSuccess){
                layer.confirm("数据已更新，是否刷新页面？",{icon:1}, function (index) {
                    layer.load();
                    window.location.reload();
                    layer.close(index);
                });
            }
        });

        //是否检查过
        var isReady = false;
        //检查是否通过
        var  isWorn = false;
        //是否导入成功
        var isSuccess = false;
        $("#file").change(function () {
            isReady = false;
            isWorn = false;
            $("#message_detil div").html('');
            $("#message_detil").hide();
        })
        function importData(isUpload) {
            layer.load();
            $("#isUpload").val(isUpload);
            console.log("isUpload :"+isUpload);
            if ($(":file[name=file]").val() == "") {
                alert("请选择文件");
                layer.closeAll("loading");
                return;
            }
            if(!isReady && isUpload){
                layer.confirm("请先进行数据检查！再上传！",{icon:0}, function (index) {
                    layer.close(index);
                    return;
                });
                layer.closeAll("loading");
                return;
            }
            if(!isWorn && isUpload){
                layer.confirm("检查出错！，是否继续上传？",{icon:0},function (index) {
                    isWorn = true;
                    layer.close(index);
                });
            }
            if(!isWorn && isUpload){
                layer.closeAll("loading");
                return;
            }
            var form = new FormData(document.getElementById('putInExcel'));
            $.ajax({
                url: "importData",
                type: "post",
                data: form,
                processData: false,
                contentType: false,
                success: function (data) {
                    layer.closeAll("loading");
                    isReady = true;
                    if(data.content.indexOf("检查无异常")>0){
                        isWorn = true;
                    }
                    var a = data.content.substring(data.content.indexOf("<h5>成功")+7,data.content.indexOf("条。失败:"));
                    if(a>0){
                        isSuccess = true;
                    }
                    showImportMessage(isUpload,data.content);

                },
                error: function (XMLHttpRequest) {
                    layer.closeAll("loading");
                    alertErrorMessage(XMLHttpRequest);
                }
            })
        }
        function showImportMessage(isUpload,message) {
            if(isUpload){
                layer.open({
                    type: 1,
                    title: '',
                    shadeClose: true,
                    shade: 0.8,
                    anim: 2,
                    area: ['40%', '50%'],
                    btn: ['关闭'],
                    content: '<div style="text-align: center;line-height: 32px;padding: 20px;">' + message + '</div>',
                    end: function (index) {
                        if (isSuccess){
                            layer.confirm("数据已更新，是否刷新页面？",{icon:1}, function (index) {
                                layer.load();
                                window.location.reload();
                                layer.close(index);
                            });
                        }
                    }
                });
            }else {
                $("#message_detil div").html(message);
                $("#message_detil").show();
            }

        }
    </script>
</body>
</html>