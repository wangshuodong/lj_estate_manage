$(function() {
	$('#submitForm').validate({
		rules : {
			"productName" : {
				maxlength : 14,
				minlength : 5,
				required : true
			},
			"price" : {
				number : true,
				required : true
			}
		},
		messages : {
			"productName" : {
				minlength : "输入最小长度为 1",
				maxlength : "输入最大长度为14",
				required : "产品名称不能为空"
			},
			"price" : {
				number : "单价必须为数字",
				required : "单价不合法"
			}
		},
		highlight : function(label) {
			$(label).closest('input').addClass('error');
		}
	});
	
	$("#submitBTN_news").click(function(){
		var param = $("#submitForm").serialize();
		if ($("#submitForm").valid()) {
			$("#submitBTN_news").attr('disabled',true);
			add(param);
		}
	});
	$("#type").change(function(){
		isShow($(this).val());
	});
});
function isShow(val){
	if(val=="1"){
		$("#isLoginShow_frame").show();
		if($.trim(show_val)=="" || show_val=="1"){$("input[name='isShow']").first().attr("checked",true);}
		else {$("input[name='isShow']").last().attr("checked",true);}
		
	}
	else{
		$("#isLoginShow_frame").hide();
		$("input[name='isShow']").each(function(){
			$(this).attr("checked",false);
		});
	}
}

function add(param){
	$.ajax({
		url:"saveBillAccount.html",
		type:"post",
		dataType:"json",
		data:param,
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
	});
}
