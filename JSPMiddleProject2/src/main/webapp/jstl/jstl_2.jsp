<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 선언문은 자바로 취급하지 않는다 -->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>자바 IF문</h1>
	<%
		for(int i=1;i<=10;i++){
			if(i%2==0){
	%>
				<%=i %>&nbsp;&nbsp;&nbsp;
	<%
			}
		}
	%>
	<h1>JSTL(JavaServerPage Tab Library) IF문</h1>
	<%-- step="1" 생략 가능 --%>
	<c:forEach var="i" begin="1" end="10">
		<c:if test="${i%2==0 }">
			${i }&nbsp;&nbsp;&nbsp;		
		</c:if>
	</c:forEach>
</body>
</html>