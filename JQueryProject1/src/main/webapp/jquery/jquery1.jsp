<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%--
		JQuery : 자바스크립트 라이브러리 
		=> 라이브러리 다운로드 / 원격 읽기
		=> 자바스크립트
			시작과 동시에 처리 => window.onload=function(){---}
		=> JQuery
			$(document).ready(function(){---})
			  ---------------- 생략 가능 
			$(function(){---})
			$() => 태그(selector 이용) => querySelector()
			-------------------------------------------
								|
					DOM => 태그를 제어하는 프로그램
			$(내장 객체) => window, document, location, history ... 
			$() => function
		=> Vue => mounted() , React => componentDidMount()
		
		Jquery (HTTP책 370page)
		------
		1) 라이브러리 로드
			=> <script type="text/javascript" src="http://code.jquery.com/jquery-1.3.js"></script>
			=> include => 파일을 여러개 묶어서 사용 (jquery 충돌시 작동하지 않는다)
			   ------- main.jsp (자바스크립트를 모아둔다)
		2) 문서객체 선택 (selector)
		  ------- 태그
		  $('태그명') => 여러 개일 경우, 배열로 잡힘
		  	ex)
		  		<h1>1</h1>
		  		<h1>2</h1>
		  		<h1>3</h1>
		  		<h1>4</h1>
		  		<h1>5</h1>
		  	let h1=$('h1')	==> let h1=[] ==> 선택된 h1 태그를 읽기 $(this)
		  	=> 모든 태그에 이벤트 가능
		  	
		 $('#아이디명') => id 속성 사용
		 $('.클래스명') => class 속성 사용
		   -------- CSS Selector와 같음
		   			$('p + h1') , $('p ~ h1'), $('p > h1'), $('p h1'), $('a[href*="a"]') 
 --%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
$(function(){
	// 모든 처리가 포함 => 값 읽기 , 이벤트 처리 , css적용 ...
	// 1. CSS 적용
	$('h1').css("color", "red")
	$('h1').css("backgroundColor", "green")		// - 사용 x, 대문자로 구분
/* 
	<style>
		h1 {
			color:"red"
		}
	</style>
*/
})
</script>
</head>
<body>
	<h1>Hello Jquery</h1>
	<h1>Hello Jquery</h1>
	<h1>Hello Jquery</h1>
	<h1>Hello Jquery</h1>
	<h1>Hello Jquery</h1>
</body>
</html>