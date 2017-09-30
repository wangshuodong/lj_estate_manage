$(function () {/*
	$._ajaxPost("homePageData.html",null,function(r){
		$("#by_show").html(r.by_show);
		$("#by_install").html(r.by_install);
		$("#y_show").html(r.y_show);
		$("#y_install").html(r.y_install);
		
		$("#by_out").html(r.by_out.toFixed(2));
		$("#by_in").html(r.by_in.toFixed(2));
		$("#y_out").html(r.y_out.toFixed(2));
		$("#y_in").html(r.y_in.toFixed(2));
		
		$("#by_out_rate").html(r.by_out_rate.toPercent());
		$("#by_in_rate").html(r.by_in_rate.toPercent());
		$("#y_out_rate").html(r.y_out_rate.toPercent());
		$("#y_in_rate").html(r.y_in_rate.toPercent());
		
		$("#by_lastweek_out_rate").html(r.by_lastweek_out_rate.toPercent());
		$("#by_lastweek_in_rate").html(r.by_lastweek_in_rate.toPercent());
		$("#y_lastweek_out_rate").html(r.y_lastweek_out_rate.toPercent());
		$("#y_lastweek_in_rate").html(r.y_lastweek_in_rate.toPercent());
		
		var dateList = r.dateList;
		var weekList = r.weekList;
		var installList = r.installList;
		var outList = r.outList;
		var incomeList = r.incomeList;
		var dayShowList = r.dayShowList;
		var dayClickList = r.dayClickList;
		var dayInstallkList = r.dayInstallkList;
		var dayOutkList = r.dayOutkList;
		var dayIncomeList = r.dayIncomeList;
		$('#container1').highcharts({
        chart: {
            type: 'column'
        },
        credits:{enabled:false},
        title: {
            text: '前四周报表'
        },
        
        xAxis: {
            categories: weekList
        },
       yAxis: {
            min: 0,
            title: {
                text: ''
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table width="150">',
            pointFormat: '<tr><td style="color:{series.color};padding:0;width:50px;">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y} </b></td></tr>',
            footerFormat: "</table>",
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: [{
            name: '安装',
            data: installList

        }, {
            name: '支出',
            data: outList

        }, {
            name: '收入',
            data: incomeList

        }, {
            name: '利润',
            data: [formatNumber(incomeList[0]-outList[0]),formatNumber(incomeList[1]-outList[1]), formatNumber(incomeList[2]-outList[2]), formatNumber(incomeList[3]-outList[3])]

        }]
    });



$('#container2').highcharts({
        title: {
            text: '展示量点击量总和报表',
            x: -20 //center
        },
       
        xAxis: {
            categories: dateList
        },
        credits:{enabled:false},
        yAxis: {
            title: {
                text: ''
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            
        },
        series: [{
            name: '展示',
            data: dayShowList
        }, {
            name: '点击',
            data: dayClickList
        }]
    });

    $('#container3').highcharts({
        title: {
            text: '综合报表',
            x: -20 //center
        },
        credits:{enabled:false},
        xAxis: {
            categories: dateList
        },
        yAxis: {
            title: {
                text: ''
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            
        },
       
        series: [{
            name: '安装',
            data: dayInstallkList
        }, {
            name: '支出',
            data: dayOutkList
        }, {
            name: '收入',
            data: dayIncomeList
        }, {
            name: '利润',
            data: [formatNumber(dayIncomeList[0] - dayOutkList[0]), formatNumber(dayIncomeList[1] - dayOutkList[1]), formatNumber(dayIncomeList[2] - dayOutkList[2]), formatNumber(dayIncomeList[3] - dayOutkList[3]),
            		formatNumber(dayIncomeList[4] - dayOutkList[4]), formatNumber(dayIncomeList[5] - dayOutkList[5]), formatNumber(dayIncomeList[6] - dayOutkList[6])]
        }]
    });
    
	});
*/});
//保留小数点后两位数字
function formatNumber(value){
	return Number(value.toFixed(2));
}