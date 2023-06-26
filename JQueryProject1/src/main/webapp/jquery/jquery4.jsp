<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
$(function(){
	// 글자 조작 => text() , html()	=> getter / setter  동시에 가지고 있음 (공백은 getter / 값을 넣어주면 setter)
	// 태그 조작 => append()
	//alert($('h1').text())
	//alert($('h1').html())
	$('#h').text("<span style=color:green>hello jquery~~~</span>")
	$('#h').html("<span style=color:green>hello jquery~~~</span>")
})
</script>
</head>
<body>
	<h1><span style="color:red">Hello Jquery</span>
		<div>Hello Jquery!!!</div>
	</h1>
	<h1 id="h"></h1>
</body>
</html>