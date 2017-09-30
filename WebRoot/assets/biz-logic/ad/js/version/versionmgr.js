$(function() {
	var now = new Date();
	var nowStr = formatDate(now,'YYYY-MM-DD');
	var checkSubmitFlg  = false;
	$('#versionDialog').dialog({
		buttons:[{text:'保存',handler:function(){
			if(!$('#versionForm').form('validate')){return;}
			if(checkSubmitFlg){
				return;
			}
			$('#versionForm')._ajaxForm(function(r){
				if(r.r){
					checkSubmitFlg = false;
					$('#versionDialog').dialog({closed:true});$('#grid').datagrid('reload');
					}else{
						checkSubmitFlg = false;
						alert(r.m);
						}
			});
			checkSubmitFlg = true;
		}},{text:'关闭',handler:function(){$('#versionDialog').dialog({closed:true});}}]
	});
	var grid = $('#grid')._datagrid({
		pageList:[10,20,30,40,50],
		pageSize:20,
		toolbar : [{
			text : '添加新版本',
			iconCls : 'icon-add',
			handler : handler_add
		},'-'
		]
	});
	$('#queryButton').click(function(){
		var params = $('#queryForm')._formToJson();
		$(grid).datagrid('load',params);
	});
	function handler_add(){
		$('#versionForm').resetForm();
		$('#versionForm').attr('action','version/add.html');
		$('#versionDialog').dialog({closed:false,title:'新增版本记录'});
	}
});
var formatter = {
		opt : function(value, rowData, rowIndex) {
			return '<a class="spacing a-blue" onclick="handler_upd('+rowIndex+');" href="javascript:void(0);">修改</a>'+
			'<a class="spacing a-red" onclick="handler_del('+rowIndex+');" href="javascript:void(0);">删除</a>';
		},
		isNewest : function(value, rowData, rowIndex) {
			if(value==1){
				return '是';
			}
			return '否';
		}
};

function handler_upd(rowIndex){
	$('#versionForm').resetForm();
	$('#versionForm').attr('action','version/upd.html');
	var rows = $('#grid').datagrid('getRows');
	var data = rows[rowIndex];

	$('#versionForm')._jsonToForm(data);
	var arr =[];
	if(data.channelIds != null){
		arr=data.channelIds.split(",");//得到 渠道id数组
	}
	$('#channelIds').combobox('setValues',arr);//对渠道默认被选中值
	
	$('#versionDialog').dialog({closed:false,title:'修改版本记录'});
}

function handler_del(rowIndex){
	var rows = $('#grid').datagrid('getRows');
	var data = rows[rowIndex];
	if(data.isNewest==1){
		$.messager.alert('提示','该版本为当前最新版本，不能删除！');
		return;
	}
	$.messager.confirm('操作提示', '确定要删除该记录？', function(r){
		if (r){
			$._ajaxPost('version/del.html',{id:data.id}, function(r){
				if(r.r){$('#grid').datagrid('reload');}else{alert(r.m);}
			});
		}
	});
}