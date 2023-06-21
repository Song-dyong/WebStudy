<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,com.sist.dao.*"%>
<%
// JSP => 데이터 수집 (자바를 통해서 오라클의 데이터를 읽어옴)
/* 
		 JSP 순서
		1. 사용자가 보내준 데이터 받기
		2. 받은 데이터를 이용해서 데이터베이스와 연결
		3. 오라클에서 데이터 읽기
		4. HTML을 이용해서 화면에 출력
	cf) 회원가입과 같이 출력할 데이터가 필요없다면, 3번까지 생략
		
		순서			Model	View
		요청(JSP) ==> 자바 ==> JSP
	  |
	 오라클

*/
FoodDAO dao = FoodDAO.newInstance();
List<FoodVO> list = dao.foodListData();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
.container {
	margin-top: 50px;
}

.row {
	margin: 0px auto;
	width: 960px;
}

h1 {
	text-align: center;
}
</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<%
			for (FoodVO vo : list) {
			%>
			<div class="row">
				<div class="col-md-3">
					<div class="thumbnail">
						<a href="food_detail.jsp?fno=<%=vo.getFno()%>"> 
						<img src="<%=vo.getPoster()%>" class="img-rounded" style="width: 100%">
							<div class="caption">
								<p><%=vo.getName()%></p>
							</div>
						</a>
					</div>
				</div>
				<%
				}
				%>
			</div>
		</div>
	</div>
</body>
</html>