var currPage;
$(function(){
	showDailyData('');
	$('#addMenu').click(function(){
		window.location.href = 'menu_add.html';
	});
});

var formatter = {
	delNull : function(value){
		if (null == value) {
			return "";
		} else {
			return value;
		};
	},
	isParent : function(value){
		if (value) {
			return "是";
		} else {
			return "否";
		}
	},
	status : function(value){
		if (value) {
			return "可用";
		} else {
			return "禁用";
		}
	}
};

function showDailyData(params)
{
	$.post('queryAll.html',params,function(result){
		$("#daily_tbody").html('');
		dataList = result.result;
		
		for(var i in dataList)
		{
			var dataObj=dataList[i];
			var tr_plan = '<tr>'+'<td align="center">'+formatter.delNull(dataObj.menuName)+'</td>'
	  				+'<td align="center">'+formatter.isParent(formatter.delNull(dataObj.isParent))+'</td>'
	  				+'<td align="center">'+formatter.delNull(dataObj.parentMenuName)+'</td>'
	  				+'<td align="center">'+formatter.delNull(dataObj.url)+'</td>'
	  				+'<td align="center">'+formatter.delNull(dataObj.type)+'</td>'
	  				+'<td align="center">'+formatter.status(formatter.delNull(dataObj.status))+'</td>'
	  				+'<td align="center">'+formatter.delNull(dataObj.remark)+'</td>'
	  				+'<td align="center"><a href="menu_modify.html?menuId='+dataObj.menuId+'">修改</a>|<a onClick="delMenu('+dataObj.menuId+');">删除</a></td>'
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
	        	currPage = newPage;
	        	showDailyData({pageNo:newPage});
	        }
	    };
		
		$("#d_totalRecord").html(result.totalRecord);
		$("#d_pageNo").html(result.pageNo);
		$("#d_totalPage").html(result.totalPage=='0' ? 1 :result.totalPage);
		$('#d_paginationId').bootstrapPaginator(options);
		
	},'json');
}

function delMenu(value) {
	if (confirm("确定删除？")) {
		$.post('delMenu.html',{menuId:value},function(result){
			alert(result.m);
			showDailyData({pageNo:currPage});
		},'json');
	}
}