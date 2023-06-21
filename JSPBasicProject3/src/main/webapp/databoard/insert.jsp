<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Gasoek+One&display=swap" rel="stylesheet">
<style type="text/css">

.container {
	margin-top: 50px;
}

.row {
	margin: 0px auto;
	width: 900px;
}

h1 {
	text-align: center;
	font-family: 'Gasoek One', sans-serif;
}

</style>
<script type="text/javascript">
function board_write(){
	// 유효성 검사
	// form 읽기
	let frm=document.frm;	// 오라클에 not null로 설정된 값만 처리
	if(frm.name.value==""){
		frm.name.focus();
		return;
	}
	if(frm.subject.value==""){
		frm.subject.focus();
		return;
	}
	if(frm.content.value==""){
		frm.content.focus();
		return;
	}
	if(frm.pwd.value==""){
		frm.pwd.focus();
		return;				// return은 수행하지 않은 채 돌려보내는 것
	}
	frm.submit();			// submit 버튼 ==> 글쓰기 버튼의 클래스를 submit으로 설정하지 않아도 submit 가능
}
</script>
</head>
<body>
	<div class="container">
		<h1>글쓰기</h1>
		<div class="row">
		<form method="post" action="insert_ok.jsp" name=frm
			enctype="multipart/form-data"
		>	<!-- 파일 업로드 프로토콜명
				==> POST기반 -->
			<table class="table">
				<tr>
					<th class="text-center danger" width=15%>이름</th>
					<td width=85%>
						<input type=text name=name class="input-sm" size=15>
					</td>
				</tr>
				<tr>
					<th class="text-center danger" width=15%>제목</th>
					<td width=85%>
						<input type=text name=subject class="input-sm" size=50>
					</td>
				</tr>
				<tr>
					<th class="text-center danger" width=15%>내용</th>
					<td width=85%>
						<textarea rows="10" cols="50" name=content></textarea>
					</td>
				</tr>
				<tr>
					<th class="text-center danger" width=15%>첨부파일</th>
					<td width=85%>
						<input type=file name=upload class="input-sm" size=20>
					</td>
				</tr>
				<tr>
					<th class="text-center danger" width=15%>비밀번호</th>
					<td width=85%>
						<input type=password name=pwd class="input-sm" size=15>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="text-center">
						<input type=button value="글쓰기" class="btn btn-sm btn-primary" onclick="board_write()">
						<input type=button value="취소" class="btn btn-sm btn-info"
							onclick="javascript:history.back()">
					</td>				
				</tr>
			</table>
			</form>
		</div>
	</div>
</body>
</html>