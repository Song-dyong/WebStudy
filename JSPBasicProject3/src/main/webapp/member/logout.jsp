<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// session 해제 => 저장된 모든 정보를 지운다
	session.invalidate();
	// session 정보를 1개씩 지울 경우 session.removeAttribute("key");
	response.sendRedirect("../databoard/list.jsp");	// ..은 changeDirectory
	
%>