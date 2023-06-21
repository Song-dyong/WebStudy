<%--
		JSP
		---
		 1. 동작순서
		 	--------------------------------------------
		 	1) client 요청 (주소창을 이용해서 서버에 연결)
		 		http://localhost:8080/JSPBasicProject2/jsp/request.jsp
		 		----   --------- ---- ---------------- 
		 	protocol   ServerIP  port   ContextPath
		 		--------------------- --------------------------------
		 				| 서버 관련				| 클라이언트 요청 관련 (URI)
		 				------------------------------------------ URL
		 	2) DNS를 거쳐서 => localhost(도메인) => ip변경
		 	3) ip/port를 이용해서 서버에 연결
		 		new socket(ip,port) => TCP
		 	------------------------------------------
		 	4) Web Server
		 		httpd
		 		-----
		 		 = HTML, XML, CSS, JSON => 웹 서버 자체에서 처리 후에, 값을 브라우저로 전송
		 		 = JSP, Servlet => 웹 컨테이너에서 HTML로 변환 후, 값을 브라우저로 전송
		 		   ------------
		 		   		|
		 		   	Web Container (WAS) => Java로 변경
		 		   						=> 컴파일
		 		   						=> 실행
		 		   						  ----- 실행결과를 메모리에 모아둔다
		 	5) 메모리에 출력한 내용을 브라우저로 응답
		 	
		 JSP (Java Server Page) : 서버에서 실행되는 자바파일
		 --------------------------------------------
		 	_jspInit()		=> web.xml => 초기화
		 	_jspWervice()	=> 사용자의 요청을 처리하고, 결과값을 HTML로 전송
		 	------------- 공백
		 	{
		 		영역에 소스 코딩 => JSP   // 메소드 안에 코딩
		 	}
		 	_jspDestroy()	=> 새로고침, 화면이동... => 메모리에서 해제
		 	
public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException { 	
		 	<내부객체들>
		 	final javax.servlet.jsp.PageContext pageContext;
    		javax.servlet.http.HttpSession session = null;
    		final javax.servlet.ServletContext application;
    		final javax.servlet.ServletConfig config;
    		javax.servlet.jsp.JspWriter out = null;
   		 	final java.lang.Object page = this;
    		javax.servlet.jsp.JspWriter _jspx_out = null;
    		javax.servlet.jsp.PageContext _jspx_page_context = null;
    		
    		JSP => jspService() 메소드 안에 코딩하는 중
}		 	
		 	
		 2. 지시자
		 	page	형식) <%@ page 속성="" 속성="" %>
		 	----
		 	  JSP파일에 대한 정보
		 	  속성 : 
		 	  		contentType=""
		 	  			= 브라우저에 어떤 파일인지 알려준다
		 	  			  ------ HTML / XML / JSON (외에는 일반 텍스트)
		 	  			  		-----	---	  ----
		 	  			  	text/html text/xml text/plain => RestFul
		 	  		import="java.util.*,java.io.*"
		 	  		errorPage: error시에 이동하는 페이지 지정
		 	  		buffer="8kb" => 16kb, 32kb...
		 3. 스크립트 사용법
		 	=> 자바가 코딩되는 영역
		 		<%! %>: 선언문 (메소드, 멤버변수) => 사용 빈도가 거의 없음
		 		<%  %>: 자바 코딩(일반 자바) => 제어문, 메소드 호출, 지역변수
		 		<%= %>: 화면출력
		 				out.println(여기에 들어가는 코딩)
		 		JSP = Model1 방식은 현재 사용되지않음 => model2(mvc) => Domain(MSA)
		 												|			  |
		 											  Spring4	Spring5/Spring6
		 4. 내장객체
		 	=> 165page
		 	9가지 지원
		 		= request 		=> HttpServletRequest	(★★★★★)
		 			-request는 관리자 (톰캣)
		 			1) 서버정보 / 클라이언트 브라우저 정보
		 				getServerInfo()
		 				getPort()
		 				getMethod()
		 				getRequestURL()
		 				getRequestURI()
		 				getContextPath()
		 				
		 			2) 사용자 요청 정보
		 				데이터 전송시 => 데이터가 request에 묶여서 들어옴
		 				=단일 데이터
		 					getParameter() : 값을 읽어오는 메소드 (1과 aaa를 읽어옴)
		 				=다중 데이터
		 					getParameterValues() => checkbox, select => multiline (hobby의 값이 여러개이므로, 값들을 배열로 받음)
		 				=한글 변환(디코딩)												String[] getParameterValues()
		 					setCharacterEncoding => UTF-8
		 				=키를 읽는다
		 					getParameterNames() : 키 이름만 가져오는 메소드 (no, name을 읽을 수 있음)
		 					받는파일명?no=1&name=aaa
		 					-------
		 					a.jsp?no1&name=aaa
		 					
		 					a.jsp?no1&name=aaa&hobby=a&hobby=b&hobby=c	
		 					
		 					
		 					
		 			3) 추가 정보 => MVC
		 				setAttribute(): request 데이터 추가 전송
		 				getAttribute(): 전송된 데이터 읽기
		 				
		 		= response 		=> HttpServletResponse	(★★★★★)
		 			=Header 정보 
		 			  다운로드 => 파일명, 크기 등을 미리 보여주는 것
		 			  setHeader()
		 			=응답 정보
						-HTML전송 => text/html
						-Cookie전송 => addCookie
					=화면 이동
						-sendRedirect()
		 		= session		=> HttpSession			(★★★★★)
		 		= out			=> JspWriter			(★★)
		 		= application	=> ServletContext		(★★★)
		 		= pageContext	=> PageContext			(★★★★★)
		 		= page			=> Object (this) 
		 		= exception		=> Exception => try~catch
		 		= config		=> ServletConfig => web.xml
		 
		 -------------------------------------------------------------		
			페이지 입출력
				request , response , out
		 -------------------------------------------------------------		
			외부환경 정보
				config
		 -------------------------------------------------------------		
			서블릿 관련
				application , pageContext , session
		 -------------------------------------------------------------		
			예외처리 관련
				exception
		 -------------------------------------------------------------		
		 
		 5. 액션태그
		 6. include
		 7. cookie
		 8. JSTL
		 9. EL
		 10. MVC
		 
		<jsp:setProperty name="vo" property="*"> 
		 

 --%>
 <%	pageContext.include(""); %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style type="text/css">
