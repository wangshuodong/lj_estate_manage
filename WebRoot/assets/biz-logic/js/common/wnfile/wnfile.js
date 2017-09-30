var filea;
(function() {
	function getBox(el) {
		var tw = el.outerWidth();
		var th = el.outerHeight();
		var tOffSet = el.offset();
		var top = tOffSet.top;
		var left = tOffSet.left;
		return {
			left : left,
			right : left + tw,
			top : top,
			bottom : top + th
		};
	}
	;
	var getUID = function() { 
		var id = 0;
		return function() {
			return 'ValumsAjaxUpload' + id++;
		}
	}();
	function fileFromPath(file){
		return file.replace(/.*(\/|\\)/, "");			
	}
	WnFile = function(button, options) {
		this._button = $(button);
		this.configer = $.fn.extend( {
			//请求地址
			action : 'fileUpload.html',
			loadAction:'findFile.html',
			downAction:'download.html',
			//记录id
			moduleid : '',
			name : 'file',
			//那个模块
			filemodule : '',
			//存储在服务器什么地址
			path : '',
			//允许传多少张图片
			maxlength : -1,
			allowRepeat : false,
			//允许上传文件类型
			accept : "",
			readonly:false,
			appenddiv:"",
			onChange : function(file) {
			},
			onSubmit : function(files) {
			},
			onComplete : function(files) {
			},
			onEndAddFile:function(file,filediv){
				
			}
		}, options);
		this._rerouteClicks();
		this.initWnfile();
	}
	WnFile.prototype = {
		initWnfile : function() {
			//this._createAlter();
			this._createWnfileList();
			if(!this.configer.readonly){
				this._button.show();
				this._createInputs();
				this.deleteIds='-1';
				this.deleteFiles='-1';
			}else{
				this.loadWnfile();
			}
//			this._loadWnfile();
		},
		changeConfigers:function(options){
			this.configer = $.fn.extend(this.configer, options);
		},
		changeModuleid:function(id){
			this.configer = $.fn.extend(this.configer, {moduleid : id});
		},
		getModuleid:function(){
			return this.configer.moduleid;
		},
		loadWnfile:function(){
			var o=this;
			$.ajax({
　　　　			url: this.configer.loadAction,
　　　　			error: function (xmlHttpRequest, error) {
					//alert("访问异常！");
　　　　			},
    			type:'post',
    			data:{
    				filemodule:this.configer.filemodule,
    				moduleid:this.configer.moduleid
    			},
    			dataType:'json',
    			success:function(data) {
    				var reslutData=data.root;
    				var filediv = $("<div>");
					var img = $("<a href='"+o.configer.downAction+"?id="+reslutData.id+"'>"+reslutData.filename+"</a>");
					filediv.append(img);
					if(!o.configer.readonly){
						 filea = $("<a style='cursor:pointer;' title='在服务器中删除' alt='RX' fileid='"+reslutData.id+"' realname='"+reslutData.realname+"'>&nbsp;&nbsp;RX</a>");
						filediv.append(filea);
						filea.click(function() {
							if(confirm("确认从服务器删除？")){
								o._deleteFile($(this).attr("fileid"), this,$(this).attr("realname"));
							}
						});
					}
					o._addfile(filediv,reslutData.filename);
					$("#kv").val(data.kit.kv);
					$("input[name='flagKv']").val(data.kit.kv);
					$("#name").val(data.kit.name);
					
					$("#returnKit").attr("disabled",false);
					$("#addsubmit").html("提&nbsp;&nbsp;交");
					
    				/*for ( var i = 0; i < reslutData.length; i++) {
    					var filediv = $("<div>");
    					var img = $("<a href='"+o.configer.downAction+"?id="+reslutData[i].id+"'>"+reslutData[i].filename+"</a>");
    					filediv.append(img);
    					if(!o.configer.readonly){
    						var filea = $("<a style='cursor:pointer;' title='在服务器中删除' alt='RX' fileid='"+reslutData[i].id+"' realname='"+reslutData[i].realname+"'>&nbsp;&nbsp;RX</a>");
							filediv.append(filea)
							filea.click(function() {
								if(confirm("确认从服务器删除？")){
									o._deleteFile($(this).attr("fileid"), this,$(this).attr("realname"));
								}
							});
						}
						o._addfile(filediv,reslutData[i].filename);
    				}*/
    				
    			}
    		});
		},
		getFileLength:function(){
			return this.wnfileList.children("div").length;
		},
		_addfile:function(filediv,filename){
			this.wnfileList.append(filediv);
			if(this.wnfileList.children("div").length==this.configer.maxlength){
				this._button.hide();
			}
			this.configer.onEndAddFile.call(this, filename,filediv);
		},
		_deleteFile:function(f,a,realname){
			if($(a).attr("alt")=="X"){
				for ( var i = 0; i < this.inputs.length; i++) {
					if (this.inputs[i].val() == f) {
						this.inputs[i].val("");
						$(a).parent().remove();
						break;
					}
				}
			}else{
				this.deleteIds=this.deleteIds+','+f;
				this.deleteFiles=this.deleteFiles+','+realname;
				$(a).parent().remove();
			}
			if(this.wnfileList.children("div").length<this.configer.maxlength){
				this._button.show();
			}
		},
//		_createAlter : function() {
//			if(this.extObjAlterMsg!=null){
//				this.extObjAlterMsg.remove();
//			}
//			this.extObjAlterMsg = $("<div id='extObjAlterMsg' style='color:red;'></div>");
//			this._button.before(this.extObjAlterMsg);
//		},
		_createWnfileList:function(){
			if(this.configer.appenddiv!=null&&this.configer.appenddiv!=""){
				this.wnfileList=$(this.configer.appenddiv);
				return;
			}
			if(this.wnfileList!=null){
				this.wnfileList.remove();
			}
			this.wnfileList = $("<div>");
			this._button.after(this.wnfileList);
		},
//		_alterShow : function(msg) {
//			this.extObjAlterMsg.html(msg);
//			this.extObjAlterMsg.show();
//		},
//		_alterHide : function(msg) {
//			this.extObjAlterMsg.hide();
//		},
		_isFileHaveNow : function(fileName) {
			var haveNum = 0;
			for ( var i = 0; i < this.inputs.length; i++) {
				if (this.inputs[i].val() != ""
						&& this.inputs[i].val() == fileName) {
					haveNum++;
					if (haveNum >= 2)
						return true;
				}
			}
		},
		_isFileTypeAllow : function(fileName) {
			var file = fileName.replace(/.*(\/|\\)/, "");
			var fileType = (/[.]/.exec(file)) ? /[^.]+$/.exec(file
					.toLowerCase()) : '';
			var re = new RegExp("^(" + this.configer.accept + ")$", "i");
			if (!re.test(fileType)) {
				return false;
			}
			return true;
		},
		_createInputs : function() {
			this.inputs = new Array();
			var o = this;
			for ( var i = 0; i < this.configer.maxlength; i++) {
				var input = $("<input type='file'>");
				input.attr('name', this.configer.name);
				var styles = {
					'position' : 'absolute',
					'margin' : '-5px 0 0 -175px',
					'padding' : 0,
					'width' : '220px',
					'height' : '30px',
					'fontSize' : '14px',
					'opacity' : 0,
					'cursor' : 'pointer',
					'display' : 'none',
					'zIndex' : 2147483583
				};
				for ( var j in styles) {
					input[0].style[j] = styles[j];
				}
				if (!(input[0].style.opacity === "0")) {
					input[0].style.filter = "alpha(opacity=0)";
				}
				input.change(function() {
					if ($(this).val() == "")
						return;

//					o._alterHide();
					//检查是否允许传同一个文件
						if (!o.configer.allowRepeat) {
							if (o._isFileHaveNow($(this).val())) {
//								o._alterShow('该文件已经上传！');
								alert('该文件已经上传！');
								$(this).val("");
								return;
							}
						}
						//检查文件类型
						if (o.configer.accept != "") {
							if (!o._isFileTypeAllow($(this).val())) {
//								o._alterShow('上传文件类型错误！');
								alert('上传文件类型错误！');
								$(this).val("");
								return;
							}
						}
						if(!(o.configer.onChange.call(this, fileFromPath($(this).val()))==false)){
							var filediv = $("<div>");
							var filea = $("<a style='cursor:pointer;' title='删除' alt='X'>&nbsp;&nbsp;X</a>");
							filediv.html($(this).val());
							filediv.append(filea)
							o._addfile(filediv,fileFromPath($(this).val()));
							var filename=$(this).val();
							filea.click(function() {
								o._deleteFile(filename, this);
							});
						}else{
							$(this).val("");
						}
						
					});
				$("body").append(input);
				this.inputs.push(input);
			}
		},
		_rerouteClicks : function() {
			var input = null;
			var box, over = false;
			var o = this;
			this._button.mouseover(function() {
				if (over)
					return;
				over = true;
				box = getBox($(this));
			});

			$(document).mousemove(
					function(e) {
						if (!over)
							return;
						if ((e.pageX >= box.left) && (e.pageX <= box.right)
								&& (e.pageY >= box.top)
								&& (e.pageY <= box.bottom)) {
							for ( var i = 0; i < o.inputs.length&&o.wnfileList.children("div").length<o.inputs.length; i++) {
								if (o.inputs[i].val() == "") {
									input = o.inputs[i][0];
									input.style.top = e.pageY+ 'px';
									input.style.left = e.pageX + 'px';
									input.style.display = 'block';
									break;
								}
							}
//							if (input == null)
//								return;
							
						} else {
							over = false;
							if (input == null)
								return;
							input.style.display = 'none';
							input = null;
						}
					});

		},

		_createIframe : function() {
			var id = getUID();
			if(this.iframe!=null){
				return;
			}
			var iframe = $('<iframe src="javascript:false;" name="'
					+ this.configer.filemodule + id + '" id="'
					+ this.configer.filemodule + id + '" />');
			iframe.hide();
			$("body").append(iframe);
			this.iframe=iframe;
		},
		_createForm : function(iframe) {
			var form = $('<form method="post"  enctype="multipart/form-data"></form>');
			form.attr("action", this.configer.action);
			form.attr("target", iframe.attr("name"));
			form.hide();
			$("body").append(form);
			this.form=form;
		},
		_creatOthinput:function(form){
			var input=$("<input type='hidden'>");
			input.attr("name","filemodule");
			input.val(this.configer.filemodule);
			form.append(input);
			input=$("<input type='hidden'>");
			input.attr("name","moduleid");
			input.val(this.configer.moduleid);
			form.append(input);
			input=$("<input type='hidden'>");
			input.attr("name","path");
			input.val(this.configer.path);
			form.append(input);
			
			input=$("<input type='hidden'>");
			input.attr("name","deleteIds");
			input.val(this.deleteIds);
			form.append(input);
			input=$("<input type='hidden'>");
			input.attr("name","deleteFiles");
			input.val(this.deleteFiles);
			form.append(input);
		},
		submit : function() {
			var o = this;
			var haveValue = false;
			var files = new Array();
			$.each(this.inputs, function(i, n) {
				if (n.val() != "") {
					files.push(n.val());
					haveValue = true;
				}
			});
			if (!haveValue&&this.deleteIds=="-1") {
				o.configer.onComplete.call(this, files);
				return;
			}

			if (!(this.configer.onSubmit.call(this, files) == false)) {
				this._createIframe();
				this._createForm(this.iframe);
				for(var i=0;i<this.inputs.length;i++){
					this.form.append(this.inputs[i]);
				}
				this._creatOthinput(this.form);
				this.form[0].submit();
				this.form.remove();
				this.form = null;
				this.inputs = null;
				this.iframe[0].onload=function(){o.configer.onComplete.call(this, files);/*o.initWnfile();o.loadWnfile()*/};
			}
			
		}
	};

})();