<%--
		response (173page) = 응답
		=> request(요청) , response(응답)
		=> response : HttpServletResponse (클래스이름)
			응답메소드 (174page)
			setHeader(): 사전에 전송할 데이터가 있는 경우
			----------- 권한을 부여 / 다운로드
			sendRedirect(): 서버에서 화면 이동
			-------------- 
				로그인 => 메인페이지로 이동
				글쓰기 => 목록페이지로 이동
				수정	 => 목록/상세페이지로 이동
				
			addCookie(): 쿠키 전송 (클라이언트 브라우저로 전송)
			*** 쿠키와 동시에 HTML을 전송할 수 없다
			
			*** response / request는 각 JSP마다 따로 가지고 있다
			
			화면이 이동될 때마다, request의 값은 초기화
			Session은 모든 jsp에서 같은 값을 쓰도록 따로 저장 (Static변수와 같은 역할)
			
			여러 번의 선택으로 화면이 이동되는 것을 한 페이지에서 request를 유지하기위해 Ajax사용
		
 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- response_1 페이지로 request값을 보내면, 그 페이지에서 사용 가능 
		but! 1페이지에서 2페이지로 다시 넘기면, request값이 넘어가지 않음
			request는 매개변수이므로, 초기화됨 -->
	<a href="response_1.jsp?id=admin&pwd=1234">전송</a>
</body>
</html>