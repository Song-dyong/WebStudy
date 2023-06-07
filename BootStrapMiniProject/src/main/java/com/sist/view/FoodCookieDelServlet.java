package com.sist.view;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FoodCookieDelServlet")
public class FoodCookieDelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode=request.getParameter("mode");
		String cno=request.getParameter("cno");
		String fno=request.getParameter("fno");
		// 네트워크 프로그램 => 요청(Client: 브라우저) / 응답(Server: Servlet)
		// 					=> 요청처리에 필요한 데이터를 전송
		// 현재 1개의 쿠키만 지울 떄, 필요한 데이터 
		// mode -> 화면 변환을 위해서 / cno -> 출력할 카테고리를 위해서 / fno -> 지우기 위한 데이터를 찾기 위해서 
		// http://localhost:8080/BootStrapMiniProject/FoodListServlet?cno=9 
		// 이 화면으로 다시 돌아와야 함 --> mode에 해당하는 FoodListServlet을 가져오기 위한 mode
		// 							  cno에 해당하는 데이터 
		//							  지워야할 쿠키에 저장된 데이터인 fno
		
		// 쿠키 가져오기
		Cookie[] cookies=request.getCookies();
		if(cookies!=null)
		{
			for(int i=0;i<cookies.length;i++)
			{
				if(cookies[i].getName().equals("food_"+fno))
				{
					cookies[i].setPath("/");
					cookies[i].setMaxAge(0);
					response.addCookie(cookies[i]);
					break;
				}
			}
		}
		response.sendRedirect("MainServlet?mode=2&cno="+cno);
		
	}
}
