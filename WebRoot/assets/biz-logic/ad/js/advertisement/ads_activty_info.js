$(function() {
	
	$('#activtyEditDialog').dialog({
		buttons:[{text:'保存',handler:function(){
			if(!$('#activtyEditForm').form('validate')){return;}
			$('#activtyEditForm')._ajaxForm(function(r){
				if(r.r){$('#activtyEditDialog').dialog({closed:true});$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
			});
		}},{text:'关闭',handler:function(){$('#activtyEditDialog').dialog({closed:true});}}]
	});
	
	$('#flushDataDialog').dialog({
		buttons:[{text:'确定',handler:function(){
			if(!$('#flushDataForm').form('validate')){return;}
			$('#flushDataForm')._ajaxForm(function(r){
				if(r.r){$('#flushDataDialog').dialog({closed:true});$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
			});
		}},{text:'关闭',handler:function(){$('#flushDataDialog').dialog({closed:true});}}]
	});
	
	/*自定义验证：整数和小数*/
	$.extend($.fn.validatebox.defaults.rules, {
		digit:{
			validator: function (value,param) {
				var reg = new RegExp(/^[0-9]+([.][0-9]{1,2})?$/);
				if(Number(value)==0){
					$.fn.validatebox.defaults.rules.digit.message = '不能为0！';
					return false;
				}else if(Number(value)>=Number(param)){
					$.fn.validatebox.defaults.rules.digit.message = '数值过大！';
					return false;
				}else if(!reg.test(value)){
					$.fn.validatebox.defaults.rules.digit.message = '请输入正确的整数或者小数！';
					return false;
				}else{
					return true;
				}
			},
			message: ''
		}
	});
	
	/*重写显示方式，footer算作数据行*/
	var myview = $.extend({}, $.fn.datagrid.defaults.view, {
    renderFooter: function(target, container, frozen){
        var opts = $.data(target, 'datagrid').options;
        var rows = $.data(target, 'datagrid').footer || [];
        var fields = $(target).datagrid('getColumnFields', frozen);
        var table = ['<table class="datagrid-ftable" cellspacing="0" cellpadding="0" border="0"><tbody>'];
         
        for(var i=0; i<rows.length; i++){
            var styleValue = opts.rowStyler ? opts.rowStyler.call(target, i, rows[i]) : '';
            var style = styleValue ? 'style="' + styleValue + '"' : '';
            table.push('<tr class="datagrid-row" datagrid-row-index="' + i + '"' + style + '>');
            table.push(this.renderRow.call(this, target, fields, frozen, i, rows[i]));
            table.push('</tr>');
        }
         
        table.push('</tbody></table>');
        $(container).html(table.join(''));
    }
	});
	
	var grid = $('#grid')._datagrid({
		view:myview,
		checkOnSelect:false,
		selectOnCheck:false,
		showFooter:true,
		remoteSort:true,
		rowStyler:function(index,row){//更改foot颜色
			if(row.id==null){
				return 'background-color:#F4F4F4;';
			}
		},
		toolbar : [{
					text : '添加数据',
					iconCls : 'icon-add',
					handler : handler_add
				  }]
	});
	
	$('#queryButton').click(function(){
		var params = $('#queryForm')._formToJson();
		$(grid).datagrid('load',params);
	});
	
	$('#flushData').click(function(){
		$('#flushDataForm').attr('action','ads_activty_info/manualFlush.html').resetForm();
		$('#flushDataDialog').dialog({
			closed:false,
			title:'手动数据刷取'
		});
	});
});

var formatter = {
	status : function(value, rowData, rowIndex) {
		if(value == 0){ return '<font color=green>已录入</font>'; } 
		else if(value == 1){ return '<font color=black>未录入</font>'; }
		else{return '';}//合计
	}, 
	adsName : function(value, rowData, rowIndex){
		if(rowData.id!=null){
			return '<a class="a-link" href="ads_detail/page.html?adsId='+rowData.id+'">'+value+'</a>';
		}else{
			return value;
		}
	},
	adsSize : function(value, rowData, rowIndex) {
		if(value == 0.00){ return value; } 
		else{ return Math.round((value/(1024*1024))*100)/100; }
	}, 
	price : function(value, rowData, rowIndex){
		if(value==null){
			return rowData.id==null?'':'0.00';
		}
		return Number(value).toFixed(2);
	},
	income : function(value, rowData, rowIndex){
		return value==null?'0.00':value;
	},
	count : function(value, rowData, rowIndex){
		return value==null?'0':value;
	},
	opt : function(value, rowData, rowIndex) {
		var html= '<a class="spacing a-blue" onclick="update('+rowIndex+');" href="javascript:void(0);">修改</a>';
		return rowData.id==null?'':html;
	},
	installRate : function (value, rowData, rowIndex){
		if(rowData.totalInstallCount == null || rowData.totalInstallCount == 0|| rowData.totalShowCount == null || rowData.totalShowCount == 0){
			return "0.0%";
		}
		return (rowData.totalInstallCount/rowData.totalShowCount).toPercent();
	},
	activationRate : function(value, rowData, rowIndex){
		if(rowData.totalActivationCount == null || rowData.totalActivationCount == 0 || rowData.totalInstallCount == null || rowData.totalInstallCount == 0){
			return "0.0%";
		}
		return (rowData.totalActivationCount/rowData.totalInstallCount).toPercent();
	}
};

$.extend($.fn.datagrid.methods, {
	editCell: function(jq,param){
		return jq.each(function(){
			var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field){
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	}
});

/*新增数据记录*/
function handler_add() {
	$('#activtyEditForm').attr('action','ads_activty_info/add.html').resetForm();
	$('#activtyEditDialog').dialog({closed:false,title:'手动添加数据'});
}

var editIndex;

function onClickCell(index, field, value){
	if('activty' != field){
		return;
	}
	$('#grid').datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
	//编辑激活量失去焦点事件
	$(".datagrid-editable-input").focus();
	$($(".datagrid-editable-input")[0]).blur(function(){
		saveActivty();
	});
	editIndex = index;
}

function onAfterEdit(rowIndex, rowData, changes){
	if(changes.activty == undefined){
		editIndex = undefined;
		return;
	}
	
	//ajax请求保存修改的数据
	$.ajax({
        type: "POST",
        url: "ads_activty_info/update.html",
        data: {id:rowData.id, activty:rowData.activty, productId:rowData.productId},
        dataType: "json",
        success: function(data){
        	if(data.r){
        		$.messager.alert('信息',data.m,'info');
        	}else{
            	$.messager.alert('信息',data.m,'error');
        	}
        	var params = $('#queryForm')._formToJson();
    		$('#grid').datagrid('load',params);
        	editIndex = undefined;
        }
    });
}

function saveActivty(){
	//注销失去焦点事件
	$($(".datagrid-editable-input")[0]).unbind();
	//结束编辑
	$('#grid').datagrid('endEdit', editIndex);
}

