$(document).ready(function(){
	$('#submitForm').validate({
		rules : {
			"username" : {
				maxlength : 14,
				minlength : 5,
				required : true
			},
			"companyName" : {
				maxlength : 14,
				minlength : 5,
				required : true
			},
			"phone" : {
				number : true,
				minlength : 8,
				maxlength : 14
			}
		},
		messages : {
			"username" : {
				minlength : "输入最小长度为 5",
				maxlength : "输入最大长度为14",
				required : "用户名不能为空"
			},
			"companyName" : {
				minlength : "输入最小长度为 5",
				maxlength : "输入最大长度为14",
				required : "代工厂名称不能为空"
			},
			"phone" : {
				number : "必须为电话号码",
				minlength : "输入最小长度为 8",
				maxlength : "输入最大长度为 14",
				required : "电话号码不合法"
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

//计算金额
function computeMoney(){
	var formObj = $("#submitForm");
	var price = formObj.find("input[name='price']").val();
	var number = formObj.find("input[name='number']").val();
	var money=price*number;
	formObj.find("input[name='money']").val(money);
}

function change(param){
	$.ajax({
		url:"saveProxyFactory.html",
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
			if(data.status == true && data.result>0){
				$("#modal-body").html(data.msg);
				$("#infowindow").modal('show');
				location.href="news.html";
			}
			else{
				$("#modal-body").html(data.msg);
				$("#infowindow").modal('show');
			}
		}
	});
}
function add(param){
	$.ajax({
		url:"saveProxyFactory.html",
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
