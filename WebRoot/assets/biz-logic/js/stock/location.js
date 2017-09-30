function sumbitForm(type){
	var d = $("#submitForm"). serialize(); 
	$.ajax({
		url:"queryLocation.html",
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
					html += "<tr>";
					html += "<td>"+p.id+"</td>";
					html += "<td>"+p.housingName+"</td>";
					html += "<td>"+p.buildingName+"</td>";
					html += "<td>"+p.name+"</td>";
					html += "<td>"+p.remark+"</td>";
					html += "<td style='text-align:center;'><button type='button'  onclick='deleteRoomInfo("+p.id+")'>删除房屋&nbsp;&nbsp;</button></td>";
					html += "<td style='text-align:center;'><button type='button'  onclick='deleteBillAccount("+p.id+")'>删除&nbsp;&nbsp;</button></td>";
					html += "</tr>";
					
				})
			}
			$("#d_body").html(html);
			$("#totalCount").html("共有记录"+count+"条 ");
			pageFun(currPage,count,pageSize);
		}
	});
}

function deleteBillAccount(id){
	if(window.confirm('你确定要删除当前单元账单吗？')){
		deleteBillAccountFromAlipay(id);
        return true;
     }else{
        return false;
    }
}

function deleteBillAccountFromAlipay(id){
	$.ajax({
		url:"deleteBillAccountFromAlipay.html",
		type:"post",
		dataType:"json",
		data:{
			locationId:id
		},
		beforeSend:function(){
			$("#loading").modal('show');
		},
		complete:function(){
			$("#loading").modal('hide');
		},
		success:function(data){
			if(data.status == true){
				$(".closeTipModal").click(function(){
					location.href="location.html";
				});	
				$("#modal-body").html(data.msg);
				$("#infowindow").modal('show');
			}
			else{
				$("#modal-body").html(data.msg);
				$("#infowindow").modal('show');
			}
		}
	})
}


function deleteRoomInfo(id){
	if(window.confirm('你确定要删除当前单元号对应的房屋信息吗？')){
		deleteRoomInfoFromAlipay(id);
        return true;
     }else{
        return false;
    }
}

function deleteRoomInfoFromAlipay(id){
	$.ajax({
		url:"deleteRoomInfoFromAlipay.html",
		type:"post",
		dataType:"json",
		data:{
			locationId:id
		},
		beforeSend:function(){
			$("#loading").modal('show');
		},
		complete:function(){
			$("#loading").modal('hide');
		},
		success:function(data){
			if(data.status == true){
				$(".closeTipModal").click(function(){
					location.href="location.html";
				});	
				$("#modal-body").html(data.msg);
				$("#infowindow").modal('show');
			}
			else{
				$("#modal-body").html(data.msg);
				$("#infowindow").modal('show');
			}
		}
	})
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
	window.location.href="addLocation.html";
}

function basicserviceInitialize(id){
	$.ajax({
		url:"basicserviceInitialize.html",
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
				$("#modal-body").html(data.msg);
				$("#infowindow").modal('show');
			}
			else{
				$("#modal-body").html(data.msg);
				$("#infowindow").modal('show');
			}
		}
	})
}

function synchCommunityToAlipay(id){
	$.ajax({
		url:"synchCommunityToAlipay.html",
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
				$("#modal-body").html(data.msg);
				$("#infowindow").modal('show');
			}
			else{
				$("#modal-body").html(data.msg);
				$("#infowindow").modal('show');
			}
		}
	})
}