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
            <a href="javascript:openAddWin('');" class="btn btn-sm btn-success">
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
                </tr>
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
                    layer.confirm(res.content,{icon:res.icon}, function (index) {
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

    function openAddWin(id) {
        layer.open({
            type: 2,
            title: '编辑${data.module}',
            maxmin: true,
            area: ['500px', '600px'],
            content: 'detail.html'
        });
    }

</script>

[#--数据导出方法--]
<div class="modal fade" id="exportModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <form class="modal-dialog" action="exportData.html" method="post" target="_blank">
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
                <#noparse>[@webPermissionExpWanhy orgProperty="exp_eq_adminClassInformation.faculty.id"
                classProperty="exp_eq_adminClassInformation.id"
                gradeProperty="exp_eq_adminClassInformation.grade"][/@]</#noparse>
                <div style="display: inline-block;width: 30%">
                    <label>选择1</label>
                    <select name="exp_eq_schoolStatus.id" class="form-control input-sm">
                        <option value="" selected >全部</option>
                    <#noparse>[#list statusList as status]</#noparse>
                        <option value="${status}" <#noparse>[#if searchParams['search_eq_status']==status]</#noparse>selected<#noparse>[/#if]</#noparse>><#noparse>${status}</#noparse></option>
                    <#noparse>[/#list]</#noparse>
                    </select>
                </div>

                <h4>请选择导出字段</h4>
                <div>
                    <div class="exp_column">
                        <label class="css-input css-checkbox css-checkbox-primary">
                            <input type="checkbox" checked name="fields" value="学号"/><span></span>学号
                        </label>
                    </div>

                    <div class="exp_column">
                        <label class="css-input css-checkbox css-checkbox-primary">
                            <input type="checkbox" checked name="fields" value="年级"/><span></span>年级
                        </label>
                    </div>
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
