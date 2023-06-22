<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" buffer="16kb"%>
<%--
		out.print()
		<%= %>		둘은 동일한 역할
		------ 클래스로 변경될 때, 자동으로 out.print()로 변환
			
			==> 자바코드를 최대한 줄여서 사용하길 권장
			
		
 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	버퍼 크기: <%=out.getBufferSize() %><br>
	남은 버퍼: <%=out.getRemaining() %><br>
	현재 사용 중인 버퍼: <%=out.getBufferSize()-out.getRemaining() %>
</body>
</html>