<#include "/common/tmp/template.ftl">
<script type="text/javascript" src="/static/jquery/jquery.min.js"></script>
<script type="text/javascript" src="/static/jquery/bootstrap.min.js"></script>
<script type="text/javascript" src="/static/layui-v2.5.6/layui/layui.all.js"></script>
<script type="text/javascript" src="/static/echart4.5/echarts.min.js"></script>
<script type="text/javascript" src="/static/zTree_v3-master/js/jquery.ztree.all.js"></script>
<link href="/static/zTree_v3-master/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css">
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
        position: relative;
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
    #listForm{
        width: 98%;
        margin: 0 auto;
    }
    .exp_column {
        display: inline-block;
        padding-right: 5px;
    }

    .edit-win-foot{
        position: absolute;
        height: 35px;
        bottom: 0px;
        background-color: #87c5ff;
        width: 100%;
        text-align: center;
    }
    .orders-lable{
        display: inline-block;vertical-align: middle
    }
    .orders-lable a{
        top: 0px;
        display: block;
        font-size: 12px;
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

    function pageSkip(pageNumber) {
        layer.load();
        $("#pageNumber").val(pageNumber);
        $("#listForm").submit();
    }


    function changePagesize(pageSize) {
        layer.load();
        $("#pageSize").val(pageSize);
        $("#pageNumber").val("1");
        $("#listForm").submit();
    }

    /**
     * Ajax ?????????????????????????????????????????????
     * url:??????
     * data:??????
     * successFun:????????????????????????
     * errorFun:????????????????????????
     **/
    function ajaxSend(url,type,data,successFun,errorFun) {
        layer.load();
        $.ajax({
            url: url,
            type: type,
            data: data,
            success: function (res) {
                layer.closeAll("loading");
                if(res.type =='SUCCESS'){
                    successFun(res);
                }else {
                    errorFun(res);
                }
            },
            error: function (XMLHttpRequest) {
                layer.closeAll("loading");
                layer.open({
                    type: 1,
                    title: '???????????????',
                    maxmin: true,
                    area: ['70%', '70%'],
                    content:XMLHttpRequest
                })
            }
        });
    }

    /**
     * Ajax ????????????
     * type???get/post
     * url:??????
     * data:??????
     * successFun:????????????????????????
     * errorFun:????????????????????????
     **/
    function ajaxSubmit(url,type,data,successFun,errorFun) {
        layer.load();
        $.ajax({
            url: url,
            type: type,
            data: data,
            success: function (res) {
                layer.closeAll("loading");
                layer.confirm(res.msg,{icon:res.icon}, function (index) {
                    layer.close(index);
                    if(res.type =='SUCCESS'){
                        if(successFun!=null){
                            successFun(res);
                        }
                    }else {
                        if(errorFun!=null){
                            errorFun(res);
                        }

                    }
                });
            },
            error: function (XMLHttpRequest) {
                layer.closeAll("loading");
                layer.open({
                    type: 1,
                    title: '???????????????',
                    maxmin: true,
                    area: ['70%', '70%'],
                    content:XMLHttpRequest
                })
            }
        });
    }

    /**
     * Ajax post??????
     * url:??????
     * data:??????
     * successFun:????????????????????????
     * errorFun:????????????????????????
     **/
    function ajaxPost(url,data,successFun,errorFun) {
        ajaxSubmit(url,"POST",data,successFun,errorFun);
    }

    /**
     * Ajax get??????
     * url:??????
     * data:??????
     * successFun:????????????????????????
     * errorFun:????????????????????????
     **/
    function ajaxGet(url,data,successFun,errorFun) {
        ajaxSubmit(url,"GET",data,successFun,errorFun);
    }

    /**
     * Ajax????????????
     * url:??????
     * data:??????
     * tips:?????????
     * successFun:????????????????????????
     * errorFun:????????????????????????
     **/
    function ajaxDelete(url,data,tips,successFun,errorFun) {
        data._method = "delete";
        console.log(data);
        layer.confirm(tips,{icon:3}, function (index) {
            layer.load();
            ajaxSubmit(url,"POST",data,successFun,errorFun);
            layer.close(index);
        })
    }

    /**
     * ???????????????????????????
     *
     **/
    function isBlank(s) {
        if(s ==undefined){
            return true;
        }
        if(s == null){
            return true;
        }
        if(typeof(s)=='string'){
            if(s.replace(/^\s*|\s*$/g,'')==''){
                return true;
            }
        }
        return false;
    }
    
    function isNotBlank(s) {
        return !isBlank(s);
    }

    /**
     * ???????????????
     * @param children
     * @param node
     * @param arry
     */
    function pushChildren(children,node,arry) {
        for (var i in arry) {
            var node1 = arry[i];
            if((isBlank(node1.pid) && isBlank(node))|| (isNotBlank(node) && isNotBlank(node1.pid) && node1.pid == node.id)){
                pushChildren(node1.children,node1, arry);
                children.push(node1);
            }
        }
    }

    function alert(msg) {
        layer.msg(msg);
    }

    $(document).on("click","#selectAll", function () {
        $("#listTable").find("input[name='ids']:enabled").prop("checked",this.checked);
    });
</script>
