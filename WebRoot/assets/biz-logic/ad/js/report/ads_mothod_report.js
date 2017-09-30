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
		mothod : function(value, rowData, rowIndex) {
	        if(value == 0){ return '<font color=green>插屏</font>'; }
	        else if(value == 1){ return '<font color=blue>广告墙</font>'; }
	        else if(value == 2){ return '<font color=red>一键安装</font>'; }
	        else{ return '<font>合计</font>'; }
	    }		
	};
