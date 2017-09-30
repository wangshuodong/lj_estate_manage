$(function() {
	//编辑
	$('#adsEditDialog').dialog({
		buttons:[{text:'保存',handler:function(){
			if(!$('#adsEditForm').form('validate')){return;}
			$('#adsEditForm')._ajaxForm(function(r){
				if(r.r){$('#adsEditDialog').dialog({closed:true});$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
			});
		}},{text:'取消',handler:function(){$('#adsEditDialog').dialog({closed:true});}}]
	});
	//复制规则
	$('#adsCopyDialog').dialog({
		buttons:[{text:'确定复制',handler:function(){
			if(!$('#adsCopyForm').form('validate')){return;}
			$('#adsCopyForm')._ajaxForm(function(r){
				if(r.r){$('#adsCopyDialog').dialog({closed:true});$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
			});
		}},{text:'取消',handler:function(){$('#adsCopyDialog').dialog({closed:true});}}]
	});
	var grid = $('#grid')._datagrid({
		checkOnSelect:false,
		selectOnCheck:false,
		frozenColumns:[[
			{field:'ck',checkbox:true,styler:function(value,row,index){
				if (row.startTime == getDateStr(0)){
					return 'background-color:#F0FEDD;';
				}else if(row.startTime == getDateStr(-1)){
					return 'background-color:#FDDBDC;';
				}else if(row.startTime == getDateStr(1)){
					return 'background-color:#71B2D8;';
				}
			}}
		]],
		remoteSort:true,
		sortName: 'startTime',
		sortOrder: 'desc',
		
		toolbar : [{
					text : '添加限制规则',
					iconCls : 'icon-add',
					handler : handler_add
				}, '-', {
					text : '删除所选',
					iconCls : 'icon-remove',
					handler : batch_del
				}, '-', {
					text : '复制所选',
					iconCls : 'icon-redo',
					handler : batch_copy
				}, '-']
	});
});

var formatter = {
	status : function(value, rowData, rowIndex) {
		if(value == 0){ return '<font color=green>已完成</font>'; } else { return '<font color=black>正常执行</font>'; }
	},
	opt : function(value, rowData, rowIndex) {
		var html = '<a class="spacing a-blue" onclick="update('+rowIndex+');" href="javascript:void(0);">修改</a>';
			html+= '<a class="spacing a-red" onclick="delet('+rowIndex+');" href="javascript:void(0);">删除</a>';
		return html;
	}
};

/*新增规则*/
function handler_add() {
	$('#adsEditForm').attr('action','ads_limit/add.html').resetForm();
	$('#adsEditDialog').dialog({closed:false,title:'新增限制规则'});
}

/*修改规则*/
function update(rowIndex) {
	$('#adsEditForm').attr('action','ads_limit/update.html').resetForm();
	var data = $('#grid').datagrid('getRows')[rowIndex];
	$('#start').val(data.startTime);
	$('#adsEditForm')._jsonToForm(data);
	$('#adsEditDialog').dialog({closed:false,title:'修改限制规则'});
}

/*删除规则*/
function delet(rowIndex) {
	$.messager.confirm('操作提示', '确定要删除该规则？', function(r){
		if (r){
			var data = $('#grid').datagrid('getRows')[rowIndex];
			$._ajaxPost('ads_limit/delete.html',{id:data.id}, function(r){
				if(r.r){$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
			});
		}
	});
}

/*批量删除规则*/
function batch_del() {
	var check = $('#grid').datagrid('getChecked');
	if(check.length > 0){
		$.messager.confirm('操作提示', '确定要删除所选规则？', function(r){
			if (r){
				var ids = new Array();
				for(var i in check){
					ids[i] = check[i].id;
				}
				$._ajaxPost('ads_limit/batchDelete.html',{idStr:ids.join('|')},function(r){
					if(r.r){$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
				});
			}
		});
	}
}

/*批量复制*/
function batch_copy(){
	var check = $('#grid').datagrid('getChecked');
	if(check.length == 0){
		$.messager.alert('操作提示','请选择您要复制的数据','info');
	}else{
		var ids = new Array();
		var adsIds = new Array();
		for(var i in check){
			ids[i] = check[i].id;
			adsIds[i] = check[i].adsInfoId;
		}
		$('#adsCopyForm').attr('action','ads_limit/batchCopy.html?idStr='+ids.join('|')+'&adsIdStr='+adsIds.join('|')).resetForm();
		$('#adsCopyDialog').dialog({closed:false,title:'复制规则'});
	}
}

function getDateStr(day){
	var date = new Date();
	date.setDate(date.getDate()+day);
	var month = date.getMonth()+1;//月
	var day = date.getDate();//日
	return  date.getFullYear() + "-" + 
			(month <=9 ? "0"+month : month) + "-" + 
			(day <=9 ? "0"+day : day) + " 00:00:00";
}


