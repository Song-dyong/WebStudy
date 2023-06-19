<%--
		주석처리
		JSP => Java Server Pages (파일이 아닌, 페이지)
		-----------------------------------------
			*** 웹 프로그램: C/S => cloud (CI/CD)
					Client / Server
					  |		   |
					 CSS	  Java
					 HTML	 Oracle	==> JDBC -> MyBatis -> JPA	
				  JavaScript Spring			
				  		   Spring-Boot
		1. 구성요소
			Client: 브라우저
			Server: WebServer / WAS
								Web Application Server
									=> Tomcat / Regin ... 
				Client접속 	========== WAS ==========
							Web Server => WebContainer
							----------	  ------------
							1) Client의 요청 받기
							  *** Client: 파일명 요청
							2) Web Server
								자체처리 (HTML,CSS,Json)
							   Web Container로 전송
							   	JSP,Servlet일 경우 HTML파일이 아니기 때문에 컨테이너로 전송
							   	=> JSP와 Servlet을 HTML로 변환
							   	=> 변환된 HTML을 Client로 전송
							=========================
				***WAS는 어떤 것을 사용했는지?
				  ----- Tomcat
				  형상관리는 어떤 것을 사용했는지?
				  ----- Git
		2. 웹 동작
					요청(request)
			Client -------------- ----------- WAS ------------
									1. request/response객체 생성
									   ------- --------
									    |			| 
								사용자가 보낸 모든 정보  브라우저 정보(IP/PORT)
								?뒤에 있는 데이터값
								사용자의 브라우저 정보
								(사용자 ip/사용자 port)	
									2. 서블릿 분석 => get방식 => doGet
												=> post방식 => doPost
										----------------------------
										-> 서블릿을 찾고, HTML을 만들어서
											브라우저로 전송
			 	   -------------- ----------------------------
					응답(response)
			
			위의 동작은 서블릿
			
			JSP동작
			개발자 (소스코딩) => 찾은 JSP ==> 서블릿 변환 ==> 컴파일 ==> 실행
															   |
										(메모리에 HTML / 메모리에 있는 HTML을 브라우저가 읽어가게 만든다.)
										
		3. Servlet vs JSP
		   --------------> 공통점: 웹 서비스 기능 (사용자 요청에 따라 HTML로 변환해서 화면에 보여줌)
			Servlet
				Servlet은 자바파일 => 수정시마다 컴파일 必
				1) 변경시마다 컴파일
				2) HTML을 자바 안에서 코딩 => 문자열 out.write() 필요
				3) CSS를 구사하기 어려움 (웹 퍼블리셔) => 개발자가 HTML, JS, CSS, Java, Oracle 모두 해야함
					==> 이러한 단점을 보완한 JSP (HTML을 쉽게 다루도록 만듦)
			JSP
				장점
				1) 서블릿보다 쉽고 작성이 빠름
				2) HTML과 Java가 분리되어 있음 => 분업 용이
				3) 컴파일 없이 바로 확인 가능 
							
		4. JSP 주로 사용되는 위치
			= JSP는 VIEW기능만 가져감 (Java를 별도로 만들기 때문) => 출력만 담당
			= JSP 사라지는 추세
				why? Spring이 서버 역할을 수행
					 화면 UI는 HTML에서 직접 작업
					 		 -------------- HTML의 제어문 기능 (타임리프)
					 						Vue, React등 JavaScript로 대체
			19page
			------
			  요청: URL을 이용해서 서버에 접근 + 요청
			  	  ---- 파일명 ? 요청하는 데이터
			  응답: Servlet/JSP에서 받아서 처리 후 => HTML로 변환
			------
			  정적 페이지: HTML (파일을 따로 제작해야함)
			  동적 페이지: JSP / Servlet (1개의 파일에서 데이터 변경)
			  -------- 정적 쿼리 / 동적 쿼리
			
		5. JSP 문법
			= 지시자 (★★★)
			= 스크립트릿
			= 내장객체 (★★★)
			= 액션태그 (★★★)
			= 빈즈 (★★★)
			= JSTL (★★★)
			= EL (★★★)
			= MVC (★★★)
			------------------------  (★★★)는 Spring에서도 사용 (JSP: 브라우저 연결)
	
		
		JSP => 24page
		-------------
		 1) 동적 컨텐츠 => 한 개의 파일 안에서 데이터를 변경할 수 있음 
		 2) 자바 언어로 사용
		 3) 자바 / HTML 분리 
		 4) JSP는 항상 서블릿으로 변경 후 실행
		 			------------------ 톰캣에 의해 변경
		 5) JSP는 화면에 출력할 내용의 메소드 안에 들어가는 내용을 코딩하는 것
		 	--- 클래스가 아닌 메소드 영역
		 	_jspService()
		 	{
		 		JSP
		 	}
		 	
		26page
		  => JSP
		  	동적 페이지를 생성하기 위한 자바의 사용기술 (자바)
		  	컴포넌트(클래스) 기반에서 개발 가능
		  => Servlet (28page)
		  	JSP와 연결해서 처리
		  	JSP: 정적 페이지 => 코딩하기 편리 (자바/HTML) 구분
		  				  => 소스 코딩이 노출 (라이브러리 제작은 어려움)
		  	Servlet: 동적 페이지 => 보안
		  	
		  	MVC
		  	Model: 자바
		  	View: 화면출력(JSP)
		  	Controller: 서블릿 (Spring)
		
		  72page
		  ------
		JSP 동작
		
		1. 사용자 요청
			브라우저의 주소창만 이용 가능
			http://localhost:8080/프로젝트명/폴더/XXX.jsp
			=>     -------------- 서버에 접근
		2. 맨 마지막 파일
			= .html(htm) , .css , .json => 웹 서버 자체에서 처리
			= .jsp => 일반 브라우저에서는 일반 텍스트파일로 인식 -> 자바로 변환 必
				=> Web Container(톰캣에 포함되어있음)에서 자바로 변환
					a.jsp => a_jsp.java
						  => 컴파일
						  => a_jsp.class (서블릿)
			= JVM(가상머신)이 서블릿 실행
						  ---------
						  메모리에 <%= %> , out.println(), HTML 저장
						 => 브라우저에서 읽기 => 실행
		3. servlet
			  |
			init(): 생성자 대신 멤버변수 초기화 => web.xml에서 담당
			  |
		  service() ==> 브라우저에 요청한 내용에 대한 처리 => 응답할 html을 작성하는 위치
		  		=doGet()
		  		=doPost()
		  	  |
		  destroy(): 파일이동 / 새로고침 => 자동으로 메모리 해제 (gc())
		
		4. JSP
		  	|
		  _jspInit() : web.xml => 저장된 내용 읽어서 저장
		  				error코드 , 서블릿 저장 , 환경 설정 위치 , 한글 변환
		  	|
		  _jspService(): 사용자 요청 처리 결과 
		  	|
		  _jspDestroy(): 페이지 이동 , 새로고침 (초기화) 
		  				=> request에 담긴 모든 정보를 잃어버림    cf) 장바구니 등 저장된 데이터가 필요한 경우, session 사용
		  				=> request유지  : forward, include()
		  				   request초기화 : sendRedirect()
						  
			
			 
				
	
	
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
	<% out.println("Hello JSP"); %> <p>
	<%= "Hello JSP" %> <%-- 권장사항 --%>
</body>
</html>