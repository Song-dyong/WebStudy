<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	List<String> list=new ArrayList<String>();
	list.add("red");
	list.add("blue");
	list.add("cyan");
	list.add("yellow");
	list.add("green");
%>
<c:set var="list" value="<%=list %>"/>
<%--
		JSTL에서 제공하는 태그는 XML로 만들어져 있따
		=> 1. 태그, 속성은 대소문자 구분
		=> 2. 속성값은 반드시 " "
		=> 3. 여는 태그 / 닫는 태그를 반드시 사용
 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Java</h1>
	<ul>
		<li><%=list.get(0) %></li>
		<li><%=list.get(1) %></li>
		<li><%=list.get(2) %></li>
		<li><%=list.get(3) %></li>
		<li><%=list.get(4) %></li>
	</ul>
	<h1>자바에서 for문</h1>
	<ul>
		<%
			for(int i=0;i<list.size();i++){
		%>
			<li><%=list.get(i) %></li>
		<%		
			}
		%>
	</ul>
	<h1>Java에서 forEach</h1>
	<ul>
		<%
			for(String color:list){
		%>
			<li><%=color %></li>
		<%
			}
		%>
	</ul>
	<h1>EL</h1>
	<ul>
		<li>${list[0] }</li>
		<li>${list[1] }</li>
		<li>${list[2] }</li>
		<li>${list[3] }</li>
		<li>${list[4] }</li>
	</ul>
	<h1>JSTL forEach</h1>
	<ul>
		<c:forEach var="color" items="${list }">
			<li>${color }</li>
		</c:forEach>
	</ul>
	<h1>JSTL2 forEach</h1>
	<ul>
	<%-- varStatus: list의 index번호를 읽어옴 --%>
		<c:forEach var="color" items="${list }" varStatus="s">
			<%-- <li>${s.index+1}.${color }</li> --%>
			<li>${list[s.index] }</li>
		</c:forEach>
	</ul>
	<h1>JSTL3 forEach</h1>
	<ul>
		<c:forEach var="color" items="${list }" varStatus="s">
			<li>${s.index+1}. ${color }</li>
		</c:forEach>
	</ul>
	<h1>JSTL4</h1>
	<%
		List<String> names=new ArrayList<String>();
		names.add("hong");
		names.add("shim");
		names.add("kim");
		names.add("lee");
		names.add("park");
		
		List<String> sexs=new ArrayList<String>();
		sexs.add("m");
		sexs.add("w");
		sexs.add("m");
		sexs.add("w");
		sexs.add("m");
	%>
	<c:set var="names" value="<%=names %>"/>
	<c:set var="sexs" value="<%=sexs %>"/>
	<ul>
		<c:forEach var="name" items="${names }" varStatus="s">
			<li>${name }(${sexs[s.index]})</li>
		</c:forEach>	
	</ul>
	
</body>
</html>