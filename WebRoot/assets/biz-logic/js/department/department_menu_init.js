$(function() {
	var menuTree;
  //  $.fn.zTree.init($("#rolePanel"), roleSetting, window.roles);
  //  var roleTree = $.fn.zTree.getZTreeObj("rolePanel");
  //  roleTree.expandAll(true);
    
    var othersetting = {
        check : {
            enable : false,
            chkStyle : "checkbox",
            chkboxType : { "Y" : "ps", "N" : "ps" },
            autoCheckTrigger : true
        },
        data : {
            simpleData : { enable : true, idKey : "id", pIdKey : "parentId" },
            key : { name : "name", url : "mUrl" }
        },
        callback : {
            onClick : function(event, treeId, treeNode) {
                if (treeNode.isParent) {
                	othermenuTree.expandNode(treeNode, !treeNode.open, false, true);
                }
            },
            onCheck:function(event, treeId, treeNode){
                $.post('menu_checked.html',{menuId:treeNode.id,checked:treeNode.checked},
                		function(r){console.log(r.m);},'json');
            },
            beforeCheck:function(treeId, treeNode){
            	return true;
            }
        },
        view : {showTitle : false, selectedMulti : false, autoCancelSelected : false}
    };
    $.fn.zTree.init($("#othermenuPanel"), othersetting, window.othermenusJson);
    menuTree = $.fn.zTree.getZTreeObj("othermenuPanel");
    menuTree.expandAll(false);
    
    $("#chkAll").click(function() {
    	$("#chkNotAll").attr("checked",false);
    	//if($('#othertitleRoleName').data('roleId')){
    		if($(this).is(':checked')) {
    			var nodes = menuTree.transformToArray(menuTree.getNodes());
        		for(var i in nodes) {
                    menuTree.checkNode(nodes[i], true, true, true);
                }
                return true;
    		}
//        }else {
//        	$("#chkAll").attr("checked",false);
//            asyncbox.tips('请双击需要分配菜单的角色！', 'error');
//            return false;
//        }
    });
    
    $("#chkNotAll").click(function() {
    	$("#chkAll").attr("checked",false);
//    	if($('#othertitleRoleName').data('roleId')){
    		if($(this).is(':checked')) {
    			var checkNodes = menuTree.getCheckedNodes(true);
    			var nocheckNodes = menuTree.getCheckedNodes(false);
    			for(var i in checkNodes) {
                    menuTree.checkNode(checkNodes[i], false, true, true);
                }
        		for(var i in nocheckNodes) {
        			menuTree.checkNode(nocheckNodes[i], true, true, true);
                }
                return true;
    		}
//        }else {
//        	$("#chkNotAll").attr("checked",false);
//            asyncbox.tips('请双击需要分配菜单的角色！', 'error');
//            return false;
//        }
    });
    
    
    sumbitForm("none");
});

function jumpToAdd(){
	window.location.href="department_add.html";
}
function delById(id){
	$.ajax({
		url:"department_delete.do",
		type:"post",
		dataType:"json",
		data:{
			id:id
		},
		beforeSend:function(){
			$("#loading").modal('show');
		},
		complete:function(){
			$("#loading").modal('hide');
		},
		success:function(data){
			if(data.status == true){
				alert(data.msg);
				location.reload();
			}else{
				alert(data.msg);
			}
		}
	})
}
function sumbitForm(type){
	var d = $("#submitForm"). serialize(); 
	$.ajax({
		url:"department_menu.do",
		type:"post",
		dataType:"json",
		data:d+"&type="+type,
		success:function(data){
			var html = "";
			var count = data.count;
			var currPage = data.currPage;
			var pageSize = data.pageSize;
			if(data.list == null || data.list.length==0){
				html += "<tr>";
				html += "<td colspan='8' style='text-align:center;font-weight:bold;'>暂无数据</td>";
				html += "</tr>";
			}else{
				$.each(data.list,function(i,p){
					html += "<tr>";
					html += "<td nowrap='nowrap'>"+p.name+"</td>";
					html += "<td nowrap='nowrap' style='text-align:center;'>"+ p.parentName+"</td>";
					html += "<td nowrap='nowrap'>"+p.address+"</td>";
					html += "<td>"+p.phone+"</td>";
					html += "<td>"+p.contact_people+"</td>";
					html += "<td style='text-align:center;'><a href=\"department_update.html?id="+p.id+"\">修改</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"javascript:;\" onclick=\"delById("+p.id+")\">删除</a></td>";
					html += "</tr>";
					
				})
			}
			$("#d_body").html(html);
			$("#totalCount").html("共有记录"+count+"条 ");
			try {
				pageFun(currPage,count,pageSize);
			} catch (e) {
				// TODO: handle exception
			}
		}
	});
	
}