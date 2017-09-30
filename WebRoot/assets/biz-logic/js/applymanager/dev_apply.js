var modal_body_html="";
function sumbitForm(type){
	var d = $("#submitForm"). serialize(); 
	$.ajax({
		url:"dev_apply_data_list.html",
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
				html += "<td colspan='4' style='text-align:center;'>汇总</td>";
				html += "<td >"+data.totalICount+"</td>";
				html += "<td>"+data.totalAcount+"</td>";
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
				var status;
				if(app.status=='1,1,1'){status='正常';}else{status='<span style=color:#979191;>暂停</span>';}
				html += "<tr>";
				html += "<td>"+app.aid+"</td>";
				html += "<td>"+app.uid+"-"+app.qid+"-"+app.aid+"</td>";
				html += "<td>"+app.qname+"</td>";
				html += "<td>"+app.appname+"</td>";
				html += "<td>"+app.icount+"</td>";
				html += "<td>"+app.acount+"</td>";
				html += "<td>"+ app.earn_money.toFixed(2)+"</td>";
				html += "<td style='text-align:center;'>"+ status+"</td>";
				html += "<td style='text-align:center;color:#428bca;'><a href='javascript:void(0)' onclick='changeModel(\""+app.aid+"\",\""+app.status+"\")' class='update_status'>启动/暂停</a>" +
						" | <a href='dev_apply_au.html?id="+app.aid+"'  class='update_mess'>修改</a>"+
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
function changeModel(id,status){
	$('#myModal').modal('show');
	$("#appidval").val(id);
	$(".mb-input-checked div span").html("【&nbsp;"+id+"&nbsp;】&nbsp;&nbsp;").css({"font-size": "16px"});
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
			url:"dev_update_app_status.html",
			type:"post",
			dataType:"json",
			data:"appid="+$("#appidval").val()+"&status="+$('input:checkbox[name="status"]:checked').val(),
			success:function(data){
				if(data.result>0){
					modal_body_html=$("#model-body-devapply").html();
					$("#model-body-devapply").html("修改状态成功！");
					$("#btn-updatestatus").hide();
				}
				else{
					modal_body_html=$("#model-body-devapply").html();
					$("#model-body-devapply").html("修改状态失败！");
					$("#btn-updatestatus").hide();
				}
				
			}
		});
	});
	$(".close-up").click(function(){
		if($.trim(modal_body_html)!=""){
			$("#model-body-devapply").html(modal_body_html);
		}
		$('#myModal').modal('hide');
		$("#btn-updatestatus").show();
		sumbitForm("none");
	});
});





