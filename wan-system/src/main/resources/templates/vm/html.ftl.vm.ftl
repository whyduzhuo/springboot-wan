<!DOCTYPE html>
<!--[if IE 9]>         <html class="ie9 no-focus"> <![endif]-->
<!--[if gt IE 9]><!-->
<html class="no-focus" xmlns="http://www.w3.org/1999/html"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <title>${data.module}列表</title>
    <meta name="author" content="bianmaren">
    <meta name="robots" content="noindex, nofollow">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0">
    <#noparse>[#include "/common/tzz/common.ftl" /]</#noparse>
    <link rel="stylesheet" href="<#noparse>${base}</#noparse>/assets/js/plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" href="<#noparse>${base}</#noparse>/assets/js/plugins/bootstarp-switch/css/bootstrap-switch.css">
    <script type="text/javascript" src="<#noparse>${base}</#noparse>/assets/js/plugins/bootstarp-switch/bootstrap-switch.min.js"></script>
    <script type="text/javascript" src="<#noparse>${base}</#noparse>/assets/js/plugins/bootstrap-datetimepicker/moment.min.js"></script>
    <script type="text/javascript" src="<#noparse>${base}</#noparse>/assets/js/plugins/bootstrap-datetimepicker/locale/zh-cn.js"></script>
    <script type="text/javascript"
            src="<#noparse>${base}</#noparse>/assets/js/plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.js"></script>
</head>
<style>
    td, th {
        text-align: center !important;
        vertical-align: middle !important;
    }
</style>
<body>
    <#noparse>[@adminPageNav navName1='</#noparse>${data.module}<#noparse>' navName2='</#noparse>${data.module}<#noparse>列表' isListPage=true /]</#noparse>

<div class="block">
    <form id="listForm" action="list.html" method="get">
        <div class="block-header form-inline" style="position: relative;">
            <input type="hidden" name="pageInit" id="pageInit" value="">
            <#noparse>[@listPageHeadLeft isShowDeleteBtn=false isShowAddBtn=false]</#noparse>
            <a href="javascript:openAddWin();" class="btn btn-sm btn-success">
                <i class="fa fa-plus"></i>新增</a>
            <a href="javascript:openImportWin();" class="btn btn-sm btn-success hidden-xs">
                <i class="fa fa-plus"></i>从Excel文件导入${data.module}</a>
            <a href="javascript:;" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#exportModel">
                数据导出
            </a>
            <a href="javascript:;" class="btn btn-sm btn-success" onclick="batchApproveWin()">批量审批</a>
            <#noparse>[/@listPageHeadLeft]</#noparse>
            <#noparse>[@listPageHeadRight true]</#noparse>
            <select class="form-control input-sm" name="search_eq_batch">
                <option value="" selected disabled hidden>请选择</option>
                <option value="">测试1</option>
                <option value="">测试2</option>
            </select>
            <#noparse>[/@listPageHeadRight]</#noparse>
        </div><!-- /.block-header -->
        <div class="block-content table-responsive remove-padding-t">
            <div class="alert alert-warning alert-dismissable">
                <button type="button" class="close" data-dismiss="alert"
                        aria-hidden="true">
                    &times;
                </button>疫情期间，请勿装x
            </div>
            <table class="table table-bordered" id="listTable">
                <thead>
                <tr>
                    <th class="remove-padding-t remove-padding-b">
                        <label class="css-input css-checkbox css-checkbox-primary">
                            <input type="checkbox" id="selectAll"><span></span>
                        </label>
                    </th>
                    <th>序号</th>
                    <th>字段1</th>
                    <th>字段2</th>
                    <th>字段3</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#noparse>[#if customSearch.pagedata.content!]</#noparse>
                <#noparse>[#list  customSearch.pagedata.content as data]</#noparse>
                <tr>
                    <td>
                        <label class="css-input css-checkbox css-checkbox-primary remove-padding remove-margin">
                            <input type="checkbox" name="ids" value="<#noparse>${data.id}</#noparse>"/><span></span>
                        </label>
                    </td>
                    <td><#noparse>${data_index+1}</#noparse></td>
                    <td>数据1</td>
                    <td>数据2</td>
                    <td>数据3</td>
                    <td>
                        <div class="btn-group">
                            <a href="javascript:;"
                               onclick="openAddWin(<#noparse>${data.id}</#noparse>)"
                               class="btn btn-xs btn-primary" type="button"
                               data-toggle="tooltip" data-original-title="编辑">编辑</a>
                            <a href="javascript:;"
                               onclick="remove(<#noparse>${data.id}</#noparse>)"
                               class="btn btn-xs btn-danger" type="button"
                               data-toggle="tooltip" data-original-title="删除">删除</a>
                        </div>
                    </td>
                    <#noparse>[/#list]</#noparse>
                <#noparse>[/#if]</#noparse>
                </tbody>

            </table>
            <div class="row"><#noparse>[@pagesTemaplteNew customSearch.pagedata /]</#noparse></div>
        </div>
    </form>
