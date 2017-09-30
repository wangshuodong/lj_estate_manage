$(function() {
	$("#startDate").val(new Date().format("yyyy-MM-dd"));
	$("#endDate").val(new Date().format("yyyy-MM-dd")); 
	
	var grid = $('#grid')._datagrid({
		showFooter:true,
		pageList:[10,20,30,40,50],
		pageSize:20,
		queryParams: $('#queryForm')._formToJson()
	});
	/*
	$('#grid').datagrid({
		onLoadSuccess:function(data){
			var ncount_val = 0;
			var acount_val = 0;
			var scount_val = 0;
			var dcount_val = 0;
			var icount_val = 0;
			var chaping_earn_val = 0;
			var expenditure_val = 0;
			for(var i = 0;i< data.rows.length;i++){
				d = data.rows[i];
				ncount_val += d.ncount;
				acount_val += d.acount;
				scount_val += d.scount;
				dcount_val += d.dcount;
				icount_val += d.icount;
				chaping_earn_val += d.chaping_earn;
				expenditure_val += d.expenditure;
			}
			$('#grid').datagrid('appendRow',{
				report_date: '汇总',
				ncount: ncount_val,
				acount: acount_val,
				scount: scount_val,
				dcount: dcount_val,
				icount: icount_val,
				chaping_earn: new Number(chaping_earn_val).toFixed(2),
				expenditure: new Number(expenditure_val).toFixed(2)
			});
		}
	});
	*/
	
	
	$('#queryButton').click(function(){
		var params = $('#queryForm')._formToJson();
		$(grid).datagrid('load',params);
	});
	
	$("#select_startDate_endDate").change(function (){
		if($(this).val()==0){
			dateKeySearch(0,0);
		}else if($(this).val()==1){
			dateKeySearch(-1,-1);
		}else if($(this).val()==2){
			dateKeySearch(-6,0);
		}else if($(this).val()==3){
			var startDate = new Date(); 
			startDate.setDate(1);
			dateKeySearch(null,0,startDate);
		}else if($(this).val()==4){
			var startDate = new Date();
			startDate.setMonth(startDate.getMonth()-1, 1);
			dateKeySearch(null,-new Date().getDate(),startDate);
		}else if($(this).val()==5){
			$("#startDate").val('');
			$("#endDate").val('');
			$("#queryButton").click();
		}
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
		}		
	};

/**
 * 时间格式
 * 
 * @param format
 * @returns
 */
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}

function dateKeySearch(start, end, startDate, endDate) {
	if(!end) end = 0;
	if(!startDate){
		startDate = new Date();
		startDate.setTime(new Date().getTime() + start * 24 * 3600 * 1000);
	}
	if(!endDate){
		endDate = new Date();
		endDate.setTime(new Date().getTime() + end * 24 * 3600 * 1000);
	}
	
	$("#startDate").val(startDate.format("yyyy-MM-dd"));
	$("#endDate").val(endDate.format("yyyy-MM-dd"));
	$("#queryButton").click();
}