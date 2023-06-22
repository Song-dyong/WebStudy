<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*, com.sist.dao.*"
	%>
<%--
		
		웹 브라우저 ======================== 서버: 사용자가 요청한 파일을 컴파일, 인터프리터해서 html로 바꾼 뒤, buffer로 보냄
					TCP(소켓을 통해 연결)		=> 버퍼에 저장된 html/xml/json 등을 웹 브라우저가 읽어서 출력
															 --------------- 파싱해야할 타입을 미리 알려주는 것이 contentType
													buffer와 브라우저의 데이터를 연결해주는 stream => 스트림이 없을 경우, 데이터 패킷이 손실될 가능성이 있음
													
		
			
		JSP
		---
		1) 지시자: page / include / taglib
				 -----
				 JSP파일에 대한 정보 
				 1. contentType: 브라우저에 실행하는 타입
				 				--------------------
				 				html => text/html
				 				xml  => text/xml
				 				json => text/plain (★★★★★)
				 				---------------------------
				 				AJax / Vue / React => JSON
				 				-------------------------- RestFul
				 2. import: 여러 번 사용 가능, 라이브러리 읽기
				 3. errorPage: 에러 발생시 에러 출력 화면으로 이동
				 4. buffer: html태그를 저장하는 공간
				 			소스 미리보기 
				 			=> 사용자마다 한개만 생성 (브라우저가 연결해서 읽어가는 위치)
		2) 스크립트: 자바/HTML을 분리해서 소스코딩
		   ------ 자바언어 코딩 위치 => 스크립트를 벗어나면 일반 텍스트로 인식 
			   <%!	%>: 선언문 (멤버변수, 메소드 선언)
			   			=> 클래스 제작시 클래스 블록 (사용 빈도가 없음)
			   			=> 자바 클래스를 만들어서 호출하기 때문에
			   <%	%>: 일반 메소드 영역 => 지역변수 , 제어문 , 메소드 호출 ...
			   			_jspService()
			   			{
			   				------------------
			   				 JSP에 첨부하는 소스
			   				------------------
			   			}
			   <%=	%>: 화면 출력 (변수 , 문자열...) => out.println(<%= %>)
			   
		3) 내장 객체: 미리 객체를 생성해서 필요한 위치에 사용 가능
			request = HttpServletRequest
						사용자 정보 (요청정보, 추가정보, 브라우저정보)
						  -요청정보
						  	1|getParameter(): 사용자가 보낸 단일값 받기 
						  					==> 리턴형이 String (변환시에 Wrapper클래스 사용)
						  	2|getParameterValues(): 사용자가 보낸 여러 개의 값을 동시에 받기
						  					==> String[] (checkbox / select 멀티사용...)
						  	3|setCharacterEncoding(): 디코딩 메소드
						  					==> POST에서만 사용 (GET방식은 자동화 처리 - window10 이상)
						  									  ---------------------------------
						  									   server.xml에서 처리 (한글처리, 포트)
						  									   	cf) 포트 8080은 프록시서버로 이미 사용중이므로, 원래는 사용하면 안됨
						  									   	
						  -추가정보 (MVC, MV구조에서 사용) => 오라클에서 받은 값을 추가 => JSP
						  	1|setAttribute(): 기존에 있는 request값에 출력에 필요한 데이터를 추가해서 전송
						  	2|getAttribute(): 추가해서 보낸 데이터를 받을 경우에 사용
						  	
						  -브라우저정보
						  	1|getRemoteAddr(): 접속자의 IP
						  	2|getRequestURL(), getRequestURI(), getContextPath()
						  
			response = HttpServletResponse (응답정보, 화면이동정보)
						-응답정보
							1|setContextType(): HTML / XML 
										==> 페이지 지시자
							2|addCookie(): 쿠키 전송
						  	  ----------- 한 번만 수행할 수 있음
						  	  
						-화면이동정보
						 ---------
						 	필요시에 서버에서 화면을 요청 화면이 아닌 다른 화면으로 이동
						 		   ----------------------------------------
						 		   login => main
						 		   insert => list
						 		   delete => list
						 		   update => detail
						 	1|sendRedirect(): GEt방식 / request의 초기화 후 이동
						 	
						-Header 정보: 실제 데이터 전송 전에 처리
							1|setHeader(): 한글변환 / 다운로드시에 파일명, 크기 => 다운로드 창을 보여줌
						 		   
			out = JspWriter (출력버퍼 관리)
					-출력(메모리)
						1|println(): <%=	%>로 대체
						
					-메모리 확인
						1|getBufferSize(): 총 버퍼크기확인
						2|getRemaining(): 사용 중인 버퍼크기확인
					-버퍼 지우기
						1|clear(): 버퍼 지우기
			session
			application
			pageContext
			-------------- 필수사항
			config: web.xml으로 바뀜 (환경설정)
			exception: try~catch로 처리
			page: this객체를 의미
			
			
		autoFlush="true"
		---------
			-'ture'일때 : 버퍼가 가득 차면 버퍼 내용을 전송하고 버퍼를 비움.
			-'false'일때 : 버퍼가 가득 차면 예외발생 후 작업을 중지시키고 에러페이지 출력
			
 --%>

<%-- out 출력 (복잡한 HTML구조) print --%>
<!--  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
</body>
</html> -->

<%
	EmpDAO dao = new EmpDAO();
	List<EmpVO> list = dao.empListData();
	out.print("<!DOCTYPE html>");
	out.print("<html>");
	out.print("<head>");
	out.print("<meta charset=\"UTF-8\">");
	out.print("<title>Insert title here</title>");
	out.print("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
	out.print("<style>");
	out.print(".container{ margin-top:50px }");
	out.print(".row{ margin: 0px auto; width: 800px }");
	out.print("h1{text-align:center}");
	out.print("</style>");
	out.print("</head>");
	out.print("<body>");
	out.print("<div class=container>");
	out.print("<h1>사원 목록</h1>");
	out.print("<div class=row>");
	out.print("<table class=\"table table-striped\">");
	out.print("<tr class=warning>");
	out.print("<th class=text-center>사번</th>");
	out.print("<th class=text-center>이름</th>");
	out.print("<th class=text-center>직위</th>");
	out.print("<th class=text-center>입사일</th>");
	out.print("<th class=text-center>급여</th>");
	out.print("<th class=text-center>성과급</th>");
	out.print("<th class=text-center>부서명</th>");
	out.print("<th class=text-center>근무지</th>");
	out.print("<th class=text-center>호봉</th>");
	out.print("</tr>");
	for (EmpVO vo : list) {
		out.print("<tr>");
		out.print("<td class=text-center><a href=\"MainServlet?mode=2&empno=" + vo.getEmpno() + "\">" + vo.getEmpno()
		+ "</a></td>");
		out.print("<td class=text-center><a href=\"MainServlet?mode=2&empno=" + vo.getEmpno() + "\">" + vo.getEname()
		+ "</a></td>");
		out.print("<td class=text-center>" + vo.getJob() + "</td>");
		out.print("<td class=text-center>" + vo.getDbday() + "</td>");
		out.print("<td class=text-center>" + vo.getDbsal() + "</td>");
		out.print("<td class=text-center>" + vo.getComm() + "</td>");
		out.print("<td class=text-center>" + vo.getDvo().getDname() + "</td>");
		out.print("<td class=text-center>" + vo.getDvo().getLoc() + "</td>");
		out.print("<td class=text-center>" + vo.getSvo().getGrade() + "</td>");
		out.print("</tr>");
	}
	out.print("</table>");
	out.print("</div>");
	out.print("</div>");
	out.print("</body>");
	out.print("</html>");
%>