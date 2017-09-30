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
			var collect = data.collect;
			var userCompanyId = data.userCompanyId;
			var listSales = data.listSales;
			var countSize = data.countSize;
			var dqsaleCode = data.dqsaleCode;
			var status = data.status;
			var productId = data.productId;
			
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
			
			$("#productId").empty();
			$("#productId").append("<option value=''>全部</option>");
			
			$.each(data.products, function(i, product) {
				var productId = product.id;
				var productName = product.name;
				$("#productId").append("<option value='" + productId + "'>"+ productName + "</option>");
			})

			var countProduct = $("#productId option").length;
	
			for ( var i = 0; i < countProduct; i++) {
				if ($("#productId").get(0).options[i].value == productId) {
					$("#productId").get(0).options[i].selected = true;
					break;
				}
			}

			if (assortment == 'manger' || assortment == 'financial' || assortment == 'other' ) {
				$("#saleBill").attr('disabled', false);
			}
			else{
				$("#saleBillDiv").text('');
			}
			
			if (assortment == 'djsale') {
				$("#dqsaleDiv").text('');
				$("#djsaleDiv").text('');
			}
			
			if (assortment == 'dqsale' || assortment == 'proxyFactory') {
				$("#dqsaleDiv").text('');
			}
			
			if (assortment == 'customer'  || assortment == 'proxyFactory') {
				$("#dqsaleDiv").text('');
				$("#djsaleDiv").text('');
				$("#provinceDiv").text('');
				$("#cityDiv").text('');
			}
		
			var money = 0;
			var number = 0;
			var payMoney = 0;
			var endNumber = 0;

			if (collect != null) {
				money = collect.money;
				number = collect.number;
				payMoney = collect.payMoney;
				endNumber = collect.endNumber;
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
			html += "<td><font color='red'>" + endNumber + "</td>";
			html += "<td></td>";
			html += "<td><font color='red'>" + money + "</td>";
			html += "<td></td>";
			html += "<td></td>";
			html += "<td><font color='red'>" + payMoney + "</td>";
			html += "<td></td>";
			html += "</tr>";

			$.each(data.listOrdes, function(i, news) {
				var status = news.status;
				var addate = news.addate;
				var createTime = news.createTime;
				var companyId= news.companyId;
				
				addate = addate.substr(0, 10);
				
				var paydate = news.paydate;

				if (paydate == null) {
					paydate = "";
				} else {
					paydate = paydate.substr(0, 10);
				}

				var companyName = news.companyName;

				html += "<tr>";
				html += "<td>" + news.id + "</td>";
				html += "<td>" + news.code + "</td>";
				html += "<td>" + companyName + "</td>";
				html += "<td nowrap='nowrap'>" + addate + "</td>";
				html += "<td nowrap='nowrap'>" + createTime + "</td>";
				html += "<td ><font color='red'>" + news.statusName
						+ "</font></td>";
				html += "<td nowrap='nowrap'>" + news.productName + "</td>";
				html += "<td>" + news.unit + "</td>";
				html += "<td>" + news.number + "</td>";
				html += "<td>" + news.endNumber + "</td>";
				html += "<td>" + news.price + "</td>";
				html += "<td>" + news.money + "</td>";
				html += "<td nowrap='nowrap'>" + news.no + "</td>";
				html += "<td nowrap='nowrap'>" + paydate + "</td>";
				html += "<td>" + news.payMoney + "</td>";
				html += "<td style='text-align:center;' nowrap='nowrap'>";

				if (status == 0 && assortment == 'customer') {
					html = html + "<a href='initOrderPay.html?orderId="
							+ news.id
							+ "' ><font size='2px'>付款&nbsp;</font></a>" + "";
				}

				if (status == 0 && assortment == 'proxyFactory') {
					html = html + "<a href='initOrderPay.html?orderId="
							+ news.id
							+ "' ><font size='2px'>付款&nbsp;</font></a>" + "";
				}
				
				if (status == 0 && assortment == 'djsale' && companyId==userCompanyId) {
					html = html + "<a href='initOrderPay.html?orderId="
							+ news.id
							+ "' ><font size='2px'>付款&nbsp;</font></a>" + "";
				}
				
				if (status == 0 &&  ( assortment == 'manger') ) {
					html = html + "<a href='#' onclick='deleteOrder("+news.id+")' ><font size='2px'>作废</font></a>";
				}
				
				if (status == 0 && assortment == 'djsale' && companyId==userCompanyId) {
					html = html + "<a href='#' onclick='deleteOrder("+news.id+")' ><font size='2px'>作废</font></a>";
				}
				
				if (status == 0 && assortment == 'customer' && companyId==userCompanyId) {
					html = html + "<a href='#' onclick='deleteOrder("+news.id+")' ><font size='2px'>作废</font></a>";
				}
				
				if (status == 1 && assortment == 'financial') {
					html = html + "<a href='auditingOrder.html?orderId="+ news.id + "&status="+status+"&currPage="+currPage+"' ><font size='2px'>审核</font></a>";
				}
		
				if (status == 2 && assortment == 'warehouse') {
					html = html + "<a href='factoryReceiveOrder.html?orderId="+ news.id + "&status="+status+"&currPage="+currPage+"' ><font size='2px'>接收订单</font></a>";
				}

				if (status == 3 && assortment == 'warehouse') {
					html = html + "<a href='initOrderExit.html?orderId="
							+ news.id + "' ><font size='2px'>订单出库</font></a>"
							+ "";
				}

				if (status == 4 && assortment == 'customer') {
					html = html + "<a href='receivePackage.html?orderId="
							+ news.id + "' ><font size='2px'>收货</font></a>"
							+ "";
				}
				
				if (status == 4 && assortment == 'djsale' &&  companyId==userCompanyId ) {
					html = html + "<a href='receivePackage.html?orderId="
							+ news.id + "' ><font size='2px'>收货</font></a>"
							+ "";
				}
				
				if (status == 0 &&  ( assortment == 'manger' || assortment == 'financial'  ) ) {
					html = html + "&nbsp;<a href='initUpdateOrderPrice.html?orderId="+ news.id + "' ><font size='2px'>价格修改</font></a> ";
				}

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

function deleteOrder(orderId){
	if(confirm("确认要作废该笔订单？")){
		window.location.href="deleteOrder.html?orderId="+orderId;
	}
}

function ordersDetail(){
	var startdate = $("#startdate").val();
	var enddate = $("#enddate").val();
	var departmentCode = $("#departmentCode").val();
	var djsale = $("#djsale").val();
	var name = $("#name").val();
	var productId = $("#productId").val();
	var dqsale = $("#dqsale").val();
	
	var params = "";
	var reportID = "orders";
	
	params += "&departmentCode=" + departmentCode;
	
	if (startdate != null && startdate!='' ) {
		params += "&startdate=" + startdate;
	}
	
	if (enddate != null && enddate!='') {
		params += "&enddate=" + enddate;
	}
	
	if (djsale != null && djsale!='') {
		params += "&djsaleName=" + djsale;
	}
	
	if (name != null && name!='') {
		params += "&customerName=" + name;
	}
	
	if (productId != null && productId!='') {
		params += "&productId=" + productId;
	}
	
	if (dqsale != null && dqsale!='') {
		params += "&dqsaleName=" + dqsale;
	}

	if (reportID != "") {
		window.open("/reportJsp/showReport.jsp?raq=/" + reportID+ ".raq" + params);
	}
}

// 自动加载
sumbitForm("none");
