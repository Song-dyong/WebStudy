<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.sist.dao.*"%>
<%--
		301Page : Session VS Cookie
		---------------------------
		
				Session / Cookie의 차이점 
		
		Session
			1) 클래스명: HttpSession (내장객체)
					   생성 => HttpSession session = request.getSession()  (Interface) 
					   
			2) 저장값 : 자바에서 사용되는 모든 클래스 저장 (Object)
			
			3) 저장위치 : 서버 안에 저장 => 구분자는 sessionId => getId()를 통해 값 얻을 수 있음 (WebSocket에서 사용), (보안 우수)
			
			4) 사용처 : 로그인 처리 (사용자 일부 정보) / 장바구니
						
			
		Cookie
			1) 클래스명: 필요시마다 생성
					   생성 => Cookie cookie = new Cookie()  (일반 클래스)
					   
			2) 저장값 : 문자열만 저장 가능
			
			3) 저장위치 : 클라이언트 브라우저에 저장 (보안 취약)
			
			4) 사용처 : 최근 방문 목록 
			
		----------------------------------------------------------------------------------
		
		Cookie
			1) Cookie 생성
				Cookie cookie = new Cookie(String key, String value)
				
			2) 저장 기간 설정
				cookie.setMaxAge(초 단위) => 0은 삭제
			
			3) 저장 path 지정
				cookie.setPath("/")
			
			4) 저장된 쿠키를 클라이언트 브라우저로 전송
				response.addCookie(cookie)
			
			5) 쿠키 읽기
				Cookie[] cookies=request.getCookie()
								-------------------- 저장된 쿠키 전체 읽기
			6) Key읽기
				getName()
			
			7) Value읽기
				getValue()
			-----------------
			*** 쿠키 / 세션은 상태를 지속적으로 유지하기위해 사용
						   ---- 값 변경 (react / vue => state)
						   => 데이터를 관리하기위한 프로그램 
		
		
		Session (HttpSession)
		---------------------- 내장 객체
		서블릿 : request.getSession()
		JSP : 내장 객체 (session)
		---------------------- 서버에 저장
		=> 전역변수 (모든 JSP에서 사용 가능)
		=> 서버에 저장 (보안 우수)
		----------------------
			1) 세션 저장	★★★
				session.setAttribute(String key, Object obj)
				
			2) 세션 저장 기간
				session.setMaxInactiveInterval() => 디폴트 30분 (1800)
				
			3) 세션 읽기  ★★★
				session.getAttribute(String key) => return형은 Object (형변환 주의)
			
			4) 세션 일부 삭제  ★★★
				session.removeAttribute(String key)
			
			5) 세션 전체 삭제  ★★★
				session.invalidate()
				
			6) 생성여부 확인
				session.isNew()
			
			7) 세션에 등록된 sessionID (각 클라이언트마다 1개만 생성) => 모든 정보 저장
				session.getId()
			------------------------------------------------------------------
			저장 방식: Map (키, 값) => value는 중복 가능 / key는 중복 불가능 (key가 중복될 시, 덮어씀)
						Web에서 사용하는 클래스는 대부분 Map방식을 사용
							ex) request , response , session , application ... 
								=> request.getParameter(String key)
								
				cf) 세션은 서버 종료, 브라우저 닫기, 로그아웃시 사라짐
				    쿠키는 파일로 저장되기 때문에, 장바구니처럼 로그아웃한 뒤에도 유지해야할 경우, 사용할수도 있음
			
 --%>

<%
	// 출력할 데이터 읽기 (Oracle)
	// 1. 사용자 요청
	// 2. DAO 연결 => 데이터 읽기
	// ----------------------- Java (Model)
	// 3. 읽은 데이터 출력 =>  HTML
	// ----------------------- HTML (View) 	Java와 HTML 나눠서 작업 => MV 구조	(Controller -> servlet에서 작업)
	//										MVC 구조는 JSP와 달리 파일이 기본적으로 2개 (Java와 HTML이 나뉘기 때문에) => 작업 효율 증가
	
	String strPage=request.getParameter("page");
	if(strPage==null)
		strPage="1";
	int curpage=Integer.parseInt(strPage);
	FoodDAO dao=FoodDAO.newInstance();
	List<FoodBean> list=dao.foodListData(curpage);
	int totalpage=dao.foodTotalPage();
	
	// 쿠키 읽기
	Cookie[] cookies=request.getCookies();
	List<FoodBean> cList=new ArrayList<FoodBean>();
	if(cookies!=null){	// 쿠키가 존재하면 ,, 
		for(int i=cookies.length-1;i>=0;i--){	// 최신순 읽기
			if(cookies[i].getName().startsWith("food_")){		// 쿠키에 food이외의 값들도 저장되어 있기 때문에 key값의 이름을 통해 찾기
				String fno=cookies[i].getValue();
				FoodBean vo=dao.foodDetailData(Integer.parseInt(fno));
				cList.add(vo);
						
			}
		}
	}
	
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
		<%-- 맛집 목록 --%>
		<%
			for(FoodBean vo:list){
		%>
			<div class="col-md-3">
					<div class="thumbnail">
						<a href="detail_before.jsp?fno=<%=vo.getFno() %>" target="_blank"> <img
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
		<div style="height:20px"></div> <%-- 칸 간격 조절 --%>		
		<div class="row">
		<%-- 페이지 --%>
			<div class="text-center">
				<a href="main.jsp?page=<%=curpage>1?curpage-1:curpage %>" class="btn btn-sm btn-danger">이전</a>
				<%=curpage %> page / <%=totalpage %> pages
				<a href="main.jsp?page=<%=curpage<totalpage?curpage+1:curpage %>" class="btn btn-sm btn-primary">다음</a>
			</div>
		</div>
		<div style="height:20px"></div> <%-- 칸 간격 조절 --%>
		<h3>최신 방문 맛집</h3>
		<a href="all_delete.jsp" class="btn btn-xs btn-warning">전체 삭제</a>
		<a href="all_cookie.jsp" class="btn btn-xs btn-warning">더보기</a>
		<hr>
		<div class="row">
		<%-- 최신 방문 --%>
		<table class="table">
			<tr>
		<%
			int i=0;
			if(cookies!=null && cList.size()>0){
				for(FoodBean vo:cList){
							// title은 마우스 오버시, 보여줄 문자
		%>
			
		<%
					if(i>9) break;
		%>									
					<td>					
					<img src="<%=vo.getPoster() %>" style="width:100px; height:100px" title="<%=vo.getName() %>">	
					<p>
					<a href="cookie_delete.jsp?fno=<%=vo.getFno() %>" class="btn btn-sm btn-danger">삭제</a>
					</td>
		<%
					i++;
				}
		%>
		</tr>
		</table>
		<%
			}
			else
			{
		%>
				<h3>쿠키가 없습니다.</h3>
		<%
			}
		%>
		</div>
	</div>
</body>
</html>