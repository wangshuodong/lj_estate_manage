$(document).ready(function() {
	$("#submitBTN_news").click(function() {

		var param = $("#submitForm").serialize();
		if ($.trim(news_id) != "") {
			change(param);
		} else {
			$("#submitBTN_news").attr('disabled',true);
			add(param);
		}
	});
	$("#type").change(function() {
		isShow($(this).val());
	});
});
function isShow(val) {
	if (val == "1") {
		$("#isLoginShow_frame").show();
		if ($.trim(show_val) == "" || show_val == "1") {
			$("input[name='isShow']").first().attr("checked", true);
		} else {
			$("input[name='isShow']").last().attr("checked", true);
		}

	} else {
		$("#isLoginShow_frame").hide();
		$("input[name='isShow']").each(function() {
			$(this).attr("checked", false);
		});
	}
}

// 计算金额
function computeMoney() {
	var formObj = $("#submitForm");
	var price = formObj.find("input[name='price']").val();
	var number = formObj.find("input[name='number']").val();
	var money = price * number;
	money = money.toFixed(2);
	formObj.find("input[name='money']").val(money);
}


function add(param) {
	$.ajax({
		url : "updateOrderPrice.html",
		type : "post",
		dataType : "json",
		data : param,
		beforeSend : function() {
			$("#loading").modal('show');
		},
		complete : function() {
			$("#loading").modal('hide');
		},
		success : function(data) {
			if (data.status == true) {
				$(".closeTipModal").click(function(){
					location.href="orders.html";
				});	
				$("#modal-body").html(data.msg);
				$("#infowindow").modal('show');
			} else {
				$("#modal-body").html(data.msg);
				$("#infowindow").modal('show');
			}
		}
	});
}
