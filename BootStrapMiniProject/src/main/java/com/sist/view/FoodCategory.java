package com.sist.view;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;

@WebServlet("/FoodCategory")
public class FoodCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 전송방식 => 브라우저로 보낸다 (미리 알려준다) => text/html (html 보냄) 캐릭터셋은 utf-8
		// html => text/html , xml => text/xml , json => text/plain
		response.setContentType("text/html;charset=UTF-8");
		// html을 저장 => 브라우저에서 읽어가는 위치에 저장 (PrintWriter)
		PrintWriter out=response.getWriter();
		// print.out	=>------------------ 사용자의 브라우저
		// 데이터 베이스 연결
		FoodDAO dao=FoodDAO.newInstance();
		List<CategoryVO> list=dao.food_category_list(); // 30개
		// 카테고리 정보를 오라클로부터 받아옴
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>");
		out.println(".container{margin-top:50px}"); // margin은 웹 페이지 전체 기준 패딩은 카드 기준
		out.println(".row{");
		out.println("margin:0px auto;"); // 가운데 정렬
		out.println("width:1024px}</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<h1>믿고보는 맛집 리스트</h1>");
		out.println("<div class=row>");
		for(int i=0;i<12;i++)
		{
			CategoryVO vo=list.get(i);
			out.println("<div class=\"col-md-3\">");	// 한 줄에 4개씩 출력
			out.println("<div class=\"thumbnail\">");		
			out.println("<a href=\"MainServlet?mode=2&cno="+vo.getCno()+"\">");	// target=\"_blank\"
			out.println("<img src=\""+vo.getPoster()+"\" style=\"width:100%\">");
			out.println("<div class=\"caption\">");
			out.println("<p style=\"font-size:9px\">"+vo.getTitle()+"</p>");
			out.println("</div>");
			out.println("</a>");
			out.println("</div>");
			out.println("</div>");
		}
		out.println("</div>");	// row
		
		out.println("<h1>지역별 맛집 리스트</h1>");
		out.println("<div class=row>");
		for(int i=12;i<18;i++)
		{
			CategoryVO vo=list.get(i);
			out.println("<div class=\"col-md-4\">");	// 한 줄에 4개씩 출력
			out.println("<div class=\"thumbnail\">");		
			out.println("<a href=\"MainServlet?mode=2&cno="+vo.getCno()+"\">");	// target=\"_blank\"
			out.println("<img src=\""+vo.getPoster()+"\" style=\"width:100%\">");
			out.println("<div class=\"caption\">");
			out.println("<p style\"font-size:9px\">"+vo.getTitle()+"</p>");
			out.println("</div>");
			out.println("</a>");
			out.println("</div>");
			out.println("</div>");
		}
		out.println("</div>");	// row
		
		out.println("<h1>메뉴별 맛집 리스트</h1>");
		out.println("<div class=row>");
		for(int i=18;i<30;i++)
		{
			CategoryVO vo=list.get(i);
			out.println("<div class=\"col-md-3\">");	// 한 줄에 4개씩 출력
			out.println("<div class=\"thumbnail\">");		
			out.println("<a href=\"MainServlet?mode=2&cno="+vo.getCno()+"\">");	// target=\"_blank\"
			out.println("<img src=\""+vo.getPoster()+"\" style=\"width:100%\">");
			out.println("<div class=\"caption\">");
			out.println("<p style\"font-size:9px\">"+vo.getTitle()+"</p>");
			out.println("</div>");
			out.println("</a>");
			out.println("</div>");
			out.println("</div>");
		}
		out.println("</div>");	// row
		
		out.println("</div>");	// container
		out.println("</body>");
		out.println("</html>");
	}

}
