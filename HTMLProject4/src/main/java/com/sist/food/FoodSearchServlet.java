package com.sist.food;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;

/*
	Spring에서는..
	@GetMapping		=> GET
	@PostMapping	=> POST
	@RequestMapping	=> GET/POST				  ----
	---------------------------- 형식이 안맞으면 |400|에러
											  ----
*/
/**
 * Servlet implementation class FoodSearchServlet
 */
@WebServlet("/FoodSearchServlet")
public class FoodSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		// 사용자의 요청값 받기
		String addr=request.getParameter("addr");
		if(addr==null)
		{
			addr="마포";	// 초기값이 없으면, |500| 오류
		}
		String strPage=request.getParameter("page");
		if(strPage==null)
		{
			strPage="1"; // 초기값이 없으면, |500| 오류
		}
		int curpage=Integer.parseInt(strPage);
		
		FoodDAO dao=FoodDAO.newInstance();
		List<FoodVO> list=dao.foodFindData(addr, curpage);
		int totalpage=(int)(Math.ceil(dao.foodRowCount(addr)/12.0));
		int count=dao.foodRowCount(addr);	// 0일 경우, 검색결과가 없는 것을 보여주기 위해 count변수 설정
		final int BLOCK=5;
		// curpage가 1~5일 때, (curpage-1)/BLOCK은 0으로 계산된 뒤, BLOCK를 곱하기 때문에 (자바로직)
		//	curpage가 1~5일 때, startPage는 항상 1을 유지함
		int startPage=((curpage-1)/BLOCK*BLOCK)+1;
		int endPage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
		// < [1][2][3][4][5] >
		// < [6][7][8][9][10] >
		//	startPage	EndPage
		// 페이징 기법
		
		// 화면
		/*
		<ul class="pagination">
		  <li><a href="#">1</a></li>
		  <li class="active"><a href="#">2</a></li>
		  <li><a href="#">3</a></li>
		  <li><a href="#">4</a></li>
		  <li><a href="#">5</a></li>
		</ul>
		*/
		if(endPage>totalpage)
		{
			endPage=totalpage;
		}
		// 마지막 페이지까지 갈 경우, 최종 페이지가 토탈페이지가 되도록 코딩
		PrintWriter out=response.getWriter();
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
		out.println("<div class=row>");
		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<td>");
		out.println("<form method=post action=FoodSearchServlet>");
		out.println("<input type=text name=addr size=25 class=input-sm>");
		out.println("<input type=submit value=검색 class=\"btn btn-sm btn-danger\">");
		out.println("</form>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("<div style=\"height:30px\"></div>");
		for(FoodVO vo:list)
		{
			out.println("<div class=\"col-md-3\">");	// 한 줄에 4개씩 출력
			out.println("<div class=\"thumbnail\">");		
			out.println("<a href=\"#\">");	// target=\"_blank\"
			out.println("<img src=\""+vo.getPoster()+"\" style=\"width:100%\">");
			out.println("<div class=\"caption\">");
			out.println("<p style=\"font-size:9px\">"+vo.getName()+"</p>");
			out.println("</div>");
			out.println("</a>");
			out.println("</div>");
			out.println("</div>");
		}
		out.println("</div>"); // row
		out.println("<div style=\"height:10px\"></div>");
		out.println("<div class=row>");
		out.println("<div class=text-center>");
		out.println("<ul class=pagination>");
		out.println("<li><a href=#>&lt;</a></li>");
		for(int i=startPage;i<=endPage;i++)
		{
			out.println("<li "+(curpage==i?"class=active":"")+"><a href=FoodSearchServlet?page="+i+">"+i+"</a></li>");
		}
		out.println("<li><a href=#>&gt;</a></li>");  
		out.println("</ul>");
		out.println("</div>");
		out.println("</div>");
		out.println("</div>"); // container
		out.println("</body>");
		out.println("</html>");
		
	}

}
