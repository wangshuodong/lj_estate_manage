function sumbitForm(type) {
	var d = $("#submitForm").serialize();
	$.ajax({
		url : "myOrders.html",
		type : "post",
		dataType : "json",
		data : d + "&ser=" + type,
		success : function(data) {
			var html = "";
			var count = data.count;
			var currPage = data.currPage;
			var pageSize = data.pageSize;
			var totalPage = data.totalPage;
			var assortment = data.assortment;
	
			$.each(data.listOrdes, function(i, news) {
				var status = news.status;
				var addate = news.addate;
				addate = addate.substr(0, 10);

				var paydate = news.paydate;

				if (paydate == null) {
					paydate = "";
				} else {
					paydate = paydate.substr(0, 10);
				}

				var companyName = news.companyName;
				var code=news.code;

				html += "<tr>";
				html += "<td>" + news.id + "</td>";
				html += "<td>" + companyName + "</td>";
				html += "<td>" + code + "</td>";
				html += "<td>" + addate + "</td>";
				html += "<td><font color='red'>" + news.statusName + "</font></td>";
				html += "<td>" + news.productName + "</td>";
				html += "<td>" + news.unit + "</td>";
				html += "<td>" + news.number + "</td>";
				html += "<td>" + news.endNumber + "</td>";
				html += "<td style='text-align:center;' nowrap='nowrap'>";


				if (status == 2 && assortment == 'warehouse') {
					html = html + "<a href='factoryReceiveOrder.html?orderId="+ news.id + "&status="+status+"&currPage="+currPage+"' ><font size='2px'>接收订单</font>&nbsp;</a>";
				}

				if (status == 3 && assortment == 'warehouse') {
					html = html + "<a href='initOrderExit.html?orderId="+ news.id + "&status="+status+"&currPage="+currPage+"' ><font size='2px'>订单出库</font>&nbsp;</a>";
				}
				
				html = html + "<a href='#' onclick='printExitBill("+news.id+")' ><font size='2px'>打印</font></a>&nbsp;";
				
				
				html = html + "<a href='orderView.html?orderId=" + news.id
						+ "'><font size='2px'>&nbsp;详细</font></a>" + "";
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

function printExitBill(orderId){
	var params="";
	var reportID="saleBill";
	
	params+="&id="+orderId;

	if(reportID!=""){
		window.open("/reportJsp/showReport.jsp?raq=/"+reportID+".raq"+params);
	}
}
