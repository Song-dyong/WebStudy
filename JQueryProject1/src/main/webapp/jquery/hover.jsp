<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
.container {
	margin-top: 50px;
}

.row {
	margin: 0px auto;
	width: 960px;
}
</style>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
$(function(){
	for(let i=2; i<=7; i++){
		$('body').append('<img src=cgv'+i+'.jpg width=120 height=180 style="margin-left:5px">')
	}
	$('img').css('cursor','pointer')
	// $('img') => [] (배열)
	$('img').hover(function(){
		$(this).css('opacity',.3) // mouse-over
	},function(){
		$(this).css('opacity',1) // mouse-out
	})
})
</script>
</head>
<body>

</body>
</html>