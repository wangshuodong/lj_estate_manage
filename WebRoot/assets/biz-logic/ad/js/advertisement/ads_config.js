
$(function() {
	$('#configTable').propertygrid({
        width: 400,
        showGroup: false,
        url:'ads_config/get.html',
        columns: [[
                { field: 'name', title: '名称', width: 100,formatter:field_ch},
                { field: 'value', title: '值', width: 100, editor : {type : 'validatebox',options : {validType : 'number'}}}
        ]],
        toolbar : [{
					text : '保存',
					iconCls : 'icon-add',
					handler : handler_save
				}],
		onDblClickCell:function(rowIndex){
			$('#configTable').datagrid('beginEdit', rowIndex);
		},
    });
});
		//配置名称显示中文
		function field_ch(value,rowData,rowIndex){
			if(value=='firstStartTime'){
				return '安装后首次启动时间(分钟)';
			}else if(value=='showRatio'){
				return '显示比例(%)';
			}else if(value=='showInterval'){
				return '两次广告显示最短间隔(分钟)';
			}
			else if(value=='adsShowTotal'){
				return '一天广告显示的总次数(次)';
			}
			else if(value=='singleAdsShowTotal'){
				return '单条广告每天显示次数(次)';
			}
		}
        
		//保存配置
        function handler_save(){
			var paramStr = '';
			var rows = $('#configTable').propertygrid('getRows');
			for(var i=0; i<rows.length; i++){
				$('#configTable').datagrid('endEdit', i);
				paramStr += rows[i].name + ':' + rows[i].value + ',';
			}
			$.post('ads_config/saveOrUpdate.html',{paramStr:paramStr},function(json){
				//待完成，后台返回状态判断
				var map = $.parseJSON(json);
				if(map.r){
					$.messager.alert('操作提示', map.m,'info');
				}else{
					$.messager.alert('操作提示', map.m,'error');
				}
			});
		}
        


