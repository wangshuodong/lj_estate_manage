function sumbitForm(type){
	var d = $("#submitForm"). serialize(); 
	$.ajax({
		url:"apply_data_list.html",
		type:"post",
		dataType:"json",
		data:d+"&type="+type,
		success:function(data){
			var html = "";
			var count = data.count;
			var currPage = data.currPage;
			var pageSize = data.pageSize;
			var totalPage = data.totalPage;
			if(data.listApp.length>0){
				html += "<tr>"; 
				html += "<td colspan='5' style='text-align:center;'>汇总</td>";
				html += "<td >"+data.totalICount+"</td>";
				html += "<td>"+data.totalEarn.toFixed(2)+"</td>";
				html += "<td colspan='2'></td>";
				html += "</tr>";
			}
			else{
				html += "<tr>";
				html += "<td colspan='9' style='text-align:center;font-weight:bold;'>暂无数据</td>";
				html += "</tr>";
			}
			$.each(data.listApp,function(i,app){
				var status,username,appname,qname,appkey,aid,update;
				app.status=='1,1,1'?status='启动':status='<span style=color:#979191;>暂停</span>';
				if(app.aid==null||$.trim(app.aid)==""||app.uid==null||$.trim(app.uid)==""){
					appkey="";
					update="";
				}
				else{
					appkey=app.uid+"-"+app.qid+"-"+app.aid;
					update="<a href='javascript:void(0)' onclick='changeModel(\""+app.aid+"\",\""+app.status+"\")' class='update_status'>修改</a>";
				}
				html += "<tr>";
				html += "<td>"+app.aid+"</td>";
				html += "<td>"+appkey+"</td>";
				html += "<td>"+app.username+"</td>";
				html += "<td>"+app.qname+"</td>";
				html += "<td>"+app.appname+"</td>";
				html += "<td>"+app.icount+"</td>";
				html += "<td>"+ app.earn_money.toFixed(2)+"</td>";
				html += "<td style='text-align:center;'>"+ status+"</td>";
				html += "<td style='text-align:center;'>"+update+"</td>";
				html += "</tr>";
				
			})
			$("#d_body").html(html);
			$("#totalCount").html("共有记录"+count+"条 当前第"+currPage+"页/ 总"+totalPage+"页");
			pageFun(currPage,count,pageSize); 
		}
	});
	
}
//自动加载
function changeModel(id,status){
	$('#myModal').modal('show');
	$("#appidval").val(id);
	if(status=='1,1,1'){
		$("#status").attr("checked",true);
	}
	else{
		$("#status").attr("checked",false);
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


function setSort(cm){
	var order_status = $("input[name='sortType']").val();
	if(order_status == null || $.trim(order_status) == ""){
		order_status = "desc";
		$("input[name='sortType']").val("desc");
	}
	$("input[name='sortColum']").val(cm);
	$(".asc").css("display","none");
	$(".desc").css("display","none");
	if(order_status == "desc"){
		$("#sort_"+cm+"_desc").css("display","none");
		$("#sort_"+cm+"_asc").css("display","block");
		$("input[name='sortType']").val("asc");
	}else if(order_status == "asc"){
		$("#sort_"+cm+"_asc").css("display","none");
		$("#sort_"+cm+"_desc").css("display","block");
		$("input[name='sortType']").val("desc");
	}
	sumbitForm("none");
}
setSort("aid");


