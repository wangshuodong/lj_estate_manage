$(function() {
	jQuery.validator.addMethod("isIdCardNo", function(value, element) {
		return this.optional(element) || checkidcard(value);
	}, "请输入正确的身份证号码");
	
	$('#personalForm').validate({
		rules : {
			"qq" : {
				required : true,
				number : true,
				minlength : 6,
				maxlength : 14
			},
			"phone" : {
				required : true,
				number : true,
				minlength : 8,
				maxlength : 14
			},
			"oldPassword" : {
				minlength : 6,
				maxlength : 14
			},
			"newPassword" : {
				minlength : 6,
				maxlength : 14
			},
			"confirmPassword" : {
				equalTo : "#newPassword"
			}
		},
		messages : {
			"qq" : {
				required : "不能为空",
				number : "必须为qq号码",
				minlength : "输入最小长度为 6",
				maxlength : "输入最大长度为 14"
			},
			"phone" : {
				required : "不能为空",
				number : "必须为电话号码",
				minlength : "输入最小长度为 8",
				maxlength : "输入最大长度为 14"
			},
			"oldPassword" : {
				minlength : "输入最小长度为 6",
				maxlength : "输入最大长度为 14"
			},
			"newPassword" : {
				minlength : "输入最小长度为 6",
				maxlength : "输入最大长度为 14"
			},
			"confirmPassword" : {
				equalTo : "必须与新密码一致"
			}
		},
		highlight : function(label) {
			$(label).closest('input').addClass('error');
		}
	});
	
	$("#personalForm button[name='userSave']").click(function() {
		var messageDiv = $("#messageDiv");
		var parms = $("#personalForm").serialize();

		if ($("#personalForm").valid()) {
			$.ajax({
				type : "post",
				url : "personal_info.html",
				dataType : 'json',
				data : parms,
				success : function(result) {
					var message = result.m;
					messageDiv.html(message);
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