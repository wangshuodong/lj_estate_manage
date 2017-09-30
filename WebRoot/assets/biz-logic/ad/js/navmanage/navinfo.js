$(function(){
	var grid = $('#grid')._datagrid({
		pageList:[10,20,30,40,50],
		pageSize:20,
		toolbar : [{
			text : '添加新导航',
			iconCls : 'icon-add',
			handler : add
		},'-'
		]
	});
	/*
	$('#queryButton').click(function(){
		var params = $('#queryForm')._formToJson();
		$(grid).datagrid('load',params);
	});
	*/
	$('#navEditDialog').dialog({
		buttons:[{text:'保存',handler:function(){
			if(!$('#navEditForm').form('validate')){return;}
			$('#navEditForm')._ajaxForm(function(r){		
				if(r.r){$('#navEditDialog').dialog({closed:true});$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
			});
		}
		},{text:'关闭',handler:function(){$('#navEditDialog').dialog({closed:true});}}]
	});
});

var formatter = {
		opt : function(value, rowData, rowIndex) {
			var html= '<a class="spacing a-green"  onclick="upd('+rowIndex+')" href="javascript:void(0)">修改</a>';
				html+='<a class="spacing a-blue" onclick="del('+rowIndex+')" href="javascript:void(0)">删除</a>';
			return html;
		}
};

function add(rowIndex){
	var rowlength = $('#grid').datagrid('getRows').length;
	if(rowlength>0){
		alert('已存在一条数据，不能在添加！');
		return;
	}
	$('#navEditForm').resetForm();
	$('#navEditForm').attr('action','navmanage/add.html');
	$('#navEditDialog').dialog({closed:false,title:"添加导航"});
}


function upd(rowIndex){
	var rows = $('#grid').datagrid('getRows');
	var data = rows[rowIndex];
	$('#navEditForm').resetForm();
	$('#navEditForm').attr('action','navmanage/upd.html');
	$('#navEditForm')._jsonToForm(data);
	$('#navEditDialog').dialog({closed:false,title:"修改导航"});
}

function del(rowIndex){
	var rows = $('#grid').datagrid('getRows');
	var data = rows[rowIndex];
	$.messager.confirm('操作提示', '确定要删除该导航？', function(r){
		if (r){
			$._ajaxPost('navmanage/del.html',{id:data.id}, function(r){
				if(r.r){$('#grid').datagrid('reload');}else{alert(r.m);}
			});
		}
	});
}