var weeks = [ '日', '一', '二', '三', '四', '五', '六' ];
function formatDate(date, format) {
	return format.replace(/YYYY|MM|DD|hh|mm|ss|星期/g, function(a) {
		switch (a) {
		case 'YYYY':
			return date.getFullYear();
		case 'MM':
			return ((date.getMonth() + 1 < 10) ? '0' : '')
					+ (date.getMonth() + 1);
		case 'DD':
			return (date.getDate() < 10 ? '0' : '') + date.getDate();
		case 'hh':
			return (date.getHours() < 10 ? '0' : '') + date.getHours();
		case 'mm':
			return (date.getMinutes() < 10 ? '0' : '') + date.getMinutes();
		case 'ss':
			return (date.getSeconds() < 10 ? '0' : '') + date.getSeconds();
		case '星期':
			return '星期' + weeks[date.getDay()];
		}
	});
}

function cutContent(content,length){
	content = content == null ? "" : content ;
	var contentStr = content.substring(0,length) ;
	if(content.length > length)
		contentStr += "...";
	return contentStr;
}

function nullToStr(str){
	if(str == null)
		return "";
	else 
		return str;
}

function compareDate(d1,d2)
{
	return (new Date(d1.replace(/-/g,"/")) > new Date(d2.replace(/-/g,"/")))
}

$.fn
	.extend({
		_jsonToForm : function(json) {
			var form = $(this);
			if ($.isPlainObject(json)) {
				$.each(json, function(i, n) {
					var field = form.find('input[name=' + i + ']');
					switch (field.attr('type')) {
					case 'hidden':
						field.val(n);
						break;
					case 'text':
						field.val(n);
						break;
					case 'radio':
						field.each(function() {
							var value = $(this).val();
							if (value == 'true') {
								value = true;
							} else if (value == 'false') {
								value = false;
							}
							if (value == n) {
								$(this).get(0).checked = true;
							}
						});
						break;
					case 'checkbox':
						field.each(function() {
							var value = $(this).val();
							if (value == 'true') {
								value = true;
							} else if (value == 'false') {
								value = false;
							}
							if (value == n) {
								$(this).get(0).checked = true;
							}
						});
						break;
					default:
						field = form.find('select[name=' + i + ']');
						if (!$.isEmptyObject(field.get(0))) {
							field.children('option').each(function() {
								if ($(this).val() == n) {
									this.selected = true;
								}
							});
						}
						field = form.find('textarea[name=' + i + ']');
						if (!$.isEmptyObject(field.get(0))) {
							field.val(n);
						}
						break;
					}
				});
			}
		},
		_formToJson : function() {
			var str = $(this).serialize();
			str = str.replace(/\+/g, "%20");
			str = str.replace(/&/g, "\",\"");
			str = str.replace(/=/g, "\":\"");
			var data = eval("({\"" + str + "\"})");
			$.each(data, function(i, n) {
				data[i] = $.trim(decodeURIComponent(n));
			});
			return data;
		},
		_ajaxForm : function(callback) {
			if ($.isEmptyObject($('#ajaxFormResultTip').get(0))) {
				$('body')
						.append(
								'<div id="ajaxFormResultTip" style="display: none;"></div>');
			}
			var options = {
				target : '#ajaxFormResultTip',
				url : $(this).attr('action'),
				type : 'POST',
				success : function() {
					if (typeof callback === 'function') {
						try {
							callback(eval('('
									+ $('#ajaxFormResultTip').text()
									+ ')'));
						} catch (e) {
							alert('AJAX请求返回数据解析错误');
							/* console.log('AJAX请求返回数据解析错误~['+e.message+']'); */
						}
					}
				}
			};
			$(this).ajaxSubmit(options);
		}
});


