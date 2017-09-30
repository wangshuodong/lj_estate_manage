$(function() {
	
	$('#logEditDialog').dialog({
		buttons:[{text:'保存',handler:function(){
			if(!$('#logEditForm').form('validate')){return;}
			$('#logEditForm')._ajaxForm(function(r){
				if(r.r){$('#logEditDialog').dialog({closed:true});$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
			});
		}},{text:'关闭',handler:function(){$('#logEditDialog').dialog({closed:true});}}]
	});
	
	var grid = $('#grid')._datagrid({
		checkOnSelect:false,
		selectOnCheck:false,
		remoteSort:true,
		sortName: 'versionNo',
		sortOrder: 'desc',
		
		frozenColumns:[[
			{field:'ck',checkbox:true}
		]],
		toolbar : [{
					text : '添加记录',
					iconCls : 'icon-add',
					handler : handler_add
				}, '-', {
					text : '删除所选',
					iconCls : 'icon-remove',
					handler : batch_del
				}, '-' ]
	});
	
	$('#queryButton').click(function(){
		var params = $('#queryForm')._formToJson();
		$(grid).datagrid('load',params);
	});
	
});

var formatter = {
	opt : function(value, rowData, rowIndex) {
		var html= '<a class="spacing a-blue" onclick="update('+rowIndex+');" href="javascript:void(0);">修改</a>';
			html+= '<a class="spacing a-red" onclick="delet('+rowIndex+');" href="javascript:void(0);">删除</a>';
		return rowData.id==null?'':html;
	}
};


/*新增版本记录*/
function handler_add() {
	$('#logEditForm').attr('action','version_log/add.html').resetForm();
	$('#logEditDialog').dialog({closed:false,title:'新增版本记录'});
}

/*修改版本记录*/
function update(rowIndex) {
	$('#logEditForm').attr('action','version_log/update.html').resetForm();
	var data = $('#grid').datagrid('getRows')[rowIndex];
	$('#logEditForm')._jsonToForm(data);
	$('#logEditDialog').dialog({closed:false,title:'修改广告版本记录'});
}

/*删除版本记录*/
function delet(rowIndex) {
	$.messager.confirm('操作提示', '确定要删除条记录？', function(r){
		if (r){
			var data = $('#grid').datagrid('getRows')[rowIndex];
			$._ajaxPost('version_log/delete.html',{id:data.id}, function(r){
				if(r.r){$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
			});
		}
	});
}

/*批量删除版本记录*/
function batch_del() {
	var check = $('#grid').datagrid('getChecked');
	if(check.length == 0){
		$.messager.alert('操作提示', '请选择您要删除的记录？','info');
	}else{
		$.messager.confirm('操作提示', '确定要删除所选记录？', function(r){
			if (r){
				var ids = new Array();
				for(var i in check){
					ids[i] = check[i].id;
				}
				$._ajaxPost('version_log/batchDelete.html',{idStr:ids.join('|')},function(r){
					if(r.r){$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
				});
			}
		});
	}
}




