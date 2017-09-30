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
		opt : function(value, rowData, rowIndex) {
			//footer行
			if(rowData.timediff!=null){
				return ;
			}
			return '<a class="spacing a-blue" href="/sheet/ads_expendituresheet_detail.html?data='+rowData.a_date+'">详情</a>';
		},
		installcountrate : function(value, rowData, rowIndex) {
			if(rowData.timediff!=null){
				return ;
			}
			if(rowData.showCount== 0){
				return "0.0%";
			}
			return (rowData.installCount/rowData.showCount).toPercent();
		},
		newimeirate : function(value, rowData, rowIndex) {
			if(rowData.timediff!=null){
				return ;
			}
			if(rowData.activeCount== 0){
				return "0.00";
			}
			return ((rowData.profit/rowData.activeCount)*1000).toFixed(2);
		},
		out:function(value, rowData, rowIndex){
				return value==null?0:value.toFixed(2);
		},
		profit:function(value, rowData, rowIndex){
			return value==null?0:value.toFixed(2);
		},
		income:function(value, rowData, rowIndex){
			return value==null?0:value.toFixed(2);
		},
		show:function(value, rowData, rowIndex){
			if(rowData.timediff!=null){
				return value.toFixed(1);
			}else{
				return value;
			}
		},
		click:function(value, rowData, rowIndex){
			if(rowData.timediff!=null){
				return value.toFixed(1);
			}else{
				return value;
			}
		},
		install:function(value, rowData, rowIndex){
			if(rowData.timediff!=null){
				return value.toFixed(1);
			}else{
				return value;
			}
		},
		imei:function(value, rowData, rowIndex){
			if(rowData.timediff!=null){
				return value.toFixed(1);
			}else{
				return value;
			}
		},
		active : function(value, rowData, rowIndex){
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

