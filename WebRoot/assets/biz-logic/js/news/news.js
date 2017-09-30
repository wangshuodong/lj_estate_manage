
function sumbitForm(type){
	var d = $("#submitForm"). serialize(); 
	$.ajax({
		url:"news_data.html",
		type:"post",
		dataType:"json",
		data:d+"&ser="+type,
		success:function(data){
			var html = "";
			var count = data.count;
			var currPage = data.currPage;
			var pageSize = data.pageSize;
			var totalPage = data.totalPage;
			$.each(data.listNews,function(i,news){
				var typename=news.type==1?"公告":"新闻";
				html += "<tr>";
				html += "<td>"+news.date+"</td>";
				html += "<td><a target='_blank' title='点击预览 公告/新闻' href='news_detail.html?id="+news.id+"&type=gonggao&check=news'>"+news.title+"</a></td>";
				html += "<td>"+typename+"</td>";
				html += "<td>"+news.createTime+"</td>";
				html += "<td style='text-align:center;'>" +
						"<a href='javascript:void(0)' onclick='delnews(\""+news.id+"\",\""+news.title+"\")'>删除</a> | " +
						"<a href='news_au.html?id="+news.id+"' >修改</a>" +
						"</td>";
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
function delnews(id,title){
	if(confirm("确定删除公告名称为【"+title+"】的公告？")){
		$.ajax({
			url:"deletenews.html",
			type:"post",
			dataType:"json",
			data:"id="+id,
			success:function(data){
				if(data.result>0){
					alert("操作成功！");
					sumbitForm("none");
					$('#myModal').modal('hide');
				}
				else{
					alert("操作失败！");
				}
				
			}
		});
	}
}
$(function(){
	$("#btn-updatestatus").click(function(){
		$.ajax({
			url:"update_app_status.html",
			type:"post",
			dataType:"json",
			data:"appid="+$("#appidval").val()+"&status="+$('input:checkbox[name="status"]:checked').val(),
			success:function(data){
				if(data.result>0){
					alert("操作成功！");
					sumbitForm("none");
					$('#myModal').modal('hide');
				}
				else{
					alert("操作失败！");
				}
				
			}
		});
	});
});




