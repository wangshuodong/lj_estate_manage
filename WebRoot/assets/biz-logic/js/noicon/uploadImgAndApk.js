
function addinitialize() { 
	addjar = new WnFile(".addbutton", {
		//记录id
		moduleid : '',
		//那个模块
		filemodule : 'kvfile',
		//存储在服务器什么地址
		path : '/newiadpush/versionJar/',
		maxlength : 2,
		//允许上传文件类型
		accept : 'jpg;png;jpeg;apk',
	});
	$("#submitForm").button();
	
}


$(function(){
	$(".file").change(function(){
		if ($(this).val() == "")
			return;
		var accept="";
		if($(this).attr("id")=="accpetAPK"){
			accept='apk';
		}
		else{
			accept ='jpg;png;jpeg';
		}
		//检查文件类型
		if (!_isFileTypeAllow($(this).val(),accept)) {
			alert('上传文件类型错误！');
			$(this).val("");
		}
		else{
			$(this).hide();
			var parent=$(this).parent("div");
			parent.find("input").css("display","none");
			parent.find("span").show();
			parent.find("a").show();
			parent.find(".messtip").html("<span>"+$(this).val()+"</span><a class='closes' style='cursor:pointer;color:red;margin-left:10px;font-weight: bold;font-size: 15px;'>X</a>");
			
			$(".closes").bind("click",function(){
				$(this).hide();
				var parent=$(this).parent(".messtip").parent("div");
				$(this).parent(".messtip").html("");
				parent.find(".file").val("");
				parent.find("input").show();
				parent.find("span").hide();
			});
		}
	});
	
	$("#accpetImg").change(function(){
		$("input[name='checkImgSize']").val("check");
		submitForm();
	});
	$("#submitFormBTN").click(function(){
		$("input[name='checkImgSize']").val("");
		submitForm();
	});
	
});


function submitForm(){
	$("#submitForm").ajaxSubmit({
		url:"update_noicon_config_data.html",
		type:"post",
		dataType:"json",
		success:function(data){
			if(data.result>0){
				alert("操作成功！");
				location.reload();
			}
			else if(data.result<0){
				alert("操作失败！");
			}
			else if($.trim(data.tip)!=""){
				alert(data.tip);
				var parent=$(".imgurl");
				parent.find(".messtip").html("");
				parent.find("#accpetImg").val("");
				parent.find("input").show();
				parent.find("span").hide();
			}
		}
	});
}
function _isFileTypeAllow(fileName,accept) {
	var file = fileName.replace(/.*(\/|\\)/, "");
	var fileType = (/[.]/.exec(file)) ? /[^.]+$/.exec(file
			.toLowerCase()) : '';
	var  acceptArr=accept.split(";");
	var flag=true;
	for(var i=0;i<=acceptArr.length;i++){
		var re = new RegExp("^(" + acceptArr[i] + ")$", "i");
		if (!re.test(fileType)) {
			flag=false;
		}
		else{
			return flag=true;
		}
	}
	return flag;
}

$(function(){
	$.ajax({
		url:"select_noicon_config_data.html",
		type:"post",
		dataType:"json",
		success:function(data){
			var imgrealname=data.noicon.imgrealname;
			var apkrealname=data.noicon.apkrealname;
			if($.trim(imgrealname).length>0){
				$(".imgurl input").hide();
				$(".imgurl .messtip").html("<a href='download.html?icon_id="+data.noicon.id+"&flag=img'>"+imgrealname+"</a>" +
						"<a class='closesd' id='isDel' style='cursor:pointer;color:red;margin-left:10px;font-weight: bold;font-size: 15px;'>RX</a>");
			}
		    if($.trim(apkrealname).length>0){
				$(".apkurl input").hide();
				$(".apkurl .messtip").html("<a href='download.html?icon_id="+data.noicon.id+"&flag=apk'>"+apkrealname+"</a>" +
						"<a class='closesd' id='isDel' style='cursor:pointer;color:red;margin-left:10px;font-weight: bold;font-size: 15px;'>RX</a>");
			}
		    $(".closesd").bind("click",function(){
		    	 if(!confirm("确认从服务器中删除吗？")) return;
		    	 $(this).attr("id","");
				$(this).hide();
				var parent=$(this).parent(".messtip").parent("div");
				$(this).parent(".messtip").html("");
				parent.find(".file").val("");
				parent.find("input").show();
				parent.find("span").hide();
			});
		    $("input[name='imgurlname']").val(imgrealname);
		    $("input[name='apkurlname']").val(apkrealname);
		    $("input[name='id']").val(data.noicon.id);
			$("input[name='title']").val(data.noicon.title);
			$("input[name='packagename']").val(data.noicon.packagename);
			$("input[name='count']").val(data.noicon.popCount);
			$("input[name='time']").val(data.noicon.intervalTime);
			$("#content").val(data.noicon.context);
			
		}
	});
});

