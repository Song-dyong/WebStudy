<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,com.sist.dao.*"%>

<%--
		_jspService()	=> 브라우저에 실행하는 메소드 => service
		{
			============
				JSP
			============
		}
 --%>
	
<%
	// 자바영역
	// DAO 생성
	MemberDAO dao=MemberDAO.newInstance();
	request.setCharacterEncoding("UTF-8");
	String dong=request.getParameter("dong");
	// jsp는 시작하자마자 java 영역을 전부 실행한 뒤 html이 실행되므로, 초기값을 null로 잡아야 모든 데이터를 읽지 않는다.
	// request할 데이터가 없으면 전체를 읽는다??
	int count=0;
	List<ZipcodeVO> list=null;
	if(dong!=null)
	{
		count=dao.postfindCount(dong);
		list=dao.postfind(dong);
	}
	
	// Model1(JSP) => MV 구조 => MVC 구조
			
	
%>

<%--  
	
	실행시 => JSP => servlet 클래스로 변경
				|
			   톰캣
	
		post.jsp => post_jsp.java
					-------------
						  |
						compile
						  |
					post_jsp.java	===> 일련의 과정을 톰캣이 담당
						  |				변환을 통해 다른 프로그램과 연결 => WAS (Web Application Server)
						인터프리터									  -----
						  |										   톰캣
					메모리에 html만 출력
					--------------- 버퍼 => 브라우저에서 읽어서 출력	
		
		1) 구성요소
		=> AWS : 자바 / Tomcat => 프로젝트 파일을 묶어서 올려준다 (WAR file)
			server : WebServer 		=> 아파치 / IIS 
						| WAS(미들웨어) => 톰캣 (연습용 웹서버 내장-아파치)
			client : 브라우저
	
	JSP => Java Server Page => 서버에서 실행하는 자바파일
	--- 스크립트 형식
	1) 지시자
		= page
			<%@ page 속성=값 ... %> : JSP 파일 정보
			속성
			  - contentType : 브라우저와 연결 => 파일형태를 알려주는 역할
			  		text/html 	=> html
			  		text/xml	=> xml 
			  		text/plain	=> json
			  		------------------- default: 영어 => charset=UTF-8
		= import: 다른 라이브러리 로딩시 사용
			<%@ page import="java.util.*, java.io.*,..."%>
			<%@ page import="java.util.*"%>
			<%@ page import="java.io.*"%>
			<%@ page import="java.sql.*"%>
			한 번에 쓰거나, 나눠서 쓸 수 있음
		= errorPage: error 발생시 이동하는 파일을 지정
			<%@page errorPage="error.jsp"%>
			= 파일의 확장자는 변경 가능 (.do, .nhn...)
		= include 지시자: JSP안에 특정위치 다른 JSP를 포함
			<%@ include file="a.jsp"%>
		= taglib 지시자: 자바의 제어문, 변수선언, String
						=> 태그로 만들어서 제공
				<%
					for(int i=0;i<10;i++){
				%>
					<ul>
					</ul>
				<%		
					}
				%>
				
				==> Spring에서 JSP사용시 (MVC구조)
				<c:forEach var="i" begin="0" end="10" step="1">
					<ul>
					</ul>
				</c:forEach>
				
	1-1) 스트립트릿
			자바언어 분리
			1| 선언식: 	<%! 메소드 선언 , 전역변수 %>				==> 사용빈도 少
			2| 표현식: 	<%= 화면 출력내용 %> => out.println()
			3| 스크립트릿: 	<% 일반 자바: 지역변수, 메소드호출, 객체 생성, 제어문 %>
	2) 내장객체
		자바에서 지원
		***= request : 사용자가 보내준 데이터 관리
		***= response : 응답 (html, cookie)
		***= session : 서버에 사용자 정보 일부를 저장, 장바구니
		***= application : 서버 관리 => getRealPath() -- 톰캣이 실제로 읽어가는 파일 위치
		(아래는 형식이 바뀌기 때문에, 자주 사용하지 않음)
		= out <%= %>
		= pageContext => 페이지 흐름
				<jsp:include> <jsp:forward> -> jsp action태그로 변경
		= exception (try~catch)
		= page (this)
		= config (web, xml)
	3) JSP 액션 태그
		<jsp:useBean> : 클래스 객체 생성
		<jsp:setProperty> : set메소드에 값을 채운다
		<jsp:getProperty> : get메소드 호출
		<jsp:param> : 데이터 전송
		<jsp:include> : 특정 위치에 다른 JSP를 추가
		<jsp:forward> : 화면 이동시 사용 
						------
						=sendRedirect(): request를 초기화
						=forward(): request를 유지한 채
	4) JSTL / EL
	   ---- 태그로 자바 라이브러리를 만들어줌 Java Standard Tag Library
	   Expression Language(화면출력) => ${}   <%= %>를 대체함
	5) MVC 구조 => 자바와 HTML을 분리해서 코딩
		Model View Controller
		----- ---- ----------	
		자바	  HTML	Servlet
		Back/Front

--%>
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
	width: 450px;
}

h1 {
	text-align: center;
}
td,th{
	font-size: 9px;
}
a{
	text-decoration: none;
	color:black
}
a:hover {
	text-decoration: underline;
	color: cyan;
}
</style>
<script type="text/javascript">
let ok=(zip,addr)=>{
   // opener => js7.html (팝업창을 열어주는 파일 오픈)
   // 000-000
   opener.frm.post1.value=zip.substring(0,3);
   opener.frm.post2.value=zip.substring(4,7);
   opener.frm.addr.value=addr;
   self.close(); // 자신 => post.jsp
}
</script>
</head>
<body>
   <div class="container">
      <h1>우편번호 검색</h1>
      <div class="row">
       <form method=post action="post.jsp">
         <table class="table">
            <tr>
               <td>
                  우편번호 : <input type="text" name=dong size=15
                           class="input-sm">
                         <input type=submit value="검색" 
                            class="btn btn-sm btn-danger">
               </td>
            </tr>
         </table>
        </form>
        <div style="height: 20px"></div>
        <%
           if(list!=null) // 창을 띄워놓고 입력해야함 // 입력값 없는상태 => null
           {
        %>
              <table class="table">
                 <tr>
                    <th width=20% class="text-center">우편번호</th>
                    <th width=80% class="text-center">주소</th>
                 </tr>
                 <%
                    if(count==0 || dong=="")
                    {
                 %>
                       <tr>
                          <td colspan="2" class="text-center">
                             <h3>검색된 결과가 없습니다</h3>
                          </td>
                       </tr>
                 <%       
                    }
                    else
                    {
                       for(ZipcodeVO vo:list)
                       {
                 %>
                          <tr>
                             <td width="20%" class="text-center">
                                <%=vo.getZipcode() %>
                             </td>
                             <td width="80%">
                                <a href="javascript:ok('<%=vo.getZipcode() %>','<%=vo.getAddress() %>')"><%=vo.getAddress() %></a>
                             </td>
                          </tr>
                 <%
                       }
                    }
                 %>
              </table>
        <%      
           }
        %>
      </div>
   </div>
</body>
</html>