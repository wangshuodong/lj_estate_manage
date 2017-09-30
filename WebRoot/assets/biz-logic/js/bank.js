function findBank(bankName) {
	var d = bankName;
	if (d == null) {
		d = "请选择";
	}
	$.ajax({
		url : "query_bank.html",
		type : "post",
		dataType : "html",
		data : {
			bankName : bankName
		},
		success : function(data) {
			$("#banklist").html(data);
		}
	});
}
