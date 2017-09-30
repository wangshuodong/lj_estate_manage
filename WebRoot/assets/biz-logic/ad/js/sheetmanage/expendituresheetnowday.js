$(function() {
	var now = new Date();
	var nowStr = formatDate(now,'YYYY-MM-DD');
	var grid = $('#grid')._datagrid({
		checkOnSelect:false,
		selectOnCheck:false,
		pageList:[10,20,30,40,50],
		pageSize:20
	});
	$('#restDate').click(function(){
		$('#begin').datebox("setValue","");
		$('#end').datebox("setValue","");
	});
	$('#begin').datebox({editable:false});
	$('#end').datebox({editable:false});
	
	$('#queryButton').click(function(){
		var params = $('#queryForm')._formToJson();
		$(grid).datagrid('load',params);
	});
	var params = $('#queryForm')._formToJson();
	
	$('#sheetEditDialog').dialog({
		buttons:[{text:'保存',handler:function(){
			if(!$('#sheetEditForm').form('validate')){return;}
			$('#sheetEditForm')._ajaxForm(function(r){
				if(r.r){$('#sheetEditDialog').dialog({closed:true});$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
			});
		}},{text:'关闭',handler:function(){$('#sheetEditDialog').dialog({closed:true});}}]
	});
});
var formatter = {
		
		installcountrate: function(value, rowData, rowIndex){
			
			return value.toPercent();
		},
		dianjicountrate: function(value, rowData, rowIndex){
			
			return value.toPercent();
		},
		flow_out:function(value, rowData, rowIndex){
			return value.toFixed(2);
		},
		spending:function(value, rowData, rowIndex){
			return (value ).toFixed(2);
		},
		totalspeeding: function(value, rowData, rowIndex){
			
			return value.toFixed(2);
		},
		income:function(value, rowData, rowIndex){
			
			return value.toFixed(2);
		},
		profit:function(value, rowData, rowIndex){
			
			return value.toFixed(2);
		}
		
		
};
function showtotal(rowIndex,type){
	var rows = $('#grid').datagrid('getRows');
	var data = rows[rowIndex];
	$('#sheetEditForm').resetForm();
	$('#sheetEditForm').attr('action','speeding.html');
	$('#sheetEditForm')._jsonToForm(data);
	$('#sheetEditDialog').dialog({closed:false,title:"结算"});
}

function isNulltoNumber(value){
	if(!value){
		return	0;
	}
	return value;
}

