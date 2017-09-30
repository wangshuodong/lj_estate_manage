$(function() {
	var grid = $('#grid')._datagrid({
		pageList:[10,20,30,40,50],
		pageSize:20
	});
	$('#queryButton').click(function(){
		var params = $('#queryForm')._formToJson();
		$(grid).datagrid('load',params);
	});
	var checkSubmitFlg  = false;
//	$('#settleButton').click(function(){
//		if(!$('#settleForm').form('validate')){return;}
//		if(checkSubmitFlg){
//			return;
//		}
//		$('#settleForm')._ajaxForm(function(r){
//			if(r.r){
//				checkSubmitFlg = false;
//				var params = $('#queryForm')._formToJson();
//				$('#Dialog').dialog({closed:true});$('#grid').datagrid('reload',params);
//				}else{
//					checkSubmitFlg = false;
//					alert(r.m);
//					}
//		});
//		checkSubmitFlg = true;
//	});
//	
	$('#Dialog').dialog({buttons:[{text:"保存",handler:function(){
		if(checkSubmitFlg){
			return;
		}
		if(!$('#settleForm').form('validate')){return;}
		$('#settleForm')._ajaxForm(function(r){
			if(r.r){
				checkSubmitFlg = false;
				var params = $('#queryForm')._formToJson();
				$('#Dialog').dialog({closed:true});$('#grid').datagrid('reload',params);
				}else{
					checkSubmitFlg = false;
					alert(r.m);
					}
		});
		checkSubmitFlg = true;
	}},
	                              {text:"关闭",handler:function(){
	                            	  $('#Dialog').dialog({closed:true});
	                              }}]});
	
});

var formatter = {
//		opt : function(value, rowData, rowIndex) {
//			if(rowData.isSettle == "1"){
//				return '<a class="spacing a-blue" onclick="settleUpdate('+rowIndex+');" href="javascript:void(0);">修改</a>';
//			}else{
//				return '<a class="spacing a-green" onclick="settle('+rowIndex+');" href="javascript:void(0);">结算</a>';
//			}
//		}
		
		opt : function(value, rowData, rowIndex) {
			if(rowData.active == "0"){
				return '<a class="spacing a-green" onclick="settle('+rowIndex+');" href="javascript:void(0);">结算</a>';
			}else{
				
				return '<a class="spacing a-blue" onclick="settleUpdate('+rowIndex+');" href="javascript:void(0);">修改</a>';
			}
		}
		
};
function settle(rowIndex){
	var rows = $('#grid').datagrid('getRows');
	var data = rows[rowIndex];
	$('#settleForm').resetForm();
	$('#settleForm').attr('action','report/settle.html');
	$('#settleForm')._jsonToForm(data);
	$('#Dialog').dialog({closed:false,title:"收入结算"});
}

function settleUpdate(rowIndex){
	var rows = $('#grid').datagrid('getRows');
	var data = rows[rowIndex];
	$('#settleForm').resetForm();
	$('#settleForm')._jsonToForm(data);
	$('#settleForm').attr('action','report/updateSettle.html');
	$('#Dialog').dialog({closed:false,title:"结算修改"});
}