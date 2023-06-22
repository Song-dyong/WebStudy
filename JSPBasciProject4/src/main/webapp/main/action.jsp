<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>JSP 액션 태그</h1>
	<table class="table">
		<tr>
			<td>
				<ul>
					<li><sup style="color:gold">★</sup>&lt;jsp:include page=""&gt;: JSP특정 영억에 다른 JSP를 첨부(조립식)
						<br>
						&lt;jsp:include page=""&gt; => JSP를 따로 실행, HTML만 묶어서 사용 (동적)
						<br>
						&lt;%@ include file="" %@&gt; => JSP를 먼저 묶어서, 한번에 컴파일
						<br> 이 경우, 파일이 한번에 묶이기 때문에, 같은 변수를 사용하면 오류 발생
						<br> &nbsp;메뉴, footer 등 변수가 거의 없고, 변경사항이 없는 경우 사용 (정적)
						<br>
					</li>
					
					<li>&lt;jsp:forward page=""&gt;: 파일 덮어쓰기 (URL유지 => request 사용)</li>
					<li>&lt;jsp:param value="값" name="키"&gt;: 해당 JSP로 값을 전송할 때 사용</li>
					<li><sup style="color:gold">★</sup>&lt;jsp:useBean id="" class=""&gt;: 클래스 메모리 할당</li>
					<li><sup style="color:gold">★</sup>&lt;jsp:setProperty name="객체명" property=""&gt;: 빈즈(VO)에 값 채우기</li>
				</ul>
			</td>
		</tr>
	</table>
</body>
</html>