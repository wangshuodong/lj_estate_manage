
function sumbitForm(type){
	var d = $("#submitForm"). serialize();
	$.ajax({
		url:"queryProxyFactorys.html",
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

			$.each(data.list,function(i,news){
				var status=news.status;

				html += "<tr>";
				html += "<td>"+news.name+"</td>";
				html += "<td>"+news.username+"</td>";
				html += "<td>"+news.province+"</td>";
				html += "<td>"+news.city+"</td>";
				html += "<td>"+news.county+"</td>";
				html += "<td>"+news.towns+"</td>";
				html += "<td>"+news.address+"</td>";
				html += "<td>"+news.contract_people+"</td>";
				html += "<td>"+news.phone+"</td>";
				html += "<td>"+news.mobilePhone+"</td>";
				html += "<td>"+news.role_name+"</td>";
				html += "<td>"+news.createTime+"</td>"
				html += "<td style='text-align:center;' nowrap='nowrap' >";
				
				if(assortment=='manger'   || assortment=='other' ){
					html = html + "<a href='initUpdateComanyInfo.html?companyId=" + news.id+"'><font size='2px'>&nbsp;修改</font></a> ";
				}

				html = html + "<a href='company_infoView.html?companyId=" + news.id+"'><font size='2px'>&nbsp;详细</font></a> ";
				
				html += "</tr>";
				
			})
			$("#d_body").html(html);
			$("#totalCount").html("共有记录"+count+"条 当前第"+currPage+"页/ 总"+totalPage+"页");
			pageFun(currPage,count,pageSize); 
		}
	});
	
}
//自动加载
sumbitForm("none");
