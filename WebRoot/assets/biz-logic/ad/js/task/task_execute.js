$(function() {

});

function execute(btn){
	var task = $(btn).parent().siblings().find("[name='task']").val();
	var date = $(btn).parent().siblings().find("[name='executeDate']").val();
	if(date == null || date == ''){
		$.messager.alert('提示', '请选择需要执行的时间','error');
		return;
	}
	$._ajaxPost('task/execute.html',{task:task,date:date},function(r){
		if(r.r){
			$.messager.alert('操作提示', r.m,'info');
		}else{
			$.messager.alert('操作提示', r.m,'error');
		}
	});
}
