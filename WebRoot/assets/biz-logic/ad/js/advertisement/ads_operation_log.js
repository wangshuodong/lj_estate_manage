$(function() {
	var grid = $('#grid').datagrid({
		sortName: 'operationTime',
		sortOrder: 'desc',
		remoteSort:true,
		view: detailview,
		url:'ads_operation_log/list.html',
	    detailFormatter:function(index,row){
	        return '<div class="ddv" style="padding:5px 0">' + row.updateContent + '</div>';
	    }
	});
	
	/*输入匹配*/
	$.getJSON('ads_info/allAdsName.html',function(allAdsName){
		var data = allAdsName.split(",");
		$("#adsName").autocomplete(data,
			{highlight: false,scroll: true, scrollHeight: 200 }
		);  
	});
	
	$('#queryButton').click(function(){
		var params = $('#queryForm')._formToJson();
		$(grid).datagrid('load',params);
	});
	
	if($("#hiddenAdsId").val() != ""){
		$('#queryButton').click();
	}
});

function onExpandRow(index,row){
	var ddv = $(this).datagrid('getRowDetail',index).find('div.ddv');
    ddv.panel({
        border:false,
        cache:false,
        onLoad:function(){
            $('#grid').datagrid('fixDetailRowHeight',index);
        }
    });
    $('#grid').datagrid('fixDetailRowHeight',index);
}

var formatter = {
	optType : function(value, rowData, rowIndex) {
		if(value == 1){ return '增加广告'; }
		if(value == 2){ return '删除广告'; }
		if(value == 3){ return '修改广告'; }
	},
	showTitle : function(value, rowData, rowIndex){
		return '<span title="'+value+'">'+value+'</span>';
	}
};




