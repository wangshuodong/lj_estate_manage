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
//		opt : function(value, rowData, rowIndex) {
//			//footer行
//			if(rowData.timediff!=null){
//				return ;
//			}
//			var str = '<a class="spacing a-blue" href="'+window.rootPath + '/report/AdsIncomeReportIndex.html?date='+rowData.date +'&isSettle=' + rowData.isSettle + '">详情</a>';
//			return str;
//		},
		trend:function(value, rowData, rowIndex){
			//footer行
			if(rowData.timediff!=null){
				return value;
			}
			return value;
		},
		startLoad:function(value, rowData, rowIndex){
			if(rowData.timediff!=null){
				return value==null?0:value.toFixed(1);
			}
			return value;
		},
		endLoad:function(value, rowData, rowIndex){
			if(rowData.timediff!=null){
				return value==null?0:value.toFixed(1);
			}
			return value;
		},
		newimei:function(value, rowData, rowIndex){
			if(rowData.timediff!=null){
				return value==null?0:value.toFixed(1);
			}
			return value;
		},
		showCount:function(value, rowData, rowIndex){
			if(rowData.timediff!=null){
				return value==null?0:value.toFixed(1);
			}
			return value;
		},
		click:function(value, rowData, rowIndex){
			if(rowData.timediff!=null){
				return value==null?0:value.toFixed(1);
			}
			return value;
		},
		install:function(value, rowData, rowIndex){
			if(rowData.timediff!=null){
				return value==null?0:value.toFixed(1);
			}
			return value;
		},
		active:function(value, rowData, rowIndex){
			if(rowData.timediff!=null){
				return value==null?0:value.toFixed(1);
			}
			return value;
		},
		installcountrate : function(value, rowData, rowIndex) {
			if(rowData.timediff!=null){
				return ;
			}
			if(rowData.showCount== 0){
				return "0.0%";
			}
			return (rowData.installCount/rowData.showCount).toPercent();
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

function showDetails(date){
	
	
}
