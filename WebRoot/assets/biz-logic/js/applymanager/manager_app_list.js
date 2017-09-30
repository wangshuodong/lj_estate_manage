function sumbitForm(type){
	var d = $("#submitForm"). serialize(); 
	$.ajax({
		url:"manager_app_list.html",
		type:"post",
		dataType:"json",
		data:d+"&type="+type,
		success:function(data){
			var html = "";
			var count = data.count;
			var currPage = data.currPage;
			var pageSize = data.pageSize;
			var totalPage = data.totalPage;
			if(data.listApp == null || data.listApp.length==0){
				html += "<tr>";
				html += "<td colspan='7' style='text-align:center;font-weight:bold;'>暂无数据</td>";
				html += "</tr>";
			}else{
				$.each(data.listApp,function(i,app){
					var status,username,appname,qname,appkey,aid,update;
					app.status=='1,1,1'?status='启动':status='<span style=color:#979191;>暂停</span>';
					if(app.aid==null||$.trim(app.aid)==""||app.uid==null||$.trim(app.uid)==""){
						aid="";
						appkey="";
						update="";
					}
					else{
						aid=app.aid;
						appkey=app.uid+"-"+app.qid+"-"+app.aid;
						update="<a href='javascript:void(0)' onclick='changeModel(\""+app.aid+"\",\""+app.status+"\")' class='update_status'>修改</a>";
					}
					app.username==null?username="未知用户ID为："+app.uid:username=app.username;
					app.appname==null?appname="未知应用ID为："+app.aid:appname=app.appname;
					app.qname==null?qname="":qname=app.qname;
					html += "<tr>";
					html += "<td>"+aid+"</td>";
					html += "<td>"+appkey+"</td>";
					html += "<td>"+username+"</td>";
					html += "<td>"+qname+"</td>";
					html += "<td>"+appname+"</td>";
					html += "<td>"+param.greenYellow(aid, app.greenYellow)+"</td>";
					html += "<td style='text-align:center;'>"+ status+"</td>";
					html += "<td style='text-align:center;'>"+update+"</td>";
					html += "</tr>";
					
				})
			}
			$("#d_body").html(html);
			$("#totalCount").html("共有记录"+count+"条 当前第"+currPage+"页/ 总"+totalPage+"页");
			pageFun(currPage,count,pageSize); 
			//绑定事件
			$(".green_yellow").bind("click",function(){
				param.selectStatus = $(this).val();
			});
			$(".green_yellow").bind("change",function(){
				if(confirm("确认修改该APP的图片类型吗？")){
					$.getJSON("updateGreenYellow.html",{
						appid:$(this).attr("appid"),
						greenYellow:$(this).val()
					},function(json){
						//alert(json.message);
					});
				}else{
					//取消保存，需要将下拉框还原成初始状态
					$(this).val(param.selectStatus);
				}
			});
		}
	});
}
var param={
		greenYellow:function(appid,val){selected="true"
			var select="<select class='green_yellow form-control' style='width:72px;padding-right:0px;display: inline;' appid="+appid+"><option value='green' " ;
			if(val=="green"){
				select += "selected='true'";
			}
			select += ">正常</option><option value='yellow' " ;
			if(val=="yellow"){
				select += "selected='true'";
			}
			select+= ">诱导</option>" +
			"</select>";
			return select;
		},
		selectStatus:null
};
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


