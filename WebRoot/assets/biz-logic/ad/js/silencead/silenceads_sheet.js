$(function() {
	var grid = $('#grid')._datagrid({
		showFooter:true,
		pageList:[10,20,30,40,50],
		pageSize:20
	});

	$('#queryButton').click(function(){
		var params = $('#queryForm')._formToJson();
		$(grid).datagrid('load',params);
	});
});


var formatter = {
		trend:function(value, rowData, rowIndex){
			if(rowData.timediff!=null){
				return ;
			}
			var str = '<a class="a-link" href="'+window.rootPath + '/silencead/silenceads_day_sheet.html?data='+rowData.ssDate +'">'+value+'</a>';
			return str;
		},
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

