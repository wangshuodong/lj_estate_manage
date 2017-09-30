$(function() {
	
	var now = new Date();
	var nowStr = formatDate(now,'YYYY-MM-DD');
	$('#date').val(nowStr);
	
	var date1 = $("#date").val();
	rednerpie(date1);
	$('#queryButton').click(function(){
		var date = $('#date').val();
		rednerpie(date);
	});
});

function rednerpie(date){
	$._ajaxPost('sheetmanage/getlistpie.html',{date:date}, function(r){
		var strdata=eval(r);
	    $('#container').highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false
	        },
	        title: {
	            text: '版本统计'
	        },
	        tooltip: {
	    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    color: '#000000',
	                    connectorColor: '#000000',
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: '版本',
	            data: strdata
	        }]
	    });
	});
}