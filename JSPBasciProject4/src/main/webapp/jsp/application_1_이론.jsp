<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
		application: ServletContext
		 서버 관리 (서버에 대한 정보 , 로그 정보 , 자원 관리)
			=서버 버전: servlet의 버전
				1) 서버 이름: getServerInfo() - 톰캣
				2) getMajorVersion()
				3) getMinorVersion()
					17.01 , 3.5
				    -- --	
				 메이저  마이너
				 	cf) Spring5 => 보안 중심
				 		Spring6 => 분산 서버 (cloud)
				4) log(): 로그 기록 붉은 색으로 출력
				5) 자원관리 (파일) => getRealPath(): 실제 저장 경로 ex) server.core
 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
서버이름: <%=application.getServerInfo() %> <br>
버전: <%=application.getMajorVersion() %> <br>	<!-- Dynamic Web Module Version의 버전 -->
부버전: <%=application.getMinorVersion() %> <br>
<!-- MIME:<%=application.getMimeType("application_1.jsp") %>  contentType확인 -->
</body>
</html>