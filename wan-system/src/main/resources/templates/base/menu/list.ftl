<!DOCTYPE html>
<html>
<head>
    <title></title>
    <link rel="shortcut icon" href="/static/favicon.ico"/>
    <script type="text/javascript" src="/static/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/static/jquery/bootstrap.min.js"></script>
    <script type="text/javascript" src="/static/layui-v2.5.6/layui/layui.all.js"></script>
    <script type="text/javascript" src="/static/zTree_v3-master/js/jquery.ztree.all.js"></script>

    <link href="/static/layui-v2.5.6/layui/css/layui.css" rel="stylesheet" type="text/css">
    <link href="/static/zTree_v3-master/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="/static/bootstrap/bootstrap.min.css">
    <style type="text/css">
        html{
            height: 100%;
        }
        body{
            height: 100%;
            padding: 10px;
        }
    </style>
</head>
<body>
    <div style="position: fixed;height: 50px;">
        <@shiro.hasPermission name="100200">
        <button  onclick="addWin()"
                 class="btn btn-success" type="button"
                 data-toggle="tooltip" data-original-title="新增">新增</button>
        </@shiro.hasPermission>
        <@shiro.hasPermission name="100201">
        <button  onclick="editWin()"
           class="btn btn-primary" type="button"
           data-toggle="tooltip" data-original-title="编辑">编辑</button>
        </@shiro.hasPermission>
        <@shiro.hasPermission name="100202">
        <button onclick="del()"
           class="btn btn-danger" type="button"
           data-toggle="tooltip" data-original-title="删除">删除</button>
        </@shiro.hasPermission>
        <button   class="btn btn-default" type="button"
                  data-toggle="tooltip" data-original-title="刷新" onclick="relfush()">刷新</button>
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

    function pushChildren(children,node,arry) {
        for (var i in arry) {
            var node1 = arry[i];
            if((node1.pid==null && node==null)|| (node!=null && node1.pid!=null && node1.pid == node.id)){
                pushChildren(node1.children,node1, arry);
                children.push(node1);
            }
        }
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
                            layer.load();
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
