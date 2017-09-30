$(function() {
	$('#userEditDialog').dialog({
		buttons:[{text:'保存',handler:function(){
			if(!$('#userEditForm').form('validate')){return;}
			$('#userEditForm')._ajaxForm(function(r){
				if(r.r){$('#userEditDialog').dialog({closed:true});$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
			});
		}},{text:'关闭',handler:function(){$('#userEditDialog').dialog({closed:true});}}]
	});
	$('#userRoleDialog').dialog({buttons:[{text:'关闭',handler:function(){$('#userRoleDialog').dialog({closed:true});}}]});
	var grid = $('#grid')._datagrid({
		checkOnSelect:false,
		selectOnCheck:false,
		frozenColumns:[[
			{field:'ck',checkbox:true}
		]],
		toolbar : [{
					text : '创建用户',
					iconCls : 'icon-add',
					handler : handler_add
				}, '-', {
					text : '删除所选',
					iconCls : 'icon-remove',
					handler : batch_del
				}, '-' ]
	});
	
	var setting = {
		check : {
			enable : true, chkStyle : "checkbox", chkboxType : { "Y" : "ps", "N" : "ps" }
		},
		data : {
			simpleData : { enable : true, idKey : "roleId" },
			key : { name : "roleName" }
		},
		callback : {
			onCheck : function(event, treeId, treeNode){
				$._ajaxPost('user/role_checked.html',{userId:$('#roleZtee').data('userId'),roleId:treeNode.roleId,checked:treeNode.checked},function(r){
					if(r.r){
						var nodes = zTree.getCheckedNodes(true);
						var rowIndex = $('#roleZtee').data('rowIndex');
						var data = $('#grid').datagrid('getRows')[rowIndex];
						data.roles = nodes;
						$('#grid').datagrid('refreshRow', rowIndex);
					}
				});
			}
		},
		view : { showTitle : false, selectedMulti : false, autoCancelSelected : false }
	};
	$.fn.zTree.init($("#roleZtee"), setting, window.roles);
	var zTree = $.fn.zTree.getZTreeObj("roleZtee");
	
	$('#queryButton').click(function(){
		var params = $('#queryForm')._formToJson();
		$(grid).datagrid('load',params);
	});
	
	/*新增用户*/
	function handler_add() {
		$('#userEditForm').attr('action','user/add.html').resetForm();
		$('#username').removeAttr('readonly');
		$('#userId').val('');
		$('#userEditDialog').dialog({closed:false,title:'新增用户'});
	}
	/*批量删除*/
	function batch_del() {
		var check = $('#grid').datagrid('getChecked');
		if(check.length > 0){
			$.messager.confirm('操作提示', '确定要删除所选用户？', function(r){
				if (r){
					var userIds = new Array();
					for(var i in check){
						userIds[i] = check[i].userId;
					}
					$._ajaxPost('user/batch_del.html',{userIds:userIds.join('|')},function(r){
						if(r.r){$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
					});
				}
			});
		}
	}
});
var formatter = {
	status : function(value, rowData, rowIndex) {
		if(value == 1){ return '<font color=green>正常</font>'; } else { return '<font color=red>停用</font>'; }
	},
	isauth : function(value, rowData, rowIndex) {
		if(value == 0){ return '<font color=red>未完善</font>'; } else if(value == 1) { return '<font color=yellow>待审核</font>'; } else {return '<font color=green>已认证</font>';}
	},
	roles : function(value, rowData, rowIndex){
		var roles = new Array();
		for(var i in value){
			roles[i] = value[i].roleName;
		}
		var text = roles.join('，');
		return '<span title="'+text+'">'+text+'</span>';
	},
	opt : function(value, rowData, rowIndex) {
		var html = '<a class="spacing a-green" onclick="updRole('+rowIndex+');" href="javascript:void(0);">角色分配</a>';
			html+= '<a class="spacing a-blue" onclick="updUser('+rowIndex+');" href="javascript:void(0);">修改</a>';
			html+= '<a class="spacing a-red" onclick="delUser('+rowIndex+');" href="javascript:void(0);">删除</a>';
		return html;
	}
};
/*分配角色*/
function updRole(rowIndex) {
	var data = $('#grid').datagrid('getRows')[rowIndex];
	$('#roleZtee').data('userId', data.userId).data('rowIndex', rowIndex);
	var zTree = $.fn.zTree.getZTreeObj("roleZtee");
	zTree.checkAllNodes(false);
	for(var i in data.roles){
		zTree.checkNode(zTree.getNodeByParam('roleId', data.roles[i].roleId, null), true, false, false);
	}
	$('#userRoleDialog').dialog({closed:false});
}
/*修改用户*/
function updUser(rowIndex) {
	$('#userEditForm').attr('action','user/upd.html').resetForm();
	$('#username').attr('readonly', 'readonly');
	var data = $('#grid').datagrid('getRows')[rowIndex];
	$('#userEditForm')._jsonToForm(data);
	$('#userEditDialog').dialog({closed:false,title:'修改用户'});
}
/*删除用户*/
function delUser(rowIndex) {
	$.messager.confirm('操作提示', '确定要删除该用户？', function(r){
		if (r){
			var data = $('#grid').datagrid('getRows')[rowIndex];
			$._ajaxPost('user/del.html',{userId:data.userId}, function(r){
				if(r.r){$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
			});
		}
	});
}