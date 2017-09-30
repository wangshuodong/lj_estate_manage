$(function() {
	jQuery.validator.addMethod("isIdCardNo", function(value, element) {
		return this.optional(element) || checkidcard(value);
	}, "请输入正确的身份证号码");

	$('#addUserForm').validate({
		rules : {
			"username" : {
				maxlength : 14,
				minlength : 2,
				required : true
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
				maxlength : 3,
				required : true
			}
		},
		messages : {
			"username" : {
				minlength : "输入最小长度为 5",
				maxlength : "输入最大长度为14",
				required : "不能为空"
			},
			"nick" : {
				minlength : "输入最小长度为2",
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
				maxlength : "输入最大长度为3",
				required : "不能为空"
			}
		},
		highlight : function(label) {
			$(label).closest('input').addClass('error');
		}
	});

	$("#addUserForm button[name='userSave']").click(function() {
		var messageDiv = $("#messageDiv");
		var parms = $("#addUserForm").serialize();

		if ($("#addUserForm").valid()) {
			$.ajax({
				type : "post",
				url : "addUser.html",
				dataType : 'json',
				data : parms,
				success : function(data) {
					if(data.r==false){
						alert(data.m);
						return;
					}
					window.location.href = "user.html";
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