function sumbitForm(type) {
	var d = $("#submitForm").serialize();
	$.ajax({
		url : "queryCurrentAcountCollect.html",
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
			var dqsaleCode = data.dqsaleCode;
			var listSales = data.listSales;
			var collect = data.collect;
			
			$("#dqsale").empty();
			$("#dqsale").append("<option value=''>全部</option>");
			
			$.each(data.listSales, function(i, companyInfo) {
				var departmentCode = companyInfo.departmentCode;
				var departmentName = companyInfo.departmentName;
				$("#dqsale").append("<option value='" + departmentCode + "'>"+ departmentName + "</option>");
			})
			
			
			var countdqsale = $("#dqsale option").length;
	
			for ( var i = 0; i < countdqsale; i++) {
				if ($("#dqsale").get(0).options[i].value == dqsaleCode) {
					$("#dqsale").get(0).options[i].selected = true;
					break;
				}
			}

			var receMoney = 0;
			var payMoney = 0;
			var advancePayment = 0;
			
			if (collect != null) {
				receMoney = collect.receMoney;
				payMoney = collect.payMoney;
				advancePayment = collect.advancePayment;
			}

			html += "<tr>";
			html += "<td></td>";
			html += "<td>汇总:</td>";
			html += "<td></td>";
			html += "<td><font color='red'>" + receMoney + "</td>";
			html += "<td><font color='red'>" + payMoney + "</td>";
			html += "<td><font color='red'>" + advancePayment + "</td>";
			html += "<td></td>";
			html += "</tr>";
			
			$.each(data.list, function(i, news) {
				html += "<tr>";
				html += "<td>" + news.month + "</td>";
				html += "<td>" + news.companyName + "</td>";
				html += "<td>" + news.startMoney + "</td>";
				html += "<td>" + news.receMoney + "</td>";
				html += "<td>" + news.payMoney + "</td>";
				html += "<td>" + news.advancePayment + "</td>";
				html += "<td>" + news.endMoney + "</td>";
				html += "</tr>";

			});
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
