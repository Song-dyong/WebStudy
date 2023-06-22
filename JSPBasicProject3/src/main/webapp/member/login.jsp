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
	width: 450px;
}

h1 {
	text-align: center;
	font-family: 'Gasoek One', sans-serif;
}

</style>
<!-- jquery를 import한 뒤 (src), 아래에 script를 다시 열고 작업 -->
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
/* 
	(자바스크립트)	let id=document.querySelector('#id')
					   ----------------------
					   값을 읽는 경우
					   	 자바스크립트		 		JQUERY
					   	id.value		==> $('#id').val()
					   	--------------
					   	id.textContent	==> $('#id').text()
					   	--------------
					   	id.innerHTML	==> $('#id').html()
					   						$('#id').attr() 	(속성값 읽을 때)
	(JQUERY) 	$('#id')
	
*/
/* 
$(document).ready(function(){
	
}); 
	==> main을 이렇게 작성하기도 함		
*/
$(function(){ /* window.onload=function(){}와 같은 역할 (자바스크립트 라이브러리) */
	/*$('#logBtn').click(function(){
		// logBtn 태그를 클릭했을 때 기능 처리
		alert("Hello JQuery!!")
	})*/
	$('#logBtn').on('click',function(){
		//alert("Hello JQuery!!")
		let id=$('#id').val();		//	=> let id=document.querySelector('#id').value
		if(id.trim()==""){
			$('#id').focus();
			return;
		}
		let pwd=$('#pwd').val();	
		if(pwd.trim()==""){
			$('#pwd').focus();
			return;
		}
		//$('#frm').submit();			// login_ok.jsp로 전송
		$.ajax({
			type:'post',
			url:'login_ok.jsp',
			data:{"id":id,"pwd":pwd},
			success:function(result){
				alert(result);
				let res=result.trim();
				if(res=='NoID'){
					$('#id').val("");
					$('#pwd').val("");
					$('#id').focus();
					$('#print').text("아이디가 존재하지 않습니다.");
				}else if(res=='NoPWD'){
					$('#pwd').val("");
					$('#pwd').focus();
					$('#print').text("비밀번호가 틀립니다.");
				}else if(res=='OK'){
					location.href="../databoard/list.jsp";
				}else{
					$('#print').text("ㅋㅋㅋㅋㅋㅋ");
				}
				
			}
		})
	})
})
</script>
</head>
<body>
	<div class="container">
		<h1>Login</h1>
		<div class="row">
		<form method="post" action="login_ok.jsp" id="frm">
			<table class="table">
				<tr>
					<td width=20%>ID</td>
					<td width=80%>
						<input type="text" name="id" size=15 class="input-sm" id=id>
					</td>
				</tr>
				<tr>
					<td width=20%>Password</td>
					<td width=80%>
						<input type="text" name="pwd" size=15 class="input-sm" id=pwd>
					</td>
				</tr>
				<tr>
					<td colspan=2 class="text-center">
						<span id="print" style="color:red"></span>
					</td>				
				</tr>
				<tr>
					<td colspan=2 class="text-center">
						<input type=button class="btn btn-sm btn-danger" value="로그인" id="logBtn">
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
</body>
</html>