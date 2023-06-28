<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String msg="i'm hong";
%>
<c:set var="msg" value="<%=msg %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Java</h1>
	<%=msg.length() %>
	<%=msg.subSequence(0, 3) %>
	<h1>JSTL</h1>
	${fn:length(msg) }<br>
	${fn:substring(msg,0,3) }	<br>
	${fn:replace(msg,'hong','park') }
</body>
</html>