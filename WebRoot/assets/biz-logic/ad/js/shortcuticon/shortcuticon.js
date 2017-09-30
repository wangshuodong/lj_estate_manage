$(function() {
	var grid = $('#grid')._datagrid({
		pageList:[10,20,30,40,50],
		pageSize:20,
		toolbar : [{
			text : '添加快捷图标',
			iconCls : 'icon-add',
			handler : handler_add
		},'-'
		]
	});
	$('#queryButton').click(function(){
		var params = $('#queryForm')._formToJson();
		$(grid).datagrid('load',params);
	});
	$('#shortcuticonEditDialog').dialog({
		buttons:[{text:'保存',handler:function(){
			if(!$('#shortcuticonEditForm').form('validate')){return;}
			var check = false;
			var pa = {shortcuticonName:$("#shortcuticonEditForm").find("[name='shortcuticonName']").val(),
					id:$("#shortcuticonEditForm").find("[name='id']").val()};
			if($("#shortcuticonEditForm").attr("action") =="shortcuticon/add.html"){
				if($("#shortImg").val()==''){
					alert("图片不能为空！");
					return;
				}
			}
			$.ajax({
				async:false,
				dataType:"json",
				url:"shortcuticon/checkName.html",
				data:pa,
				success:function(data,status){
					check= data;
					$.ajaxSetup({async:true});
				}
				
			});
			if(!check){
				alert("名称重复，请重新输入！");
				return;
			}
			$('#shortcuticonEditForm')._ajaxForm(function(r){
				if(r.r){
					$('#shortcuticonEditDialog').dialog({closed:true});
					$('#grid').datagrid('reload');
					}else{
						alert(r.m);
						}
			});
		}},{text:'关闭',handler:function(){$('#shortcuticonEditDialog').dialog({closed:true});}}]
	});
});

var formatter = {
		opt : function(value, rowData, rowIndex) {
			return '<a class="spacing a-blue" onclick="handler_upd('+rowIndex+');" href="javascript:void(0);">修改</a><a class="spacing a-red" onclick="handler_del('+rowIndex+');" href="javascript:void(0);">删除</a>';
		},
		pic:function(value, rowData, rowIndex){
			return '<img height="25" style="margin-left:37px" src="'+value+'"/>';
		},
		status:function(value, rowData, rowIndex){
			if(value == '1'){
				return '<font color="green">获取</font>';
			}else{
				return '<font color="red">不获取</font>';
			}
		}
	};


/*修改*/
function handler_upd(rowIndex) {
	$('#shortcuticonEditForm').resetForm();
	$('#shortcuticonEditForm').attr('action','shortcuticon/update.html');
	var rows = $('#grid').datagrid('getRows');
	var data = rows[rowIndex];
	$('#beforePic').find('img').attr('src',data.shortcuticonPath);//展示原图
	$('#beforePic').show();
	$('#shortcuticonEditForm')._jsonToForm(data);
	$('#shortcuticonEditDialog').dialog({closed:false,title:'修改快捷图片'});
}
function handler_del(rowIndex) {
	var rows = $('#grid').datagrid('getRows');
	var data = rows[rowIndex];
	$.messager.confirm('操作提示', '确定要删除该快捷图片？', function(r){
		if (r){
			$._ajaxPost('shortcuticon/delete.html',{id:data.id}, function(r){
				if(r.r){$('#grid').datagrid('reload');}else{alert(r.m);}
			});
		}
	});
}

function handler_add() {
	$('#shortcuticonEditForm').resetForm();
	$('#beforePic').hide();
	$("#id").val('');
	$('#shortcuticonEditForm').attr('action','shortcuticon/add.html');
	$('#shortcuticonEditDialog').dialog({closed:false,title:'新增快捷图片'});
}

/*显示大图 */
function showBigPic(){
	//$('#bigPic').attr("src",$('#beforePic').show().find('img').attr("src"));
	//$('#bigPicDiv').dialog({closed:false,title:'显示大图'});
}
$.fn.validatebox.defaults.rules.remote.message = '名称重复，请重新输入！';