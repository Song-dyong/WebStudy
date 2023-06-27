<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
		EL 지원하는 내장객체 (581page)
		1) requestScope => request.setAttribute()
		2) sessionScope	=> session.setAttribute()
		3) param		=> request.getParameter()
		4) paramValues	=> request.getParameterValues()
 --%>
<%
	String name="hong";
	request.setAttribute("name", "hong");
	session.setAttribute("name", "shim");
	// request와 session의 key값이 중복될 경우, request가 우선순위 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
이름:${name },${requestScope.name }	<!-- requestScope 생략 가능 --> <br>
<%=request.getAttribute("name") %>
<!-- 변수명 x => key를 설정한 뒤, key에 해당하는 값 출력 -->
</body>
</html>