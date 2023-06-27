<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 화면 전송이 아닌, Cookie를 생성해서 전송해주는 역할
	=> 전송이 끝나면, detail로 화면 이동 --%>

<%
	// 사용자 요청값 받기
	String fno=request.getParameter("fno");
	// 1. 쿠키 생성
	Cookie cookie=new Cookie("food_"+fno,fno);	// 중복된 cookie는 저장 x 
	// 2. 저장 기간 설정
	cookie.setMaxAge(60*60*24);	// 24시간 저장
	// 3. 저장 경로 지정
	cookie.setPath("/");	// Root에 저장
	// 4. 클라이언트 브라우저로 전송
	response.addCookie(cookie);
	// 5. 실제 Detail.jsp로 이동
	response.sendRedirect("detail.jsp?fno="+fno);	// GET방식이므로, ?를 통해 값 전송 cf)POST는 form을 통해 전송 
	
%>