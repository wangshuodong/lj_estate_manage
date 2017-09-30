$(function() {
	var grid = $('#grid')._datagrid({
		pageList:[10,20,30,40,50],
		pageSize:20,
		toolbar : [{
			text : '添加',
			iconCls : 'icon-add',
			handler : handler_add
		},'-'
		]
	});
	$('#queryButton').click(function(){
		var params = $('#queryForm')._formToJson();
		$(grid).datagrid('load',params);
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
		type : function(value, rowData, rowIndex) {
			if(value == 1){
				return "网站公告";
			}else {
				return "行业资讯";
			}
		},
		opt : function(value, rowData, rowIndex) {
			return '<a class="spacing a-blue" onclick="handler_upd('+rowIndex+');" href="javascript:void(0);">修改</a>'+
			'<a class="spacing a-red" onclick="handler_del('+rowIndex+');" href="javascript:void(0);">删除</a>';
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

