<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="error.jsp"%>
<%
	int a=10/0;
/* 
	500: 내부서버오류 (자바문법 오류) 
	404: 파일을 못찾는 경우
	400: bed request ()
		 ?no=10	===> boolean no	
	412: 한글 변환 코드 문제
	200: 정상수행
	403: 접근 거부

*/
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%=a %>
</body>
</html>