$(function(){
	$("input[name='edate']").datetimepicker({
		language:'zh-CN',
        todayBtn:1,
		autoclose:1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0,
		format:'yyyy-mm-dd',
		initialDate:new Date()
	});
	
	$("#devBalanceForm button[name='queryBtn']").click(function(){
		var parms = $("#devBalanceForm").serialize();
		queryData(parms);
	});
	
});

function status(value) {
	if (value == 0) {
		return "未付款";
	} else if (vlaue == 1) {
		return "已付款";
	} else if (value == 2) {
		return "已拒绝";
	}
}

function queryData(parms) {
	$.post('devBalance.html',parms,function(result){
		$("#daily_tbody").html('');
		dataList = result.result;
		var index = 0;
		
		for(var i in dataList)
		{
			var dataObj=dataList[i];
			var tr_plan = '<tr>'
	  				+'<td align="center">'+dataObj.sdate + '&nbsp;至&nbsp;' + dataObj.edate + '</td>'
	  				+'<td align="center">'+dataObj.earn+'</td>'
	  				+'<td align="center">'+dataObj.bonus+'</td>'
	  				+'<td align="center">'+dataObj.earnNofee+'</td>'
	  				+'<td align="center">'+status(dataObj.status)+'</td>'
					+'</tr>' ;
			$("#daily_tbody").append(tr_plan);
		}
		var options = {
	        alignment:'right',
	        currentPage: result.pageNo,
	        numberOfPages:5,
	        bootstrapMajorVersion:3,
	        totalPages: result.totalPage=='0' ? 1 :result.totalPage,
	        onPageChanged:function(event, oldPage, newPage){
	    		var edate = $("#devBalanceForm").find("input[name='edate']").val();
	        	var status = $("#devBalanceForm").find("input[name='status']").val();
	        	queryData({edate:edate,status:status,pageNo:newPage});
	        }
	    };
		
		$("#d_totalRecord").html(result.totalRecord);
		$("#d_pageNo").html(result.pageNo);
		$("#d_totalPage").html(result.totalPage=='0' ? 1 :result.totalPage);
		$('#d_paginationId').bootstrapPaginator(options);
		
	},'json');
}