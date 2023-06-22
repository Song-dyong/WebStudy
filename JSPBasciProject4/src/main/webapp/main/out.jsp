<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>JspWriter(out) - 175page</h1>
	<table class="table">
		<tr>
			<th width=20% class="text-center">클래스명</th>
			<td width=80%>HttpServeltResponse</td>
		</tr>
		<tr>
			<th width=20% class="text-center">주요기능</th>
			<td width=80%>
				<ul>
					<li>화면 출력기능</li>
					<li>버퍼에 있는 데이터 지우는 기능</li>
					<li>출력버퍼에 대한 정보</li>
				</ul>
			</td>
		</tr>
		<tr>
			<th width=20% class="text-center">주요메소드</th>
			<td width=80%>
				<ul>
					<li>
					화면출력기능
						<ul>
							<li><sup style="color:gold">★</sup>print()<sup style="color:gold">★</sup>: &lt;%= %&gt;</li>
							<li>write(): &lt;%= %&gt;</li>
						</ul>
					</li>
					<li>
					버퍼 지우기 기능
						<ul>
							<li>clear()</li>
							<li>flush(): page지시자 안에 등록 (autoFlush="true")</li>
						</ul>
					</li>
					<li>
					출력 버퍼에 대한 정보
						<ul>
							<li>getBufferSize(): 총 버퍼</li>
							<li>getRemaining(): 남아있는 버퍼</li>
						</ul>
					</li>
				</ul>
			</td>
		</tr>
	</table>
</body>
</html>