$(function() {
	var grid = $('#grid')._datagrid({
		pageList:[10,20,30,40,50],
		pageSize:20,
		method:'post',
		singleSelect:false,
		toolbar : [{
			text : '添加',
			iconCls : 'icon-add',
			handler : handler_add
		},'-',{
			text : '支付选中',
			iconCls : 'icon-edit',
			handler : function(){payA();}
		},'-',{
			text : '拒绝选中',
			iconCls : 'icon-edit',
			handler : rejectA
		},
		]
	});
	$('#queryButton').click(function(){
		var params = $('#queryForm')._formToJson();
		$(grid).datagrid('load',params);
	});
	$('#idcardDownloadButton').click(function(){
		window.open(window.rootPath+"/finance/downloadIdcard.html");
	});
	$('#payButton').click(function(){
		payAll();
	});
	$('#exportButton').click(function(){
		window.open(window.rootPath+"/finance/export.html");
	});
	$('#settleButton').click(function(){
		$.ajax({
			url:'settleAccount.html',
			dataType:'json',
			data:{date:$("input[name=date]").val(),pwd:$("input[name=pwd]").val()},
			success:function(r){
				if(r.r){
					$.messager.alert('操作提示', r.m,'success');
					$('#grid').datagrid('reload');
				}else{
					$.messager.alert('操作提示', r.m,'error');
				}
			
		}});
	});
	var checkSubmitFlg  = false;
	$('#infoDialog').dialog({
		buttons:[{text:'保存',handler:function(){
			if(!$('#baseForm').form('validate')){return;}
			if(checkSubmitFlg){
				return;
			}
			$('#baseForm')._ajaxForm(function(r){
				if(r.r){
					checkSubmitFlg = false;
					$('#infoDialog').dialog({closed:true});$('#grid').datagrid('reload');
					}else{
						checkSubmitFlg = false;
						alert(r.m);
						}
			});
			checkSubmitFlg = true;
		}},{text:'关闭',handler:function(){$('#infoDialog').dialog({closed:true});}}]
	});
});

var formatter = {
		isauth : function(value, rowData, rowIndex) {
			if(value == 0){
				return "<font color='red'>待完善</font>";
			}else if(value == 1 ) {
				return "<font color='yellow'>待审核</font>";
			}else if(value == 2){
				return "<font color='green'>已认证</font>";
			}
		},
		status:function(value, rowData, rowIndex){
			if(value == 0){
				return "待付款";
			}else if(value == 1 ) {
				return "<font color='green'>已付款</font>";
			}else if(value == 2){
				return "<font color='red'>拒绝</font>";
			}
		},
		date:function(value, rowData, rowIndex){
			return rowData.sdate +"至"+ rowData.edate;
		},
		tax:function(value, rowData, rowIndex){
			return (rowData.bonus+rowData.earn-rowData.earn_nofee).toFixed(2);
		},
		opt : function(value, rowData, rowIndex) {
			return '<a class="spacing a-blue" onclick="payB('+rowIndex+');" href="javascript:void(0);">付款</a>'+
			'<a class="spacing a-red" onclick="rejectB('+rowIndex+');" href="javascript:void(0);">拒绝</a>'+
			'<a class="spacing a-green" onclick="idcard('+rowData.fid+');" href="javascript:void(0);">查看身份证</a>';
		},
		
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

/*修改*/
function handler_upd(rowIndex) {
	$('#baseForm').resetForm();
	$('#baseForm').attr('action','update.html');
	var rows = $('#grid').datagrid('getRows');
	var data = rows[rowIndex];
	$('#baseForm')._jsonToForm(data);
	$('#infoDialog').dialog({closed:false,title:'修改'});
	
}
function handler_del(rowIndex) {
	var rows = $('#grid').datagrid('getRows');
	var data = rows[rowIndex];
	$.messager.confirm('操作提示', '确定要删除该渠道？', function(r){
		if (r){
			$._ajaxPost('delete.html',{id:data.id}, function(r){
				if(r.r){$('#grid').datagrid('reload');}else{alert(r.m);}
			});
		}
	});
}
 

function handler_add() {
	$('#baseForm').resetForm();
	$("#id").val('');
	$('#baseForm').attr('action','add.html');
	$('#infoDialog').dialog({closed:false,title:'新增渠道'});
}
function idcard(fid){
	if(!fid){
		$.messager.alert('操作提示', "用户没有上传身份证",'error');
		return;
	}
	$("#idcard_img").attr("src",window.rootPath +"/document/file-"+fid+".do");
	$('#idcardDialog').dialog({closed:false,title:'查看身份证'});

}

//支付查询后的所有结果
function payAll(){
	pay("",2);
}
//支付选中的
function payA(){
	var rows = $('#grid').datagrid('getSelections');
	if(rows && rows.length>=1){
		var ids = "";
		for ( var i = 0; i < rows.length; i++) {
			ids += rows[i].id+","
		}
		pay(ids,1);
	}else {
		$.messager.alert('操作提示', "请选择需要支付的记录",'error');
	}
}

//支付当前记录
function payB(rowIndex){
	var rows = $('#grid').datagrid('getRows');
	var data = rows[rowIndex];
	pay(data.id,1);
}

function pay(ids,type){

		$.ajax({url:window.rootPath +'/finance/pay.html',
			data:{type:type,ids:ids},
			dataType:'json',
			success:function(r){
				if(r.r){
					$.messager.alert('操作提示', r.m,'success');
				}else{
					$.messager.alert('操作提示', r.m,'error');
				}
				$('#grid').datagrid('reload');
			
		}});
	
}
//拒绝选中记录
function rejectA(){
	var rows = $('#grid').datagrid('getSelections');
	if(rows && rows.length>=1){
		var ids = "";
		for ( var i = 0; i < rows.length; i++) {
			ids += rows[i].id+",";
		}
		reject(ids);
	}else {
		$.messager.alert('操作提示', "请选择需要拒绝支付的记录",'error');
	}
}
//拒绝当前记录
function rejectB(rowIndex){
	var rows = $('#grid').datagrid('getRows');
	var data = rows[rowIndex];
	reject(data.id);
}
function reject(ids){
		$.ajax({url:window.rootPath +'/finance/reject.html',
					data:{ids:ids},
					dataType:'json',
					success:function(r){
						if(r.r){
							$.messager.alert('操作提示', r.m,'success');
						}else{
							$.messager.alert('操作提示', r.m,'error');
						}
						$('#grid').datagrid('reload');
					}});

	

}


