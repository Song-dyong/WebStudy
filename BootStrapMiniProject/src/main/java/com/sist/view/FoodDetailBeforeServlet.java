package com.sist.view;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FoodDetailBeforeServlet")
public class FoodDetailBeforeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// foodlistservlet에서 보내준 2개의 값을 스트링에 저장
		String mode=request.getParameter("mode");
		String fno=request.getParameter("fno");
		// 1. 쿠키 생성
		Cookie cookie=new Cookie("food_"+fno, fno);
		// 2. 저장 기간을 설정 (초 단위로 저장) 
		cookie.setMaxAge(60*60*24);
		// 3. 저장 루트 결정
		cookie.setPath("/");
		// 4. 브라우저에 전송
		response.addCookie(cookie);
		// 5. 화면 이동 명령
		response.sendRedirect("MainServlet?mode="+mode+"&fno="+fno);
		// 본인의 브라우저에 저장
		// 단점: 보안에 취약 , 저장값은 무조건 String
		/*
				실행방법
			1. Cookie 생성
				Cookie cookie=new Cookie("food_"+fno, fno);
			2. 저장 기간을 설정
			3. 저장 루트 결정
			4. 브라우저로 전송
			
				response는 HTML/Cookie를 브라우저로 전송하는 역할
				=> 한 개의 Servlet / JSP에서 한 개만 전송 가능
		*/
	}
}
