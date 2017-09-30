var token = "token";
$(function(){
	$("input[name='settleDate']").datetimepicker({
		language:'zh-CN',
        todayBtn:1,
		autoclose:1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0,
		format:'yyyy-mm-dd',
		initialDate:new Date()
	});
	
	$("#settleForm button[name='settleBtn']").click(function(){
		$("#settleBtn").unbind('click');
		var formObj = $("#settleForm");
		var settleDate = formObj.find("input[name='settleDate']").val();
		var settlePass = formObj.find("input[name='settlePass']").val();
		
		if ($.trim(settleDate) == '') {
			alert("日期不能为空!");
			return;
		}
		
		var reg = /\d{4}-\d{2}-\d{2}/;
		if (!reg.test(settleDate)) {
			alert("日期输入有误!");
			return;
		}
    	
    /*	$.post('settle.html',{settleDate:settleDate,settlePass:settlePass},function(result){
    		alert(result.m);
    		$("#settleBtn").bind('click');
    	},'json');*/
    	
    	$.ajax({
    		url:"settle.html",
    		type:"post",
    		dataType:"json",
    		data:{
    			settleDate:settleDate,
    			settlePass:settlePass,
    			token:token
    		},
    		beforeSend:function(){
				$("#loading").modal('show');
			},
			complete:function(){
				$("#loading").modal('hide');
			},
    		success:function(data){
    			alert(data.m);
    			token = data.token;
    			location.reload();
        		$("#settleBtn").bind('click');
    		}
    	})
	});
});