//view data  table 数据
var dataList = [];
var today = formatDate(new Date(), "YYYY-MM-DD");

$(function() {
	$("input[name='startdate']").datetimepicker({
		language : 'zh-CN',
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		forceParse : 0,
		format : 'yyyy-mm-dd',
		initialDate : new Date()
	}).on(
			'changeDate',
			function(ev) {
				var startdate = $("input[name='startdate']:visible").val();
				$("input[name='enddate']:visible").datetimepicker(
						'setStartDate', startdate);
			});

	$("input[name='enddate']").datetimepicker({
		language : 'zh-CN',
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		forceParse : 0,
		format : 'yyyy-mm-dd',
		initialDate : new Date()
	});

	$("#versionSdkForm button[name='queryBtn']").click(function() {
		operate.queryData();
	});

	$("#versionSdkForm button[name='versionSdkAdd']").click(function() {
		window.location.href = "version_sdk_add.html";
	});
});

/**
 * 常用操作的方法定义
 * 
 */
var operate = {
	queryData : function(type) {
		var formObj = $("#versionSdkForm");
		var startDate = formObj.find("input[name='startdate']").val();
		var endDate = formObj.find("input[name='enddate']").val();
		var sv = formObj.find("input[name='sv']").val();
		var name = formObj.find("input[name='name']").val();
		var mid = formObj.find("input[name='mid']").val();

		showDailyData({
			startDate : startDate,
			endDate : endDate,
			sv : sv,
			mid : mid,
			name : name
		});

	},
	exportFile : function(rowIndex, type) {
		var username = $("#currenName").val();
		switch (type) {
		case 'daily':
			var rowObj = dailyData[rowIndex];
			var url = "export-work-day.html?id=" + rowObj.id + "&username="
					+ username;
			$("#downFrame").attr("src", url);
			break;
		case 'week':
			var rowObj = weekData[rowIndex];
			var url = "export-work-week.html?id=" + rowObj.id + "&username="
					+ username;
			$("#downFrame").attr("src", url);
			break;
		case 'month':
			var rowObj = monthData[rowIndex];
			var url = "export-work-month.html?id=" + rowObj.id
					+ "&sign=2&username=" + username;
			$("#downFrame").attr("src", url);
			break;
		case 'quarter':
			var rowObj = quarterData[rowIndex];
			var url = "export-work-quarter.html?id=" + rowObj.id
					+ "&sign=3&username=" + username;
			$("#downFrame").attr("src", url);
			break;
		default:
			break;
		}
	}
};

/**
 * 字段的格式化方法定义
 */
var formatter = {
	status : function(value) {
		if (value == 1)
			return "已完成";
		else if (value == -1)
			return "未完成";
		else if (value == -2)
			return "延误";
		else {
			return "";
		}
		;
	},
	plandate : function(starttime, endtime) {
		if (starttime == null && endtime == null)
			return "";
		else
			return starttime.substring(0, starttime.length - 3) + '-'
					+ endtime.substring(0, starttime.length - 3);

	},
	content : function(plans) {
		var contents = "";
		for ( var i in plans) {
			contents += '<span title="' + plans[i].content
					+ '" style="white-space:nowrap;">' + (parseInt(i) + 1)
					+ "、" + cutContent(plans[i].content, 15) + '</span><br/>';
			if (i == 4) {
				contents += "...";
				break;
			}
		}
		return contents;
	},
	planStatus : function(plans) {
		var status = "";
		for ( var i in plans) {
			status += formatter.status(plans[i].status) + "<br />";
			if (i == 4) {
				status += "...";
				break;
			}
		}
		return status;
	},
	finishedTime : function(plans) {
		var str = "";
		for ( var i in plans) {
			str += formatter.plandate(plans[i].starttime, plans[i].endtime)
					+ '<br />';
			if (i == 4) {
				str += "...";
				break;
			}
		}
		return str;
	},
	nextPlan : function(nextPlans) {
		var str = "";
		for ( var i in nextPlans) {
			str += '<span title="' + nextPlans[i].content + '" >'
					+ (parseInt(i) + 1) + "、"
					+ cutContent(nextPlans[i].content, 10) + "</span><br/>";
			if (i == 4) {
				str += "...";
				break;
			}
		}
		return str;
	},
	opt : function(rowIndex, type) {
		var optText = '<button type="button" class="btn btn-link btn-xs" onclick="operate.exportFile('
				+ rowIndex + ',\'' + type + '\')">导出</button>';
		return optText;
	}
};

function modifyVersionSdk(id) {
	window.location.href = "modifyVersionSdk.html?id=" + id;
}
function deleteVersionSdk(mid){
	 var statu = confirm("确认删除ID为【"+mid+"】的子版本数据?");
    if(!statu){
        return;
    }
    $.ajax({
		url:"deleteVersionSdk.html",
		type:"post",
		dataType:"json",
		data:"mid="+mid,
		success:function(data){
			if(data.result>0){
				alert("操作成功！");
				showDailyData('none');
			}
			else{
				alert("操作失败！");
			}
		}
	});
}


// 表单方式提交
function showDailyData(params) {
	$
			.post(
					'queryVersionSdk.html',
					params,
					function(result) {
						$("#daily_tbody").html('');
						dataList = result.result;
				
						$.each(dataList,function(i,dataObj){
							var tr_plan = '<tr '+ (i % 2 == 0 ? 'class="active"': 'class="success"')+ '>'
									+ '<td align="center">'+ dataObj.mid+ '</td>'
									+ '<td align="center">'+ dataObj.sv+ '</td>'
									+ '<td align="center">'+ dataObj.name+ '</td>'
									+ '<td class="style-center"><a href="javascript:void(0);" onclick="modifyVersionSdk('+ dataObj.mid + ')">修改</a> | '+
									'<a href="javascript:void(0);" onclick="deleteVersionSdk('+ dataObj.mid + ')">删除</a></td>';
									+ '</tr>';
							$("#daily_tbody").append(tr_plan);
						});
						var options = {
							alignment : 'right',
							currentPage : result.pageNo,
							numberOfPages : 5,
							bootstrapMajorVersion : 3,
							totalPages : result.totalPage == '0' ? 1: result.totalPage,
							onPageChanged : function(event, oldPage, newPage) {
								var formObj = $("#versionSdkForm");
								var startDate = formObj.find("input[name='startdate']").val();
								var endDate = formObj.find(	"input[name='enddate']").val();
								var sv = formObj.find("input[name='sv']").val();
								var name = formObj.find("input[name='name']").val();
								var mid = formObj.find("input[name='mid']").val();
								
								showDailyData({
									startDate : startDate,
									endDate : endDate,
									sv : sv,
									mid : mid,
									name : name,
									pageNo : newPage
								});
							}
						};

						$("#d_totalRecord").html(result.totalRecord);
						$("#d_pageNo").html(result.pageNo);
						$("#d_totalPage").html(result.totalPage == '0' ? 1 : result.totalPage);
						$('#d_paginationId').bootstrapPaginator(options);
					}, 'json');
}
showDailyData('none');