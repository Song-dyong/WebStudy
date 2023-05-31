package com.sist.servlet;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
import com.sist.dao.*;
@WebServlet("/BoardInsertServlet")
public class BoardInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 입력을 전송 => HTML (HTML을 브라우저로 보낸다)
		response.setContentType("text/html;charset=UTF-8");
		// 메모리에 HTML을 저장 => 브라우저에서 읽어서 출력
		PrintWriter out=response.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=stylesheet href=html/table.css>");
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>글쓰기</h1>");
		// 입력된 데이터를 한 번에 action에 등록된 클래스로 전송 (input에 입력된 데이터만 전송)
		out.println("<form method=post action=BoardInsertServlet>");
		// post로 보냈으면, 처리할 때 doPost메소드에서 처리
		// doPost()를 호출
		out.println("<table class=table_content width=700>");
		out.println("<tr>");
		out.println("<th width=15%>이름</th>");
		out.println("<td width=85%><input type=text name=name size=15 required></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=15%>제목</th>");
		out.println("<td width=85%><input type=text name=subject size=50 required></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=15%>내용</th>");
		out.println("<td width=85%><textarea rows=10 cols=50 name=content required></textarea></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=15%>비밀번호</th>");
		out.println("<td width=85%><input type=password name=pwd size=10 required></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td colspan=2 align=center>");
		out.println("<input type=submit value=글쓰기>");
		out.println("<input type=button value=취소 onclick=\"javascript:history.back()\">");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</form>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
	}

	/*
	 http://localhost:8080/HTMLProject1/BoardInsertServlet?
	 name=%EC%86%A1%EB%91%90%EC%9A%A9&
	 subject=%EC%B2%98%EC%9D%8C+%EB%A7%8C%EB%93%9C%EB%8A%94+%EA%B2%8C%EC%8B%9C%ED%8C%90&
	 content=%EC%B2%98%EC%9D%8C+%EB%A7%8C%EB%93%9C%EB%8A%94+%EA%B2%8C%EC%8B%9C%ED%8C%90&
	 pwd=1234
	 --> 한글이 바이트 코드로 인코딩됨 --> 바이트코드를 다시 디코딩해서 저장해야함 (setCharacterEncoding)
	 		%EC%86%A1%EB%91%90%EC%9A%A9&(인코딩) ==> 홍길동 (디코딩)
	 		디코딩: 바이트 배열을 한글로 변환
	 			why? 자바에서 한글은 2byte --> 브라우저에서는 1byte로 받는다 
	 			--> 한글을 1byte단위의 바이트코드로 변환하는 것이 인코딩
	 			--> 바이트코드를 다시 자바에서 2byte 단위의 한글로 변환하는 것이 디코딩
	 			
	 		★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★	
	 		★	디코딩에서 사용되는 메소드 setCharacterEncoding  ★
	 		★	화면이동에 사용되는 메소드 sendRedirect		   ★
	 		★	값을 읽어오는 메소드		 getParameter		   ★
	 		★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★	
	 		
	 		
	 		
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			request.setCharacterEncoding("UTF-8");	// 한글 변환 
		} catch (Exception e) {
			// TODO: handle exception
		}
		// 값을 받는다
		String name=request.getParameter("name");	// name= 뒤의 값을 받아옴
		String subject=request.getParameter("subject");	// getParameter() 사이에는 "변수명"을 입력
		String content=request.getParameter("content");
		String pwd=request.getParameter("pwd");
		
		
//		System.out.println("이름: "+name);
//		System.out.println("제목: "+subject);
//		System.out.println("내용: "+content);
//		System.out.println("비밀번호: "+pwd);
		
		// dao에서 boardInsert는 BoardVO를 매개변수로 받기때문에, 위의 값을 vo에 모아야 함
		BoardVO vo=new BoardVO();
		vo.setName(name);
		vo.setSubject(subject);
		vo.setContent(content);
		vo.setPwd(pwd);
		
		// 오라클로 INSERT요청
		BoardDAO dao=BoardDAO.newInstance();
		dao.boardInsert(vo);
		
		// 화면이동
		response.sendRedirect("BoardListServlet");
		
		
	}

}
