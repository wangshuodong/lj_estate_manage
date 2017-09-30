$(function() {
	$('#submitForm').validate({
		rules : {
			"name" : {
				maxlength : 14,
				minlength : 1,
				required : true
			}
		},
		messages : {
			"name" : {
				minlength : "输入最小长度为 1",
				maxlength : "输入最大长度为14",
				required : "仓库名称不能为空"
			}
		},
		highlight : function(label) {
			$(label).closest('input').addClass('error');
		}
	});
	
	$("#submitBTN_news").click(function(){
		var param = $("#submitForm").serialize();
		if ($("#submitForm").valid()) {
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
		url:"communityDetailsQuery.html",
		type:"post",
		dataType:"json",
		data:param,
		success:function(data){
			if(data.status == true){
				$("#audit_status").html(data.audit_status);
				$("#jiaofei").html(data.msgJiaofei);
				$("#xiaoqu").html(data.msgXiaoqu);
			}
		}
	});
}
