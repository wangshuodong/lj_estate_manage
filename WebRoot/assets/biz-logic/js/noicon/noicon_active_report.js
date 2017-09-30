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
		url:"noicon_active_report_data.html",
		type:"post",
		dataType:"json",
		data:d+"&dateType="+type,
		success:function(data){
			var html = "";
			var count = data.count;
			var currPage = data.currPage;
			var pageSize = data.pageSize;
			var totalPage = data.totalPage;
			if(data.listNoiconActive.length>0){
				html += "<tr>"; 
				html += "<td colspan='8' style='text-align:center;'>汇总</td>";
				html += "<td >"+data.noicon.totalIcount+"</td>";
				html += "<td>"+data.noicon.totalAcount+"</td>";
				html += "</tr>";
			}
			else{
				html += "<tr>";
				html += "<td colspan='10' style='text-align:center;font-weight:bold;'>暂无数据</td>";
				html += "</tr>";
			}
			$.each(data.listNoiconActive,function(i,icon){
				var appkey,appName,userName,qdName;
				if($.trim(icon.appid)==""||$.trim(icon.uid)==""||$.trim(icon.qdid)==""){
					appkey="";
				}
				else{
					appkey=icon.uid+"-"+icon.qdid+"-"+icon.appid;
				}
				appName=$.trim(icon.appName)==""?"未知":icon.appName;
				userName=$.trim(icon.userName)==""?"未知":icon.userName;
				qdName=$.trim(icon.qdName)==""?"未知":icon.qdName;
				html += "<tr>";
				html += "<td>"+icon.adate+"</td>";
				html += "<td>"+appkey+"</td>";
				html += "<td>"+icon.sv+"</td>";
				html += "<td>"+icon.kv+"</td>";
				html += "<td>"+icon.version+"</td>";
				html += "<td>"+appName+"</td>";
				html += "<td>"+qdName+"</td>";
				html += "<td>"+userName+"</td>";
				html += "<td >"+ icon.icount+"</td>";
				html += "<td >"+icon.acount+"</td>";
				html += "</tr>";
				
			});
			$("#d_body").html(html);
			$("#totalCount").html("共有记录"+count+"条 当前第"+currPage+"页/ 总"+totalPage+"页");
			pageFun(currPage,count==0?1:count,pageSize); 
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





