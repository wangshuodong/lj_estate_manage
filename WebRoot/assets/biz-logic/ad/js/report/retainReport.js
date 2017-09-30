$(function() {
	var grid = $('#grid')._datagrid({
		pageList:[10,20,30,40,50],
		pageSize:20
	});
	$('#queryButton').click(function(){
		$(grid).datagrid('load');
	});
	
});
var formatter = {
		opt : function(value, rowData, rowIndex) {
			var str = '<a class="spacing a-blue" href="'+window.rootPath + '/report/RetainReportPie.html?retain_0='+rowData.retain_0 
			+'&retain_1=' + rowData.retain_1
			+'&retain_2=' + rowData.retain_2
			+'&retain_3=' + rowData.retain_3
			+'&retain_4=' + rowData.retain_4
			+'&retain_5=' + rowData.retain_5
			+'&retain_6=' + rowData.retain_6
			+'&retain_over_7=' + rowData.retain_over_7
			+'&date=' + rowData.a_date
			+ '">查看</a>';
			return str;
		},
		clickRate: function(value, rowData, rowIndex){
			return (rowData.click/rowData.showCount).toPercent();
		},
		installRate: function(value, rowData, rowIndex){
			return (rowData.install/rowData.showCount).toPercent();
		},
		activeRate: function(value, rowData, rowIndex){
			return (rowData.active/rowData.install).toPercent();
		},
		thousandsRate :function(value, rowData, rowIndex){
			return (rowData.profit/rowData.showCount *1000);
		},
		getChart:function(value, rowData, rowIndex){
			return '<a href="'+window.rootPath + '/report/AdsIncomeReportIndex.html?date1='+rowData.date + '">'+value+'</a>';
		},
		trend:function(value, rowData, rowIndex){
			return '<a href="'+window.rootPath + '/report/trendChart.html?date1='+rowData.date + '">' + value + '</a>';
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

