<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>자바 Switch~Case</h1>
	<%
		int i=3;
		switch(i){
		case 0:
	%>
			☆☆☆☆☆
	<%
			break;
		case 1:
	%>
			★☆☆☆☆
	<%
			break;
		case 2:
	%>
			★★☆☆☆
	<%
			break;
		case 3:
	%>
			★★★☆☆
	<%
			break;
		case 4:
	%>
			★★★★☆
	<%
			break;
		case 5:
	%>
			★★★★★
	<%
		}
	%>
	<h1>JSTL Switch~Case(choose) JavaServerPage Tag Library</h1>
	<c:set var="i" value="1"/>
	<c:choose>
		<c:when test="${i==0 }">☆☆☆☆☆</c:when>
		<c:when test="${i==1 }">★☆☆☆☆</c:when>
		<c:when test="${i==2 }">★★☆☆☆</c:when>
		<c:when test="${i==3 }">★★★☆☆</c:when>
		<c:when test="${i==4 }">★★★★☆</c:when>
		<c:when test="${i==5 }">★★★★★</c:when>
		<c:otherwise>ㅋㅋㅋㅋㅋ</c:otherwise>
	</c:choose>
</body>
</html>