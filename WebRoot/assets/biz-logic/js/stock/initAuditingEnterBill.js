function sumbitForm(type) {
	var d = $("#submitForm").serialize();
	$.ajax({
		url : "queryNoAuditingEnteringBillList.html",
		type : "post",
		dataType : "json",
		data : d + "&ser=" + type,
		success : function(data) {
			var html = "";
			var count = data.count;
			var currPage = data.currPage;
			var pageSize = data.pageSize;
			var totalPage = data.totalPage;
			

			$.each(data.list, function(i, news) {
				var code = news.code;
				var addate = news.addate;
				var createTime = news.createTime;
				var companyId= news.companyId;
				addate = addate.substr(0, 10);

				html += "<tr>";
				html += "<td>" + news.id + "</td>";
				html += "<td>" + news.code + "</td>";
				html += "<td>" + news.outsideCode + "</td>";
				html += "<td nowrap='nowrap'>" + addate + "</td>";
				html += "<td>" + news.warehouseName + "</td>";
				html += "<td>" + news.companyName + "</td>";
				html += "<td>" + news.productName + "</td>";
				html += "<td>" + news.unit + "</td>";
				html += "<td>" + news.number + "</td>";
				html += "<td style='text-align:center;' nowrap='nowrap'>";
				html = html + "<a href='auditingEnterBill.html?id=" + news.id+ "'><font size='2px'>&nbsp;审核</font></a>" + "";
				html = html + "|<a href='enterBillView.html?id=" + news.id+ "'><font size='2px'>&nbsp;详细</font></a>" + "";
				
				html += "</td>";
				html += "</tr>";

			})
			$("#d_body").html(html);
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
