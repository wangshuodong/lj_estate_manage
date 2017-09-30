$(function() {
	var grid = $('#grid')._datagrid({
		pageList:[10,20,30,40,50],
		pageSize:20,
		toolbar : [{
			text : '添加新渠道',
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
	$('#channelDialog').dialog({
		buttons:[{text:'保存',handler:function(){
			if(!$('#channelForm').form('validate')){return;}
			if(checkSubmitFlg){
				return;
			}
			$('#channelForm')._ajaxForm(function(r){
				if(r.r){
					checkSubmitFlg = false;
					$('#channelDialog').dialog({closed:true});$('#grid').datagrid('reload');
					}else{
						checkSubmitFlg = false;
						alert(r.m);
						}
			});
			checkSubmitFlg = true;
		}},{text:'关闭',handler:function(){$('#channelDialog').dialog({closed:true});}}]
	});
	
	$('#channelSetupDialog').dialog({
		buttons:[{text:'保存',handler:function(){
			if(!$('#channelSetupForm').form('validate')){return;}
			if(checkSubmitFlg){
				return;
			}
			$('#channelSetupForm')._ajaxForm(function(r){
				if(r.r){
					checkSubmitFlg = false;
					$('#channelSetupDialog').dialog({closed:true});$('#grid').datagrid('reload');
					}else{
						checkSubmitFlg = false;
						alert(r.m);
						}
			});
			checkSubmitFlg = true;
		}},{text:'关闭',handler:function(){$('#channelSetupDialog').dialog({closed:true});}}]
	});
	
});

var formatter = {
		opt : function(value, rowData, rowIndex) {
			return '<a class="spacing a-blue" onclick="handler_upd('+rowIndex+');" href="javascript:void(0);">修改</a>'+
			'<a class="spacing a-red" onclick="handler_del('+rowIndex+');" href="javascript:void(0);">删除</a>'+
			'<a class="spacing a-green" onclick="updateChannelSetup('+rowIndex+');" href="javascript:void(0);">渠道设置</a>';
		},
		out: function(value, rowData, rowIndex){
			return ((rowData.flow_out==null?0:rowData.flow_out) + (rowData.out_amt==null?0:rowData.out_amt)).toFixed(2) ;
		},
		_in:function(value, rowData, rowIndex){
			if(value==null){
				value=0;
			}
			return value.toFixed(2);
		},
		show: function(value, rowData, rowIndex){
			var rate;
			var icon;
			if(rowData.y_show==null&&rowData.by_show!=null){
				icon = '<font color="red">&darr;</font>';
				rate="";
			}else if(rowData.y_show!=null&&rowData.by_show==null){
				icon = '<font color="green">&uarr;</font>';
				rate="";
			}else if(rowData.y_show==null&&rowData.by_show==null){
				icon = "&mdash;";
				rate="";
			}else  if(rowData.y_show!=null&&rowData.by_show!=null){
				rate = (rowData.y_show-rowData.by_show)/rowData.by_show;
				if(rate>0){
					icon = '<font color="green">&uarr;</font>';
				}else if(rate<0){
					icon = '<font color="red">&darr;</font>';
				}else{
					icon = "&mdash;";
				}
				rate = rate.toPercent();
			}
			return (rowData.by_show==null?0:rowData.by_show )+'|' +(rowData.y_show==null?0:rowData.y_show) + '|'+rate +icon;
		},
		install :function(value, rowData, rowIndex){
			var rate;
			var icon;
			if(rowData.y_install==null&&rowData.by_install!=null){
				icon = '<font color="red">&darr;</font>';
				rate="";
			}else if(rowData.y_install!=null&&rowData.by_install==null){
				icon = '<font color="green">&uarr;</font>';
				rate="";
			}else if(rowData.y_install==null&&rowData.by_install==null){
				icon = "&mdash;";
				rate="";
			}else  if(rowData.y_install!=null&&rowData.by_install!=null){
				rate = (rowData.y_install-rowData.by_install)/rowData.by_install;
				if(rate>0){
					icon = '<font color="green">&uarr;</font>';
				}else if(rate<0){
					icon="&darr;";
					icon = '<font color="red">&darr;</font>';
				}else{
					icon = "&mdash;";
				}
				rate = rate.toPercent();
			}
			return (rowData.by_install==null?0:rowData.by_install) +"|" +(rowData.y_install==null?0:rowData.y_install) + "|"+rate +icon;
		},
		channelName:function(value, rowData, rowIndex){
			var href = window.rootPath + '/channel/channelDetailIndex.html?channelId='+rowData.id +'&appkey='+rowData.appkey;
			return '<a class="a-link" href="'+encodeURI(href) + '">' + value + '</a>';
		},
		isPop:function(value, rowData, rowIndex){
			if(value == "1"){
				return   "开启";
			}else{
				return "关闭";
			}
		},
		setMethod:function(value, rowData, rowIndex){
			var setMethod = value;
			if(null!=setMethod&&""!=setMethod){
				var arr = setMethod.split(",");
				var tmp = "";
				for(var i =0 ;i < arr.length;i++){
					if(arr[i] == 0){
						tmp = "插屏";
					}
					if(arr[i] == 1){
						tmp += " 广告墙";				
					}
					if(arr[i] == 2){
						tmp += " 一键安装";
					}
				}
				return tmp;
			}
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

/*修改*/
function handler_upd(rowIndex) {
	$('#channelForm').resetForm();
	$('#channelForm').attr('action','channel/update.html');
	var rows = $('#grid').datagrid('getRows');
	var data = rows[rowIndex];
	$('#channelForm')._jsonToForm(data);
	$('#channelDialog').dialog({closed:false,title:'修改渠道'});
	
	var setMethod = data.setMethod;
	
	$("#set_method_0").attr("checked", null);
	$("#set_method_1").attr("checked", null);
	$("#set_method_2").attr("checked", null);
	
	if(null!=setMethod&&""!=setMethod){
		var arr = setMethod.split(",");
		for(var i =0 ;i < arr.length;i++){
			$("#set_method_" + arr[i]).attr("checked", "checked");
		}
	}
}
function handler_del(rowIndex) {
	var rows = $('#grid').datagrid('getRows');
	var data = rows[rowIndex];
	$.messager.confirm('操作提示', '确定要删除该渠道？', function(r){
		if (r){
			$._ajaxPost('channel/delete.html',{id:data.id}, function(r){
				if(r.r){$('#grid').datagrid('reload');}else{alert(r.m);}
			});
		}
	});
}
 

function handler_add() {
	$('#channelForm').resetForm();
	$("#id").val('');
	$('#channelForm').attr('action','channel/add.html');
	$('#channelDialog').dialog({closed:false,title:'新增渠道'});
}

/*渠道设置*/
function updateChannelSetup(rowIndex) {
	$('#channelSetupForm').resetForm();
	$('#channelSetupForm').attr('action','channel/channel_setup.html');
	var rows = $('#grid').datagrid('getRows');
	var data = rows[rowIndex];
	$('#channelSetupForm')._jsonToForm(data);
			
	var channelId = data.id;
	var channelName = data.channelName;
    $('#channel_name').val(channelName);
    $('#channel_id').val(channelId);
    $('#channelSetupDialog').dialog('open').dialog("setTitle","渠道设置");
}
