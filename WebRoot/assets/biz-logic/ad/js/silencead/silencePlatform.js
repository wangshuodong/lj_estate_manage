$(function() {
	
	$('#silenceEditDialog').dialog({
		buttons:[{text:'保存',handler:function(){
			if(!$('#silenceEditForm').form('validate')){return;}
			$('#silenceEditForm')._ajaxForm(function(r){
				if(r.r){$('#silenceEditDialog').dialog({closed:true});$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
			});
		}},{text:'关闭',handler:function(){$('#silenceEditDialog').dialog({closed:true});}}]
	});
	
	var grid = $('#grid')._datagrid({
		checkOnSelect:false,
		selectOnCheck:false,
		remoteSort:true,
//		sortName: 'addTime',
//		sortOrder: 'desc',
		
		frozenColumns:[[
			{field:'ck',checkbox:true}
		]],
		toolbar : [
		           {
					text : '添加静默广告',
					iconCls : 'icon-add',
					handler : handler_add
				}, '-', {
					text : '删除所选',
					iconCls : 'icon-remove',
					handler : batch_del
				}, '-' 
				]
	});
	
	$('#queryButton').click(function(){
		var params = $('#queryForm')._formToJson();
		$(grid).datagrid('load',params);
	});
//	$('#resetButton').click(function(){
//		$("#q_adsUrl").val("");
//		$("#q_pageName").val("");
//		$("#startDate").val("");
//		$("#endDate").val("");
//	//	$("#q_adsStatus").attr('value','2'); 
//		   $("#q_adsStatus option[value='']").attr("selected",true)
//		var params = $('#queryForm')._formToJson();
//		$(grid).datagrid('load',params);
//	});
	/*自定义验证：qq*/
});
var formatter = {
		opt : function(value, rowData, rowIndex) {
			var html= '<a class="spacing a-blue" onclick="update('+rowIndex+');" href="javascript:void(0);">修改</a>';
				html+= '<a class="spacing a-red" onclick="delet('+rowIndex+');" href="javascript:void(0);">删除</a>';
			return rowData.id==null?'':html;
		},
		adsStatus : function(value, rowData, rowIndex) {
			if(value == 0){ return '<font color=green>开启</font>'; } 
			else if(value == 1){ return '<font color=red>关闭</font>'; }
			else{return '';}//合计
		}
	};
/*新增静默广告*/
function handler_add() {
	$('#silenceEditForm').attr('action','silencead/silencePlatform_add.html').resetForm();
	$("#id").val("");
	$('#silenceEditDialog').dialog({closed:false,title:'新增静默广告'});
}
/*删除静默广告*/
function delet(rowIndex) {
	$.messager.confirm('操作提示', '确定要删除该广告？', function(r){
		if (r){
			var data = $('#grid').datagrid('getRows')[rowIndex];
			$._ajaxPost('silencead/silencePlatform_delete.html',{id:data.id}, function(r){
				if(r.r){$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
			});
		}
	});
}
/*修改静默广告*/
function update(rowIndex) {
	$('#silenceEditForm').attr('action','silencead/silencePlatform_update.html').resetForm();
	var data = $('#grid').datagrid('getRows')[rowIndex];
	$('#silenceEditForm')._jsonToForm(data);
	$('#silenceEditDialog').dialog({closed:false,title:'修改静默广告'});
}
/*批量删除广告*/
function batch_del() {
	var check = $('#grid').datagrid('getChecked');
	if(check.length == 0){
		$.messager.alert('操作提示', '请选择您要删除的静默广告？','info');
	}else{
		$.messager.confirm('操作提示', '确定要删除所选静默广告？', function(r){
			if (r){
				var ids = new Array();
				for(var i in check){
					ids[i] = check[i].id;
				}
				$._ajaxPost('silencead/silencePlatform_batch_delete.html',{idStr:ids.join('|')},function(r){
					if(r.r){$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
				});
			}
		});
	}
}