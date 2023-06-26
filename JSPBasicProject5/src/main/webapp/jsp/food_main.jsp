<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*, com.sist.dao.*"%>

<%--
		293page DBCP => DataBaseConnectionPool
		
		1. 연결 / 해제 반복 
			=> 시간 소모가 많다 (오라클 연결시에 시간이 많이 소모)
			   ------------ 연결시 시간 소모를 절약
			=> 미리 COnnection 객체를 생성해서 저장 후에 사용
			=> 일반적으로 웹 프로그램에서는 일반화
			=> Connection 객체 생성을 제한
			=> 사용 후에 재사용 가능
		2. Connection을 미리 생성하기 때문에, Connection 객체 관리가 쉽다
		3. 쉽게 서버가 다운되지 않는다.
		4. 라이브러리가 만들어져 있다 (commons-dbcp, commons-pool)		==> 매번 getConnection()을 할 필요가 없음 -> localhost이기 때문에 빨라 보임
			-----------------------------------------------						외부에 서버를 둘 경우, 점점 속도가 느려진다
			
			Connection 객체의 모음 (pool)										
		1) 생성 Connection 얻기 												MyBatic / Spring
		2) 사용 Statement													 DBCP		MVC
		3) 반환  				==> 사용 후 반환되므로, 재사용 (Connection 갯수의 한계가 정해져있기 때문에, 서버 다운 x) 
		
		
		280page 
			목적 : 웹 사용자는 응답시간이 짧아야 사용
				 ----------------------------
				 	DB연결을 원활하게 => BackEnd => FrontEnd가 BackEnd의 속도를 따라가지 못함 => Vue, React등 프론트 라이브러리 발달
				 	
			DBCP
			----
			방법)
			  1. Connection 객체 생성 (maxActive: 최대, maxIdle: 풀 기본)
			  2. Pool영역에 저장
			  3. 사용자 요청시 Connection의 주소를 얻어옴
			  4. 사용자 요청에 따라 SQL문장 실행
			  5. 반드시 Pool안에 Connection객체를 반환
			-----------------------------------------------
			  1. server.xml => Connection 객체 생성
			  2. 코딩 방법
			  	---------
			  	 1) getConnection() : 미리 생성된 Connection객체 얻기
			  	 2) disConnection() : 반환 
			  	 3) 기능 : 이전과 동일한 방식 
			  	 ★★★ 보안성 우수 ★★★
		
		
 --%>

<%
// 1. 사용자가 전송한 데이터를 받는다 (page)
String strPage = request.getParameter("page");
// 2. 실행과 동시에 페이지 전송이 불가능 => default 페이지 잡기 (1page)
if (strPage == null)
	strPage = "1";
// 3. 현재 페이지 지정
int curpage = Integer.parseInt(strPage);
// 4. 현재 페이지의 데이터 가져오기 (DAO의 메소드 foodListDate => Oracle에 연결해서 데이터 가져옴)
FoodDAO dao = FoodDAO.newInstance();
List<FoodBean> list = dao.foodListData(curpage);
// 5. 총 페이지 읽기
int totalpage = dao.foodTotalPage();
// 6. 블록별로 나누기 (페이징)
final int BLOCK = 10;
// [1] ~ [10] , [11] ~ [20] , [21] ~ [30]
// 7. 시작위치
int startPage = ((curpage - 1) / BLOCK * BLOCK) + 1;
int endPage = ((curpage - 1) / BLOCK * BLOCK) + BLOCK;
if (endPage > totalpage)
	endPage = totalpage;
// 8. 끝 위치
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
			for (FoodBean vo : list) {
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
		</div>
</body>
</html>