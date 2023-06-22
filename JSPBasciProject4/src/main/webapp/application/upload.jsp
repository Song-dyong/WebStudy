<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.oreilly.servlet.*"%>
<%@ page import="com.oreilly.servlet.multipart.*" %>

<%
	request.setCharacterEncoding("UTF-8");
//C:\WebDev\WebStudy\JSPBasciProject4\src\main\webapp\image 아마 여기 다름
	//String path="C:\\WebDev\\WebStudy\\JSPBasciProject4\\src\\main\\webapp\\image";

//	C:\WebDev\WebStudy\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\JSPBasciProject4\image 
//	==> 톰캣이 읽어가는 경로				cf) 깃에 commit할 경우, 경로명 바뀜
	String path=application.getRealPath("/image");
	int max=1024*1024*100;
	String enctype="UTF-8";
	MultipartRequest mr=new MultipartRequest(request,path,max,enctype,new DefaultFileRenamePolicy());
	String name=mr.getOriginalFileName("upload");
	// 이동
	response.sendRedirect("list.jsp?fn="+name);
	
%>