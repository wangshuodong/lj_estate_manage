document.write('<base href="' + window.rootPath + '/" />');
$(function() {
	var now = new Date();
	var nowStr = formatDate(now,'YYYY-MM-DD');
	var date = document.getElementsByName("date")[0].value;
	var grid = $('#grid')._datagrid({
		queryParams:{date:date},
		showFooter:true,
		pageList:[10,20,30,40,50],
		pageSize:20
	});
	$('#queryButton').click(function(){
		var params = $('#queryForm')._formToJson();
		$(grid).datagrid('load',params);
	});
	
	var checkSubmitFlg  = false;
	$('#settleButton').click(function(){
		if(!$('#settleForm').form('validate')){return;}
		if(checkSubmitFlg){
			return;
		}
		$('#settleForm')._ajaxForm(function(r){
			if(r.r){
				checkSubmitFlg = false;
				$('#Dialog').dialog({closed:true});$('#grid').datagrid('reload',{date:date});
				}else{
					checkSubmitFlg = false;
					alert(r.m);
					}
		});
		checkSubmitFlg = true;
	});
});

var formatter = {
		opt : function(value, rowData, rowIndex) {
			var isSettle = $("#isSettle").val();
			if(rowData.isSettle == "1"){
				return '<a class="spacing a-blue" onclick="settleUpdate('+rowIndex+');" href="javascript:void(0);">修改</a>';
			}else{
				return '<a class="spacing a-blue" onclick="settle('+rowIndex+');" href="javascript:void(0);">结算</a>';
			}
			
		},
		clickRate: function(value, rowData, rowIndex){
			if(rowData.click==0||rowData.showCount==0){
				return "0.0%";
			}
			return (rowData.click/rowData.showCount).toPercent();
		},
		installRate: function(value, rowData, rowIndex){
			if(rowData.install==0||rowData.showCount==0){
				return "0.0%";
			}
			return (rowData.install/rowData.showCount).toPercent();
		},
		activeRate: function(value, rowData, rowIndex){
			if(rowData.active==0||rowData.install==0){
				return "0.0%";
			}
			return (rowData.active/rowData.install).toPercent();
		},
		activationRatio : function(value, rowData, rowIndex){
			if(rowData.active == null || rowData.active == 0||rowData.click == null || rowData.click == 0){
				return "0.0‰";
			}
			return (rowData.active/rowData.click).toQianPercent();
		},
		thousandsRate :function(value, rowData, rowIndex){
			if(rowData.showCount==0){
				return 0;
			}
			return ((rowData.income - rowData.expense)/rowData.showCount *1000).toFixed(2);
		},
		profit:function(value, rowData, rowIndex){
			return (rowData.income - rowData.expense).toFixed(2);
		},
		expense:function(value, rowData, rowIndex){
			return value.toFixed(2);
		},
		adsName : function(value, rowData, rowIndex){
			if(rowData.adId!=null){
				return '<a href="ads_detail/page.html?adsId='+rowData.adId+'">'+value+'</a>';
			}else{
				return value;
			}
		},
		income:function(value, rowData, rowIndex){
			return value.toFixed(2);
		},
		showRate : function(value, rowData, rowIndex){
			if(rowData.endLoad == null || rowData.endLoad == 0||rowData.showCount == null || rowData.showCount == 0){
				return 0;
			}
			return (rowData.showCount/rowData.endLoad).toPercent();
		},
		downRate : function(value, rowData, rowIndex){
			if(rowData.endLoad == null || rowData.endLoad == 0||rowData.startLoad == null || rowData.startLoad == 0){
				return 0;
			}
			return (rowData.endLoad/rowData.startLoad).toPercent();
		}
	};

function mum_fmt(value){
	var len = value.toString().length;
	var star = len%3;
	var retvalue=value.toString().substr(0,star);
	if(retvalue.length!=0){
		retvalue +=",";
	}
	for(var i=1;i<=len/3;i++){
		retvalue =retvalue+value.toString().substr(star +(i-1)*3,3)+",";
	}
	return retvalue.substr(0,retvalue.length-1);
}

function settle(rowIndex){
	var rows = $('#grid').datagrid('getRows');
	var data = rows[rowIndex];
	$('#settleForm').resetForm();
	$('#settleForm').attr('action','settle.html');
	$('#settleForm')._jsonToForm(data);
	$('#Dialog').dialog({closed:false,title:"结算"});
}

function settleUpdate(rowIndex){
	var rows = $('#grid').datagrid('getRows');
	var data = rows[rowIndex];
	$('#settleForm').resetForm();
	$('#settleForm')._jsonToForm(data);
	$('#settleForm').attr('action','updateSettle.html');
	$('#Dialog').dialog({closed:false,title:"结算修改"});
}