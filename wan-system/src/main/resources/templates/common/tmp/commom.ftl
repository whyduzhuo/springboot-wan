<#include "/common/tmp/template.ftl">
<script type="text/javascript" src="/static/jquery/jquery.min.js"></script>
<script type="text/javascript" src="/static/jquery/bootstrap.min.js"></script>
<script type="text/javascript" src="/static/layui-v2.5.6/layui/layui.all.js"></script>
<script type="text/javascript" src="/static/echart4.5/echarts.min.js"></script>
<link rel="shortcut icon" href="/static/favicon.ico"/>
<link href="/static/layui-v2.5.6/layui/css/layui.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="/static/bootstrap/bootstrap.min.css">
<link rel="stylesheet" href="/static/css/oneui.css">
<style type="text/css">
    html{
        height: 100%;
        overflow: hidden;
        background-color: #2b542c;
    }
    body{
        height: 100%;
        overflow-y: scroll;
        padding-top: 10px;
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
        text-align: right;
        flex: 7;
    }
    .page-head-right button{
        vertical-align: top;
    }
    .search-item{
        display: inline-block;
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
    .exp_column{
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
    }
</style>
<script>
    function initSubmit() {
        layer.load();
        $("#pageInit").val("yes");
        $("#listForm").submit();
    }

    function clearCondition(){
        $("#listForm").find(":text[readonly!=readonly]").val("");
        $("#listForm").find("select[noclear!=noclear][disabled!=disabled]").val("");
        $("#listForm").find("select[special=special]").val("0");
        $.each($("#listForm select"),function () {
            var a =$(this).children().length;
            if (a == 1){
                $(this).val($(this).find("option:first").val());
            }
        });
    }
</script>
