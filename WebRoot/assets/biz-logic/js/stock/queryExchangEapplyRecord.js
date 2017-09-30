function sumbitForm(type) {
	var d = $("#submitForm").serialize();
	$.ajax({
		url : "queryExchangEapplyRecordList.html",
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

		
			var number = 0;

			if (collect != null) {
				number = collect.number;
			}

			html += "<tr>";
			html += "<td></td>";
			html += "<td></td>";
			html += "<td>汇总:</td>";
			html += "<td></td>";
			html += "<td></td>";
			html += "<td></td>";
			html += "<td></td>";
			html += "<td></td>";
			
			html += "<td><font color='red'>" + number + "</td>";
			html += "<td></td>";
			html += "<td></td>";
			html += "<td></td>";
			html += "</tr>";

			$.each(data.list, function(i, news) {
				var code = news.code;
				var addate = news.addate;
				var createTime = news.createTime;
				var companyId= news.companyId;
				var checked=news.checked;
				var status=news.status;
				
				var str=""
				
				addate = addate.substr(0, 10);

				html += "<tr>";
				html += "<td>" + news.id + "</td>";
				html += "<td>" + news.code + "</td>";
				html += "<td>" + news.outsideCode + "</td>";
				html += "<td nowrap='nowrap'>" + addate + "</td>";
				html += "<td>" + news.companyName + "</td>";
				html += "<td>" + news.productName + "</td>";
				html += "<td>" + news.newproductName + "</td>";
				html += "<td>" + news.unit + "</td>";
				html += "<td>" + news.number + "</td>";
				html += "<td>" + news.remark + "</td>";
				html += "<td>" + news.statusName + "</td>";
				html += "<td style='text-align:center;' nowrap='nowrap'>";
				html = html + "<a href='enterBillView.html?id=" + news.id+ "'><font size='2px'>&nbsp;</font></a>" + "";
				if (status == 0) {
					html = html + "<a href='exchangEapplyRecordInit.html?id=" + news.id+ "'><font size='2px'>&nbsp;换货</font></a>" + "";
				}
				if (status == 1) {
					html = html + "..." + "";
				}
				
			
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

function deleteOrder(orderId){
	if(confirm("确认要作废该笔订单？")){
		window.location.href="deleteOrder.html?orderId="+orderId;
	}
}

function ordersDetail(){
	var startdate = $("#startdate").val();
	var enddate = $("#enddate").val();
	var departmentCode = $("#departmentCode").val();
	var params = "";
	var reportID = "orders";
	
	params += "&code=" + departmentCode;
	
	if (startdate != null && startdate!='' ) {
		params += "&startdate=" + startdate;
	}
	
	if (enddate != null && enddate!='') {
		params += "&enddate=" + enddate;
	}

	if (reportID != "") {
		window.open("/reportJsp/showReport.jsp?raq=/" + reportID+ ".raq" + params);
	}
}

// 自动加载
sumbitForm("none");
