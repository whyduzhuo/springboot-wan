<!DOCTYPE html>
<html>
<head>
    <title></title>
    <#include "/common/tmp/commom.ftl">
</head>
<body>
<div style="position: fixed;height: 50px;">
    <button   class="btn btn-success" type="button"
              data-toggle="tooltip" data-original-title="刷新" onclick="relfush()">刷新</button>
</div>
<div style="height: 50px"></div>
<div style="display: inline-block;width: 60%">
    <ul id="treeDemo" class="ztree"></ul>
</div>
<div style="display: inline-block;width: 30%;vertical-align: top">
    <ul>
        <#list roleList as role >
        <li style="display: block">
            <div class="exp_column">
                <label class="css-input css-checkbox css-checkbox-primary">
                    <input type="checkbox" onchange="localBuild()" checked name="roleId" value="${role.id}"/><span></span>
                    ${role.name}
                </label>
            </div>
        </li>
        </#list>
    </ul>
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

    var allMenus = [];
    var rolesMenus = [];
    buildTree();

    function buildTree() {
        $.ajax({
            url:"/base/admin/getRolesMenu?adminId=${admin.id}",
            type: "get",
            async:false,
            success:function (message) {
                rolesMenus = message.data;
            },
            error:function (e) {
                alert(e);
            }
        });
        $.ajax({
            url:"/base/menu/getNode",
            type: "get",
            async:false,
            success:function (message) {
                allMenus = message.data;
            },
            error:function (e) {
                alert(e);
            }
        });
        localBuild();
    }
    
    function localBuild() {
        var zNodes=[];
        var trees = buildSelected();
        pushChildren(zNodes,null,trees);
        zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    }

    /**
     * 设置选中的checked属性为true
     * @param allMenus
     * @param rolesMenus
     */
    function buildSelected() {
        var checkedRoles =[];
        $('input[name="roleId"]:checked').each(function(){
            checkedRoles.push($(this).val());
        });
        for (var i in allMenus){
            var menu = allMenus[i];
            menu.children=[];
            if(havaMenu(menu,checkedRoles,rolesMenus)){
                menu.checked=true;
            }else {
                menu.checked=false;
            }
        }
        return allMenus;
    }

    /**
     * 判断menus 中是否含有menu,并且被选中
     * @param menu
     * @param menus
     */
    function havaMenu(menu,checkedRoles,menus) {
        for (var i in menus){
            var m = menus[i];
            if(menu.id===m.id){
                for(var j in checkedRoles){
                    var roleId = checkedRoles[j];
                    if(roleId==m.roleId){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 递归组装树结构
     * @param children
     * @param node
     * @param arry
     */
    function pushChildren(children,node,array) {
        for (var i in array) {
            var node1 = array[i];
            if((node1.pid==null && node==null)|| (node!=null && node1.pid!=null && node1.pid === node.id)){
                children.push(node1);
                pushChildren(node1.children,node1, array);
            }
        }
    }

</script>
</html>
