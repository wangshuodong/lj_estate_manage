$(document).ready(function() {
	$('#submitForm').validate({
		rules : {
			"orgName" : {
				maxlength : 20,
				minlength : 4,
				required : true
			},
		},
		messages : {
			"orgName" : {
				minlength : "输入最小长度为 4",
				maxlength : "输入最大长度为20",
				required : "组织机构名称不能为空"
			},
		},
		highlight : function(label) {
			$(label).closest('input').addClass('error');
		}
	});
	
	$("#submitBTN_news").click(function(){
		var d = $("#submitForm").serialize();
		if ($("#submitForm").valid()) {
			$.ajax({
				url : "department_add.do",
				type : "post",
				dataType : "json",
				data : d,
				beforeSend : function() {
					$("#loading").modal('show');
				},
				complete : function() {
					$("#loading").modal('hide');
				},
				success : function(data) {
					if (data.status == true) {
						alert("操作成功");
						window.location.href = "department_menu.html";
					} else {
						alert("操作失败");
					}
				}

			})
		}
	});
});

