$(function() {
	var grid = $('#grid')._datagrid({
		showFooter:true,
		pageList:[10,20,30,40,50],
		pageSize:20,
		queryParams:$('#queryForm')._formToJson()
	});
	$('#queryButton').click(function(){
		var params = $('#queryForm')._formToJson();
		$(grid).datagrid('load',params);
	});
});

var formatter = {
		
		out: function(value, rowData, rowIndex){
			return ((rowData.settle_amt==null?0:rowData.settle_amt)+(rowData.flow_out==null?0:rowData.flow_out)).toFixed(2);
		},
		profit :function(value, rowData, rowIndex){
			return ((rowData.income==null?0:rowData.income) -(rowData.settle_amt==null?0:rowData.settle_amt)-(rowData.flow_out==null?0:rowData.flow_out)).toFixed(2);
		},
		_in:function(value, rowData, rowIndex){
			return value.toFixed(2);
		}
	};

function mum_fmt(value){
	var len = value.toString().length;
	var star = len%3;
	var retvalue=value.toString().substr(0,star);
	if(retvalue.length!=0){
		retvalue +=",";
	}
	for(var i=1;i<=len/3;i++){
		retvalue =retvalue+value.toString().substr(star +(i-1)*3,3)+",";
	}
	return retvalue.substr(0,retvalue.length-1);
}
