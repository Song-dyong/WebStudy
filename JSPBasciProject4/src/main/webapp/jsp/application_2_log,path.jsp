<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%--
		application: 자원관리 (log, getRealPath)
		
 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	// web.xml에 등록된 내용을 읽어올 수 있음.
								// Parameter -> 매개변수
	String driver = application.getInitParameter("driver");
	String url = application.getInitParameter("url");
	String username = application.getInitParameter("username");
	String password = application.getInitParameter("password");

	/* System.out.println("driver: " + driver);
	System.out.println("url: " + url);
	System.out.println("username: " + username);
	System.out.println("password: " + password); */
	
	// log는 날짜, 시간, 
	application.log("driver: " + driver);
	application.log("url: " + url);
	application.log("username: " + username);
	application.log("password: " + password);
	
	String path=application.getRealPath("/");
	System.out.println(path);
	// C:\WebDev\WebStudy\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\JSPBasciProject4\
	// ==> 실제 저장되는 경로
	
	%>
</body>
</html>