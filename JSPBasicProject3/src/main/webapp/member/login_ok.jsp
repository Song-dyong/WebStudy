<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*, com.sist.vo.*"%>
<%
	String id=request.getParameter("id");
	String pwd=request.getParameter("pwd");
	
	MemberDAO dao=MemberDAO.newInstance();
	MemberVO vo=dao.isLogin(id, pwd);	
	
	if(vo.getMsg().equals("OK")){
		session.setAttribute("id", vo.getId());
		session.setAttribute("name", vo.getName());
		session.setAttribute("sex", vo.getSex());
		session.setMaxInactiveInterval(3600);	// 1시간 session 저장
	}
%>
<%=vo.getMsg() %>
