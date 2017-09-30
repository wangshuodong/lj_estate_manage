
$(function(){
	$.ajax({
		url:"loadChannel.html",
		type:"post",
		dataType:"json",
		success:function(data){
			var html="";
			$.each(data.listChannel,function(i,c){
				html+="<option value="+c.id+">"+c.name+"</option>";
			});
			$("#channel").append(html);
			$("#channel").val(channelVal);
			if($.trim(appid)==""){
				$("#channel").removeAttr("readonly");
				$("input[name='packagename']").removeAttr("readonly");
				$("#ex").removeAttr("readonly");
			}
			if($.trim(appid)!=""){
				$("#channel option").hide();
			}
		}
	});
	$(".colose-modal").click(function(){
		location.reload();
	});
});

$(function(){
	$("#submitBTN").click(function(){
		if($.trim(appid)==""){
			addApp();
		}
		else{
			updateApp();
		}
	});
});
function addApp(){
	if(!checkFrom()){return;}
	$("#submitBTN").attr("disabled","disabled");
	$("#submitForm").ajaxSubmit({
		url:"addApp.html",
		type:"post",
		dataType:"json",
		success:function(data){
			if(data.result>0){
				location.href="dev_app_list.html";
			}
			else if(data.name==false){
				$(".tip_name span").html("* 应用名称不能为空！");
			}
			else if(data.packagename==false){
				$(".tip_name span").html("* ");
				$(".tip_package span").html("* 包名不能为空！");
			}
			else{
 				changeModel("添加应用失败！");
 			}
		}
	});
}
function updateApp(){
	if(!checkFrom()){return;}
	$("#submitBTN").attr("disabled","disabled");
	$("#submitForm").ajaxSubmit({
		url:"updateApp.html",
		type:"post",
		dataType:"json",
		success:function(data){
			if(data.result>0){
				changeModel("修改应用成功！");
			}
			else if(data.name==false){
				$(".tip_name span").html("* 应用名称不能为空！");
			}
			else{
				changeModel("修改应用失败！");
 			}
		}
	});
}
function checkFrom(){
	if($.trim($("input[name='name']").val())==""){
		$(".tip_name span").html("* 应用名称不能为空！");
		return false;
	}
	$(".tip_name span").html("* ");
	if($.trim($("input[name='packagename']").val())==""){
		$(".tip_package span").html("* 包名不能为空！");
		return false;
	}
	$(".tip_package span").html("* ");
	return true;
}
function changeModel(html){
	$('#myModal').modal('show');
	$(".modal-backdrop").css({background:"#fff"});
	$(".modal-dialog").css({"width":"330px"});
	$(".modal-body div").html(html);
}




