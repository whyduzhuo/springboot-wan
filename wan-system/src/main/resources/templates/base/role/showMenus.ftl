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
            overflow: hidden;
            background-color: #2b542c;
        }
        body{
            height: 100%;
            overflow-y: scroll;
            padding: 10px;
        }
    </style>
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

    function pushChildren(children,node,arry) {
        for (var i in arry) {
            var node1 = arry[i];
            if((node1.pid==null && node==null)|| (node!=null && node1.pid!=null && node1.pid == node.id)){
                pushChildren(node1.children,node1, arry);
                children.push(node1);
            }
        }
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
