<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/runqianReport4.tld" prefix="report" %>

<html>
<body leftMargin=0 topMargin=0 rightMargin=0 bottomMargin=0>

<script language=javascript>
	var _editorBorderLeft = "1px solid red";      //填报编辑框左边框
	var _editorBorderTop = "1px solid red";       //填报编辑框上边框
	var _editorBorderRight = "1px solid red";     //填报编辑框右边框
	var _editorBorderBottom = "1px solid red";    //填报编辑框下边框
	var _editingRowBackColor = "#d1f2fe";         //填报编辑行背景色，设为空值则不标记当前编辑行
	var _calendarMainBackColor = "#fa8072";       //填报下拉日历主面板色
	var _calendarWeekColor = "#FFFFFF";           //填报下拉日历周文字色
	var _calendarDayColor = "#000040";            //填报下拉日历日期文字色
	var _calendarDayBackColor = "#ffe4e1";        //填报下拉日历日期面板色
</script>

<%
	request.setCharacterEncoding( "GBK" );
	String file = request.getParameter( "file" );
	String form = request.getParameter( "form" );
	String needQuery = request.getParameter( "needQuery" );
	if( needQuery == null ) needQuery = "1";
	String params = request.getParameter( "params" );
	String saveAsName = request.getParameter( "saveAsName" );
	if( saveAsName == null || saveAsName.trim().length() == 0 ) saveAsName = file;
	String needFunctionBar = request.getParameter( "needFunctionBar" );
	String border = request.getParameter( "border" );
	if( border == null || border.trim().length() == 0 ) border = "border: thin inset";
	String sscale = request.getParameter( "scale" );
	double scale = 100;
	try {
		scale = Double.parseDouble( sscale );
	}catch( Exception e ) {}
	scale = scale / 100;
	sscale = String.valueOf( scale );
	
	if( form != null && form.trim().length() > 0 ) {
	%>
	<div id=paramDiv>
		<table><tr><td>
			<report:param name="form1" paramFileName="<%=form%>"
				needSubmit="no"
				params="<%=params%>"
			/>
		</td>
		<% if( needQuery.equals( "1" ) ) { %>
		<td valign=top><a href="javascript:_submit( form1 )"><img src="images/rq_query.jpg" border=no style="vertical-align:middle"></a></td>
		<% } %>
		</tr></table>
		<hr width=100%>
	</div>
	<%}
	String appmap = request.getContextPath();
	String printImage = "<img src='" + appmap + "/images/rq_print.gif' border=no style='vertical-align:middle' alt='打印'>";
	String excelImage = "<img src='" + appmap + "/images/rq_excel.gif' border=no style='vertical-align:middle' alt='存为Excel'>";
	String pdfImage = "<img src='" + appmap + "/images/rq_pdf.gif' border=no style='vertical-align:middle' alt='存为Pdf'>";
	String submitImage = "<img src='" + appmap + "/images/rq_savedata.gif' border=no style='vertical-align:middle' alt='保存数据'>";
	if( needFunctionBar.equals( "1" ) ) { %>
		<div id="functionBar">
			<a href="#" onClick="report1_print();return false;"><%=printImage%></a><a href="#" onClick="report1_saveAsExcel();return false;"><%=excelImage%></a><a href="#" onClick="report1_saveAsPdf();return false;"><%=pdfImage%></a><a href="#" onClick="try{_submitTable(document.getElementById( 'report1'));}catch(e){}return false;"><%=submitImage%></a>
		</div>
	<%}
%>

<report:html name="report1" reportFileName="<%=file%>"
	scale="<%=sscale%>"
	params="<%=params%>"
	excelPageStyle="0"
	needScroll="yes"
	scrollWidth="100%"
	scrollHeight="400"
	scrollBorder="<%=border%>"
	selectText="yes"
	promptAfterSave="yes"
	funcBarLocation=""
	saveAsName="<%=saveAsName%>"
	backAndRefresh="no"
/>
<div id=div1 style="width:100%;height:100%"></div>

<script language=javascript>
	document.body.style.overflow = "hidden";
	window.onresize = myResize;
	function myResize() {
		var scrolldiv = document.getElementById( "report1_scrollArea" );
		if( scrolldiv != null ) {
			var div1 = document.getElementById( "div1" );
			div1.style.display = "";
			var h = div1.offsetHeight;
			h -= getHeightX( document.body );
			var paramDiv = document.getElementById( "paramDiv" );
			if( paramDiv != null ) h -= paramDiv.offsetHeight;
			var functionBar = document.getElementById( "functionBar" );
			if( functionBar != null ) h -= functionBar.offsetHeight;
			if( ! document.all ) {
				h -= 3;
				scrolldiv.style.width = scrolldiv.offsetWidth - 4;
			}
			scrolldiv.style.height = h;
			_resizeScroll();
			div1.style.display = "none";
		}
	}
	myResize();
</script>

</body>
</html>
