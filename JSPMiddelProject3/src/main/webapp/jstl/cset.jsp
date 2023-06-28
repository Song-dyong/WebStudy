<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String name="hong";
	// stack on request (data plus)
	request.setAttribute("name", name);
	
%>
<%-- c:set은 request.getAttribute로는 사용 불가능
	 EL(expression language) ${} 내의 값으로만 출력 가능
	 JSTL(JavaServerPage Tag Library) --%>
<c:set var="name" value="shim"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Print by Java</h1>
	name1: <%=name %><br>
	<%-- 자바에서 request값 가져오는 방법 => request.getAttribute() --%>
	name2: <%=request.getAttribute("name") %><br>
	<h1>EL로 출력</h1>
	<%-- JSTL에서 출력되는 변수는 setAttribute된 key에 대한 값 --%>
	<%-- Jquery와 충돌 방지
		출력 => $
		VueJS => {{}}
		React => {}
	 --%>
	name3: ${name } <br>
	name4: <c:out value="${name }"/><br>
	name5: <c:out value="<%=name %>"/><br>
</body>
</html>