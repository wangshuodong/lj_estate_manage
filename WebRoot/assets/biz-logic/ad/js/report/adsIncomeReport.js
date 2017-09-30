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
			var str = '<a class="spacing a-blue" href="'+window.rootPath + '/report/AdsIncomeReportIndex.html?date='+rowData.date +'&isSettle=' + rowData.isSettle + '">详情</a>';
			return str;
		},
		clickRate: function(value, rowData, rowIndex){
			//footer行
			if(rowData.timediff!=null){
				return ;
			}
			if(rowData.showCount == 0){
				return "0.0%";
			}
			return (rowData.click/rowData.showCount).toPercent();
		},
		installRate: function(value, rowData, rowIndex){
			//footer行
			if(rowData.timediff!=null){
				return ;
			}
			if(rowData.showCount== 0){
				return "0.0%";
			}
			return (rowData.install/rowData.showCount).toPercent();
		},
		activeRate: function(value, rowData, rowIndex){
			//footer行
			if(rowData.timediff!=null){
				return;
			}
			if(rowData.install == 0){
				return "0.0%";
			}
			return (rowData.active/rowData.install).toPercent();
		},
		thousandsRate :function(value, rowData, rowIndex){
			//footer行
			if(rowData.timediff!=null){
				return;
			}
			if(rowData.showCount == 0){
				return 0;
			}
			return ((rowData.income - rowData.settleAmt - rowData.expense)/rowData.showCount *1000).toFixed(2);
		},
		trend:function(value, rowData, rowIndex){
			//footer行
			if(rowData.timediff!=null){
				return value;
			}
			return '<a class="a-link" href="'+window.rootPath + '/report/trendChart.html?date1='+rowData.date + '">' + value + '</a>';
		},
		out:function(value, rowData, rowIndex){
			//footer行
			if(rowData.timediff!=null){
				return value.toFixed(2);
			}
			//流量支出+渠道支出
			return (rowData.settleAmt + rowData.expense).toFixed(2);
		},
		profit:function(value, rowData, rowIndex){
			//footer行
			if(rowData.timediff!=null){
				return value.toFixed(2);
			}
			return (rowData.income - rowData.settleAmt - rowData.expense).toFixed(2);
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
		income:function(value, rowData, rowIndex){
			if(rowData.timediff!=null){
				return value==null?0:value.toFixed(2);
			}
			return value.toFixed(2);
		},
		expense:function(value, rowData, rowIndex){
			if(rowData.timediff!=null){
				return value==null?0:value.toFixed(2);
			}
			return value.toFixed(2);
		},
		settleAmt:function(value, rowData, rowIndex){
			if(rowData.timediff!=null){
				return value==null?0:value.toFixed(2);
			}
			return value.toFixed(2);
		},
		profitRate:function(value, rowData, rowIndex){
			if(rowData.timediff!=null){
				return;
			}
			if(rowData.settleAmt + rowData.expense == 0){
				return "0.0%";
			}
			return ((rowData.income - rowData.settleAmt - rowData.expense)/(rowData.settleAmt + rowData.expense)).toPercent();
		},
		showRate : function(value, rowData, rowIndex){
			if(rowData.timediff!=null){
				return ;
			}
			if(rowData.endLoad == null || rowData.endLoad == 0||rowData.showCount == null || rowData.showCount == 0){
				return "0.0%";
			}
			return (rowData.showCount/rowData.endLoad).toPercent();
		},
		downRate : function(value, rowData, rowIndex){
			if(rowData.timediff!=null){
				return;
			}
			if(rowData.endLoad == null || rowData.endLoad == 0||rowData.startLoad == null || rowData.startLoad == 0){
				return "0.0%";
			}
			return (rowData.endLoad/rowData.startLoad).toPercent();
		},
		activeRatio : function(value, rowData, rowIndex){
			if(rowData.timediff!=null){
				return;
			}
			if(rowData.active == null || rowData.active == 0||rowData.click == null || rowData.click == 0){
				return "0.0%";
			}
			return ( rowData.active / rowData.click ).toPercent();
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
