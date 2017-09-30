$(document).ready(function(){
	$("#submitBTN_news").click(function(){
		$("#context").val(CKEDITOR.instances.context.getData());
		var param = $("#submitForm").serialize();
		if($.trim(news_id)!=""){
			change(param);
		}
		else{
			add(param);
		}
	});
	$("#type").change(function(){
		isShow($(this).val());
	});
});
function isShow(val){
	if(val=="1"){
		$("#isLoginShow_frame").show();
		if($.trim(show_val)=="" || show_val=="1"){$("input[name='isShow']").first().attr("checked",true);}
		else {$("input[name='isShow']").last().attr("checked",true);}
		
	}
	else{
		$("#isLoginShow_frame").hide();
		$("input[name='isShow']").each(function(){
			$(this).attr("checked",false);
		});
	}
}
function change(param){
	$.ajax({
		url:"updateNews.html",
		type:"post",
		dataType:"json",
		data:param,
		beforeSend:function(){
			$("#loading").modal('show');
		},
		complete:function(){
			$("#loading").modal('hide');
		},
		success:function(data){
			if(data.status == true && data.result>0){
				$("#modal-body").html(data.msg);
				$("#infowindow").modal('show');
				location.href="news.html";
			}
			else{
				$("#modal-body").html(data.msg);
				$("#infowindow").modal('show');
			}
		}
	});
}
function add(param){
	$.ajax({
		url:"addNews.html",
		type:"post",
		dataType:"json",
		data:param,
		beforeSend:function(){
			$("#loading").modal('show');
		},
		complete:function(){
			$("#loading").modal('hide');
		},
		success:function(data){
			if(data.status == true && data.result>0){
				$("#modal-body").html(data.msg);
				$("#infowindow").modal('show');
				//location.href="news.html";
			}
			else{
				$("#modal-body").html(data.msg);
				$("#infowindow").modal('show');
			}
		}
	});
}
