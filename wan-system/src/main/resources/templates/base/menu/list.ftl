<!DOCTYPE html>
<html>
<head>
    <title></title>
    <#include "/common/tmp/commom.ftl">
</head>
<body>
<@adminPageNav navName1='系统管理' navName2='菜单管理'/>
    <div style="position: fixed;height: 40px;">
        <@shiro.hasPermission name="100200">
        <button  onclick="addWin()"
                 class="btn btn-sm btn-success" type="button"
                 data-toggle="tooltip" data-original-title="新增">新增</button>
        </@shiro.hasPermission>
        <@shiro.hasPermission name="100201">
        <button  onclick="editWin()"
           class="btn btn-sm btn-primary" type="button"
           data-toggle="tooltip" data-original-title="编辑">编辑</button>
        </@shiro.hasPermission>
        <@shiro.hasPermission name="100202">
        <button onclick="del()"
           class="btn btn-sm btn-danger" type="button"
           data-toggle="tooltip" data-original-title="删除">删除</button>
        </@shiro.hasPermission>
    </div>
    <div style="height: 50px"></div>
    <div>
        <ul id="treeDemo" class="ztree"></ul>
    </div>
</body>
<SCRIPT LANGUAGE="JavaScript">
    var zTreeObj;
    var setting = {};
    
    function relfush() {
        buildTree();
        layer.msg("刷新成功！",{icon:6});
    }

    buildTree();

    function buildTree() {
        var trees =[];
        $.ajax({
            url:"getNode",
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

    
    function addWin() {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = zTree.getSelectedNodes();
        var treeNode = nodes[0];
        if(nodes.length==0){
            layer.msg("请先选择一个节点！",{icon:0});
            return;
        }
        if(treeNode.type=="按钮"){
            layer.msg("按钮下面不可新增！",{icon:0});
            return;
        }
        layer.open({
            type: 2,
            title: '新增菜单',
            maxmin: true,
            area: ['500px', '600px'],
            content: 'addWin?pid='+treeNode.id
        });
    }
    
    function addMenu(menu) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = zTree.getSelectedNodes();
        var treeNode = nodes[0];
        zTree.addNodes(treeNode,menu);

    }
    
    function editWin() {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = zTree.getSelectedNodes();
        var treeNode = nodes[0];
        if(nodes.length==0){
            layer.msg("请先选择一个节点！",{icon:0});
            return;
        }
        layer.open({
            type: 2,
            title: '编辑菜单',
            maxmin: true,
            area: ['500px', '600px'],
            content: 'editWin?id='+treeNode.id
        });
    }

    function del() {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = zTree.getSelectedNodes();
        var treeNode = nodes[0];
        if(nodes.length==0){
            layer.msg("请先选择一个节点！",{icon:0});
            return;
        }
        var id = treeNode.id;
        layer.confirm("您确定删除此条记录?</br>删除之后不可恢复!",{icon:3}, function (index) {
            layer.load();
            $.ajax({
                url: 'del',
                type: 'post',
                data: {
                    _method:'delete',
                    'id':id
                    },
                async:false,
                success: function (res) {
                    layer.closeAll("loading");
                    layer.confirm(res.msg,{icon:res.icon}, function (index) {
                        if(res.type=='SUCCESS'){
                            relfush();
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
</SCRIPT>
</html>
