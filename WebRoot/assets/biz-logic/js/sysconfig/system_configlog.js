$(function() {
	$("#configLogForm button[name='queryBtn']").click(function() {
		var startDate = $("#startdate").val();
		var endDate = $("#enddate").val();
		showDailyData({startDate:startDate,endDate:endDate});
	});
});

function showDailyData(params) {
	$.post('system_configlog.html',params,function(result){
		$("#daily_tbody").html('');
		dataList = result.result;
		
		for(var i in dataList)
		{
			var dataObj=dataList[i];
			var tr_plan = '<tr>'+'<td align="center">'+dataObj.oprationUsername+'</td>'
	  				+'<td align="center">'+dataObj.updateTime+'</td>'
	  				+'<td align="center">'+dataObj.changeContent+'</td>'
	  				+'</tr>' ;
					
			$("#daily_tbody").append(tr_plan);
		}
		
		var options = {
	        alignment:'right',
	        currentPage: result.pageNo,
	        numberOfPages:5,
	        bootstrapMajorVersion:3,
	        totalPages: result.totalPage=='0' ? 1 :result.totalPage ,
	        onPageChanged:function(event, oldPage, newPage){
	        	var startDate = $("#startDate").val();
	    		var endDate = $("#endDate").val();
	        	showDailyData({startDate:startDate,endDate:endDate,pageNo:newPage});
	        }
	    };
		
		$("#d_totalRecord").html(result.totalRecord);
		$("#d_pageNo").html(result.pageNo);
		$("#d_totalPage").html(result.totalPage=='0' ? 1 :result.totalPage);
		$('#d_paginationId').bootstrapPaginator(options);
		
	},'json');
}