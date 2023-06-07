package com.sist.view;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;

@WebServlet("/FoodListServlet")
public class FoodListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// html을 출력한다고 브라우저에 알리기
		response.setContentType("text/html;charset=UTF-8");
		// 클라이언트가 전송값을 받는다
		String cno = request.getParameter("cno");
		// DAO연동
		FoodDAO dao = FoodDAO.newInstance();
		List<FoodVO> list = dao.food_category_data(Integer.parseInt(cno));
		CategoryVO cvo = dao.food_category_info(Integer.parseInt(cno));

		// 화면에 출력
		PrintWriter out = response.getWriter();
		// html 출력 => 오라클에서 받은 결과값 출력
		out.println("<html>");
		out.println("<head>");
		out.println(
				"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>");
		out.println(".container{margin-top:50px}"); // margin은 웹 페이지 전체 기준 패딩은 카드 기준
		out.println(".row{");
		out.println("margin:0px auto;"); // 가운데 정렬
		out.println("width:800px}</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<div class=row>");
		out.println("</row>");

		out.println("<div class=jumbotron>");
		out.println("<center>");
		out.println("<h2>" + cvo.getTitle() + "</h2>");
		out.println("<h4>" + cvo.getSubject() + "</h4>");
		out.println("</center>");
		out.println("</div>"); // jumbotron
		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<td>");
		for (FoodVO vo : list) {
			out.println("<table class=table>");
			out.println("<tr>");
			out.println("<td width=30% align=center rowspan=4>");
			out.println("<a href=FoodDetailBeforeServlet?mode=3&fno=" + vo.getFno() + "><img src=" + vo.getPoster()
					+ " class=img-rounded style=\"width:240px; height:200px\"></a>");
			out.println("</td>");
			out.println("<td width=70%><h3>");
			out.println("<a href=FoodDetailBeforeServlet?mode=3&fno=" + vo.getFno() + ">" + vo.getName()
					+ "&nbsp<span style=\"color:orange\"></a>" + vo.getScore() + "</span>");
			out.println("</h3></td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td width=70%>");
			out.println(vo.getAddress());
			out.println("</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td width=70%>");
			out.println(vo.getPhone());
			out.println("</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td width=70%>");
			out.println(vo.getType());
			out.println("</td>");
			out.println("</tr>");
			out.println("</table>");
		}
		out.println("</td>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</div>"); // row
		out.println("<h3>최근 방문 맛집</h3>");
		out.println(
				"<a href=\"FoodCookieDeleteServlet?mode=2&cno=" + cno + "\" class=\"btn btn-xs btn-danger\">전체삭제</a>");
		out.println("<hr>");
		// 쿠키 처리 (최근 본 페이지)
		// 1. 쿠키 읽어오기
		Cookie[] cookies = request.getCookies();
		List<FoodVO> cList = new ArrayList<FoodVO>();
		if (cookies != null) {
			for (int i = cookies.length - 1; i >= 0; i--) // 최신순 표시
			{
				// Cookie cookie=new Cookie("food_"+fno, fno);
				// key 값을 가져오는 getName() / value 값을 가져오는 getValue()
				// request, response, session, cookie => Spring-Boot까지 계속 사용
				// Spring Library는 Servlet으로 이루어짐
				if (cookies[i].getName().startsWith("food_")) {
					String fno = cookies[i].getValue();
					FoodVO vo = dao.foodDetailData(Integer.parseInt(fno));
					String temp = vo.getPoster();
					temp = temp.substring(0, temp.indexOf("^"));
					// 테이블 컬럼에 저장시에는 한 개의 값만 저장 -> 관리가 쉬움
					// => 2정규화 / 1정규화는 중복을 없애는 것 / 3정규화는 2개를 묶어서 새로운 테이블을 만드는 것
					vo.setPoster(temp);
					cList.add(vo);
				}
			}
		}
		int i = 0;
		if (cList != null && cList.size() > 0) {
			for (FoodVO vo : cList) {
				if (i > 7)
					break;
				out.println("<div class=\"col-md-3\">"); // 한 줄에 4개씩 출력
				out.println("<div class=\"thumbnail\">");
				out.println("<a href=\"MainServlet?mode=3&fno=" + vo.getFno() + "\">"); // target=\"_blank\"
				out.println("<img src=\"" + vo.getPoster() + "\" style=\"width:230px; height:200px\"></a>");
				out.println("<div class=\"caption\">");
				// white-space: nowrap;
				out.println("<p style=\"font-size:9px\">" + vo.getName() + "</p>");
				// 쿠키 1개만 삭제하기
				out.println("<p><a href=\"FoodCookieDelServlet?mode=2&cno=" + vo.getCno() + "&fno=" + vo.getFno()
						+ "\" class=\"btn btn-xs btn-warning\">삭제</a>");
				out.println("</div>");
				out.println("</div>");
				out.println("</div>");
				i++;
			}
		}
		out.println("</div>"); // container
		out.println("</body>");
		out.println("</html>");

	}

}