</div>


<script>
    /**
     * 删除
     * @param id
     */
    function remove(id) {
        layer.confirm("您确定删除此条记录?</br>删除之后不可恢复!",{icon:3}, function (index) {
            layer.load();
            $.ajax({
                url: 'del.do',
                type: 'post',
                data: {'id':id},
                async:false,
                success: function (res) {
                    layer.closeAll("loading");
                    var i = 7;
                    switch (res.type){
                        case 'success':i=1;
                            break;
                        case 'warn':i = 0;
                            break;
                        case 'error':i =2;
                            break;
                    }
                    layer.confirm(res.content,{icon:i}, function (index) {
                        if(res.type=='success'){
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

</script>

<!-- 编辑/新增结点对话框 -->
<div class="modal fade" id="modal-add" tabindex="-1" data-backdrop="static" role="dialog" aria-hidden="true" style="display: none;position: absolute">
    <div class="modal-dialog  modal-dialog-popin" style="min-width: 600px;max-width: 800px;">
        <div class="modal-content">
            <div class="block block-themed block-transparent remove-margin-b">
                <div class="block-header bg-info">
                    <ul class="block-options">
                        <li>
                            <button data-dismiss="modal" type="button"><i class="si si-close"></i></button>
                        </li>
                    </ul>
                    <h3 class="block-title"></h3>
                </div>
                <div class="block-content">
                    <form class="form-horizontal push-10-t" method="post" onsubmit="return false;" id="dataForm"
                          style="margin: 0 auto;position: relative;">
                        <div class="form-group">
                            <input id="data_id" name="id" hidden>
                            <div class="col-xs-6">
                                <label for="column1">字段1<span class="text-danger">*</span></label>
                                <select class="form-control" id="column1" name="column1">
                                    <option disabled selected hidden  value="">请选择</option>
                                    <option value="">aaa</option>
                                    <option value="">bbb</option>
                                </select>
                            </div>
                            <div class="col-xs-6">
                                <label for="column2">输入2<span class="text-danger">*</span></label>
                                <input class="form-control" type="text"  id="column2" name="column2">
                            </div>
                            <div class="col-xs-12">
                                <label for="column4">输入3<span class="text-danger">*</span></label>
                                <textarea style="width: 100%;height: 150px;resize: none" class="form-control" id="column3" name="column3"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-sm btn-default" type="button" data-dismiss="modal">关闭</button>
                <button class="btn btn-sm btn-primary" id="addNodeButton" type="button" onclick="ajaxSubmit()"><i class="fa fa-check"></i> 保存
                </button>
            </div>
        </div>
    </div>
</div>
<script>
    function openAddWin(id) {
        layer.load();
        $('#dataForm')[0].reset();
        if(id==null||id==""){//新增
            $(".block-title").html("新增${data.module}");
            layer.closeAll("loading");
        }else {
            $(".block-title").html("修改${data.module}");
            $.ajax({
                url:id+'.do',
                type:'get',
                dataType: 'json',
                success:function (data) {
                    layer.closeAll("loading");
                    $("#data_id").val(data.id);
                    $("#column1").val(data.column1);
                    $("#column2").val(data.column2);
                    $("#column3").val(data.column3);
                },
                error: function (XMLHttpRequest) {
                    layer.closeAll("loading");
                    alertErrorMessage(XMLHttpRequest);
                }
            });
        }
        $('#modal-add').modal('show');
    }

    function ajaxSubmit(){
        layer.load();
        var url = 'addData.do';
        var data_id = $("#data_id").val();
        if(data_id!=null && data_id!=""){
            url ='edit.do';
        }
        console.log(url);
        $.ajax({
            url: url,
            type: "post",
            data: $('#dataForm').serialize(),
            success: function (res) {
                layer.closeAll("loading");
                var i = 7;
                switch (res.type){
                    case 'success':i=1;
                        break;
                    case 'warn':i = 0;
                        break;
                    case 'error':i =2;
                        break;
                }
                layer.confirm(res.content,{icon:i}, function (index) {
                    if(res.type=='success'){
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
    }
</script>

[#--数据导出方法--]
<div class="modal fade" id="exportModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <form class="modal-dialog" action="exportData.html" method="get" target="_blank">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    请选择导出范围
                </h4>
            </div>
            <div class="modal-body">
                <#noparse>[@tzzPermission3 orgProperty="exp_eq_classInformation.faculty.id" classProperty="exp_eq_classInformation.id" gradeProperty="exp_eq_classInformation.grade"][/@]</#noparse>
                <div  style="display: inline-block;width: 30%">
                    <label for="exp_column1">字段1</label>
                    <select class="form-control" id="exp_column1">
                        <option value="eg1">eg1</option>
                        <option value="eg2">eg2</option>
                    </select>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">关闭
                </button>
                <button type="submit" class="btn btn-primary" >
                    导出excel
                </button>
            </div>
        </div><!-- /.modal-content -->
    </form><!-- /.modal-dialog -->
</div><!-- /.modal -->


[#--文件导入模态框--]
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
                                <input id="file" type="file" name="filePath" class="form-control input-sm">
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
        if ($(":file[name=filePath]").val() == "") {
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
            url: "importData.do",
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
[#--批量审批--]
<div class="modal fade" id="batchApproveModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:500px">
        <form id="batch_approve_win" class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×
                </button>
                <h4 class="modal-title" id="myModalLabel">批量审批</h4>
            </div>
            <div class="modal-body">
                <div  style="display: inline-block;width: 95%">
                    <label for="exp_batch_id">审批结果</label><i class="text-danger">*</i>
                    <select class="form-control input-sm" name="status" id="approve_status">
                        <option value="-1" style="color: #FF3B30;">默认专家审批结果</option>
                        <#noparse>[#list statusList as status]</#noparse>
                        <option value="<#noparse>${status}</#noparse>"><#noparse>${status}</#noparse></option>
                        <#noparse>[/#list]</#noparse>
                    </select>
                </div>
                <div  style="display: inline-block;width: 95%">
                    <label for="exp_batch_id">审批意见</label>
                    <textarea name="option" id="approve_option" class="form-control input-sm" placeholder="" style="resize: none;height: 200px;" maxlength="300"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary" onclick="batchApprove()">
                    提交
                </button>
            </div>
        </form><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script>
    function batchApproveWin() {
        var id_array=new Array();
        $('input[name="ids"]:checked').each(function(){
            id_array.push($(this).val());//向数组中添加元素
        });
        if(id_array.length<1){
            alert("请先勾选数据");
            return;
        }
        $('#batchApproveModel').modal('show');
    }


    function batchApprove() {
        layer.load();
        var id_array=new Array();
        $('input[name="ids"]:checked').each(function(){
            id_array.push($(this).val());//向数组中添加元素
        });
        if(id_array.length<1){
            layer.closeAll("loading");
            alert("请选择操作对象");
            return;
        }

        $.ajax({
            url: "batchApprove.do",
            type: "post",
            data: {
                "ids": id_array,
                "status":$("#approve_status").val(),
                "option":$("#approve_option").val()
            },
            success: function (data) {
                layer.closeAll("loading");
                showDeleteMessage(data.content);
            },
            error: function (XMLHttpRequest) {
                $("#BatchCommit").attr("disabled",false);
                layer.closeAll("loading");
                alertErrorMessage(XMLHttpRequest);

            }
        });
    }

    function showDeleteMessage(message) {
        layer.open({
            type: 1,
            title: '审批结果',
            shadeClose: true,
            shade: 0.8,
            anim: 2,
            area: ['40%', '50%'],
            btn: ['关闭'],
            content: '<div style="text-align: center;line-height: 32px;padding: 20px;">' + message + '</div>',
            end: function (index) {
                window.location.reload();
            }
        });
    }
</script>
</body>
</html>
