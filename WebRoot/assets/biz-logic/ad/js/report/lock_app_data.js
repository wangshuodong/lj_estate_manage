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
		is_cp: function(value, rowData, rowIndex) {
			return value=='1' ? "已开启" : '停用';
		},
		opt : function(value, rowData, rowIndex) {
			var optButton = rowData.is_cp=='1' ? "停用" : '启用';
			var html = '<a class="spacing a-blue" onclick="updCpStatus('+rowIndex+');" href="javascript:void(0);">'+optButton+'</a>';
			return html;
		}
	};
function updCpStatus(rowIndex) {
	var param = $('#grid').datagrid('getRows')[rowIndex];
	$.ajax({
		async:false,
		dataType:"json",
		url:"lockapp/updCpStatus.html",
		data:param,
		success:function(data){
			if(data == 'true'){
				var params = $('#queryForm')._formToJson();
				$(grid).datagrid('load',params);
			}else{
				alert("操作失败");
			}
		}
	});
	

}
