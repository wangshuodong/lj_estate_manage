$(function() {
	$("#authorityRoleForm button[name='queryBtn']").click(function() {
		var username = $("#userName").val();
		var rolename = $("#rolename").val();
		var menuname = $("#menuname").val();
		
		showDailyData({username:username,rolename:rolename,menuname:menuname});
	});
});

var formatter = {
		delNull : function(value) {
			if (null == value) {
				return "";
			} else {
				return value;
			};
		}
	};

function showDailyData(params) {
	$.post('authorityRole.html',params,function(result){
		$("#daily_tbody").html('');
		dataList = result.result;
		
		for(var i in dataList)
		{
			var dataObj=dataList[i];
			var tr_plan = '<tr>'+'<td align="center">'+formatter.delNull(dataObj.username)+'</td>'
	  				+'<td align="center">'+formatter.delNull(dataObj.rolename)+'</td>'
	  				+'<td align="center">'+formatter.delNull(dataObj.menuname)+'</td>'
	  				+'<td align="center">'+formatter.delNull(dataObj.parentMenu)+'</td>'
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
	        	var username = $("#userName").val();
	    		var rolename = $("#rolename").val();
	    		var menuname = $("#menuname").val();
	        	showDailyData({username:username,rolename:rolename,menuname:menuname,pageNo:newPage});
	        }
	    };
		
		$("#d_totalRecord").html(result.totalRecord);
		$("#d_pageNo").html(result.pageNo);
		$("#d_totalPage").html(result.totalPage=='0' ? 1 :result.totalPage);
		$('#d_paginationId').bootstrapPaginator(options);
		
	},'json');
}