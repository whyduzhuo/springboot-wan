<!DOCTYPE html>
<html>
<head>
    <title></title>
    <#include "/common/tmp/commom.ftl">
    <style>
        .role_item{
            display: inline-block;
            width: 150px;
            height: 160px;
            padding: 20px;
            text-align: center;
            background-color: #fff;
            margin: 15px;
            transition: all ease 300ms;
            position: relative;
        }

        .role_item:hover{
            box-shadow: 0px 5px 10px #dadada;
            transform: translateY(-5px);
        }
        .role_item .checkedRole{
            position: absolute;
            right: 0px;
            top: 0px;
            width: 75%;
        }
    </style>
<body>
<form id="dataForm"  class="form-horizontal">
    <#if dataList?size gt 0>
        <div style="text-align: center">
            <#list dataList as data>
            <a class="role_item" href="javascript:void(0)" onclick="changeRole('${data.id}')">
                <img src="/static/img/login/icon1.png" width="66">
                <p>${data.name}</p>
                <#if data.id==role.id>
                    <img class="checkedRole" src="/static/img/login/checkedRole.png">
                </#if>
            </a>
            </#list>
        </div>
    <#else>
        你还未有角色，请联系管理员添加
    </#if>

</form>
</body>
</head>
<script type="text/javascript">

    function closeLayer() {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }

    function changeRole(id) {
        if(id == ${role.id}){
            closeLayer();
            return;
        }
        ajaxSend("/base/changeRole","get",{"id":id},function (res) {
            layer.msg(res.msg);
            window.top.location.reload();
        },function (res) {
            layer.msg(res.msg);
        });
    }
</script>
</html>