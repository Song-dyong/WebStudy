<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*, java.util.*"%>
    <jsp:useBean id="dao" class="com.sist.dao.FoodDAO"></jsp:useBean>
<%
    String strPage=request.getParameter("page");
    if(strPage==null)
    	strPage="1";
    int curpage=Integer.parseInt(strPage);
    List<FoodBean> list=dao.foodListData(curpage);
    int totalpage=dao.foodTotalPage();
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
			<%
				for(FoodBean vo:list){
			%>
				<div class="col-md-3">
					<div class="thumbnail">
						<a href="#" target="_blank"> <img
							src="<%=vo.getPoster() %>" style="width: 100%">
							<div class="caption">
								<p><%=vo.getName() %></p>
							</div>
						</a>
					</div>
				</div>
			<%
				}
			%>
		</div>
		<div style="height:10px"></div>
		<div class="row">
			<div class="text-center">
				<a href="food.jsp?page=<%=curpage>1?curpage-1:curpage %>" class="btn btn-sm btn-danger">&lt;</a>
				<%=curpage %> page / <%=totalpage %> pages
				<a href="food.jsp?page=<%=curpage<totalpage?curpage+1:curpage %>" class="btn btn-sm btn-danger">&gt;</a>
			</div>
		</div>
	</div>
</body>
</html>