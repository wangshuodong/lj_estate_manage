$(function(){
	$("input[name='startDate']").datetimepicker({
		language:'zh-CN',
	    todayBtn:1,
		autoclose:1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0,
		format:'yyyy-mm-dd',
		initialDate:new Date()
	}).on('changeDate',function(ev){
		var startdate = $("input[name='startDate']:visible").val();
		$("input[name='endDate']:visible").datetimepicker('setStartDate', startdate);
	});

	$("input[name='endDate']").datetimepicker({
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

function queryData(parms) {
	$.post('countEarn.html',parms,function(result){
		$("#daily_tbody").html('');
		dataList = result.result;
		var index = 0;
		
		var sumTr = '<tr><td>总计</td>'
			+'<td>'+result.summary.icount+'</td>'
			+'<td>'+result.summary.earnMoney+'</td>'
			+'</tr>';
		$("#daily_tbody").append(sumTr);
		
		for(var i in dataList)
		{
			var dataObj=dataList[i];
			var tr_plan = '<tr>'
	  				+'<td align="center">'+dataObj.edate + '</td>'
	  				+'<td align="center">'+dataObj.icount+'</td>'
	  				+'<td align="center">'+dataObj.earnMoney+'</td>'
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
	    		var startDate = $("#devBalanceForm").find("input[name='startDate']").val();
	        	var endDate = $("#devBalanceForm").find("input[name='endDate']").val();
	        	queryData({startDate:startDate,endDate:endDate,pageNo:newPage});
	        }
	    };
		
		$("#d_totalRecord").html(result.totalRecord);
		$("#d_pageNo").html(result.pageNo);
		$("#d_totalPage").html(result.totalPage=='0' ? 1 :result.totalPage);
		$('#d_paginationId').bootstrapPaginator(options);
		
	},'json');
}