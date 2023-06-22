<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%!
	String msg="Hello JSP(멤버변수)";	// 멤버변수
	public String display(){	// 메소드
		return msg;
	}
	/* 
		public class page_jsp extends HttpServlet
		{
			String msg="Hello JSP!!";	
			public String display()
			{	
				return msg;
			}
			public void _jspService()
			{
				String msg="";	
				------------------------
					this.msg	편의상, 같은 클래스 내에서 this 생략 가능하므로 안썼음
									단! super가 나오면, 반드시 this 사용
										지역변수와 변수명이 충돌할 경우 사용
				------------------------
			}
		}
	*/
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String msg="Hello JSP(지역변수)";
		//page = Object
	%>
	<%=msg %>	<br>
	<%=this.msg %>	<br>
</body>
</html>