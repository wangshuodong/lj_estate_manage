//view data  table 数据
var dataList = [];

var today = formatDate(new Date(), "YYYY-MM-DD");

$(function() {
	// 初始化加载需要修改的内容
	var sdkId = $("#sdkId").val();
	modifyVersionSdk(sdkId);

	// ajax方式提交内容
	$("#modifyForm button[name='versionSdkModify']").click(function() {
		var messageDiv = $("#messageDiv");
		var formObj = $("#modifyForm");
		var sv = formObj.find("#sv").val();
		var name = formObj.find("#name").val();
		var mid = formObj.find("#mid").val();
		var flagSv=formObj.find("#flagSv").val();

		$.ajax({
			type : "post",
			url : "saveModifyVersionSdk.html",
			dataType : 'json',// 指定ajax返回结果类型，重要
			data : {
				name : name,
				mid : mid,
				sv : sv,
				flagSv:flagSv
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

				formObj.find("#mid").val('');
				formObj.find("#sv").val('');
				formObj.find("#name").val('');
			},
			error : function() {
				// 请求出错处理
				messageDiv.html('服务器内部错误，请联系管理员!');
			}
		});
	});
});

function modifyVersionSdk(id) {
	var formObj = $("#modifyForm");
	$.ajax({
		type : "post",
		url : "initModifyVersionSdk.html",
		dataType : 'json',// 指定ajax返回结果类型，重要
		data : {
			id : id
		},
		success : function(result) {
			var name = result.name;
			var sv = result.sv;
			var mid = result.mid;
			var createTime = result.createTime;

			formObj.find("#name").val(name);
			formObj.find("#sv").val(sv);
			formObj.find("#mid").val(mid);
			$("#flagSv").val(sv);
		},
		error : function(result) {
			// messageDiv.html('服务器内部错误，请联系管理员!');
		}
	});
}
