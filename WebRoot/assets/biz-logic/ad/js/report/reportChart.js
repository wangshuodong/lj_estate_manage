$(function(){
	var date1 = $("#date").val();
	renderChart(date1);
	
	$('#queryButton').click(function(){
		var date1 = $('#date1').val();
		var date2 = $('#date2').val();
		if(date1 == ""||date2 == ""){
			alert("请选择两个对比日期");
			return;
		}
		renderChart(date1,date2);
	});
	 
});

function showData (checkbox){
	var checked = $(checkbox).attr("checked");
	var value= checkbox.value;
	var chart =  $('#container').highcharts();
	switch (value) {
	case "1":
		if(checked){
			chart.series[0].show();
			chart.series[4].show();
		}else{
			chart.series[0].hide();
			chart.series[4].hide();
		}
		break;

	case "2":
		if(checked){
			chart.series[1].show();
			chart.series[5].show();
		}else{
			chart.series[1].hide();
			chart.series[5].hide();
		}
		break;
	case "3":
		if(checked){
			chart.series[2].show();
			chart.series[6].show();
		}else{
			chart.series[2].hide();
			chart.series[6].hide();
		}
	break;
	case "4":
		if(checked){
			chart.series[3].show();
			chart.series[7].show();
		}else{
			chart.series[3].hide();
			chart.series[7].hide();
		}
	break;
	}
	
}
function renderChart(date1,date2){
//	var date1 = $("#date1").val();
	$._ajaxPost('getChartDate.html',{date1:date1,date2:date2}, function(r){
		var date1 = r.date1;
		var date2 = r.date2;
		var series1 = r.series1;
		var series2 = r.series2;
		var series3 = r.series3;
		var series4 = r.series4;
		var series5 = r.series5;
		var series6 = r.series6;
		var series7 = r.series7;
		var series8 = r.series8;
		$('#container').highcharts({
		    title: {
		        text: '24小时走势图',
		        x: -20
		    },
		    xAxis: {
		        categories: ['0:00', '1:00', '2:00', '3:00', '4:00', '5:00',
		            '6:00', '7:00', '8:00', '9:00', '10:00', '11:00','12:00', '13:00', '14:00', '15:00', '16:00', '17:00',
		            '18:00', '19:00', '20:00', '21:00', '22:00', '23:00']
		    },
		    yAxis: {
		        title: {
		            text: ' '
		        }
		    },
		    
		    series: [{
		        name: date1.substring(5) + '(展示)',
		        data: series1
		    }, {
		        name: date1.substring(5) + '(点击)',
		        data: series2
		    }, {
		        name: date1.substring(5) + '(下载)',
		        data: series3
		    }, {
		        name: date1.substring(5) + '(安装)',
		        data: series4
		    }, {
		        name: date2.substring(5) + '(展示)',
		        data: series5
		    }, {
		        name: date2.substring(5) + '(点击)',
		        data:series6
		    }, {
		        name: date2.substring(5) + '(下载)',
		        data: series7
		    }, {
		        name: date2.substring(5) + '(安装)',
		        data: series8
		    }]
		});
	});
}