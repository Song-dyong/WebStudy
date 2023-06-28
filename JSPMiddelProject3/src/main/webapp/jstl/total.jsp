<%@page import="java.util.StringTokenizer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%--
		EL => <%= %> 대체 => 가급적이면 자바코딩 제거
		-- 표현식 (화면출력) => System.out.println()
		
		형식)
			${표현식 } => View(태그형으로 프로그램 제작)
			
		표현식)
			연산자
			   산술 연산: + , - , * , /(div) , %(mod)
			   			+는 순수하게 산술연산만 가능 (문자열 결합 x => +=을 통해 문자열 결합)
			   			null을 연산처리할 경우, 0으로 취급
			   			"1"과 같은 숫자로 이루어진 문자열은 자동 정수형변환 
			   			정수 / 정수 => 실수
			   비교 연산: ==(eq) , !=(ne) , <(lt) , >(gt) , <=(le) , >=(ge)
			   			${10 eq 10} => ${10 == 10}
			   			날짜, 문자열 포함
			   논리 연산: &&(and) , ||(or)
			   			&& => 예약 가능 날짜
			   			|| => 예약 불가능 날짜
			   삼항연산자: 페이지, select, radio ...
			   			${sex == "남자' ? "checked" : ""}
			   			${조건 ? "값"(true) : "값"(false)}
			   			
			내장객체 (일반 JSP ==> 자바 / HTML 분리)
					-------		-------------- => 모든 처리는 자바 -> 결과값만 출력
					   |
					자바/HTML 혼합
				=requestScope => request.getAttribute("key")
							기존의 request + 추가 데이터 설정 
							 => request.setAttribute("key","value") : 오라클에서 받은 값 추가
							 => request.getAttribute("key")
							 	   |
							 	${requestScope.key} => ${key }
							 	
				=sessionScope
					session에 저장된 값 읽기
					${sessionScope.key} => ${key } => key값이 동일할 경우, request가 우선순위 => sessionScope 사용 권장
					
				=param: ${param.key} => request.getParameter("key")
				=paramValues : ${paramValues.key} => request.getParameterValues()
				
					${ 자바의 일반 변수 사용 불가능 }
					ex) String name="hong";
							${name} => 출력 X
						request.setAttribute("name", "hong")
							${name} => 출력 O
							 ------ key 값
					자바 변수에 담긴 값을 바로 사용 x => Model에서 request.setAttribute()를 통해 key에 변수값을 Mapping해서 사용
					==> $를 통해 출력하는 값은 자바 변수값이 아닌, key에 해당하는 value값
			
			제어 : JSTL
			----------
			1)core : 제어문 , URL관련 , 변수 설정 (request.setAttribute())
				   	  -----
				   	조건문
				   	-----
				   		<c:if test="조건식">				=>	자바:	if( 조건식 )
				   			true일 때 처리 문장						{
				   		</c:if>											처리문장
				   		=> 다중 조건문 , if~else 없음					}
				   	선택문
				   	-----
				   		<c:choose>
				   			<c:when test=""> 처리 문장 </c:when>
				   			<c:when test=""> 처리 문장 </c:when>
				   			<c:when test=""> 처리 문장 </c:when>
				   			<c:when test=""> 처리 문장 </c:when>
				   			<c:otherwise> DEFALUT 문장</c:otherwise>
				   		</c:choose>
				   		=> switch~case / 다중 if
				   	반복문		
				   	-----
				   		<c:forEach> => for문
				   			for(int i=0; i<=10; i++)
				   		=> <c:forEach var="i" begin="0" end="10" step="1">
				   			-단점
				   				for(int i=10; i>=0; i--)
				   				------------------------ <c:forEach>로 구현 불가능 
				   										=> i++만 사용 가능
				   										=> 데이터를 가져올 때부터 Sort해야함	
				   		<c:forEach> => 향상된 for문
				   			for(BoardVO vo:list)
				   		=> <c:forEach var="vo" items="list"> ★★★★★
				   		
				   		<c:forTokens> =>StringTokenizer
				
				URL관련: 화면 이동
				---------------
				  response.sendRedirect(url)
				  	=> <c:redirect url="url">
				  request.setAttribute("a", 값)
				  	=> <c:set var="a" value="값">
				  out.println()
				  	=> <c:out value="">
				  	   --------------- 자바 스크립트에서 자바 데이터를 받아서 출력할 경우 사용
			
			2)fmt: 변환 (날짜변환 ,숫자변환)
						-----	------ DecimalFormat: <fmt:formatNumber value="" pattern="999,999">
						SimpleDateFormat: <fmt:formatDate value="" pattern="yyyy-MM-dd">
						
			3)fn: String 메소드 처리
					${fn:length(문자열)}
					${fn:substring(문자열, start, end)}
					
			4)sql: DAO
			5)xml: OXM
				=> 자바에서 처리 (사용빈도가 거의 없음)
				
		import 방법
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	  
		<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
		
		prefix => 사용자가 지정
		
 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	// 데이터 설정
	List<String> names=new ArrayList<String>();
	names.add("홍길동");
	names.add("심청이");
	names.add("춘향이");
	names.add("박문수");
	names.add("이순신");
	// EL 사용 불가능 => EL ${} => request, session에 저장해야만 사용 가능
	request.setAttribute("names", names);
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>자바를 이용한 for문</h1>
	<h3>이름 목록</h3>
	<ul>
	<%
		for(String name: names){
	%>
			<li><%=name %></li>
	<%	
		}
	%>
	</ul>
	<h1>JSTL을 이용한 for문</h1>
	<h3>이름 목록</h3>
	<ul>
		<c:forEach var="name" items="${names }">
			<li>${name }</li>
		</c:forEach>
	</ul>
	<h1>자바 (StringTokenizer)</h1>
	<ul>
	<%
		String color="red,blue,green,yellow,black";
		StringTokenizer st=new StringTokenizer(color,",");
		while(st.hasMoreTokens()){
	%>
		<li><%=st.nextToken() %></li>
	<%
		}
	%>
	</ul>
	<h1>JSTL (forTokens)</h1>
	<ul>
	<!-- 
		var="color" 							=> st.nextToken()
		items="red,blue,green,yellow,black" 	=> 문자열
		delims=","								=> 구분자
	 -->
		<c:forTokens var="color" items="red,blue,green,yellow,black" delims=",">
			<li>${color }</li>
		</c:forTokens>
	</ul>
</body>
</html>