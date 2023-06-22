<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>HttpServletResponse(response)</h1>
	<table class="table">
		<tr>
			<th width=20% class="text-center">클래스명</th>
			<td width=80%>HttpServeltResponse</td>
		</tr>
		<tr>
			<th width=20% class="text-center">주요기능</th>
			<td width=80%>
				<ul>
					<li>응답정보 (브라우저로 전송:HTML/Cookie)</li>
					<li>헤더정보 (한글, 파일명, 크기)</li>
					<li>화면이동정보 (Redirect)</li>
				</ul>
			</td>
		</tr>
		<tr>
			<th width=20% class="text-center">주요메소드</th>
			<td width=80%>
				<ul>
					<li>
					응답정보
						<ul>
							<li>setContentType(): HTML, XML, JSON에 대한 정보를 알려준다.</li>
							<li><sup style="color:gold">★</sup>addCookie(): 쿠키 해당 사용자의 브라우저로 전송시 사용</li>
						</ul>
					</li>
					<li>
					헤더정보
						<ul>
							<li>setHeader(): 다운로드, AJax</li>
						</ul>
					</li>
					<li>
					추가정보
						<ul>
							<li><sup style="color:gold">★</sup>sendRedirect(): GET방식</li>
						</ul>
					</li>
				</ul>
			</td>
		</tr>
	</table>
</body>
</html>