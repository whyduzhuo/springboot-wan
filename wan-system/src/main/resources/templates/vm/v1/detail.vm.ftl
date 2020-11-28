<!DOCTYPE html>
<!--[if IE 9]>         <html class="ie9 no-focus"> <![endif]-->
<!--[if gt IE 9]><!-->
<html class="no-focus"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <title>编辑${data.module}</title>

    <meta name="author" content="bianmaren">
    <meta name="robots" content="noindex, nofollow">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0">

    <#noparse>[#include "/common/xsxx/common.ftl" /]</#noparse>
    <style type="text/css">
        .exp_column {
            display: inline-block;
            padding-right: 5px;
        }
    </style>
</head>
<body>
<#noparse>[@adminPageNav navName1='</#noparse>${data.module}<#noparse>' navName2='编辑</#noparse>${data.module}<#noparse>' isListPage=false /]</#noparse>
<div class="block">
    <div class="block-content table-responsive">
        <form id="inputForm" class="form-horizontal" >
            <input type="hidden" name="id" id="data_id" value="<#noparse>${data.id}</#noparse>"/>
            <div class="form-group">
                <div class="col-xs-12 col-md-4">
                    <label for="column1">输入1<span class="text-danger">*</span></label>
                    <input class="form-control" type="text" id="column1" name="column1" maxlength="200" value="<#noparse>${data.column1}</#noparse>"
                           required/>
                </div>
                <div class="col-xs-12 col-md-4">
                    <label for="column2">选择1<span class="text-danger">*</span></label>
                    <select class="form-control" name="column2" id="column2">
                    <#noparse>[#list  statusList as status]</#noparse>
                            <option value="<#noparse>${status}</#noparse>"  <#noparse>[#if data.column2 == status]</#noparse>selected<#noparse>[/#if]</#noparse>><#noparse>${status}</#noparse></option>
                    <#noparse>[/#list]</#noparse>
                    </select>
                </div>

                <div class="col-xs-12 col-md-4 ">
                    <label for="column3">选择3<span class="text-danger">*</span></label>
                    <div class="exp_column">
                        <label class="css-input css-checkbox css-checkbox-primary">
                            <input type="radio" name="column3" value="是" <#noparse>[#if data.column3 == '是']</#noparse>checked<#noparse>[/#if]</#noparse> /><span></span>
                        </label>是
                    </div>

                    <div class="exp_column">
                        <label class="css-input css-checkbox css-checkbox-primary">
                            <input type="radio" name="column3" value="否" <#noparse>[#if data.column3 == '否']</#noparse>checked<#noparse>[/#if]</#noparse> /><span></span>
                        </label>否
                    </div>

                </div>
                <div class="col-xs-12 col-md-4">
                    <label for="column4">输入4<span class="text-danger">*</span></label>
                    <textarea style="width: 100%;height: 150px;resize: none" class="form-control" id="column4"
                              name="column4"><#noparse>${data.column4}</#noparse></textarea>
                </div>
            </div>
            <div class="form-group">
                <div class="col-xs-12 col-md-4">
                    <button type="button" class="btn btn-success" id="submit-btn"
                            onclick="ajaxSubmit()">提交</button>
                    <button type="button" class="btn btn-square" id="submit-btn"
                            onclick="closeLayer()">关闭
                    </button>
                </div>
            </div>
        </form>
    </div>
</div><!-- /.block -->
<script>

    function closeLayer() {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }

    function ajaxSubmit() {
        layer.load();
        var url = 'addData.do';
        var data_id = $("#data_id").val();
        if (data_id != null && data_id != "") {
            url = 'edit.do';
        }
        $.ajax({
            url: url,
            type: "post",
            data: $('#inputForm').serialize(),
            success: function (res) {
                layer.closeAll("loading");
                layer.confirm(res.content, {icon: res.icon}, function (index) {
                    if (res.type == 'success') {
                        layer.load();
                        window.parent.location.reload();
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
</body>
</html>

