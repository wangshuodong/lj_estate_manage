function pageFun(currentPage, totalPages, pageSize) {
	if(totalPages == 0){
		$("#pagination").html("");
		//totalPages = 1;
		//currentPage = 1;
	}
	var options = {
		alignment : 'right',
		currentPage : currentPage,
		totalPages : Math.ceil(totalPages / pageSize),
		numberOfPages : 3,
		num_display_entries : 6,
		bootstrapMajorVersion : 3,
		useBootstrapTooltip : true,
		onPageChanged : function(event, oldPage, newPage) {
			if (newPage != currentPage) {
				pageNext(newPage);
			}
		},
		tooltipTitles : function(type, currPage, current) {
			switch (type) {
			case "first":
				return "最后一页 <i class='icon-fast-backward icon-white'></i>";
			case "prev":
				return "上一页  <i class='icon-backward icon-white'></i>";
			case "next":
				return "下一页 <i class='icon-forward icon-white'></i>";
			case "last":
				return "最后一页 <i class='icon-fast-forward icon-white'></i>";
			case "page":
				return "第 " + currPage
						+ "  页<i class='icon-file icon-white'></i>";
			}
		},
		itemContainerClass : function(type, page, current) {
			return (page === current) ? "active" : "pointer-cursor";
		}
	};
	$('#pagination').bootstrapPaginator(options);
	$("#pagination ul").attr("class", "pagination");

}

function pageNext(newPage) {
	$("#currPage").val(newPage);
	sumbitForm("none");
}