<%@page import="com.sist.vo.ReplyVO"%>
<%@page import="javax.xml.crypto.Data"%>
<%@page import="java.util.List"%>
<%@page import="com.sist.vo.DataBoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*, java.util.*, com.sist.vo.*"%>
<%-- 메모리 할당
	DataBoardDAO dao=new DataBoardDAO();
 --%>
<jsp:useBean id="dao" class="com.sist.dao.DataBoardDAO"/>
<%
	String no=request.getParameter("no");
	DataBoardVO vo=dao.databoardDetailData(Integer.parseInt(no),0);
	
	// id 읽기
	String id=(String)session.getAttribute("id");
	
	// 인기 10
	List<DataBoardVO> list=dao.databoardTop10();
	for(DataBoardVO tvo:list){
		String temp=tvo.getSubject();
		if(temp.length()>10){
			temp=temp.substring(0,10)+"...";
			tvo.setSubject(temp);
		}
		tvo.setSubject(temp);
	}
	
	// 댓글 받기
	List<ReplyVO> rList=dao.replyListData(Integer.parseInt(no));
	
	
%>
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

h1{
	text-align: center;
	font-family: 'Gasoek One', sans-serif;
}

</style>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
let i=0;
function rm() {
	if(i==0){
		document.querySelector("#del").style.display='';
		// $('#del').show()
		document.querySelector("#delBtn").textContent='취소';
		// $('#delBtn').text("취소")
		i=1;
	}
	else{
		document.querySelector("#del").style.display='none';
		// $('#del').hide()
		document.querySelector("#delBtn").textContent='삭제';
		// $('#delBtn').text("삭제")
		i=0;
	}
}

let u=0;

$(function(){	// JQUERY 시작점  $(document).ready(function(){ 코딩 영역 })
	$('.updates').click(function(){
		$('.ups').hide();
		$('.updates').text("수정");
		let no=$(this).attr("data-no")
		if(u==0){
			$('#u'+no).show("slow");
			$(this).text("취소");
			u=1;
		}else{
			$('#u'+no).hide("slow");
			$(this).text("수정");
			u=0;
		}
	})	
})
</script>
</head>
<body>
	<div class="container">
		<h1>내용 보기</h1>
		<div class="row">
			<table class="table">
				<tr>
					<th width=20% class="text-center danger">번호</th>
					<td width=30% class="text-center"><%=vo.getNo() %></td>
					<th width=20% class="text-center danger">작성일</th>
					<td width=30% class="text-center"><%=vo.getDbday() %></td>
				</tr>
				<tr>
					<th width=20% class="text-center danger">이름</th>
					<td width=30% class="text-center"><%=vo.getName() %></td>
					<th width=20% class="text-center danger">조회수</th>
					<td width=30% class="text-center"><%=vo.getHit() %></td>
				</tr>
				<tr>
					<th width=20% class="text-center danger">제목</th>
					<td colspan="3"><%=vo.getSubject() %></td>
				</tr>
				<%
					if(vo.getFilesize()!=0)
					{
				%>
				<tr>
					<th width=20% class="text-center danger">첨부파일</th>
					<td colspan="3">
					<a href="download.jsp?fn=<%=vo.getFilename() %>"><%=vo.getFilename() %>(<%=vo.getFilesize() %>Bytes)</a>
					</td>
				</tr>
				<%
					}
				%>
				<tr>
					<td colspan="4" class="text-left" valign="top" height="200">
						<pre style="white-space:pre-wrap;background-color: white;border:none"><%=vo.getContent() %></pre>
					</td>
				</tr>
				<tr>
					<td colspan="4" class="text-right">
						<a href="update.jsp?no=<%=vo.getNo() %>" class="btn btn-xs btn-info">수정</a>
						<span class="btn btn-xs btn-success" id="delBtn" onclick="rm()">삭제</span>
						<a href="list.jsp" class="btn btn-xs btn-warning">목록</a>
						<!-- back을 주면, hit 증가x -->
					</td>
				</tr>
				<tr style="display:none" id="del">
					<td colspan="4" class="text-right">
					<form method="post" action="delete.jsp">
					비밀번호:<input type="password" name=pwd size=15 class="input-sm" required>
						<input type=hidden name=no value="<%=vo.getNo() %>">
						<input type="submit" value="삭제" class="btn btn-sm btn-danger">
						</form>
					</td>
				</tr>
			</table>
		</div>
		<div class="row">
			<div class="col-sm-8">
				<table class="table">
				<%-- 댓글 --%>
					<tr>
						<td>
							<%
								for(ReplyVO rvo:rList){
									
							%>
								<table class="table">
									<tr>
										<td class="text-left">◐<%=rvo.getName() %>&nbsp;(<%=rvo.getDbday() %>)</td>
										<td class="text-right">
											<%
												if(id!=null){
													if(id.equals(rvo.getId())){
											%>
														<span class="btn btn-xs btn-danger updates" data-no="<%=rvo.getNo() %>">수정</span>
														<a href="reply_delete.jsp?no=<%=rvo.getNo() %>&bno=<%=rvo.getBno() %>" 
														class="btn btn-xs btn-warning">삭제</a>
											<%
													}
												}
											%>
										</td>
									</tr>
									
									<tr>
										<td colspan="2" class="text-left" valign="top">
										<pre style="white-space:pre-wrap;background-color:white; border:none"><%=rvo.getMsg() %></pre>
										</td>
									</tr>
									<tr class="ups" id="u<%=rvo.getNo()%>" style="display:none">
										<td colspan="2">
										<form method="post" action="reply_update.jsp">
											<textarea rows="4" cols="40" name="msg" style="float: left"><%=rvo.getMsg() %></textarea>
											<input type="hidden" name="bno" value="<%=vo.getNo()%>">
											<input type="hidden" name="no" value="<%=rvo.getNo()%>">
											<input type="submit" value="댓글수정" class="btn btn-sm btn-danger"
												style="width:80px;height:85px;">
										</form>
										</td>
									</tr>
								</table>
							<%
								}
							%>
						</td>
					</tr>
				</table>
				<div style="height:10px"></div>
				<%
					if(id!=null)
					{
				%>				
				<table class="table">
				<%-- 로그인된 사람만 댓글 가능 --%>
					<tr>
						<td>
						<form method="post" action="reply_insert.jsp">
							<input type="hidden" name="bno" value="<%=vo.getNo()%>">
							<textarea rows="4" cols="60" name="msg" style="float: left"></textarea>
							<input type="submit" value="댓글쓰기" class="btn btn-sm btn-danger"
								style="width:100px;height:85px;">
						</form>
						</td>
					</tr>
				</table>
				<%
				}
				%>
			</div>
			<div class="col-sm-4">
				<%-- 인기 게시물 --%>
				<table class="table">
					<caption>인기 Top 10 게시물</caption>	<!-- caption: 테이블 제목 -->
					<tr>
						<th>제목</th>
						<th>이름</th>
					</tr>
					<%
						for(DataBoardVO tvo:list){
					%>
							<tr>
								<td><a href="detail.jsp?no=<%=tvo.getNo() %>"><%=tvo.getSubject() %></a></td>
								<td><%=tvo.getName() %></td>
							</tr>
					<%		
						}
					%>
				</table>
			</div>
		</div>
	</div>
</body>
</html>