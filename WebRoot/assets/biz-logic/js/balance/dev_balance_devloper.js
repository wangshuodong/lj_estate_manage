var currPage;
$(function(){
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
		$("input[name='enddate']:visible").datetimepicker('setStartDate', startdate);
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
		initialDate:new Date()
	});
	
	$("input[name='edate']").datetimepicker({
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
	
	$("input[name='pay_date']").datetimepicker({
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
	
	$("#userBalanceForm button[name='queryBtn']").click(function(){
		operate.queryData();
	});
	
	var formObj=$("#userBalanceForm");
	var status = formObj.find("select[name='status']").val();
	var uid = formObj.find("input[name='uid']").val();
	var certification = formObj.find("select[name='certification']").val();
	var startDate = formObj.find("input[name='startdate']").val();
	var endDate = formObj.find("input[name='enddate']").val();
	var edate = formObj.find("input[name='edate']").val();
	
	var url = "export.html?" + "status=" + status + "&uid=" + uid + "&certification=" + certification + "&startDate=" + startDate + "&endDate=" + endDate + "&edate=" + edate;
	$("#excelExport").attr("href",url);
	
	$("#pay_date").val(formatDate(new Date(), "YYYY-MM-DD"));
	addApp_initialize();
	$('#detail_div').dialog({
		autoOpen: false,
		width: 650,
		open:function(){
		},
		close:function(){
			dialog_input_reset();
		}
	});
	
	operate.queryData();
});

/**
 * 常用操作的方法定义
 * 
 */
var operate = {
	queryData : function(){
		var formObj=$("#userBalanceForm");
		var status = formObj.find("select[name='status']").val();
		var uid = formObj.find("input[name='uid']").val();
		var certification = formObj.find("select[name='certification']").val();
		var startDate = formObj.find("input[name='startdate']").val();
    	var endDate = formObj.find("input[name='enddate']").val();
    	var edate = formObj.find("input[name='edate']").val();
    	
		showDailyData({status:status,uid:uid,certification:certification,startDate:startDate,endDate:endDate,edate:edate});

	}
};

/**
 * 字段的格式化方法定义
 */
var formatter = {
	status : function(value){
		if(value == 0)
			return "未付款";
		else if(value == 1)
			return "已付款";
		else if(value == 2)
			return "已拒绝";
		else {
			return "";
		};
	},
	certification : function(value){
		if(value == 0)
			return "未上传";
		else if(value == 1)
			return "待审核";
		else if(value == 2)
			return "已认证";
		else {
			return "";
		};
	},
	delNull : function(value){
		if (null == value || 'null' == value) {
			return "";
		} else {
			return value;
		};
	}
};

function showDailyData(params)
{
	$.post('queryBalance_devloper.html',params,function(result){
		$("#daily_tbody").html('');
		dataList = result.result;
		var index = 0;
		
		var sumTr = '<tr><td colspan="4">汇总</td>'
			+'<td>'+formatter.delNull(result.collect.earn)+'</td>'
			+'<td>'+formatter.delNull(result.collect.bonus)+'</td>'
			+'<td>'+formatter.delNull(result.collect.fee)+'</td>'
			+'<td>'+formatter.delNull(result.collect.earnNofee)+'</td>'
			+'<td colspan="5"></td>'+'</tr>';
		$("#daily_tbody").append(sumTr);
		
		for(var i in dataList)
		{
			var dataObj=dataList[i];
			index = (Number(result.pageNo) - 1) * Number(result.pageSize) + Number(i) + 1;
			var tr_plan = '<tr>'
	  				+'<td align="center">'+index+'</td>'
	  				+'<td align="left">用户名:'+dataObj.username+ '<br/>&nbsp;&nbsp;&nbsp;QQ:'
	  				+ dataObj.qq + '<br/>客服名称:' + dataObj.serviceName + '</td>'
	  				+'<td align="center">'+formatter.certification(dataObj.certification)+'</td>'
	  				+'<td align="left">'+dataObj.sdate+'<br/>至<br/>'+dataObj.edate+'</td>'
	  				+'<td align="center">'+dataObj.earn+'</td>'
	  				+'<td align="center">'+dataObj.bonus+'</td>'
	  				+'<td align="center">'+dataObj.fee+'</td>'
	  				+'<td align="center">'+dataObj.earnNoFee+'</td>'
	  				+'<td align="left">'+'银行名称:'+dataObj.bankname+'<br/>卡号:'+dataObj.bankNo+'<br/>账户名:'+dataObj.bankUserName+'</td>'
	  				+'<td align="center">'+formatter.status(dataObj.status)+'</td>'
	  				+'<td align="center">'+formatter.delNull(dataObj.payDate)+'</td>'
	  				+'<td class="left" style="word-break:break-all">'+formatter.delNull(dataObj.remark)+'</td>'
	  				+'</tr>';
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
	        	var formObj=$("#userBalanceForm");
	    		var status = formObj.find("select[name='status']").val();
	    		var uid = formObj.find("input[name='uid']").val();
	    		var certification = formObj.find("select[name='certification']").val();
	    		var startDate = formObj.find("input[name='startdate']").val();
	        	var endDate = formObj.find("input[name='enddate']").val();
	        	var edate = formObj.find("input[name='edate']").val();
	        	showDailyData({status:status,uid:uid,certification:certification,startDate:startDate,endDate:endDate,edate:edate,pageNo:newPage});
	        }
	    };
		
		$("#d_totalRecord").html(result.totalRecord);
		$("#d_pageNo").html(result.pageNo);
		$("#d_totalPage").html(result.totalPage=='0' ? 1 :result.totalPage);
		$('#d_paginationId').bootstrapPaginator(options);
		
	},'json');
}

function addApp_initialize(){
	$('#addApp_div').dialog({
		autoOpen: false,
		width: 650,
		buttons: {
			"提交":function(){
				var payDate = $("#pay_date").val();
				if(null == payDate || "" == payDate){
					alert("请选择付款或拒绝时间");
					$("#pay_date").focus();
					return false;
				}
				$.ajax({
　　　　				url: 'payBalance.html',
    				type:'post',
    				data:{
						id : $("#b_id").val(),
						status :  $("#b_status").val(),
						payDate : payDate,
						remark : $("#remark").val()
    				},
    				dataType:'json',
    				success:function(data) {
    					if(data.r){
    						var id = "b_id_"+$("#b_id").val();
	    					var statusId = "status"+$("#b_id").val();
	    					var opId = "op"+$("#b_id").val();
	    					$('#addApp_div').dialog("close");
	    					
	    					$("#infomation").html(data.message);
	    					$("#infomation").dialog();
	    					
	    					if(data.m != '操作失败'){
	    						if($("#b_status").val() == 1){
		    						$("tr[id="+id+"]").css("background","#80E6A9");
			    					$("td[id="+statusId+"]").html("<span style=\"color:#FFFF66\">已结算</span>");
			    					$("td[id="+opId+"]").html("<span style=\"color:#FFFF66\">已付款</span>");
		    					}else if($("#b_status").val() == 2){
		    						$("tr[id="+id+"]").css("background","#FFFF00");
			    					$("td[id="+statusId+"]").html("<span style=\"color:#111100\">已拒绝</span>");
		    					}
	    					}
	    					
	    					setTimeout(function(){$('#infomation').dialog('close');},2000);
	    					
	    					var formObj=$("#userBalanceForm");
	    		    		var status = formObj.find("select[name='status']").val();
	    		    		var uid = formObj.find("input[name='uid']").val();
	    		    		var certification = formObj.find("select[name='certification']").val();
	    		    		var startDate = formObj.find("input[name='startdate']").val();
	    		        	var endDate = formObj.find("input[name='enddate']").val();
	    		        	var edate = formObj.find("input[name='edate']").val();
	    		        	showDailyData({status:status,uid:uid,certification:certification,startDate:startDate,endDate:endDate,edate:edate,pageNo:currPage});
	    					
    					}else{
    						var id = "b_id_"+$("#b_id").val();
	    					$('#addApp_div').dialog("close");
	    					$("#infomation").html(data.m);
    					}
					}
　　　			});
			}
		},
		open:function(){
		},
		close:function(){
			dialog_input_reset();
		}
	});
	$("#addApp_a").live('click',function(){
		$('#addApp_div').dialog('open');
		return false;
	});
}

function editDialog(id, username,phone, sdate, edate, bankname, bank, earnNofee) {
	dialog_input_reset();
	
	$("#b_op").html("");
	$("#b_id").val(id);

	$("#b_username").html(username);
	$("#b_phone").html(phone);
	$("#b_sdate").html(sdate);
	$("#b_edate").html(edate);
	$("#b_bankname").html(bankname);
	$("#b_bank").html(bank);
	$("#b_earnnofee").html(earnNofee);
	
	$('#addApp_div').dialog('open');
}

function detailDialog(province,payUsername,city,bankname,bankAddress,bankUserName,bankNo,certificate,payTime,earnNoFee) {
	$("#province").html(formatter.delNull(province));
	$("#city").html(formatter.delNull(city));
	$("#bankname").html(formatter.delNull(bankname));
	$("#bankAddress").html(formatter.delNull(bankAddress));
	$("#bankUserName").html(formatter.delNull(bankUserName));
	$("#bankNo").html(formatter.delNull(bankNo));
	$("#certificate").html(formatter.delNull(certificate));
	$("#earnnofee").html(formatter.delNull(earnNoFee));
	$("#payTime").html(formatter.delNull(payTime));
	$("#payUsername").html(formatter.delNull(payUsername));
	$('#detail_div').dialog('open');
}

function dialog_input_reset(){
	
	$("#pay_date").val();
	$("#remark").val("");
}