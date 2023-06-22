<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>config, exception, page</h1>
	<table class="table">
		<tr>
			<th width=20% class="text-center">클래스명</th>
			<td width=80%>ServletConfig, Exception, Object</td>
		</tr>
		<tr>
			<th width=20% class="text-center">주요기능</th>
			<td width=80%>
				<ul>
					<li>config: 서블릿에서 주로 사용 <br>(web.xml의 데이터를 읽을 수 있음) => MVC 등록된 파일 읽기</li>
					<li>exception: 예외처리 관리 => try~catch로 사용하므로, 빈도 下 / 사용시에는 page 지시자에서 isErrorPage 등록</li>
					<li>page: this(자신의 객체를 의미 => return이 Object)</li>
				</ul>
			</td>
		</tr>
	</table>
</body>
</html>