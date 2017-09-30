function sumbitForm(type){
	var d = $("#submitForm"). serialize(); 
	$.ajax({
		url:"queryBillAccount.html",
		type:"post",
		dataType:"json",
		data:d+"&type="+type,
		success:function(data){
			var html = "";
			var count = data.count;
			var currPage = data.currPage;
			var pageSize = data.pageSize;
			var collect = data.collect;
			var bill_entry_amount = 0;
			
			if (collect != null) {
				bill_entry_amount = collect.bill_entry_amount;
			}
			//6,8,9,10,11
			html += "<tr>";
			html += "<td></td>";
			html += "<td></td>";
			html += "<td>汇总:</td>";
			html += "<td></td>";
			html += "<td></td>";
			html += "<td><font color='red'>" + bill_entry_amount + "</td>";
			html += "<td></td>";
			html += "<td></td>";
			html += "<td></td>";
			html += "<td></td>";
			html += "<td></td>";
			html += "<td></td>";
			html += "<td></td>";
			html += "</tr>";
			
			
			if(data.list == null || data.list.length==0){
				html += "<tr>";
				html += "<td colspan='13' style='text-align:center;font-weight:bold;'>暂无数据</td>";
				html += "</tr>";
			}else{
				$.each(data.list,function(i,p){
					html += "<tr>";
					
					html += "<td>"+p.bill_entry_id+"</td>";
					html += "<td>"+p.roomAddress+"</td>";
					
					html += "<td>"+p.proprietorName+"</td>";
					html += "<td>"+p.cost_type+"</td>";
					html += "<td>"+p.acct_period+"</td>";
					html += "<td>"+p.bill_entry_amount+"</td>";
					html += "<td>"+p.release_day+"</td>";
					html += "<td>"+p.deadline+"</td>";
					
					if(p.payStatus==0){
						html += "<td style='text-align:center;' nowrap='nowrap'><font color='red'>未付款</font></td>";
					}
					else{ 
						html += "<td style='text-align:center;' nowrap='nowrap'>已付款</td>";	
					}

					html += "<td>"+p.payType+"</td>";
					
					if(p.sendStatus==0){
						html += "<td style='text-align:center;'  nowrap='nowrap'>" +
						"<a href=\"javascript:;\" onclick=\"roominfoUpload("+p.id+")\"><font color='red'>上传支付宝</font></</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
						"</td>";
					}
					else{ 
						if(p.sendStatus==1){
							html += "<td style='text-align:center;' nowrap='nowrap'>已上传</td>";
						}
					}
					if(p.payStatus==0){
						html += "<td style='text-align:center;' nowrap='nowrap'><a href=\"initPayBillAccount.html?id="+p.id+"\">账单收款</a>&nbsp;&nbsp;<a href=\"initUpdatePayBillAccount.html?id="+p.id+"\">修改</a></td>";
					}
					else{ 
						html += "<td style='text-align:center;'>...</td>";	
					}
					
					html += "<td style='text-align:center;' nowrap='nowrap'><button type='button'  onclick='deleteBillAccount("+p.id+")'>删除&nbsp;&nbsp;</button></td>";
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
	if(window.confirm('你确定要删除当前账单吗？')){
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
			billAccountId:id
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
					location.href="billAccount.html";
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
	window.location.href="addBillAccount.html";
}

function billAccountUploadExcel(){
	location.href="billAccountUploadExcel.html";
}

function billAccountBachUpload(){
	var param = $("#submitForm").serialize();
	if ($("#submitForm").valid()) {
		$("#billSycy").attr('disabled',true);
	}

	$.ajax({
		url:"billAccountBachUpload.html",
		type:"post",
		dataType:"json",
		data:{
			id:""
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
					location.href="billAccount.html";
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
function initPayBillAccount(id){
	$.ajax({
		url:"initPayBillAccount.html",
		type:"get",
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
function roominfoUpload(id){
	$.ajax({
		url:"billAccountUpload.html",
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