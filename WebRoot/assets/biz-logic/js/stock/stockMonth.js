function sumbitForm(type) {
	var d = $("#submitForm").serialize();
	$.ajax({
		url : "queryStockMonth.html",
		type : "post",
		dataType : "json",
		data : d + "&ser=" + type,
		success : function(data) {
			var html = "";
			var count = data.count;
			var currPage = data.currPage;
			var pageSize = data.pageSize;
			var totalPage = data.totalPage;
			var collect = data.collect;

			
			var enterNumber = 0;
			var outNumber = 0;
			
			if (collect != null) {
				enterNumber = collect.enterNumber;
				outNumber = collect.outNumber;
			}

			html += "<tr>";
			html += "<td></td>";
			html += "<td>汇总:</td>";
			html += "<td></td>";
			html += "<td></td>";
			html += "<td></td>";
			html += "<td><font color='red'>" + enterNumber + "</td>";
			html += "<td><font color='red'>" + outNumber + "</td>";
			html += "<td></td>";
			html += "</tr>";
			

			$.each(data.list, function(i, news) {
				
				html += "<tr>";
				html += "<td>" + news.month + "</td>";
				html += "<td>" + news.warehouseName + "</td>";
				html += "<td>" + news.productName + "</td>";
				html += "<td>" + news.unit + "</td>";
				html += "<td>" + news.startNumber + "</td>";
				html += "<td>" + news.enterNumber + "</td>";
				html += "<td>" + news.outNumber + "</td>";
				html += "<td>" + news.endNumber + "</td>";
				html += "</td>";
				html += "</tr>";

			})
			$("#d_body").html(html);
			$("#totalCount").html(
					"共有记录" + count + "条 当前第" + currPage + "页/ 总" + totalPage
							+ "页");
			pageFun(currPage, count, pageSize);
		}
	});

}
// 自动加载
sumbitForm("none");
