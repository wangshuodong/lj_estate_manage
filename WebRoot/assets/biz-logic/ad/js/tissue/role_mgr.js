$(function() {
	$('#roleEditDialog').dialog({
		buttons:[{text:'保存',handler:function(){
			if(!$('#roleEditForm').form('validate')){return;}
			$('#roleEditForm')._ajaxForm(function(r){
				if(r.r){$('#roleEditDialog').dialog({closed:true});$('#roleGrid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
			});
		}},{text:'关闭',handler:function(){$('#roleEditDialog').dialog({closed:true});}}]
	});
	$('#roleGrid')._datagrid({
		toolbar : [{
			text : '创建角色',
			iconCls : 'icon-add',
			handler : function(){
				$('#roleEditForm').attr('action','role/add.html').resetForm();
				$('#roleId').removeAttr('readonly');
				$('#roleEditDialog').dialog({closed:false,title:'新增角色'});
			}
		}, '-'],
		onDblClickRow:function(rowIndex, rowData){
			$._ajaxPost('role/role_menus.html',{roleId: rowData.roleId},function(r){
				if(r.r) {
					$('#titleRoleName').text(' - ' + rowData.roleName);
					$('#titleRoleName').data('roleId', rowData.roleId).data('trigger',false);
					var nodes = zTree.transformToArray(zTree.getNodes());
					for(var i in nodes) {
						zTree.checkNode(nodes[i], false, false, false);
					}
					for(var i in r.d){
						zTree.checkNode(zTree.getNodeByParam('menuId', r.d[i].menuId, null), true, false, false);
					}
				} else {
					$.messager.alert('操作提示', r.m,'error');
				}
			});
		}
	});
	
	var setting = {
		check : {
			enable : true,
			chkStyle : "checkbox",
			chkboxType : { "Y" : "ps", "N" : "ps" },
			autoCheckTrigger : true
		},
		data : {
			simpleData : { enable : true, idKey : "menuId", pIdKey : "parentId" },
			key : { name : "menuName", url : "mUrl" }
		},
		callback : {
			onClick : function(event, treeId, treeNode) {
				if (treeNode.isParent) {
					zTree.expandNode(treeNode, !treeNode.open, false, true);
				}
			},
			onCheck:function(event, treeId, treeNode){
				$._ajaxPost('role/menu_checked.html',{roleId:$('#titleRoleName').data('roleId'),menuId:treeNode.menuId,checked:treeNode.checked},function(r){console.log(r.m);});
			},
			beforeCheck:function(treeId, treeNode){
				if($('#titleRoleName').data('roleId')){
					return true;
				}else {
					$.messager.alert('操作提示', '请双击需要分配菜单的角色！','info');
					return false;
				}
			}
		},
		view : { showTitle : false, selectedMulti : false, autoCancelSelected : false }
	};
	$.fn.zTree.init($("#menuPanel"), setting, window.menus);
	var zTree = $.fn.zTree.getZTreeObj("menuPanel");
	zTree.expandAll(true);
});
function custom(width, height) {
	$('#menuMgrPanel,#roleMgrPanel').height(height - 29);
	$('#rightPanel').css({'margin-left':$('#roleMgrPanel').width() + 7});
	$('#roleGrid').datagrid('resize',{width:$('#roleMgrPanel').width(),height:height});
}
var formatter = {
	opt : function(value, rowData, rowIndex) {
		var html = '<a class="spacing a-blue" onclick="updRole('+rowIndex+');" href="javascript:void(0);">修改</a>';
			html+= '<a class="spacing a-red" onclick="delRole('+rowIndex+');" href="javascript:void(0);">删除</a>';
		return html;
	}
};
function updRole(rowIndex){
	var role = $('#roleGrid').datagrid('getRows')[rowIndex];
	$('#roleEditForm').attr('action','role/upd.html').resetForm();
	$('#roleEditForm')._jsonToForm(role);
	$('#roleId').attr('readonly', 'readonly');
	$('#roleEditDialog').dialog({closed:false,title:'修改角色'});
}
function delRole(rowIndex){
	$.messager.confirm('操作提示', '确定要删除该角色？', function(r){
		if (r){
			var role = $('#roleGrid').datagrid('getRows')[rowIndex];
			$._ajaxPost('role/del.html',{roleId:role.roleId}, function(r){
				if(r.r){$('#roleGrid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
			});
		}
	});
}