.container {
	margin-top: 50px;
}

.row {
	margin: 0px auto;
	width: 800px;
}

h1 {
	text-align: center;
}
</style>
</head>
<!-- 170page: getParameter() , getParameterValues() , setCharacterEncoding() -->
<body>	
	<div class="container">
		<h1>개인 정보</h1>
		<div class="row">
		<!-- name을 설정한 데이터가 넘어감 -->
		<form method="post" action="request_ok.jsp">
			<table class="table">
				<tr>
					<th class="text-center" width=20%>이름</th>
					<td width=80%>
						<input type=text name=name size=15 class="input-sm">
					</td>
				</tr>
				<tr>
					<th class="text-center" width=20%>성별</th>
					<td width=80%>
					<!-- 라디오버튼은 반드시 그룹으로 설정: name을 동일하게 만들기 => 단일값을 받음(받는 값은 value) -->
						<input type=radio name=sex value="남자" checked>남자
						<input type=radio name=sex value="여자">여자
					</td>
				</tr>
				<tr>
					<th class="text-center" width=20%>전화번호</th>
					<td width=80%>
					<!-- id와 class는 자바 스크립트 / name이 자바와 연결되는 속성
						getParameter("tel") -->
						<select name="tel" class="input-sm">
							<option>010</option>
							<option>011</option>
							<option>016</option>
							<option>019</option>
							<option>070</option>
						</select>
						<input type="text" name="tel2" size=15 class="input-sm">
					</td>
				</tr>
				<tr>
					<th class="text-center" width=20%>소개</th>
					<td width=80%>
						<textarea rows="8" cols="50" name="content"></textarea>
					</td>
				</tr>
				<tr>
					<th class="text-center" width=20%>취미</th>
					<td width=80%>
						<input type="checkbox" name="hobby" value="운동">운동
						<input type="checkbox" name="hobby" value="등산">등산
						<input type="checkbox" name="hobby" value="영화">영화
						<input type="checkbox" name="hobby" value="음악">음악
						<input type="checkbox" name="hobby" value="게임">게임
						<input type="checkbox" name="hobby" value="낚시">낚시
						<input type="checkbox" name="hobby" value="독서">독서
						<input type="checkbox" name="hobby" value="수렵">수렵
					</td>
				</tr>
				<tr>
					<td colspan="2" class="text-center">
						<button class="btn btn-sm btn-danger">전송</button>
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
</body>
</html>