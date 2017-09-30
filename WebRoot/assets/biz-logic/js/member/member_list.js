function sumbitForm(type){
	var d = $("#submitForm"). serialize(); 
	$.ajax({
		url:"member_list.do",
		type:"post",
		dataType:"json",
		data:d+"&type="+type,
		success:function(data){
			var html = "";
			var count = data.count;
			var currPage = data.currPage;
			var pageSize = data.pageSize;
			if(data.list == null || data.list.length==0){
				html += "<tr>";
				html += "<td colspan='9' style='text-align:center;font-weight:bold;'>暂无数据</td>";
				html += "</tr>";
			}else{
				$.each(data.list,function(i,p){
					html += "<tr>";
					html += "<td>"+i+"</td>";
					html += "<td>"+p.level+"</td>";
					html += "<td>"+p.desc+"</td>";
					html += "<td>"+p.rate+"</td>";
					html += "<td style='text-align:center;'><a href=\"member_update.html?id="+p.id+"\">修改</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"javascript:;\" onclick=\"delById("+p.id+")\">删除</a></td>";
					html += "</tr>";
					
				})
			}
			$("#d_body").html(html);
			$("#totalCount").html("共有记录"+count+"条 ");
			pageFun(currPage,count,pageSize);
		}
	});
	
}

function jumpToAdd(){
	window.location.href="member_add.html";
}
function delById(id){
	$.ajax({
		url:"member_delete.do",
		type:"post",
		dataType:"json",
		data:{
			id:id
		},
		beforeSend:function(){
			$("#loading").modal('show');
		},
		complete:function(){
			$("#loading").modal('hide');
		},
		success:function(data){
			if(data.status == true){
				alert(data.msg);
				location.reload();
			}else{
				alert(data.msg);
			}
		}
	})
}
$(document).ready(function(){
	sumbitForm("none");
})