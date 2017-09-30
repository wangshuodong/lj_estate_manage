$(function() {
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
		showFooter:true,
		remoteSort:true,
		sortName: 'aDate',
		sortOrder: 'desc',
		
		rowStyler:function(index,row){
			if(row.adsId==null){
				return 'background-color:#F4F4F4;';
			}
		}
	});
	
	$('#queryButton').click(function(){
		var params = $('#queryForm')._formToJson();
		$(grid).datagrid('load',params);
	});
});

var formatter = {
	flow_out : function(value, rowData, rowIndex) {
		var flowOutMoney = rowData.endLoadCount * rowData.adsSize * 0.3 / 1000;
		return (Math.round(flowOutMoney * 100)/100).toFixed(2);
	},
	profit : function(value, rowData, rowIndex) {
		var profit = rowData.incomeAmt - rowData.endLoadCount * rowData.adsSize * 0.3 / 1000;
		return (Math.round(profit * 100)/100).toFixed(2);
	},
	thousandsProfit : function(value,rowData, rowIndex){
		if(rowData.showCount==0 || rowData.showCount==null){
			return '0.00';
		}
		var thousandsProfit = (rowData.incomeAmt - rowData.endLoadCount * rowData.adsSize * 0.3 / 1000)/rowData.showCount * 1000;
		return  (Math.round(thousandsProfit * 100)/100).toFixed(2);
	},
	count : function(value, rowData, rowIndex) {
		return value==null?0:value;
	},
	//廖江洪添加激活比2014 -4-10
	activeRate : function(value, rowData, rowIndex){
		if(rowData.activationCount == null || rowData.activationCount == 0||rowData.dianjiCount == null || rowData.dianjiCount == 0){
			return "0.0‰";
		}
		return (rowData.activationCount/rowData.dianjiCount).toQianPercent();
	},
	ratio : function(value, rowData, rowIndex) {
		return value==null?'0.00%':value.toPercent();
	},
	income : function(value, rowData, rowIndex) {
		return value==null?'0.00':(Math.round(value * 100)/100).toFixed(2);
	},
	showRate : function(value, rowData, rowIndex){
		if(rowData.endLoadCount == null || rowData.endLoadCount == 0||rowData.showCount == null || rowData.showCount == 0){
			return "0.0%";
		}
		return (rowData.showCount/rowData.endLoadCount).toPercent();
	},
	downRate : function(value, rowData, rowIndex){
		if(rowData.endLoadCount == null || rowData.endLoadCount == 0||rowData.startLoadCount == null || rowData.startLoadCount == 0){
			return "0.0%";
		}
		return (rowData.endLoadCount/rowData.startLoadCount).toPercent();
	}
};
