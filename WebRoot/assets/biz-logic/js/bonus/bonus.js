
function sumbitForm(type){
	if(type == null || type == "none"){
		$("input[name='username']").val("");
		$("input[name='startdate']").val("");
		$("input[name='enddate']").val("");
		$("select[name='type']").val("all");
		$("select[name='status']").val("-1");
	}
	var d = $("#submitForm"). serialize(); 
	$.ajax({
		url:"bonus_data.html",
		type:"post",
		dataType:"json",
		data:d+"&ser="+type,
		success:function(data){
			var html = "";
			var count = data.count;
			var currPage = data.currPage;
			var pageSize = data.pageSize;
			var totalPage = data.totalPage;
			$.each(data.listBonus,function(i,bonus){
				var type="",status=(bonus.status)==0?"未结算":"已结算";
				if(bonus.type=="sign"){type="注册";}
				else if(bonus.type=="re_sign"){type="推荐注册";}
				else if(bonus.type=="party"){type="活动";}
				html += "<tr>";
				html += "<td>"+bonus.id+"</td>";
				html += "<td>"+bonus.username+"</td>";
				html += "<td>"+bonus.taxBonus+"</td>";
				html += "<td>"+type+"</td>";
				html += "<td>"+bonus.description+"</td>";
				html += "<td>"+status+"</td>";
				html += "<td>"+bonus.createTime+"</td>";
				html += "<td style='text-align:center;'>" +
						"<a href='javascript:void(0)' onclick='delbonus(\""+bonus.id+"\")'>删除</a> | " +
						"<a href='bonus_au.html?id="+bonus.id+"' >修改</a>" +
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
function delbonus(id){
	if(confirm("确定删除ID为【"+id+"】的奖金信息？")){
		$.ajax({
			url:"delete_bonus.html",
			type:"post",
			dataType:"json",
			data:"id="+id,
			success:function(data){
				if(data.result>0){
					alert("操作成功！");
					sumbitForm("none");
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




