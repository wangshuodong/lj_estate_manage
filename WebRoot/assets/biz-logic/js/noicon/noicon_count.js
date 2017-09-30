var ver = 'ALL';
var talbe_list = '<tr><th>日期</th><th>展示量</th><th>点击量</th><th>安装量</th><th>点击率</th><th>安装率</th></tr>';
function changeVer(o){
	ver = o.value;
	if(ver == "KV"){
		talbe_list = '<tr><th>日期</th><th>KV</th><th>展示量</th><th>点击量</th><th>安装量</th><th>点击率</th><th>安装率</th></tr>';
	}else if(ver == "SV"){
		talbe_list = '<tr><th>日期</th><th>SV</th><th>展示量</th><th>点击量</th><th>安装量</th><th>点击率</th><th>安装率</th></tr>';
	}else{
		talbe_list = '<tr><th>日期</th><th>展示量</th><th>点击量</th><th>安装量</th><th>点击率</th><th>安装率</th></tr>';
	}
}
function sumbitForm(type){
	if(type == null || type == "none"){
		$("#dateType").val("");
	}else{
		$("#startdate").val("");
		$("#enddate").val("");
		$("#dateType").val(type);
	}
	var d = $("#submitForm"). serialize(); 
	$.ajax({
		url:"noicon_count_data.html",
		type:"post",
		dataType:"json",
		data:d+"&dateType="+type,
		beforeSend:function(){
			$("#loading").modal('show');
		},
		complete:function(){
			$("#loading").modal('hide');
		},
		success:function(data){
			var kv = $("input[name='KV']");
			var sv = $("input[name='SV']");
			if(kv.attr("checked") == "checked" && sv.attr("checked") != "checked" ){
				talbe_list = '<tr><th>日期</th><th>KV</th><th>展示量</th><th>点击量</th><th>安装量</th><th>点击率</th><th>安装率</th></tr>';
			}else if(kv.attr("checked") != "checked" && sv.attr("checked") == "checked" ){
				talbe_list = '<tr><th>日期</th><th>SV</th><th>展示量</th><th>点击量</th><th>安装量</th><th>点击率</th><th>安装率</th></tr>';
			}else if(kv.attr("checked") != "checked" && sv.attr("checked") != "checked" ){
				talbe_list = '<tr><th>日期</th><th>展示量</th><th>点击量</th><th>安装量</th><th>点击率</th><th>安装率</th></tr>';
			}else if(kv.attr("checked") == "checked" && sv.attr("checked") == "checked" ){
				talbe_list = '<tr><th>日期</th><th>KV</th><th>SV</th><th>展示量</th><th>点击量</th><th>安装量</th><th>点击率</th><th>安装率</th></tr>';
			}
			
			
			$("#talbe_list").html(talbe_list);
			var html = "";
			var count = data.count;
			var currPage = data.currPage;
			var pageSize = data.pageSize;
			var totalPage = data.totalPage;
			if(data.listNoicon.length>0){
				html += "<tr>";
				
				if(kv.attr("checked") == "checked" && sv.attr("checked") != "checked" ){
					html += "<td style='text-align:center;font-weight:bold;'  colspan='2'>总计</td>";
				}else if(kv.attr("checked") != "checked" && sv.attr("checked") == "checked" ){
					html += "<td style='text-align:center;font-weight:bold;'  colspan='2'>总计</td>";
				}else if(kv.attr("checked") != "checked" && sv.attr("checked") != "checked" ){
					html += "<td style='text-align:center;font-weight:bold;'>总计</td>";
				}else if(kv.attr("checked") == "checked" && sv.attr("checked") == "checked" ){
					html += "<td style='text-align:center;font-weight:bold;' colspan='3'>总计</td>";
				}
				
				
				html += "<td>"+data.noicon.scount+"</td>";
				html += "<td>"+data.noicon.infoc+"</td>";
				html += "<td>"+data.noicon.icount+"</td>";
				html += "<td>"+toDecimal(data.noicon.infoc_rate)+"%</td>";
				html += "<td>"+toDecimal(data.noicon.icount_rate)+"%</td>";
				html += "</tr>";
			}
			else{
				html += "<tr>";
				
				if(kv.attr("checked") == "checked" && sv.attr("checked") != "checked" ){
					html += "<td colspan='7' style='text-align:center;font-weight:bold;'>暂无数据</td>";
				}else if(kv.attr("checked") != "checked" && sv.attr("checked") == "checked" ){
					html += "<td colspan='7' style='text-align:center;font-weight:bold;'>暂无数据</td>";
				}else if(kv.attr("checked") != "checked" && sv.attr("checked") != "checked" ){
					html += "<td colspan='6' style='text-align:center;font-weight:bold;'>暂无数据</td>";
				}else if(kv.attr("checked") == "checked" && sv.attr("checked") == "checked" ){
					html += "<td colspan='8' style='text-align:center;font-weight:bold;'>暂无数据</td>";
				}
				
				html += "</tr>";
			}
			$.each(data.listNoicon,function(i,icon){
				/*var infoc_l,icount_l;
				if((icon.icount/icon.scount)==Infinity){icount_l=1;}
				else if(!(icon.icount/icon.scount)){icount_l=0;}
				else{icount_l=icon.icount/icon.scount;}
				
				if((icon.infoc/icon.icount)==Infinity){infoc_l=1;}
				else if(!(icon.infoc/icon.icount)){infoc_l=0;}
				else{infoc_l=icon.infoc/icon.icount;}*/
				
				html += "<tr>";
				html += "<td>"+icon.adate+"</td>";
				if(kv.attr("checked") == "checked"){
					html += "<td>"+icon.kv+"</td>";
				}
				if(sv.attr("checked") == "checked"){
					html += "<td>"+icon.sv+"</td>";
				}
				html += "<td>"+icon.scount+"</td>";
				html += "<td>"+icon.infoc+"</td>";
				html += "<td>"+icon.icount+"</td>";
				html += "<td>"+toDecimal(icon.infoc_rate)+"%</td>";
				html += "<td>"+toDecimal(icon.icount_rate)+"%</td>";
				html += "</tr>";
				
			})
			$("#d_body").html(html);
			$("#totalCount").html("共有记录"+count+"条 当前第"+currPage+"页/ 总"+totalPage+"页");
			$("input[name='startdate']").val(data.startDate);
			$("input[name='enddate']").val(data.endDate);
			$("#currPage").val(currPage);
			if(count == 0){
				currPage = 0;
			}
			try{
				pageFun(currPage,count,pageSize); 
			}catch(e){
				
			}
			
		}
	});
	
}
function toDecimal(number) { 
	var f = parseFloat(number); 
	if (isNaN(f)) { 
	return; 
	} 
	f = Math.round(number*100)/100; 
	return f; 
} 
//自动加载
$(document).ready(function(){
	sumbitForm('none');
})





