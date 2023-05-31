package com.sist.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;


/**
 * Servlet implementation class BoardListServlet
 */
@WebServlet("/BoardListServlet")


public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		// JSP 
		// 1. 변환 => 전송 (HTML, XML, JSON)
		// 브라우저로 미리 알려준다 => response
		response.setContentType("text/html;charset=UTF-8");
		// XML -> text/XML , JSON -> text/plain
		PrintWriter out=response.getWriter();
		// 사용자가 요청한 페이지를 받는다 => request
		String strPage=request.getParameter("page");
		if(strPage==null)
		{
			strPage="1"; // default --> 지정하지 않으면 500 오류
		}
		int curpage=Integer.parseInt(strPage);
		// 사용자의 브라우저에서 읽어가는 위치를 설정 => OutputStream
		BoardDAO dao=BoardDAO.newInstance();
		List<BoardVO> list=dao.boardListData(curpage);
		// 총 페이지 받기
		int totalpage=dao.boardTotalPage();
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=stylesheet href=html/table.css>");
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>자유게시판</h1>");
		out.println("<table width=700 class=table_content>");
		out.println("<tr>");
		out.println("<td>");
		out.println("<a href=BoardInsertServlet>새글</a>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("<table width=700 class=table_content>");
		out.println("<tr>");
		out.println("<th width=10%>번호</th>");
		out.println("<th width=45%>제목</th>");
		out.println("<th width=15%>이름</th>");
		out.println("<th width=20%>작성일</th>");
		out.println("<th width=10%>조회수</th>");
		out.println("</tr>");
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String today=sdf.format(date);	// 오늘 날짜 가져오기
		for(BoardVO vo:list)
		{
			out.println("<tr class=dataTr>");
			out.println("<td width=10% align=center>"+vo.getNo()+"</td>");
			// BoardDetailServlet?no= 에서 no=vo.getNo()의 요청을 BoardDetailServlet에서 처리
			// 이때, a태그는 get방식을 사용하므로 doGet을 가진 Servlet을 새로 생성
			// form태그만 post방식 사용 --> ?뒤의 내용이 너무 많아지므로 ...
			out.println("<td width=45%><a href=BoardDetailServlet?no="+vo.getNo()+">"+vo.getSubject()+"</a>");
			if(today.equals(vo.getDbday()))
			{
				out.println("&nbsp;<sup style=\"color:red\">new</sup>");
			}
			out.println("</td>");
			out.println("<td width=15% align=center>"+vo.getName()+"</td>");
			out.println("<td width=20% align=center>"+vo.getDbday()+"</td>");
			out.println("<td width=10% align=center>"+vo.getHit()+"</td>");
			out.println("</tr>");
		}
		out.println("<tr>");
		out.println("<td colspan=5 align=center>");
		out.println("<a href=BoardListServlet?page="
					+(curpage>1?curpage-1:curpage)+">이전</a>");
		out.println(curpage+" page / "+totalpage+" pages");
		// 현재 페이지가 전체 페이지보다 작다면, 현재페이지 +1
		out.println("<a href=BoardListServlet?page="
						+(curpage<totalpage?curpage+1:curpage)+">다음</a>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
		
	}

}
