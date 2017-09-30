function sumbitForm(type){
	var d = $("#submitForm"). serialize(); 
	$.ajax({
		url:"product_list.do",
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
				html += "<td colspan='13' style='text-align:center;font-weight:bold;'>暂无数据</td>";
				html += "</tr>";
			}else{
				$.each(data.list,function(i,p){
					var productDate = p.productDate;
					productDate = productDate.substr(0, 10);
					
					var endDate = p.endDate;
					endDate = endDate.substr(0, 10);
					
					html += "<tr>";
					html += "<td>"+p.id+"</td>";
					html += "<td>"+p.name+"</td>";
					html += "<td>"+p.price+"</td>";
					html += "<td>"+p.unit+"</td>";
					html += "<td>"+p.material+"</td>";
					html += "<td>"+p.manufacture+"</td>";
					html += "<td style='text-align:center;'>"+ productDate+"</td>";
					html += "<td style='text-align:center;'>"+endDate+"</td>";
					html += "<td style='text-align:center;'>"+p.weight+"</td>";
					html += "<td style='text-align:center;'>"+p.standard+"</td>";
					html += "<td style='text-align:center;'>"+p.facadeDesc+"</td>";			  		
					html += "<td style='text-align:center;'>"+p.createTime+"</td>";
					html += "<td style='text-align:center;'>"+p.remark+"</td>";
					html += "<td style='text-align:center;'><a href=\"product_update.html?pid="+p.id+"\">修改</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"javascript:;\" onclick=\"delById("+p.id+")\">删除</a></td>";
					html += "</tr>";
					
				})
			}
			$("#d_body").html(html);
			$("#totalCount").html("共有记录"+count+"条 ");
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

function jumpToAdd(){
	window.location.href="product_add.html";
}
function delById(id){
	$.ajax({
		url:"product_del.do",
		type:"post",
		dataType:"json",
		data:{
			pid:id
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