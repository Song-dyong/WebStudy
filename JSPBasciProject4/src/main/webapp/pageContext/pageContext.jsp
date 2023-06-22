<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%--
    	String name=request.getParameter("name");
    	String name2=pageContext.getRequest().getParameter("name2");
    	==> 값을 얻을 수는 있지만, 소스가 길어지기 때문에 굳이 사용하지 않는다.
    	
    		pageContext = PageContext
    			=> 내장 객체 얻기 (사용빈도 거의 없음)
    			=> 웹 흐름
    				include() => <jsp:include>		==> 태그형식으로 변경되었기때문에, pageContext 내장객체는 거의 사용되지 않는다
    					=> JSP 여러 개를 조립시 사용
    				forward() => <jsp:forward>
    					=> 화면 이동시에 사용
    				------------------------------ request값을 공유
    			
    	
    --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>