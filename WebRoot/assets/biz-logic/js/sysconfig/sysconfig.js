$(function(){
	
	loadData();
	$(".btn").mousedown(function(){
		var $this=$(this).parent("div").siblings("div").find("input").first();
		$this.hide();
		$this.next().show();
	}).mouseup(function(){
		var $this=$(this).parent("div").siblings("div").find("input").first();
		$this.show();
		$this.next().hide();
	});
	$(".cfgval_pwd").focusout (function(){
		var pwd=$(".cfgval_pwd").val();
		$(".cfgval_text").val(pwd);
	});
});
function submitForm(){
	var d = $("#submitForm"). serialize(); 
	var c = $("#checkfen").val();
	 var reg = new RegExp("^[0-9]*$");
	 if(!reg.test(c)){
	        alert("请输入正整数!");
	        return ;
	    }else if(c >= 100){
	    	alert("请输入小于100的正整数");
	    	return;
	    }
	$.ajax({
		url:"update_data_config.html",
		type:"post",
		dataType:"json",
		data:d,
		success:function(data){
			if(data.error>0){
				alert("信息输入不完整！");
			}
			else if(data.result>0){
				loadData();
				alert("修改成功！");
			}
		}
	});
}
function loadData(){
	$.ajax({
		url:"system_data_config.html",
		type:"post",
		dataType:"json",
		success:function(data){
			$.each(data.listSysConfig,function(i,sys){
				$(".keyvalue-"+(i+1)+" input").first().val(sys.cfgVal);
				$(".keyvalue-"+(i+1)+" input").last().val(sys.sysid);
				if(i==0){
					$(".keyvalue-"+(i+1)+" input").first().next().val(sys.cfgVal);
				}
			});
		}
	});
}
	
	





