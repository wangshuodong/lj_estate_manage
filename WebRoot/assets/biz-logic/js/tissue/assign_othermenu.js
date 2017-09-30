$(function() {
	var menuTree;
    var roleSetting = {
        view : {
            showTitle : false,
            selectedMulti : false,
            autoCancelSelected : false,
            dblClickExpand : false,
            fontCss : function(treeId, treeNode){
                if(treeNode.status == 0){
                    return {color:"red"};
                } else {
                    return {color:"black"};
                }
            }
        },
        data : {
            simpleData : { enable : true, idKey : "roleId", pIdKey : "parentRole",rootPId: 0 },
            key : { name : "roleName"}
        },
        callback : {
            onDblClick : function(event, treeId, treeNode){
                $.post('role_menus.html',{roleId: treeNode.roleId},function(r){
                    if(r.r) {
                    	$("#chkNotAll").attr("checked",false);
                    	$("#chkAll").attr("checked",false);
                        $('#othertitleRoleName').text(' - ' + treeNode.roleName);
                        $('#othertitleRoleName').data('roleId', treeNode.roleId).data('trigger',false);
                        var nodes = menuTree.transformToArray(menuTree.getNodes());
                        for(var i in nodes) {
                            menuTree.checkNode(nodes[i], false, false, false);
                        }
                        for(var i in r.d){
                        	if (menuTree.getNodeByParam('menuId', r.d[i].menuId, null) == null) continue;
                            menuTree.checkNode(menuTree.getNodeByParam('menuId', r.d[i].menuId, null), true, false, false);
                        }
                    } else {
                        asyncbox.tips(r.m, 'error');
                    }
                },'json');
            }
        }
    };
    $.fn.zTree.init($("#rolePanel"), roleSetting, window.roles);
    var roleTree = $.fn.zTree.getZTreeObj("rolePanel");
    roleTree.expandAll(true);
    
    var othersetting = {
        check : {
            enable : true,
            chkStyle : "checkbox",
            chkboxType : { "Y" : "ps", "N" : "ps" },
            autoCheckTrigger : true
        },
        data : {
            simpleData : { enable : true, idKey : "menuId", pIdKey : "parentId" },
            key : { name : "menuname", url : "mUrl" }
        },
        callback : {
            onClick : function(event, treeId, treeNode) {
                if (treeNode.isParent) {
                	othermenuTree.expandNode(treeNode, !treeNode.open, false, true);
                }
            },
            onCheck:function(event, treeId, treeNode){
                $.post('menu_checked.html',{roleId:$('#othertitleRoleName').data('roleId'),menuId:treeNode.menuId,checked:treeNode.checked},
                		function(r){console.log(r.m);},'json');
            },
            beforeCheck:function(treeId, treeNode){
                if($('#othertitleRoleName').data('roleId')){
                    return true;
                }else {
                    asyncbox.tips('请双击需要分配菜单的角色！', 'error');
                    return false;
                }
            }
        },
        view : {showTitle : false, selectedMulti : false, autoCancelSelected : false}
    };
    $.fn.zTree.init($("#othermenuPanel"), othersetting, window.othermenusJson);
    menuTree = $.fn.zTree.getZTreeObj("othermenuPanel");
    menuTree.expandAll(true);
    
    $("#chkAll").click(function() {
    	$("#chkNotAll").attr("checked",false);
    	if($('#othertitleRoleName').data('roleId')){
    		if($(this).is(':checked')) {
    			var nodes = menuTree.transformToArray(menuTree.getNodes());
        		for(var i in nodes) {
                    menuTree.checkNode(nodes[i], true, true, true);
                }
                return true;
    		}
        }else {
        	$("#chkAll").attr("checked",false);
            asyncbox.tips('请双击需要分配菜单的角色！', 'error');
            return false;
        }
    });
    
    $("#chkNotAll").click(function() {
    	$("#chkAll").attr("checked",false);
    	if($('#othertitleRoleName').data('roleId')){
    		if($(this).is(':checked')) {
    			var checkNodes = menuTree.getCheckedNodes(true);
    			var nocheckNodes = menuTree.getCheckedNodes(false);
    			for(var i in checkNodes) {
                    menuTree.checkNode(checkNodes[i], false, true, true);
                }
        		for(var i in nocheckNodes) {
        			menuTree.checkNode(nocheckNodes[i], true, true, true);
                }
                return true;
    		}
        }else {
        	$("#chkNotAll").attr("checked",false);
            asyncbox.tips('请双击需要分配菜单的角色！', 'error');
            return false;
        }
    });
});
