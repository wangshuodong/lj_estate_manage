$(function() {
	jQuery.validator.addMethod("isIdCardNo", function(value, element) {
		return this.optional(element) || checkidcard(value);
	}, "请输入正确的身份证号码");

	$('#updUserForm').validate({
		rules : {
			"email" : {
				email : true
			},
			"nick" : {
				maxlength : 14,
				minlength : 2,
				required : true
			},
			"qq" : {
				number : true,
				minlength : 6,
				maxlength : 14
			},
			"phone" : {
				number : true,
				minlength : 8,
				maxlength : 14
			},
			"bankNo" : {
				creditcard : true
			},
			"certificate" : {
				isIdCardNo : true
			},
			"rate" : {
				number : true,
				maxlength : 2,
				required : true
			}
		},
		messages : {
			"email" : {
				email : "请输入正确的邮件地址"
			},
			"nick" : {
				minlength : "输入最小长度为 2",
				maxlength : "输入最大长度为14",
				required : "不能为空"
			},
			"qq" : {
				number : "必须为qq号码",
				minlength : "输入最小长度为 6",
				maxlength : "输入最大长度为 14"
			},
			"phone" : {
				number : "必须为电话号码",
				minlength : "输入最小长度为 8",
				maxlength : "输入最大长度为 14"
			},
			"bankNo" : {
				creditcard : "请输入正确的卡号"
			},
			"certificate" : {
				isIdCardNo : "请输入正确的身份证号"
			},
			"rate" : {
				number : "必须为数字",
				maxlength : "请输入小于100的整数",
				required : "不能为空"
			}
		},
		highlight : function(label) {
			$(label).closest('input').addClass('error');
		}
	});

	$("#updUserForm button[name='userSave']").click(function() {
		var messageDiv = $("#messageDiv");
		var parms = $("#updUserForm").serialize();

		if ($("#updUserForm").valid()) {
			$.ajax({
				type : "post",
				url : "updUser.html",
				dataType : 'json',
				data : parms,
				success : function(result) {
					 var message = result.m;
						alert(message);
						if(result.r == false){
							messageDiv.html(message);
						}else{
							window.location.href = "user.html";
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

function checkidcard(num) {
	var len = num.length, re;
	if (len == 15)
		re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{3})$/);
	else if (len == 18)
		re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\d)$/);
	else {
		return false;
	}
	var a = num.match(re);
	if (a != null) {
		if (len == 15) {
			var D = new Date("19" + a[3] + "/" + a[4] + "/" + a[5]);
			var B = D.getYear() == a[3] && (D.getMonth() + 1) == a[4]
					&& D.getDate() == a[5];
		} else {
			var D = new Date(a[3] + "/" + a[4] + "/" + a[5]);
			var B = D.getFullYear() == a[3] && (D.getMonth() + 1) == a[4]
					&& D.getDate() == a[5];
		}
		if (!B) {
			return false;
		}
	}

	return true;
}