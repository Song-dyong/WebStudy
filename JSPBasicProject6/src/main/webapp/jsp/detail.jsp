<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*, java.util.*"%>
<%--
			JSP에서 맨 위에 되어있는 코딩은 jsp파일을 실행했을 때, 자동으로 아래의 코딩으로 변환된다
					나머지 코딩을 _jspservice() 메소드에 작성
		response => HTML / XML 전송 역할
					response.setContextType("text/html; charset=UTF-8")
					PrintWriter out = response.getWriter()
					=> Servlet에서는 직접 코딩
 --%>
<%
	// 1. 사용자가 보내준 값 받기
	String fno=request.getParameter("fno");
	FoodDAO dao=FoodDAO.newInstance();
	FoodBean vo=dao.foodDetailData(Integer.parseInt(fno));
%>
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
</head>
<body>
	<div class="container">
		<div class="row">
			<%-- 이미지 5개 출력 위치 --%>
			<table class="table">
				<tr>
					<%
						StringTokenizer st=new StringTokenizer(vo.getPoster(),"^");
						while(st.hasMoreTokens()){
					%>
						<td>
							<img src="<%=st.nextToken() %>" style="width:100%">
						</td>
					<%		
						}
					%>
				</tr>
			</table>
		</div>
		<div style="height:20px"></div>
		<div class="row">
			<div class="col-sm-8">
				<%-- 상세보기 위치 --%>
				<table class="table">
					<tr>
						<td colspan="2">
							<h3><%=vo.getName() %>&nbsp;<span style="color:orange"><%=vo.getScore() %></span></h3>
						</td>
					</tr>
					<tr>
						<th width=15%>주소</th>
						<td width=85%><%=vo.getAddress() %></td>
					</tr>
					<tr>
						<th width=15%>전화</th>
						<td width=85%><%=vo.getTel() %></td>
					</tr>
					<tr>
						<th width=15%>음식종류</th>
						<td width=85%><%=vo.getType() %></td>
					</tr>
					<tr>
						<th width=15%>가격대</th>
						<td width=85%><%=vo.getPrice() %></td>
					</tr>
					<tr>
						<th width=15%>주차</th>
						<td width=85%><%=vo.getParking() %></td>
					</tr>
					<tr>
						<th width=15%>영업시간</th>
						<td width=85%><%=vo.getTime() %></td>
					</tr>
					<tr>
						<th width=15%>메뉴</th>
						<td width=85%><%=vo.getMenu() %></td>
					</tr>
					<tr>
						<td class="text-right" colspan="2">
							<a href="main.jsp" class="btn btn-xs btn-danger">목록</a>
						</td>
					</tr>
				</table>
			</div>
			<div class="col-sm-4">
				<%-- 지도 출력 위치 --%>
			</div>
		</div>
	</div> 
</body>
</html>