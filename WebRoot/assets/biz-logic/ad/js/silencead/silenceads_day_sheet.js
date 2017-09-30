$(function() {
	var grid = $('#grid')._datagrid({
		checkOnSelect:false,
		selectOnCheck:false,
		pageList:[10,20,30,40,50],
		pageSize:20
	});
	var params = $('#queryForm')._formToJson();
	$('#queryButton').click(function(){
		var params = $('#queryForm')._formToJson();
		$(grid).datagrid('load',params);
	});

	

});
var formatter = {
		
		beginLoad:function(value, rowData, rowIndex){
			if(rowData.timediff!=null){
				return value.toFixed(1);
			}else{
				return value;
			}
		},
		endLoad:function(value, rowData, rowIndex){
			if(rowData.timediff!=null){
				return value.toFixed(1);
			}else{
				return value;
			}
		},
		install : function(value, rowData, rowIndex){
			if(rowData.timediff!=null){
				return value.toFixed(1);
			}else{
				return value;
			}
		}
		
		
};


function isNulltoNumber(value){
	if(!value){
		return	0;
	}
	return value;
}

