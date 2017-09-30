$(function() {
	var now = new Date();
	var nowStr = formatDate(now,'YYYY-MM-DD');
	var grid = $('#grid')._datagrid({
		checkOnSelect:false,
		selectOnCheck:false,
		pageList:[10,20,30,40,50],
		pageSize:20
	});
	$('#end').val(nowStr);
	$('#begin').val(nowStr);
	$('#queryButton').click(function(){
		if($('#begin').val()==null||$('#begin').val()==''||$('#end').val()==null||$('#end').val()==''){
			alert('查询日期不能为空！');
			
		}else{
			var params = $('#queryForm')._formToJson();
			$(grid).datagrid('load',params);
		}
		
	});
	$('#restDate').click(function(){
		$('#begin').val('');
		$('#end').val('');
	});
	
});