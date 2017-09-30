$(document).ready(function() {

	// 表单提交方式
	// $("#addForm button[name='versionSdkInput']").click(function(){
	// $("#addForm").attr("action", "saveVersionSdk.html");
	// $("#addForm").attr("method", "post");
	// $("#addForm").submit();
	// });

	// ajax方式提交内容
	$("#addForm button[name='versionSdkInput']").click(function() {
		var messageDiv = $("#messageDiv");
		var formObj = $("#addForm");
		var mid = formObj.find("#mid").val();
		var sv = formObj.find("#sv").val();
		var name = formObj.find("#name").val();

		$.ajax({
			type : "post",
			url : "saveVersionSdk.html",
			dataType : 'json',// 指定ajax返回结果类型，重要
			data : {
				name : name,
				mid : mid,
				sv : sv
			},
			success : function(result) {
				if(result.error>0){
					messageDiv.html('信息输入不完整!');
					return;
				}
				if(result.exist>0){
					messageDiv.html('版本号已经存在，请重新输入!');
					return;
				}
				var message = result.message;
				messageDiv.html(message);
				location.href="version_sdk.html";
			},
			error : function() {
				// 请求出错处理
				messageDiv.html('服务器内部错误，请联系管理员!');
			}
		});
	});
});