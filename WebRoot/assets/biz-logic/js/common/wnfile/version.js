var addjar,completed = 0,adpid;
$(document).ready(function() {
	addinitialize();
	if (id != "") {
		loadAdWnfile();
	}
})

function loadAdWnfile() {
	addjar.changeModuleid(id);
	addjar.loadWnfile();
}

function addinitialize() { 
	addjar = new WnFile("#addbutton_jar", {
		//记录id
		moduleid : '',
		//那个模块
		filemodule : 'kvfile',
		//存储在服务器什么地址
		path : '/newiadpush/versionJar/',
		maxlength : 1,
		//允许上传文件类型
		accept : "jar",
		onComplete:function(){
			if(id == ""){
				location.href=location.href+"?id="+addjar.getModuleid();
			}else{
				location.href=location.href;
			}
		}
	});
	$("#addsubmit").button();
	$("#addsubmit").click(function(){
		checkUnqiueKv();
	});
}
function addForm_submit() {
	$("#addsubmit").html("提交中...");
	$("#returnKit").attr("disabled",true);
	
	var d = $("#submitForm"). serialize(); 
	$.ajax({
		url: 'version_kit_data_add.html',
    	type:'post',
    	data:d+"&isButton=isButton",
    	dataType:'json',
    	success:function(data){
    		addjar.changeModuleid(data.id);
    		addjar.submit();
    		
    	}
	});
}
function updateForm(){
	$("#addsubmit").html("提交中...");
	$("#returnKit").attr("disabled",true);
	var d = $("#submitForm"). serialize();
	var fil;
	if(filea==undefined){fil="none";}else{fil=filea.get(0).baseURI;}
	$.ajax({
		url:"version_data_kit_modify.html",
		type:"post",
		dataType:"json",
		data:d+"&isButton=isButton&isDel="+fil,
		success:function(data){
			if(data.result>0){
				if(filea.get(0).baseURI==""){
					addjar.changeModuleid(data.id);
					addjar.submit();
				}
				else{
					$("input[name='flagKv']").val($("input[name='kv']").val());
					$("#returnKit").attr("disabled",false);
					$("#addsubmit").html("提&nbsp;&nbsp;交");
				}
			}
			else{
 				alert("操作失败！");
 			}
		}
	});
}
$(function(){
	$("#kv").focusout(function(){
		checkUnqiueKv();
	});
});
function checkUnqiueKv(){
	if($("input[name='flagKv']").val()==$("input[name='kv']").val()&&$("input[name='flagKv']").val()!=""){
		$(".kv_tip").html("<span style='color:green;'>*</span>");
		addOrUpdate();
	}
	else{
		if($("#kv").val()==""){
			$(".kv_tip").html("<span style='color:red;'>* 子版本号输入不能为空!</span>");
			return;
		}
		var d = $("#submitForm"). serialize();
		$.ajax({
			url:"checkUniqueKv.html",
			type:"post",
			dataType:"json",
			data:d,
			success:function(data){
				if(data.kit!=null){
					$(".kv_tip").html("<span style='color:red;'>* 该子版本号已存在，请重新输入!</span>");
					return;
				}
				else{
					$(".kv_tip").html("<span style='color:green;'>*</span>");
					addOrUpdate();
					
				}
			}
		});
	}
   
}
function addOrUpdate(){
	if($("#name").val()==""){
			$(".name_tip").html("<span style='color:red;'>* 名称输入不能为空!</span>");
			return;
		}
	    $(".name_tip").html("<span style='color:green;'>*</span>");
		if(id!=""){
			if($("input[name='file']").val()==""&&filea.get(0).baseURI==""){
				$(".jar_tip").html("<span style='color:red;'>* 请上传JAR文件！</span>");
				return;
			}
			updateForm();
		}
		else{
			if($("input[name='file']").val()==""){
				$(".jar_tip").html("<span style='color:red;'>* 请上传JAR文件！</span>");
				return;
			}
			addForm_submit();
		}
}