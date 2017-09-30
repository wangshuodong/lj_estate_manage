
function sumbitForm(type){
	var d = $("#submitForm"). serialize(); 
	$.ajax({
		url:"querySales.html",
		type:"post",
		dataType:"json",
		data:d+"&ser="+type,
		success:function(data){
			var html = "";
			var count = data.count;
			var currPage = data.currPage;
			var pageSize = data.pageSize;
			var totalPage = data.totalPage;
			var assortment=data.assortment;
			var dqsaleCode = data.dqsaleCode;
			var listSales = data.listSales;
			
			
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
			
			if (assortment == 'djsale' || assortment == 'dqsale' ) {
				$("#dqsaleDiv").text('');
			}
			
			$.each(data.list,function(i,news){
				var status=news.status;

				html += "<tr>";
				html += "<td>"+news.name+"</td>";
				html += "<td>"+news.susername+"</td>";
				html += "<td>"+news.province+"</td>";
				html += "<td>"+news.city+"</td>";
				html += "<td>"+news.county+"</td>";
				html += "<td>"+news.towns+"</td>";
				html += "<td>"+news.address+"</td>";
				html += "<td>"+news.contract_people+"</td>";
				html += "<td>"+news.phone+"</td>";
				html += "<td>"+news.mobilePhone+"</td>";
				html += "<td>"+news.role_name+"</td>";
				html += "<td style='text-align:center;' >";
				html = html + "<a href='company_infoView.html?companyId="
				+ news.parentCompanyId + "' ><font size='2px'>"+news.parentCompanyName+"</font></a>" + "";
				html += "</td>";
				html += "<td>"+news.departmentName+"</td>";
				html += "<td>"+news.createTime+"</td>"
				
				html += "<td style='text-align:center;' nowrap='nowrap' >";
				
				if(assortment=='manger'  ){
					html = html + "<a href='#' onclick='deleteCompany_info("+news.id+")' ><font size='2px'>作废</font></a>";
				}
				
				if(assortment=='manger'   || assortment=='other' ){
					html = html + "<a href='initUpdateComanyInfo.html?companyId=" + news.id+"'><font size='2px'>&nbsp;修改</font></a> ";
				}

				html = html + "<a href='company_infoView.html?companyId=" + news.id+"'><font size='2px'>&nbsp;详细</font></a> ";
				
				html += "</td>";
				
				html += "</tr>";
				
			})
			$("#d_body").html(html);
			$("#totalCount").html("共有记录"+count+"条 当前第"+currPage+"页/ 总"+totalPage+"页");
			pageFun(currPage,count,pageSize); 
		}
	});
	
}

function deleteCompany_info(orderId){
	if(confirm("确认要作废该经销商？")){
		window.location.href="deleteCompanyInfo.html?companyId="+orderId;
	}
}
//自动加载
sumbitForm("none");
