$(function() {
	$('#addMenuForm').validate({
		rules : {
			"menuName" : {
				maxlength : 15,
				minlength : 2,
				required : true
			},
			"remark" : {
				maxlength : 100
			}
		},
		messages : {
			"menuName" : {
				maxlength : "输入最大长度为15",
				minlength : "输入最小长度为2",
				required : "不能为空"
			},
			"remark" : {
				maxlength : "输入最大长度为100"
			}
		},
		highlight : function(label) {
			$(label).closest('input').addClass('error');
		}
	});

	$("#addMenuForm button[name='menuSave']").click(function() {
		var messageDiv = $("#messageDiv");
		var parms = $("#addMenuForm").serialize();

		if ($("#addMenuForm").valid()) {
			$.ajax({
				type : "post",
				url : "menu_add.html",
				dataType : 'json',
				data : parms,
				success : function(result) {
					var message = result.m;
					alert(message);
					if(result.r == false){
						messageDiv.html(message);
					}else{
						window.location.href = "menu_mgr.html";
					}
				},
				error : function() {
					// 请求出错处理
					messageDiv.html('服务器内部错误，请联系管理员!');
				}
			});
		}
	});
});