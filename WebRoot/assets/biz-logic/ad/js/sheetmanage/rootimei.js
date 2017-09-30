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
