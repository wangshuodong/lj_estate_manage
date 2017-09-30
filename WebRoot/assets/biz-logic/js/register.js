$('#submitForm').validate({
		onkeyup : true,
		rules : {
			"username" : {
				minlength : 4,
				maxlength : 20,
				required : true
			},
			"password" : {
				minlength : 6,
				maxlength : 20,
				required : true
			},
			"password2" : {
				minlength : 6,
				maxlength : 20,
				required : true
			},
			"phone" : {
				minlength : 11,
				maxlength : 11,
				digits:true,
				required : true
			},
			"qq" : {
				minlength :5,
				maxlength : 11,
				digits:true,
				required : true
			},
			"email" : {
				email:true,
				maxlength:50,
				required : true
			},
			"nick" : {
				minlength :2,
				maxlength : 11,
				required : true
			},
			"bankUserName" : {
				minlength :2,
				maxlength : 11
				//required : true
			},
			"province":{
				minlength :1,
				maxlength : 50
				//required : true
			},
			"city":{
				minlength :1,
				maxlength : 50
				//required : true
			},
			"bankAddress":{
				minlength :1,
				maxlength : 50
				//required : true
			},
			"bankNo":{
				maxlength : 20,
				creditcard:true
				//required : true
			},
			"vcode":{
				minlength :4,
				maxlength : 4,
				required : true
			}
		},
		messages : {
	
			"username" : {
				minlength : "请输入字符长度介于4~20之间的用户名",
				maxlength : "请输入字符长度介于4~20之间的用户名",
				required : "请输入字符长度介于4~20之间的用户名"
			},
			"password" : {
				minlength : "请输入字符长度介于6~20之间的密码",
				maxlength : "请输入字符长度介于6~20之间的密码",
				required : "请输入字符长度介于6~20之间的密码"
			},
			"password2" : {
				minlength : "请输入字符长度介于6~20之间的密码",
				maxlength : "请输入字符长度介于6~20之间的密码",
				required : "请输入字符长度介于6~20之间的密码"
			},
			"phone" : {
				minlength : "请输入11位的有效手机号",
				maxlength : "请输入11位的有效手机号",
				digits:"请输入11位的有效手机号",
				required : "请输入11位的有效手机号"
			},
			"qq" : {
				minlength :"请输入5~11位有效QQ号",
				maxlength : "请输入5~11位有效QQ号",
				digits:"请输入5~11位有效QQ号",
				required : "请输入5~11位有效QQ号"
			},
			"email" : {
				email:"请按正确的邮箱格式输入",
				maxlength:"邮箱长度不能超过50字符",
				required : "请输入你的邮箱,长度不能超过50字符"
			},
			"nick" : {
				minlength :"请输入你的昵称,长度不能超过11字符",
				maxlength : "您输入的字符长度超11字符，请重新输入",
				required : "比填选项"
			},
			"bankUserName" : {
				minlength :"请输入你的真实姓名,长度不能超过11字符",
				maxlength : "您输入的字符长度超11字符，请重新输入",
				required : "比填选项"
			},
			"province":{
				minlength :"请输入省份",
				maxlength : "字符长度不能超过50字符"
				//required : "请输入省份"
			},
			"city":{
				minlength :"请输入所在城市",
				maxlength : "字符长度不能超过50字符"
				//required : "请输入所在城市"
			},
			"bankAddress":{
				minlength :"请填写支行名称",
				maxlength : "支行名称不能超过50字符"
				//required : "请填写支行名称"
			},
			"bankNo":{
				maxlength : "请输入20位以内的银行卡号",
				creditcard:"请输入合法的银行卡或信用卡卡号"
				//required : "请填写你的银行卡号"
			},
			"vcode":{
				minlength :"请输入4位字符验证码",
				maxlength : "请输入4位字符验证码",
				required : "请输入4位字符验证码"
			}
		}

	});


