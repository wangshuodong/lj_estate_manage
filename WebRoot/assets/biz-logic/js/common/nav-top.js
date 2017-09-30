var param={record_length:0,
		   listnotice:null,
		   timer:null,
		   requestTime:300000,//300000
		   op:true
		 };
$(function(){
	$(".tip_font").click(function(){
		if(param.record_length==0||param.listnotice==null){
			return;
		}
		param.op=false;
		clearInterval(param.timer);
		$.ajax({
			url:"getNewsNotice.html",
			type:"post",
			dataType:"json",
			data:"noticeid="+param.listnotice[0].id,
			success:function(data){
				param.listnotice.splice(0,1);
				var html=''+data.news.context+''+
	        	'<br/>'+
	        	'<div class="news_m_foot">'+
		        	'iadpush传媒<br/>'+data.news.date+''+
	        	'</div><br/><br/>';
				$("#myModal_news .news_m_title").html(data.news.title);
				$("#myModal_news .news_m_body").html(html);
				$("input[name='news_m_noticeid']").val(data.news.id);
				$("input[name='news_m_title']").val(data.news.title);
				var count=--param.record_length;
				$(".tip_font font").html(count);
				$(".tip_font").attr("title","最新公告：共"+(count)+"条！");
				//show
				$('#myModal_news').modal({
					show:true,
					backdrop:'static'
				});
				
			}
		});
	});
	param.timer=setInterval(function() {
		ajaxGetNewsNotice();
	},param.requestTime);
});

function ajaxGetNewsNotice(){
	if(!param.op){return;}
	$.ajax({
		url:"getCountNewsNotice.html",
		type:"post",
		dataType:"json",
		success:function(data){
			if(data==null){return;}
			param.record_length=data.listnotice.length;
			param.listnotice=data.listnotice;
			$(".tip_font font").html(param.record_length);
			$(".tip_font").attr("title","最新公告：共"+param.record_length+"条！");
		}
	});
}
$(function(){
	$(".colseNotice").click(function(){
		param.timer=setInterval(function() {
			ajaxGetNewsNotice();
		},param.requestTime);
		param.op=true;
		var noticeid=$("input[name='news_m_noticeid']").val();
		$.ajax({
			url:"insertNotice.html",
			type:"post",
			dataType:"json",
			data:"noticeid="+noticeid+"&title="+$("input[name='news_m_title']").val(),
			success:function(data){
				
			}
		});
	});
});