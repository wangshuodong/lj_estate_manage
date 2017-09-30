$(function(){
	$("#submitBTN_Bonus").click(function(){
		if($.trim(bonusid)!=""){
			if(!checkForm()){
				return;
			};
			$("#submitForm").ajaxSubmit({
				url:"updateBonus.html",
				type:"post",
				dataType:"json",
				success:function(data){
					if(data.tip_name){
						$(".tip_name span").html("* 请输入存在的用户");
					}
					else if(data.tip_num){
						$(".tip_bonus span").html("* 请输入数值");
					}
					else if(data.result>0){
						alert("操作成功！");
					}
					else{
						alert("操作失败！");
					}
				}
			});
		}
		else{
			if(!checkForm()){
				return;
			};
			$("#submitForm").ajaxSubmit({
				url:"addBonus.html",
				type:"post",
				dataType:"json",
				success:function(data){
					if(data.tip_name){
						$(".tip_name span").html("* 请输入存在的用户");
					}
					else if(data.tip_num){
						$(".tip_bonus span").html("* 请输入数值");
					}
					else if(data.result>0){
						location.href="bonus.html";
					}
					else{
						alert("操作失败！");
					}
				}
			});
		}
	});
	$("input[name='username']").focusout(function(){
		checkUsername();
	});
});
function checkForm(){
	var taxBonus=$("input[name='taxBonus']").val();
	if(!checkUsername()){
		return false;
	}
	if(isNaN(taxBonus)||$.trim(taxBonus)==""){
		$(".tip_bonus span").html("* 请输入数值");
		return false;
	}
	$(".tip_bonus span").html("<span style='color:green'>* </span>");
	return true;
}
function checkUsername(){
	var username=$("input[name='username']").val();
	if($.trim(username)==""){
		$(".tip_name span").html("* 请输入用户名");
		return false;
	}
	$.ajax({
		url:"getByName.html",
		type:"post",
		dataType:"json",
		data:"username="+username,
		success:function(data){
			if(data.user==null){
				$(".tip_name span").html("* 请输入存在的用户");
				return false;
			}
		}
	});
	$(".tip_name span").html("<span style='color:green'>* </span>");
	return true;
}