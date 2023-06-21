<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.temp.*"%>
    
<%
	request.setCharacterEncoding("UTF-8");
	
%>
<jsp:useBean id="bean" class="com.sist.temp.SawonBean">
	<jsp:setProperty name="bean" property="*"/>
</jsp:useBean>
<%--
		jsp:useBean id="bean" class="com.sist.temp.SawonBean"
		SawonBean bean=new SawonBean()
		jsp:setProperty name="bean" property="*"
		
		public void aaa(SawonBean bean)
		----------------------------------------------------------
		218page 빈을 사용한 JSP 파일 작성
		-----------------------------
		 		JSP 액션 태그					
		화면 이동: <jsp:forward> 
		화면 연결: <jsp:include> 	★★★
		값 전송: 	 <jsp:param> 	★★★
		객체 생성: <jsp:useBean>
		멤버변수에 값 주입: <jsp:setProperty>
		멤버변수의 값 읽기: <jsp:getProperty>
		-----------------------------
		 <jsp:useBean> : 객체 메모리 할당 => 객체 생성
		 	속성
		 	 id: 객체명
		 	 class: 클래스명
		 	 scope: 사용범위
		 	 	=page (default) => 1개의 JSP에서만 사용 (다른 페이지로 이동시, 자동으로 메모리 해제) ★★★ (생략가능)
		 	 	=request => 사용자의 요청이 있는 경우 사용
		 	 	=session => 프로그램이 유지하고 있는 동안 (접속 ~ 종료)
		 	 	=application => 프로그램 종료시까지 유지 
		  <jsp:useBean id="a" class="A">
		  					  -------- 패키지명.클래스명 => Class.forName()
		  	=> 자바 변경
		  		A a = new A();
		-----------------------------
		 <jsp:setProperty> : 변수의 값 설정 (메모리에 쓰기)
		 	=> setter
		 	name: 객체명
		 	property: 변수
		 	value: 값 설정
		  class A{
		  	private int no;
		  	private String name;
		  	
		  	setNo(), getNo(), setName(), getName()
		  }
		   1) 메모리 할당
		   <jsp:useBean id="a" class="A">
		   		=초기화
		   		<jsp:setProperty name="a" property="name" value="홍길동">
		   		A a=new A();
		   		- -
		   	class id
		   		a.setName("홍길동")
		   		<jsp:setProperty name="a" property="no" value="10">
		   		a.setNo(10)
		   		<jsp:setProperty name="a" property="*"/>
		   		a.setName(request.getParameter("name"))	
		   		a.setNo(Integer.parseInt(request.getParameter("no")))
		   		==> Bean(VO)에서 설정한 변수명과 name값이 동일해야 값을 받을 수 있음!	
		   </jsp:useBean>		 -------- id의 값과 동일해야함
		-----------------------------  
		 <jsp:getProperty> : 변수의 값 읽기 (메모리 값 읽기)
		 	=> getter
		   <jsp:getProperty name="a" property="name"> ==> a.getName()	
		   	getProperty는 "*"을 사용할 수 없음
		   <jsp:getProperty name="a" property="no"> ==> a.getNo()
		-----------------------------
		useBean => 메모리 할당
		setProperty => setXxx() 메소드 호출
		getProperty => getXxx() 메소드 호출
		===========> 태그 자체가 라이브러리이므로, 자바 코드로 변경됨
		
		보통 VO에 값을 대입할 경우, 변수를 선언해서 그 변수에 사용자가 보낸 값을 대입한 뒤, 그 변수를 VO에 넣어주는 것보다
		setProperty 사용하는 것이 편리하므로 사용
		반면, getProperty를 길게 쓰는 것보다, 선언된 VO의 get메소드를 사용하는 것이 편하므로 사용하지 않는다.
		
	// 기존의 방식										==> 	<jsp:useBean id="bean" class="com.sist.temp.SawonBean">
	request.setCharacterEncoding("UTF-8");						<jsp:setProperty name="bean" property="*"/>
	String name=request.getParameter("name");				</jsp:useBean>
	String sex=request.getParameter("sex");
	String dept=request.getParameter("dept");
	String job=request.getParameter("job");
	String pay=request.getParameter("pay");
	// VO에 값 채우기
	SawonBean bean=new SawonBean();
	bean.setName(name);
	bean.setSex(sex);
	bean.setDept(dept);
	bean.setJob(job);
	bean.setPay(Integer.parseInt(sex));
	
	
		
 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	이름: <%=bean.getName() %><br>
	이름: <jsp:getProperty name="bean" property="name"/><br>
	성별: <%=bean.getSex() %><br>
	부서: <%=bean.getDept() %><br>
	직위: <%=bean.getJob() %><br>
	급여: <%=bean.getPay() %>
</body>
</html>