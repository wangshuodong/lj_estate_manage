$(document).ready(function(){
	$("input[name='startdate']").datetimepicker({
		language:'zh-CN',
        todayBtn:1,
		autoclose:1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0,
		format:'yyyy-mm-dd',
		initialDate:new Date()
	}).on('changeDate',function(ev){
		var startdate = $("input[name='startdate']:visible").val();
		//$("input[name='enddate']:visible").datetimepicker('setEndDate');
		$("input[name='enddate']:visible").datetimepicker('setStartDate',startdate);
	});
	
	$("input[name='enddate']").datetimepicker({
		language:'zh-CN',
        todayBtn:1,
		autoclose:1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0,
		format:'yyyy-mm-dd',
		initialDate:new Date(),
	});
	$("input[name='enddate']:visible").datetimepicker('setEndDate',new Date());
})