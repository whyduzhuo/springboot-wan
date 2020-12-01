<!DOCTYPE html>
<html>
<head>
    <title></title>
    <#include "/common/tmp/commom.ftl">
</head>
<body>
<div style="position: fixed;height: 50px;">
    <@shiro.hasPermission name="100303">
    <button  onclick="save()"
             class="btn btn-success" type="button"
             data-toggle="tooltip" data-original-title="授权">授权</button>
    </@shiro.hasPermission>
    <button   class="btn btn-default" type="button"
              data-toggle="tooltip" data-original-title="刷新" onclick="relfush()">刷新</button>
</div>
<div style="height: 50px"></div>
<div>
    <ul id="treeDemo" class="ztree"></ul>
</div>
</body>
<script LANGUAGE="JavaScript">
    var zTreeObj;
    var setting = {
        check: {
            autoCheckTrigger: true,
            chkboxType: { "Y": "p", "N": "s" },
            chkStyle: "checkbox",
            nocheckInherit: false,
            chkDisabledInherit: true,
            enable: true
        }
    };

    function relfush() {
        buildTree();
        layer.msg("刷新成功！",{icon:6});
    }

    buildTree();

    function buildTree() {
        var trees =[];
        $.ajax({
            url:"getMenuTree?id=${data.id}",
            type: "get",
            async:false,
            success:function (message) {
                trees = message.data;
            },
            error:function (e) {

            }
        });
        var zNodes=[];
        pushChildren(zNodes,null,trees);
        zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    }


    function closeLayer() {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }
    
    function save() {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = zTree.getCheckedNodes(true);
        var roleId = '${data.id}';
        var menus = [];
        for (var i in nodes){
            menus.push(nodes[i].id);
        }
        $.ajax({
            url: "grantMenu",
            type: "POST",
            data: {
                'roleId':roleId,
                'menus':menus
            },
            success: function (res) {
                layer.closeAll("loading");
                layer.confirm(res.msg,{icon:res.icon}, function (index) {
                    if(res.type=="SUCCESS"){
                        closeLayer();
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
</html>
