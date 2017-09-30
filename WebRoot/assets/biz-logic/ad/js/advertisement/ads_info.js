$(function() {
	
	$('#adsEditDialog').dialog({
		buttons:[{text:'保存',handler:function(){
			if(!$('#adsEditForm').form('validate')){return;}
			var iconPath=$("#iconPath").val();
			if(iconPath==null||iconPath==""){
						$.messager.alert('操作提示',"请上传应用图片!",'error');
						return false;
			}
			var picPath=$("#picPath").val();
			if(picPath==null||picPath==""){
						$.messager.alert('操作提示',"请上传广告图片!",'error');
						return false;
			}
			var downloadUrl=$("#loadUrl").val();
			if(downloadUrl==null||downloadUrl==""){
						$.messager.alert('操作提示',"请上传应用!",'error');
						return false;
			}
			$('#adsEditForm')._ajaxForm(function(r){
				if(r.r){$('#adsEditDialog').dialog({closed:true});$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
			});
		}},{text:'关闭',handler:function(){$('#adsEditDialog').dialog({closed:true});}}]
	});
	
	/*输入匹配*/
	$.getJSON('ads_info/allAdsName.html',function(allAdsName){
		var data = allAdsName.split(",");
		$("#adsName").autocomplete(data,
			{highlight: false,scroll: true, scrollHeight: 200 }
		);  
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
		},
		issuedCount:{
			validator: function (value,param) {
				var reg = new RegExp("^-?\\d+$");
				if(Number(value)==0){
					return true;
				}else if(!reg.test(value)){
					return false;
				}else{
					return true;
				}
			},
			message:'请输入正整数、0、负整数！'
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
					text : '添加广告',
					iconCls : 'icon-add',
					handler : handler_add
				} ]
	});
	
	$('#queryButton').click(function(){
		var params = $('#queryForm')._formToJson();
		$(grid).datagrid('load',params);
	});
	
	
});

var formatter = {
	status : function(value, rowData, rowIndex) {
		if(value == 0){ return '<font color=green>启用</font>'; } 
		else if(value == 1){ return '<font color=black>暂停</font>'; }
		else if(value == 2){ return '<font color=black>禁用/下架</font>';}
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
			html+= '<a class="spacing a-green" onclick="log('+rowData.id+');" href="javascript:void(0);">日志</a>';
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

/*查看广告日志*/
function log(adId){
	window.location.href = 'menus/ads_operation_log.html?adsId='+adId;
}

/*显示大图 */
function showBigPic(){
	$('#bigPic').attr("src",$('#beforePic').show().find('img').attr("src"));
	$('#bigPicDiv').dialog({closed:false,title:'显示大图'});
}


/*新增广告*/
function handler_add() {
	$('#beforePic').hide();
	$('#iconImgShow').attr("src","");
	$('#picImgShow').attr("src","");
	$('#downloadApp').attr("style","display:none");
	$('#adsEditForm').attr('action','ads_info/add.html').resetForm();
	$('#adsEditDialog').dialog({closed:false,title:'新增广告'});
}

/*修改广告*/
function update(rowIndex) {
	$('#adsEditForm').attr('action','ads_info/update.html').resetForm();
	var data = $('#grid').datagrid('getRows')[rowIndex];
	$('#adsEditForm')._jsonToForm(data);
	if(data.picPath==null || data.picPath==''){
		$('#beforePic').hide(); //先前未传图片
	}else{
		$('#beforePic').show();
		$('#beforePic').find('img').attr('src',window.rootPath+"/document/download-"+data.picPath+".do").show();//展示原图
	}
	 if(data.loadUrl != "" && data.loadUrl != null){
	    	$('#downloadApp').attr("style","display:false");
	    	$('#downloadApp').attr("href",window.rootPath+"/document/download-"+data.loadUrl+".do");
	    }else{
	    	$('#downloadApp').attr("style","display:none");
	    }
	$('#iconImgShow').attr("src",window.rootPath+"/document/download-"+data.iconPath+".do");
	$('#picImgShow').attr("src",window.rootPath+"/document/download-"+data.picPath+".do");
	$('#beforeStatus').val(data.status);//原状态
	$('#adsEditDialog').dialog({closed:false,title:'修改广告'});
	
	$('#productId').combobox('setValue', data.productId);
}

/*删除广告*/
function delet(rowIndex) {
	$.messager.confirm('操作提示', '确定要删除该广告？', function(r){
		if (r){
			var data = $('#grid').datagrid('getRows')[rowIndex];
			$._ajaxPost('ads_info/delete.html',{id:data.id}, function(r){
				if(r.r){$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
			});
		}
	});
}

/*批量删除广告*/
function batch_del() {
	var check = $('#grid').datagrid('getChecked');
	if(check.length == 0){
		$.messager.alert('操作提示', '请选择您要删除的广告？','info');
	}else{
		$.messager.confirm('操作提示', '确定要删除所选广告？', function(r){
			if (r){
				var ids = new Array();
				for(var i in check){
					ids[i] = check[i].id;
				}
				$._ajaxPost('ads_info/batchDelete.html',{idStr:ids.join('|')},function(r){
					if(r.r){$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
				});
			}
		});
	}
}
 


