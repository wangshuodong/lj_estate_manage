var isCheck=true;
$(function() {
	var now = new Date();
	var nowStr = formatDate(now,'YYYY-MM-DD');
	var grid = $('#grid')._datagrid({
		checkOnSelect:false,
		selectOnCheck:false,
		pageList:[10,20,30,40,50],
		pageSize:20
	});
	$('#queryButton').click(function(){
		var params = $('#queryForm')._formToJson();
		$(grid).datagrid('load',params);
	});
	
	$('#sheetEditDialog').dialog({
		buttons:[{text:'保存',handler:function(){
			if(!isCheck){
				$.messager.alert('提示','请输入0-5000000的正数！');
				return;
			}
			if(!$('#sheetEditForm').form('validate')){return;}
			$('#sheetEditForm')._ajaxForm(function(r){		
				if(r.r){$('#sheetEditDialog').dialog({closed:true});$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
			});
		}
		},{text:'关闭',handler:function(){$('#sheetEditDialog').dialog({closed:true});}}]
	});
});
var formatter = {
		opt : function(value, rowData, rowIndex) {
			if(rowData.settle_amt==0&&rowData.profit==0){
				return '<a class="spacing a-green"  onclick="showtotal('+rowIndex+')" href="javascript:void(0)">结算</a>';
			}
			return '<a class="spacing a-blue" onclick="settleUpdate('+rowIndex+')" href="javascript:void(0)">修改</a>';
		},
		spending:function(value, rowData, rowIndex){
			if(rowData.timediff!=null){
				return value==null?0:value.toFixed(1);
			}
			return ( value ).toFixed(2);
		}
		
};
function showtotal(rowIndex){
	var rows = $('#grid').datagrid('getRows');
	var data = rows[rowIndex];
	$('#sheetEditForm').resetForm();
	$('#sheetEditForm').attr('action','sheet/speeding.html');
	$('#sheetEditForm')._jsonToForm(data);
	$('#sheetEditDialog').dialog({closed:false,title:"渠道结算"});
}
function settleUpdate(rowIndex){
	var rows = $('#grid').datagrid('getRows');
	var data = rows[rowIndex];
	$('#sheetEditForm').resetForm();
	$('#sheetEditForm').attr('action','sheet/updatespeeding.html');
	$('#sheetEditForm')._jsonToForm(data);
	$('#sheetEditDialog').dialog({closed:false,title:"修改渠道结算"});
}

function isNumberOk(value){
	if(isfloat(value.value)){
		if(value.value>0&&value.value<5000000){
			isCheck=true;
		}else{
			isCheck=false;	
		}
	}else{
		isCheck=false;
	}
}

function isfloat(oNum){

	  if(!oNum) return false;

	  var strP=/^\d+(\.\d+)?$/;

	  if(!strP.test(oNum)) return false;

	  try{

	  	if(parseFloat(oNum)!=oNum) return false;

	  }catch(ex){

	    return false;

	  }

	  return true;

}


