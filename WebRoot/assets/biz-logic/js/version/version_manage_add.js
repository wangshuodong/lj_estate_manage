$(function(){
	$.ajax({
		url:"loadVersionSdkOrKit.html",
		type:"post",
		dataType:"json",
		success:function(data){
			var html="",html_kit="<option value='0'>&nbsp;&nbsp;请选择</option>";
			$.each(data.listSdk,function(i,s){
				html+='<li><input type="checkbox" id="'+s.mid+'" value="'+s.mid+'"  name="sdk_id">'+
				'<label for="'+s.mid+'">'+s.sv+'</label></li>';
			});
			$(".version_check").html(html);
			$.each(data.listKit,function(i,s){
				html_kit+=' <option value="'+s.id+'">&nbsp;&nbsp;'+s.kv+'</option>';
			});
			$("#kit_id").html(html_kit);
		}
	});
	//-----------------------------------------------------------
	
	$("#appkey").focusout(function(){
		$.ajax({
			url:"uniqueAppkey.html",
			type:"post",
			dataType:"json",
			data:"appkey="+$("#appkey").val(),
			success:function(data){
				if(data.status){
					$(".appkeytip").html("已经存在的APPKEY，请重新输入！");
					return;
				}
			}
		});
	});
	
});

function submitForm(){
	var appkey=$("#appkey").val();
	var kit_id=$("#kit_id").val();
	var sdk=-$(".version_check input:checkbox[name='sdk_id']:checked").val();
	
	if(appkey==""||appkey==null){
		alert("APPKEY输入不能为空！");
		return;
	}
	else if(!sdk){
		alert("请选择一个版本框架号！");
		return;
	}
	else if(kit_id=="0"){
		alert("请选择版本号！");
		return;
	}
	var d = $("#submitForm"). serialize(); 
	$.ajax({
		url:"version_manage_data_add.html",
		type:"post",
		dataType:"json",
		data:d,
		success:function(data){
			if(data.status){
				$(".appkeytip").html("已经存在的APPKEY，请重新输入！");
				return;
			}
			$(".appkeytip").html("多个ID以英文逗号分隔，0为所有渠道");
			if(data.result>0){
				location.href="version_manage.html";
			}
			else{
 				alert("操作失败！");
 			}
		}
	});
	
}
	
	
	





