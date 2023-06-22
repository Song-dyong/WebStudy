<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%	// exception 은 isErrorPage=true여야 사용 가능
		String msg=exception.getMessage();
		String message=exception.toString();
	%>
	에러메세지: <%=msg %><br>
	<%=message %>
</body>
</html>