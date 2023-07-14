<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 
		*.do : 무조건 DispatcherServlet호출
					------------------ FrontController
					1. 요청받기
					2. Model 호출
						=> DispatcherServlet으로부터 request를 받아서,
						=> 사용자가 요청한 데이터를 받는다
						=> 오라클을 통해서 request값을 통해 값을 얻은 뒤,
						=> 결과값을 request에 담아서 전송 (request.setAttribute())
					3. Model은 JSP를 찾아서 request를 전송
				Model에서 처리 (비즈니스 로직) : Model (Model, VO, DAO) => BackEnd
					Model : 브라우저 연결
					VO : 데이터를 모아서 전송할 목적
					DAO : 데이터베이스만 연결
				JSP에서 화면에 데이터 출력 (프레젠테이션 로직) => FrontEnd  
 -->
    <%
    	response.sendRedirect("main/main.do");
    %>
    
    
    