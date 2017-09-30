function submitForm(url){
	var d = $("#submitForm"). serialize(); 
	$.ajax({
		url:url+".html",
		type:"post",
		dataType:"json",
		data:d,
		success:function(data){
			if(data.result>0){
				alert("操作成功！");
			}
			else{
 				alert("操作失败！");
 			}
		}
	});
}