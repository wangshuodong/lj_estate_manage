var currPage;
$(function() {
	$("input[name='startDate']").datetimepicker({
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
		var startdate = $("input[name='startDate']:visible").val();
		$("input[name='endDate']:visible").datetimepicker('setStartDate', startdate);
	});
	
	$("input[name='endDate']").datetimepicker({
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
	
	$("#queryUserForm button[name='userAdd']").click(function(){
		window.location.href = "addUser.html";
	});
	
	$("#queryUserForm button[name='queryBtn']").click(function(){
		loadList();
	});
	
	loadList();
});

function loadList() {
	var username = $("#userName").val();
	var roleId = $("#roleId").val();
	var status = $("#status").val();
	var certification = $("#certification").val();
	var startDate = $("#startDate").val();
	var serviceId = $("#serviceId").val();
	var endDate = $("#endDate").val();
	var companyName = $("#companyName").val();
	var departmentName = $("#departmentName").val();
	
	var params = {departmentName:departmentName,companyName:companyName,username:username,roleId:roleId,serviceId:serviceId,status:status,certification:certification,startDate:startDate,endDate:endDate};
	showDailyData(params);
}

var formatter = {
	delNull : function(value) {
		if (null == value) {
			return "";
		} else {
			return value;
		};
	},
	status : function(value) {
		if (value == '1') {
			return "激活";
		} else {
			return "暂停";
		}
	},
	type : function(value) {
		if (value == 'dev') {
			return "开发者后台用户";
		} else if (value == 'other') {
			return "管理者后台用户";
		} else {
			return "";
		}
	},
	certification : function(value) {
		if (value == '0') {
			return "未上传";
		} else if (value == '1') {
			return "待审核";
		} else if (value == '2'){
			return "已认证";
		} else {
			return "";
		}
	},
	dealStatus : function(value) {
		if (value == '1') {
			return "暂停";
		} else {
			return "激活";
		}
	}
};

function updStatus(userId,status) {
	if (status == 1) {
		status = 0;
	} else if(status == 0) {
		status = 1;
	}
	$.post('updStatus.html',{userId:userId,status:status},function(r){
		if (r.r) {
			var username = $("#userName").val();
    		var roleId = $("#roleId").val();
    		var serviceId = $("#serviceId").val();
    		var status = $("#status").val();
    		var certification = $("#certification").val();
    		var startDate = $("#startDate").val();
    		var endDate = $("#endDate").val();
    		var companyName = $("#companyName").val();
    		
        	showDailyData({companyName:companyName,username:username,roleId:roleId,serviceId:serviceId,status:status,certification:certification,startDate:startDate,endDate:endDate,pageNo:currPage});
		} else {
			alert(r.m);
		}
		
	},'json');
}

function showDailyData(params) {
	$.post('queryUser.html',params,function(result){
		$("#daily_tbody").html('');
		dataList = result.result;
		
		for(var i in dataList)
		{
			var dataObj=dataList[i];
			var tr_plan = '<tr>'+'<td align="center">'+dataObj.userId+'</td>'
	  				+'<td align="center">'+formatter.delNull(dataObj.username)+'</td>'
	  				+'<td align="center">'+formatter.delNull(dataObj.ptpwd)+'</td>'
	  				+'<td align="center">'+formatter.delNull(dataObj.companyName)+'</td>'
	  				+'<td align="center">'+formatter.delNull(dataObj.qq)+'</td>'
	  				+'<td align="center">'+formatter.delNull(dataObj.phone)+'</td>'
	  				+'<td align="center">'+formatter.delNull(dataObj.email)+'</td>'
	  				+'<td align="center">'+formatter.certification(dataObj.certification)+'</td>'
	  				+'<td align="center">'+formatter.status(dataObj.status)+'</td>'
	  				+'<td align="center">'+formatter.delNull(dataObj.regeditTime)+'</td>'
	  				+'<td align="center">'+formatter.delNull(dataObj.roleName)+'</td>'
	  				+'<td align="center">'+formatter.delNull(dataObj.departmentName)+'</td>'
	  				+'<td align="center">'+formatter.delNull(dataObj.desc)+'</td>'
	  				+'<td align="center"><a href="updUser.html?userId='+dataObj.userId+'">修改</a>|<a onClick="updStatus('
	  				+dataObj.userId+','+dataObj.status+');">'+formatter.dealStatus(dataObj.status)+'</a>|<a href="resetUserPasword.html?userId='+dataObj.userId+'">密码重置</a>|<a href="deleteUser.html?userId='+dataObj.userId+'">删除帐户</a></td>'
	  				+'</tr>' ;
					
			$("#daily_tbody").append(tr_plan);
		}
		
		var options = {
	        alignment:'right',
	        currentPage: result.pageNo,
	        numberOfPages:5,
	        bootstrapMajorVersion:3,
	        totalPages: result.totalPage=='0' ? 1 :result.totalPage ,
	        onPageChanged:function(event, oldPage, newPage){
	        	currPage = newPage;
	        	var username = $("#userName").val();
	    		var roleId = $("#roleId").val();
	    		var status = $("#status").val();
	    		var serviceId = $("#serviceId").val();
	    		var certification = $("#certification").val();
	    		var startDate = $("#startDate").val();
	    		var endDate = $("#endDate").val();
	        	showDailyData({username:username,roleId:roleId,serviceId:serviceId,status:status,certification:certification,startDate:startDate,endDate:endDate,pageNo:newPage});
	        }
	    };
		
		$("#d_totalRecord").html(result.totalRecord);
		$("#d_pageNo").html(result.pageNo);
		$("#d_totalPage").html(result.totalPage=='0' ? 1 :result.totalPage);
		$('#d_paginationId').bootstrapPaginator(options);
		
	},'json');
}

function showInfo(userId,username){
	$("#myModalLabel").html("用户名:"+username);
	$("#tempUserId").val(userId);
	$("#imageinfo").attr("src","./download.html?moduleid="+userId+"&filemodule=user");
	$("#showModel").modal({
		show:true
	});
}
function downUserImg(){
	var userId = $("#tempUserId").val();
	window.location.href="./download.html?moduleid="+userId+"&filemodule=user";
}