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
		srate:function(value, rowData, rowIndex){
			if(rowData.acount!=null || rowData.acount!=0){
				return ;
			}
			if(rowData.scount == null || rowData.scount == 0 || rowData.acount == null || rowData.acount == 0){
				return "0.0%";
			}
			return (rowData.scount/rowData.acount).toPercent();
		},
		drate : function(value, rowData, rowIndex){
			if(rowData.acount!=null || rowData.acount!=0){
				return ;
			}
			if(rowData.dcount == null || rowData.dcount == 0 || rowData.acount == null || rowData.acount == 0){
				return "0.0%";
			}
			return (rowData.dcount/rowData.acount).toPercent();
		},
		date:function(value, rowData, rowIndex){
			return '<a class="a-link" href="'+window.rootPath+'/menus/lock_app_statistics_detail.html?date='+value+'">'+value+'</a>';
		}
	};

