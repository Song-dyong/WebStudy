<%--
		JSP 안에서 조건문
 --%>


<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
	int sum=0;
	int even=0;
	int odd=0;
%>
<%--
	<%! %>: JSP를 통해 함수를 만들거나, 멤버변수를 선언하지 않기 때문에 보통 사용되지 않는다
			메소드와 멤버변수의 선언은 자바에서 코딩
 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		for(int i=1;i<=100;i++)
		{
			if(i%2==0){
				even+=i;
			}
			else{
				odd+=i;
			}
			sum+=i;
		}
	%>
	<h1>1~100까지의 합:<%=sum %></h1>
	<h1>1~100까지의 짝수합:<%=even %></h1>
	<h1>1~100까지의 홀수합:<%=odd %></h1>
</body>
</html>