<%@ page contentType="text/html;charset=GBK" %>
<html>
<title>����ݱ����쳣��Ϣ</title>
<body>

<%
	Exception e = ( Exception ) request.getAttribute( "exception" );
	out.println( "<div style='color:blue'>" + e.getMessage() + "</div>" );
%>

</body>
</html>