$('#userinfo').validate({
		onkeyup : true,
		rules : {
			"province" : {
				minlength : 1,
				maxlength : 255,
				required : true
			},
			"city" : {
				minlength : 1,
				maxlength : 255,
				required : true
			},
			"bankAddress" : {
				minlength : 1,
				maxlength : 255,
				required : true
			},
			"bankUserName" : {
				minlength : 1,
				maxlength : 50,
				required : true
			},
			"bankNo" : {
				creditcard : true
			},
			"certificate" : {
				minlength : 15,
				maxlength : 20,
				required : true
			}
		},
		messages : {
	
			"province" : {
				minlength : "请输入所在省份",
				maxlength : "你来自外太空的么，地址这么长？",
				required : "请输入所在省份"
			},
			"city" : {
				minlength : "请填写所在城市",
				maxlength : "你来自外太空的么，地址这么长？",
				required : "请填写所在城市"
			},
			"bankAddress" : {
				minlength : "请填写支行地址",
				maxlength : "你来自外太空的么，地址这么长？",
				required : "请填支行地址"
			},
			"bankUserName" : {
				minlength : "请填写您的真实姓名",
				maxlength : "不装B，会死啊",
				required : "请填写您的真实姓名"
			},
			"bankNo" : {
				creditcard : "请输入合法的银行卡账号，不知道什么叫合法请联系管理员。"
			},
			"certificate" : {
				minlength : "请输入长度介于15-20间的身份证号",
				maxlength: "请输入长度介于15-20间的身份证号",
				required : "必填选项"
			}
		}

	});


