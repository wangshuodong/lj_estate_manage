$('#userinfo').validate({
		onkeyup : true,
		rules : {
			"nick" : {
				minlength : 1,
				maxlength : 20,
				required : true
			},
			"bankUserName" : {
				minlength : 1,
				maxlength : 20,
				required : true
			},
			"phone" : {
				minlength : 11,
				maxlength : 11,
				required : true,
				digits:true
			},
			"qq" : {
				minlength : 5,
				maxlength : 11,
				required : true,
				digits:true
			},
			"idcard" : {
				minlength : 15,
				maxlength : 20,
				required : true
			}
		},
		messages : {
	
			"nick" : {
				minlength : "请填昵称",
				maxlength : "亲，低调点，20字符以内的昵称就OK",
				required : "必填选项"
			},
			"bankUserName" : {
				minlength : "请填您的真实姓名",
				maxlength : "咋能不装B么,真实姓名请保持在20字符以内",
				required : "真实姓名不允许为空"
			},
			"phone" : {
				minlength : "请输入11位的手机号",
				maxlength : "请输入11位的手机号",
				required : "请输入11位的手机号",
				digits:"请输入11位的手机号"
			},
			"qq" : {
				minlength : "请输入5~11位QQ号",
				maxlength : "请输入5~11位QQ号",
				required : "请输入5~11位QQ号",
				digits:"请输入5~11位QQ号"
			},
			"idcard" : {
				minlength : "请输入长度介于15-20间的身份证号",
				maxlength: "请输入长度介于15-20间的身份证号",
				required : "必填选项"
			}
		}

	});


