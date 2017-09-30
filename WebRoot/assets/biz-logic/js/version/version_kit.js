$(function(){
	sumbitForm('none');
});
function sumbitForm(type){
	var d = $("#submitForm"). serialize(); 
	$.ajax({
		url:"version_data_kit.html",
		type:"post",
		dataType:"json",
		data:d,
		success:function(data){
			var html='';
			$.each(data.listVersonKit,function(i,v){
				
				html+='<tr>'+
					'<td>'+v.id+'</td>'+
					'<td>'+v.kv+'</td>'+
					'<td>'+v.name+'</td>'+
					'<td>'+v.create_time+'</td>'+
					'<td style="text-align:center;"><a href="version_kit_au.html?id='+v.id+'" )">修改</a> | '+
					'<a href="javascript:void(0)" onclick="removeVersionKit(\''+v.id+'\')">删除</a></td>'+
					'<tr>';
			});
			$("#d_body").html(html);
			$("#totalCount").html("共有记录"+data.count+"条 当前第"+data.currPage+"页/ 总"+data.totalPage+"页");
			pageFun(data.currPage,data.count,data.pageSize); 
		}
	});
}

function removeVersionKit(id){
	 var statu = confirm("确认删除ID为【"+id+"】的子版本数据?");
     if(!statu){
         return;
     }
     $.ajax({
 		url:"version_kit_data_delete.html",
 		type:"post",
 		dataType:"json",
 		data:"id="+id,
 		success:function(data){
 			if(data.result>0){
 				alert("操作成功！");
 				sumbitForm('none');
 			}
 			else{
 				alert("操作失败！");
 			}
 		}
 	});
}

	
	
	





