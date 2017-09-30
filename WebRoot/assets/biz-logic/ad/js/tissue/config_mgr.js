$(function(){
	$('#confEditDialog').dialog({
		buttons:[{text:'保存',handler:function(){
			if(!$('#confEditForm').form('validate')){return;}
			$('#confEditForm')._ajaxForm(function(r){
				if(r.r){
					$('#confEditDialog').dialog({closed:true});
					$('#grid').datagrid('reload');
				}else{
					$.messager.alert('操作提示', r.m,'error');
				}
			});
		}},
		{text:'关闭',handler:function(){$('#confEditDialog').dialog({closed:true});}}]
	});
	
	var grid = $('#grid')._datagrid({
		checkOnSelect:false,
		selectOnCheck:false,
		frozenColumns:[[
			{field:'ck',checkbox:true}
		]],
		toolbar : [{
					text : '创建配置',
					iconCls : 'icon-add',
					handler : handler_add
				}, '-' ]
	});
	
	$('#searchButton').click(function(){
		var params = $('#queryForm')._formToJson();
		$(grid).datagrid('load',params);
	});
	/*新增用户*/
	function handler_add() {
		$('#confEditForm').attr('action','/conf/add.html').resetForm();
		$('#cfgKey,#cfgId').removeAttr('readonly');
		$('#confEditDialog').dialog({closed:false,title:'新建配置'});
	}
});
var formatter = {
	parentCfg: function(value, rowData, rowIndex) {
		return value ? value : '--';
	},
	parentKey: function(value, rowData, rowIndex) {
		return value ? value : '--';
	},
	status : function(value, rowData, rowIndex) {
		if(value == 1){ return '<font color=green>正常</font>'; } else { return '<font color=red>停用</font>'; }
	},
	opt : function(value, rowData, rowIndex) {
		var html = '<a class="spacing a-blue" onclick="updConfig('+rowIndex+');" href="javascript:void(0);">修改</a>';
		html += '<a class="spacing a-red" onclick="delConfig('+rowIndex+');" href="javascript:void(0);">删除</a>';
		return html;
	}
};

function updConfig(rowIndex) {
	$('#confEditForm').attr('action','conf/upd.html').resetForm();
	$('#cfgKey,#cfgId').attr('readonly', 'readonly');
	var data = $('#grid').datagrid('getRows')[rowIndex];
	$('#confEditForm')._jsonToForm(data);
	$('#confEditDialog').dialog({closed:false,title:'修改配置'});
}
function delConfig(rowIndex) {
	$.messager.confirm('操作提示', '确定要删除该配置？', function(r){
		if (r){
			var data = $('#grid').datagrid('getRows')[rowIndex];
			$._ajaxPost('conf/del.html',{cfgId:data.cfgId,cfgKey:data.cfgKey}, function(r){
				if(r.r){$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
			});
		}
	});
}
